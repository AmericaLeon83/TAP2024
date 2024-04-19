package com.example.demo.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import static com.example.demo.Main.*;
// import static sample.Main.*;

public class BebidasDAO {

    private int idBebida;
    private String nombreBebida;
    private String descripcion;
    private double precio;
    private Statement stmt;

    public int getIdBebida() { return idBebida; }

    public void setIdBebida(int idBebida) { this.idBebida = idBebida; }

    public String getNombreBebida() { return nombreBebida; }

    public void setNombreBebida(String nombreBebida) { this.nombreBebida = nombreBebida; }

    public String getDescripcion() { return descripcion; }

    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public double getPrecio() { return precio; }

    public void setPrecio(double precio) { this.precio = precio; }

    public void INSERTAR(){
        String query = "INSERT INTO bebidas (nombreBebida, descripcion, precio) " +
                "VALUES('"+this.nombreBebida+"','"+this.descripcion+"',"+this.precio+")";
        try {
            Statement stmt = Conexion.conexion.createStatement();
            // Ejecutar la consulta de inserción
            stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            // Obtener el ID generado para la nueva bebida
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                this.idBebida = rs.getInt(1); // Obtener el ID generado
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void ACTUALIZAR(){
        String query = "UPDATE bebidas SET nombreBebida='"+this.nombreBebida+"', descripcion='"+this.descripcion+"'," +
                "precio="+this.precio+" WHERE idBebida = "+this.idBebida;
        try {
            Statement stmt = Conexion.conexion.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void ELIMINAR(){
        String query = "DELETE FROM bebidas WHERE idBebida = "+this.idBebida;
        try {
            Statement stmt = Conexion.conexion.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<BebidasDAO> SELECCIONAR(){

        ObservableList<BebidasDAO> listaBebidas = FXCollections.observableArrayList();
        BebidasDAO objBebida;
        String query = "SELECT * FROM bebidas ORDER BY nombreBebida";
        try {
            stmt = Conexion.conexion.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while( res.next() ){
                objBebida = new BebidasDAO();
                objBebida.setIdBebida(res.getInt("idBebida"));
                objBebida.setNombreBebida(res.getString("nombreBebida"));
                objBebida.setDescripcion(res.getString("descripcion"));
                objBebida.setPrecio(res.getDouble("precio"));
                listaBebidas.add(objBebida);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaBebidas;
    }

    public void seleccionarPorId() {
        // Aquí iría el código para seleccionar una bebida por su id
    }
}
