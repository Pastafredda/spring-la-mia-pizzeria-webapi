package com.experis.course.springlamiapizzeriacrud.controller;

import com.experis.course.springlamiapizzeriacrud.exception.PizzaNotFoundException;
import com.experis.course.springlamiapizzeriacrud.model.Pizza;
import com.experis.course.springlamiapizzeriacrud.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/files")
public class FileController {

    @Autowired
    private PizzaService pizzaService;

    //Metodo che restituisce l'immagine della pizza presa per id
    @GetMapping("/cover/{pizzaId}")
    public ResponseEntity<byte[]> cover(@PathVariable Integer pizzaId) {
        try {
            //recupero la pizza con quell'id
            Pizza pizza = pizzaService.getPizzaId(pizzaId);
            byte[] coverBytes = pizza.getFoto();
            //se abbiamo la cover
            if (coverBytes != null && coverBytes.length > 0) {
                MediaType mediaType = MediaType.IMAGE_JPEG;
                //ritorno uno status ok (200) come contentType jpeg, e nel body la mia cover
                return ResponseEntity.ok().contentType(mediaType).body(coverBytes);
            } else {
                //se non c'è la cover 404
                return ResponseEntity.notFound().build();
            }
        } catch (PizzaNotFoundException e) {
            //se non trovo la pizza ritorno un 404
            return ResponseEntity.notFound().build();
        }
    }
}
