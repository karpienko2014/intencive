package ru.aston.karpenko_ds.jdbc.dao;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.aston.karpenko_ds.jdbc.entity.Author;

import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.aston.karpenko_ds.jdbc.Database.closeConnection;
import static ru.aston.karpenko_ds.jdbc.Database.getConnection;
import static ru.aston.karpenko_ds.jdbc.TestDatabase.*;

class AuthorDAOTest {

    private AuthorDAO authorDAO = new AuthorDAO();

    @BeforeAll
    static void init() {
        createTables();
    }

    @AfterAll
    static void cleanUp() {
        dropTables();
        closeConnection();
    }

    @Test
    void getAllAuthors() throws Exception {
        try (Statement statement = getConnection().createStatement()) {
            statement.executeUpdate("INSERT INTO authors (first_name, last_name) VALUES ('Лев', 'Толстой')");
            List<Author> authors = authorDAO.getAll();
            assertTrue(authors.size() == 1);
        }
    }
}