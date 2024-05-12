package com.example.demo.vistas;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import com.example.demo.modelos.PlatosDAO;
public class PlatosForms extends VBox {
    private Label lblTitulo; // Nuevo campo para el título
    private TextField txtNombre, txtPrecio, txtFecha, txtCategoria;
    private Button btnGuardar;
    private PlatosDAO objPDAO;
    private TableView<PlatosDAO> tbvPlatos;

    public PlatosForms(TableView<PlatosDAO> tbvPlatos, PlatosDAO objPDAO){
        this.tbvPlatos = tbvPlatos;
        if( objPDAO != null ) {
            this.objPDAO = objPDAO;             // La acción es una actualización
        } else {
            this.objPDAO = new PlatosDAO();  // La acción es una inserción
            // Inicializar id como 0 para un nuevo plato
            this.objPDAO.setId(0);
        }
        CrearUI();
    }

    private void CrearUI() {
        lblTitulo = new Label("Nuevo Plato"); // Crear el título
        lblTitulo.setStyle("-fx-font-size: 20; -fx-font-weight: bold;"); // Estilo del título
        txtNombre = new TextField();
        txtNombre.setPromptText("Nombre Del Plato");
        txtNombre.setText(objPDAO.getNombre());

        txtPrecio = new TextField();
        txtPrecio.setPromptText("Precio Del Plato");
        txtPrecio.setText(String.valueOf(objPDAO.getPrecio()));

        txtFecha = new TextField();
        txtFecha.setPromptText("Fecha Del Plato");
        txtFecha.setText(objPDAO.getFecha());

        txtCategoria = new TextField();
        txtCategoria.setPromptText("Categoria Del Plato");
        txtCategoria.setText(objPDAO.getCategoria());

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
            objPDAO.setPrecio(Double.parseDouble(txtPrecio.getText()));
            objPDAO.setFecha(txtFecha.getText());
            objPDAO.setCategoria(txtCategoria.getText());

            if (objPDAO.getId() > 0)
                objPDAO.ACTUALIZAR();
            else
                objPDAO.INSERTAR();

            // Actualizar la lista observable de la tabla con los datos de la base de datos
            tbvPlatos.setItems(objPDAO.SELECCIONAR());
        });

        this.setSpacing(10.0);
        this.getChildren().addAll(lblTitulo,txtNombre, txtPrecio, txtFecha, txtCategoria, btnGuardar);
    }
}

/*package sample.views;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.models.PlatosDAO;
import sample.views.PlatosForms;

public class PlatosForms extends VBox {
    private ComboBox<String> cbxCategoria;
    private Label lblTitulo; // Nuevo campo para el título
    private TextField txtNombre, txtPrecio;
    private Button btnGuardar;
    private PlatosDAO objPDAO;
    private TableView<PlatosDAO> tbvPlatos;

    public PlatosForms(TableView<PlatosDAO> tbvPlatos, PlatosDAO objPDAO){
        this.tbvPlatos = tbvPlatos;
        if( objPDAO != null ) {
            this.objPDAO = objPDAO;             // La acción es una actualización
        } else {
            this.objPDAO = new PlatosDAO();  // La acción es una inserción
            // Inicializar id como 0 para un nuevo plato
            this.objPDAO.setId(0);
        }
        CrearUI();
    }

    private void CrearUI() {
        cbxCategoria = new ComboBox<>();
        cbxCategoria.getItems().addAll("bebida", "desayuno", "bocadillo", "guarnicion", "cafe", "postre", "snackTapas", "platillo");
        ///////
        lblTitulo = new Label("Nuevo Plato"); // Crear el título
        lblTitulo.setStyle("-fx-font-size: 20; -fx-font-weight: bold;"); // Estilo del título
        txtNombre = new TextField();
        txtNombre.setPromptText("Nombre Del Plato");
        txtNombre.setText(objPDAO.getNombre());

        txtPrecio = new TextField();
        txtPrecio.setPromptText("Precio Del Plato");
        txtPrecio.setText(String.valueOf(objPDAO.getPrecio()));

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
            objPDAO.setPrecio(Double.parseDouble(txtPrecio.getText()));



            if (objPDAO.getId() > 0)
                objPDAO.ACTUALIZAR();
            else
                objPDAO.INSERTAR();

            // Actualizar la lista observable de la tabla con los datos de la base de datos
            tbvPlatos.setItems(objPDAO.SELECCIONAR());
        });

        this.setSpacing(10.0);
        this.getChildren().addAll(lblTitulo,txtNombre, txtPrecio, btnGuardar);
    }
}*/