package main;

import entity.Books;
import service.*;
import util.FIndId;

import java.sql.SQLOutput;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class LibraryRunner {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            while (true) {
                System.out.println("=== Меню ===");
                System.out.println("1. Добавить книгу");
                System.out.println("2. Просмотреть все книги");
                System.out.println("3. Найти книгу");
                System.out.println("4. Удалить книгу");
                System.out.println("5. Обновить информацию о книге");
                System.out.println("0. Выход");

                System.out.println("Введите номер действия:");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 0 -> {
                        System.out.println("Выход из приложения");
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        return;
                    }
                    case 1 -> {
                        AddBook.result();
                    }
                    case 2 -> {
                        var instance = LookAllBooks.getInstance();
                        var all = instance.findAll();
                        System.out.println(all);
                    }
                    case 3 -> {
                        while (true) {
                            System.out.println("1. Найти по ID");
                            System.out.println("2. Найти по названию");
                            System.out.println("3. Найти по автору");
                            System.out.println("4. Найти по году издания");
                            System.out.println("5. Найти по жанру");
                            System.out.println("0. exit");

                            int choiceBook = scanner.nextInt();
                            scanner.nextLine();
                            switch (choiceBook) {
                                case 0 -> {
                                    return;
                                }
                                case 1 -> {
                                    FindBook.findId(scanner);
                                }
                                case 2 -> {
                                    System.out.println("Введите название книги:");
                                    String nameBook = scanner.nextLine().trim().toLowerCase();
                                    var instance = FindBook.getInstance();
                                    var byTitle = instance.findByTitle(nameBook);
                                    if (byTitle.isEmpty()) {
                                        System.out.println("Книга с названием \"" + nameBook + "\" не найдена.");
                                    } else {
                                        System.out.println("Найдены следующие книги:");
                                        byTitle.forEach(System.out::println);
                                    }
                                }
                                case 3 -> {
                                    System.out.println("Введите псевдоним автора");
                                    String authorBook = scanner.nextLine().trim().toLowerCase();
                                    var instance = FindBook.getInstance();
                                    var byAuthor = instance.findByAuthor(authorBook);
                                    if (byAuthor.isEmpty()) {
                                        System.out.println("Автора с псевдонимом " + byAuthor + " не найдена.");
                                    } else {
                                        System.out.println("Автор найден: ");
                                        byAuthor.forEach(System.out::println);
                                    }
                                }
                                case 4 -> {
                                    boolean metka = true;
                                    int yearResult = 0;
                                    while (metka) {
                                        System.out.println("Введите год издания книги");
                                        int year = scanner.nextInt();
                                        if (year > 0 && year < 2026)
                                            scanner.nextLine();
                                        yearResult = year;
                                        metka = false;
                                    }
                                    var instance = FindBook.getInstance();
                                    var byYearPublished = instance.findByYearPublished(yearResult);
                                    System.out.println("Все книги с " + yearResult + " годом изданий");
                                    byYearPublished.forEach(System.out::println);
                                }
                                case 5 -> {
                                    System.out.println("Введите жанр книги");
                                    String genreBook = scanner.nextLine().trim().toLowerCase();
                                    var instance = FindBook.getInstance();
                                    var byGenre = instance.findByGenre(genreBook);
                                    if (byGenre.isEmpty()) {
                                        System.out.println("Книги с жанром " + genreBook + " не найдены!");
                                    } else {
                                        System.out.println("Книги с жанром " + genreBook + " найдены");
                                        byGenre.forEach(System.out::println);
                                    }
                                }
                            }

                        }

                    }
                    case 4 -> {
                        System.out.println("Введите ID книги которую хотите удалить");
                        int idBook = scanner.nextInt();
                        var instance = DeleteBook.getInstance();
                        var delete = instance.delete(idBook);
                        if (delete) {
                            System.out.println("Книга с id:  " + idBook + " была успешно удалена");
                        } else {
                            System.out.println("Книги с таким id не существует");
                        }
                    }
                    case 5 -> {
                        System.out.println("Введите ID книги, которую хотите обновить:");
                        int id = scanner.nextInt();
                        scanner.nextLine();

                        System.out.println("Введите новое название книги:");
                        String title = scanner.nextLine();

                        System.out.println("Введите нового автора книги:");
                        String author = scanner.nextLine();

                        System.out.println("Введите новый год издания книги:");
                        int yearPublished = scanner.nextInt();
                        scanner.nextLine();

                        System.out.println("Введите новый жанр книги:");
                        String genre = scanner.nextLine();


                        Books book = new Books(id, title, author, yearPublished, genre);


                        try {
                            UpdateBook.update(book);
                            System.out.println("Книга успешно обновлена.");
                        } catch (Exception e) {
                            System.err.println("Ошибка при обновлении книги: " + e.getMessage());
                        }

                    }
                }
            }
        } catch (
                Exception e) {
            throw new RuntimeException();
        }

    }
}
