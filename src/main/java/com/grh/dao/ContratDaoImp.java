package com.grh.dao;

import com.grh.model.Contrat;
import com.grh.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContratDaoImp implements ContratDao {

    private Connection con;

    public ContratDaoImp() {
        con = DBConnection.getConnection();
    }

    @Override
    public int addContrat(Contrat contrat) {
        String sql = "INSERT INTO contrat (type_contrat, date_debut, date_fin, salaire_base, id_employe) " +
                "VALUES (?, ?, ?, ?, ?)";
        int rows = 0;
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, contrat.getType_contrat());
            st.setDate(2, Date.valueOf(contrat.getDate_debut()));
            st.setDate(3, contrat.getDate_fin() != null ? Date.valueOf(contrat.getDate_fin()) : null);
            st.setDouble(4, contrat.getSalaire_base());
            st.setInt(5, contrat.getId_employe());
            rows = st.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rows;
    }

    @Override
    public int updateContrat(Contrat contrat) {
        String sql = "UPDATE contrat SET type_contrat=?, date_debut=?, date_fin=?, " +
                "salaire_base=?, id_employe=? WHERE id_contrat=?";
        int rows = 0;
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, contrat.getType_contrat());
            st.setDate(2, Date.valueOf(contrat.getDate_debut()));
            st.setDate(3, contrat.getDate_fin() != null ? Date.valueOf(contrat.getDate_fin()) : null);
            st.setDouble(4, contrat.getSalaire_base());
            st.setInt(5, contrat.getId_employe());
            st.setInt(6, contrat.getId_contrat());
            rows = st.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rows;
    }

    @Override
    public int deleteContrat(int id) {
        String sql = "DELETE FROM contrat WHERE id_contrat = ?";
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
    public Contrat findContratById(int id) {
        String sql = "SELECT * FROM contrat WHERE id_contrat = ?";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return mapResultSetToContrat(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Contrat> findAllContrats() {
        List<Contrat> list = new ArrayList<>();
        String sql = "SELECT * FROM contrat";
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapResultSetToContrat(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Contrat> findContratsByEmployeId(int idEmploye) {
        List<Contrat> list = new ArrayList<>();
        String sql = "SELECT * FROM contrat WHERE id_employe = ?";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, idEmploye);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                list.add(mapResultSetToContrat(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    private Contrat mapResultSetToContrat(ResultSet rs) throws SQLException {
        return new Contrat(
                rs.getInt("id_contrat"),
                rs.getString("type_contrat"),
                rs.getDate("date_debut").toLocalDate(),
                rs.getDate("date_fin") != null ? rs.getDate("date_fin").toLocalDate() : null,
                rs.getDouble("salaire_base"),
                rs.getInt("id_employe")
        );
    }
}