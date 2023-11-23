package com.experis.course.springlamiapizzeriacrud.api;

import com.experis.course.springlamiapizzeriacrud.exception.PizzaNotFoundException;
import com.experis.course.springlamiapizzeriacrud.model.Pizza;
import com.experis.course.springlamiapizzeriacrud.service.PizzaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/pizze")
@CrossOrigin
public class PizzaRestController {

    @Autowired
    private PizzaService pizzaService;

    //lista di tutti i libri sempre tramite il service
    @GetMapping
    public List<Pizza> index(@RequestParam Optional<String> searchString) {
        //getPizzaList senza il parametro stringa
        return pizzaService.getPizzaList(searchString);
    }

    //dettagli della pizza per id
    @GetMapping("/{id}")
    public Pizza details(@PathVariable Integer id) {
        try {
            return pizzaService.getPizzaId(id);
        } catch (PizzaNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id della pizza " + id + " non è stato trovato");
        }
    }

    //creazione nuova pizza
    //requestBody mi permette di creare un oggetto json
    //tramite questo metodo possiamo creare anche nuovi ingredienti
    @PostMapping
    public Pizza create(@Valid @RequestBody Pizza pizza) {
        try {
            return pizzaService.savePizzaCreate(pizza);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    //modifica della pizza
    //metodo put
    @PutMapping("/{id}")
    public Pizza update(@Valid @RequestBody Pizza pizza, @PathVariable Integer id) {
        pizza.setId(id);
        try {
            return pizzaService.savePizzaEdit(pizza);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
