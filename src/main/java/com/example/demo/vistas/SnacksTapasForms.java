package com.example.demo.vistas;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.example.demo.modelos.SnacksTapasDAO;

public class SnacksTapasForms extends Stage {

    private Scene escena;
    private VBox vBox;
    private TextField txtNombre, txtDescripcion, txtPrecio;
    private Button btnGuardar;
    private SnacksTapasDAO objSTDAO;
    private TableView<SnacksTapasDAO> tbvSnacksTapas;

    public SnacksTapasForms(TableView<SnacksTapasDAO> tbvSnacksTapas, SnacksTapasDAO objSTDAO){
        this.tbvSnacksTapas = tbvSnacksTapas;
        if( objSTDAO != null ) {
            this.objSTDAO = objSTDAO;             // La acción es una actualización
        } else {
            this.objSTDAO = new SnacksTapasDAO();  // La acción es una inserción
            // Inicializar idSnackTapas como 0 para un nuevo snack o tapa
            this.objSTDAO.setIdSnackTapas(0);
        }
        CrearUI();
        this.setTitle("Formulario De Snacks y Tapas");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {

        txtNombre = new TextField();
        txtNombre.setPromptText("Nombre Del Snack o Tapa");
        txtNombre.setText(objSTDAO.getNombreSnackTapas());

        txtDescripcion = new TextField();
        txtDescripcion.setPromptText("Descripción Del Snack o Tapa");
        txtDescripcion.setText(objSTDAO.getDescripcion());

        txtPrecio = new TextField();
        txtPrecio.setPromptText("Precio Del Snack o Tapa");
        txtPrecio.setText(Double.toString(objSTDAO.getPrecio()));

        btnGuardar = new Button("Guardar");

        btnGuardar.setOnAction(event -> {
            objSTDAO.setNombreSnackTapas(txtNombre.getText());
            objSTDAO.setDescripcion(txtDescripcion.getText());
            objSTDAO.setPrecio(Double.parseDouble(txtPrecio.getText()));

            if( objSTDAO.getIdSnackTapas() > 0 )
                objSTDAO.actualizar();
            else
                objSTDAO.insertar();

            tbvSnacksTapas.setItems(objSTDAO.seleccionar());
            tbvSnacksTapas.refresh();

            this.close();
        });

        vBox = new VBox();
        vBox.setSpacing(10.0);
        vBox.setPadding(new Insets(10.0));
        vBox.getChildren().addAll(txtNombre, txtDescripcion, txtPrecio, btnGuardar);
        escena = new Scene(vBox, 300 ,250);
    }
}
