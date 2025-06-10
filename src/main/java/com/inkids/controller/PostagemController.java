package com.example.rotinainteligente.controller;

import com.example.rotinainteligente.model.Postagem;
import com.example.rotinainteligente.service.PostagemService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import static spark.Spark.*;
import java.util.List;

public class PostagemController {
    private PostagemService postagemService;
    private ObjectMapper objectMapper;

    public PostagemController(PostagemService postagemService) {
        this.postagemService = postagemService;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule()); // For LocalDateTime serialization

        setupRoutes();
    }

    private void setupRoutes() {
        // Create Postagem
        post("/api/postagens", (request, response) -> {
            response.type("application/json");
            Postagem postagem = objectMapper.readValue(request.body(), Postagem.class);
            Postagem novaPostagem = postagemService.criarPostagem(postagem);
            if (novaPostagem != null) {
                response.status(201); // Created
                return objectMapper.writeValueAsString(novaPostagem);
            } else {
                response.status(500); // Internal Server Error
                return "{\"error\":\"Erro ao criar postagem\"}";
            }
        });

        // Get Postagem by ID
        get("/api/postagens/:id", (request, response) -> {
            response.type("application/json");
            try {
                int id = Integer.parseInt(request.params(":id"));
                Postagem postagem = postagemService.buscarPostagemPorId(id);
                if (postagem != null) {
                    return objectMapper.writeValueAsString(postagem);
                } else {
                    response.status(404); // Not Found
                    return "{\"error\":\"Postagem não encontrada\"}";
                }
            } catch (NumberFormatException e) {
                response.status(400); // Bad Request
                return "{\"error\":\"ID de postagem inválido\"}";
            }
        });

        // Get All Postagens
        get("/api/postagens", (request, response) -> {
            response.type("application/json");
            List<Postagem> postagens = postagemService.listarTodasPostagens();
            return objectMapper.writeValueAsString(postagens);
        });
        
        // Get Postagens by User ID
        get("/api/postagens/usuario/:usuarioId", (request, response) -> {
            response.type("application/json");
             try {
                int usuarioId = Integer.parseInt(request.params(":usuarioId"));
                List<Postagem> postagens = postagemService.listarPostagensPorUsuario(usuarioId);
                return objectMapper.writeValueAsString(postagens);
            } catch (NumberFormatException e) {
                response.status(400); // Bad Request
                return "{\"error\":\"ID de usuário inválido\"}";
            }
        });


        // Update Postagem
        put("/api/postagens/:id", (request, response) -> {
            response.type("application/json");
            try {
                int id = Integer.parseInt(request.params(":id"));
                Postagem postagem = objectMapper.readValue(request.body(), Postagem.class);
                postagem.setId(id); // Ensure ID from path is used
                boolean success = postagemService.atualizarPostagem(postagem);
                if (success) {
                    return objectMapper.writeValueAsString(postagemService.buscarPostagemPorId(id)); // Return updated
                } else {
                    response.status(404); // Or 500 if update failed for other reasons
                    return "{\"error\":\"Erro ao atualizar postagem ou postagem não encontrada\"}";
                }
            } catch (NumberFormatException e) {
                response.status(400);
                return "{\"error\":\"ID de postagem inválido\"}";
            }
        });

        // Delete Postagem
        delete("/api/postagens/:id", (request, response) -> {
            response.type("application/json");
            try {
                int id = Integer.parseInt(request.params(":id"));
                boolean success = postagemService.deletarPostagem(id);
                if (success) {
                    return "{\"message\":\"Postagem deletada com sucesso\"}";
                } else {
                    response.status(404);
                    return "{\"error\":\"Erro ao deletar postagem ou postagem não encontrada\"}";
                }
            } catch (NumberFormatException e) {
                response.status(400);
                return "{\"error\":\"ID de postagem inválido\"}";
            }
        });
    }
}