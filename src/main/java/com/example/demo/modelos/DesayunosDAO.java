package com.example.demo.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DesayunosDAO {

    private int idDesayuno;
    private String nombreDesayuno;
    private String descripcion;
    private double precio;
    private Statement stmt;

    public int getIdDesayuno() { return idDesayuno; }

    public void setIdDesayuno(int idDesayuno) { this.idDesayuno = idDesayuno; }

    public String getNombreDesayuno() { return nombreDesayuno; }

    public void setNombreDesayuno(String nombreDesayuno) { this.nombreDesayuno = nombreDesayuno; }

    public String getDescripcion() { return descripcion; }

    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public double getPrecio() { return precio; }

    public void setPrecio(double precio) { this.precio = precio; }

    public void INSERTAR(){
        String query = "INSERT INTO desayunos (nombreDesayuno, descripcion, precio) " +
                "VALUES('"+this.nombreDesayuno+"','"+this.descripcion+"',"+this.precio+")";
        try {
            Statement stmt = Conexion.connection.createStatement();
            // Ejecutar la consulta de inserción
            stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            // Obtener el ID generado para el nuevo desayuno
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                this.idDesayuno = rs.getInt(1); // Obtener el ID generado
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void ACTUALIZAR(){
        String query = "UPDATE desayunos SET nombreDesayuno='"+this.nombreDesayuno+"', descripcion='"+this.descripcion+"'," +
                "precio="+this.precio+" WHERE idDesayuno = "+this.idDesayuno;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void ELIMINAR(){
        String query = "DELETE FROM desayunos WHERE idDesayuno = "+this.idDesayuno;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<DesayunosDAO> SELECCIONAR(){

        ObservableList<DesayunosDAO> listaDesayunos = FXCollections.observableArrayList();
        DesayunosDAO objDesayuno;
        String query = "SELECT * FROM desayunos ORDER BY nombreDesayuno";
        try {
            stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while( res.next() ){
                objDesayuno = new DesayunosDAO();
                objDesayuno.setIdDesayuno(res.getInt("idDesayuno"));
                objDesayuno.setNombreDesayuno(res.getString("nombreDesayuno"));
                objDesayuno.setDescripcion(res.getString("descripcion"));
                objDesayuno.setPrecio(res.getDouble("precio"));
                listaDesayunos.add(objDesayuno);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaDesayunos;
    }

    public void seleccionarPorId() {
        // Aquí iría el código para seleccionar un desayuno por su id
    }
}
