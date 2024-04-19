package com.example.demo.Componentes;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import com.example.demo.modelos.BebidasDAO;
import com.example.demo.vistas.BebidasForms;

import java.util.Optional;

public class CellCustomBebida extends TableCell<BebidasDAO, String> {
    private Button btnCelda;
    private int opc;
    private BebidasDAO objBDAO;

    public CellCustomBebida(int opc) {
        this.opc = opc;
        if (opc == 1) {
            btnCelda = new Button("Editar");
            btnCelda.setOnAction(event -> {
                objBDAO = CellCustomBebida.this.getTableView().getItems().get(CellCustomBebida.this.getIndex());
                new BebidasForms(CellCustomBebida.this.getTableView(), objBDAO);
            });
        } else {
            btnCelda = new Button("Borrar");
            btnCelda.setOnAction(event -> {
                Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                alerta.setTitle("Mensaje del Sistema");
                alerta.setHeaderText("Confirmar acción");
                alerta.setContentText("¿Realmente deseas borrar esta bebida?");
                Optional<ButtonType> result = alerta.showAndWait();

                if (result.get() == ButtonType.OK) {
                    objBDAO = CellCustomBebida.this.getTableView().getItems().get(CellCustomBebida.this.getIndex());
                    objBDAO.ELIMINAR();
                    CellCustomBebida.this.getTableView().setItems(objBDAO.SELECCIONAR());
                    CellCustomBebida.this.getTableView().refresh();
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
