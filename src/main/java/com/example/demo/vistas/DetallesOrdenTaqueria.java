package com.example.demo.vistas;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
//import sample.models.DetallesOrdenDAO;
import com.example.demo.Componentes.CellCustomDetallesOrden;
import com.example.demo.modelos.DetallesOrdenDAO;

public class DetallesOrdenTaqueria extends Stage {

    private Scene escena;
    private TableView<DetallesOrdenDAO> tbvDetallesOrden;
    private Button btnAgregar;
    private VBox vBox;
    private DetallesOrdenDAO detallesOrdenDAO;

    public DetallesOrdenTaqueria(){
        detallesOrdenDAO = new DetallesOrdenDAO();
        CrearUI();
        this.setTitle("Detalles de Orden Taqueria");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {

        tbvDetallesOrden = new TableView<>();
        btnAgregar = new Button("Nuevo Detalle");
        detallesOrdenDAO = new DetallesOrdenDAO();
        btnAgregar.setOnAction(event -> {
            new DetallesOrdenForms(tbvDetallesOrden, null);
        });

        vBox = new VBox();
        vBox.setSpacing(10.0);
        vBox.setPadding(new Insets(10.0));
        vBox.getChildren().addAll(tbvDetallesOrden, btnAgregar);
        escena = new Scene(vBox, 408, 300);
        CrearTabla();
    }

    private void CrearTabla() {
        TableColumn<DetallesOrdenDAO, Integer> columnaIdDetalleOrden = new TableColumn<>("ID");
        columnaIdDetalleOrden.setCellValueFactory(new PropertyValueFactory<>("idDetalleOrden"));

        TableColumn<DetallesOrdenDAO, Integer> columnaIdOrden = new TableColumn<>("ID Orden");
        columnaIdOrden.setCellValueFactory(new PropertyValueFactory<>("idOrden"));

        TableColumn<DetallesOrdenDAO, Integer> columnaIdPlatillo = new TableColumn<>("ID Platillo");
        columnaIdPlatillo.setCellValueFactory(new PropertyValueFactory<>("idPlatillo"));

        TableColumn<DetallesOrdenDAO, Integer> columnaIdBebida = new TableColumn<>("ID Bebida");
        columnaIdBebida.setCellValueFactory(new PropertyValueFactory<>("idBebida"));

        TableColumn<DetallesOrdenDAO, Integer> columnaCantidadPlatillo = new TableColumn<>("Cantidad Platillo");
        columnaCantidadPlatillo.setCellValueFactory(new PropertyValueFactory<>("cantidadPlatillo"));

        TableColumn<DetallesOrdenDAO, Integer> columnaCantidadBebida = new TableColumn<>("Cantidad Bebida");
        columnaCantidadBebida.setCellValueFactory(new PropertyValueFactory<>("cantidadBebida"));

        tbvDetallesOrden.getColumns().addAll(columnaIdDetalleOrden, columnaIdOrden, columnaIdPlatillo, columnaIdBebida, columnaCantidadPlatillo, columnaCantidadBebida);
        tbvDetallesOrden.setItems(detallesOrdenDAO.seleccionar());
    }
}
