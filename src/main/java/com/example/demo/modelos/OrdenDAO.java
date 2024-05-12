package com.example.demo.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OrdenDAO {
    private int id;
    private String nombre;
    private float precio;
    private int cantidad;
    private float subtotal;
    private String comentario;
    private int id_plato;
    private Statement stmt;

    // Getters y setters para cada uno de los atributos
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public float getPrecio() { return precio; }
    public void setPrecio(float precio) { this.precio = precio; }
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public float getSubtotal() { return subtotal; }
    public void setSubtotal(float subtotal) { this.subtotal = subtotal; }
    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }
    public int getId_plato() { return id_plato; }
    public void setId_plato(int id_plato) { this.id_plato = id_plato; }

    public int INSERTAR(){
        int generatedId = 0;
        String query = "INSERT INTO orden (nombre, precio, cantidad, comentario, id_plato) " +
                "VALUES('"+this.nombre+"',"+this.precio+","+this.cantidad+",'"+this.comentario+"',"+this.id_plato+")";
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                generatedId = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return generatedId;
    }

    /*public void INSERTAR(){
        String query = "INSERT INTO orden (nombre, precio, cantidad, comentario, id_plato) " +
                "VALUES('"+this.nombre+"',"+this.precio+","+this.cantidad+",'"+this.comentario+"',"+this.id_plato+")";
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
    }*/

    /*public void INSERTAR(){
        String query = "INSERT INTO orden (nombre, precio, cantidad, subtotal, comentario, id_plato) " +
                "VALUES('"+this.nombre+"',"+this.precio+","+this.cantidad+","+this.subtotal+",'"+this.comentario+"',"+this.id_plato+")";
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
    }*/

    public void ACTUALIZAR(){
        String query = "UPDATE orden SET nombre='"+this.nombre+"', precio="+this.precio+", cantidad="+this.cantidad+", subtotal="+this.subtotal+", comentario='"+this.comentario+"', id_plato="+this.id_plato+" WHERE id = "+this.id;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void ELIMINAR(){
        String query = "DELETE FROM orden WHERE id = "+this.id;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<OrdenDAO> SELECCIONAR(){
        ObservableList<OrdenDAO> listaOrdenes = FXCollections.observableArrayList();
        OrdenDAO objOrden;
        String query = "SELECT * FROM orden ORDER BY nombre";
        try {
            stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while( res.next() ){
                objOrden = new OrdenDAO();
                objOrden.setId(res.getInt("id"));
                objOrden.setNombre(res.getString("nombre"));
                objOrden.setPrecio(res.getFloat("precio"));
                objOrden.setCantidad(res.getInt("cantidad"));
                objOrden.setSubtotal(res.getFloat("subtotal"));
                objOrden.setComentario(res.getString("comentario"));
                objOrden.setId_plato(res.getInt("id_plato"));
                listaOrdenes.add(objOrden);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaOrdenes;
    }

    public void BORRAR_TODOS() {
        if (Conexion.connection != null) {
            String query = "DELETE FROM orden";
            try {
                this.stmt = Conexion.connection.createStatement();
                stmt.executeUpdate(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("La conexión a la base de datos no se ha establecido correctamente.");
        }
    }
   /* public static void BORRAR_TODOS() {
        if (Conexion.conexion != null) {
            String query = "DELETE FROM orden";
            try {
                this.stmt = Conexion.conexion.createStatement();
                stmt.executeUpdate(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("La conexión a la base de datos no se ha establecido correctamente.");
        }
    }*/
    public float calcularTotal() {
        float total = 0;
        String query = "SELECT SUM(subtotal) AS total FROM orden";
        try {
            this.stmt = Conexion.connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                total = rs.getFloat("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }
    public void seleccionarPorId() {
        // Aquí iría el código para seleccionar una orden por su id
    }
}
