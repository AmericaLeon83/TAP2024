package com.example.demo.vistas;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.example.demo.Componentes.CellCustomeEmpleados;
import com.example.demo.modelos.EmpleadosDAO;


public class EmpleadoTaqueria extends Stage {

    private Scene escena;
    private TableView<EmpleadosDAO> tbvEmpleado;
    private Button btnAgregar;
    private VBox vBox;
    private EmpleadosDAO EmpDAO;

    public EmpleadoTaqueria() {
        EmpDAO = new EmpleadosDAO();
        CrearUI();
        this.setTitle("Empleados Taqueria :)");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {

        tbvEmpleado = new TableView<>();
        btnAgregar = new Button("Nuevo Empleado");
        EmpDAO = new EmpleadosDAO();
        btnAgregar.setOnAction(event -> {
            new EmpleadosForms(tbvEmpleado, null);
        });

        vBox = new VBox();
        vBox.setSpacing(10.0);
        vBox.setPadding(new Insets(10.0));
        vBox.getChildren().addAll(tbvEmpleado, btnAgregar);
        escena = new Scene(vBox, 980, 300);
        CrearTabla();
    }

    private void CrearTabla() {
        TableColumn<EmpleadosDAO, Integer> tbcIdEmpleado = new TableColumn<>("ID");
        tbcIdEmpleado.setCellValueFactory(new PropertyValueFactory<>("idEmpleado"));

        TableColumn<EmpleadosDAO, String> tbcNomEmpleado = new TableColumn<>("NOMBRE");
        tbcNomEmpleado.setCellValueFactory(new PropertyValueFactory<>("nomEmpleado"));

        TableColumn<EmpleadosDAO, String> tbcApeMatEmpleado = new TableColumn<>("APELLIDO MATERNO");
        tbcApeMatEmpleado.setCellValueFactory(new PropertyValueFactory<>("apellidoMaternoE"));
        tbcApeMatEmpleado.setMinWidth(130);

        TableColumn<EmpleadosDAO, String> tbcApePatEmpleado = new TableColumn<>("APELLIDO PATERNO");
        tbcApePatEmpleado.setCellValueFactory(new PropertyValueFactory<>("apellidoPaternoE"));
        tbcApePatEmpleado.setMinWidth(130);

        TableColumn<EmpleadosDAO, String> tbcRFCEmpleado = new TableColumn<>("RFC");
        tbcRFCEmpleado.setCellValueFactory(new PropertyValueFactory<>("RFCEmpleado"));

        TableColumn<EmpleadosDAO, Double> tbcSueldoEmpleado = new TableColumn<>("SUELDO");
        tbcSueldoEmpleado.setCellValueFactory(new PropertyValueFactory<>("salario"));

        TableColumn<EmpleadosDAO, String> tbcTelfono = new TableColumn<>("TELEFONO");
        tbcTelfono.setCellValueFactory(new PropertyValueFactory<>("telefono"));

        TableColumn<EmpleadosDAO, String> tbcEmailEmpleado = new TableColumn<>("EMAIL");
        tbcEmailEmpleado.setCellValueFactory(new PropertyValueFactory<>("emailEmpleado"));

        TableColumn<EmpleadosDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(param -> new CellCustomeEmpleados(1));

        TableColumn<EmpleadosDAO, String> tbcBorrar = new TableColumn<>("Borrar");
        tbcBorrar.setCellFactory(param -> new CellCustomeEmpleados(2));

        tbvEmpleado.getColumns().addAll(tbcIdEmpleado, tbcNomEmpleado, tbcApePatEmpleado, tbcApeMatEmpleado,
                tbcRFCEmpleado, tbcSueldoEmpleado, tbcTelfono, tbcEmailEmpleado, tbcEditar, tbcBorrar);
        tbvEmpleado.setItems(EmpDAO.SELECCIONAREmpleados());

    }
}
