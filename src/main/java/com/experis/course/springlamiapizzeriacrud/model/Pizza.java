package com.experis.course.springlamiapizzeriacrud.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pizze")
public class Pizza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Il nome non può essere vuoto")
    @Size(min = 3, max = 30, message = "Inserisci un nome tra i 3 e i 30 caratteri")
    private String name;
    @Lob
    @NotBlank(message = "La descrizione non può essere vuota")
    @Size(min = 3, max = 255, message = "inserisci una descrizione tra i 3 e i 255 caratteri")
    private String descrizione;

    @Lob
    @Column(length = 16777215)
    private byte[] foto;
    @NotNull(message = "Inserisci un valore")
    @DecimalMin(value = "0.01", message = "il prezzo deve essere maggiore di 0")
    private BigDecimal prezzo;

    //jsonIngore ci serve per ignorare la lista delle offerte nel json
    //possiamo metterlo o qui o sulla pizza in base a come vogliamo vedere i dati
    //altrimenti darebbe un errore di ritorsione
    @OneToMany(mappedBy = "pizza", orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<OffertaSpeciale> offerteSpeciali = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Ingrediente> ingredienti;

    public List<Ingrediente> getIngredienti() {
        return ingredienti;
    }

    public void setIngredienti(List<Ingrediente> ingredienti) {
        this.ingredienti = ingredienti;
    }

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

    public BigDecimal getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(BigDecimal prezzo) {
        this.prezzo = prezzo;
    }

    public List<OffertaSpeciale> getOfferteSpeciali() {
        return offerteSpeciali;
    }

    public void setOfferteSpeciali(List<OffertaSpeciale> offerteSpeciali) {
        this.offerteSpeciali = offerteSpeciali;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }
}

