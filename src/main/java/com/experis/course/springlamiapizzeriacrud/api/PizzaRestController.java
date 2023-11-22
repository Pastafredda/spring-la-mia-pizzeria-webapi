package com.experis.course.springlamiapizzeriacrud.api;

import com.experis.course.springlamiapizzeriacrud.model.Pizza;
import com.experis.course.springlamiapizzeriacrud.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}
