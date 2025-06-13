package com.inkids.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import java.util.Map;

public class CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryService() {
        String cloudName = System.getenv("CLOUDINARY_CLOUD_NAME");
        String apiKey = System.getenv("CLOUDINARY_API_KEY");
        String apiSecret = System.getenv("CLOUDINARY_API_SECRET");

        if (cloudName == null || apiKey == null || apiSecret == null) {
            System.err.println("ERRO: Variáveis de ambiente do Cloudinary não configuradas!");
            this.cloudinary = null;
        } else {
            this.cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret,
                "secure", true
            ));
        }
    }

    /**
     * Faz o upload de uma imagem em formato Base64 para o Cloudinary.
     * @param base64ImageDataUri A string da imagem em Base64, incluindo o prefixo "data:image/png;base64,".
     * @return A URL segura da imagem que foi salva.
     */
    @SuppressWarnings("rawtypes")
    public String uploadImage(String base64ImageDataUri) throws Exception {
        if (cloudinary == null) {
            throw new IllegalStateException("Cloudinary não foi inicializado. Verifique as variáveis de ambiente.");
        }

        Map uploadResult = cloudinary.uploader().upload(base64ImageDataUri, ObjectUtils.asMap(
            "folder", "postagens"
        ));

        String publicUrl = (String) uploadResult.get("secure_url");
        System.out.println("Upload para Cloudinary bem-sucedido: " + publicUrl);
        return publicUrl;
    }
}