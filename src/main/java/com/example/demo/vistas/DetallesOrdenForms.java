package com.example.demo.vistas;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.example.demo.modelos.DetallesOrdenDAO;

public class DetallesOrdenForms extends Stage {

    private Scene escena;
    private VBox vBox;
    private TextField txtIdOrden, txtIdPlatillo, txtIdBebida, txtCantidadPlatillo, txtCantidadBebida;
    private Button btnGuardar;
    private DetallesOrdenDAO objDODAO;
    private TableView<DetallesOrdenDAO> tbvDetallesOrden;

    public DetallesOrdenForms(TableView<DetallesOrdenDAO> tbvDetallesOrden, DetallesOrdenDAO objDODAO){
        this.tbvDetallesOrden = tbvDetallesOrden;
        if( objDODAO != null ) {
            this.objDODAO = objDODAO;             // La acción es una actualización
        } else {
            this.objDODAO = new DetallesOrdenDAO();  // La acción es una inserción
            // Inicializar idDetalleOrden como 0 para un nuevo detalle de orden
            this.objDODAO.setIdDetalleOrden(0);
        }
        CrearUI();
        this.setTitle("Formulario De Detalles de Orden");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {

        txtIdOrden = new TextField();
        txtIdOrden.setPromptText("ID de la Orden");
        txtIdOrden.setText(String.valueOf(objDODAO.getIdOrden()));

        txtIdPlatillo = new TextField();
        txtIdPlatillo.setPromptText("ID del Platillo");
        txtIdPlatillo.setText(String.valueOf(objDODAO.getIdPlatillo()));

        txtIdBebida = new TextField();
        txtIdBebida.setPromptText("ID de la Bebida");
        txtIdBebida.setText(String.valueOf(objDODAO.getIdBebida()));

        txtCantidadPlatillo = new TextField();
        txtCantidadPlatillo.setPromptText("Cantidad del Platillo");
        txtCantidadPlatillo.setText(String.valueOf(objDODAO.getCantidadPlatillo()));

        txtCantidadBebida = new TextField();
        txtCantidadBebida.setPromptText("Cantidad de la Bebida");
        txtCantidadBebida.setText(String.valueOf(objDODAO.getCantidadBebida()));

        btnGuardar = new Button("Guardar");

        btnGuardar.setOnAction(event -> {
            objDODAO.setIdOrden(Integer.parseInt(txtIdOrden.getText()));
            objDODAO.setIdPlatillo(Integer.parseInt(txtIdPlatillo.getText()));
            objDODAO.setIdBebida(Integer.parseInt(txtIdBebida.getText()));
            objDODAO.setCantidadPlatillo(Integer.parseInt(txtCantidadPlatillo.getText()));
            objDODAO.setCantidadBebida(Integer.parseInt(txtCantidadBebida.getText()));

            if( objDODAO.getIdDetalleOrden() > 0 )
                objDODAO.actualizar();
            else
                objDODAO.insertar();

            tbvDetallesOrden.setItems(objDODAO.seleccionar());
            tbvDetallesOrden.refresh();

            this.close();
        });

        vBox = new VBox();
        vBox.setSpacing(10.0);
        vBox.setPadding(new Insets(10.0));
        vBox.getChildren().addAll(txtIdOrden, txtIdPlatillo, txtIdBebida, txtCantidadPlatillo, txtCantidadBebida, btnGuardar);
        escena = new Scene(vBox, 300, 250);
    }
}
