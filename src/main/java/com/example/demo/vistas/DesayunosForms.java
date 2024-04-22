package com.example.demo.vistas;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.example.demo.modelos.DesayunosDAO;

public class DesayunosForms extends Stage {

    private Scene escena;
    private VBox vBox;
    private TextField txtNombre, txtDescripcion, txtPrecio;
    private Button btnGuardar;
    private DesayunosDAO objDDAO; // Cambiado de BebidasDAO a DesayunosDAO
    private TableView<DesayunosDAO> tbvDesayunos; // Cambiado de tbvBebidas a tbvDesayunos

    public DesayunosForms(TableView<DesayunosDAO> tbvDesayunos, DesayunosDAO objDDAO){ // Cambiado de BebidasDAO a DesayunosDAO
        this.tbvDesayunos = tbvDesayunos; // Cambiado de tbvBebidas a tbvDesayunos
        if( objDDAO != null ) {
            this.objDDAO = objDDAO;             // La acción es una actualización
        } else {
            this.objDDAO = new DesayunosDAO();  // La acción es una inserción
            // Inicializar idDesayuno como 0 para un nuevo desayuno
            this.objDDAO.setIdDesayuno(0);
        }
        CrearUI();
        this.setTitle("Formulario De Desayunos"); // Cambiado de "Bebidas" a "Desayunos"
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {

        txtNombre = new TextField();
        txtNombre.setPromptText("Nombre Del Desayuno"); // Cambiado de "Bebida" a "Desayuno"
        txtNombre.setText(objDDAO.getNombreDesayuno()); // Cambiado de getNombreBebida() a getNombreDesayuno()

        txtDescripcion = new TextField();
        txtDescripcion.setPromptText("Descripción Del Desayuno"); // Cambiado de "Bebida" a "Desayuno"
        txtDescripcion.setText(objDDAO.getDescripcion()); // Cambiado de getDescripcion() a getDescripcion()

        txtPrecio = new TextField();
        txtPrecio.setPromptText("Precio Del Desayuno"); // Cambiado de "Bebida" a "Desayuno"
        txtPrecio.setText(Double.toString(objDDAO.getPrecio())); // Cambiado de getPrecio() a getPrecio()

        btnGuardar = new Button("Guardar");

        btnGuardar.setOnAction(event -> {
            objDDAO.setNombreDesayuno(txtNombre.getText()); // Cambiado de setNombreBebida() a setNombreDesayuno()
            objDDAO.setDescripcion(txtDescripcion.getText()); // Cambiado de setDescripcion() a setDescripcion()
            objDDAO.setPrecio(Double.parseDouble(txtPrecio.getText())); // Cambiado de setPrecio() a setPrecio()

            if( objDDAO.getIdDesayuno() > 0 )
                objDDAO.ACTUALIZAR();
            else
                objDDAO.INSERTAR();

            tbvDesayunos.setItems(objDDAO.SELECCIONAR());
            tbvDesayunos.refresh();

            this.close();
        });

        vBox = new VBox();
        vBox.setSpacing(10.0);
        vBox.setPadding(new Insets(10.0));
        vBox.getChildren().addAll(txtNombre, txtDescripcion, txtPrecio, btnGuardar);
        escena = new Scene(vBox, 300 ,250);
    }
}
