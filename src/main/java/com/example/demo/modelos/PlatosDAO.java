package com.example.demo.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PlatosDAO {

    private int id;
    private String nombre;
    private double precio;
    private String fecha;
    private String categoria;
    private Statement stmt;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getPrecio() { return precio; }

    public void setPrecio(double precio) { this.precio = precio; }

    public String getFecha() { return fecha; }

    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getCategoria() { return categoria; }

    public void setCategoria(String categoria) { this.categoria = categoria; }
    public void INSERTAR(){
        String query = "INSERT INTO platos (nombre, precio, fecha, categoria) " +
                "VALUES('"+this.nombre+"',"+this.precio+", CURRENT_DATE(), '"+this.categoria+"')";
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
        String query = "UPDATE platos SET nombre='"+this.nombre+"', precio="+this.precio+"," +
                "fecha='"+this.fecha+"', categoria='"+this.categoria+"' WHERE id = "+this.id;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void ELIMINAR(){
        String query = "DELETE FROM platos WHERE id = "+this.id;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<PlatosDAO> SELECCIONAR(){

        ObservableList<PlatosDAO> listaPlatos = FXCollections.observableArrayList();
        PlatosDAO objPlato;
        String query = "SELECT * FROM platos ORDER BY nombre";
        try {
            stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while( res.next() ){
                objPlato = new PlatosDAO();
                objPlato.setId(res.getInt("id"));
                objPlato.setNombre(res.getString("nombre"));
                objPlato.setPrecio(res.getDouble("precio"));
                objPlato.setFecha(res.getString("fecha"));
                objPlato.setCategoria(res.getString("categoria"));
                listaPlatos.add(objPlato);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaPlatos;
    }

    public void seleccionarPorId() {
        // Aquí iría el código para seleccionar un plato por su id
    }
}

/*package sample.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.views.IconManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PlatosDAO {

    private int id;
    private String nombre;
    private double precio;
    private String fecha;
    private Statement stmt;
    private String categoria;
    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getPrecio() { return precio; }

    public void setPrecio(double precio) { this.precio = precio; }

    public String getFecha() { return fecha; }

    public void setFecha(String fecha) { this.fecha = fecha; }
    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    public void INSERTAR(){
        String query = "INSERT INTO platos (nombre, precio, fecha, categoria) " +
                "VALUES('"+this.nombre+"',"+this.precio+", CURRENT_DATE(), '"+this.categoria+"')";
        try {
            Statement stmt = Conexion.conexion.createStatement();
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
        String query = "UPDATE platos SET nombre='"+this.nombre+"', precio="+this.precio+"," +
                "fecha='"+this.fecha+"', categoria='"+this.categoria+"' WHERE id = "+this.id;
        try {
            Statement stmt = Conexion.conexion.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
   /* public void INSERTAR(){
        String query = "INSERT INTO platos (nombre, precio, fecha) " +
                "VALUES('"+this.nombre+"',"+this.precio+", CURRENT_DATE())";
        try {
            Statement stmt = Conexion.conexion.createStatement();
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
        String query = "UPDATE platos SET nombre='"+this.nombre+"', precio="+this.precio+"," +
                "fecha='"+this.fecha+"' WHERE id = "+this.id;
        try {
            Statement stmt = Conexion.conexion.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
*/
  /*  public void ELIMINAR(){
        String query = "DELETE FROM platos WHERE id = "+this.id;
        try {
            Statement stmt = Conexion.conexion.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ObservableList<PlatosDAO> SELECCIONAR(){

        ObservableList<PlatosDAO> listaPlatos = FXCollections.observableArrayList();
        PlatosDAO objPlato;
        String query = "SELECT * FROM platos ORDER BY nombre";
        try {
            stmt = Conexion.conexion.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while( res.next() ){
                objPlato = new PlatosDAO();
                objPlato.setId(res.getInt("id"));
                objPlato.setNombre(res.getString("nombre"));
                objPlato.setPrecio(res.getDouble("precio"));
                objPlato.setFecha(res.getString("fecha"));
                objPlato.setCategoria(res.getString("categoria")); // Agregar esta línea
                listaPlatos.add(objPlato);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaPlatos;
    }
   /* public ObservableList<PlatosDAO> SELECCIONAR(){

        ObservableList<PlatosDAO> listaPlatos = FXCollections.observableArrayList();
        PlatosDAO objPlato;
        String query = "SELECT * FROM platos ORDER BY nombre";
        try {
            stmt = Conexion.conexion.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while( res.next() ){
                objPlato = new PlatosDAO();
                objPlato.setId(res.getInt("id"));
                objPlato.setNombre(res.getString("nombre"));
                objPlato.setPrecio(res.getDouble("precio"));
                objPlato.setFecha(res.getString("fecha"));
                listaPlatos.add(objPlato);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaPlatos;
    }*/

  /*  public void seleccionarPorId() {
        // Aquí iría el código para seleccionar un plato por su id
    }
}
*/