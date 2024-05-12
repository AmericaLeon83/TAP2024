package com.example.demo.Componentes;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;



import com.example.demo.modelos.OrdenDAO;
import com.example.demo.vistas.OrdenTaqueria;

import java.util.Optional;

public class CellCustomOrden extends TableCell<OrdenDAO, String> {
    private Button btnCelda;
    private int opc;
    private OrdenDAO objODAO;

    public CellCustomOrden(int opc, OrdenTaqueria ordenTaqueria) {
        this.opc = opc;
        if (opc == 1) {
            btnCelda = new Button("Editar");
            btnCelda.setOnAction(event -> {
                objODAO = CellCustomOrden.this.getTableView().getItems().get(CellCustomOrden.this.getIndex());
                ordenTaqueria.actualizarFormulario(objODAO); // Llamar al método actualizarFormulario de OrdenTaqueria
            });
        } else {
            btnCelda = new Button("Borrar");
            btnCelda.setOnAction(event -> {
                Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                alerta.setTitle("Mensaje del Sistema");
                alerta.setHeaderText("Confirmar acción");
                alerta.setContentText("¿Realmente deseas borrar esta orden?");
                Optional<ButtonType> result = alerta.showAndWait();

                if (result.get() == ButtonType.OK) {
                    objODAO = CellCustomOrden.this.getTableView().getItems().get(CellCustomOrden.this.getIndex());
                    objODAO.ELIMINAR();
                    CellCustomOrden.this.getTableView().setItems(objODAO.SELECCIONAR());
                    CellCustomOrden.this.getTableView().refresh();
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
