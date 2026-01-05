package com.grh.dao;

import com.grh.model.Employe;
import com.grh.util.DBConnection;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeDaoImp implements EmployeDao {

    private Connection con;

    public EmployeDaoImp() {
        con = DBConnection.getConnection();
    }

    @Override
    public int addEmploye(Employe employe) {

        LocalDate dateNaissance = LocalDate.parse(employe.getDate_naissance());
        LocalDate dateRecrutement = LocalDate.parse(employe.getDate_recrutement()) ;

        String sql = "INSERT INTO employe (nom, prenom, cin, adresseEmail, telephone, adresse, date_naissance, " +
                     "date_recrutement, poste, salaire, jours_conge_annuel , id_departement) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
        int rows = 0;
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, employe.getNom());
            st.setString(2, employe.getPrenom());
            st.setString(3, employe.getCin());
            st.setString(4, employe.getEmail());
            st.setString(5, employe.getTelephone());
            st.setString(6, employe.getAdresse());
            st.setDate(7 , java.sql.Date.valueOf(dateNaissance)) ;
            st.setDate( 8 ,java.sql.Date.valueOf(dateRecrutement) );
            st.setString(9, employe.getPoste());
            st.setDouble(10, employe.getSalaire());
            st.setInt(11, employe.getJours_conge_annuel());
            st.setInt(12, employe.getId_departement());
            rows = st.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rows;
    }

    @Override
    public int updateEmploye(Employe employe) {

        LocalDate dateNaissance = LocalDate.parse(employe.getDate_naissance());
        LocalDate dateRecrutement = LocalDate.parse(employe.getDate_recrutement()) ;

        String sql = "UPDATE employe SET nom=?, prenom=?, cin=?, adresseEmail=?, telephone=?, adresse=?, " +
                     "date_naissance=?, date_recrutement=?, poste=?, salaire=?, jours_conge_annuel=? , id_departement=?" +
                     " WHERE id_employe=?";

        int rows = 0;

        System.out.println(sql);
        try (PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, employe.getNom());
            st.setString(2, employe.getPrenom());
            st.setString(3, employe.getCin());
            st.setString(4, employe.getEmail());
            st.setString(5, employe.getTelephone());
            st.setString(6, employe.getAdresse());
            st.setDate(7, java.sql.Date.valueOf(dateNaissance));
            st.setDate(8, java.sql.Date.valueOf(dateRecrutement));
            st.setString(9, employe.getPoste());
            st.setDouble(10, employe.getSalaire());
            st.setInt(11, employe.getJours_conge_annuel());
            st.setInt(12, employe.getId_departement());
            st.setInt(13, employe.getId_employe()) ;

            rows = st.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rows;
    }

    @Override
    public int deleteEmploye(int id) {
        String sql = "DELETE FROM employe WHERE id_employe = ?";
        int rows = 0;
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, id);
            rows = st.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rows;
    }

    @Override
    public Employe findEmployeById(int id) {
        String sql = "SELECT * FROM employe WHERE id_employe = ?";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return mapResultSetToEmploye(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Employe> findAllEmployes() {
        List<Employe> list = new ArrayList<>();
        String sql = "SELECT * FROM employe";
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapResultSetToEmploye(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public Employe findEmployeByNom(String nom) {

        Employe emp = null ;
        String sql = "SELECT * FROM employe WHERE nom = ?";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, nom);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                emp = mapResultSetToEmploye(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return emp;

    }

    private Employe mapResultSetToEmploye(ResultSet rs) throws SQLException {

        return new Employe(
                rs.getInt("id_employe"),
                rs.getString("nom"),
                rs.getString("prenom"),
                rs.getString("cin"),
                rs.getString("adresseEmail"),
                rs.getString("telephone"),
                rs.getString("adresse"),
                rs.getDate("date_naissance").toString(),
                rs.getDate("date_recrutement").toString(),
                rs.getString("poste"),
                rs.getDouble("salaire"),
                rs.getInt("jours_conge_annuel"),
                rs.getInt("id_departement")
        );
    }

    public int countAllEmployes(){
        int count  = 0 ;
        String sql = "select count(*) from employe" ;
        try(PreparedStatement st = con.prepareStatement(sql)){
            ResultSet rs =  st.executeQuery() ;
            if(rs.next()){
                count = rs.getInt(1) ;
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return count ;
    }
}
