package com.example.demo.vistas;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import com.example.demo.Componentes.CellCustomeDetallePedido;
import com.example.demo.modelos.DetallePedidosDAO;

public class DetallePedidosTaqueria extends Stage {

    private Scene escena;
    private TableView<DetallePedidosDAO> tbvDetallePedidos;
    private Button btnAgregar;
    private VBox vBox;
    private DetallePedidosDAO detPedDAO;

    public DetallePedidosTaqueria(){
        detPedDAO = new DetallePedidosDAO();
        CrearUI();
        this.setTitle("Detalle de Pedidos Taqueria :)");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {

        tbvDetallePedidos = new TableView<>();
        btnAgregar = new Button("Nuevo Detalle de Pedido");
        detPedDAO = new DetallePedidosDAO();
        btnAgregar.setOnAction(event -> {
            new DetallePedidosForms(tbvDetallePedidos, null);
        });

        vBox = new VBox();
        vBox.setSpacing(10.0);
        vBox.setPadding(new Insets(10.0));
        vBox.getChildren().addAll(tbvDetallePedidos, btnAgregar);
        escena = new Scene(vBox, 600, 400);
        CrearTabla();
    }

    private void CrearTabla() {
        TableColumn<DetallePedidosDAO, Integer> columnaId = new TableColumn<>("ID");
        columnaId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<DetallePedidosDAO, String> columnaNombre = new TableColumn<>("Nombre");
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<DetallePedidosDAO, Double> columnaPrecio = new TableColumn<>("Precio");
        columnaPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));

        TableColumn<DetallePedidosDAO, Integer> columnaCantidad = new TableColumn<>("Cantidad");
        columnaCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));

        TableColumn<DetallePedidosDAO, String> columnaComentario = new TableColumn<>("Comentario");
        columnaComentario.setCellValueFactory(new PropertyValueFactory<>("comentario"));

        TableColumn<DetallePedidosDAO, Integer> columnaIdPedido = new TableColumn<>("ID Pedido");
        columnaIdPedido.setCellValueFactory(new PropertyValueFactory<>("id_pedido"));

        TableColumn<DetallePedidosDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(param -> new CellCustomeDetallePedido(1));

        TableColumn<DetallePedidosDAO, String> tbcBorrar = new TableColumn<>("Borrar");
        tbcBorrar.setCellFactory(param -> new CellCustomeDetallePedido(2));

        tbvDetallePedidos.getColumns().addAll(columnaId, columnaNombre, columnaPrecio, columnaCantidad, columnaComentario, columnaIdPedido, tbcEditar, tbcBorrar);
        tbvDetallePedidos.setItems(detPedDAO.seleccionar());
    }
}
