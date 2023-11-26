package com.experis.course.springlamiapizzeriacrud.service;

import com.experis.course.springlamiapizzeriacrud.dto.PizzaDto;
import com.experis.course.springlamiapizzeriacrud.exception.PizzaNotFoundException;
import com.experis.course.springlamiapizzeriacrud.model.Pizza;
import com.experis.course.springlamiapizzeriacrud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
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

    //metodo per api per la lista di tutte le pizze senza il search
    public List<Pizza> getPizzaList() {
        return pizzaRepository.findAll();
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


    //metodo per salvare la pizza nel database
    public Pizza savePizzaCreate(Pizza pizza) {
        //setid a null per quando cerchiamo di creare una pizza con id settato
        //ci aumenta l'id dal database senza riscrivere l'id
        pizza.setId(null);
        return pizzaRepository.save(pizza);
    }

    public Pizza pizzaDtoCreate(PizzaDto pizzaDto) throws IOException {
        Pizza pizza = convertDtoToPizza(pizzaDto);
        //chiamo il metodo che salva la pizza su db
        return savePizzaCreate(pizza);
    }

    private static Pizza convertDtoToPizza(PizzaDto pizzaDto) throws IOException {
        //converto pizzaDto in pizza
        Pizza pizza = new Pizza();
        pizza.setName(pizzaDto.getName());
        pizza.setDescrizione(pizzaDto.getDescrizione());
        pizza.setIngredienti(pizzaDto.getIngredienti());
        pizza.setPrezzo(pizzaDto.getPrezzo());
        pizza.setId(pizzaDto.getId());
        //per la cover
        //se non è nullo e se non è vuoto
        if (pizzaDto.getCoverFile() != null && !pizzaDto.getCoverFile().isEmpty()) {
            //trasformo il multipartFile in byte[]
            byte[] bytes = pizzaDto.getCoverFile().getBytes();
            pizza.setFoto(bytes);
        }
        return pizza;
    }

    //metodo per salvare una pizza già presente e modificarla
    public Pizza savePizzaEdit(Pizza pizza) throws PizzaNotFoundException {
        Pizza pizzaToEdit = getPizzaId(pizza.getId());
        
        pizzaToEdit.setName(pizza.getName());
        pizzaToEdit.setDescrizione(pizza.getDescrizione());
        pizzaToEdit.setIngredienti(pizza.getIngredienti());
        pizzaToEdit.setPrezzo(pizza.getPrezzo());
        if (pizza.getFoto() != null && pizza.getFoto().length > 0) {
            pizzaToEdit.setFoto(pizza.getFoto());
        }
        return pizzaRepository.save(pizzaToEdit);
    }

    public Pizza pizzaDtoEdit(PizzaDto pizzaDto) throws IOException {
        //converto pizzaDto in pizza
        Pizza pizza = convertDtoToPizza(pizzaDto);
        //salvo pizza sul database
        //passando al metodo savePizzaEdit la pizza convertita
        return savePizzaEdit(pizza);
    }

    //creo un metodo per prendere l'id da pizzaDto
    public PizzaDto getPizzaDtoById(Integer id) throws PizzaNotFoundException {
        //prendo la pizza dal database
        Pizza pizza = getPizzaId(id);
        return convertPizzaToDto(pizza);
    }

    //per edit metodo contrario ovvero converto pizza in pizzaDto
    private static PizzaDto convertPizzaToDto(Pizza pizza) {
        //converto pizzaDto in pizza
        PizzaDto pizzaDto = new PizzaDto();
        pizzaDto.setName(pizza.getName());
        pizzaDto.setDescrizione(pizza.getDescrizione());
        pizzaDto.setIngredienti(pizza.getIngredienti());
        pizzaDto.setPrezzo(pizza.getPrezzo());
        pizzaDto.setId(pizza.getId());
        return pizzaDto;
    }


    //metodo per eliminare una pizza
    public void deletePizza(Integer id) {
        pizzaRepository.deleteById(id);
    }

}
