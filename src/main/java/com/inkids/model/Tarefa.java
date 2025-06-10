package com.example.rotinainteligente.model;

import java.time.LocalDateTime;

public class Tarefa {
    private int id;
    private String titulo;
    private String descricao;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataConclusao;
    private String status; // PENDENTE, EM_ANDAMENTO, CONCLUIDA
    private String prioridade; // BAIXA, MEDIA, ALTA
    private int usuarioId;

    // Constructors
    public Tarefa() {}

    public Tarefa(String titulo, String descricao, String status, String prioridade, int usuarioId) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.status = status;
        this.prioridade = prioridade;
        this.usuarioId = usuarioId;
    }


    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataConclusao() {
        return dataConclusao;
    }

    public void setDataConclusao(LocalDateTime dataConclusao) {
        this.dataConclusao = dataConclusao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    @Override
    public String toString() {
        return "Tarefa{" +
               "id=" + id +
               ", titulo='" + titulo + '\'' +
               ", descricao='" + descricao + '\'' +
               ", dataCriacao=" + dataCriacao +
               ", dataConclusao=" + dataConclusao +
               ", status='" + status + '\'' +
               ", prioridade='" + prioridade + '\'' +
               ", usuarioId=" + usuarioId +
               '}';
    }
}