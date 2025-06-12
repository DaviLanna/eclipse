package com.inkids.app;

import com.inkids.controller.*;
import com.inkids.dao.DAO;
import com.inkids.model.Usuario;
import com.inkids.service.*;

import java.time.LocalDate;

import static spark.Spark.*;

/**
 * Classe principal que inicia a aplicação backend.
 */
public class Aplicacao {

    public static void main(String[] args) {
        // 1. Inicializa o esquema do banco de dados na memória.
        DAO.initializeDatabase();

        // 2. Configura a porta do servidor web.
        port(8081); // Mantendo a porta 8081 que definimos anteriormente

        // --- LINHA ADICIONADA AQUI ---
        // 3. Configura o servidor para servir os arquivos estáticos (front-end)
        // O Spark vai procurar os arquivos na pasta 'src/main/resources/static'
        staticFiles.location("/static");

        // 4. Configura o CORS (Cross-Origin Resource Sharing).
        enableCORS();

        // 5. Instancia todas as camadas de serviço.
        UsuarioService usuarioService = new UsuarioService();
        PostagemService postagemService = new PostagemService();
        TarefaService tarefaService = new TarefaService();
        ContatoService contatoService = new ContatoService();

        // 6. Instancia todos os controllers, passando os serviços correspondentes.
        new UsuarioController(usuarioService);
        new PostagemController(postagemService);
        new TarefaController(tarefaService);
        new ContatoController(contatoService);

        System.out.println("\nServidor Java (Spark) iniciado com sucesso!");
        System.out.println("Ouvindo na porta: http://localhost:8081");
        System.out.println("Front-end e API estão disponíveis no mesmo endereço.");
    }

    /**
     * Habilita o CORS para permitir requisições de origens diferentes.
     */
    private static void enableCORS() {
        options("/*", (request, response) -> {
            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }
            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }
            return "OK";
        });

        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            response.header("Access-Control-Allow-Headers", "Content-Type, Authorization, X-Requested-With");
            response.type("application/json");
        });
    }
}