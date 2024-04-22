package com.example.demo.vistas;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.example.demo.modelos.PlatillosDAO;

public class PlatillosForms extends Stage {

    private Scene escena;
    private VBox vBox;
    private TextField txtNombre, txtTamanio, txtPrecio;
    private Button btnGuardar;
    private PlatillosDAO objPDAO;
    private TableView<PlatillosDAO> tbvPlatillos;

    public PlatillosForms(TableView<PlatillosDAO> tbvPlatillos, PlatillosDAO objPDAO) {
        this.tbvPlatillos = tbvPlatillos;
        if (objPDAO != null) {
            this.objPDAO = objPDAO;             // La acción es una actualización
        } else {
            this.objPDAO = new PlatillosDAO();  // La acción es una inserción
            // Inicializar idPlatillo como 0 para un nuevo platillo
            this.objPDAO.setIdPlatillo(0);
        }
        CrearUI();
        this.setTitle("Formulario De Platillos");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {

        txtNombre = new TextField();
        txtNombre.setPromptText("Nombre Del Platillo");
        txtNombre.setText(objPDAO.getNombrePlatillo());

        txtTamanio = new TextField();
        txtTamanio.setPromptText("Tamaño Del Platillo");
        txtTamanio.setText(objPDAO.getTamanio());

        txtPrecio = new TextField();
        txtPrecio.setPromptText("Precio Del Platillo");
        txtPrecio.setText(Double.toString(objPDAO.getPrecio()));

        btnGuardar = new Button("Guardar");

        btnGuardar.setOnAction(event -> {
            objPDAO.setNombrePlatillo(txtNombre.getText());
            objPDAO.setTamanio(txtTamanio.getText());
            objPDAO.setPrecio(Double.parseDouble(txtPrecio.getText()));

            if (objPDAO.getIdPlatillo() > 0)
                objPDAO.ACTUALIZAR();
            else
                objPDAO.INSERTAR();

            tbvPlatillos.setItems(objPDAO.SELECCIONAR());
            tbvPlatillos.refresh();

            this.close();
        });

        vBox = new VBox();
        vBox.setSpacing(10.0);
        vBox.setPadding(new Insets(10.0));
        vBox.getChildren().addAll(txtNombre, txtTamanio, txtPrecio, btnGuardar);
        escena = new Scene(vBox, 300, 250);
    }
}
