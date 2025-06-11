const postRouteUrl = 'http://localhost:8080/api/postagens';

function findAllPosts(processData) {
    fetch(postRouteUrl)
        .then((response) => response.json())
        .then((data) => {
            processData(data);
        })
        .catch((error) => {
            console.error('Erro ao ler postagens na API:', error);
            displayMessage('Erro ao carregar postagens', 'danger');
        });
}

function findPostById(postId, processData) {
    fetch(`${postRouteUrl}/${postId}`)
        .then((response) => response.json())
        .then((data) => {
            processData(data);
        })
        .catch((error) => {
            console.error(
                'Erro ao encontrar postagem na API:',
                error
            );
            displayMessage('Erro ao ler postagem', 'danger');
        });
}

function createPost(post, updateFunction) {
    fetch(postRouteUrl, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(post),
    })
        .then((response) => {
            if (response.status === 201) {
                return response.json();
            }
            throw new Error('Falha ao criar postagem.');
        })
        .then((data) => {
            displayMessage('Postagem criada com sucesso!', 'success');
            if (updateFunction) {
                updateFunction(data);
            }
        })
        .catch((error) => {
            console.error('Erro ao criar postagem na API:', error);
            displayMessage('Erro ao criar postagem', 'danger');
        });
}

function updatePost(id, post, updateFunction) {
    fetch(`${postRouteUrl}/${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(post),
    })
        .then((response) => response.json())
        .then((data) => {
            if (updateFunction) {
                updateFunction();
            }
        })
        .catch((error) => {
            console.error('Erro ao atualizar postagem da API:', error);
            displayMessage('Erro ao atualizar postagem', 'danger');
        });
}

function deletePost(id, updateFunction) {
    fetch(`${postRouteUrl}/${id}`, {
        method: 'DELETE',
    })
        .then((response) => {
            if (response.ok) {
                location.reload();
                 if (updateFunction) {
                    updateFunction();
                }
            } else {
                 throw new Error('Falha ao deletar postagem.');
            }
        })
        .catch((error) => {
            console.error('Erro ao remover postagem da API:', error);
        });
}