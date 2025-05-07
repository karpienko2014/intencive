package ru.aston.karpenko_ds.jdbc.dao;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.aston.karpenko_ds.jdbc.entity.Reader;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.aston.karpenko_ds.jdbc.Database.closeConnection;
import static ru.aston.karpenko_ds.jdbc.Database.getConnection;
import static ru.aston.karpenko_ds.jdbc.TestDatabase.createTables;
import static ru.aston.karpenko_ds.jdbc.TestDatabase.dropTables;

class ReaderDAOTest {

    private Reader reader;
    private ReaderDAO readerDAO = new ReaderDAO();

    @BeforeAll
    static void init() {
        createTables();
    }

    @BeforeEach
    void setUp() {
        reader = new Reader();
        reader.setFirstName("Иван");
        reader.setLastName("Иванов");
        reader.setEmail("ivanov@mail.ru");
    }

    @AfterAll
    static void cleanUp() {
        dropTables();
        closeConnection();
    }

    @Test
    void addReader() throws Exception {
        ResultSet resultSet = null;
        String sql = "SELECT COUNT(*) AS count FROM readers WHERE last_name = '" + reader.getLastName() + "'";
        try (Statement statement = getConnection().createStatement()) {
            statement.executeUpdate(String.format("DELETE FROM readers WHERE last_name = '%s'", reader.getLastName()));

            readerDAO.add(reader);

            resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                int count = resultSet.getInt("count");
                assertEquals(1, count);
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
        }
    }

    @Test
    void getAllReaders() throws Exception {
        try (Statement statement = getConnection().createStatement()) {
            statement.executeUpdate(String.format("DELETE FROM readers WHERE last_name = '%s'", reader.getLastName()));
            statement.executeUpdate("INSERT INTO readers (first_name, last_name, email) VALUES ('Иван', 'Иванов', 'ivanov@mail.ru')");

            List<Reader> readers = readerDAO.getAll();
            assertTrue(readers.size() == 1);
        }
    }
}