package com.example.demo.vistas;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import com.example.demo.modelos.DetallePedidosDAO;

public class DetallePedidosForms extends Stage {

    private Scene escena;
    private VBox vBox;
    private TextField txtNombre, txtPrecio, txtCantidad, txtComentario, txtIdPedido;
    private Button btnGuardar;
    private DetallePedidosDAO objDPDAO;
    private TableView<DetallePedidosDAO> tbvDetallePedidos;

    public DetallePedidosForms(TableView<DetallePedidosDAO> tbvDetallePedidos, DetallePedidosDAO objDPDAO){
        this.tbvDetallePedidos = tbvDetallePedidos;
        if( objDPDAO != null ) {
            this.objDPDAO = objDPDAO;             // La acción es una actualización
        } else {
            this.objDPDAO = new DetallePedidosDAO();  // La acción es una inserción
            // Inicializar id_pedido como 0 para un nuevo detalle de pedido
            this.objDPDAO.setId_pedido(0);
        }
        CrearUI();
        this.setTitle("Formulario De Detalle de Pedidos");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {

        txtNombre = new TextField();
        txtNombre.setPromptText("Nombre del producto");
        txtNombre.setText(objDPDAO.getNombre());

        txtPrecio = new TextField();
        txtPrecio.setPromptText("Precio del producto");
        txtPrecio.setText(String.valueOf(objDPDAO.getPrecio()));

        txtCantidad = new TextField();
        txtCantidad.setPromptText("Cantidad del producto");
        txtCantidad.setText(String.valueOf(objDPDAO.getCantidad()));

        txtComentario = new TextField();
        txtComentario.setPromptText("Comentario");
        txtComentario.setText(objDPDAO.getComentario());

        txtIdPedido = new TextField();
        txtIdPedido.setPromptText("ID del Pedido");
        txtIdPedido.setText(String.valueOf(objDPDAO.getId_pedido()));

        btnGuardar = new Button("Guardar");

        btnGuardar.setOnAction(event -> {
            objDPDAO.setNombre(txtNombre.getText());
            objDPDAO.setPrecio(Double.parseDouble(txtPrecio.getText()));
            objDPDAO.setCantidad(Integer.parseInt(txtCantidad.getText()));
            objDPDAO.setComentario(txtComentario.getText());
            objDPDAO.setId_pedido(Integer.parseInt(txtIdPedido.getText()));

            if( objDPDAO.getId() > 0 )
                objDPDAO.actualizar();
            else
                objDPDAO.insertar();

            tbvDetallePedidos.setItems(objDPDAO.seleccionar());
            tbvDetallePedidos.refresh();

            this.close();
        });

        vBox = new VBox();
        vBox.setSpacing(10.0);
        vBox.setPadding(new Insets(10.0));
        vBox.getChildren().addAll(txtNombre, txtPrecio, txtCantidad, txtComentario, txtIdPedido, btnGuardar);
        escena = new Scene(vBox,300 ,250);
    }
}
