package com.example.demo.Componentes;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import com.example.demo.modelos.BocadillosDAO;
import com.example.demo.vistas.BocadillosForms;


import java.util.Optional;

public class CellCustomBocadillo extends TableCell<BocadillosDAO, String> {
    private Button btnCelda;
    private int opc;
    private BocadillosDAO objBDAO;

    public CellCustomBocadillo(int opc) {
        this.opc = opc;
        if (opc == 1) {
            btnCelda = new Button("Editar");
            btnCelda.setOnAction(event -> {
                objBDAO = CellCustomBocadillo.this.getTableView().getItems().get(CellCustomBocadillo.this.getIndex());
                new BocadillosForms(CellCustomBocadillo.this.getTableView(), objBDAO);
            });
        } else {
            btnCelda = new Button("Borrar");
            btnCelda.setOnAction(event -> {
                Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                alerta.setTitle("Mensaje del Sistema");
                alerta.setHeaderText("Confirmar acción");
                alerta.setContentText("¿Realmente deseas borrar este bocadillo?");
                Optional<ButtonType> result = alerta.showAndWait();

                if (result.get() == ButtonType.OK) {
                    objBDAO = CellCustomBocadillo.this.getTableView().getItems().get(CellCustomBocadillo.this.getIndex());
                    objBDAO.ELIMINAR();
                    CellCustomBocadillo.this.getTableView().setItems(objBDAO.SELECCIONAR());
                    CellCustomBocadillo.this.getTableView().refresh();
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
