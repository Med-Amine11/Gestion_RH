package com.grh.dao;

import com.grh.model.Employe;
import java.util.List;

public interface EmployeDao {
    int addEmploye(Employe employe);
    int updateEmploye(Employe employe);
    int deleteEmploye(int id);
    Employe findEmployeById(int id);
    List<Employe> findAllEmployes();
    Employe findEmployeByNom(String nom);
    int countAllEmployes() ;
}
