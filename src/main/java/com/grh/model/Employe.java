package com.grh.model;

import java.sql.Date;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Employe {
    private int id_employe;
    private String nom;
    private String prenom;
    private String cin;
    private String email;
    private String telephone;
    private String adresse;
    private String date_naissance;
    private String date_recrutement;
    private String poste;
    private double salaire;
    private int jours_conge_annuel;
    private int id_departement ;
    private String nom_departement ;

    public Employe(int id_employe, String nom, String prenom, String cin, String email,
                   String telephone, String adresse, String date_naissance, String date_recrutement,
                   String poste, double salaire, int jours_conge_annuel, int id_departement) {
        this(id_employe, nom, prenom, cin, email, telephone, adresse, date_naissance,
                date_recrutement, poste, salaire, jours_conge_annuel, id_departement, null);
    }
}
