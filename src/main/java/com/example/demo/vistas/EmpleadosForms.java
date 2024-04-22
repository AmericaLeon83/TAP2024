package com.example.demo.vistas;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import com.example.demo.modelos.EmpleadosDAO;


public class EmpleadosForms extends Stage {

    private Scene escena;
    private VBox vBox;
    private TextField txtNombre, txtApeMaterno, txtApePaterno, txtRFC, txtSueldo, txtTelefono, txtEmail;
    private Button btnGuardar;
    private EmpleadosDAO objEmpDAO;
    private TableView<EmpleadosDAO> tbvEmpleados;

    public EmpleadosForms(TableView<EmpleadosDAO> tbvEmpleados, EmpleadosDAO objEmpDAO) {
        this.tbvEmpleados = tbvEmpleados;
        if (objEmpDAO != null)
            this.objEmpDAO = objEmpDAO;          // La acción es una actualización
        else
            this.objEmpDAO = new EmpleadosDAO();  // La acción es una inserción
        CrearUI();
        this.setTitle("Formulario De Empleados");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {

        txtNombre = new TextField();
        txtNombre.setPromptText("Nombre Del Empleado");
        txtNombre.setText(objEmpDAO.getNomEmpleado());

        txtApePaterno = new TextField();
        txtApePaterno.setPromptText("Apellido Paterno");
        txtApePaterno.setText(objEmpDAO.getApellidoPaternoE());

        txtApeMaterno = new TextField();
        txtApeMaterno.setPromptText("Apellido Materno");
        txtApeMaterno.setText(objEmpDAO.getApellidoMaternoE());

        txtRFC = new TextField();
        txtRFC.setPromptText("RFC Del Empleado");
        txtRFC.setText(objEmpDAO.getRFCEmpleado());

        txtSueldo = new TextField();
        txtSueldo.setPromptText("Sueldo Del Empleado");
        txtSueldo.setText(String.valueOf(objEmpDAO.getSalario()));

        txtTelefono = new TextField();
        txtTelefono.setPromptText("Telefono Del Empleado");
        txtTelefono.setText(objEmpDAO.getTelefono());

        txtEmail = new TextField();
        txtEmail.setPromptText("Email Del Empleado");
        txtEmail.setText(objEmpDAO.getEmailEmpleado());

        btnGuardar = new Button("Guardar");

        btnGuardar.setOnAction(event -> {
            objEmpDAO.setNomEmpleado(txtNombre.getText());
            objEmpDAO.setApellidoPaternoE(txtApePaterno.getText());
            objEmpDAO.setApellidoMaternoE(txtApeMaterno.getText());
            objEmpDAO.setRFCEmpleado(txtRFC.getText());
            objEmpDAO.setSalario(Double.parseDouble(txtSueldo.getText()));
            objEmpDAO.setTelefono(txtTelefono.getText());
            objEmpDAO.setEmailEmpleado(txtEmail.getText());

            if (objEmpDAO.getIdEmpleado() > 0)
                objEmpDAO.ACTUALIZAREmpleados();
            else
                objEmpDAO.INSERTAREmpleados();

            tbvEmpleados.setItems(objEmpDAO.SELECCIONAREmpleados());
            tbvEmpleados.refresh();

            this.close();
        });

        vBox = new VBox();
        vBox.setSpacing(10.0);
        vBox.setPadding(new Insets(10.0));
        vBox.getChildren().addAll(txtNombre, txtApePaterno, txtApeMaterno, txtRFC, txtSueldo, txtTelefono,
                txtEmail, btnGuardar);
        escena = new Scene(vBox, 300, 330);
    }
}
