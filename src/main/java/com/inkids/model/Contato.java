package com.example.rotinainteligente.model;

import java.time.LocalDateTime;

public class Contato {
    private int id;
    private String nome;
    private String email;
    private String telefone;
    private String assunto;
    private String mensagem;
    private LocalDateTime createdAt;

    // Constructors
    public Contato() {}

    public Contato(String nome, String email, String telefone, String assunto, String mensagem) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.assunto = assunto;
        this.mensagem = mensagem;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Contato{" +
               "id=" + id +
               ", nome='" + nome + '\'' +
               ", email='" + email + '\'' +
               ", telefone='" + telefone + '\'' +
               ", assunto='" + assunto + '\'' +
               ", mensagem='" + mensagem + '\'' +
               ", createdAt=" + createdAt +
               '}';
    }
}