package com.example.demo.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.Statement;

public class EmpleadosDAO {
    private int  idEmpleado;
    private String nomEmpleado;
    private String RFCEmpleado;
    private float salario;
    private String telefono;
    private String direccion;

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getNomEmpleado() {
        return nomEmpleado;
    }

    public String getRFCEmpleado() {
        return RFCEmpleado;
    }

    public void setRFCEmpleado(String RFCEmpleado) {
        this.RFCEmpleado = RFCEmpleado;
    }

    public void setNomEmpleado(String nomEmpleado) {
        this.nomEmpleado = nomEmpleado;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public float getSalario() {
        return salario;
    }

    public void setSalario(float salario) {
        this.salario = salario;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void INSERTAR(){
        String query ="INSERT INTO Empleado(nomEmpleado," +
                "RFCEmpleado,salario,telefono,direccion) " +
                "VALUES('"+nomEmpleado+"','"+ RFCEmpleado +"'" +
                ","+salario+",'"+telefono+"','"+direccion+"')";

        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    public void ACTUALIZAR(){
        String query = "UPDATE Empleado SET nomEmpleado='"+nomEmpleado+"'," +
                "RCFEmpleado='"+ RFCEmpleado +"',salario="+salario+"," +
                "telefono='"+telefono+"',direccion='"+direccion+"' " +
                "WHERE idEmpledo ="+idEmpleado;
        try{
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void ELIMINAR(){
        String query = "DELETE FROM Empleado WHERE idEmpleado="+idEmpleado;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public ObservableList<EmpleadosDAO> CONSULTAR(){
        ObservableList<EmpleadosDAO> listaEmp = FXCollections.observableArrayList();
        String query ="SELECT * FROM Empleado";
        try{
            EmpleadosDAO objEmp;
            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while (res.next()){
                objEmp = new EmpleadosDAO();
                objEmp.idEmpleado =res.getInt("idEmpleado");
                objEmp.nomEmpleado = res.getString("nomEmpleado");
                objEmp.RFCEmpleado = res.getString("RFCEmpleado");
                objEmp.salario = res.getFloat("salario");
                objEmp.telefono = res.getString("telefono");
                objEmp.direccion = res.getString("direccion");
                listaEmp.add(objEmp);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return listaEmp;
    }
}