package com.experis.course.springlamiapizzeriacrud.controller;

import com.experis.course.springlamiapizzeriacrud.model.Pizza;
import com.experis.course.springlamiapizzeriacrud.repository.PizzaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    //tramite il valid, validiamo le annotazioni che abbiamo inserito nella classe Pizza
    //bindingResult ci permette ci catchare gli errori
    //model attribute per ricaricare la pagina con i dati sbagliati inseriti dall'utente(l'attributo pizza che abbiamo inserito nel controller sopra)
    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("pizza") Pizza formpizza, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            //se ci sono errori ricarico la pagina del form create
            return "pizzas/create";
        }
        //salvo il libro sul database tramite pizzarepository
        Pizza savedPizza = pizzaRepository.save(formpizza);
        return "redirect:/pizze";
    }

    //metodo per la modifica
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        //recupero dei dati della pizza a partire dall'id
        Optional<Pizza> result = pizzaRepository.findById(id);
        if (result.isPresent()) {
            //aggiunta pizza come attributo model
            model.addAttribute("pizza", result.get());
            //restituiamo il template di modifica
            return "pizzas/edit";
        } else {
            //eccezione
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id della pizza " + id + " non è stato trovato");
        }
    }

    //metodo che riceve il submit e salva le modifiche
    @PostMapping("/edit/{id}")
    public String doEdit(@PathVariable Integer id, @Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult) {
        //valido la pizza
        if (bindingResult.hasErrors()) {
            return "pizzas/edit";
        }
        Pizza savedPizza = pizzaRepository.save(formPizza);
        return "redirect:/pizze/show/" + savedPizza.getId();
    }

    //metodo per eliminare una pizza dal db
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        //recuperare la pizza con l'id
        Optional<Pizza> result = pizzaRepository.findById(id);
        //se esiste eliminarla
        if (result.isPresent()) {
            pizzaRepository.deleteById(id);
            return "redirect:/pizze";
        } else {
            //eccezione
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id della pizza " + id + " non è stato trovato");
        }
    }

}
