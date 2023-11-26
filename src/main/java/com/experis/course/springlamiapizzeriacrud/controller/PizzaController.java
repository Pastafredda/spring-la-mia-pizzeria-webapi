package com.experis.course.springlamiapizzeriacrud.controller;

import com.experis.course.springlamiapizzeriacrud.dto.PizzaDto;
import com.experis.course.springlamiapizzeriacrud.exception.PizzaNotFoundException;
import com.experis.course.springlamiapizzeriacrud.model.Pizza;
import com.experis.course.springlamiapizzeriacrud.service.IngredienteService;
import com.experis.course.springlamiapizzeriacrud.service.PizzaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/pizze")
public class PizzaController {

    //fatto il refactor del controller nel service possiamo eliminare pizzaRepository da qua

    @Autowired
    private PizzaService pizzaService;

    @Autowired
    private IngredienteService ingredienteService;

    @GetMapping
    public String index(@RequestParam Optional<String> searchString, Model model) {
        model.addAttribute("list", pizzaService.getPizzaList(searchString));
        return "pizzas/list";
    }

    @GetMapping("/show/{id}")
    public String show(@PathVariable Integer id, Model model) {
        try {
            Pizza pizza = pizzaService.getPizzaId(id);
            //uso il model per passare l'oggetto al template
            model.addAttribute("pizza", pizza);
            return "pizzas/show";
        } catch (PizzaNotFoundException e) {
            //un eccezione per dire non è stato trovato + un messaggio
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id della pizza " + id + " non è stato trovato");
        }
    }

    //metodo che mostra il form di creazione
    //usiamo il model per prendere una nuova Pizza e inserirla nel th:object in create.html
    //richiamiamo la lista di tutti gli ingredienti dal service
    @GetMapping("/create")
    public String create(Model model) {
        //passo pizzaDto al posto di pizza per il file foto
        model.addAttribute("pizza", new PizzaDto());
        model.addAttribute("listaIngredienti", ingredienteService.getAll());
        return "pizzas/create";
    }

    //controller che prende i parametri in post
    //tramite il valid, validiamo le annotazioni che abbiamo inserito nella classe Pizza
    //bindingResult ci permette ci catchare gli errori
    //modelattribute per ricaricare la pagina con i dati sbagliati inseriti dall'utente(l'attributo pizza che abbiamo inserito nel controller sopra)
    @PostMapping("/create")
    //anche qui passiamo nelle parentesi pizzadto al posto di pizza per il file della foto
    public String store(@Valid @ModelAttribute("pizza") PizzaDto formPizza, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            //richiamo la lista degli ingredienti se ho degli errori tramite model
            model.addAttribute("listaIngredienti", ingredienteService.getAll());
            //se ci sono errori ricarico la pagina del form create
            return "pizzas/create";
        }
        //salvo il libro sul database tramite pizzaservice
        //metto in un try catch e uso la funzione dto che ho creato per il file
        try {
            Pizza savedPizza = pizzaService.pizzaDtoCreate(formPizza);
        } catch (IOException e) {
            bindingResult.addError(new FieldError("pizza", "coverFile", null, false, null, null, "impossibile vedere il file"));
        }
        return "redirect:/pizze";
    }

    //metodo per la modifica
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        try {
            //aggiunta pizza come attributo model
            //in aggiunta il metodo pizzaDto richiamo getPizzaDtoById invece di getPizzaId
            model.addAttribute("pizza", pizzaService.getPizzaDtoById(id));//utilizzo il metodo nel service per recuperare l'id
            model.addAttribute("listaIngredienti", ingredienteService.getAll()); //richiamo la lista di ingredienti come nella create
            //restituiamo il template di modifica
            return "pizzas/edit";
        } catch (PizzaNotFoundException e) {
            //eccezione
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id della pizza " + id + " non è stato trovato");
        }
    }

    //metodo che riceve il submit e salva le modifiche
    @PostMapping("/edit/{id}")
    //anche qui nelle parentesi mettiamo pizzaDto e non pizza
    public String doEdit(@PathVariable Integer id, @Valid @ModelAttribute("pizza") PizzaDto formPizza, BindingResult bindingResult, Model model) {
        //valido la pizza
        if (bindingResult.hasErrors()) {
            //richiamo la lista degli ingredienti se ho degli errori tramite model
            model.addAttribute("listaIngredienti", ingredienteService.getAll());
            return "pizzas/edit";
        }
        try {
            //cambio savePizzaEdit in pizzaDtoEdit
            Pizza savedPizza = pizzaService.pizzaDtoEdit(formPizza);
            return "redirect:/pizze/show/" + savedPizza.getId();
        } catch (PizzaNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id della pizza " + id + " non è stato trovato");
        } catch (IOException e) {
            bindingResult.addError(new FieldError("pizza", "coverFile", null, false, null, null, "impossibile vedere il file"));
            return "pizzas/edit";
        }
    }

    //metodo per eliminare una pizza dal db
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        //recuperare la pizza con l'id
        //se esiste eliminarla altrimenti eccezione
        //redirect attributes per il messaggio di confermata eliminazione
        try {
            Pizza pizzaDelete = pizzaService.getPizzaId(id);
            pizzaService.deletePizza(id);
            redirectAttributes.addFlashAttribute("message", "La pizza: " + pizzaDelete.getName() + " è stata eliminata");
            return "redirect:/pizze";
        } catch (PizzaNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id della pizza " + id + " non è stato trovato");
        }
    }
}
