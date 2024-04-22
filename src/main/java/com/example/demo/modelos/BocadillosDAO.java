package com.example.demo.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BocadillosDAO {

    private int idBocadillo;
    private String nombreBocadillo;
    private String descripcion;
    private double precio;
    private Statement stmt;

    public int getIdBocadillo() { return idBocadillo; }

    public void setIdBocadillo(int idBocadillo) { this.idBocadillo = idBocadillo; }

    public String getNombreBocadillo() { return nombreBocadillo; }

    public void setNombreBocadillo(String nombreBocadillo) { this.nombreBocadillo = nombreBocadillo; }

    public String getDescripcion() { return descripcion; }

    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public double getPrecio() { return precio; }

    public void setPrecio(double precio) { this.precio = precio; }

    public void INSERTAR(){
        String query = "INSERT INTO bocadillos (nombreBocadillo, descripcion, precio) " +
                "VALUES('"+this.nombreBocadillo+"','"+this.descripcion+"',"+this.precio+")";
        try {
            Statement stmt = Conexion.connection.createStatement();
            // Ejecutar la consulta de inserción
            stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            // Obtener el ID generado para el nuevo bocadillo
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                this.idBocadillo = rs.getInt(1); // Obtener el ID generado
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void ACTUALIZAR(){
        String query = "UPDATE bocadillos SET nombreBocadillo='"+this.nombreBocadillo+"', descripcion='"+this.descripcion+"'," +
                "precio="+this.precio+" WHERE idBocadillo = "+this.idBocadillo;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void ELIMINAR(){
        String query = "DELETE FROM bocadillos WHERE idBocadillo = "+this.idBocadillo;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<BocadillosDAO> SELECCIONAR(){

        ObservableList<BocadillosDAO> listaBocadillos = FXCollections.observableArrayList();
        BocadillosDAO objBocadillo;
        String query = "SELECT * FROM bocadillos ORDER BY nombreBocadillo";
        try {
            stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while( res.next() ){
                objBocadillo = new BocadillosDAO();
                objBocadillo.setIdBocadillo(res.getInt("idBocadillo"));
                objBocadillo.setNombreBocadillo(res.getString("nombreBocadillo"));
                objBocadillo.setDescripcion(res.getString("descripcion"));
                objBocadillo.setPrecio(res.getDouble("precio"));
                listaBocadillos.add(objBocadillo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaBocadillos;
    }

    public void seleccionarPorId() {
        // Aquí iría el código para seleccionar un bocadillo por su id
    }
}
