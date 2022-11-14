package org.suai.library.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.suai.library.dao.BookDAO;
import org.suai.library.dao.PeopleDAO;
import org.suai.library.models.Book;
import org.suai.library.models.Person;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BookController {
    private final PeopleDAO peopleDAO;
    private final BookDAO bookDAO;

    @Autowired
    public BookController(PeopleDAO peopleDAO, BookDAO bookDAO) {
        this.peopleDAO = peopleDAO;
        this.bookDAO = bookDAO;
    }

    @GetMapping()
    public String showAllBook(Model model){
        model.addAttribute("books", this.bookDAO.showAllBook());
        return "books/list";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book){
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult){

        if(bindingResult.hasErrors())
            return "books/new";

        this.bookDAO.addBook(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person){
        Book book = bookDAO.show(id);
        model.addAttribute("book", book);

        Person owner = this.bookDAO.getOwner(book);
        if(owner != null) {
            model.addAttribute("owner", owner);
        }
        else {
            model.addAttribute("people", this.peopleDAO.showAllPerson());
        }
        return "books/show";
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int bookId, @ModelAttribute("person") Person person){
        this.bookDAO.assign(this.peopleDAO.show(person.getId()), this.bookDAO.show(bookId));
        return "redirect:/books/" + bookId;
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int bookId){
        this.bookDAO.release(this.bookDAO.show(bookId));
        return "redirect:/books/" + bookId;
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("book", this.bookDAO.show(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                         @PathVariable("id") int id){

        if(bindingResult.hasErrors())
            return "books/edit";

        this.bookDAO.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        this.bookDAO.delete(id);
        return "redirect:/books";
    }

}
