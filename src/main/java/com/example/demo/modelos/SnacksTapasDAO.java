package com.example.demo.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SnacksTapasDAO {

    private int idSnackTapas;
    private String nombreSnackTapas;
    private String descripcion;
    private double precio;
    private Statement stmt;

    public int getIdSnackTapas() { return idSnackTapas; }

    public void setIdSnackTapas(int idSnackTapas) { this.idSnackTapas = idSnackTapas; }

    public String getNombreSnackTapas() { return nombreSnackTapas; }

    public void setNombreSnackTapas(String nombreSnackTapas) { this.nombreSnackTapas = nombreSnackTapas; }

    public String getDescripcion() { return descripcion; }

    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public double getPrecio() { return precio; }

    public void setPrecio(double precio) { this.precio = precio; }

    public void insertar(){
        String query = "INSERT INTO snacks_tapas (nombreSnackTapas, descripcion, precio) " +
                "VALUES('"+this.nombreSnackTapas+"','"+this.descripcion+"',"+this.precio+")";
        try {
            Statement stmt = Conexion.connection.createStatement();
            // Ejecutar la consulta de inserción
            stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            // Obtener el ID generado para el nuevo snack o tapa
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                this.idSnackTapas = rs.getInt(1); // Obtener el ID generado
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizar(){
        String query = "UPDATE snacks_tapas SET nombreSnackTapas='"+this.nombreSnackTapas+"', descripcion='"+this.descripcion+"'," +
                "precio="+this.precio+" WHERE idSnackTapas = "+this.idSnackTapas;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminar(){
        String query = "DELETE FROM snacks_tapas WHERE idSnackTapas = "+this.idSnackTapas;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<SnacksTapasDAO> seleccionar(){

        ObservableList<SnacksTapasDAO> listaSnacksTapas = FXCollections.observableArrayList();
        SnacksTapasDAO objSnackTapas;
        String query = "SELECT * FROM snacks_tapas ORDER BY nombreSnackTapas";
        try {
            stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while( res.next() ){
                objSnackTapas = new SnacksTapasDAO();
                objSnackTapas.setIdSnackTapas(res.getInt("idSnackTapas"));
                objSnackTapas.setNombreSnackTapas(res.getString("nombreSnackTapas"));
                objSnackTapas.setDescripcion(res.getString("descripcion"));
                objSnackTapas.setPrecio(res.getDouble("precio"));
                listaSnacksTapas.add(objSnackTapas);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaSnacksTapas;
    }

    public void seleccionarPorId() {
        // Aquí iría el código para seleccionar un snack o tapa por su id
    }
}
