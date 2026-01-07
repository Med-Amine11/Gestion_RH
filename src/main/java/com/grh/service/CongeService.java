package com.grh.service;

import com.grh.dao.CongeDao;
import com.grh.dao.CongeDaoImp;
import com.grh.model.Conge;

import java.time.temporal.ChronoUnit;
import java.util.List;

public class CongeService {
    private CongeDao congeDao ;
    public CongeService(){
        congeDao = new CongeDaoImp() ;
    }
    public int countAllCongesEnAttente(){
        return congeDao.countAllCongesEnAttente() ;
    }
    public List<Conge> getAllCongesEnCours(){
        return congeDao.getAllCongesEnCours() ;
    }

    public Boolean verifierEmployeInCongesEnCours(List<Conge> conges , int id_employe){
        return conges.stream().anyMatch(c -> c.getId_employe() == id_employe)  ;
    }

    public Boolean archiverConge(Conge conge){
        return congeDao.archiverConge(conge) > 0 ;
    }

    public Boolean supprimerCongesEnAttente(int id_employe){
        return congeDao.supprimerCongesEnAttente(id_employe) > 0 ;
    }
    public boolean ajouterConge(Conge conge) {
        long duree = ChronoUnit.DAYS.between(conge.getDate_debut(), conge.getDate_fin()) + 1; // +1 pour inclure le jour de début
        if (duree <= 0) {
            System.out.println("Erreur : la date de fin doit être après la date de début.");
            return false;
        }
        if (duree > 5) {
            System.out.println("Erreur : la durée du congé ne peut pas dépasser 5 jours.");
            return false;
        }
        return congeDao.ajouterConge(conge);
    }

    /**
     * Valider un congé (statut -> Validé)
     */
    public boolean validerConge(int idConge) {
        return congeDao.validerConge(idConge);
    }

    /**
     * Refuser un congé (supprimer de la DB)
     */
    public boolean refuserConge(int idConge) {
        return congeDao.refuserConge(idConge);
    }

    /**
     * Modifier un congé
     */
    public boolean modifierConge(Conge conge) {
        long duree = ChronoUnit.DAYS.between(conge.getDate_debut(), conge.getDate_fin()) + 1;
        if (duree <= 0) {
            System.out.println("Erreur : la date de fin doit être après la date de début.");
            return false;
        }
        if (duree > 5) {
            System.out.println("Erreur : la durée du congé ne peut pas dépasser 5 jours.");
            return false;
        }
        return congeDao.modifierConge(conge);
    }

    /**
     * Rechercher congés par nom d'employé
     */
    public List<Conge> rechercherCongeParNomEmploye(String nom) {
        return congeDao.rechercherCongeParNomEmploye(nom);
    }

    /**
     * Lister tous les congés
     */
    public List<Conge> listerConges() {
        return congeDao.listerConges();
    }
}


