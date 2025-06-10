package com.example.rotinainteligente.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DAO {
    // Replace with your actual DB connection details
    // For H2 in-memory database:
    private static final String JDBC_URL = "jdbc:h2:mem:rotinadb;DB_CLOSE_DELAY=-1";
    private static final String JDBC_USER = "sa";
    private static final String JDBC_PASSWORD = "";

    // For PostgreSQL:
    // private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/yourdatabase";
    // private static final String JDBC_USER = "youruser";
    // private static final String JDBC_PASSWORD = "yourpassword";

    protected Connection connection;

    public DAO() {
        this.connection = null;
    }

    /**
     * Opens a connection to the database.
     * @return true if connection is successful, false otherwise.
     */
    public boolean conectar() {
        try {
            // Load the JDBC driver (optional for modern JDBC versions)
            // Class.forName("org.h2.Driver"); // For H2
            // Class.forName("org.postgresql.Driver"); // For PostgreSQL
            connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
            return false;
        }
        // catch (ClassNotFoundException e) {
        //     System.err.println("Driver JDBC não encontrado: " + e.getMessage());
        //     return false;
        // }
    }

    /**
     * Closes the database connection.
     * @return true if connection is closed successfully or was already null, false if an error occurs.
     */
    public boolean close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao fechar a conexão com o banco de dados: " + e.getMessage());
            return false;
        }
    }

    /**
     * Initializes the database schema by executing the script.sql file.
     * This is a basic example; consider using a migration tool for production.
     */
    public static void initializeDatabase() {
        // This method would typically read and execute the script.sql content.
        // For simplicity, we'll directly include table creation if this DAO is used early.
        // Or, this can be called once at application startup.
        // For a real app, use Flyway or Liquibase.
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             Statement stmt = conn.createStatement()) {

            // Create Usuarios Table
            stmt.execute("CREATE TABLE IF NOT EXISTS Usuarios (" +
                         "id INT AUTO_INCREMENT PRIMARY KEY, " + // SERIAL for PostgreSQL
                         "nome VARCHAR(255) NOT NULL, " +
                         "email VARCHAR(255) UNIQUE NOT NULL, " +
                         "senha VARCHAR(255) NOT NULL, " +
                         "data_nascimento DATE, " +
                         "genero VARCHAR(50), " +
                         "telefone VARCHAR(20), " +
                         "tipo_usuario VARCHAR(50) DEFAULT 'USER', " +
                         "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                         "updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)");

            // Create Postagens Table
            stmt.execute("CREATE TABLE IF NOT EXISTS Postagens (" +
                         "id INT AUTO_INCREMENT PRIMARY KEY, " + // SERIAL for PostgreSQL
                         "titulo VARCHAR(255) NOT NULL, " +
                         "conteudo TEXT NOT NULL, " +
                         "autor_id INTEGER NOT NULL, " +
                         "imagem_url VARCHAR(1024), " +
                         "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                         "updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                         "FOREIGN KEY (autor_id) REFERENCES Usuarios(id) ON DELETE CASCADE)");

            // Create Tarefas Table
            stmt.execute("CREATE TABLE IF NOT EXISTS Tarefas (" +
                         "id INT AUTO_INCREMENT PRIMARY KEY, " + // SERIAL for PostgreSQL
                         "titulo VARCHAR(255) NOT NULL, " +
                         "descricao TEXT, " +
                         "data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                         "data_conclusao TIMESTAMP, " +
                         "status VARCHAR(50) DEFAULT 'PENDENTE', " +
                         "prioridade VARCHAR(50) DEFAULT 'MEDIA', " +
                         "usuario_id INTEGER NOT NULL, " +
                         "FOREIGN KEY (usuario_id) REFERENCES Usuarios(id) ON DELETE CASCADE)");

            // Create Contatos Table
            stmt.execute("CREATE TABLE IF NOT EXISTS Contatos (" +
                         "id INT AUTO_INCREMENT PRIMARY KEY, " + // SERIAL for PostgreSQL
                         "nome VARCHAR(255) NOT NULL, " +
                         "email VARCHAR(255) NOT NULL, " +
                         "telefone VARCHAR(20), " +
                         "assunto VARCHAR(255), " +
                         "mensagem TEXT NOT NULL, " +
                         "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)");

            System.out.println("Database initialized successfully.");

        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
            e.printStackTrace();
        }
    }
}