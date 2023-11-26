package com.experis.course.springlamiapizzeriacrud.dto;

import com.experis.course.springlamiapizzeriacrud.model.Ingrediente;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

public class PizzaDto {
    //in questa classe di "passaggio tengo solo ciò che non serve al database
    private Integer id;
    @NotBlank(message = "Il nome non può essere vuoto")
    @Size(min = 3, max = 30, message = "Inserisci un nome tra i 3 e i 30 caratteri")
    private String name;

    @NotBlank(message = "La descrizione non può essere vuota")
    @Size(min = 3, max = 255, message = "inserisci una descrizione tra i 3 e i 255 caratteri")
    private String descrizione;

    //sarà questo il nome da passare al form
    private MultipartFile coverFile;
    @NotNull(message = "Inserisci un valore")
    @DecimalMin(value = "0.01", message = "il prezzo deve essere maggiore di 0")
    private BigDecimal prezzo;
    private List<Ingrediente> ingredienti;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public MultipartFile getCoverFile() {
        return coverFile;
    }

    public void setCoverFile(MultipartFile coverFile) {
        this.coverFile = coverFile;
    }

    public BigDecimal getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(BigDecimal prezzo) {
        this.prezzo = prezzo;
    }

    public List<Ingrediente> getIngredienti() {
        return ingredienti;
    }

    public void setIngredienti(List<Ingrediente> ingredienti) {
        this.ingredienti = ingredienti;
    }
}
