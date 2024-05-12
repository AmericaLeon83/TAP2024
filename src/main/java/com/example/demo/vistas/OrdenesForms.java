package com.example.demo.vistas;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import com.example.demo.modelos.OrdenDAO;
public class OrdenesForms extends VBox {
    private Label lblTitulo; // Nuevo campo para el título
    private TextField txtNombre, txtPrecio, txtCantidad, txtSubtotal, txtComentario, txtId_plato;
    private Button btnGuardar;
    private OrdenDAO objODAO;
    private TableView<OrdenDAO> tbvOrdenes;

    public OrdenesForms(TableView<OrdenDAO> tbvOrdenes, OrdenDAO objODAO) {
        this.tbvOrdenes = tbvOrdenes;
        if (objODAO != null) {
            this.objODAO = objODAO;             // La acción es una actualización
        } else {
            this.objODAO = new OrdenDAO();  // La acción es una inserción
            // Inicializar id como 0 para una nueva orden
            this.objODAO.setId(0);
        }
        CrearUI();
    }

    private void CrearUI() {
        lblTitulo = new Label("Nueva Orden"); // Crear el título
        lblTitulo.setStyle("-fx-font-size: 20; -fx-font-weight: bold;"); // Estilo del título

        // Campos de texto para cada uno de los atributos de la tabla orden
        txtNombre = new TextField();
        txtNombre.setPromptText("Nombre del Plato");
        txtNombre.setText(objODAO.getNombre());

        txtPrecio = new TextField();
        txtPrecio.setPromptText("Precio del Plato");
        txtPrecio.setText(String.valueOf(objODAO.getPrecio()));

        txtCantidad = new TextField();
        txtCantidad.setPromptText("Cantidad del Plato");
        txtCantidad.setText(String.valueOf(objODAO.getCantidad()));

        txtSubtotal = new TextField();
        txtSubtotal.setPromptText("Subtotal de la Orden");
        txtSubtotal.setText(String.valueOf(objODAO.getSubtotal()));

        txtComentario = new TextField();
        txtComentario.setPromptText("Comentario de la Orden");
        txtComentario.setText(objODAO.getComentario());

        txtId_plato = new TextField();
        txtId_plato.setPromptText("ID del Plato");
        txtId_plato.setText(String.valueOf(objODAO.getId_plato()));

        btnGuardar = new Button("Guardar");

        this.setStyle("-fx-background-color: #f1ffc8;" +
                "-fx-padding: 20;" +
                "-fx-border-color: #ffdecc;" +
                "-fx-border-radius: 10;" +
                "-fx-font-size: 14;" +
                "-fx-font-family: 'Arial';");

        btnGuardar.setOnAction(event -> {
            // Guardar los datos del formulario en la base de datos
            objODAO.setNombre(txtNombre.getText());
            objODAO.setPrecio(Float.parseFloat(txtPrecio.getText()));
            objODAO.setCantidad(Integer.parseInt(txtCantidad.getText()));
            objODAO.setSubtotal(Float.parseFloat(txtSubtotal.getText()));
            objODAO.setComentario(txtComentario.getText());
            objODAO.setId_plato(Integer.parseInt(txtId_plato.getText()));

            if (objODAO.getId() > 0)
                objODAO.ACTUALIZAR();
            else
                objODAO.INSERTAR();

            // Actualizar la lista observable de la tabla con los datos de la base de datos
            tbvOrdenes.setItems(objODAO.SELECCIONAR());
        });

        this.setSpacing(10.0);
        // Aquí irían los campos de texto añadidos al VBox
        this.getChildren().addAll(lblTitulo, txtNombre, txtPrecio, txtCantidad, txtSubtotal, txtComentario, txtId_plato, btnGuardar);
    }
}
