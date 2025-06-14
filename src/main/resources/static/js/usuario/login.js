let userId = localStorage.getItem('userId');

if (userId) {
    window.location.replace('./editar-informacoes.html');
} else {
    let db = [];

    findAllUsers((data) => {
        db = data;
        verificarUsuario();
    });

    function verificarUsuario() {
        let formularioLogin = document.querySelector('form');
        let botaoLogin = document.getElementById('botao-login');

        botaoLogin.addEventListener('click', (e) => {
            e.preventDefault();

            let campoEmail = document.getElementById('email').value;
            let campoSenha = document.getElementById('password').value;

            if (!formularioLogin.checkValidity()) {
                displayMessage(
                    'Preencha o formulário corretamente.',
                    'warning'
                );
                return;
            }

            // Correção 1: usa 'usuario.senha'
            const usuarioEncontrado = db.find(
                (usuario) =>
                    usuario.email === campoEmail &&
                    usuario.senha === CryptoJS.SHA256(campoSenha).toString()
            );

            if (usuarioEncontrado) {
                // Correção 2: usa 'usuarioEncontrado.id'
                localStorage.setItem('userId', usuarioEncontrado.id);
                window.location.replace('./editar-informacoes.html');
            } else {
                displayMessage('Email ou senha incorretos.', 'danger');
            }
        });
    }

    document
        .getElementById('togglePassword')
        .addEventListener('click', function () {
            const passwordField = document.getElementById('password');
            const type =
                passwordField.getAttribute('type') === 'password'
                    ? 'text'
                    : 'password';
            passwordField.setAttribute('type', type);
            this.classList.toggle('fa-eye');
            this.classList.toggle('fa-eye-slash');
        });
}