package com.example.rotinainteligente.dao;

import com.example.rotinainteligente.model.Usuario;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO extends DAO {

    public UsuarioDAO() {
        super();
    }

    /**
     * Inserts a new user into the database.
     * @param usuario The Usuario object to insert.
     * @return The generated ID of the new user, or -1 if an error occurred.
     */
    public int insert(Usuario usuario) {
        String sql = "INSERT INTO Usuarios (nome, email, senha, data_nascimento, genero, telefone, tipo_usuario, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int generatedId = -1;
        if (conectar()) {
            try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, usuario.getNome());
                pstmt.setString(2, usuario.getEmail());
                pstmt.setString(3, usuario.getSenha()); // Remember to hash passwords
                pstmt.setDate(4, usuario.getDataNascimento() != null ? Date.valueOf(usuario.getDataNascimento()) : null);
                pstmt.setString(5, usuario.getGenero());
                pstmt.setString(6, usuario.getTelefone());
                pstmt.setString(7, usuario.getTipoUsuario());
                pstmt.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
                pstmt.setTimestamp(9, Timestamp.valueOf(LocalDateTime.now()));

                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            generatedId = generatedKeys.getInt(1);
                            usuario.setId(generatedId);
                        }
                    }
                }
            } catch (SQLException e) {
                System.err.println("Erro ao inserir usuário: " + e.getMessage());
            } finally {
                close();
            }
        }
        return generatedId;
    }

    /**
     * Retrieves a user by their ID.
     * @param id The ID of the user.
     * @return The Usuario object, or null if not found or an error occurred.
     */
    public Usuario get(int id) {
        String sql = "SELECT * FROM Usuarios WHERE id = ?";
        Usuario usuario = null;
        if (conectar()) {
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setInt(1, id);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    usuario = new Usuario();
                    usuario.setId(rs.getInt("id"));
                    usuario.setNome(rs.getString("nome"));
                    usuario.setEmail(rs.getString("email"));
                    usuario.setSenha(rs.getString("senha"));
                    Date dataNasc = rs.getDate("data_nascimento");
                    if (dataNasc != null) usuario.setDataNascimento(dataNasc.toLocalDate());
                    usuario.setGenero(rs.getString("genero"));
                    usuario.setTelefone(rs.getString("telefone"));
                    usuario.setTipoUsuario(rs.getString("tipo_usuario"));
                    usuario.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    usuario.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
                }
            } catch (SQLException e) {
                System.err.println("Erro ao buscar usuário: " + e.getMessage());
            } finally {
                close();
            }
        }
        return usuario;
    }

    /**
     * Retrieves all users from the database.
     * @return A list of Usuario objects.
     */
    public List<Usuario> getAll() {
        String sql = "SELECT * FROM Usuarios";
        List<Usuario> usuarios = new ArrayList<>();
        if (conectar()) {
            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setId(rs.getInt("id"));
                    usuario.setNome(rs.getString("nome"));
                    usuario.setEmail(rs.getString("email"));
                    // Do not load password for lists usually, or ensure it's handled securely
                    Date dataNasc = rs.getDate("data_nascimento");
                    if (dataNasc != null) usuario.setDataNascimento(dataNasc.toLocalDate());
                    usuario.setGenero(rs.getString("genero"));
                    usuario.setTelefone(rs.getString("telefone"));
                    usuario.setTipoUsuario(rs.getString("tipo_usuario"));
                    usuario.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    usuario.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
                    usuarios.add(usuario);
                }
            } catch (SQLException e) {
                System.err.println("Erro ao listar usuários: " + e.getMessage());
            } finally {
                close();
            }
        }
        return usuarios;
    }

    /**
     * Updates an existing user in the database.
     * @param usuario The Usuario object with updated information.
     * @return true if successful, false otherwise.
     */
    public boolean update(Usuario usuario) {
        String sql = "UPDATE Usuarios SET nome = ?, email = ?, senha = ?, data_nascimento = ?, genero = ?, telefone = ?, tipo_usuario = ?, updated_at = ? WHERE id = ?";
        boolean success = false;
        if (conectar()) {
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, usuario.getNome());
                pstmt.setString(2, usuario.getEmail());
                pstmt.setString(3, usuario.getSenha()); // Remember to hash passwords
                pstmt.setDate(4, usuario.getDataNascimento() != null ? Date.valueOf(usuario.getDataNascimento()) : null);
                pstmt.setString(5, usuario.getGenero());
                pstmt.setString(6, usuario.getTelefone());
                pstmt.setString(7, usuario.getTipoUsuario());
                pstmt.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
                pstmt.setInt(9, usuario.getId());
                success = pstmt.executeUpdate() > 0;
            } catch (SQLException e) {
                System.err.println("Erro ao atualizar usuário: " + e.getMessage());
            } finally {
                close();
            }
        }
        return success;
    }

    /**
     * Deletes a user from the database by ID.
     * @param id The ID of the user to delete.
     * @return true if successful, false otherwise.
     */
    public boolean delete(int id) {
        String sql = "DELETE FROM Usuarios WHERE id = ?";
        boolean success = false;
        if (conectar()) {
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setInt(1, id);
                success = pstmt.executeUpdate() > 0;
            } catch (SQLException e) {
                System.err.println("Erro ao deletar usuário: " + e.getMessage());
            } finally {
                close();
            }
        }
        return success;
    }

    // You might want to add a method to get user by email for login
    public Usuario getByEmail(String email) {
        String sql = "SELECT * FROM Usuarios WHERE email = ?";
        Usuario usuario = null;
        if (conectar()) {
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, email);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    usuario = new Usuario();
                    usuario.setId(rs.getInt("id"));
                    usuario.setNome(rs.getString("nome"));
                    usuario.setEmail(rs.getString("email"));
                    usuario.setSenha(rs.getString("senha")); // Important for login
                    Date dataNasc = rs.getDate("data_nascimento");
                    if (dataNasc != null) usuario.setDataNascimento(dataNasc.toLocalDate());
                    usuario.setGenero(rs.getString("genero"));
                    usuario.setTelefone(rs.getString("telefone"));
                    usuario.setTipoUsuario(rs.getString("tipo_usuario"));
                    usuario.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    usuario.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
                }
            } catch (SQLException e) {
                System.err.println("Erro ao buscar usuário por email: " + e.getMessage());
            } finally {
                close();
            }
        }
        return usuario;
    }
}