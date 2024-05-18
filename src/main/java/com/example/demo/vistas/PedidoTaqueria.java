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

import com.example.demo.Componentes.CellCustomPedido;
import com.example.demo.modelos.PedidoDAO;

public class PedidoTaqueria extends Stage{

    private Scene escena;
    private TableView<PedidoDAO> tbvPedidos;
    private Button btnAgregar;
    private GridPane gridPane;
    private PedidoDAO pedDAO;
    private PedidosForms pedidosForms;

    public PedidoTaqueria() {
        pedDAO = new PedidoDAO();
        pedidosForms = new PedidosForms(tbvPedidos, null); // Crear el formulario de pedido

        CrearUI();
        this.setTitle("Pedidos Taqueria :)");
        this.setScene(escena);
        this.show();
    }
    private void CrearUI() {
        tbvPedidos = new TableView<>();
        btnAgregar = new Button("Nuevo Pedido");
        pedDAO = new PedidoDAO();
        btnAgregar.setOnAction(event -> {
            pedidosForms = new PedidosForms(tbvPedidos, null); // Actualizar el formulario de pedido
            gridPane.add(pedidosForms, 1, 0); // Añadir el formulario de pedido al GridPane

        });
        tbvPedidos.setStyle("-fx-background-color: #fffed1;" +
                "-fx-table-cell-border-color: transparent;" +
                "-fx-padding: 10;" +
                "-fx-font-size: 14;" +
                "-fx-font-family: 'Arial';");
        gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10, 10, 10, 10));

        // Agregar la tabla y el formulario de pedido al GridPane
        gridPane.add(tbvPedidos, 0, 0);
        gridPane.add(btnAgregar, 0, 1);

        escena = new Scene(gridPane, 1200, 450);
        CrearTabla();
    }

    public void actualizarFormulario(PedidoDAO pedido) {
        pedidosForms = new PedidosForms(tbvPedidos, pedido); // Actualizar el formulario de pedido con los datos del pedido seleccionado
        gridPane.add(pedidosForms, 1, 0); // Añadir el formulario de pedido al GridPane
    }

    private void CrearTabla() {
        TableColumn<PedidoDAO, Integer> columnaIdPedido = new TableColumn<>("ID");
        columnaIdPedido.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<PedidoDAO, String> columnaNombre = new TableColumn<>("Nombre");
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<PedidoDAO, Float> columnaPrecio = new TableColumn<>("Precio");
        columnaPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));

        TableColumn<PedidoDAO, Integer> columnaCantidad = new TableColumn<>("Cantidad");
        columnaCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));

        TableColumn<PedidoDAO, String> columnaComentario = new TableColumn<>("Comentario");
        columnaComentario.setCellValueFactory(new PropertyValueFactory<>("comentario"));

        TableColumn<PedidoDAO, Integer> columnaId_plato = new TableColumn<>("ID Plato");
        columnaId_plato.setCellValueFactory(new PropertyValueFactory<>("id_plato"));

        PedidoTaqueria pedidoTaqueria = this; // Guardar una referencia a this

        TableColumn<PedidoDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(new Callback<TableColumn<PedidoDAO, String>, TableCell<PedidoDAO, String>>() {
            @Override
            public TableCell<PedidoDAO, String> call(TableColumn<PedidoDAO, String> param) {
                return new CellCustomPedido(1, pedidoTaqueria); // Pasar la referencia a PedidoTaqueria al constructor de CellCustomPedido
            }
        });

        TableColumn<PedidoDAO, String> tbcBorrar = new TableColumn<>("Borrar");
        tbcBorrar.setCellFactory(new Callback<TableColumn<PedidoDAO, String>, TableCell<PedidoDAO, String>>() {
            @Override
            public TableCell<PedidoDAO, String> call(TableColumn<PedidoDAO, String> param) {
                return new CellCustomPedido(2, pedidoTaqueria); // Pasar la referencia a PedidoTaqueria al constructor de CellCustomPedido
            }
        });

        tbvPedidos.getColumns().addAll(columnaIdPedido, columnaNombre, columnaPrecio, columnaCantidad, columnaComentario, columnaId_plato, tbcEditar, tbcBorrar);
        tbvPedidos.setItems(pedDAO.SELECCIONAR());
    }
}
