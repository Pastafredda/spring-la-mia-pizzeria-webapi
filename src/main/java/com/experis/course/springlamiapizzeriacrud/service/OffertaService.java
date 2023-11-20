package com.experis.course.springlamiapizzeriacrud.service;

import com.experis.course.springlamiapizzeriacrud.exception.PizzaNotFoundException;
import com.experis.course.springlamiapizzeriacrud.model.OffertaSpeciale;
import com.experis.course.springlamiapizzeriacrud.model.Pizza;
import com.experis.course.springlamiapizzeriacrud.repository.OffertaRepository;
import com.experis.course.springlamiapizzeriacrud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class OffertaService {
    @Autowired
    PizzaRepository pizzaRepository;

    @Autowired
    OffertaRepository offertaRepository;

    //metodo per creare un offerta
    public OffertaSpeciale createOfferta(Integer pizzaId) throws PizzaNotFoundException {
        //cerco la pizza, se non c'è eccezione
        Pizza pizza = pizzaRepository.findById(pizzaId).orElseThrow(() -> new PizzaNotFoundException("id della pizza " + pizzaId + " non è stato trovato"));
        //se c'è creo una nuova offerta
        OffertaSpeciale offertaSpeciale = new OffertaSpeciale();
        //do alla data di inizio un set iniziale a oggi
        offertaSpeciale.setDataInizio(LocalDate.now());
        //precarico la pizza
        offertaSpeciale.setPizza(pizza);
        //Ritorno offerta speciale
        return offertaSpeciale;
    }

    public OffertaSpeciale insertOffertaIntoDb(OffertaSpeciale offertaSpeciale) {
        return offertaRepository.save(offertaSpeciale);
    }
}
