const userRouteUrl = 'http://localhost:8081/api/usuarios';

function findAllUsers(processData) {
    fetch(userRouteUrl)
        .then((response) => response.json())
        .then((data) => {
            processData(data);
        })
        .catch((error) => {
            console.error('Erro ao encontrar usuários na API:', error);
            displayMessage('Erro ao encontrar usuários', 'danger');
        });
}

function findUserById(userId, processData) {
    fetch(`${userRouteUrl}/${userId}`)
        .then((response) => response.json())
        .then((data) => {
            processData(data);
        })
        .catch((error) => {
            console.error('Erro ao encontrar usuário na API:', error);
            displayMessage('Erro ao encontrar usuário', 'danger');
        });
}

function createUser(user, updateFunction) {
    fetch(userRouteUrl, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(user),
    })
        .then((response) => {
             if (response.status === 201) return response.json();
             throw new Error("Falha no cadastro.")
        })
        .then((data) => {
            displayMessage('Usuário cadastrado com sucesso', 'success');
            if (updateFunction) {
                updateFunction();
            }
        })
        .catch((error) => {
            console.error('Erro ao cadastrar usuário na API:', error);
            displayMessage('Erro ao cadastrar usuário', 'danger');
        });
}

function updateUser(id, user, updateFunction) {
    fetch(`${userRouteUrl}/${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(user),
    })
        .then((response) => response.json())
        .then((data) => {
            if (updateFunction) {
                updateFunction();
            }
        })
        .catch((error) => {
            console.error('Erro ao alterar dados do usuário da API:', error);
            displayMessage('Erro ao alterar dados do usuário', 'danger');
        });
}

function deleteUser(id, updateFunction) {
    fetch(`${userRouteUrl}/${id}`, {
        method: 'DELETE',
    })
    .then((response) => {
        if(response.ok) {
            displayMessage('Conta excluída com sucesso', 'success');
            if (updateFunction) {
                updateFunction();
            }
        } else {
            throw new Error("Falha ao excluir conta.")
        }
    })
    .catch((error) => {
        console.error('Erro ao excluir conta da API:', error);
        displayMessage('Erro ao remover conta', 'danger');
    });
}