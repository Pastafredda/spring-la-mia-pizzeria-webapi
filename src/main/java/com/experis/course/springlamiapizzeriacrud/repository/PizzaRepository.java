package com.experis.course.springlamiapizzeriacrud.repository;

import com.experis.course.springlamiapizzeriacrud.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PizzaRepository extends JpaRepository<Pizza, Integer> {
    //query custom per ricercare le pizze tramite nome
    List<Pizza> findByNameContainingIgnoreCase(String nameKeyword);
}
