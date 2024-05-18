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

import com.example.demo.Componentes.CellCustomOrden;
import com.example.demo.modelos.OrdenDAO;

public class OrdenTaqueria extends Stage{

    private Scene escena;
    private TableView<OrdenDAO> tbvOrdenes;
    private Button btnAgregar;
    private GridPane gridPane;
    private OrdenDAO ordDAO;
    private OrdenesForms ordenesForms;

    public OrdenTaqueria() {
        ordDAO = new OrdenDAO();
        ordenesForms = new OrdenesForms(tbvOrdenes, null); // Crear el formulario de orden

        CrearUI();
        this.setTitle("Ordenes Taqueria :)");
        this.setScene(escena);
        this.show();
    }
    private void CrearUI() {
        tbvOrdenes = new TableView<>();
        btnAgregar = new Button("Nueva Orden");
        ordDAO = new OrdenDAO();
        btnAgregar.setOnAction(event -> {
            ordenesForms = new OrdenesForms(tbvOrdenes, null); // Actualizar el formulario de orden
            gridPane.add(ordenesForms, 1, 0); // Añadir el formulario de orden al GridPane

        });
        tbvOrdenes.setStyle("-fx-background-color: #fffed1;" +
                "-fx-table-cell-border-color: transparent;" +
                "-fx-padding: 10;" +
                "-fx-font-size: 14;" +
                "-fx-font-family: 'Arial';");
        gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10, 10, 10, 10));

        // Agregar la tabla y el formulario de orden al GridPane
        gridPane.add(tbvOrdenes, 0, 0);
        gridPane.add(btnAgregar, 0, 1);

        escena = new Scene(gridPane, 1200, 450);
        CrearTabla();
    }

    public void actualizarFormulario(OrdenDAO orden) {
        ordenesForms = new OrdenesForms(tbvOrdenes, orden); // Actualizar el formulario de orden con los datos de la orden seleccionada
        gridPane.add(ordenesForms, 1, 0); // Añadir el formulario de orden al GridPane
    }

    private void CrearTabla() {
        TableColumn<OrdenDAO, Integer> columnaId = new TableColumn<>("ID");
        columnaId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<OrdenDAO, String> columnaNombre = new TableColumn<>("Plato");
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<OrdenDAO, Float> columnaPrecio = new TableColumn<>("Precio");
        columnaPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));

        TableColumn<OrdenDAO, Integer> columnaCantidad = new TableColumn<>("Cantidad");
        columnaCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));

        TableColumn<OrdenDAO, Float> columnaSubtotal = new TableColumn<>("Subtotal");
        columnaSubtotal.setCellValueFactory(new PropertyValueFactory<>("subtotal"));

        TableColumn<OrdenDAO, String> columnaComentario = new TableColumn<>("Comentario");
        columnaComentario.setCellValueFactory(new PropertyValueFactory<>("comentario"));

        TableColumn<OrdenDAO, Integer> columnaId_plato = new TableColumn<>("ID Plato");
        columnaId_plato.setCellValueFactory(new PropertyValueFactory<>("id_plato"));

        OrdenTaqueria ordenTaqueria = this; // Guardar una referencia a this

        TableColumn<OrdenDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(new Callback<TableColumn<OrdenDAO, String>, TableCell<OrdenDAO, String>>() {
            @Override
            public TableCell<OrdenDAO, String> call(TableColumn<OrdenDAO, String> param) {
                return new CellCustomOrden(1, ordenTaqueria); // Pasar la referencia a OrdenTaqueria al constructor de CellCustomOrden
            }
        });

        TableColumn<OrdenDAO, String> tbcBorrar = new TableColumn<>("Borrar");
        tbcBorrar.setCellFactory(new Callback<TableColumn<OrdenDAO, String>, TableCell<OrdenDAO, String>>() {
            @Override
            public TableCell<OrdenDAO, String> call(TableColumn<OrdenDAO, String> param) {
                return new CellCustomOrden(2, ordenTaqueria); // Pasar la referencia a OrdenTaqueria al constructor de CellCustomOrden
            }
        });

        tbvOrdenes.getColumns().addAll(columnaId, columnaNombre, columnaPrecio, columnaCantidad, columnaSubtotal, columnaComentario, columnaId_plato, tbcEditar, tbcBorrar);
        tbvOrdenes.setItems(ordDAO.SELECCIONAR());
    }
}
