package com.grh.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Departement {
    private int id_departement;
    private String nom;
    private String description;

    // Constructeur vide
    public Departement() {
    }

    // Constructeur complet
    public Departement(int id_departement, String nom, String description) {
        this.id_departement = id_departement;
        this.nom = nom;
        this.description = description;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return getNom() ;
    }
}

