package com.example.demo.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MesaDAO {

    private int idMesa;
    private int numeroMesa;
    private int capacidad;
    private String estado;
    private int idCliente;
    private Statement stmt;

    public int getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(int idMesa) {
        this.idMesa = idMesa;
    }

    public int getNumeroMesa() {
        return numeroMesa;
    }

    public void setNumeroMesa(int numeroMesa) {
        this.numeroMesa = numeroMesa;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public void insertar() {
        String query = "INSERT INTO mesas (numeroMesa, capacidad, estado) " +
                "VALUES(" + this.numeroMesa + "," + this.capacidad + ",'" + this.estado + "')";
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                this.idMesa = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void actualizar() {
        String query = "UPDATE mesas SET numeroMesa=?, capacidad=?, estado=?, idCliente=? WHERE idMesa=?";
        try {
            PreparedStatement pstmt = Conexion.connection.prepareStatement(query);
            pstmt.setInt(1, this.numeroMesa);
            pstmt.setInt(2, this.capacidad);
            pstmt.setString(3, this.estado);
            pstmt.setInt(4, this.idCliente);
            pstmt.setInt(5, this.idMesa);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*public void actualizar() {
        String query = "UPDATE mesas SET numeroMesa=" + this.numeroMesa + ", capacidad=" + this.capacidad + ", estado='" + this.estado + "', idCliente=" + this.idCliente +
                " WHERE idMesa = " + this.idMesa;
        try {
            Statement stmt = Conexion.conexion.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/

    public void eliminar() {
        String query = "DELETE FROM mesas WHERE idMesa = " + this.idMesa;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<MesaDAO> seleccionar() {
        ObservableList<MesaDAO> listaMesas = FXCollections.observableArrayList();
        MesaDAO objMesa;
        String query = "SELECT * FROM mesas ORDER BY numeroMesa";
        try {
            stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while (res.next()) {
                objMesa = new MesaDAO();
                objMesa.setIdMesa(res.getInt("idMesa"));
                objMesa.setNumeroMesa(res.getInt("numeroMesa"));
                objMesa.setCapacidad(res.getInt("capacidad"));
                objMesa.setEstado(res.getString("estado"));
                objMesa.setIdCliente(res.getInt("idCliente"));
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
