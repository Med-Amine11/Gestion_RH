package com.grh.dao;

import com.grh.model.Conge ;
import java.util.List;

public interface CongeDao {

    boolean ajouterConge(Conge conge);               // Ajouter un congé (statut par défaut "En attente")
    boolean validerConge(int idConge);               // Valider un congé (changer statut en "Validé")
    boolean refuserConge(int idConge);               // Refuser un congé (supprimer de la DB)
    boolean modifierConge(Conge conge);             // Modifier un congé existant
    List<Conge> rechercherCongeParNomEmploye(String nom);  // Rechercher congé par nom employé
    List<Conge> listerConges();
    List<Conge> getAllCongesEnCours() ;
    int countAllCongesEnAttente() ;
    int archiverConge(Conge conge) ;
    int supprimerCongesEnAttente(int idEmploye) ;
}