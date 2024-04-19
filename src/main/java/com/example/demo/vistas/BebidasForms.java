package com.example.demo.vistas;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.example.demo.modelos.BebidasDAO;

public class BebidasForms extends Stage {

    private Scene escena;
    private VBox vBox;
    private TextField txtNombre, txtDescripcion, txtPrecio;
    private Button btnGuardar;
    private BebidasDAO objBDAO;
    private TableView<BebidasDAO> tbvBebidas;

    public BebidasForms(TableView<BebidasDAO> tbvBebidas, BebidasDAO objBDAO){
        this.tbvBebidas = tbvBebidas;
        if( objBDAO != null ) {
            this.objBDAO = objBDAO;             // La acción es una actualización
        } else {
            this.objBDAO = new BebidasDAO();  // La acción es una inserción
            // Inicializar idBebida como 0 para una nueva bebida
            this.objBDAO.setIdBebida(0);
        }
        CrearUI();
        this.setTitle("Formulario De Bebidas");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {

        txtNombre = new TextField();
        txtNombre.setPromptText("Nombre De La Bebida");
        txtNombre.setText(objBDAO.getNombreBebida());

        txtDescripcion = new TextField();
        txtDescripcion.setPromptText("Descripción De La Bebida");
        txtDescripcion.setText(objBDAO.getDescripcion());

        txtPrecio = new TextField();
        txtPrecio.setPromptText("Precio De La Bebida");
        txtPrecio.setText(Double.toString(objBDAO.getPrecio()));

        btnGuardar = new Button("Guardar");

        btnGuardar.setOnAction(event -> {
            objBDAO.setNombreBebida(txtNombre.getText());
            objBDAO.setDescripcion(txtDescripcion.getText());
            objBDAO.setPrecio(Double.parseDouble(txtPrecio.getText()));

            if( objBDAO.getIdBebida() > 0 )
                objBDAO.ACTUALIZAR();
            else
                objBDAO.INSERTAR();

            tbvBebidas.setItems(objBDAO.SELECCIONAR());
            tbvBebidas.refresh();

            this.close();
        });

        vBox = new VBox();
        vBox.setSpacing(10.0);
        vBox.setPadding(new Insets(10.0));
        vBox.getChildren().addAll(txtNombre, txtDescripcion, txtPrecio, btnGuardar);
        escena = new Scene(vBox, 300 ,250);
    }
}
