package com.example.rotinainteligente.app;

import com.example.rotinainteligente.controller.*;
import com.example.rotinainteligente.dao.DAO;
import com.example.rotinainteligente.service.*;

import static spark.Spark.*;

public class Aplicacao {

    public static void main(String[] args) {
        // Initialize Database Schema
        DAO.initializeDatabase();

        // Configure Spark port (default is 4567)
        port(8080); // Example port, ensure it doesn't conflict

        // Enable CORS for all routes (basic example)
        // For production, configure more specifically
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
            response.header("Access-Control-Allow-Origin", "*"); // Allow all origins
            response.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            response.header("Access-Control-Allow-Headers", "Content-Type, Authorization, X-Requested-With,userId");
            response.type("application/json");
        });
        
        // Initialize services
        UsuarioService usuarioService = new UsuarioService();
        PostagemService postagemService = new PostagemService();
        TarefaService tarefaService = new TarefaService();
        ContatoService contatoService = new ContatoService();

        // Initialize controllers
        new UsuarioController(usuarioService);
        new PostagemController(postagemService);
        new TarefaController(tarefaService);
        new ContatoController(contatoService);
        // The image generation is part of PostagemService, no separate controller for it.

        System.out.println("Servidor Java iniciado em http://localhost:8080");

        // Demonstrating CRUD calls (as per one of the questions, this is more for illustration
        // or if the app had a console component rather than being purely a web backend)
        // In a web backend, these operations are triggered by HTTP requests via controllers.
        demonstrateCrudOperations(usuarioService, postagemService, tarefaService, contatoService);
    }

    /**
     * This method demonstrates CRUD operations as requested by the prompt.
     * In a real web application, these would be invoked via API calls handled by controllers.
     */
    private static void demonstrateCrudOperations(UsuarioService usuarioService, PostagemService postagemService,
                                                 TarefaService tarefaService, ContatoService contatoService) {
        System.out.println("\n--- Demonstração de Operações CRUD ---");

        // --- Usuário ---
        System.out.println("\n** CRUD Usuário **");
        // Insert
        // Usuario novoUsuario = new Usuario("Teste User", "teste@example.com", "senha123", LocalDate.now().minusYears(25), "Outro", "123456789", "USER");
        // novoUsuario = usuarioService.criarUsuario(novoUsuario);
        // System.out.println("Usuário Inserido: " + (novoUsuario != null ? novoUsuario.getId() : "Falha"));

        // Get
        // if (novoUsuario != null) {
        //     Usuario usuarioBuscado = usuarioService.buscarUsuarioPorId(novoUsuario.getId());
        //     System.out.println("Usuário Buscado: " + usuarioBuscado);
        // }

        // Listar
        // System.out.println("Listar Usuários: " + usuarioService.listarTodosUsuarios());

        // Update
        // if (novoUsuario != null) {
        //     novoUsuario.setNome("Teste User Atualizado");
        //     boolean atualizado = usuarioService.atualizarUsuario(novoUsuario);
        //     System.out.println("Usuário Atualizado: " + atualizado);
        //     System.out.println("Usuário Após Update: " + usuarioService.buscarUsuarioPorId(novoUsuario.getId()));
        // }

        // Remove
        // if (novoUsuario != null) {
        //     boolean removido = usuarioService.deletarUsuario(novoUsuario.getId());
        //     System.out.println("Usuário Removido: " + removido);
        //     System.out.println("Usuário Após Remoção: " + usuarioService.buscarUsuarioPorId(novoUsuario.getId()));
        // }

        // --- Postagem (similar demonstration would go here) ---
        // ...

        // --- Tarefa (similar demonstration would go here) ---
        // ...

        // --- Contato (similar demonstration would go here) ---
        // ...

        System.out.println("\n--- Fim da Demonstração CRUD ---");
        // Note: For a real application, these demonstrations might interfere with web API testing
        // if they modify the same in-memory database. Consider running them conditionally.
    }
}