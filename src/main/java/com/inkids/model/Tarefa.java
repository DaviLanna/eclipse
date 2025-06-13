package com.inkids.model;

import com.google.cloud.firestore.annotation.ServerTimestamp;
import java.util.Date;

public class Tarefa {
    private String id;
    private String titulo;
    private String descricao;
    private String status;
    private String prioridade;
    private String usuarioId;

    // CAMPOS PARA ROTINA
    private String diaDaSemana;
    private String horario;

    @ServerTimestamp private Date dataCriacao;
    private Date dataConclusao;
    public Tarefa() {}
    
    // Getters e Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getPrioridade() { return prioridade; }
    public void setPrioridade(String prioridade) { this.prioridade = prioridade; }
    public String getUsuarioId() { return usuarioId; }
    public void setUsuarioId(String usuarioId) { this.usuarioId = usuarioId; }
    public Date getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(Date dataCriacao) { this.dataCriacao = dataCriacao; }
    public Date getDataConclusao() { return dataConclusao; }
    public void setDataConclusao(Date dataConclusao) { this.dataConclusao = dataConclusao; }

    // GETTERS E SETTERS PARA ROTINA
    public String getDiaDaSemana() { return diaDaSemana; }
    public void setDiaDaSemana(String diaDaSemana) { this.diaDaSemana = diaDaSemana; }
    public String getHorario() { return horario; }
    public void setHorario(String horario) { this.horario = horario; }
}