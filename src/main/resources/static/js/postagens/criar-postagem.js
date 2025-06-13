document.addEventListener('DOMContentLoaded', () => {
    const postForm = document.getElementById('postForm');
    if (!postForm) {
        console.error('Formulário de postagem não encontrado.');
        return;
    }

    const userId = localStorage.getItem('userId');
    if (!userId) {
        alert('Acesso restrito. Por favor, faça login para criar uma postagem.');
        window.location.href = '../usuario/login.html';
        return;
    }

    postForm.addEventListener('submit', handlePostSubmit);
});

async function handlePostSubmit(event) {
    event.preventDefault();

    const titleInput = document.getElementById('postTitle');
    const contentInput = document.getElementById('postContent');
    const selectedImageUrlInput = document.getElementById('selectedImageUrl');
    const submitBtn = document.getElementById('btnCadastrarPostagem');

    const title = titleInput.value.trim();
    const content = contentInput.value.trim();
    const imageUrl = selectedImageUrlInput.value;

    if (title.length < 5 || content.length < 20) {
        displayMessage('O título deve ter no mínimo 5 caracteres e o conteúdo no mínimo 20.', 'warning');
        return;
    }

    if (!imageUrl) {
        displayMessage('Por favor, gere e selecione uma imagem para a postagem.', 'warning');
        return;
    }

    submitBtn.disabled = true;
    submitBtn.innerHTML = '<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span> Publicando...';

    try {
        const autorId = localStorage.getItem('userId');
        
        const postData = {
            titulo: title,
            conteudo: content,
            autorId: autorId, 
            imagemUrl: imageUrl,
        };

        createPost(postData, (novaPostagem) => {
            displayMessage('Postagem criada com sucesso! Redirecionando...', 'success');
            setTimeout(() => {
                window.location.href = './postagens.html';
            }, 1500);
        });

    } catch (error) {
        console.error('Erro ao criar postagem:', error);
        displayMessage('Ocorreu um erro ao criar a postagem.', 'danger');
        submitBtn.disabled = false;
        submitBtn.innerHTML = '<i class="fas fa-paper-plane me-2"></i>Publicar';
    }
}