package com.dbconnection;

import com.movilidad.utils.Util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author stevenlizarazo
 */
public class Common {

    public static int inited = 0;

    private static final String host = "10.0.3.34";
    private static final String db = "rgmo";
    private static final String pwd = ".*R1g3l2021GMO//*";
    private static final String user = "green_usr";

    public static void initDriver() {
        if (inited == 1) {
            return;
        }
        inited = 1;
        try {
            // The newInstance() call is a work around for some
            // broken Java implementations
            //MySQL5
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            //MySQL8
//            Class.forName("com.mysql.cj.jdbc.MysqlDataSource").newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            // handle the error
            ex.printStackTrace();
        }
    }

    public static Connection getConnection() throws Exception {
//        System.out.println("|"+Util.getProperty2("host"));
        initDriver();
//        return DriverManager.getConnection("jdbc:mysql://" + Util.getProperty("host") + ":3306/" + Util.getProperty("db") + "?autoReconnect=true"
//                + "&user=" + Util.getProperty("usr") + "&password=" + Util.getProperty("pass"));
        return DriverManager.getConnection("jdbc:mysql://" + host + ":3306/" + db + "?autoReconnect=true"
                + "&user=" + user + "&password=" + pwd+ "&useSSL=false");
    }

    public static ResultSet openrs(Statement stat, String sql) throws SQLException {
        ResultSet rs = stat.executeQuery(sql);
        return rs;
    }

    public static ResultSet openrs(PreparedStatement pstat, String sql) throws SQLException {
        ResultSet rs = pstat.executeQuery(sql);
        return rs;
    }

    public static boolean Execute(String sql) throws Exception {
        boolean res = false;
        Connection c = null;
        Statement t = null;
        ResultSet r = null;
        try {
            c = getConnection();
            t = c.createStatement();
            res = t.execute(sql);
        } finally {
            if (r != null) {
                r.close();
            }
            if (t != null) {
                t.close();
            }
            if (c != null) {
                c.close();
            }
        }
        return res;
    }

    public static String lookUp(String table, String fName, String where) throws Exception {
        Connection conn = null;
        Statement stat = null;
        String w = "" + where;
        if (!"".equals(where)) {
            w = " WHERE " + w;
        }
        try {
//            conn = getConnectionMargarita();
            conn = getConnection();
            stat = conn.createStatement();
            ResultSet rsLookUp = null;
            try {
                rsLookUp = openrs(stat, "SELECT " + fName + " FROM " + table + " " + w);
                if (!rsLookUp.next()) {
                    rsLookUp.close();
                    stat.close();
                    //  conn.close();
                    return "";
                }
            } catch (Exception r) {
            }
            String res = null;
            if (rsLookUp != null) {
                res = (rsLookUp.getString(1));

                rsLookUp.close();
            }
            stat.close();
            //conn.close();
            return (res == null ? "123" : res);
        } catch (Exception e) {
            try {
            } catch (Exception t) {
            }
            // System.out.println("Error lookUp " + e.getMessage());
            //e.printStackTrace();
            e.printStackTrace();
//            System.out.println("query intentado " + "SELECT " + fName + " FROM " + table + " WHERE " + where);
            return "";
        } finally {
            if (stat != null) {
                stat.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }
}
