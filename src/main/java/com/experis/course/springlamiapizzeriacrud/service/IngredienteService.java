package com.experis.course.springlamiapizzeriacrud.service;

import com.experis.course.springlamiapizzeriacrud.model.Ingrediente;
import com.experis.course.springlamiapizzeriacrud.repository.IngredienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredienteService {
    @Autowired
    private IngredienteRepository ingredienteRepository;

    //prendiamo la lista degli ingredienti, in ordine alfabetico
    public List<Ingrediente> getAll() {
        return ingredienteRepository.findByOrderByName();
    }
}
