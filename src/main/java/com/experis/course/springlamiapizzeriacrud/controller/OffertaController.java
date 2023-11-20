package com.experis.course.springlamiapizzeriacrud.controller;

import com.experis.course.springlamiapizzeriacrud.exception.PizzaNotFoundException;
import com.experis.course.springlamiapizzeriacrud.model.OffertaSpeciale;
import com.experis.course.springlamiapizzeriacrud.service.OffertaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/offerte")
public class OffertaController {

    @Autowired
    OffertaService offertaService;

    @GetMapping("/create")
    public String create(@RequestParam Integer pizzaId, Model model) {
        try {
            model.addAttribute("offerta", offertaService.createOfferta(pizzaId));
            return "/offerteSpeciali/form";
        } catch (PizzaNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id della pizza " + pizzaId + " non è stato trovato");
        }
    }

    @PostMapping("/create")
    public String doCreate(@Valid @ModelAttribute("offerta") OffertaSpeciale formOffertaSpeciale, BindingResult bindingResult) {
        //validare
        if (bindingResult.hasErrors()) {
            return "offerteSpeciali/form";
        }
        //salvare i dati su database tramite service
        OffertaSpeciale savedOfferta = offertaService.insertOffertaIntoDb(formOffertaSpeciale);
        //redirect alla show
        return "redirect:/pizze/show/" + formOffertaSpeciale.getPizza().getId();

    }
}
