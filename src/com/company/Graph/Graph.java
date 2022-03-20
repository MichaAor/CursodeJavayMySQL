package com.company.Graph;

import com.company.databaseManagement.ConnectionDB;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Graph extends JFrame {
    ConnectionDB connectionDB = ConnectionDB.getInstance();
    private JPanel panel1;
    private JTextField txt_name;
    private JTextField txt_surname;
    private JTextField txt_phone;
    private JTextField txt_email;
    private JComboBox combo_profession;
    private JButton btn_register;
    private JButton btn_refresh;
    private JTextArea information;
    private JButton btn_consult;
    private JButton btn_null;
    private JButton btn_Modify;
    private JButton btn_delete;
    private JTextField txt_search;
    private JButton btn_search;


    public Graph(){
        super("Ejemplo");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panel1);
        this.setLocationRelativeTo(null);
        this.setTitle("Register to BD");
        this.pack();
        registerBTN();      refreshBTN();       nullbdBTN();        consultBTN();   deleteBTN();    modifyBTN();    searchBTN();
    }

    public void registerBTN(){
        btn_register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try{

                    Connection connection = connectionDB.connect();
                    PreparedStatement insert = connection.prepareStatement("insert into employee values(?,?,?,?,?,?)");
                    insert.setString(1,"0");
                    insert.setString(2,txt_name.getText().trim());
                    insert.setString(3,txt_surname.getText().trim());
                    insert.setString(4,txt_phone.getText().trim());
                    insert.setString(5,txt_email.getText().trim());
                    insert.setString(6,combo_profession.getSelectedItem().toString());
                    insert.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Data was inserted!!");

                    connectionDB.disconect();
                }catch(Exception e){
                    JOptionPane.showMessageDialog(null,"Error: " + e);
                }
            }
        });
    }

    public void refreshBTN(){
        btn_refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                txt_name.setText("");
                txt_surname.setText("");
                txt_phone.setText("");
                txt_email.setText("");
            }
        });
    }

    public void nullbdBTN(){
        btn_null.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    Connection connection = connectionDB.connect();
                    PreparedStatement select = connection.prepareStatement("select * from employee");
                    ResultSet consult = select.executeQuery();
                    if(consult.next()){
                        JOptionPane.showMessageDialog(null,"Not Null BD");
                    }else{
                        JOptionPane.showMessageDialog(null,"Null BD");
                    }
                    connectionDB.disconect();
                }catch (Exception e){
                    JOptionPane.showMessageDialog(null,"Error: " + e);
                }

            }
        });
    }

    public void consultBTN(){
        btn_consult.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                information.setText("");
                try {
                    Connection connection = connectionDB.connect();
                    PreparedStatement select = connection.prepareStatement("select * from employee");
                    ResultSet consult = select.executeQuery();
                    while(consult.next()){
                        information.append(consult.getString(1));
                        information.append("        ");
                        information.append(consult.getString(2));
                        information.append("        ");
                        information.append(consult.getString(3));
                        information.append("        ");
                        information.append(consult.getString(4));
                        information.append("        ");
                        information.append(consult.getString(5));
                        information.append("        ");
                        information.append(consult.getString(6));
                        information.append("\n");
                    }
                    connectionDB.disconect();
                }catch (Exception e){
                    JOptionPane.showMessageDialog(null,"Error: " + e);
                }
            }
        });
    }

    public void deleteBTN(){
        btn_delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    Connection connection = connectionDB.connect();

                    PreparedStatement delete = connection.prepareStatement("delete from employee where id = ? ");
                    delete.setString(1,txt_search.getText().trim());
                    delete.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Record successfully removed");

                    connectionDB.disconect();
                }catch (Exception e){
                    JOptionPane.showMessageDialog(null,"Error: " + e);
                }
            }
        });
    }

    public void modifyBTN(){
        btn_Modify.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    Connection connection = connectionDB.connect();

                    String ID = txt_search.getText().trim();
                    PreparedStatement modify = connection.prepareStatement("update employee set name = ?, surname = ?, phone= ?, email= ?, profession= ? where id= " + ID);
                    modify.setString(1,txt_name.getText().trim());
                    modify.setString(2,txt_surname.getText().trim());
                    modify.setString(3,txt_phone.getText().trim());
                    modify.setString(4,txt_email.getText().trim());
                    modify.setString(5,combo_profession.getSelectedItem().toString());
                    modify.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Registry Modified Successfully");

                    connectionDB.disconect();
                }catch (Exception e){
                    JOptionPane.showMessageDialog(null,"Error: " + e);
                }
            }
        });
    }

    public void searchBTN(){
        btn_search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    Connection connection = connectionDB.connect();

                    PreparedStatement search = connection.prepareStatement("select * from employee where id = ?");
                    search.setString(1,txt_search.getText().trim());
                    ResultSet consult = search.executeQuery();
                    while(consult.next()){
                        txt_name.setText(consult.getString("name"));
                        txt_surname.setText(consult.getString("surname"));
                        txt_phone.setText(consult.getString("phone"));
                        txt_email.setText(consult.getString("email"));
                        combo_profession.setSelectedItem(consult.getString("profession"));
                    }

                    JOptionPane.showMessageDialog(null,"The data was found");

                    connectionDB.disconect();
                }catch (Exception e){
                    JOptionPane.showMessageDialog(null,"Error: " + e);
                }
            }
        });
    }

}
