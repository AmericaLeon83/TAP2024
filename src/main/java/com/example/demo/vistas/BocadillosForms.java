package com.example.demo.vistas;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.example.demo.modelos.BocadillosDAO;

public class BocadillosForms extends Stage {

    private Scene escena;
    private VBox vBox;
    private TextField txtNombre, txtDescripcion, txtPrecio;
    private Button btnGuardar;
    private BocadillosDAO objBDAO;
    private TableView<BocadillosDAO> tbvBocadillos;

    public BocadillosForms(TableView<BocadillosDAO> tbvBocadillos, BocadillosDAO objBDAO){
        this.tbvBocadillos = tbvBocadillos;
        if( objBDAO != null ) {
            this.objBDAO = objBDAO;             // La acción es una actualización
        } else {
            this.objBDAO = new BocadillosDAO();  // La acción es una inserción
            // Inicializar idBocadillo como 0 para un nuevo bocadillo
            this.objBDAO.setIdBocadillo(0);
        }
        CrearUI();
        this.setTitle("Formulario De Bocadillos");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {

        txtNombre = new TextField();
        txtNombre.setPromptText("Nombre Del Bocadillo");
        txtNombre.setText(objBDAO.getNombreBocadillo());

        txtDescripcion = new TextField();
        txtDescripcion.setPromptText("Descripción Del Bocadillo");
        txtDescripcion.setText(objBDAO.getDescripcion());

        txtPrecio = new TextField();
        txtPrecio.setPromptText("Precio Del Bocadillo");
        txtPrecio.setText(Double.toString(objBDAO.getPrecio()));

        btnGuardar = new Button("Guardar");

        btnGuardar.setOnAction(event -> {
            objBDAO.setNombreBocadillo(txtNombre.getText());
            objBDAO.setDescripcion(txtDescripcion.getText());
            objBDAO.setPrecio(Double.parseDouble(txtPrecio.getText()));

            if( objBDAO.getIdBocadillo() > 0 )
                objBDAO.ACTUALIZAR();
            else
                objBDAO.INSERTAR();

            tbvBocadillos.setItems(objBDAO.SELECCIONAR());
            tbvBocadillos.refresh();

            this.close();
        });

        vBox = new VBox();
        vBox.setSpacing(10.0);
        vBox.setPadding(new Insets(10.0));
        vBox.getChildren().addAll(txtNombre, txtDescripcion, txtPrecio, btnGuardar);
        escena = new Scene(vBox, 300 ,250);
    }
}
