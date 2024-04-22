package com.example.demo.vistas;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import com.example.demo.Componentes.CellCustomeCliente;
import com.example.demo.modelos.ClientesDAO;

public class ClienteTaqueria extends Stage{

    private Scene escena;
    private TableView<ClientesDAO> tbvClientes;
    private Button btnAgregar;
    private VBox vBox;
    private ClientesDAO cteDAO;

    public ClienteTaqueria(){
        cteDAO = new ClientesDAO();
        CrearUI();
        this.setTitle("Clientes Taqueria :)");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {

        tbvClientes = new TableView<>();
        btnAgregar = new Button("Nuevo Cliente");
        cteDAO = new ClientesDAO();
        btnAgregar.setOnAction(event -> {
            new ClientesForms(tbvClientes, null);
        });

        vBox = new VBox();
        vBox.setSpacing(10.0);
        vBox.setPadding(new Insets(10.0));
        vBox.getChildren().addAll(tbvClientes,btnAgregar);
        escena = new Scene(vBox,408,300);
        CrearTabla();
    }

    private void CrearTabla() {
        TableColumn<ClientesDAO, Integer> columnaIdCliente = new TableColumn<>("ID");
        columnaIdCliente.setCellValueFactory(new PropertyValueFactory<>("idCliente"));

        TableColumn<ClientesDAO, String> columnaNombreCliente = new TableColumn<>("NOMBRE");
        columnaNombreCliente.setCellValueFactory(new PropertyValueFactory<>("nombreCliente"));

        TableColumn<ClientesDAO, String> columnaTelefonoCliente = new TableColumn<>("TELEFONO");
        columnaTelefonoCliente.setCellValueFactory(new PropertyValueFactory<>("telefonoCliente"));

        TableColumn<ClientesDAO, String> columnaDireccionCliente = new TableColumn<>("DIRECCION");
        columnaDireccionCliente.setCellValueFactory(new PropertyValueFactory<>("direccionCliente"));

        TableColumn<ClientesDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(new Callback<TableColumn<ClientesDAO, String>, TableCell<ClientesDAO, String>>() {
            @Override
            public TableCell<ClientesDAO, String> call(TableColumn<ClientesDAO, String> param) {
                return new CellCustomeCliente(1);
            }
        });

        TableColumn<ClientesDAO, String> tbcBorrar = new TableColumn<>("Borrar");
        tbcBorrar.setCellFactory(new Callback<TableColumn<ClientesDAO, String>, TableCell<ClientesDAO, String>>() {
            @Override
            public TableCell<ClientesDAO, String> call(TableColumn<ClientesDAO, String> param) {
                return new CellCustomeCliente(2);
            }
        });


        tbvClientes.getColumns().addAll(columnaIdCliente, columnaNombreCliente, columnaTelefonoCliente, columnaDireccionCliente, tbcEditar,tbcBorrar);
        tbvClientes.setItems(cteDAO.SELECCIONAR());

    }
}
