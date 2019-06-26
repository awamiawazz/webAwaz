package com.Beans;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MapDao {

    List<Report> listt;

    public MapDao() {

        listt=new ArrayList<Report>();
    }

    public List<Report> getReports() throws SQLException {
        Connection c = null;
        listt=new ArrayList<Report>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/mymap","root","");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }


        PreparedStatement pstmt = c.prepareStatement("select * from markers");
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            Report report = new Report();

            listt.add(report);
        }
        System.out.println("list size is : "+listt.size());
        for (int i=0;i<listt.size();i++)
        {
        }
        return listt;
    }
}
