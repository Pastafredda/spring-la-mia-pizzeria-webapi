package com.experis.course.springlamiapizzeriacrud.service;

import com.experis.course.springlamiapizzeriacrud.exception.IngredienteNameException;
import com.experis.course.springlamiapizzeriacrud.exception.IngredienteNotFoundException;
import com.experis.course.springlamiapizzeriacrud.model.Ingrediente;
import com.experis.course.springlamiapizzeriacrud.repository.IngredienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredienteService {
    @Autowired
    private IngredienteRepository ingredienteRepository;

    //prendiamo la lista degli ingredienti, in ordine alfabetico
    public List<Ingrediente> getAll() {
        return ingredienteRepository.findByOrderByName();
    }

    //metodo per prendere l'id dell'ingrediente
    public Ingrediente getIngredienteId(Integer id) throws IngredienteNotFoundException {
        Optional<Ingrediente> result = ingredienteRepository.findById(id);
        if (result.isPresent()) {
            //uso il model per passare l'oggetto al template
            return result.get();
            //un eccezione per dire non è stato trovato + un messaggio
        } else {
            throw new IngredienteNotFoundException("id dell'ingrediente " + id + " non è stato trovato");
        }
    }

    //metodo che mi salva un ingrediente nuovo
    public Ingrediente save(Ingrediente ingrediente) throws IngredienteNameException {
        //se il nome esiste già tiro un eccezione
        if (ingredienteRepository.existsByName(ingrediente.getName())) {
            throw new IngredienteNameException(ingrediente.getName());
        } else {
            // trasformo il nome in minuscolo
            ingrediente.setName(ingrediente.getName().toLowerCase());
            //salvo sul database
            return ingredienteRepository.save(ingrediente);
        }
    }

    //metodo per eliminare
    public void deleteIngredient(Integer id) {
        ingredienteRepository.deleteById(id);
    }
}
