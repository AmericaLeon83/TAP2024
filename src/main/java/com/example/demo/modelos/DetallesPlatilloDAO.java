package com.example.demo.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DetallesPlatilloDAO {

    private int idDetallePlatillo;
    private int idPlatillo;
    private String nombrePlatillo;
    private double costo;
    private String tamanio;
    private double precio;
    private Statement stmt;

    public int getIdDetallePlatillo() { return idDetallePlatillo; }

    public void setIdDetallePlatillo(int idDetallePlatillo) { this.idDetallePlatillo = idDetallePlatillo; }

    public int getIdPlatillo() { return idPlatillo; }

    public void setIdPlatillo(int idPlatillo) { this.idPlatillo = idPlatillo; }

    public String getNombrePlatillo() { return nombrePlatillo; }

    public void setNombrePlatillo(String nombrePlatillo) { this.nombrePlatillo = nombrePlatillo; }

    public double getCosto() { return costo; }

    public void setCosto(double costo) { this.costo = costo; }

    public String getTamanio() { return tamanio; }

    public void setTamanio(String tamanio) { this.tamanio = tamanio; }

    public double getPrecio() { return precio; }

    public void setPrecio(double precio) { this.precio = precio; }

    public void insertar(){
        String query = "INSERT INTO detalles_platillo (idPlatillo, nombrePlatillo, costo, tamanio, precio) " +
                "VALUES("+this.idPlatillo+",'"+this.nombrePlatillo+"',"+this.costo+",'"+this.tamanio+"',"+this.precio+")";
        try{
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void actualizar(){
        String query = "UPDATE detalles_platillo SET idPlatillo="+this.idPlatillo+", nombrePlatillo='"+this.nombrePlatillo+"', " +
                "costo="+this.costo+", tamanio='"+this.tamanio+"', precio="+this.precio+" WHERE idDetallePlatillo = "+this.idDetallePlatillo;
        try{
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void eliminar(){
        String query = "DELETE FROM detalles_platillo WHERE idDetallePlatillo = "+this.idDetallePlatillo;
        try{
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public ObservableList<DetallesPlatilloDAO> seleccionar(){

        ObservableList<DetallesPlatilloDAO> listaDetalles = FXCollections.observableArrayList();
        DetallesPlatilloDAO objDetalle;
        String query = "SELECT * FROM detalles_platillo";
        try {
            stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while( res.next() ){
                objDetalle = new DetallesPlatilloDAO();
                objDetalle.setIdDetallePlatillo(res.getInt("idDetallePlatillo"));
                objDetalle.setIdPlatillo(res.getInt("idPlatillo"));
                objDetalle.setNombrePlatillo(res.getString("nombrePlatillo"));
                objDetalle.setCosto(res.getDouble("costo"));
                objDetalle.setTamanio(res.getString("tamanio"));
                objDetalle.setPrecio(res.getDouble("precio"));
                listaDetalles.add(objDetalle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaDetalles;
    }

    public void seleccionarPorId(int id) {
        // Aquí iría el código para seleccionar un detalle de platillo por su id
    }
}
