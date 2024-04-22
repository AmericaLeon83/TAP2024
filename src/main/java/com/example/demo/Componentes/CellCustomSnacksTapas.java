package com.example.demo.Componentes;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import com.example.demo.modelos.SnacksTapasDAO;
import com.example.demo.vistas.SnacksTapasForms;

import java.util.Optional;

public class CellCustomSnacksTapas extends TableCell<SnacksTapasDAO, String> {
    private Button btnCelda;
    private int opc;
    private SnacksTapasDAO objSTDAO;

    public CellCustomSnacksTapas(int opc) {
        this.opc = opc;
        if (opc == 1) {
            btnCelda = new Button("Editar");
            btnCelda.setOnAction(event -> {
                objSTDAO = CellCustomSnacksTapas.this.getTableView().getItems().get(CellCustomSnacksTapas.this.getIndex());
                new SnacksTapasForms(CellCustomSnacksTapas.this.getTableView(), objSTDAO);
            });
        } else {
            btnCelda = new Button("Borrar");
            btnCelda.setOnAction(event -> {
                Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                alerta.setTitle("Mensaje del Sistema");
                alerta.setHeaderText("Confirmar acción");
                alerta.setContentText("¿Realmente deseas borrar este snack o tapa?");
                Optional<ButtonType> result = alerta.showAndWait();

                if (result.get() == ButtonType.OK) {
                    objSTDAO = CellCustomSnacksTapas.this.getTableView().getItems().get(CellCustomSnacksTapas.this.getIndex());
                    objSTDAO.eliminar();
                    CellCustomSnacksTapas.this.getTableView().setItems(objSTDAO.seleccionar());
                    CellCustomSnacksTapas.this.getTableView().refresh();
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
