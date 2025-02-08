package service;

import dao.ConnectionManager;
import exception.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteBook {

    private static final DeleteBook INSTANCE = new DeleteBook();

    private static final String DELETE_SQL = """
            DELETE FROM books
            WHERE id = ?
            """;


    public DeleteBook() {
    }

    public boolean delete(int id) {
        try (var connection = ConnectionManager.open();
             var preparedStatement = connection.prepareStatement(DELETE_SQL)) {

            preparedStatement.setInt(1, id);

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public static DeleteBook getInstance() {
        return INSTANCE;
    }
}

