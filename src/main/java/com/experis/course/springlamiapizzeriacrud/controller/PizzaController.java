package com.experis.course.springlamiapizzeriacrud.controller;

import com.experis.course.springlamiapizzeriacrud.model.Pizza;
import com.experis.course.springlamiapizzeriacrud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/pizze")
public class PizzaController {
    @Autowired
    private PizzaRepository pizzaRepository;

    @GetMapping
    public String index(@RequestParam(name = "search") Optional<String> searchString, Model model) {
        List<Pizza> pizzasList;
        //se il parametro è presente filtro la lista
        if (searchString.isPresent()) {
            pizzasList = pizzaRepository.findByNameContainingIgnoreCase(searchString.get());
        } else {
            //altrimenti prendo tutta la lista di pizze
            pizzasList = pizzaRepository.findAll();
        }
        model.addAttribute("list", pizzasList);
        return "pizzas/list";
    }

    @GetMapping("/show/{id}")
    public String show(@PathVariable Integer id, Model model) {
        //optional ci restituisce una "scatola" in cui può esserci o no la pizza
        Optional<Pizza> result = pizzaRepository.findById(id);
        //tramite ispresent verifico se è presente altrimenti tiro un eccezione
        if (result.isPresent()) {
            //uso il model per passare l'oggetto al template
            model.addAttribute("pizza", result.get()); //result.get() per "aprire" la scatola di optional
            return "pizzas/show";
            //un eccezione per dire non è stato trovato + un messaggio
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id della pizza " + id + " non è stato trovato");
        }
    }

    //metodo che mostra il form di creazione
    //usiamo il model per prendere una nuova Pizza e inserirla nel th:object in create.html
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("pizza", new Pizza());
        return "pizzas/create";
    }

    //controller che prende i parametri in post
    @PostMapping("/create")
    public String store(Pizza formpizza) {
        //salvo il libro sul database tramite pizzarepository
        Pizza savedPizza = pizzaRepository.save(formpizza);
        return "redirect:/pizze";
    }

}
