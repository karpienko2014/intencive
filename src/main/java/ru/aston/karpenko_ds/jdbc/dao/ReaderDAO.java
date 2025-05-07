package ru.aston.karpenko_ds.jdbc.dao;

import ru.aston.karpenko_ds.jdbc.entity.Reader;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static ru.aston.karpenko_ds.jdbc.Database.getConnection;

public class ReaderDAO {
    public void add(final Reader reader) {
        String sql = "INSERT INTO readers (first_name, last_name, email) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, reader.getFirstName());
            preparedStatement.setString(2, reader.getLastName());
            preparedStatement.setString(3, reader.getEmail());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Reader> getAll() {
        List<Reader> readers = new ArrayList<>();
        String sql = "SELECT * FROM readers";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String email = resultSet.getString("email");

                Reader reader = new Reader(id, firstName, lastName, email);
                readers.add(reader);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return readers;
    }
}
