package com.example.demo.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PostresDAO {

    private int idPostre;
    private String nombrePostre;
    private String descripcion;
    private double precio;
    private Statement stmt;

    public int getIdPostre() { return idPostre; }

    public void setIdPostre(int idPostre) { this.idPostre = idPostre; }

    public String getNombrePostre() { return nombrePostre; }

    public void setNombrePostre(String nombrePostre) { this.nombrePostre = nombrePostre; }

    public String getDescripcion() { return descripcion; }

    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public double getPrecio() { return precio; }

    public void setPrecio(double precio) { this.precio = precio; }

    public void INSERTAR(){
        String query = "INSERT INTO postres (nombrePostre, descripcion, precio) " +
                "VALUES('"+this.nombrePostre+"','"+this.descripcion+"',"+this.precio+")";
        try {
            Statement stmt = Conexion.connection.createStatement();
            // Ejecutar la consulta de inserción
            stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            // Obtener el ID generado para el nuevo postre
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                this.idPostre = rs.getInt(1); // Obtener el ID generado
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void ACTUALIZAR(){
        String query = "UPDATE postres SET nombrePostre='"+this.nombrePostre+"', descripcion='"+this.descripcion+"'," +
                "precio="+this.precio+" WHERE idPostre = "+this.idPostre;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void ELIMINAR(){
        String query = "DELETE FROM postres WHERE idPostre = "+this.idPostre;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<PostresDAO> SELECCIONAR(){

        ObservableList<PostresDAO> listaPostres = FXCollections.observableArrayList();
        PostresDAO objPostre;
        String query = "SELECT * FROM postres ORDER BY nombrePostre";
        try {
            stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while( res.next() ){
                objPostre = new PostresDAO();
                objPostre.setIdPostre(res.getInt("idPostre"));
                objPostre.setNombrePostre(res.getString("nombrePostre"));
                objPostre.setDescripcion(res.getString("descripcion"));
                objPostre.setPrecio(res.getDouble("precio"));
                listaPostres.add(objPostre);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaPostres;
    }

    public void seleccionarPorId() {
        // Aquí iría el código para seleccionar un postre por su id
    }
}
