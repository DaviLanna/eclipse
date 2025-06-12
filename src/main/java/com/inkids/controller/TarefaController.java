package com.inkids.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inkids.model.Tarefa;
import com.inkids.service.TarefaService;
import static spark.Spark.*;

public class TarefaController {
    private final TarefaService tarefaService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public TarefaController(TarefaService tarefaService) {
        this.tarefaService = tarefaService;
        setupRoutes();
    }

    private void setupRoutes() {
        post("/api/tarefas", (request, response) -> {
            response.type("application/json");
            Tarefa tarefa = objectMapper.readValue(request.body(), Tarefa.class);
            Tarefa novaTarefa = tarefaService.criarTarefa(tarefa);
            if (novaTarefa != null) {
                response.status(201);
                return objectMapper.writeValueAsString(novaTarefa);
            }
            response.status(400);
            return "{\"error\":\"Não foi possível criar a tarefa.\"}";
        });

        get("/api/tarefas/:id", (request, response) -> {
            response.type("application/json");
            String id = request.params(":id");
            Tarefa tarefa = tarefaService.buscarTarefaPorId(id);
            if (tarefa != null) {
                return objectMapper.writeValueAsString(tarefa);
            }
            response.status(404);
            return "{\"error\":\"Tarefa não encontrada.\"}";
        });

        get("/api/tarefas", (request, response) -> {
            response.type("application/json");
            return objectMapper.writeValueAsString(tarefaService.listarTodasTarefas());
        });

        put("/api/tarefas/:id", (request, response) -> {
            response.type("application/json");
            String id = request.params(":id");
            Tarefa tarefa = objectMapper.readValue(request.body(), Tarefa.class);
            tarefa.setId(id);
            if (tarefaService.atualizarTarefa(tarefa)) {
                return objectMapper.writeValueAsString(tarefa);
            }
            response.status(404);
            return "{\"error\":\"Não foi possível atualizar. Tarefa não encontrada.\"}";
        });

        delete("/api/tarefas/:id", (request, response) -> {
            response.type("application/json");
            String id = request.params(":id");
            if (tarefaService.deletarTarefa(id)) {
                return "{\"message\":\"Tarefa deletada com sucesso.\"}";
            }
            response.status(404);
            return "{\"error\":\"Não foi possível deletar. Tarefa não encontrada.\"}";
        });
    }
}