package com.example.demo.Componentes;
import com.example.demo.modelos.ClientesDAO;
import com.example.demo.vistas.ClientesForms;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;

import java.util.Optional;
public class CellCustomDetallesOrden extends TableCell<ClientesDAO, String> {
    private Button btnCelda;
    private  int opc;
    private ClientesDAO objPDAO;

    public CellCustomDetallesOrden(int opc){
        this.opc = opc;
        if( opc == 1) {
            btnCelda = new Button("Editar");
            btnCelda.setOnAction(event -> {
                objPDAO = CellCustomDetallesOrden.this.getTableView().getItems().get(CellCustomDetallesOrden.this.getIndex());
                new ClientesForms(CellCustomDetallesOrden.this.getTableView(), objPDAO);
            });
        }else{
            btnCelda = new Button("Borrar");
            btnCelda.setOnAction(event -> {
                Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                alerta.setTitle("Mensaje del Sistema");
                alerta.setHeaderText("Confirmar de la acción");
                alerta.setContentText("¿Realmente deseas borrar este producto?");
                Optional<ButtonType> result = alerta.showAndWait();

                if(result.get() == ButtonType.OK ){
                    objPDAO = CellCustomDetallesOrden.this.getTableView().getItems().get(CellCustomDetallesOrden.this.getIndex());
                    objPDAO.ELIMINAR();
                    CellCustomDetallesOrden.this.getTableView().setItems(objPDAO.SELECCIONAR());
                    CellCustomDetallesOrden.this.getTableView().refresh();
                }
            });
        }
    }

    @Override
    protected void updateItem(String item,boolean empty){
        super.updateItem(item,empty);
        if(!empty){
            setGraphic(btnCelda);
        }
    }
}