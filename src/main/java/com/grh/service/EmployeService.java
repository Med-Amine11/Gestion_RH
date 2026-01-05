package com.grh.service;

import com.grh.dao.EmployeDao;
import com.grh.dao.EmployeDaoImp;
import com.grh.model.Employe;
import java.util.List;

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
}
