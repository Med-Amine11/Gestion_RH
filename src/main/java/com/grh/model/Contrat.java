package com.grh.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Contrat {
    private int id_contrat;
    private String type_contrat;
    private LocalDate date_debut;
    private LocalDate date_fin;
    private double salaire_base;
    private int id_employe;
}