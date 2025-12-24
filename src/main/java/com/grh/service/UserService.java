package com.grh.service;

import com.grh.model.Departement;
import com.grh.model.User ;
import com.grh.dao.* ;
public class UserService {

    private UserDao userDao;

    public UserService(){
        userDao = new UserDaoImp();
    }

   public User login(String email , String password) {
       User user = userDao.findByEmail(email);
       if (user != null && user.getPassword().equals(password))
           return user;

       return null;
   }



}
