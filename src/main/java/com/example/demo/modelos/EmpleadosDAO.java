package com.example.demo.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EmpleadosDAO {
    private int idEmpleado;
    private String nomEmpleado;
    private String apellidoMaternoE;
    private String apellidoPaternoE;
    private String RFCEmpleado;
    private double salario;
    private String telefono;

    private String emailEmpleado;
    private Statement stmt;

    public int getIdEmpleado() {return idEmpleado;}

    public void setIdEmpleado(int idEmpleado) {this.idEmpleado = idEmpleado;}

    public String getNomEmpleado() {return nomEmpleado;}

    public void setNomEmpleado(String nomEmpleado) {this.nomEmpleado = nomEmpleado;}

    public String getApellidoMaternoE() {return apellidoMaternoE;}

    public void setApellidoMaternoE(String apellidoMaternoE) {this.apellidoMaternoE = apellidoMaternoE;}

    public String getApellidoPaternoE() {return apellidoPaternoE;}

    public void setApellidoPaternoE(String apellidoPaternoE) {this.apellidoPaternoE = apellidoPaternoE;}

    public String getRFCEmpleado() {return RFCEmpleado;}

    public void setRFCEmpleado(String RFCEmpleado) {this.RFCEmpleado = RFCEmpleado;}

    public double getSalario() {return salario;}

    public void setSalario(double salario) {this.salario = salario;}

    public String getTelefono() {return telefono;}

    public void setTelefono(String telefono) {this.telefono = telefono;}

    public String getEmailEmpleado() {return emailEmpleado;}

    public void setEmailEmpleado(String emailEmpleado) {this.emailEmpleado = emailEmpleado;}

    public void INSERTAREmpleados(){
        String query = "INSERT INTO empleado2 (nomEmpleado,apellidoMaternoE,apellidoPaternoE,RFCEmpleado,salario,telefono,emailEmpleado)" +
                "VALUES('"+this.nomEmpleado+"','"+this.apellidoMaternoE+"','"+this.apellidoMaternoE+"','"+this.RFCEmpleado+"',"+
                this.salario+",'"+this.telefono+"','"+this.emailEmpleado+"')";
        try{
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void ACTUALIZAREmpleados(){
        String query = "UPDATE empleado2 SET nomEmpleado='"+this.nomEmpleado+"',apellidoMaternoE='"+this.apellidoMaternoE+"'," +
                "apellidoPaternoE='"+this.apellidoPaternoE+"', RFCEmpleado='"+this.RFCEmpleado+"',salario="+this.salario+"," +
                "telefono='"+this.telefono+"',emailEmpleado='"+this.emailEmpleado+"'  " +
                "WHERE idEmpleado = "+this.idEmpleado;
        try{
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void ELIMINAREmpleados(){
        String query = "DELETE FROM empleado2 WHERE idEmpleado = "+this.idEmpleado;
        try{
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public ObservableList<EmpleadosDAO> SELECCIONAREmpleados(){

        ObservableList<EmpleadosDAO> listaEmp = FXCollections.observableArrayList();
        EmpleadosDAO objEmp;
        String query = "SELECT * FROM empleado2 ORDER BY idEmpleado";
        try {
            stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while( res.next() ){
                objEmp = new EmpleadosDAO();
                objEmp.setIdEmpleado(res.getInt("idEmpleado"));
                objEmp.setNomEmpleado(res.getString("nomEmpleado"));
                objEmp.setApellidoMaternoE(res.getString("apellidoMaternoE"));
                objEmp.setApellidoPaternoE(res.getString("apellidoPaternoE"));
                objEmp.setRFCEmpleado(res.getString("RFCEmpleado"));
                objEmp.setSalario(res.getDouble("salario"));
                objEmp.setTelefono(res.getString("telefono"));
                objEmp.setEmailEmpleado(res.getString("emailEmpleado"));
                listaEmp.add(objEmp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaEmp;
    }

    public void seleccionarPorId() {
        // Aquí iría el código para seleccionar un cliente por su id
    }
}
