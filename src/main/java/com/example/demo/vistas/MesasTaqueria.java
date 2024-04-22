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
import com.example.demo.Componentes.CellCustomMesa;
import com.example.demo.modelos.MesaDAO;

public class MesasTaqueria extends Stage {

    private Scene scene;
    private TableView<MesaDAO> tbvMesas;
    private Button btnAgregar;
    private VBox vBox;
    private MesaDAO mesaDAO;

    public MesasTaqueria() {
        mesaDAO = new MesaDAO();
        createUI();
        this.setTitle("Mesas Taqueria :)");
        this.setScene(scene);
        this.show();
    }

    private void createUI() {

        tbvMesas = new TableView<>();
        btnAgregar = new Button("Nueva Mesa");
        mesaDAO = new MesaDAO();
        btnAgregar.setOnAction(event -> {
            new MesaForms(tbvMesas, null);
        });

        vBox = new VBox();
        vBox.setSpacing(10.0);
        vBox.setPadding(new Insets(10.0));
        vBox.getChildren().addAll(tbvMesas, btnAgregar);
        scene = new Scene(vBox, 408, 300);
        createTable();
    }

    private void createTable() {
        TableColumn<MesaDAO, Integer> columnaIdMesa = new TableColumn<>("ID");
        columnaIdMesa.setCellValueFactory(new PropertyValueFactory<>("idMesa"));

        TableColumn<MesaDAO, Integer> columnaNumeroMesa = new TableColumn<>("Número de Mesa");
        columnaNumeroMesa.setCellValueFactory(new PropertyValueFactory<>("numeroMesa"));

        TableColumn<MesaDAO, Integer> columnaCapacidad = new TableColumn<>("Capacidad");
        columnaCapacidad.setCellValueFactory(new PropertyValueFactory<>("capacidad"));

        TableColumn<MesaDAO, String> columnaEstado = new TableColumn<>("Estado");
        columnaEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        TableColumn<MesaDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(new Callback<TableColumn<MesaDAO, String>, TableCell<MesaDAO, String>>() {
            @Override
            public TableCell<MesaDAO, String> call(TableColumn<MesaDAO, String> param) {
                return new CellCustomMesa(1);
            }
        });

        TableColumn<MesaDAO, String> tbcBorrar = new TableColumn<>("Borrar");
        tbcBorrar.setCellFactory(new Callback<TableColumn<MesaDAO, String>, TableCell<MesaDAO, String>>() {
            @Override
            public TableCell<MesaDAO, String> call(TableColumn<MesaDAO, String> param) {
                return new CellCustomMesa(2);
            }
        });

        tbvMesas.getColumns().addAll(columnaIdMesa, columnaNumeroMesa, columnaCapacidad, columnaEstado, tbcEditar, tbcBorrar);
        tbvMesas.setItems(mesaDAO.seleccionar());
    }
}
