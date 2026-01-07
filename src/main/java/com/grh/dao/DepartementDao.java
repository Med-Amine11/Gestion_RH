package com.grh.dao;

import com.grh.model.Departement;

import java.util.List;

public interface DepartementDao {
    public int addDepartment(String nom , String description);
    public Departement findDepartmentByName(String nom) ;
    public int updateDepartement(Departement dep);
    public int deleteDepartement(int id) ;
    public List<Departement> getAllDepartements() ;
    public int countAllDepartements() ;
}
