package com.example.demo.Componentes;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;

import com.example.demo.modelos.PedidoDAO;
import com.example.demo.vistas.PedidoTaqueria;

import java.util.Optional;

public class CellCustomPedido extends TableCell<PedidoDAO, String> {
    private Button btnCelda;
    private int opc;
    private PedidoDAO objPDAO;

    public CellCustomPedido(int opc, PedidoTaqueria pedidoTaqueria) {
        this.opc = opc;
        if (opc == 1) {
            btnCelda = new Button("Editar");
            btnCelda.setOnAction(event -> {
                objPDAO = CellCustomPedido.this.getTableView().getItems().get(CellCustomPedido.this.getIndex());
                pedidoTaqueria.actualizarFormulario(objPDAO); // Llamar al método actualizarFormulario de PedidoTaqueria
            });
        } else {
            btnCelda = new Button("Borrar");
            btnCelda.setOnAction(event -> {
                Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                alerta.setTitle("Mensaje del Sistema");
                alerta.setHeaderText("Confirmar acción");
                alerta.setContentText("¿Realmente deseas borrar este pedido?");
                Optional<ButtonType> result = alerta.showAndWait();

                if (result.get() == ButtonType.OK) {
                    objPDAO = CellCustomPedido.this.getTableView().getItems().get(CellCustomPedido.this.getIndex());
                    objPDAO.ELIMINAR();
                    CellCustomPedido.this.getTableView().setItems(objPDAO.SELECCIONAR());
                    CellCustomPedido.this.getTableView().refresh();
                }
            });
        }
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty) {
            setGraphic(btnCelda);
        }
    }
}
