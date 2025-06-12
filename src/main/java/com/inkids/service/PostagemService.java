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
    private final GeminiImageService geminiImageService = new GeminiImageService();

    public List<PostagemDTO> listarTodasPostagensDTO() {
        try {
            List<Postagem> postagens = postagemDAO.getAll();
            // Converte cada Postagem para PostagemDTO
            return postagens.stream().map(this::convertToDto).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public Postagem criarPostagem(Postagem postagem) {
        try {
            if (postagem == null || postagem.getTitulo() == null || postagem.getTitulo().trim().isEmpty()) {
                return null;
            }
            // Gera a imagem antes de salvar
            String imageUrl = geminiImageService.generateImageUrl(postagem.getTitulo());
            postagem.setImagemUrl(imageUrl);
            return postagemDAO.save(postagem);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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

    // Método auxiliar para converter a entidade Postagem em um DTO
    private PostagemDTO convertToDto(Postagem post) {
        PostagemDTO dto = new PostagemDTO();
        dto.setId(post.getId());
        dto.setTitle(post.getTitulo());
        dto.setContent(post.getConteudo());
        dto.setImageUrl(post.getImagemUrl());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setUpdatedAt(post.getUpdatedAt());
        try {
            // Busca o nome do autor usando o autorId
            Usuario autor = usuarioDAO.get(post.getAutorId());
            dto.setAuthor(autor != null ? autor.getNome() : "Autor Desconhecido");
        } catch (Exception e) {
            dto.setAuthor("Autor Desconhecido");
        }
        return dto;
    }
}