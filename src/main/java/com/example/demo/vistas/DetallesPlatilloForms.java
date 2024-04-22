package com.example.demo.vistas;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import com.example.demo.modelos.DetallesPlatilloDAO;


public class DetallesPlatilloForms extends Stage {

    private Scene escena;
    private VBox vBox;
    private TextField txtNombrePlatillo, txtCosto, txtTamanio, txtPrecio;
    private Button btnGuardar;
    private DetallesPlatilloDAO objDPDAO;
    private TableView<DetallesPlatilloDAO> tbvDetallesPlatillo;

    public DetallesPlatilloForms(TableView<DetallesPlatilloDAO> tbvDetallesPlatillo, DetallesPlatilloDAO objDPDAO){
        this.tbvDetallesPlatillo = tbvDetallesPlatillo;
        if( objDPDAO != null ) {
            this.objDPDAO = objDPDAO;               // La acción es una actualización
        } else {
            this.objDPDAO = new DetallesPlatilloDAO();  // La acción es una inserción
            // Inicializar idDetallePlatillo como 0 para un nuevo detalle de platillo
            this.objDPDAO.setIdDetallePlatillo(0);
        }
        CrearUI();
        this.setTitle("Formulario de Detalles de Platillo");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {

        txtNombrePlatillo = new TextField();
        txtNombrePlatillo.setPromptText("Nombre del Platillo");
        txtNombrePlatillo.setText(objDPDAO.getNombrePlatillo());

        txtCosto = new TextField();
        txtCosto.setPromptText("Costo");
        txtCosto.setText(String.valueOf(objDPDAO.getCosto()));

        txtTamanio = new TextField();
        txtTamanio.setPromptText("Tamaño");
        txtTamanio.setText(objDPDAO.getTamanio());

        txtPrecio = new TextField();
        txtPrecio.setPromptText("Precio");
        txtPrecio.setText(String.valueOf(objDPDAO.getPrecio()));

        btnGuardar = new Button("Guardar");

        btnGuardar.setOnAction(event -> {
            objDPDAO.setNombrePlatillo(txtNombrePlatillo.getText());
            objDPDAO.setCosto(Double.parseDouble(txtCosto.getText()));
            objDPDAO.setTamanio(txtTamanio.getText());
            objDPDAO.setPrecio(Double.parseDouble(txtPrecio.getText()));

            if( objDPDAO.getIdDetallePlatillo() > 0 )
                objDPDAO.actualizar();
            else
                objDPDAO.insertar();

            tbvDetallesPlatillo.setItems(objDPDAO.seleccionar());
            tbvDetallesPlatillo.refresh();

            this.close();
        });

        vBox = new VBox();
        vBox.setSpacing(10.0);
        vBox.setPadding(new Insets(10.0));
        vBox.getChildren().addAll(txtNombrePlatillo, txtCosto, txtTamanio, txtPrecio, btnGuardar);
        escena = new Scene(vBox, 300, 250);
    }
}
