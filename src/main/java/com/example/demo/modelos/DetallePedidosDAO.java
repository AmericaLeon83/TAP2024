package com.example.demo.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DetallePedidosDAO {

    private int id;
    private String nombre;
    private double precio;
    private int cantidad;
    private String comentario;
    private int id_pedido;
    private Statement stmt;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getPrecio() { return precio; }

    public void setPrecio(double precio) { this.precio = precio; }

    public int getCantidad() { return cantidad; }

    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public String getComentario() { return comentario; }

    public void setComentario(String comentario) { this.comentario = comentario; }

    public int getId_pedido() { return id_pedido; }

    public void setId_pedido(int id_pedido) { this.id_pedido = id_pedido; }

    public void insertar(){
        String query = "INSERT INTO detalle_pedidos (nombre, precio, cantidad, comentario, id_pedido) " +
                "VALUES('"+this.nombre+"','"+this.precio+"','"+this.cantidad+"','"+this.comentario+"','"+this.id_pedido+"')";
        try {

                Statement stmt = Conexion.connection.createStatement();
                // Ejecutar la consulta de inserción
                stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
                // Obtener el ID generado para el nuevo detalle de pedido
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    this.id = rs.getInt(1); // Obtener el ID generado
                }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizar(){
        String query = "UPDATE detalle_pedidos SET nombre='"+this.nombre+"', precio='"+this.precio+"'," +
                "cantidad='"+this.cantidad+"', comentario='"+this.comentario+"', id_pedido='"+this.id_pedido+"' WHERE id = "+this.id;
        try {

                Statement stmt = Conexion.connection.createStatement();
                stmt.executeUpdate(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminar(){
        String query = "DELETE FROM detalle_pedidos WHERE id = "+this.id;
        try {

                Statement stmt = Conexion.connection.createStatement();
                stmt.executeUpdate(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<DetallePedidosDAO> seleccionar(){

        ObservableList<DetallePedidosDAO> listaDetallePedidos = FXCollections.observableArrayList();
        DetallePedidosDAO objDetallePedido;
        String query = "SELECT * FROM detalle_pedidos";
        try {

                stmt = Conexion.connection.createStatement();
                ResultSet res = stmt.executeQuery(query);
                while( res.next() ){
                    objDetallePedido = new DetallePedidosDAO();
                    objDetallePedido.setId(res.getInt("id"));
                    objDetallePedido.setNombre(res.getString("nombre"));
                    objDetallePedido.setPrecio(res.getDouble("precio"));
                    objDetallePedido.setCantidad(res.getInt("cantidad"));
                    objDetallePedido.setComentario(res.getString("comentario"));
                    objDetallePedido.setId_pedido(res.getInt("id_pedido"));
                    listaDetallePedidos.add(objDetallePedido);
                }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaDetallePedidos;
    }

    public void seleccionarPorId() {
        // Aquí iría el código para seleccionar un detalle de pedido por su id
    }
}
