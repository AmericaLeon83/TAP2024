package com.example.demo.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class MesaDAO {

    private int id;
    private int numeroMesa;
    private int capacidad;
    private String estado;
    private Statement stmt;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public int getNumeroMesa() { return numeroMesa; }

    public void setNumeroMesa(int numeroMesa) { this.numeroMesa = numeroMesa; }

    public int getCapacidad() { return capacidad; }

    public void setCapacidad(int capacidad) { this.capacidad = capacidad; }

    public String getEstado() { return estado; }

    public void setEstado(String estado) { this.estado = estado; }

    public void INSERTAR(){
        String query = "INSERT INTO mesas (numeroMesa, capacidad, estado) " +
                "VALUES('"+this.numeroMesa+"','"+this.capacidad+"','"+this.estado+"')";
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

    public MesaDAO obtenerPorNumero(int numeroMesa) {
        MesaDAO mesa = null;
        String query = "SELECT * FROM mesas WHERE numeroMesa = " + numeroMesa;
        try {
            stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            if (res.next()) {
                mesa = new MesaDAO();
                mesa.setId(res.getInt("id"));
                mesa.setNumeroMesa(res.getInt("numeroMesa"));
                mesa.setCapacidad(res.getInt("capacidad"));
                mesa.setEstado(res.getString("estado"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mesa;
    }

    public void ACTUALIZAR(){
        String query = "UPDATE mesas SET numeroMesa='"+this.numeroMesa+"', capacidad='"+this.capacidad+"'," +
                "estado='"+this.estado+"' WHERE id = "+this.id;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void ELIMINAR(){
        String query = "DELETE FROM mesas WHERE id = "+this.id;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<MesaDAO> SELECCIONAR(){

        ObservableList<MesaDAO> listaMesas = FXCollections.observableArrayList();
        MesaDAO objMesa;
        String query = "SELECT * FROM mesas ORDER BY numeroMesa";
        try {
            stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while( res.next() ){
                objMesa = new MesaDAO();
                objMesa.setId(res.getInt("id"));
                objMesa.setNumeroMesa(res.getInt("numeroMesa"));
                objMesa.setCapacidad(res.getInt("capacidad"));
                objMesa.setEstado(res.getString("estado"));
                listaMesas.add(objMesa);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaMesas;
    }

    public void seleccionarPorId() {
        // Aquí iría el código para seleccionar una mesa por su id
    }
}