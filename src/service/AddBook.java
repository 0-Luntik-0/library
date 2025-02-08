package service;

import dao.ConnectionManager;
import entity.Books;
import exception.DaoException;

import java.sql.*;
import java.util.Scanner;

public class AddBook {

    private static final AddBook INSTANCE = new AddBook();

    private static final String ADD_SQL = """
            INSERT INTO books (title, author, year_published, genre) 
            VALUES (?,?,?,?)
            """;

    public AddBook() {
    }

    public Books add(Books books) {
        try (var connection = ConnectionManager.open();
             var preparedStatement = connection.prepareStatement(ADD_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, books.getTitle());
            preparedStatement.setString(2, books.getAuthor());
            preparedStatement.setInt(3, books.getYear_published());
            preparedStatement.setString(4, books.getGenre());

            preparedStatement.executeUpdate();
            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                books.setId(generatedKeys.getInt("id"));
            }


            return books;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public static void result() {
        System.out.println("=== Добавление книг ===");
        var instance = AddBook.getInstance();

        try {
            Scanner scanner = new Scanner(System.in);
            Books books = new Books();

            System.out.println("Введите название книги");
            String titleName = scanner.nextLine();
            books.setTitle(titleName);

            System.out.println("Введите имя автора");
            String nameAuthor = scanner.nextLine();
            books.setAuthor(nameAuthor);

            boolean metka = true;
            while (metka) {
                System.out.println("Введите год публикации книги");
                int yaer = scanner.nextInt();
                if (yaer>1000 && yaer<2026) {
                    books.setYear_published(yaer);
                    scanner.nextLine();
                    metka = false;
                }

            }

            System.out.println("Введите жанр книги");
            String genreName = scanner.nextLine();
            books.setGenre(genreName);


            instance.add(books);
            System.out.println("Книги '" + titleName + "' успешно добавлена");
        } catch (Exception e) {
            System.out.println("Ошибка, повторите попытку позже");

        }
    }

    private static AddBook getInstance() {
        return INSTANCE;
    }
}
