package com.inkids.service;

import com.inkids.dao.PostagemDAO;
import com.inkids.dao.UsuarioDAO; // Importar o UsuarioDAO
import com.inkids.dto.PostagemDTO; // Importar o novo DTO
import com.inkids.model.Postagem;
import com.inkids.model.Usuario;   // Importar o modelo Usuario

import java.util.ArrayList; // Importar ArrayList
import java.util.List;

/**
 * Camada de serviço para a entidade Postagem.
 * Contém a lógica de negócio, incluindo a integração com o serviço de geração de imagens.
 */
public class PostagemService {
    private final PostagemDAO postagemDAO;
    private final GeminiImageService geminiImageService;
    private final UsuarioDAO usuarioDAO; // Adicionar para buscar o nome do autor

    public PostagemService() {
        this.postagemDAO = new PostagemDAO();
        this.geminiImageService = new GeminiImageService();
        this.usuarioDAO = new UsuarioDAO(); // Instanciar o UsuarioDAO
    }
    
    /**
     * NOVO MÉTODO: Lista todas as postagens e as converte para o formato DTO.
     * Este método busca os dados brutos e os enriquece com o nome do autor.
     * @return Uma lista de objetos PostagemDTO, pronta para ser enviada ao frontend.
     */
    public List<PostagemDTO> listarTodasPostagensDTO() {
        List<Postagem> postagens = postagemDAO.getAll();
        List<PostagemDTO> dtos = new ArrayList<>();

        for (Postagem post : postagens) {
            PostagemDTO dto = new PostagemDTO();
            
            // Mapeia os campos da entidade para o DTO
            dto.setId(post.getId());
            dto.setTitle(post.getTitulo());     // De "titulo" para "title"
            dto.setContent(post.getConteudo());
            dto.setImageUrl(post.getImagemUrl());
            dto.setCreatedAt(post.getCreatedAt());
            dto.setUpdatedAt(post.getUpdatedAt());

            // Busca o usuário autor pelo ID para obter o nome
            Usuario autor = usuarioDAO.get(post.getAutorId());
            if (autor != null) {
                dto.setAuthor(autor.getNome()); // Define o nome do autor
            } else {
                dto.setAuthor("Autor Desconhecido"); // Fallback
            }
            
            dtos.add(dto);
        }
        return dtos;
    }

    /**
     * Cria uma nova postagem, gerando uma imagem de IA baseada no título.
     * @param postagem O objeto Postagem a ser criado.
     * @return O objeto Postagem criado com a URL da imagem e o ID, ou null em caso de falha.
     */
    public Postagem criarPostagem(Postagem postagem) {
        // Validação: Garante que a postagem e o título não são nulos.
        if (postagem == null || postagem.getTitulo() == null || postagem.getTitulo().trim().isEmpty()) {
            System.err.println("Tentativa de criar postagem com título inválido.");
            return null;
        }

        // --- Ponto de Inteligência Artificial ---
        // Gera a URL da imagem com base no título da postagem.
        String imageUrl = geminiImageService.generateImageUrl(postagem.getTitulo());
        postagem.setImagemUrl(imageUrl);
        // -----------------------------------------

        // Insere a postagem no banco de dados.
        int id = postagemDAO.insert(postagem);
        if (id != -1) {
            postagem.setId(id);
            return postagem;
        }
        
        return null;
    }

    /**
     * Busca uma postagem pelo seu ID.
     * @param id O ID da postagem.
     * @return O objeto Postagem encontrado, ou null se não existir.
     */
    public Postagem buscarPostagemPorId(int id) {
        return postagemDAO.get(id);
    }

    /**
     * Lista todas as postagens cadastradas.
     * @return Uma lista de objetos Postagem.
     */
    public List<Postagem> listarTodasPostagens() {
        return postagemDAO.getAll();
    }
    
    /**
     * Lista todas as postagens de um usuário específico.
     * @param usuarioId O ID do usuário (autor).
     * @return Uma lista de postagens do usuário.
     */
    public List<Postagem> listarPostagensPorUsuario(int usuarioId) {
        return postagemDAO.getByUserId(usuarioId);
    }

    /**
     * Atualiza os dados de uma postagem.
     * Nota: A lógica atual não gera uma nova imagem na atualização, mas poderia ser adicionada.
     * @param postagem O objeto Postagem com as informações atualizadas.
     * @return true se a atualização foi bem-sucedida, false caso contrário.
     */
    public boolean atualizarPostagem(Postagem postagem) {
        if (postagem.getId() <= 0) {
            return false;
        }
        return postagemDAO.update(postagem);
    }

    /**
     * Deleta uma postagem pelo seu ID.
     * @param id O ID da postagem a ser deletada.
     * @return true se a deleção foi bem-sucedida, false caso contrário.
     */
    public boolean deletarPostagem(int id) {
        return postagemDAO.delete(id);
    }

    /**
     * NOVO MÉTODO: Busca uma postagem por ID e a converte para o formato DTO.
     * @param id O ID da postagem.
     * @return Um objeto PostagemDTO, ou null se a postagem não for encontrada.
     */
    public PostagemDTO buscarPostagemPorIdDTO(int id) {
        Postagem postagem = postagemDAO.get(id);
        if (postagem == null) {
            return null; // Retorna nulo se a postagem não existir
        }

        PostagemDTO dto = new PostagemDTO();

        // Mapeia os campos da entidade para o DTO
        dto.setId(postagem.getId());
        dto.setTitle(postagem.getTitulo());
        dto.setContent(postagem.getConteudo());
        dto.setImageUrl(postagem.getImagemUrl());
        dto.setCreatedAt(postagem.getCreatedAt());
        dto.setUpdatedAt(postagem.getUpdatedAt());

        // Busca o usuário autor pelo ID para obter o nome
        Usuario autor = usuarioDAO.get(postagem.getAutorId());
        if (autor != null) {
            dto.setAuthor(autor.getNome());
        } else {
            dto.setAuthor("Autor Desconhecido");
        }

        return dto;
    }
}