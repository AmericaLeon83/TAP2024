package com.example.demo.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GuarnicionesDAO {

    private int idGuarnicion;
    private String nombreGuarnicion;
    private String descripcion;
    private double precio;
    private Statement stmt;

    public int getIdGuarnicion() { return idGuarnicion; }

    public void setIdGuarnicion(int idGuarnicion) { this.idGuarnicion = idGuarnicion; }

    public String getNombreGuarnicion() { return nombreGuarnicion; }

    public void setNombreGuarnicion(String nombreGuarnicion) { this.nombreGuarnicion = nombreGuarnicion; }

    public String getDescripcion() { return descripcion; }

    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public double getPrecio() { return precio; }

    public void setPrecio(double precio) { this.precio = precio; }

    public void INSERTAR(){
        String query = "INSERT INTO guarniciones (nombreGuarnicion, descripcion, precio) " +
                "VALUES('"+this.nombreGuarnicion+"','"+this.descripcion+"',"+this.precio+")";
        try {
            Statement stmt = Conexion.connection.createStatement();
            // Ejecutar la consulta de inserción
            stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            // Obtener el ID generado para la nueva guarnición
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                this.idGuarnicion = rs.getInt(1); // Obtener el ID generado
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void ACTUALIZAR(){
        String query = "UPDATE guarniciones SET nombreGuarnicion='"+this.nombreGuarnicion+"', descripcion='"+this.descripcion+"'," +
                "precio="+this.precio+" WHERE idGuarnicion = "+this.idGuarnicion;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void ELIMINAR(){
        String query = "DELETE FROM guarniciones WHERE idGuarnicion = "+this.idGuarnicion;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<GuarnicionesDAO> SELECCIONAR(){

        ObservableList<GuarnicionesDAO> listaGuarniciones = FXCollections.observableArrayList();
        GuarnicionesDAO objGuarnicion;
        String query = "SELECT * FROM guarniciones ORDER BY nombreGuarnicion";
        try {
            stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while( res.next() ){
                objGuarnicion = new GuarnicionesDAO();
                objGuarnicion.setIdGuarnicion(res.getInt("idGuarnicion"));
                objGuarnicion.setNombreGuarnicion(res.getString("nombreGuarnicion"));
                objGuarnicion.setDescripcion(res.getString("descripcion"));
                objGuarnicion.setPrecio(res.getDouble("precio"));
                listaGuarniciones.add(objGuarnicion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaGuarniciones;
    }

    public void seleccionarPorId() {
        // Aquí iría el código para seleccionar una guarnición por su id
    }
}
