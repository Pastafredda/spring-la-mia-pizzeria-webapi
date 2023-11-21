package com.experis.course.springlamiapizzeriacrud.controller;

import com.experis.course.springlamiapizzeriacrud.exception.IngredienteNameException;
import com.experis.course.springlamiapizzeriacrud.exception.IngredienteNotFoundException;
import com.experis.course.springlamiapizzeriacrud.model.Ingrediente;
import com.experis.course.springlamiapizzeriacrud.service.IngredienteService;
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
@RequestMapping("/ingredienti")
public class IngredienteController {
    @Autowired
    IngredienteService ingredienteService;

    //abbiamo la lista degli ingredienti ed il form per la creazione nella stessa pagina
    //quindi uniamo, come abbiamo fatto nella create passiamo un new Ingrediente tramite object
    @GetMapping
    public String index(Model model) {
        model.addAttribute("listaIngredienti", ingredienteService.getAll());
        //passo al model un' ingrediente vuoto come attributo ingredientiObj del form
        model.addAttribute("ingredientiObj", new Ingrediente());
        return "/ingredienti/index";
    }

    //@valid per la validazione e binding result per il messaggio

    @PostMapping
    public String doSave(@Valid @ModelAttribute("ingredientiObj") Ingrediente formIngrediente, BindingResult bindingResult, Model model) {
        //valido se l'ingrediente è valido
        if (bindingResult.hasErrors()) {
            model.addAttribute("listaIngredienti", ingredienteService.getAll());
            return "/ingredienti/index";
        }
        try {
            //salvo il nuovo ingrediente tramite service
            ingredienteService.save(formIngrediente);
            //redirect in pagina
            return "redirect:/ingredienti";
        } catch (IngredienteNameException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "L'ingrediente con il nome: " + formIngrediente.getName() + " esiste già");
        }
    }

    //eliminare l'ingrediente dal db
    @PostMapping("/delete/{id}")
    public String deleteIngrediente(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        //recuperare l'id dell'ingrediente
        try {
            Ingrediente ingredienteDelete = ingredienteService.getIngredienteId(id);
            //se esiste eliminarlo altrimenti eccezione
            ingredienteService.deleteIngredient(id);
            //redirect alla pagina
            return "redirect:/ingredienti";
        } catch (IngredienteNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id dell'ingrediente " + id + " non è stato trovato");
        }

    }
}
