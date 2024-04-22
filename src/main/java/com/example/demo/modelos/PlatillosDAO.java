package com.example.demo.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PlatillosDAO {

    private int idPlatillo;
    private String nombrePlatillo;
    private String tamanio;
    private double precio;
    private Statement stmt;

    public int getIdPlatillo() {
        return idPlatillo;
    }

    public void setIdPlatillo(int idPlatillo) {
        this.idPlatillo = idPlatillo;
    }

    public String getNombrePlatillo() {
        return nombrePlatillo;
    }

    public void setNombrePlatillo(String nombrePlatillo) {
        this.nombrePlatillo = nombrePlatillo;
    }

    public String getTamanio() {
        return tamanio;
    }

    public void setTamanio(String tamanio) {
        this.tamanio = tamanio;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void INSERTAR() {
        String query = "INSERT INTO platillos (nombrePlatillo, tamanio, precio) " +
                "VALUES('" + this.nombrePlatillo + "','" + this.tamanio + "'," + this.precio + ")";
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                this.idPlatillo = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void ACTUALIZAR() {
        String query = "UPDATE platillos SET nombrePlatillo='" + this.nombrePlatillo + "', tamanio='" + this.tamanio + "'," +
                "precio=" + this.precio + " WHERE idPlatillo = " + this.idPlatillo;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void ELIMINAR() {
        String query = "DELETE FROM platillos WHERE idPlatillo = " + this.idPlatillo;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<PlatillosDAO> SELECCIONAR() {
        ObservableList<PlatillosDAO> listaPlatillos = FXCollections.observableArrayList();
        PlatillosDAO objPlatillo;
        String query = "SELECT * FROM platillos ORDER BY nombrePlatillo";
        try {
            stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while (res.next()) {
                objPlatillo = new PlatillosDAO();
                objPlatillo.setIdPlatillo(res.getInt("idPlatillo"));
                objPlatillo.setNombrePlatillo(res.getString("nombrePlatillo"));
                objPlatillo.setTamanio(res.getString("tamanio"));
                objPlatillo.setPrecio(res.getDouble("precio"));
                listaPlatillos.add(objPlatillo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaPlatillos;
    }

    public void seleccionarPorId() {
        // Aquí iría el código para seleccionar un platillo por su id
    }
}
