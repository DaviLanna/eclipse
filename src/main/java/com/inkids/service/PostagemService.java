package com.example.rotinainteligente.service;

import com.example.rotinainteligente.dao.PostagemDAO;
import com.example.rotinainteligente.model.Postagem;
import java.util.List;

public class PostagemService {
    private PostagemDAO postagemDAO;
    private GeminiImageService geminiImageService;

    public PostagemService() {
        this.postagemDAO = new PostagemDAO();
        this.geminiImageService = new GeminiImageService();
    }

    public Postagem criarPostagem(Postagem postagem) {
        // Generate image URL based on title
        if (postagem.getTitulo() != null && !postagem.getTitulo().isEmpty()) {
            String imageUrl = geminiImageService.generateImageUrl(postagem.getTitulo());
            postagem.setImagemUrl(imageUrl);
        }
        int id = postagemDAO.insert(postagem);
        if (id != -1) {
            postagem.setId(id);
            return postagem;
        }
        return null;
    }

    public Postagem buscarPostagemPorId(int id) {
        return postagemDAO.get(id);
    }

    public List<Postagem> listarTodasPostagens() {
        return postagemDAO.getAll();
    }
    
    public List<Postagem> listarPostagensPorUsuario(int usuarioId) {
        return postagemDAO.getByUserId(usuarioId);
    }

    public boolean atualizarPostagem(Postagem postagem) {
        // Optionally, regenerate image if title changes significantly, or allow manual update
        // For now, let's assume an existing imagemUrl or a new one can be set directly
        return postagemDAO.update(postagem);
    }

    public boolean deletarPostagem(int id) {
        return postagemDAO.delete(id);
    }
}