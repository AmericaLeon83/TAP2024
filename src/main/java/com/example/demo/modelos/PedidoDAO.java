package com.example.demo.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;

public class PedidoDAO {
    private int id;
    private String nombre;
    private float precio;
    private int cantidad;

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
    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }
    public int getId_plato() { return id_plato; }
    public void setId_plato(int id_plato) { this.id_plato = id_plato; }

    public void INSERTAR(){
        String query = "INSERT INTO pedidos (nombre, precio, cantidad, comentario, id_plato) " +
                "VALUES('"+this.nombre+"',"+this.precio+","+this.cantidad+",'"+this.comentario+"',"+this.id_plato+")";

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
        String query = "UPDATE pedidos SET nombre='"+this.nombre+"', precio="+this.precio+", cantidad="+this.cantidad+", comentario='"+this.comentario+"', id_plato="+this.id_plato+" WHERE id = "+this.id;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void ELIMINAR(){
        String query = "DELETE FROM pedidos WHERE id = "+this.id;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<PedidoDAO> SELECCIONAR(){
        ObservableList<PedidoDAO> listaPedidos = FXCollections.observableArrayList();
        PedidoDAO objPedido;
        String query = "SELECT * FROM pedidos ORDER BY nombre";
        try {
            stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while( res.next() ){
                objPedido = new PedidoDAO();
                objPedido.setId(res.getInt("id"));
                objPedido.setNombre(res.getString("nombre"));
                objPedido.setPrecio(res.getFloat("precio"));
                objPedido.setCantidad(res.getInt("cantidad"));
                objPedido.setComentario(res.getString("comentario"));
                objPedido.setId_plato(res.getInt("id_plato"));
                listaPedidos.add(objPedido);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaPedidos;
    }

    public void insertarVentaDetalle() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/taqueria2", "adminTacos2", "123");
            String query = "INSERT INTO venta_detalle (fecha, id_plato, cantidad, precio, comentario) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setDate(1, Date.valueOf(LocalDate.now()));
            pstmt.setInt(2, this.id_plato);
            pstmt.setInt(3, this.cantidad);
            pstmt.setFloat(4, this.precio);
            pstmt.setString(5, this.comentario);
            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void seleccionarPorId() {
        // Aquí iría el código para seleccionar un pedido por su id
    }
}
