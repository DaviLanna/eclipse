package com.inkids.service;

import com.inkids.dao.TarefaDAO;
import com.inkids.model.Tarefa;
import java.util.ArrayList;
import java.util.List;

public class TarefaService {
    private final TarefaDAO tarefaDAO = new TarefaDAO();

    public Tarefa criarTarefa(Tarefa tarefa) {
        try {
            if (tarefa == null || tarefa.getTitulo() == null || tarefa.getTitulo().trim().isEmpty()) {
                return null;
            }
            return tarefaDAO.save(tarefa);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Tarefa buscarTarefaPorId(String id) {
        try {
            return tarefaDAO.get(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Tarefa> listarTodasTarefas() {
        try {
            return tarefaDAO.getAll();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public boolean atualizarTarefa(Tarefa tarefa) {
        try {
            tarefaDAO.save(tarefa);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletarTarefa(String id) {
        return tarefaDAO.delete(id);
    }
}