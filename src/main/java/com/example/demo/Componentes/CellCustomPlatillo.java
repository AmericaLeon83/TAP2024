package com.example.demo.Componentes;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import com.example.demo.modelos.PlatillosDAO;
import com.example.demo.vistas.PlatillosForms;

import java.util.Optional;

public class CellCustomPlatillo extends TableCell<PlatillosDAO, String> {
    private Button btnCelda;
    private int opc;
    private PlatillosDAO objPDAO;

    public CellCustomPlatillo(int opc) {
        this.opc = opc;
        if (opc == 1) {
            btnCelda = new Button("Editar");
            btnCelda.setOnAction(event -> {
                objPDAO = CellCustomPlatillo.this.getTableView().getItems().get(CellCustomPlatillo.this.getIndex());
                new PlatillosForms(CellCustomPlatillo.this.getTableView(), objPDAO);
            });
        } else {
            btnCelda = new Button("Borrar");
            btnCelda.setOnAction(event -> {
                Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                alerta.setTitle("Mensaje del Sistema");
                alerta.setHeaderText("Confirmar acción");
                alerta.setContentText("¿Realmente deseas borrar este platillo?");
                Optional<ButtonType> result = alerta.showAndWait();

                if (result.get() == ButtonType.OK) {
                    objPDAO = CellCustomPlatillo.this.getTableView().getItems().get(CellCustomPlatillo.this.getIndex());
                    objPDAO.ELIMINAR();
                    CellCustomPlatillo.this.getTableView().setItems(objPDAO.SELECCIONAR());
                    CellCustomPlatillo.this.getTableView().refresh();
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
