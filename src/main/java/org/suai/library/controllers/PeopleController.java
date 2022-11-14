package org.suai.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.suai.library.dao.PeopleDAO;
import org.suai.library.models.Person;
import org.suai.library.util.PersonValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private PeopleDAO peopleDAO;
    private PersonValidator personValidator;

    @Autowired
    public PeopleController(PeopleDAO peopleDAO, PersonValidator personValidator) {
        this.peopleDAO = peopleDAO;
        this.personValidator = personValidator;
    }


    @GetMapping()
    public String showAllPerson(Model model){
        model.addAttribute("people", this.peopleDAO.showAllPerson());
        return "people/list";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        Person person = peopleDAO.show(id);
        model.addAttribute("person", person);
        model.addAttribute("books", peopleDAO.getListOfBooks(person));
        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(Model model){
        model.addAttribute("person", new Person());
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){

        personValidator.validate(person, bindingResult);

        if(bindingResult.hasErrors())
            return "people/new";

        this.peopleDAO.addPerson(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("person", this.peopleDAO.show(id));
        return "people/edit";
    }

   @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                         @PathVariable("id") int id){

        if(bindingResult.hasErrors())
            return "people/edit";

        this.peopleDAO.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        this.peopleDAO.delete(id);
        return "redirect:/people";
    }







}
