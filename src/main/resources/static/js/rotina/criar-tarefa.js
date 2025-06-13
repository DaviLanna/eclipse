let userId = localStorage.getItem('userId');
let modal = document.getElementById('modal');

if (!userId) {
    alert('Acesso restrito. Por favor, faça login para continuar.');
    window.location.replace('../usuario/login.html');
} else {
    // A função init() é chamada pelo 'onload' no body do HTML
    // Garante que o código só rode após o carregamento da página
}

function init() {
    modal.style.opacity = 1;
    let formularioTarefa = document.querySelector('form');
    let btnCadastrarTarefa = document.getElementById('btnSalvarTarefa');

    btnCadastrarTarefa.addEventListener('click', (e) => {
        e.preventDefault();

        let tituloTarefa = document.getElementById('tituloTarefa').value;
        // CORREÇÃO: Lendo os valores dos campos de dia e hora
        let diaDaSemana = document.getElementById('diaDaSemana').value;
        let horaTarefa = document.getElementById('horaTarefa').value;

        if (!formularioTarefa.checkValidity() || diaDaSemana === "" || horaTarefa === "") {
            displayMessage('Preencha todos os campos corretamente.', 'warning');
            return;
        }

        if (tituloTarefa.trim().length < 3) {
            displayMessage('O título da tarefa deve ter pelo menos 3 caracteres.', 'warning');
            return;
        }

        const tarefa = {
            titulo: tituloTarefa,
            diaDaSemana: diaDaSemana, // ENVIANDO O DIA
            horario: horaTarefa,     // ENVIANDO A HORA
            descricao: "Tarefa da rotina",
            status: "PENDENTE",
            prioridade: "MEDIA",
            usuarioId: userId,
        };

        createTask(tarefa, () => {
             // Redireciona para a página de edição após o sucesso
             window.location.href = './editar-rotina.html';
        });
    });
}