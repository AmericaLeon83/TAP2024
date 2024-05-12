package com.example.demo.Componentes;


import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import com.example.demo.modelos.DetallePedidosDAO;
import com.example.demo.vistas.DetallePedidosForms;


import java.util.Optional;

public class CellCustomeDetallePedido extends TableCell<DetallePedidosDAO, String> {
    private Button btnCelda;
    private int opc;
    private DetallePedidosDAO objDPDAO;

    public CellCustomeDetallePedido(int opc){
        this.opc = opc;
        if(opc == 1) {
            btnCelda = new Button("Editar");
            btnCelda.setOnAction(event -> {
                objDPDAO = CellCustomeDetallePedido.this.getTableView().getItems().get(CellCustomeDetallePedido.this.getIndex());
                new DetallePedidosForms(CellCustomeDetallePedido.this.getTableView(), objDPDAO);
            });
        } else {
            btnCelda = new Button("Borrar");
            btnCelda.setOnAction(event -> {
                Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                alerta.setTitle("Mensaje del Sistema");
                alerta.setHeaderText("Confirmar de la acción");
                alerta.setContentText("¿Realmente deseas borrar este detalle de pedido?");
                Optional<ButtonType> result = alerta.showAndWait();

                if(result.get() == ButtonType.OK ){
                    objDPDAO = CellCustomeDetallePedido.this.getTableView().getItems().get(CellCustomeDetallePedido.this.getIndex());
                    objDPDAO.eliminar(); // Ajustar el método eliminar de DetallePedidosDAO
                    // Asumiendo que SELECCIONAR() devuelve una lista de DetallePedidosDAO actualizada
                    CellCustomeDetallePedido.this.getTableView().setItems(objDPDAO.seleccionar());
                    CellCustomeDetallePedido.this.getTableView().refresh();
                }
            });
        }
    }

    @Override
    protected void updateItem(String item, boolean empty){
        super.updateItem(item, empty);
        if(!empty){
            setGraphic(btnCelda);
        }
    }
}
