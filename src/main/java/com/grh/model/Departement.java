package com.grh.model;

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

    // Getter et Setter pour id_departement
    public int getId_departement() {
        return id_departement;
    }

    public void setId_departement(int id_departement) {
        this.id_departement = id_departement;
    }

    // Getter et Setter pour nom
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    // Getter et Setter pour description
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Departement : id : " + id_departement +
                " nom : " + nom +
                " descritpion : " + description ;
    }
}

