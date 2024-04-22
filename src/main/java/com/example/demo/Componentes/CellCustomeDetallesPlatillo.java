package com.example.demo.Componentes;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import com.example.demo.modelos.DetallesPlatilloDAO;
import com.example.demo.vistas.DetallesPlatilloForms;


import java.util.Optional;

public class CellCustomeDetallesPlatillo extends TableCell<DetallesPlatilloDAO, String> {
    private Button btnCelda;
    private int opc;
    private DetallesPlatilloDAO objDPDAO;

    public CellCustomeDetallesPlatillo(int opc){
        this.opc = opc;
        if( opc == 1) {
            btnCelda = new Button("Editar");
            btnCelda.setOnAction(event -> {
                objDPDAO = CellCustomeDetallesPlatillo.this.getTableView().getItems().get(CellCustomeDetallesPlatillo.this.getIndex());
                new DetallesPlatilloForms(CellCustomeDetallesPlatillo.this.getTableView(), objDPDAO);
            });
        } else {
            btnCelda = new Button("Borrar");
            btnCelda.setOnAction(event -> {
                Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                alerta.setTitle("Mensaje Del Sistema");
                alerta.setHeaderText("Confirmar De La Acción");
                alerta.setContentText("¿Realmente Deseas Borrar Este Detalle de Platillo?");
                Optional<ButtonType> result = alerta.showAndWait();

                if(result.get() == ButtonType.OK ){
                    objDPDAO = CellCustomeDetallesPlatillo.this.getTableView().getItems().get(CellCustomeDetallesPlatillo.this.getIndex());
                    objDPDAO.eliminar();
                    CellCustomeDetallesPlatillo.this.getTableView().setItems(objDPDAO.seleccionar());
                    CellCustomeDetallesPlatillo.this.getTableView().refresh();
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
