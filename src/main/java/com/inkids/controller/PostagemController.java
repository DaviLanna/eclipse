package com.inkids.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inkids.model.Postagem;
import com.inkids.service.PostagemService;
import static spark.Spark.*;

public class PostagemController {
    private final PostagemService postagemService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public PostagemController(PostagemService postagemService) {
        this.postagemService = postagemService;
        setupRoutes();
    }

    private void setupRoutes() {
        post("/api/postagens", (request, response) -> {
            response.type("application/json");
            Postagem postagem = objectMapper.readValue(request.body(), Postagem.class);
            Postagem novaPostagem = postagemService.criarPostagem(postagem);
            if (novaPostagem != null) {
                response.status(201);
                return objectMapper.writeValueAsString(novaPostagem);
            }
            response.status(400);
            return "{\"error\":\"Não foi possível criar a postagem.\"}";
        });

        get("/api/postagens/:id", (request, response) -> {
            response.type("application/json");
            String id = request.params(":id");
            Object postagemDto = postagemService.buscarPostagemPorIdDTO(id);
            if (postagemDto != null) {
                return objectMapper.writeValueAsString(postagemDto);
            }
            response.status(404);
            return "{\"error\":\"Postagem não encontrada.\"}";
        });

        get("/api/postagens", (request, response) -> {
            response.type("application/json");
            String usuarioIdParam = request.queryParams("usuarioId");
            if (usuarioIdParam != null && !usuarioIdParam.isEmpty()) {
                return objectMapper.writeValueAsString(postagemService.listarPostagensPorUsuario(usuarioIdParam));
            } else {
                return objectMapper.writeValueAsString(postagemService.listarTodasPostagensDTO());
            }
        });

        put("/api/postagens/:id", (request, response) -> {
            response.type("application/json");
            String id = request.params(":id");
            Postagem postagem = objectMapper.readValue(request.body(), Postagem.class);
            postagem.setId(id);
            if (postagemService.atualizarPostagem(postagem)) {
                return objectMapper.writeValueAsString(postagem);
            }
            response.status(404);
            return "{\"error\":\"Não foi possível atualizar. Postagem não encontrada.\"}";
        });

        delete("/api/postagens/:id", (request, response) -> {
            response.type("application/json");
            String id = request.params(":id");
            if (postagemService.deletarPostagem(id)) {
                return "{\"message\":\"Postagem deletada com sucesso.\"}";
            }
            response.status(404);
            return "{\"error\":\"Não foi possível deletar. Postagem não encontrada.\"}";
        });
    }
}