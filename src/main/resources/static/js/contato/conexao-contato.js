const postRouteUrl = 'http://localhost:8081/api/contatos';

function createContact(contact, updateFunction) {
    fetch(postRouteUrl, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(contact),
    })
        .then((response) => {
            if (response.ok) {
                return response.json();
            }
            throw new Error('Falha ao enviar mensagem.');
        })
        .then((data) => {
            displayMessage(
                'Mensagem enviada com sucesso!',
                'success'
            );

            if (updateFunction) {
                updateFunction();
            }
        })
        .catch((error) => {
            console.error(
                'Erro ao enviar mensagem para a API:',
                error
            );
            displayMessage('Erro ao enviar mensagem', 'danger');
        });
}