package com.grh.dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.grh.model.Conge;
import com.grh.util.DBConnection ;
public class CongeDaoImp implements CongeDao {
    private Connection con ;
    public CongeDaoImp(){
        con = DBConnection.getConnection() ;
    }

    @Override
    public int countAllCongesEnAttente(){
        int count  = 0 ;
        String sql = "select count(*) as total_conges from conge where statut ='en_attente' " ;
        try(PreparedStatement ps = con.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery() ;
            if(rs.next()){
                count = rs.getInt(1) ;
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return count  ;
    }
    @Override
    public List<Conge> getAllCongesEnCours(){
        List<Conge> conges = new ArrayList<Conge>() ;

        String sql = "select * from conge where statut = 'en_cours' " ;
        try(PreparedStatement st = con.prepareStatement(sql)){
            ResultSet rs = st.executeQuery() ;
            while(rs.next())   {
                Conge conge = new Conge(
                        rs.getInt(1) ,
                        rs.getString(2) ,
                        LocalDate.parse(rs.getString(3)) ,
                        LocalDate.parse(rs.getString(4)) ,
                        rs.getString(5),
                        rs.getString(6),
                        rs.getInt(7)
                ) ;
                conges.add(conge) ;
            }

        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return  conges ;
    }

    @Override
    public int archiverConge(Conge conge) {

        int count = 0;

        String sql = """
        INSERT INTO archiver_conge
        (id_conge, type_conge, date_debut, date_fin, statut, motif, id_employe)
        VALUES (?, ?, ?, ?, ?, ?, ?)
    """;

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, conge.getId_conge());
            ps.setString(2, conge.getType_conge());
            ps.setDate(3, Date.valueOf(conge.getDate_debut()));

            ps.setDate(4, Date.valueOf(conge.getDate_fin()));

            ps.setString(5, conge.getStatut());
            ps.setString(6, conge.getMotif());

            // IMPORTANT : id_employe de la table ARCHIVAGE
            ps.setInt(7, conge.getId_employe());

            count = ps.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return count;
    }

    public int supprimerCongesEnAttente(int idEmploye) {
        int count = 0;
        String sql = "DELETE FROM conge WHERE id_employe = ? AND statut = 'en_attente'";

        try (
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, idEmploye);
            count = stmt.executeUpdate(); // retourne le nombre de lignes supprimées

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return count;

    }

    @Override
    public boolean ajouterConge(Conge conge) {
        String sql = "INSERT INTO conge (type_conge, date_debut, date_fin, statut, motif, id_employe) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, conge.getType_conge());
            ps.setDate(2, Date.valueOf(conge.getDate_debut()));
            ps.setDate(3, Date.valueOf(conge.getDate_fin()));
            ps.setString(4, "En attente"); // Statut par défaut
            ps.setString(5, conge.getMotif());
            ps.setInt(6, conge.getId_employe());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean validerConge(int idConge) {
        String sql = "UPDATE conge SET statut = 'En cours' WHERE id_conge = ?";
        try (
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idConge);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean refuserConge(int idConge) {
        String sql = "DELETE FROM conge WHERE id_conge = ?";
        try (
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idConge);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean modifierConge(Conge conge) {
        String sql = "UPDATE conge SET type_conge = ?, date_debut = ?, date_fin = ?, motif = ? WHERE id_conge = ?";
        try (
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, conge.getType_conge());
            ps.setDate(2, Date.valueOf(conge.getDate_debut()));
            ps.setDate(3, Date.valueOf(conge.getDate_fin()));
            ps.setString(4, conge.getMotif());
            ps.setInt(5, conge.getId_conge());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Conge> rechercherCongeParNomEmploye(String nom) {
        List<Conge> result = new ArrayList<>();
        String sql = "SELECT c.id_conge, c.type_conge, c.date_debut, c.date_fin, c.statut, c.motif, c.id_employe " +
                "FROM conge c " +
                "JOIN employe e ON c.id_employe = e.id_employe " +
                "WHERE e.nom = ?";

        try (
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nom);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Conge c = new Conge(
                        rs.getInt("id_conge"),
                        rs.getString("type_conge"),
                        rs.getDate("date_debut").toLocalDate(),
                        rs.getDate("date_fin").toLocalDate(),
                        rs.getString("statut"),
                        rs.getString("motif"),
                        rs.getInt("id_employe"));
                result.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Conge> listerConges() {
        List<Conge> result = new ArrayList<>();
        String sql = "SELECT * FROM conge";
        try (
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Conge c = new Conge(
                        rs.getInt("id_conge"),
                        rs.getString("type_conge"),
                        rs.getDate("date_debut").toLocalDate(),
                        rs.getDate("date_fin").toLocalDate(),
                        rs.getString("statut"),
                        rs.getString("motif"),
                        rs.getInt("id_employe"));
                result.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }





}
