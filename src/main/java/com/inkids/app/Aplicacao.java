package com.inkids.app;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.inkids.controller.ContatoController;
import com.inkids.controller.PostagemController;
import com.inkids.controller.TarefaController;
import com.inkids.controller.UsuarioController;
import com.inkids.service.ContatoService;
import com.inkids.service.PostagemService;
import com.inkids.service.TarefaService;
import com.inkids.service.UsuarioService;

import java.io.FileInputStream;
import java.io.IOException;

import static spark.Spark.*;

/**
 * Classe principal que inicia a aplicação backend com Firebase.
 */
public class Aplicacao {

    public static void main(String[] args) {
        
        try {
            // ATENÇÃO: Coloque o arquivo serviceAccountKey.json que você baixou do Firebase
            // na raiz do seu projeto, ou atualize este caminho.
            FileInputStream serviceAccount = new FileInputStream("./serviceAccountKey.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

            FirebaseApp.initializeApp(options);
            
            System.out.println("Firebase Admin SDK inicializado com sucesso!");

        } catch (IOException e) {
            System.err.println("ERRO FATAL: Não foi possível inicializar o Firebase.");
            System.err.println("Verifique se o arquivo 'serviceAccountKey.json' está no local correto.");
            e.printStackTrace();
            return; // Encerra a aplicação
        }

        port(8081);
        staticFiles.location("/static");
        enableCORS();

        // Instancia as camadas de serviço e controllers
        UsuarioService usuarioService = new UsuarioService();
        PostagemService postagemService = new PostagemService();
        TarefaService tarefaService = new TarefaService();
        ContatoService contatoService = new ContatoService();

        new UsuarioController(usuarioService);
        new PostagemController(postagemService);
        new TarefaController(tarefaService);
        new ContatoController(contatoService);

        System.out.println("\nServidor Java (Spark) iniciado com sucesso!");
        System.out.println("Ouvindo na porta: http://localhost:8081");
    }

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