package service;

import dao.ConnectionManager;
import entity.Books;
import exception.DaoException;

import java.awt.print.Book;
import java.awt.print.PrinterException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class FindBook {

    private static final FindBook INSTANCE = new FindBook();

    private static final String Find_By_Id = """
            SELECT title, author, year_published, genre
            FROM books
            WHERE id = ?
            """;
    private static final String Find_By_Title = """
            SELECT id,title, author, year_published, genre
            FROM books
            WHERE LOWER(title) = ?
            """;
    private static final String Find_By_Author = """
            SELECT id,title, author, year_published, genre
            FROM books
            WHERE LOWER(author) = ?
            """;
    private static final String Find_By_Year_Published = """
            SELECT id,title, author, year_published, genre
            FROM books
            WHERE year_published = ?
            """;
    private static final String Find_By_Genre = """
            SELECT id,title, author, year_published, genre
            FROM books
            WHERE LOWER(genre) = ?
            """;


    public FindBook() {
    }

    public static Optional<Books> SettingFindById(int id) {
        try (var connection = ConnectionManager.open();
             var preparedStatement = connection.prepareStatement(Find_By_Id)) {
            preparedStatement.setInt(1, id);

            var resultSet = preparedStatement.executeQuery(); //hELLO
            Books book = null;
            if (resultSet.next()) {
                book = new Books(

                        resultSet.getString("title"),
                        resultSet.getString("author"),
                        resultSet.getInt("year_published"),
                        resultSet.getString("genre")
                );
                return Optional.of(book);
            } else {
                return Optional.empty();
            }
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public static void findId(Scanner scanner) {
        try {

            System.out.println("Введите ID книги которую хотите найти");
            int bookId = scanner.nextInt();

            Optional<Books> bookOptional = FindBook.SettingFindById(bookId);


            if (bookOptional.isPresent()) {
                Books book = bookOptional.get();
                System.out.println("Книга с ID " + bookId + " найдена. \n" +
                                   "📌 Название: " + book.getTitle() + "\n" +
                                   "✍ Автор: " + book.getAuthor() + "\n" +
                                   "📅 Год издания: " + book.getYear_published() + "\n" +
                                   " Жанр: " + book.getYear_published() + "\n");
            } else {
                System.out.println("Книга с ID " + bookId + " не найдена.");
            }
        } catch (Exception e) {
            System.out.println("Произошла ошибка при поиске книги.");
        }

    }

    public List<Books> findByTitle(String title) {
        try (var connection = ConnectionManager.open();
             var preparedStatement = connection.prepareStatement(Find_By_Title)) {
            preparedStatement.setString(1, title.toLowerCase());
            var resultSet = preparedStatement.executeQuery();
            List<Books> findNameList = new ArrayList<>();
            while (resultSet.next()) {
                findNameList.add(new Books(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("author"),
                        resultSet.getInt("year_published"),
                        resultSet.getString("genre")
                ));
            }
            return findNameList;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public List<Books> findByAuthor(String author) {
        try (var connection = ConnectionManager.open();
             var preparedStatement = connection.prepareStatement(Find_By_Author)) {

            preparedStatement.setString(1, author);
            var resultSet = preparedStatement.executeQuery();
            List<Books> findAuthorList = new ArrayList<>();
            while (resultSet.next()) {
                findAuthorList.add(new Books(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("author"),
                        resultSet.getInt("year_published"),
                        resultSet.getString("genre")
                ));
            }
            return findAuthorList;

        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public List<Books> findByYearPublished(int year) {
        try (var connection = ConnectionManager.open();
             var preparedStatement = connection.prepareStatement(Find_By_Year_Published)) {
            preparedStatement.setInt(1, year);
            var resultSet = preparedStatement.executeQuery();
            List<Books> findYearPublishedList = new ArrayList<>();
            while (resultSet.next()) {
                findYearPublishedList.add(new Books(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("author"),
                        resultSet.getInt("year_published"),
                        resultSet.getString("genre")
                ));
            }
            return findYearPublishedList;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public List<Books> findByGenre(String genre) {
        try (var connection = ConnectionManager.open();
             var preparedStatement = connection.prepareStatement(Find_By_Genre)) {
            preparedStatement.setString(1, genre);
            var resultSet = preparedStatement.executeQuery();
            List<Books> findGenreList = new ArrayList<>();
            while (resultSet.next()) {
                findGenreList.add(new Books(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("author"),
                        resultSet.getInt("year_published"),
                        resultSet.getString("genre")
                ));
            }
            return findGenreList;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }


    public static FindBook getInstance() {
        return INSTANCE;
    }

}
