let userId = localStorage.getItem('userId');
let modal = document.getElementById('modal');

if (!userId) {
    alert('Acesso restrito. Por favor, faça login para continuar.');
    window.location.replace('../usuario/login.html');
} else {
    modal.style.opacity = 1;
    init();
}

function init() {
    let formularioTarefa = document.querySelector('form');
    let btnCadastrarTarefa = document.getElementById('btnSalvarTarefa');

    btnCadastrarTarefa.addEventListener('click', (e) => {
        e.preventDefault();

        let tituloTarefa = document.getElementById('tituloTarefa').value;
        // Campos de dia e hora são ignorados pois não existem no backend.
        // let diaDaSemana = document.getElementById('diaDaSemana').value;
        // let horaTarefa = document.getElementById('horaTarefa').value;

        if (!formularioTarefa.checkValidity()) {
            displayMessage(
                'Preencha o formulário corretamente.',
                'warning'
            );
            return;
        }

        if (tituloTarefa.trim().length < 5) {
            displayMessage(
                'O título da tarefa deve ter pelo menos 5 caracteres.',
                'warning'
            );
            return;
        }

        const tarefa = {
            titulo: tituloTarefa,
            descricao: "Tarefa da rotina", // Valor Padrão
            status: "PENDENTE", // Valor Padrão
            prioridade: "MEDIA", // Valor Padrão
            usuarioId: parseInt(userId, 10),
        };

        createTask(tarefa, () => {
             formularioTarefa.reset();
        });
    });
}