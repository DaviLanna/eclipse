document.addEventListener('DOMContentLoaded', () => {
    const generateBtn = document.getElementById('generateImagesBtn');
    const postTitleInput = document.getElementById('postTitle');
    const imagesContainer = document.getElementById('generatedImagesContainer');
    const loadingElement = document.getElementById('loadingImages');
    const errorContainer = document.getElementById('errorContainer');
    const errorMessage = document.getElementById('errorMessage');
    const selectedImageUrlInput = document.getElementById('selectedImageUrl');

    if (!generateBtn) return;

    generateBtn.addEventListener('click', async () => {
        const title = postTitleInput.value.trim();
        if (title.length < 5) {
            showError('Por favor, insira um título com pelo menos 5 caracteres.');
            return;
        }

        showLoading(true);
        hideError();
        imagesContainer.classList.add('d-none');
        imagesContainer.innerHTML = '';
        selectedImageUrlInput.value = ''; // Limpa seleção anterior

        try {
            const response = await fetch('http://localhost:8081/api/generate-images', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ titulo: title })
            });

            if (!response.ok) {
                const errData = await response.json();
                throw new Error(errData.error || 'Falha ao gerar imagens.');
            }

            const imageUrls = await response.json();
            if (imageUrls && imageUrls.length > 0) {
                displayImages(imageUrls);
            } else {
                throw new Error('A API não retornou imagens.');
            }

        } catch (error) {
            console.error("Erro no fetch das imagens:", error);
            showError(error.message);
        } finally {
            showLoading(false);
        }
    });

    function displayImages(urls) {
        imagesContainer.innerHTML = ''; // Limpa imagens antigas
        urls.forEach((url, index) => {
            const col = document.createElement('div');
            col.className = 'col-6 col-md-3 mb-3'; // 2 por linha em mobile, 4 em desktop
            col.innerHTML = `
                <div class="image-option" data-url="${url}">
                    <img src="${url}" class="img-fluid rounded" alt="Imagem gerada ${index + 1}">
                    <div class="selection-overlay">
                        <i class="fas fa-check-circle"></i>
                    </div>
                </div>
            `;
            imagesContainer.appendChild(col);
        });
        imagesContainer.classList.remove('d-none');
        addSelectionEvent();
    }

    function addSelectionEvent() {
        document.querySelectorAll('.image-option').forEach(option => {
            option.addEventListener('click', function() {
                document.querySelectorAll('.image-option').forEach(el => el.classList.remove('selected'));
                
                this.classList.add('selected');
                selectedImageUrlInput.value = this.dataset.url;
            });
        });
    }
    
    function showLoading(isLoading) {
        loadingElement.classList.toggle('d-none', !isLoading);
    }

    function showError(message) {
        errorMessage.textContent = message;
        errorContainer.classList.remove('d-none');
    }

    function hideError() {
        errorContainer.classList.add('d-none');
    }
});