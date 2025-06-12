package com.inkids.model;

import com.google.cloud.firestore.annotation.ServerTimestamp;
import java.util.Date;

/**
 * Modelo de Usuário para o Firestore.
 */
public class Usuario {
    private String id; // MUDANÇA: de int para String
    private String nome;
    private String email;
    private String senha;
    private String telefone;
    private String tipoUsuario;

    @ServerTimestamp // Anotação do Firestore para gerenciar a data automaticamente
    private Date createdAt;
    
    @ServerTimestamp
    private Date updatedAt;

    // Construtor vazio é necessário para o Firestore
    public Usuario() {}

    // Getters e Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public String getTipoUsuario() { return tipoUsuario; }
    public void setTipoUsuario(String tipoUsuario) { this.tipoUsuario = tipoUsuario; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}