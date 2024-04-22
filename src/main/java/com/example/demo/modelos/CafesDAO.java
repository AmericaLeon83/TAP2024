package com.example.demo.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CafesDAO {

    private int idCafe;
    private String nombreCafe;
    private String descripcion;
    private double precio;
    private Statement stmt;

    public int getIdCafe() { return idCafe; }

    public void setIdCafe(int idCafe) { this.idCafe = idCafe; }

    public String getNombreCafe() { return nombreCafe; }

    public void setNombreCafe(String nombreCafe) { this.nombreCafe = nombreCafe; }

    public String getDescripcion() { return descripcion; }

    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public double getPrecio() { return precio; }

    public void setPrecio(double precio) { this.precio = precio; }

    public void insertar(){
        String query = "INSERT INTO cafes (nombreCafe, descripcion, precio) " +
                "VALUES('"+this.nombreCafe+"','"+this.descripcion+"',"+this.precio+")";
        try {
            Statement stmt = Conexion.connection.createStatement();
            // Ejecutar la consulta de inserción
            stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            // Obtener el ID generado para el nuevo café
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                this.idCafe = rs.getInt(1); // Obtener el ID generado
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizar(){
        String query = "UPDATE cafes SET nombreCafe='"+this.nombreCafe+"', descripcion='"+this.descripcion+"'," +
                "precio="+this.precio+" WHERE idCafe = "+this.idCafe;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminar(){
        String query = "DELETE FROM cafes WHERE idCafe = "+this.idCafe;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<CafesDAO> seleccionar(){

        ObservableList<CafesDAO> listaCafes = FXCollections.observableArrayList();
        CafesDAO objCafe;
        String query = "SELECT * FROM cafes ORDER BY nombreCafe";
        try {
            stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while( res.next() ){
                objCafe = new CafesDAO();
                objCafe.setIdCafe(res.getInt("idCafe"));
                objCafe.setNombreCafe(res.getString("nombreCafe"));
                objCafe.setDescripcion(res.getString("descripcion"));
                objCafe.setPrecio(res.getDouble("precio"));
                listaCafes.add(objCafe);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaCafes;
    }

    public void seleccionarPorId() {
        // Aquí iría el código para seleccionar un café por su id
    }
}
