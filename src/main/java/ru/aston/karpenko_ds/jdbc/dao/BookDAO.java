package ru.aston.karpenko_ds.jdbc.dao;

import ru.aston.karpenko_ds.jdbc.entity.Author;
import ru.aston.karpenko_ds.jdbc.entity.Book;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static ru.aston.karpenko_ds.jdbc.Database.getConnection;

public class BookDAO {
    public void add(final Book book) {
        String sql = "INSERT INTO books (title, author_id, published_year) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setInt(2, book.getAuthorId());
            preparedStatement.setInt(3, book.getPublishedYear());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Book> getAll() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                int authorId = resultSet.getInt("author_id");
                int publishedYear = resultSet.getInt("published_year");

                Book book = new Book(id, title, authorId, publishedYear);
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public void update(final int id, final Book book) {
        String sql = "UPDATE books SET title = ?, author_id = ?, published_year = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setInt(2, book.getAuthorId());
            preparedStatement.setInt(3, book.getPublishedYear());
            preparedStatement.setInt(4, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(final int id) {
        String sql = "DELETE FROM books WHERE id = ?";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Book> findByTitle(final String title) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE title LIKE ?";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, "%" + title + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String foundTitle = resultSet.getString("title");
                int authorId = resultSet.getInt("author_id");
                int publishedYear = resultSet.getInt("published_year");

                Book book = new Book(id, foundTitle, authorId, publishedYear);
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public void addBookAndAuthor(final Book book, final Author author) {
        String insertAuthorSQL = "INSERT INTO authors (first_name, last_name) VALUES (?, ?)";
        String insertBookSQL = "INSERT INTO books (title, author_id, published_year) VALUES (?, ?, ?)";

        try {
            getConnection().setAutoCommit(false);

            try (PreparedStatement authorStatement =
                         getConnection().prepareStatement(insertAuthorSQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
                authorStatement.setString(1, author.getFirstName());
                authorStatement.setString(2, author.getLastName());
                authorStatement.executeUpdate();

                try (ResultSet generatedKeys = authorStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int authorId = generatedKeys.getInt(1);

                        try (PreparedStatement bookStatement =
                                     getConnection().prepareStatement(insertBookSQL)) {
                            bookStatement.setString(1, book.getTitle());
                            bookStatement.setInt(2, authorId);
                            bookStatement.setInt(3, book.getPublishedYear());
                            bookStatement.executeUpdate();
                        }
                    }
                }
            }

            getConnection().commit();
        } catch (SQLException e) {
            try {
                if (getConnection() != null) {
                    getConnection().rollback();
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (getConnection() != null) {
                    getConnection().setAutoCommit(true);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
