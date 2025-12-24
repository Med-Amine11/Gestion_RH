package com.grh.util;

import java.sql.Connection ;
import com.grh.util.DBConnection ;

public class Main {
    public static void main(String[] Args){
        Connection con = DBConnection.getConnection() ;
        DBConnection.closeConnection();
    }


}