package entity;

import java.util.Objects;

public class Books {
    private int id;
    private String title;
    private String author;
    private int year_published;
    private String genre;

    public Books(int id, String title, String author, int year_published, String genre) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year_published = year_published;
        this.genre = genre;
    }

    public Books(String title, String author, int year_published, String genre) {
        this.title = title;
        this.author = author;
        this.year_published = year_published;
        this.genre = genre;
    }

    public Books() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear_published() {
        return year_published;
    }

    public void setYear_published(int year_published) {
        this.year_published = year_published;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override // 213123
    public String toString() {
        return String.format(
                "📖 Книга [ID: %d]\n" +
                "   📌 Название: %s\n" +
                "   ✍ Автор: %s\n" +
                "   📅 Год издания: %d\n" +
                "   🎭 Жанр: %s\n",
                id, title, author, year_published, genre
        );
    }






    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Books books = (Books) o;
        return id == books.id && year_published == books.year_published && Objects.equals(title, books.title) && Objects.equals(author, books.author) && Objects.equals(genre, books.genre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, year_published, genre);
    }
}
