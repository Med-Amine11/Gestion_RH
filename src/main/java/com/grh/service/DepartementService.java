package com.grh.service;

import com.grh.dao.DepartementDao;
import com.grh.dao.DepartementDaoImp;
import com.grh.model.Departement;

import java.util.List;

public class DepartementService {
    private DepartementDao departementDao ;

    public DepartementService(){
        departementDao = new DepartementDaoImp();
    }

    public Boolean addDepartement(Departement departement){
        return departementDao.addDepartment(departement.getNom(), departement.getDescription()) >  0 ;
    }

    public Departement findDepartementByName(String nom){
        return departementDao.findDepartmentByName(nom );
    }

    public Boolean updateDepartement(Departement departement){
        return departementDao.updateDepartement(departement) > 0 ;
    }

    public Boolean deleteDepartementById(int id){
        return departementDao.deleteDepartement(id )> 0 ;
    }

    public List<Departement> getAllDepartements(){
        return departementDao.getAllDepartements() ;
    }
    public int countAllDepartements(){
        return departementDao.countAllDepartements() ;
    }
}
