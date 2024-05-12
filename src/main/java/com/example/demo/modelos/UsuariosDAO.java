package com.example.demo.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UsuariosDAO {

    private int id;
    private String nombre;
    private String correo;
    private String pass;
    private String rol;
    private Statement stmt;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCorreo() { return correo; }

    public void setCorreo(String correo) { this.correo = correo; }

    public String getPass() { return pass; }

    public void setPass(String pass) { this.pass = pass; }

    public String getRol() { return rol; }

    public void setRol(String rol) { this.rol = rol; }

    public void INSERTAR(){
        String query = "INSERT INTO usuarios (nombre, correo, pass, rol) " +
                "VALUES('"+this.nombre+"','"+this.correo+"','"+this.pass+"','"+this.rol+"')";
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
        String query = "UPDATE usuarios SET nombre='"+this.nombre+"', correo='"+this.correo+"'," +
                "pass='"+this.pass+"', rol='"+this.rol+"' WHERE id = "+this.id;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void ELIMINAR(){
        String query = "DELETE FROM usuarios WHERE id = "+this.id;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<UsuariosDAO> SELECCIONAR(){

        ObservableList<UsuariosDAO> listaUsuarios = FXCollections.observableArrayList();
        UsuariosDAO objUsuario;
        String query = "SELECT * FROM usuarios ORDER BY nombre";
        try {
            stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while( res.next() ){
                objUsuario = new UsuariosDAO();
                objUsuario.setId(res.getInt("id"));
                objUsuario.setNombre(res.getString("nombre"));
                objUsuario.setCorreo(res.getString("correo"));
                objUsuario.setPass(res.getString("pass"));
                objUsuario.setRol(res.getString("rol"));
                listaUsuarios.add(objUsuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaUsuarios;
    }

    public void seleccionarPorId() {
        // Aquí iría el código para seleccionar un usuario por su id
    }
}
