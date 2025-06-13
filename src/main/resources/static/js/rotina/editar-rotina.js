let userId = localStorage.getItem('userId');
let modal = document.getElementById('modal');
const diasDaSemana = ['Domingo', 'Segunda-feira', 'Terça-feira', 'Quarta-feira', 'Quinta-feira', 'Sexta-feira', 'Sábado'];

if (!userId) {
    alert('Acesso restrito. Por favor, faça login para continuar.');
    window.location.replace('../usuario/login.html');
} else {
    // Chamada inicial
    init();
}

function init() {
    modal.style.opacity = 1;
    findAllTasks((data) => {
        db = data;
        listTarefas();
    });
}

function listTarefas() {
    const tarefasPeloUserId = db.filter((task) => {
        return task.usuarioId == userId;
    });

    const grupoHorasTarefas = ordenaEAgrupaTarefas(tarefasPeloUserId);
    let tableTarefas = document.getElementById('tableTarefas');
    
    while (tableTarefas.rows.length > 1) {
        tableTarefas.deleteRow(1);
    }

    grupoHorasTarefas.forEach((tarefasDoMesmoHorario) => {
        const linha = tableTarefas.insertRow();
        linha.insertCell().innerHTML = tarefasDoMesmoHorario[0].horario;

        for (const dia of diasDaSemana) {
            const tarefaDoDia = tarefasDoMesmoHorario.find(t => t.diaDaSemana === dia);
            const celula = linha.insertCell();
            celula.className = 'tarefas';
            if (tarefaDoDia) {
                celula.innerHTML = `
                    <button class="excluir" data-id="${tarefaDoDia.id}">
                         <i class="fa-solid fa-trash" style="color: #d80032;"></i>
                    </button>
                    <p>${tarefaDoDia.titulo}</p>`;
            } else {
                celula.innerHTML = `<p></p>`;
            }
        }
    });

    const linhaButtons = tableTarefas.insertRow();
    linhaButtons.insertCell(); // Célula vazia para a coluna de horário

    for (let i = 0; i < 7; i++) {
        const adicionarCell = linhaButtons.insertCell();
        adicionarCell.className = 'adicionar';
        // CORREÇÃO do ícone
        adicionarCell.innerHTML = `
            <button onclick="window.location.href = './criar-tarefa.html'">
                <i class="fa-solid fa-plus-circle fa-2x" style="color: var(--orange-color); cursor: pointer;"></i>
            </button>
        `;
    }
    addDeleteListeners();
}

function ordenaEAgrupaTarefas(tarefas) {
    if (!tarefas || tarefas.length === 0) return [];
    
    const tarefasAgrupadas = tarefas.reduce((acc, tarefa) => {
        if (!acc[tarefa.horario]) {
            acc[tarefa.horario] = [];
        }
        acc[tarefa.horario].push(tarefa);
        return acc;
    }, {});
    
    const arrayDeGrupos = Object.values(tarefasAgrupadas);
    arrayDeGrupos.sort((a, b) => a[0].horario.localeCompare(b[0].horario));
    
    return arrayDeGrupos;
}

function addDeleteListeners() {
    document.querySelectorAll('.excluir').forEach(button => {
        button.addEventListener('click', function () {
            const taskId = this.dataset.id;
            if (confirm('Tem certeza que deseja excluir esta tarefa?')) {
                deleteTask(taskId, () => {
                    db = db.filter(task => task.id !== taskId);
                    listTarefas();
                    displayMessage('Tarefa removida com sucesso.', 'success');
                });
            }
        });
    });
}