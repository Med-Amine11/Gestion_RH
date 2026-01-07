package com.grh.dao;

import com.grh.model.Departement;
import com.grh.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartementDaoImp implements DepartementDao {
    private Connection con ;

    public DepartementDaoImp(){
        con = DBConnection.getConnection() ;
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

    @Override
    public List<Departement> getAllDepartements(){
        List<Departement> departements = new ArrayList<Departement>() ;

        String sql = "select id_departement, nom, description from departement" ;

        try(PreparedStatement st = con.prepareStatement(sql)){
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                departements.add( new Departement(rs.getInt("id_departement"),
                        rs.getString("nom") , rs.getString("description"))  );

            }

        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return departements  ;
    }
}
