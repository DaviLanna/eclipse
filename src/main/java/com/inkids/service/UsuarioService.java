package com.inkids.service;

import com.inkids.dao.UsuarioDAO;
import com.inkids.model.Usuario;
import java.util.ArrayList;
import java.util.List;

public class UsuarioService {
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    public Usuario criarUsuario(Usuario usuario) {
        try {
            if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty() || usuarioDAO.getByEmail(usuario.getEmail()) != null) {
                return null;
            }
            return usuarioDAO.insert(usuario);
        } catch (Exception e) { e.printStackTrace(); return null; }
    }

    public Usuario buscarUsuarioPorId(String id) {
        try { return usuarioDAO.get(id); }
        catch (Exception e) { e.printStackTrace(); return null; }
    }

    public Usuario buscarUsuarioPorEmail(String email) {
        try { return usuarioDAO.getByEmail(email); }
        catch (Exception e) { e.printStackTrace(); return null; }
    }

    public List<Usuario> listarTodosUsuarios() {
        try { return usuarioDAO.getAll(); }
        catch (Exception e) { e.printStackTrace(); return new ArrayList<>(); }
    }

    public boolean atualizarUsuario(Usuario usuario) {
        try { usuarioDAO.update(usuario); return true; }
        catch (Exception e) { e.printStackTrace(); return false; }
    }

    public boolean deletarUsuario(String id) {
        return usuarioDAO.delete(id);
    }
}