package service;

import dao.ConnectionManager;
import entity.Books;
import exception.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LookAllBooks {
    private static final LookAllBooks INSTANCE = new LookAllBooks();


    private static final String FIND_BY_ALL = """
            SELECT  id, title, author, year_published, genre
            FROM books
            
            """;

    public LookAllBooks() {
    }



    public  List<Books> findAll() {
        try (var connection = ConnectionManager.open();
             var preparedStatement = connection.prepareStatement(FIND_BY_ALL)) {
            var resultSet = preparedStatement.executeQuery();
            List<Books> booksList = new ArrayList<>();

            while (resultSet.next()) {
                booksList.add(new Books(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("author"),
                        resultSet.getInt("year_published"),
                        resultSet.getString("genre")
                ));
            }


            return booksList;

        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }





    public static LookAllBooks getInstance() {
        return INSTANCE;
    }
}
