package com.example.demo.Componentes;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import com.example.demo.modelos.CafesDAO;
import com.example.demo.vistas.CafesForms;

import java.util.Optional;

public class CellCustomCafe extends TableCell<CafesDAO, String> {
    private Button btnCelda;
    private int opc;
    private CafesDAO objCafeDAO;

    public CellCustomCafe(int opc) {
        this.opc = opc;
        if (opc == 1) {
            btnCelda = new Button("Editar");
            btnCelda.setOnAction(event -> {
                objCafeDAO = CellCustomCafe.this.getTableView().getItems().get(CellCustomCafe.this.getIndex());
                new CafesForms(CellCustomCafe.this.getTableView(), objCafeDAO);
            });
        } else {
            btnCelda = new Button("Borrar");
            btnCelda.setOnAction(event -> {
                Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                alerta.setTitle("Mensaje del Sistema");
                alerta.setHeaderText("Confirmar acción");
                alerta.setContentText("¿Realmente deseas borrar este café?");
                Optional<ButtonType> result = alerta.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {
                    objCafeDAO = CellCustomCafe.this.getTableView().getItems().get(CellCustomCafe.this.getIndex());
                    objCafeDAO.eliminar();
                    CellCustomCafe.this.getTableView().setItems(objCafeDAO.seleccionar());
                    CellCustomCafe.this.getTableView().refresh();
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
