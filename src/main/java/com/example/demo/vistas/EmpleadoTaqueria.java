package com.example.demo.vistas;

import com.example.demo.Componentes.ButtonCell;
import com.example.demo.modelos.EmpleadosDAO;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.kordamp.bootstrapfx.BootstrapFX;
import org.kordamp.bootstrapfx.scene.layout.Panel;

public class EmpleadoTaqueria extends Stage {

    private Panel pnlPrincipal;
    private BorderPane bpnPrincipal;
    private ToolBar tlbMenu;
    private Scene escena;
    private Button btnAgregarEmpleado;
    private TableView<EmpleadosDAO> tbvEmpleados;
    private VBox vbxPrincipal;

    public EmpleadoTaqueria(){
        CrearUI();
        this.setTitle("Taqueria Los Inges :)");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        ImageView imvEmp = new ImageView(getClass().getResource("/images/employee.png").toString());
        imvEmp.setFitHeight(50);
        imvEmp.setFitWidth(50);

        btnAgregarEmpleado = new Button();
        btnAgregarEmpleado.setOnAction(event -> new EmpleadosForm(tbvEmpleados, null));
        btnAgregarEmpleado.setPrefSize(50,50);
        btnAgregarEmpleado.setGraphic(imvEmp);
        tlbMenu = new ToolBar(btnAgregarEmpleado);

        CrearTable();
        bpnPrincipal = new BorderPane();
        bpnPrincipal.setTop(tlbMenu);
        bpnPrincipal.setCenter(tbvEmpleados);
        pnlPrincipal = new Panel("Taqueria");
        pnlPrincipal.getStyleClass().add("panel-primary");
        pnlPrincipal.setBody(bpnPrincipal);
        escena = new Scene( pnlPrincipal, 700, 400);
        escena.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
    }

    private void CrearTable(){
        EmpleadosDAO objEmp = new EmpleadosDAO();
        tbvEmpleados = new TableView<EmpleadosDAO>();
        TableColumn<EmpleadosDAO, String> tbcNomEmp = new TableColumn<>("Empleado");
        tbcNomEmp.setCellValueFactory(new PropertyValueFactory<>("nomEmpleado"));

        TableColumn<EmpleadosDAO, String> tbcRFC = new TableColumn<>("RFC");
        tbcRFC.setCellValueFactory(new PropertyValueFactory<>("rfcEmpleado"));

        TableColumn<EmpleadosDAO, Float> tbsSueldo = new TableColumn<>("SALARIO");
        tbsSueldo.setCellValueFactory(new PropertyValueFactory<>("salario"));

        TableColumn<EmpleadosDAO, String> tbcTelefono = new TableColumn<>("TELEFONO");
        tbcTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));

        TableColumn<EmpleadosDAO, String> tbcDireccion = new TableColumn<>("DIRECCION");
        tbcDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));

        TableColumn<EmpleadosDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(
                new Callback<TableColumn<EmpleadosDAO, String>, TableCell<EmpleadosDAO, String>>() {
                    @Override
                    public TableCell<EmpleadosDAO, String> call(TableColumn<EmpleadosDAO, String> empleadosDAOStringTableColumn) {
                        return new ButtonCell(1);
                    }
                }
        );

        TableColumn<EmpleadosDAO, String> tbcEliminar = new TableColumn<>("ELIMINAR");
        tbcEliminar.setCellFactory(
                new Callback<TableColumn<EmpleadosDAO, String>, TableCell<EmpleadosDAO, String>>() {
                    @Override
                    public TableCell<EmpleadosDAO, String> call(TableColumn<EmpleadosDAO, String> empleadosDAOStringTableColumn) {
                        return new ButtonCell(2);
                    }
                }
        );


        tbvEmpleados.getColumns().addAll(tbcNomEmp,tbcRFC,tbsSueldo,tbcTelefono,tbcDireccion,tbcEditar,tbcEliminar);
        tbvEmpleados.setItems(objEmp.CONSULTAR());
    }
}