package com.example.demo.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.Statement;

public class EmpleadosDAO {
    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public void setNomEmpleado(String nomEmpleado) {
        this.nomEmpleado = nomEmpleado;
    }

    public void setRfcEmpleado(String rfcEmpleado) {
        this.rfcEmpleado = rfcEmpleado;
    }

    public void setSalario(float salario) {
        this.salario = salario;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    private int idEmpleado;
    private String nomEmpleado;
    private String rfcEmpleado;
    private float salario;
    private String telefono;

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public String getNomEmpleado() {
        return nomEmpleado;
    }

    public String getRfcEmpleado() {
        return rfcEmpleado;
    }

    public float getSalario() {
        return salario;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    private String direccion;

    public void INSERTAR() {
        String query = "INSERT INTO empleado(nomEmpleado, rfcEmpleado, salario, direccion, telefono" +
                ") values('" + nomEmpleado + "','" + rfcEmpleado + "'," + salario + ",'" + direccion  + "','" + telefono + "')";
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public void ACTUALIZAR(){
        String query = "UPDATE empleado set nomEmpleado='"+nomEmpleado+"',"+
                "rfcEmpleado='"+rfcEmpleado+"',salario="+salario+"," +
                "direccion='"+direccion+"',telefono='"+telefono+"'" +
                "where idEmpleado="+idEmpleado;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public void ELIMINAR(){
        String query ="DELETE FROM empleado where idEmpleado ="+idEmpleado;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public ObservableList<EmpleadosDAO>  CONSULTAR() {
        ObservableList<EmpleadosDAO> listaEmp = FXCollections.observableArrayList();

        String query = "SELECT * FROM empleado";
        try {
            EmpleadosDAO objEmp;
            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while (res.next()){
                objEmp = new EmpleadosDAO();
                objEmp.idEmpleado = res.getInt("idEmpleado");
                objEmp.nomEmpleado = res.getString("nomEmpleado");
                objEmp.rfcEmpleado = res.getString("rfcEmpleado");
                objEmp.salario = res.getFloat("salario");
                objEmp.direccion = res.getString("direccion");
                objEmp.telefono = res.getString("telefono");
                listaEmp.add(objEmp);
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return listaEmp;

    }
}
