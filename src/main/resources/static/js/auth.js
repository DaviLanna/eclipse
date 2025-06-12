// Este script será executado em todas as páginas para gerenciar a barra de navegação.

// Função para fazer logout
function handleLogout() {
    localStorage.removeItem('userId');
    // Mostra um alerta e redireciona para a home
    alert('Você foi desconectado com sucesso.');
    window.location.href = '/index.html'; 
}

// Executa quando o conteúdo da página é totalmente carregado
document.addEventListener('DOMContentLoaded', function() {
    const userId = localStorage.getItem('userId');
    
    // Seleciona todos os elementos que devem aparecer para usuários logados/deslogados
    const loggedInElements = document.querySelectorAll('.logged-in');
    const loggedOutElements = document.querySelectorAll('.logged-out');

    if (userId) {
        // Usuário está logado
        loggedInElements.forEach(el => el.style.display = 'inline'); // Mostra links de logado
        loggedOutElements.forEach(el => el.style.display = 'none');  // Esconde links de deslogado
    } else {
        // Usuário não está logado
        loggedInElements.forEach(el => el.style.display = 'none');   // Esconde links de logado
        loggedOutElements.forEach(el => el.style.display = 'inline'); // Mostra links de deslogado
    }
    
    // Adiciona o evento de clique ao botão de logout
    const logoutButton = document.getElementById('nav-logout');
    if (logoutButton) {
        logoutButton.addEventListener('click', function(event) {
            event.preventDefault(); // Impede a navegação imediata pelo href
            handleLogout();
        });
    }
});