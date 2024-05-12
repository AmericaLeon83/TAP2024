package com.example.demo.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EmpleadoDAO {
    private int id;
    private String nomEmpleado;
    private String apellidoMaternoE;
    private String apellidoPaternoE;
    private String RFCEmpleado;
    private float salario;
    private String telefono;
    private String direccion;
    private int id_usuarios;
    private Statement stmt;

    // Getters y setters para cada uno de los atributos
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNomEmpleado() { return nomEmpleado; }
    public void setNomEmpleado(String nomEmpleado) { this.nomEmpleado = nomEmpleado; }
    public String getApellidoMaternoE() { return apellidoMaternoE; }
    public void setApellidoMaternoE(String apellidoMaternoE) { this.apellidoMaternoE = apellidoMaternoE; }
    public String getApellidoPaternoE() { return apellidoPaternoE; }
    public void setApellidoPaternoE(String apellidoPaternoE) { this.apellidoPaternoE = apellidoPaternoE; }
    public String getRFCEmpleado() { return RFCEmpleado; }
    public void setRFCEmpleado(String RFCEmpleado) { this.RFCEmpleado = RFCEmpleado; }
    public float getSalario() { return salario; }
    public void setSalario(float salario) { this.salario = salario; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public int getId_usuarios() { return id_usuarios; }
    public void setId_usuarios(int id_usuarios) { this.id_usuarios = id_usuarios; }

    public void INSERTAR(){
        String query = "INSERT INTO empleado (nomEmpleado, apellidoMaternoE, apellidoPaternoE, RFCEmpleado, salario, telefono, direccion, id_usuarios) " +
                "VALUES('"+this.nomEmpleado+"','"+this.apellidoMaternoE+"','"+this.apellidoPaternoE+"','"+this.RFCEmpleado+"',"+this.salario+",'"+this.telefono+"','"+this.direccion+"',"+this.id_usuarios+")";
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                this.id = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void ACTUALIZAR(){
        String query = "UPDATE empleado SET nomEmpleado='"+this.nomEmpleado+"', apellidoMaternoE='"+this.apellidoMaternoE+"', apellidoPaternoE='"+this.apellidoPaternoE+"', RFCEmpleado='"+this.RFCEmpleado+"', salario="+this.salario+", telefono='"+this.telefono+"', direccion='"+this.direccion+"', id_usuarios="+this.id_usuarios+" WHERE id = "+this.id;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void ELIMINAR(){
        String query = "DELETE FROM empleado WHERE id = "+this.id;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<EmpleadoDAO> SELECCIONAR(){
        ObservableList<EmpleadoDAO> listaEmpleados = FXCollections.observableArrayList();
        EmpleadoDAO objEmpleado;
        String query = "SELECT * FROM empleado ORDER BY nomEmpleado";
        try {
            stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while( res.next() ){
                objEmpleado = new EmpleadoDAO();
                objEmpleado.setId(res.getInt("id"));
                objEmpleado.setNomEmpleado(res.getString("nomEmpleado"));
                objEmpleado.setApellidoMaternoE(res.getString("apellidoMaternoE"));
                objEmpleado.setApellidoPaternoE(res.getString("apellidoPaternoE"));
                objEmpleado.setRFCEmpleado(res.getString("RFCEmpleado"));
                objEmpleado.setSalario(res.getFloat("salario"));
                objEmpleado.setTelefono(res.getString("telefono"));
                objEmpleado.setDireccion(res.getString("direccion"));
                objEmpleado.setId_usuarios(res.getInt("id_usuarios"));
                listaEmpleados.add(objEmpleado);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaEmpleados;
    }

    public void seleccionarPorId() {
        // Aquí iría el código para seleccionar un empleado por su id
    }
}