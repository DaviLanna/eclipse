package com.inkids.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inkids.model.Contato;
import com.inkids.service.ContatoService;
import static spark.Spark.*;

public class ContatoController {
    private final ContatoService contatoService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ContatoController(ContatoService contatoService) {
        this.contatoService = contatoService;
        setupRoutes();
    }

    private void setupRoutes() {
        post("/api/contatos", (request, response) -> {
            response.type("application/json");
            Contato contato = objectMapper.readValue(request.body(), Contato.class);
            Contato novaMensagem = contatoService.salvarMensagem(contato);
            if (novaMensagem != null) {
                response.status(201);
                return "{\"message\":\"Mensagem recebida com sucesso!\"}";
            }
            response.status(400);
            return "{\"error\":\"Não foi possível enviar a mensagem.\"}";
        });

        get("/api/contatos", (request, response) -> {
            response.type("application/json");
            return objectMapper.writeValueAsString(contatoService.listarTodasMensagens());
        });

        delete("/api/contatos/:id", (request, response) -> {
            response.type("application/json");
            String id = request.params(":id");
            if (contatoService.deletarMensagem(id)) {
                return "{\"message\":\"Mensagem deletada com sucesso.\"}";
            }
            response.status(404);
            return "{\"error\":\"Não foi possível deletar. Mensagem não encontrada.\"}";
        });
    }
}