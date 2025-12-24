package com.grh.dao;

import com.grh.model.Departement;
import com.grh.model.User;

import com.grh.util.DBConnection;

import java.sql.*;

public class UserDaoImp implements UserDao{

    private  Connection con ;

    public UserDaoImp(){
        con = DBConnection.getConnection() ;
    }

    @Override
    public  User findByEmail(String email ) {

        if(con == null ) return null ;


        String sql = "select id_user , email , password from User where email = ? " ;

            try(PreparedStatement st = con.prepareStatement(sql)){

                st.setString(1,email);

                ResultSet rs = st.executeQuery();

                if(rs.next()){
                    return new User(rs.getInt("id_user"),
                            rs.getString("email"),
                            rs.getString("password")) ;
                }

            }catch(SQLException ex){
                ex.printStackTrace();
            }

        return null ;
    }
    @Override
    public int addDepartment(String nom , String description){

       int ligne = 0 ;

       String Sql = "insert into departement (nom , description) values (? , ? )" ;
       try(PreparedStatement st = con.prepareStatement(Sql) ){
           st.setString(1 , nom);
           st.setString(2,description);
           ligne = st.executeUpdate();
       }
       catch(SQLException ex){
           ex.printStackTrace();
       }
       return ligne ;
    }

    @Override
    public Departement findDepartmentByName(String nom) {

        String sql = " select id_departement, nom, description from departement where nom = ? " ;
        try(PreparedStatement st = con.prepareStatement(sql)){
            st.setString(1, nom);
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                return new Departement(rs.getInt("id_departement"),
                        rs.getString("nom") , rs.getString("description")) ;

            }

        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return null ;
    }



    @Override
    public int updateDepartement(Departement dep) {


        String sql = " update departement set nom = ? , description = ? where id_departement = ? " ;

        int ligne = 0 ;
        try(PreparedStatement st = con.prepareStatement(sql)){
            st.setString(1 , dep.getNom());
            st.setString(2 , dep.getDescription());
            st.setInt(3,dep.getId_departement());
            ligne = st.executeUpdate();
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return ligne ;
    }


    @Override
    public int deleteDepartement(int id) {
        String sql = "delete from departement where id_departement = ?" ;
        int ligne = 0 ;
        try(PreparedStatement st = con.prepareStatement(sql)){
            st.setInt(1, id);
            ligne = st.executeUpdate() ;

        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return ligne ;
    }

}
