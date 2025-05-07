package ru.aston.karpenko_ds.jdbc.dao;

import ru.aston.karpenko_ds.jdbc.entity.Author;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static ru.aston.karpenko_ds.jdbc.Database.getConnection;

public class AuthorDAO {
    public List<Author> getAll() {
        List<Author> authors = new ArrayList<>();
        String sql = "SELECT * FROM authors";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");

                Author author = new Author(id, firstName, lastName);
                authors.add(author);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return authors;
    }
}
