package com.example.demo.vistas;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import com.example.demo.modelos.PedidoDAO;

public class PedidosForms extends VBox {
    private Label lblTitulo; // Nuevo campo para el título
    private TextField txtNombre, txtPrecio, txtCantidad, txtComentario, txtId_plato;
    private Button btnGuardar;
    private PedidoDAO objPDAO;
    private TableView<PedidoDAO> tbvPedidos;

    public PedidosForms(TableView<PedidoDAO> tbvPedidos, PedidoDAO objPDAO) {
        this.tbvPedidos = tbvPedidos;
        if (objPDAO != null) {
            this.objPDAO = objPDAO;             // La acción es una actualización
        } else {
            this.objPDAO = new PedidoDAO();  // La acción es una inserción
            // Inicializar id como 0 para un nuevo pedido
            this.objPDAO.setId(0);
        }
        CrearUI();
    }

    private void CrearUI() {
        lblTitulo = new Label("Nuevo Pedido"); // Crear el título
        lblTitulo.setStyle("-fx-font-size: 20; -fx-font-weight: bold;"); // Estilo del título

        // Campos de texto para cada uno de los atributos de la tabla pedidos
        txtNombre = new TextField();
        txtNombre.setPromptText("Nombre del Pedido");
        txtNombre.setText(objPDAO.getNombre());

        txtPrecio = new TextField();
        txtPrecio.setPromptText("Precio del Pedido");
        txtPrecio.setText(String.valueOf(objPDAO.getPrecio()));

        txtCantidad = new TextField();
        txtCantidad.setPromptText("Cantidad del Pedido");
        txtCantidad.setText(String.valueOf(objPDAO.getCantidad()));


        txtComentario = new TextField();
        txtComentario.setPromptText("Comentario del Pedido");
        txtComentario.setText(objPDAO.getComentario());

        txtId_plato = new TextField();
        txtId_plato.setPromptText("ID de Orden del Pedido");
        txtId_plato.setText(String.valueOf(objPDAO.getId_plato()));

        btnGuardar = new Button("Guardar");

        this.setStyle("-fx-background-color: #f1ffc8;" +
                "-fx-padding: 20;" +
                "-fx-border-color: #ffdecc;" +
                "-fx-border-radius: 10;" +
                "-fx-font-size: 14;" +
                "-fx-font-family: 'Arial';");

        btnGuardar.setOnAction(event -> {
            // Guardar los datos del formulario en la base de datos
            objPDAO.setNombre(txtNombre.getText());
            objPDAO.setPrecio(Float.parseFloat(txtPrecio.getText()));
            objPDAO.setCantidad(Integer.parseInt(txtCantidad.getText()));
            objPDAO.setComentario(txtComentario.getText());
            objPDAO.setId_plato(Integer.parseInt(txtId_plato.getText()));

            if (objPDAO.getId() > 0)
                objPDAO.ACTUALIZAR();
            else
                objPDAO.INSERTAR();

            // Actualizar la lista observable de la tabla con los datos de la base de datos
            tbvPedidos.setItems(objPDAO.SELECCIONAR());
        });

        this.setSpacing(10.0);
        // Aquí irían los campos de texto añadidos al VBox
        this.getChildren().addAll(lblTitulo, txtNombre, txtPrecio, txtCantidad, txtComentario, txtId_plato, btnGuardar);
    }
}
