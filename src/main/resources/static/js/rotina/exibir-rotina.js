let userId = localStorage.getItem('userId');
let modal = document.getElementById('modal');
const diasDaSemana = ['Domingo', 'Segunda-feira', 'Terça-feira', 'Quarta-feira', 'Quinta-feira', 'Sexta-feira', 'Sábado'];

if (!userId) {
    alert('Acesso restrito. Por favor, faça login para continuar.');
    window.location.replace('../usuario/login.html');
} else {
    modal.style.opacity = 1;

    var db = [];

    findAllTasks((data) => {
        db = data;
        listTarefas();
    });

    function listTarefas() {
        let filtroTarefa = document.getElementById('filtro-tarefa').value.toLowerCase();
        const tableTarefas = document.querySelector('#tableTarefas > tbody');

        const tarefasFiltradas = db.filter((task) => {
            return (
                task.usuarioId == userId &&
                task.titulo.toLowerCase().includes(filtroTarefa)
            );
        });
        
        const grupoHorasTarefas = ordenaEAgrupaTarefas(tarefasFiltradas);

        while (tableTarefas.rows.length > 1) {
            tableTarefas.deleteRow(1);
        }

        grupoHorasTarefas.forEach((tarefasDoMesmoHorario) => {
            const linha = tableTarefas.insertRow();
            // Coluna do Horário
            linha.insertCell().innerHTML = tarefasDoMesmoHorario[0].horario;

            // Colunas dos dias da semana
            for (const dia of diasDaSemana) {
                const tarefaDoDia = tarefasDoMesmoHorario.find(t => t.diaDaSemana === dia);
                const celula = linha.insertCell();
                celula.className = 'tarefas'; // Adiciona classe para estilização se necessário
                if (tarefaDoDia) {
                    celula.innerHTML = `<p>${tarefaDoDia.titulo}</p>`;
                } else {
                    celula.innerHTML = `<p></p>`; // Célula vazia
                }
            }
            // Adiciona a célula vazia para a coluna "Editar"
            linha.insertCell(); 
        });
    }

    function ordenaEAgrupaTarefas(tarefas) {
        if (!tarefas || tarefas.length === 0) return [];

        // Agrupa tarefas por horário
        const tarefasAgrupadas = tarefas.reduce((acc, tarefa) => {
            if (!acc[tarefa.horario]) {
                acc[tarefa.horario] = [];
            }
            acc[tarefa.horario].push(tarefa);
            return acc;
        }, {});

        // Converte o objeto de grupos em um array e ordena pelo horário
        const arrayDeGrupos = Object.values(tarefasAgrupadas);
        arrayDeGrupos.sort((a, b) => a[0].horario.localeCompare(b[0].horario));
        
        return arrayDeGrupos;
    }

    document.getElementById('search-form').addEventListener('submit', function (event) {
        event.preventDefault();
        listTarefas();
    });
}