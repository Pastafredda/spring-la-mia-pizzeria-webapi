package com.experis.course.springlamiapizzeriacrud.controller;

import com.experis.course.springlamiapizzeriacrud.model.Pizza;
import com.experis.course.springlamiapizzeriacrud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    //metodo per il form di creazione
    @GetMapping("/create")
    public String create() {
        return "pizzas/create";
    }

}
