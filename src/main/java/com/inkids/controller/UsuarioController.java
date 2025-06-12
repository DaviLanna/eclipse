package com.inkids.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inkids.model.Usuario;
import com.inkids.service.UsuarioService;
import static spark.Spark.*;

/**
 * Controller para a entidade Usuario com Firebase.
 */
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final ObjectMapper objectMapper;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
        this.objectMapper = new ObjectMapper();
        setupRoutes();
    }
    
    private void setupRoutes() {
        post("/api/usuarios", (request, response) -> {
            response.type("application/json");
            try {
                Usuario usuario = objectMapper.readValue(request.body(), Usuario.class);
                Usuario novoUsuario = usuarioService.criarUsuario(usuario);
                if (novoUsuario != null) {
                    response.status(201);
                    return objectMapper.writeValueAsString(novoUsuario);
                } else {
                    response.status(400);
                    return "{\"error\":\"Não foi possível criar o usuário. Verifique os dados ou o e-mail pode já existir.\"}";
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.status(500);
                return "{\"error\":\"Ocorreu um erro no servidor: " + e.getMessage() + "\"}";
            }
        });

        get("/api/usuarios/:id", (request, response) -> {
            response.type("application/json");
            String id = request.params(":id");
            Usuario usuario = usuarioService.buscarUsuarioPorId(id);
            if (usuario != null) {
                return objectMapper.writeValueAsString(usuario);
            }
            response.status(404);
            return "{\"error\":\"Usuário não encontrado.\"}";
        });

        get("/api/usuarios", (request, response) -> {
            response.type("application/json");
            return objectMapper.writeValueAsString(usuarioService.listarTodosUsuarios());
        });

        put("/api/usuarios/:id", (request, response) -> {
            response.type("application/json");
            String id = request.params(":id");
            Usuario usuario = objectMapper.readValue(request.body(), Usuario.class);
            usuario.setId(id);
            if (usuarioService.atualizarUsuario(usuario)) {
                return objectMapper.writeValueAsString(usuario);
            }
            response.status(404);
            return "{\"error\":\"Não foi possível atualizar. Usuário não encontrado.\"}";
        });

        delete("/api/usuarios/:id", (request, response) -> {
            response.type("application/json");
            String id = request.params(":id");
            if (usuarioService.deletarUsuario(id)) {
                return "{\"message\":\"Usuário deletado com sucesso.\"}";
            }
            response.status(404);
            return "{\"error\":\"Não foi possível deletar. Usuário não encontrado.\"}";
        });
    }
}