package com.experis.course.springlamiapizzeriacrud.controller;

import com.experis.course.springlamiapizzeriacrud.exception.OffertaNotFoundException;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/offerte")
public class OffertaController {

    @Autowired
    OffertaService offertaService;

    @GetMapping("/create")
    public String create(@RequestParam Integer pizzaId, Model model) {
        try {
            model.addAttribute("offerta", offertaService.createOfferta(pizzaId));
            return "/offerteSpeciali/create";
        } catch (PizzaNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id della pizza " + pizzaId + " non è stato trovato");
        }
    }

    @PostMapping("/create")
    public String doCreate(@Valid @ModelAttribute("offerta") OffertaSpeciale createOffertaSpeciale, BindingResult bindingResult) {
        //validare
        if (bindingResult.hasErrors()) {
            return "offerteSpeciali/create";
        }
        //salvare i dati su database tramite service
        OffertaSpeciale savedOfferta = offertaService.insertOffertaIntoDb(createOffertaSpeciale);
        //redirect alla show
        return "redirect:/pizze/show/" + createOffertaSpeciale.getPizza().getId();
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        try {
            OffertaSpeciale offertaSpeciale = offertaService.getOfferta(id);
            model.addAttribute("offerta", offertaSpeciale);
            return "/offerteSpeciali/edit";
        } catch (OffertaNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id della pizza " + id + " non è stato trovato");
        }
    }

    @PostMapping("/edit/{id}")
    public String doEdit(@PathVariable Integer id, @Valid @ModelAttribute("offerta") OffertaSpeciale editOffertaSpeciale, BindingResult bindingResult) {
        //valido
        if (bindingResult.hasErrors()) {
            return "/offerteSpeciali/edit";
        }
        //salvo su db
        OffertaSpeciale savedOfferta = offertaService.updateOffertaIntoDb(editOffertaSpeciale);
        //redirect
        return "redirect:/pizze/show/" + editOffertaSpeciale.getPizza().getId();
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            //recupero l'id dell'offerta
            OffertaSpeciale offertaToDelete = offertaService.getOfferta(id);
            //cancello l'offerta
            offertaService.deleteOfferta(offertaToDelete);
            //redirectAttributes per il messaggio di avvenuta eliminazione
            redirectAttributes.addFlashAttribute("message", "L'offerta': " + offertaToDelete.getTitolo() + " è stata eliminata");
            //redirect alla pagina di dettaglio della pizza
            return "redirect:/pizze/show/" + offertaToDelete.getPizza().getId();
        } catch (OffertaNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id della pizza " + id + " non è stato trovato");
        }
    }
}
