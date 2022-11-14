package org.suai.library.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.suai.library.models.Book;
import org.suai.library.models.Person;

import java.util.List;

@Component
public class BookDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> showAllBook(){
        return this.jdbcTemplate.query("SELECT * FROM Book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book show(int id){
        List<Book> books = this.jdbcTemplate.query("SELECT * FROM Book WHERE id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class));
        for(Book book : books)
            if(book.getId() == id)
                return book;
        return null;
    }


    public void addBook(Book book) {
        this.jdbcTemplate.update("INSERT INTO Book (title, author, year) VALUES(?, ?, ?)",
                book.getTitle(), book.getAuthor(), book.getYear());
    }

    public Person getOwner(Book book){
        List<Person> people = this.jdbcTemplate.query("SELECT Person.* FROM Person " +
                "JOIN Book ON Person.id = Book.person_id WHERE Book.id = ?", new Object[]{book.getId()}, new BeanPropertyRowMapper<>(Person.class));
        if(people.size() != 0)
            return people.get(0);
        return null;
    }

    public void assign(Person newOwner, Book book){
        this.jdbcTemplate.update("UPDATE Book SET person_id = ? WHERE id = ?", newOwner.getId(), book.getId());
    }

    public void release(Book book){
        this.jdbcTemplate.update("UPDATE Book SET person_id = NULL WHERE id = ?", book.getId());
    }

    public void update(int id, Book updatedBook) {
        this.jdbcTemplate.update("UPDATE Book SET title = ?, author = ?, year = ? WHERE id = ?",
                updatedBook.getTitle(), updatedBook.getAuthor(),updatedBook.getYear(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Book WHERE id = ?", id);
    }



}
