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
import com.example.demo.Componentes.CellCustomeDetallesPlatillo;
import com.example.demo.modelos.DetallesPlatilloDAO;



public class DetallesPlatilloTaqueria extends Stage {

    private Scene escena;
    private TableView<DetallesPlatilloDAO> tbvDetallesPlatillo;
    private Button btnAgregar;
    private VBox vBox;
    private DetallesPlatilloDAO dpDAO;

    public DetallesPlatilloTaqueria() {
        dpDAO = new DetallesPlatilloDAO();
        CrearUI();
        this.setTitle("Detalles de Platillo Taqueria");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {

        tbvDetallesPlatillo = new TableView<>();
        btnAgregar = new Button("Nuevo Detalle Platillo");
        dpDAO = new DetallesPlatilloDAO();
        btnAgregar.setOnAction(event -> {
            new DetallesPlatilloForms(tbvDetallesPlatillo, null);
        });

        vBox = new VBox();
        vBox.setSpacing(10.0);
        vBox.setPadding(new Insets(10.0));
        vBox.getChildren().addAll(tbvDetallesPlatillo, btnAgregar);
        escena = new Scene(vBox, 600, 400);
        CrearTabla();
    }

    private void CrearTabla() {
        TableColumn<DetallesPlatilloDAO, Integer> columnaIdDetallePlatillo = new TableColumn<>("ID");
        columnaIdDetallePlatillo.setCellValueFactory(new PropertyValueFactory<>("idDetallePlatillo"));

        TableColumn<DetallesPlatilloDAO, String> columnaNombrePlatillo = new TableColumn<>("Nombre Platillo");
        columnaNombrePlatillo.setCellValueFactory(new PropertyValueFactory<>("nombrePlatillo"));

        TableColumn<DetallesPlatilloDAO, Double> columnaCosto = new TableColumn<>("Costo");
        columnaCosto.setCellValueFactory(new PropertyValueFactory<>("costo"));

        TableColumn<DetallesPlatilloDAO, String> columnaTamanio = new TableColumn<>("Tamaño");
        columnaTamanio.setCellValueFactory(new PropertyValueFactory<>("tamanio"));

        TableColumn<DetallesPlatilloDAO, Double> columnaPrecio = new TableColumn<>("Precio");
        columnaPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));

        TableColumn<DetallesPlatilloDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(new Callback<TableColumn<DetallesPlatilloDAO, String>, TableCell<DetallesPlatilloDAO, String>>() {
            @Override
            public TableCell<DetallesPlatilloDAO, String> call(TableColumn<DetallesPlatilloDAO, String> param) {
                return new CellCustomeDetallesPlatillo(1);
            }
        });

        TableColumn<DetallesPlatilloDAO, String> tbcBorrar = new TableColumn<>("Borrar");
        tbcBorrar.setCellFactory(new Callback<TableColumn<DetallesPlatilloDAO, String>, TableCell<DetallesPlatilloDAO, String>>() {
            @Override
            public TableCell<DetallesPlatilloDAO, String> call(TableColumn<DetallesPlatilloDAO, String> param) {
                return new CellCustomeDetallesPlatillo(2);
            }
        });

        tbvDetallesPlatillo.getColumns().addAll(columnaIdDetallePlatillo, columnaNombrePlatillo, columnaCosto, columnaTamanio, columnaPrecio, tbcEditar, tbcBorrar);
        tbvDetallesPlatillo.setItems(dpDAO.seleccionar());
    }
}
