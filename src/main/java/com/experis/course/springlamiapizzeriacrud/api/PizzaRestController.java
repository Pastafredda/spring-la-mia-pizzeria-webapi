package com.experis.course.springlamiapizzeriacrud.api;

import com.experis.course.springlamiapizzeriacrud.exception.PizzaNotFoundException;
import com.experis.course.springlamiapizzeriacrud.model.Pizza;
import com.experis.course.springlamiapizzeriacrud.service.PizzaService;
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
}
