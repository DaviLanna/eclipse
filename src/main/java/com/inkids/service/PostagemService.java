package com.inkids.service;

import com.inkids.dao.PostagemDAO;
import com.inkids.dao.UsuarioDAO;
import com.inkids.dto.PostagemDTO;
import com.inkids.model.Postagem;
import com.inkids.model.Usuario;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PostagemService {
    private final PostagemDAO postagemDAO = new PostagemDAO();
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    // TROCADO: Agora instancia e usa o serviço da Azure
    private final AzureImageService azureImageService = new AzureImageService();

    public List<PostagemDTO> listarTodasPostagensDTO() {
        try {
            List<Postagem> postagens = postagemDAO.getAll();
            return postagens.stream().map(this::convertToDto).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public Postagem criarPostagem(Postagem postagem) {
        try {
            if (postagem == null || postagem.getTitulo() == null || postagem.getTitulo().trim().isEmpty() || postagem.getImagemUrl() == null || postagem.getImagemUrl().trim().isEmpty()) {
                return null;
            }
            return postagemDAO.save(postagem);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List<String> gerarImagensPeloTitulo(String titulo, int quantidade) {
        // TROCADO: Agora chama o método do serviço da Azure
        return azureImageService.generateImageUrls(titulo, quantidade);
    }

    public PostagemDTO buscarPostagemPorIdDTO(String id) {
        try {
            Postagem postagem = postagemDAO.get(id);
            return (postagem != null) ? convertToDto(postagem) : null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Postagem> listarPostagensPorUsuario(String usuarioId) {
        try {
            return postagemDAO.getByUserId(usuarioId);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public boolean atualizarPostagem(Postagem postagem) {
        try {
            postagemDAO.save(postagem);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletarPostagem(String id) {
        return postagemDAO.delete(id);
    }

    private PostagemDTO convertToDto(Postagem post) {
        PostagemDTO dto = new PostagemDTO();
        dto.setId(post.getId());
        dto.setTitle(post.getTitulo());
        dto.setContent(post.getConteudo());
        dto.setImageUrl(post.getImagemUrl());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setUpdatedAt(post.getUpdatedAt());
        try {
            Usuario autor = usuarioDAO.get(post.getAutorId());
            dto.setAuthor(autor != null ? autor.getNome() : "Autor Desconhecido");
        } catch (Exception e) {
            dto.setAuthor("Autor Desconhecido");
        }
        return dto;
    }
}