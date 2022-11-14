package org.suai.library.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.suai.library.models.Book;
import org.suai.library.models.Person;

import java.util.List;
import java.util.Objects;

@Component
public class PeopleDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PeopleDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> showAllPerson(){
        return this.jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id) {

        List<Person> list = jdbcTemplate.query("SELECT * FROM Person WHERE id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class));
        for(Person person : list)
            if(person.getId() == id)
                return person;
        return null;
    }

    // для проверки есть ли такой чел уже в дб
    public Person show(String fullName){
        List<Person> list = jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
        for(Person curPerson : list)
            if(Objects.equals(curPerson.getFullName(), fullName))
                return curPerson;
        return null;
    }

    public List<Book> getListOfBooks(Person person){
        return jdbcTemplate.query("SELECT * FROM Book " +
                "WHERE person_id = ?", new Object[]{person.getId()}, new BeanPropertyRowMapper<>(Book.class));
    }

    public void addPerson(Person person) {
        this.jdbcTemplate.update("INSERT INTO Person (firstName, middleName, lastName, birthdayYear) VALUES(?, ?, ?, ?)",
                person.getFirstName(), person.getMiddleName(), person.getLastName(), person.getBirthdayYear());
    }

    public void update(int id, Person updatedPerson) {
        this.jdbcTemplate.update("UPDATE Person SET firstname = ?, middlename = ?, lastname = ?, birthdayYear = ? WHERE id = ?",
                updatedPerson.getFirstName(), updatedPerson.getMiddleName(), updatedPerson.getLastName(), updatedPerson
                        .getBirthdayYear(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Person WHERE id = ?", id);
    }








}
