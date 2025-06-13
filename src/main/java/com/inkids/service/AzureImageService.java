package com.inkids.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AzureImageService {

    private static final String AZURE_OPENAI_KEY = System.getenv("AZURE_OPENAI_KEY");
    private static final String AZURE_OPENAI_ENDPOINT = System.getenv("AZURE_OPENAI_ENDPOINT");
    private static final String DEPLOYMENT_NAME = System.getenv("AZURE_OPENAI_DEPLOYMENT_NAME");
    private static final String API_VERSION = "2024-04-01-preview";

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final CloudinaryService storageService = new CloudinaryService();

    public List<String> generateImageUrls(String prompt, int sampleCount) {
        System.out.println("Solicitando geração de " + sampleCount + " imagens via Azure OpenAI...");
        
        if (AZURE_OPENAI_KEY == null || AZURE_OPENAI_ENDPOINT == null || DEPLOYMENT_NAME == null) {
            System.err.println("ERRO: Variáveis de ambiente da Azure OpenAI não configuradas.");
            return createFallbackUrls(prompt, sampleCount);
        }

        try {
            List<CompletableFuture<String>> futures = IntStream.range(0, sampleCount)
                .mapToObj(i -> generateAndUploadSingleImage(prompt))
                .collect(Collectors.toList());

            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

            List<String> finalUrls = futures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
            
            System.out.println(finalUrls.size() + " URLs de imagem obtidas com sucesso do Cloudinary.");
            return finalUrls;

        } catch (Exception e) {
            System.err.println("Erro ao gerar ou fazer upload das imagens: " + e.getMessage());
            e.printStackTrace();
            return createFallbackUrls(prompt, sampleCount);
        }
    }

    private CompletableFuture<String> generateAndUploadSingleImage(String prompt) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                String requestUrl = String.format("%s/openai/deployments/%s/images/generations?api-version=%s", AZURE_OPENAI_ENDPOINT, DEPLOYMENT_NAME, API_VERSION);
                String jsonPayload = String.format("{\"prompt\":\"%s\",\"n\":1,\"size\":\"1024x1024\", \"response_format\":\"b64_json\"}", prompt);

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(requestUrl))
                        .header("Content-Type", "application/json")
                        .header("api-key", AZURE_OPENAI_KEY)
                        .timeout(Duration.ofSeconds(90))
                        .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                        .build();

                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() != 200) {
                    throw new RuntimeException("Falha ao gerar imagem. Status: " + response.statusCode() + " Body: " + response.body());
                }

                JsonNode responseNode = objectMapper.readTree(response.body());
                String base64Image = responseNode.path("data").get(0).get("b64_json").asText();
                String dataUri = "data:image/png;base64," + base64Image;

                return storageService.uploadImage(dataUri);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private List<String> createFallbackUrls(String prompt, int count) {
        System.out.println("Usando imagens de placeholder como fallback.");
        List<String> fallbackUrls = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            fallbackUrls.add("https://via.placeholder.com/600x400.png?text=" + prompt.replace(" ", "+") + "+(fallback+" + (i + 1) + ")");
        }
        return fallbackUrls;
    }
}