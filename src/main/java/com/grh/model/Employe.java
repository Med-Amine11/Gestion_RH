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

}
