package org.example.volodyanoy.controllers;

import org.example.volodyanoy.dao.PersonDAO;
import org.example.volodyanoy.models.Person;
import org.example.volodyanoy.services.ItemService;
import org.example.volodyanoy.services.PeopleService;
import org.example.volodyanoy.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {


    private final PeopleService peopleService;
    private final PersonValidator personValidator;
    private final ItemService itemService;
    private final PersonDAO personDAO;

    @Autowired
    public PeopleController(PeopleService peopleService, PersonValidator personValidator, ItemService itemService, PersonDAO personDAO) {
        this.peopleService = peopleService;
        this.personValidator = personValidator;
        this.itemService = itemService;
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String index(Model model){
        //Получим всех людей из DAO и передадим в views
        model.addAttribute("people", peopleService.findAll());
        personDAO.testNPlus1();

        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        //Получим одного человека по id из DAO и передадим в views
        model.addAttribute("person", peopleService.findOne(id));

        return "people/show";
    }


    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person){
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
        personValidator.validate(person, bindingResult);

        if(bindingResult.hasErrors()){
            return "people/new";
        }

        peopleService.save(person);
        return "redirect:/people";

    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("person", peopleService.findOne(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                         @PathVariable("id") int id){
        if(bindingResult.hasErrors()){
            return "people/edit";
        }

        peopleService.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        peopleService.delete(id);
        return "redirect:/people";
    }


}
