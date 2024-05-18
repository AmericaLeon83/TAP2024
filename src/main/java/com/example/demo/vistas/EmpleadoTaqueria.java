package com.example.demo.vistas;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import com.example.demo.Componentes.CellCustomEmpleado;
import com.example.demo.modelos.EmpleadoDAO;

public class EmpleadoTaqueria extends Stage{

    private Scene escena;
    private TableView<EmpleadoDAO> tbvEmpleados;
    private Button btnAgregar;
    private GridPane gridPane;
    private EmpleadoDAO empDAO;
    private EmpleadosForms empleadosForms;

    public EmpleadoTaqueria() {
        empDAO = new EmpleadoDAO();
        empleadosForms = new EmpleadosForms(tbvEmpleados, null); // Crear el formulario de empleado

        CrearUI();
        this.setTitle("Empleados Taqueria :)");
        this.setScene(escena);
        this.show();
    }
    private void CrearUI() {
        tbvEmpleados = new TableView<>();
        btnAgregar = new Button("Nuevo Empleado");
        empDAO = new EmpleadoDAO();
        btnAgregar.setOnAction(event -> {
            empleadosForms = new EmpleadosForms(tbvEmpleados, null); // Actualizar el formulario de empleado
            gridPane.add(empleadosForms, 1, 0); // Añadir el formulario de empleado al GridPane

        });
        tbvEmpleados.setStyle("-fx-background-color: #fffed1;" +
                "-fx-table-cell-border-color: transparent;" +
                "-fx-padding: 10;" +
                "-fx-font-size: 14;" +
                "-fx-font-family: 'Arial';");
        gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10, 10, 10, 10));

        // Agregar la tabla y el formulario de empleado al GridPane
        gridPane.add(tbvEmpleados, 0, 0);
        gridPane.add(btnAgregar, 0, 1);

        escena = new Scene(gridPane, 1200, 450);
        CrearTabla();
    }

    public void actualizarFormulario(EmpleadoDAO empleado) {
        empleadosForms = new EmpleadosForms(tbvEmpleados, empleado); // Actualizar el formulario de empleado con los datos del empleado seleccionado
        gridPane.add(empleadosForms, 1, 0); // Añadir el formulario de empleado al GridPane
    }

    private void CrearTabla() {
        TableColumn<EmpleadoDAO, Integer> columnaIdEmpleado = new TableColumn<>("ID");
        columnaIdEmpleado.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<EmpleadoDAO, String> columnaNomEmpleado = new TableColumn<>("Nombre");
        columnaNomEmpleado.setCellValueFactory(new PropertyValueFactory<>("nomEmpleado"));

        TableColumn<EmpleadoDAO, String> columnaApellidoMaternoE = new TableColumn<>("Apellido Materno");
        columnaApellidoMaternoE.setCellValueFactory(new PropertyValueFactory<>("apellidoMaternoE"));

        TableColumn<EmpleadoDAO, String> columnaApellidoPaternoE = new TableColumn<>("Apellido Paterno");
        columnaApellidoPaternoE.setCellValueFactory(new PropertyValueFactory<>("apellidoPaternoE"));

        TableColumn<EmpleadoDAO, String> columnaRFCEmpleado = new TableColumn<>("RFC");
        columnaRFCEmpleado.setCellValueFactory(new PropertyValueFactory<>("RFCEmpleado"));

        TableColumn<EmpleadoDAO, Float> columnaSalario = new TableColumn<>("Salario");
        columnaSalario.setCellValueFactory(new PropertyValueFactory<>("salario"));

        TableColumn<EmpleadoDAO, String> columnaTelefono = new TableColumn<>("Teléfono");
        columnaTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));

        TableColumn<EmpleadoDAO, String> columnaDireccion = new TableColumn<>("Dirección");
        columnaDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));

        TableColumn<EmpleadoDAO, Integer> columnaId_usuarios = new TableColumn<>("ID Usuario");
        columnaId_usuarios.setCellValueFactory(new PropertyValueFactory<>("id_usuarios"));

        EmpleadoTaqueria empleadoTaqueria = this; // Guardar una referencia a this

        TableColumn<EmpleadoDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(new Callback<TableColumn<EmpleadoDAO, String>, TableCell<EmpleadoDAO, String>>() {
            @Override
            public TableCell<EmpleadoDAO, String> call(TableColumn<EmpleadoDAO, String> param) {
                return new CellCustomEmpleado(1, empleadoTaqueria); // Pasar la referencia a EmpleadoTaqueria al constructor de CellCustomEmpleado
            }
        });

        TableColumn<EmpleadoDAO, String> tbcBorrar = new TableColumn<>("Borrar");
        tbcBorrar.setCellFactory(new Callback<TableColumn<EmpleadoDAO, String>, TableCell<EmpleadoDAO, String>>() {
            @Override
            public TableCell<EmpleadoDAO, String> call(TableColumn<EmpleadoDAO, String> param) {
                return new CellCustomEmpleado(2, empleadoTaqueria); // Pasar la referencia a EmpleadoTaqueria al constructor de CellCustomEmpleado
            }
        });

        tbvEmpleados.getColumns().addAll(columnaIdEmpleado, columnaNomEmpleado, columnaApellidoMaternoE, columnaApellidoPaternoE, columnaRFCEmpleado, columnaSalario, columnaTelefono, columnaDireccion, columnaId_usuarios, tbcEditar, tbcBorrar);
        tbvEmpleados.setItems(empDAO.SELECCIONAR());
    }
}
