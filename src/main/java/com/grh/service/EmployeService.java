package com.grh.service;

import com.grh.dao.EmployeDao;
import com.grh.dao.EmployeDaoImp;
import com.grh.model.Employe;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeService {
    private EmployeDao employeDao;

    public EmployeService() {
        this.employeDao = new EmployeDaoImp();
    }

    public boolean addEmploye(Employe employe) {
        // Here you can add validation logic before calling the DAO
        return employeDao.addEmploye(employe) > 0;
    }

    public boolean updateEmploye(Employe employe) {
        return employeDao.updateEmploye(employe) > 0;
    }

    public boolean deleteEmploye(int id) {
        return employeDao.deleteEmploye(id) > 0;
    }

    public Employe findEmployeById(int id) {
        return employeDao.findEmployeById(id);
    }

    public List<Employe> findAllEmployes() {
        return employeDao.findAllEmployes();
    }

    public Employe findEmployeByNom(String nom) {
         return  employeDao.findEmployeByNom(nom);
    }

    public int countAllEmployes(){return employeDao.countAllEmployes() ; }
    public Boolean employeExisteParNomPrenom(
            List<Employe> employes,
            String nom,
            String prenom
    ) {
        return employes.stream()
                .anyMatch(e ->
                        e.getNom().equalsIgnoreCase(nom)
                                && e.getPrenom().equalsIgnoreCase(prenom)
                );
    }

    public Boolean employeExisteParCin(
            List<Employe> employes,
            String cin
    ) {
        return employes.stream()
                .anyMatch(e -> e.getCin().equals(cin));
    }

    public Boolean employeExisteParEmail(
            List<Employe> employes,
            String email
    ) {
        return employes.stream()
                .anyMatch(e -> e.getEmail().equalsIgnoreCase(email));
    }

    public Boolean employeExisteParTelephone(
            List<Employe> employes,
            String telephone
    ) {
        return employes.stream()
                .anyMatch(e -> e.getTelephone().equals(telephone));
    }

    public Boolean employeExisteParAdresse(
            List<Employe> employes,
            String adresse
    ) {
        return employes.stream()
                .anyMatch(e -> e.getAdresse().equalsIgnoreCase(adresse));
    }

    public Boolean employeExisteParNomDifferentId(
            List<Employe> employes ,
            String nom ,
            String prenom ,
            int id){
        return employes.stream().anyMatch(e ->
                e.getId_employe() != id &&
                        e.getNom().equalsIgnoreCase(nom) &&
                        e.getPrenom().equalsIgnoreCase(prenom)) ;
    }

    public Boolean employeExisteParCinDifferentId(
            List<Employe> employes ,
            String cin ,
            int id){
        return employes.stream().anyMatch(e->
                e.getId_employe() != id && e.getCin().equalsIgnoreCase(cin)) ;
    }
    public Boolean employeExisteParEmailDifferentId(
            List<Employe> employes,
            String email,
            int id
    ) {
        return employes.stream()
                .anyMatch(e ->
                        e.getId_employe() != id
                                && e.getEmail().equalsIgnoreCase(email)
                );
    }
    public Boolean employeExisteParTelephoneDifferentId(
            List<Employe> employes,
            String telephone,
            int id
    ) {
        return employes.stream()
                .anyMatch(e ->
                        e.getId_employe() != id
                                && e.getTelephone().equals(telephone)
                );
    }
    public Boolean employeExisteParAdresseDifferentId(
            List<Employe> employes,
            String adresse,
            int id
    ) {
        return employes.stream()
                .anyMatch(e ->
                        e.getId_employe() != id
                                && e.getAdresse().equalsIgnoreCase(adresse)
                );
    }
    public Boolean employeExiste(List<Employe> employes, Employe employe) {
        return employes.stream().anyMatch(e ->
                // Comparaison de tous les champs sauf id_employe et nom_departement
                e.getNom().equalsIgnoreCase(employe.getNom()) &&
                        e.getPrenom().equalsIgnoreCase(employe.getPrenom()) &&
                        e.getCin().equals(employe.getCin()) &&
                        e.getEmail().equalsIgnoreCase(employe.getEmail()) &&
                        e.getTelephone().equals(employe.getTelephone()) &&
                        e.getAdresse().equalsIgnoreCase(employe.getAdresse()) &&
                        e.getDate_naissance().equals(employe.getDate_naissance()) &&
                        e.getDate_recrutement().equals(employe.getDate_recrutement()) &&
                        e.getPoste().equalsIgnoreCase(employe.getPoste()) &&
                        e.getSalaire() == employe.getSalaire() &&
                        e.getId_departement() == employe.getId_departement()
        );
    }

    public List<Employe> ListerEmployesParNom(List<Employe> employes , String nom)
    {
        return employes.stream().
                filter(employe -> employe.getNom().toLowerCase().contains(nom.toLowerCase())).
                toList() ;

    }

    public Boolean archiverEmploye(Employe e){
        return employeDao.archiverEmploye(e) > 0 ;
    }

}
