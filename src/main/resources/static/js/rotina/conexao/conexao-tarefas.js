const taskRouteUrl = 'http://localhost:8080/api/tarefas';

function findAllTasks(processData) {
    fetch(taskRouteUrl)
        .then((response) => response.json())
        .then((data) => {
            processData(data);
        })
        .catch((error) => {
            console.error('Erro ao encontrar tarefas na API:', error);
            displayMessage('Erro ao encontrar tarefas', 'danger');
        });
}

function findTaskById(taskId, processData) {
    fetch(`${taskRouteUrl}/${taskId}`)
        .then((response) => response.json())
        .then((data) => {
            processData(data);
        })
        .catch((error) => {
            console.error('Erro ao encontrar tarefa na API:', error);
            displayMessage('Erro ao ler tarefa', 'danger');
        });
}

function createTask(task, updateFunction) {
    fetch(taskRouteUrl, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(task),
    })
        .then((response) => {
            if (response.status === 201) return response.json();
            throw new Error('Falha ao criar tarefa.');
        })
        .then((data) => {
            displayMessage('Tarefa criada com sucesso', 'success');
            if (updateFunction) {
                updateFunction();
            }
        })
        .catch((error) => {
            console.error('Erro ao criar tarefa na API:', error);
            displayMessage('Erro ao criar tarefa', 'danger');
        });
}

function deleteTask(id, updateFunction) {
    fetch(`${taskRouteUrl}/${id}`, {
        method: 'DELETE',
    })
    .then((response) => {
        if (response.ok) {
            displayMessage('Tarefa removida com sucesso', 'primary');
            if (updateFunction) {
                updateFunction();
            }
            location.reload(); 
        } else {
            throw new Error('Falha ao deletar tarefa.');
        }
    })
    .catch((error) => {
        console.error('Erro ao remover tarefa da API:', error);
        displayMessage('Erro ao remover tarefa', 'danger');
    });
}