package com.inkids.service;

import com.inkids.dao.ContatoDAO;
import com.inkids.model.Contato;
import java.util.ArrayList;
import java.util.List;

public class ContatoService {
    private final ContatoDAO contatoDAO = new ContatoDAO();

    public Contato salvarMensagem(Contato contato) {
        try {
            if (contato == null || contato.getEmail() == null || contato.getMensagem() == null) {
                return null;
            }
            return contatoDAO.save(contato);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Contato> listarTodasMensagens() {
        try {
            return contatoDAO.getAll();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    public boolean deletarMensagem(String id) {
        return contatoDAO.delete(id);
    }
}