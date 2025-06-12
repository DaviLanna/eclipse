package com.inkids.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

public class PostagemDTO {
    private String id;
    private String title;
    private String content;
    private String author;
    @JsonProperty("imageLink")
    private String imageUrl;
    private Date createdAt;
    private Date updatedAt;
    public PostagemDTO() {}
    // Getters e Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}