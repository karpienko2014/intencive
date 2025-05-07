package ru.aston.karpenko_ds.jdbc.dao;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import ru.aston.karpenko_ds.jdbc.entity.Author;
import ru.aston.karpenko_ds.jdbc.entity.Book;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static ru.aston.karpenko_ds.jdbc.Database.closeConnection;
import static ru.aston.karpenko_ds.jdbc.Database.getConnection;
import static ru.aston.karpenko_ds.jdbc.TestDatabase.createTables;
import static ru.aston.karpenko_ds.jdbc.TestDatabase.dropTables;

class BookDAOTest {

    private int authorId = 2;
    private Book book;
    private BookDAO bookDAO = new BookDAO();

    @BeforeAll
    static void init() {
        createTables();
    }

    @BeforeEach
    void setUp() {
        book = new Book();
        book.setTitle("Чрево Парижа");
        book.setAuthorId(authorId);
        book.setPublishedYear(1873);
    }

    @AfterAll
    static void cleanUp() {
        dropTables();
        closeConnection();
    }

    @Test
    void addBook() throws Exception {
        ResultSet resultSet = null;
        String selectByTitle = "SELECT COUNT(*) AS count FROM books WHERE title = '" + book.getTitle() + "'";
        try (Statement statement = getConnection().createStatement()) {
            statement.executeUpdate(String.format("DELETE FROM books WHERE title = '%s'", book.getTitle()));

            bookDAO.add(book);

            resultSet = statement.executeQuery(selectByTitle);
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
    void getAllBooks() throws Exception {
        String theKillTitle = "Добыча";
        String theFortuneOfTheRougonsTitle = "Карьера Ругонов";
        int publishedYear = 1871;

        try (Statement statement = getConnection().createStatement()) {
            statement.executeUpdate(String.format("DELETE FROM books WHERE title = '%s'", theKillTitle));
            statement.executeUpdate(String.format("DELETE FROM books WHERE title = '%s'", theFortuneOfTheRougonsTitle));

            statement.executeUpdate(String.format("INSERT INTO books (title, author_id, published_year) VALUES ('%s', %d, %d)", theKillTitle, authorId, publishedYear));
            statement.executeUpdate(String.format("INSERT INTO books (title, author_id, published_year) VALUES ('%s', %d, %d)", theFortuneOfTheRougonsTitle, authorId, publishedYear));
        }

        List<Book> books = bookDAO.getAll();

        List<Book> filteredBooks =
                books.stream()
                        .filter(book -> book.getTitle().equals(theKillTitle) || book.getTitle().equals(theFortuneOfTheRougonsTitle))
                        .collect(Collectors.toList());
        assertTrue(filteredBooks.size() == 2);
    }

    @Test
    void updateBook() throws Exception {
        String sql = "SELECT * FROM books WHERE title = '" + book.getTitle() + "' LIMIT 1";
        ResultSet resultSet = null;
        try (Statement statement = getConnection().createStatement()) {
            statement.executeUpdate(String.format("INSERT INTO books (title, author_id, published_year) VALUES ('%s', %d, %d)", book.getTitle(), book.getAuthorId(), book.getPublishedYear()));
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                int bookId = resultSet.getInt("id");

                int updatedPublishedYear = 1905;
                book.setPublishedYear(updatedPublishedYear);

                bookDAO.update(bookId, book);

                sql = "SELECT * FROM books WHERE id = '" + bookId + "'";
                resultSet = statement.executeQuery(sql);
                if (resultSet.next()) {
                    int publishedYear = resultSet.getInt("published_year");
                    assertEquals(updatedPublishedYear, publishedYear);
                }
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
        }
    }

    @Test
    void deleteBook() throws Exception {
        String selectByTitle = "SELECT * FROM books WHERE title = '" + book.getTitle() + "' LIMIT 1";
        ResultSet resultSet = null;
        try (Statement statement = getConnection().createStatement()) {
            statement.executeUpdate(String.format("INSERT INTO books (title, author_id, published_year) VALUES ('%s', %d, %d)", book.getTitle(), book.getAuthorId(), book.getPublishedYear()));
            resultSet = statement.executeQuery(selectByTitle);
            if (resultSet.next()) {
                int bookId = resultSet.getInt("id");

                bookDAO.delete(bookId);

                String selectById = "SELECT * FROM books WHERE id = '" + bookId + "'";
                resultSet = statement.executeQuery(selectById);
                assertFalse(resultSet.next());
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
        }
    }

    @Test
    void findBookByTitle() throws Exception {
        try (Statement statement = getConnection().createStatement()) {
            statement.executeUpdate(String.format("INSERT INTO books (title, author_id, published_year) VALUES ('%s', %d, %d)", book.getTitle(), book.getAuthorId(), book.getPublishedYear()));
        }

        List<Book> foundBooks = bookDAO.findByTitle(book.getTitle());
        assertTrue(foundBooks.size() >= 1);
        assertTrue(
                foundBooks.get(0).getTitle()
                        .contains(book.getTitle())
        );
    }

    @Test
    void addBookAndAuthor() throws Exception {
        Book book = new Book();
        book.setTitle("Жерминаль");
        book.setPublishedYear(1885);

        Author author = new Author();
        author.setFirstName("Эмиль");
        author.setLastName("Золя");

        String deleteBooks = "DELETE FROM books WHERE title = 'Жерминаль'";
        String deleteAuthors = "DELETE FROM authors WHERE last_name = 'Золя'";
        String selectByTitle = "SELECT * FROM books WHERE title = '" + book.getTitle() + "'";
        String selectByLastName = "SELECT * FROM authors WHERE last_name = '" + author.getLastName() + "'";
        try (Statement statement = getConnection().createStatement();
             ResultSet booksResultSet = getConnection().createStatement().executeQuery(selectByTitle);
             ResultSet authorsResultSet = getConnection().createStatement().executeQuery(selectByLastName)) {
            statement.executeUpdate(deleteBooks);
            statement.executeUpdate(deleteAuthors);

            bookDAO.addBookAndAuthor(book, author);

            if (booksResultSet.next()) {
                String bookTitle = booksResultSet.getString("title");
                assertEquals(book.getTitle(), bookTitle);
            }
            if (authorsResultSet.next()) {
                String authorLastName = authorsResultSet.getString("last_name");
                assertEquals(author.getLastName(), authorLastName);
            }
        }
    }

    @Test
    void addBookAndAuthorTransactionRollback() throws Exception {
        Book book = new Book();
        book.setTitle("По направлению к Свану");
        book.setPublishedYear(1913);

        Author author = new Author();
        author.setFirstName("Марсель");
        author.setLastName("Пруст");

        String delete = "DELETE FROM authors WHERE last_name = 'Пруст'";
        String insert = "INSERT INTO authors (first_name, last_name) VALUES ('Марсель', 'Пруст')";
        try (Statement statement = getConnection().createStatement()) {
            statement.executeUpdate(delete);
            statement.executeUpdate(insert);

            Connection spy = Mockito.spy(getConnection());

            Field connection = ru.aston.karpenko_ds.jdbc.Database.class.getDeclaredField("connection");
            connection.setAccessible(true);
            connection.set(null, spy);

            bookDAO.addBookAndAuthor(book, author);

            Mockito.verify(spy).rollback();
        }
    }
}