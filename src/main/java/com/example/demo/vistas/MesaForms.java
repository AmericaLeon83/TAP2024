package com.example.demo.vistas;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.example.demo.modelos.MesaDAO;


public class MesaForms extends Stage {

    private Scene scene;
    private VBox vBox;
    private TextField txtNumeroMesa, txtCapacidad, txtEstado;
    private Button btnGuardar;
    private MesaDAO objMDAO;
    private TableView<MesaDAO> tbvMesas;

    public MesaForms(TableView<MesaDAO> tbvMesas, MesaDAO objMDAO) {
        this.tbvMesas = tbvMesas;
        if (objMDAO != null) {
            this.objMDAO = objMDAO; // La acción es una actualización
        } else {
            this.objMDAO = new MesaDAO(); // La acción es una inserción
            // Inicializar idMesa como 0 para una nueva mesa
            this.objMDAO.setIdMesa(0);
        }
        createUI();
        this.setTitle("Formulario de Mesas");
        this.setScene(scene);
        this.show();
    }

    private void createUI() {

        txtNumeroMesa = new TextField();
        txtNumeroMesa.setPromptText("Número de Mesa");
        txtNumeroMesa.setText(String.valueOf(objMDAO.getNumeroMesa()));

        txtCapacidad = new TextField();
        txtCapacidad.setPromptText("Capacidad");
        txtCapacidad.setText(String.valueOf(objMDAO.getCapacidad()));

        txtEstado = new TextField();
        txtEstado.setPromptText("Estado");
        txtEstado.setText(objMDAO.getEstado());

        btnGuardar = new Button("Guardar");

        btnGuardar.setOnAction(event -> {
            objMDAO.setNumeroMesa(Integer.parseInt(txtNumeroMesa.getText()));
            objMDAO.setCapacidad(Integer.parseInt(txtCapacidad.getText()));
            objMDAO.setEstado(txtEstado.getText());

            if (objMDAO.getIdMesa() > 0)
                objMDAO.actualizar();
            else
                objMDAO.insertar();

            tbvMesas.setItems(objMDAO.seleccionar());
            tbvMesas.refresh();

            this.close();
        });

        vBox = new VBox();
        vBox.setSpacing(10.0);
        vBox.setPadding(new Insets(10.0));
        vBox.getChildren().addAll(txtNumeroMesa, txtCapacidad, txtEstado, btnGuardar);
        scene = new Scene(vBox, 300, 250);
    }
}
