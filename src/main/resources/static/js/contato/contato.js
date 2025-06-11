function init() {
    let formularioContatos = document.querySelector('form');
    let btnCadastrarContatos = document.getElementById('btnCadastrarContato');

    btnCadastrarContatos.addEventListener('click', (e) => {
        let campoNome = document.getElementById('nome').value;
        let campoEmail = document.getElementById('email').value;
        let campoMensagem = document.getElementById('mensagem').value;

        e.preventDefault();

        if (!formularioContatos.checkValidity()) {
            displayMessage('Preencha o formulário corretamente.', 'warning');
            return;
        }

        if (campoNome.trim().length < 10) {
            displayMessage(
                'O nome deve ter pelo menos 10 caracteres.',
                'warning'
            );
            return;
        }

        if (campoMensagem.trim().length < 20) {
            displayMessage(
                'A mensagem deve ter pelo menos 20 caracteres.',
                'warning'
            );
            return;
        }

        // Payload ajustado para corresponder ao modelo do backend
        let contato = {
            nome: campoNome,
            email: campoEmail,
            telefone: '', // Campo opcional
            assunto: 'Contato via Site', // Valor padrão
            mensagem: campoMensagem,
        };

        createContact(contato, () => {
             formularioContatos.reset();
        });
    });
}