package com.example.demo.vistas;

import com.example.demo.Componentes.CellCustomPlatillo;
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
import com.example.demo.modelos.PlatillosDAO;

public class PlatillosTaqueria extends Stage {

    private Scene escena;
    private TableView<PlatillosDAO> tbvPlatillos;
    private Button btnAgregar;
    private VBox vBox;
    private PlatillosDAO platilloDAO;

    public PlatillosTaqueria() {
        platilloDAO = new PlatillosDAO();
        CrearUI();
        this.setTitle("Platillos Taqueria :)");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {

        tbvPlatillos = new TableView<>();
        btnAgregar = new Button("Nuevo Platillo");
        platilloDAO = new PlatillosDAO();
        btnAgregar.setOnAction(event -> {
            new PlatillosForms(tbvPlatillos, null);
        });

        vBox = new VBox();
        vBox.setSpacing(10.0);
        vBox.setPadding(new Insets(10.0));
        vBox.getChildren().addAll(tbvPlatillos, btnAgregar);
        escena = new Scene(vBox, 408, 300);
        CrearTabla();
    }

    private void CrearTabla() {
        TableColumn<PlatillosDAO, Integer> columnaIdPlatillo = new TableColumn<>("ID");
        columnaIdPlatillo.setCellValueFactory(new PropertyValueFactory<>("idPlatillo"));

        TableColumn<PlatillosDAO, String> columnaNombrePlatillo = new TableColumn<>("NOMBRE");
        columnaNombrePlatillo.setCellValueFactory(new PropertyValueFactory<>("nombrePlatillo"));

        TableColumn<PlatillosDAO, String> columnaTamanio = new TableColumn<>("TAMAÑO");
        columnaTamanio.setCellValueFactory(new PropertyValueFactory<>("tamanio"));

        TableColumn<PlatillosDAO, Double> columnaPrecio = new TableColumn<>("PRECIO");
        columnaPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));

        TableColumn<PlatillosDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(new Callback<TableColumn<PlatillosDAO, String>, TableCell<PlatillosDAO, String>>() {
            @Override
            public TableCell<PlatillosDAO, String> call(TableColumn<PlatillosDAO, String> param) {
                return new CellCustomPlatillo(1);
            }
        });

        TableColumn<PlatillosDAO, String> tbcBorrar = new TableColumn<>("Borrar");
        tbcBorrar.setCellFactory(new Callback<TableColumn<PlatillosDAO, String>, TableCell<PlatillosDAO, String>>() {
            @Override
            public TableCell<PlatillosDAO, String> call(TableColumn<PlatillosDAO, String> param) {
                return new CellCustomPlatillo(2);
            }
        });

        tbvPlatillos.getColumns().addAll(columnaIdPlatillo, columnaNombrePlatillo, columnaTamanio, columnaPrecio, tbcEditar, tbcBorrar);
        tbvPlatillos.setItems(platilloDAO.SELECCIONAR());
    }
}
