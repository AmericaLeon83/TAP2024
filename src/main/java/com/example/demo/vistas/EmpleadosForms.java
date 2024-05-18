package com.example.demo.vistas;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import com.example.demo.modelos.EmpleadoDAO;

public class EmpleadosForms extends VBox {
    private Label lblTitulo; // Nuevo campo para el título
    private TextField txtNomEmpleado, txtApellidoMaternoE, txtApellidoPaternoE, txtRFCEmpleado, txtSalario, txtTelefono, txtDireccion, txtId_usuarios;
    private Button btnGuardar;
    private EmpleadoDAO objEDAO;
    private TableView<EmpleadoDAO> tbvEmpleados;

    public EmpleadosForms(TableView<EmpleadoDAO> tbvEmpleados, EmpleadoDAO objEDAO) {
        this.tbvEmpleados = tbvEmpleados;
        if (objEDAO != null) {
            this.objEDAO = objEDAO;             // La acción es una actualización
        } else {
            this.objEDAO = new EmpleadoDAO();  // La acción es una inserción
            // Inicializar id como 0 para un nuevo empleado
            this.objEDAO.setId(0);
        }
        CrearUI();
    }

    private void CrearUI() {
        lblTitulo = new Label("Nuevo Empleado"); // Crear el título
        lblTitulo.setStyle("-fx-font-size: 20; -fx-font-weight: bold;"); // Estilo del título

        // Campos de texto para cada uno de los atributos de la tabla empleado
        txtNomEmpleado = new TextField();
        txtNomEmpleado.setPromptText("Nombre del Empleado");
        txtNomEmpleado.setText(objEDAO.getNomEmpleado());

        txtApellidoMaternoE = new TextField();
        txtApellidoMaternoE.setPromptText("Apellido Materno del Empleado");
        txtApellidoMaternoE.setText(objEDAO.getApellidoMaternoE());

        txtApellidoPaternoE = new TextField();
        txtApellidoPaternoE.setPromptText("Apellido Paterno del Empleado");
        txtApellidoPaternoE.setText(objEDAO.getApellidoPaternoE());

        txtRFCEmpleado = new TextField();
        txtRFCEmpleado.setPromptText("RFC del Empleado");
        txtRFCEmpleado.setText(objEDAO.getRFCEmpleado());

        txtSalario = new TextField();
        txtSalario.setPromptText("Salario del Empleado");
        txtSalario.setText(String.valueOf(objEDAO.getSalario()));

        txtTelefono = new TextField();
        txtTelefono.setPromptText("Teléfono del Empleado");
        txtTelefono.setText(objEDAO.getTelefono());

        txtDireccion = new TextField();
        txtDireccion.setPromptText("Dirección del Empleado");
        txtDireccion.setText(objEDAO.getDireccion());

        txtId_usuarios = new TextField();
        txtId_usuarios.setPromptText("ID de Usuario del Empleado");
        txtId_usuarios.setText(String.valueOf(objEDAO.getId_usuarios()));

        btnGuardar = new Button("Guardar");

        this.setStyle("-fx-background-color: #f1ffc8;" +
                "-fx-padding: 20;" +
                "-fx-border-color: #ffdecc;" +
                "-fx-border-radius: 10;" +
                "-fx-font-size: 14;" +
                "-fx-font-family: 'Arial';");

        btnGuardar.setOnAction(event -> {
            // Guardar los datos del formulario en la base de datos
            objEDAO.setNomEmpleado(txtNomEmpleado.getText());
            objEDAO.setApellidoMaternoE(txtApellidoMaternoE.getText());
            objEDAO.setApellidoPaternoE(txtApellidoPaternoE.getText());
            objEDAO.setRFCEmpleado(txtRFCEmpleado.getText());
            objEDAO.setSalario(Float.parseFloat(txtSalario.getText()));
            objEDAO.setTelefono(txtTelefono.getText());
            objEDAO.setDireccion(txtDireccion.getText());
            objEDAO.setId_usuarios(Integer.parseInt(txtId_usuarios.getText()));

            if (objEDAO.getId() > 0)
                objEDAO.ACTUALIZAR();
            else
                objEDAO.INSERTAR();

            // Actualizar la lista observable de la tabla con los datos de la base de datos
            tbvEmpleados.setItems(objEDAO.SELECCIONAR());
        });

        this.setSpacing(10.0);
        // Aquí irían los campos de texto añadidos al VBox
        this.getChildren().addAll(lblTitulo, txtNomEmpleado, txtApellidoMaternoE, txtApellidoPaternoE, txtRFCEmpleado, txtSalario, txtTelefono, txtDireccion, txtId_usuarios, btnGuardar);
    }
}