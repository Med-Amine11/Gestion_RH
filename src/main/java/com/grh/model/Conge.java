package com.grh.model;

import java.sql.Date;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Conge {
    private int id_conge;
    private String type_conge;
    private LocalDate date_debut;
    private LocalDate date_fin;
    private String statut;
    private String motif;
    private int id_employe ;

}