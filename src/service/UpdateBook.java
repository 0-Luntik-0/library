package service;

import dao.ConnectionManager;
import entity.Books;
import exception.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateBook {

    private static final UpdateBook INSTANCE = new UpdateBook();

    private static final String UPDATE_SQL = """
            UPDATE books
            SET title = ?, author = ?, year_published = ?, genre = ?
            WHERE id = ?
            """;


    public static void update(Books books) {
        if (books == null) {
            throw new IllegalArgumentException("Объект Books не может быть null");
        }
        if (books.getId() <= 0) {
            throw new IllegalArgumentException("ID книги должен быть положительным числом");
        }
        if (books.getTitle() == null || books.getAuthor() == null || books.getGenre() == null) {
            throw new IllegalArgumentException("Поля title, author и genre не могут быть null");
        }
        if (books.getYear_published() <= 0) {
            throw new IllegalArgumentException("Год издания должен быть положительным числом");
        }

        try (var connection = ConnectionManager.open();
             var preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, books.getTitle());
            preparedStatement.setString(2, books.getAuthor());
            preparedStatement.setInt(3, books.getYear_published());
            preparedStatement.setString(4, books.getGenre());
            preparedStatement.setInt(5, books.getId());

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated == 0) {
                System.err.println("Книга с ID " + books.getId() + " не найдена.");
            } else {
                System.out.println("Книга с ID " + books.getId() + " успешно обновлена.");
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при выполнении SQL-запроса: " + e.getMessage());
            throw new DaoException(e);
        }
    }

    public static UpdateBook getInstance() {
        return INSTANCE;
    }
}
