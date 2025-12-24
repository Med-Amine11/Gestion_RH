package com.grh.dao;

import com.grh.model.Departement;
import com.grh.model.User ;

public interface UserDao {
  public User findByEmail(String email) ;
  public int addDepartment(String nom , String description);
  public Departement findDepartmentByName(String nom) ;
  public int updateDepartement(Departement dep);
  public int deleteDepartement(int id) ;

}
