package org.suai.library.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class Book {

    private int id;

    @NotBlank(message = "Укажите название!")
    private String title;
    @NotBlank(message = "Укажите имя автора!")
    private String author;

    @Min(value = 1500, message = "Год должен быть больше, чем 1500!")
    private int year;



    public Book(int id, String title, String author, int year) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public Book(){}


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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

}
