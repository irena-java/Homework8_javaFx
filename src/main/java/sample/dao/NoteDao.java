package main.java.sample.dao;

import main.java.sample.entity.Note;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class NoteDao {

    private static final String URL = "jdbc:postgresql://localhost:5432/my_notes";
    private static final String USER = "postgres";
    private static final String PASSWORD = "Nbv29062006";
    public static final String INSERT_QUERY = "INSERT INTO notes(description, created_time) VALUES (?, ?)";
    public static final String DELETE_QUERY = "DELETE FROM notes WHERE id=?";
    public static final String SELECT_ALL_QUERY = "SELECT id,description, created_time FROM notes";

    public Note create(Note note) throws DaoException {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, note.getDescription());
            Timestamp timestamp = Timestamp.valueOf(note.getCreatedTime());
            preparedStatement.setTimestamp(2, timestamp);
            preparedStatement.execute();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            int id = generatedKeys.getInt(1);
            note.setId(id);
            return note;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DaoException("Failed to connect");
        }
    }

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void delete(Note note) throws DaoException {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement(DELETE_QUERY);
            preparedStatement.setInt(1, note.getId());
            preparedStatement.execute();
        } catch (SQLException | ClassNotFoundException e) {
            throw new DaoException("Failed to connect");
        }
    }

    public List<Note> findAll() throws DaoException {
        LocalDateTime localDateTime;
        List<Note> notes = new ArrayList<>();
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_QUERY);
            while (resultSet.next()) {
                Note note = new Note();
                note.setId(resultSet.getInt("id"));
                note.setDescription(resultSet.getString("description"));
                localDateTime = resultSet.getTimestamp("created_time").toLocalDateTime();
                note.setCreatedTime(localDateTime);
                notes.add(note);
            }
            return notes;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DaoException("Failed to connect");
        }
    }
}