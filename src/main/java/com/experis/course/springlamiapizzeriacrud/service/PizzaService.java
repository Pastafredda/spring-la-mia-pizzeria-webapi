package com.experis.course.springlamiapizzeriacrud.service;

import com.experis.course.springlamiapizzeriacrud.exception.PizzaNotFoundException;
import com.experis.course.springlamiapizzeriacrud.model.Pizza;
import com.experis.course.springlamiapizzeriacrud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PizzaService {
    @Autowired
    private PizzaRepository pizzaRepository;

    //metodo che restituisce la lista di tutte le pizze
    public List<Pizza> getPizzaList(Optional<String> searchString) {

        //se il parametro è presente filtro la lista
        if (searchString.isPresent()) {
            return pizzaRepository.findByNameContainingIgnoreCase(searchString.get());
        } else {
            //altrimenti prendo tutta la lista di pizze
            return pizzaRepository.findAll();
        }
    }

    //metodo che restituisce i dettagli della pizza tramite id, se c'è altrimenti eccezione
    public Pizza getPizzaId(Integer id) throws PizzaNotFoundException {
        //optional ci restituisce una "scatola" in cui può esserci o no la pizza
        Optional<Pizza> result = pizzaRepository.findById(id);
        //tramite ispresent verifico se è presente altrimenti tiro un eccezione
        if (result.isPresent()) {
            //uso il model per passare l'oggetto al template
            return result.get();
            //un eccezione per dire non è stato trovato + un messaggio
        } else {
            throw new PizzaNotFoundException("id della pizza " + id + " non è stato trovato");
        }
    }


    //metodo per salvare la pizza nel database, la usiamo sia nella edit che create
    public Pizza savePizza(Pizza pizza) {
        return pizzaRepository.save(pizza);
    }

    //metodo per eliminare una pizza
    public void deletePizza(Integer id) {
        pizzaRepository.deleteById(id);
    }

}
