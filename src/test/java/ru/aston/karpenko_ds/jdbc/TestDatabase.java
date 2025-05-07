package ru.aston.karpenko_ds.jdbc;

import java.sql.SQLException;
import java.sql.Statement;

import static ru.aston.karpenko_ds.jdbc.Database.getConnection;

public class TestDatabase {
    public static void createTables() {
        createAuthorsTable();
        createBooksTable();
        createReadersTable();
        // Связываем читателей и книги
        createReaderBookTable();
    }

    public static void dropTables() {
        dropTable("authors");
        dropTable("books");
        dropTable("readers");
    }

    public static void dropTable(String table) {
        try (Statement statement = getConnection().createStatement()) {
            statement.execute("DROP TABLE IF EXISTS " + table + " CASCADE");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createAuthorsTable() {
        String sql =
                "CREATE TABLE IF NOT EXISTS authors (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY, " +
                        "first_name VARCHAR(255) NOT NULL, " +
                        "last_name VARCHAR(255) NOT NULL UNIQUE)";
        try (Statement statement = getConnection().createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createBooksTable() {
        String sql =
                "CREATE TABLE IF NOT EXISTS books (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY, " +
                        "title VARCHAR(255) NOT NULL, " +
                        "author_id INT, " +
                        "published_year INT)";
        try (Statement statement = getConnection().createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createReadersTable() {
        String sql =
                "CREATE TABLE IF NOT EXISTS readers (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY, " +
                        "first_name VARCHAR(255) NOT NULL, " +
                        "last_name VARCHAR(255) NOT NULL, " +
                        "email VARCHAR(255))";
        try (Statement statement = getConnection().createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Связываем читателей и книги
    private static void createReaderBookTable() {
        String sql =
                "CREATE TABLE IF NOT EXISTS reader_book (" +
                        "reader_id INT, " +
                        "book_id INT, " +
                        "PRIMARY KEY (reader_id, book_id), " +
                        "FOREIGN KEY (reader_id) " +
                        "REFERENCES readers(id), " +
                        "FOREIGN KEY (book_id) " +
                        "REFERENCES books(id))";
        try (Statement statement = getConnection().createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
