package com.example.demo.modelos;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DetallesOrdenDAO {

    private int idDetalleOrden;
    private int idOrden;
    private int idPlatillo;
    private int idBebida;
    private int cantidadPlatillo;
    private int cantidadBebida;
    private Statement stmt;

    public int getIdDetalleOrden() { return idDetalleOrden; }

    public void setIdDetalleOrden(int idDetalleOrden) { this.idDetalleOrden = idDetalleOrden; }

    public int getIdOrden() { return idOrden; }

    public void setIdOrden(int idOrden) { this.idOrden = idOrden; }

    public int getIdPlatillo() { return idPlatillo; }

    public void setIdPlatillo(int idPlatillo) { this.idPlatillo = idPlatillo; }

    public int getIdBebida() { return idBebida; }

    public void setIdBebida(int idBebida) { this.idBebida = idBebida; }

    public int getCantidadPlatillo() { return cantidadPlatillo; }

    public void setCantidadPlatillo(int cantidadPlatillo) { this.cantidadPlatillo = cantidadPlatillo; }

    public int getCantidadBebida() { return cantidadBebida; }

    public void setCantidadBebida(int cantidadBebida) { this.cantidadBebida = cantidadBebida; }

    public void insertar() {
        String query = "INSERT INTO detalles_orden (idOrden, idPlatillo, idBebida, cantidadPlatillo, cantidadBebida) " +
                "VALUES('" + this.idOrden + "','" + this.idPlatillo + "','" + this.idBebida + "','" + this.cantidadPlatillo + "','" + this.cantidadBebida + "')";
        try{
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void actualizar() {
        String query = "UPDATE detalles_orden SET idOrden='" + this.idOrden + "', idPlatillo='" + this.idPlatillo + "', idBebida='" + this.idBebida + "'," +
                "cantidadPlatillo='" + this.cantidadPlatillo + "', cantidadBebida='" + this.cantidadBebida + "' WHERE idDetalleOrden = " + this.idDetalleOrden;
        try{
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void eliminar() {
        String query = "DELETE FROM detalles_orden WHERE idDetalleOrden = " + this.idDetalleOrden;
        try{
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public ObservableList<DetallesOrdenDAO> seleccionar() {

        ObservableList<DetallesOrdenDAO> listaDetalles = FXCollections.observableArrayList();
        DetallesOrdenDAO detalle;
        String query = "SELECT * FROM detalles_orden";

        try {
            stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while( res.next() ){
                detalle = new DetallesOrdenDAO();
                detalle.setIdDetalleOrden(res.getInt("idDetalleOrden"));
                detalle.setIdOrden(res.getInt("idOrden"));
                detalle.setIdPlatillo(res.getInt("idPlatillo"));
                detalle.setIdBebida(res.getInt("idBebida"));
                detalle.setCantidadPlatillo(res.getInt("cantidadPlatillo"));
                detalle.setCantidadBebida(res.getInt("cantidadBebida"));
                listaDetalles.add(detalle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaDetalles;
    }

    public void seleccionarPorId() {
        // Aquí iría el código para seleccionar un detalle de orden por su id
    }
}
