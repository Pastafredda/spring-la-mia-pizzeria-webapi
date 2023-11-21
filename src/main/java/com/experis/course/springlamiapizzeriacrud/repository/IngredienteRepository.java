package com.experis.course.springlamiapizzeriacrud.repository;

import com.experis.course.springlamiapizzeriacrud.model.Ingrediente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredienteRepository extends JpaRepository<Ingrediente, Integer> {
    //restituiamo la lista di ingredienti in ordine alfabeto
    List<Ingrediente> findByOrderByName();

    boolean existsByName(String name);
}
