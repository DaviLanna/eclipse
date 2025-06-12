package com.inkids.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

/**
 * DTO (Data Transfer Object) para a entidade Postagem.
 * Usado para formatar os dados que serão enviados como JSON para o frontend.
 */
public class PostagemDTO {

    private int id;
    private String title;       // Campo "title" como o frontend espera
    private String content;
    private String author;      // Campo para o nome do autor
    
    @JsonProperty("imageLink")  // Renomeia o campo no JSON final
    private String imageUrl;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // --- Construtores, Getters e Setters ---

    public PostagemDTO() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}