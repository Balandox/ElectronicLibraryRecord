package org.suai.library.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.suai.library.dao.PeopleDAO;
import org.suai.library.models.Person;

// для каждого класса мы должны сделать одельный валидатор
// и поместить его в пакет util
@Component
public class PersonValidator implements Validator {
    private final PeopleDAO peopleDAO;

    @Autowired
    public PersonValidator(PeopleDAO peopleDAO) {
        this.peopleDAO = peopleDAO;
    }

    // этот метод нужен для того, чтобы сказать спрингу к какому классу
    // этот валидатор относится, на объектах какого класса его можно использовать
    @Override
    public boolean supports(Class<?> clazz) {
        // возвращаем тру, если на вход приходит класс Person
        return Person.class.equals(clazz);
    }

    // этот метод будет вызываться на контроллере
    // когда с формы приходят данные, то мы должны
    // пропустить их через этот валидатор
    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        // будем проверять, есть ли такой чел уже в бд
        if(this.peopleDAO.show(person.getFullName()) != null)
            errors.rejectValue("fullName", "" , "Человек с таким ФИО уже существует!");
    }
}
