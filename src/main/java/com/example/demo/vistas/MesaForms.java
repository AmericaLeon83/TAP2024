package com.example.demo.vistas;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import com.example.demo.modelos.MesaDAO;

public class MesaForms extends VBox {
    private Label lblTitulo;
    private TextField txtNumeroMesa, txtCapacidad, txtEstado;
    private Button btnGuardar;
    private MesaDAO objMDAO;
    private TableView<MesaDAO> tbvMesas;

    public MesaForms(TableView<MesaDAO> tbvMesas, MesaDAO objMDAO){
        this.tbvMesas = tbvMesas;
        if( objMDAO != null ) {
            this.objMDAO = objMDAO;
        } else {
            this.objMDAO = new MesaDAO();
            this.objMDAO.setId(0);
        }
        CrearUI();
    }

    private void CrearUI() {
        lblTitulo = new Label("Nueva Mesa");
        lblTitulo.setStyle("-fx-font-size: 20; -fx-font-weight: bold;");
        txtNumeroMesa = new TextField();
        txtNumeroMesa.setPromptText("Número de Mesa");
        txtNumeroMesa.setText(String.valueOf(objMDAO.getNumeroMesa()));

        txtCapacidad = new TextField();
        txtCapacidad.setPromptText("Capacidad de la Mesa");
        txtCapacidad.setText(String.valueOf(objMDAO.getCapacidad()));

        txtEstado = new TextField();
        txtEstado.setPromptText("Estado de la Mesa");
        txtEstado.setText(objMDAO.getEstado());

        btnGuardar = new Button("Guardar");

        this.setStyle("-fx-background-color: #f1ffc8;" +
                "-fx-padding: 20;" +
                "-fx-border-color: #ffdecc;" +
                "-fx-border-radius: 10;" +
                "-fx-font-size: 14;" +
                "-fx-font-family: 'Arial';");

        btnGuardar.setOnAction(event -> {
            objMDAO.setNumeroMesa(Integer.parseInt(txtNumeroMesa.getText()));
            objMDAO.setCapacidad(Integer.parseInt(txtCapacidad.getText()));
            objMDAO.setEstado(txtEstado.getText());

            if (objMDAO.getId() > 0)
                objMDAO.ACTUALIZAR();
            else
                objMDAO.INSERTAR();

            tbvMesas.setItems(objMDAO.SELECCIONAR());
        });

        this.setSpacing(10.0);
        this.getChildren().addAll(lblTitulo,txtNumeroMesa, txtCapacidad, txtEstado, btnGuardar);
    }
}