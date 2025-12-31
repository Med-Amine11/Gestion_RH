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

}
