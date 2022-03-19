package com.company.databaseManagement;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
    private static Connection connection;
    private static ConnectionDB instance;

    private ConnectionDB(){
    }

    private static final String URL = "jdbc:mysql://localhost/bd_registros";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public Connection connect(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            //JOptionPane.showMessageDialog(null,"Conexion Exitosa!!");
            return connection;
        }catch (Exception e){
            JOptionPane.showInternalMessageDialog(null,"Error que da: "+ e);
        }
        return connection;
    }

    public void disconect() throws SQLException {
        try {
            connection.close();
            //JOptionPane.showMessageDialog(null,"Se Desconecto de la BDD");

        }catch(Exception e){
            JOptionPane.showInternalMessageDialog(null,"Error que da: "+ e);
            connection.close();
        }finally {
            connection.close();
        }
    }

    public static ConnectionDB getInstance(){
        if(instance == null){
            instance = new ConnectionDB();
        }
        return instance;
    }
}
