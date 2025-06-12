var db = [];

findAllPosts((data) => {
    db = data;
    listPosts();
});

function listPosts() {
    let divpostagens = document.getElementById('postagens');
    let filtroTema = document.getElementById('filtro-tema').value.toLowerCase(); // Convertido para minúsculas para busca case-insensitive

    divpostagens.innerHTML = '';

    for (let i = 0; i < db.length; i++) {
        const post = db[i];

        // Melhoria na lógica de filtro para ser case-insensitive
        if (
            post.title.toLowerCase().includes(filtroTema) ||
            post.content.toLowerCase().includes(filtroTema) ||
            filtroTema === ''
        ) {
            // ALTERAÇÕES AQUI para usar os nomes corretos dos campos do DTO
            divpostagens.innerHTML += `
                <div class="card" style="width: 18rem;" data-id="${post.id}">
                    <img class="card-img-top" src="${post.imageLink}" alt="Imagem da Postagem">
                    <div class="card-body">
                        <div class="descricao">
                            <h3 class="card-title">${post.title}</h3>
                            <h5 class="autor">${post.author}</h5>
                        </div>
                    </div>
                </div>
            `;
        }
    }

    let postCards = document.querySelectorAll('.card');
    postCards.forEach((card) => {
        card.addEventListener('click', (event) => {
            event.preventDefault();
            let postId = card.getAttribute('data-id');
            // O endpoint de detalhes ainda precisa ser ajustado para retornar o DTO também,
            // mas por enquanto, isso fará a listagem funcionar.
            window.location.href = `./detalhes-postagem.html?postId=${postId}`;
        });
    });
}

document
    .getElementById('search-form')
    .addEventListener('submit', function (event) {
        event.preventDefault();
        listPosts();
    });