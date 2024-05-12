package com.example.demo.Componentes;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;


import com.example.demo.modelos.PlatosDAO;
import com.example.demo.vistas.PlatosTaqueria;

import java.util.Optional;

public class CellCustomPlato extends TableCell<PlatosDAO, String> {
    private Button btnCelda;
    private int opc;
    private PlatosDAO objPDAO;

    public CellCustomPlato(int opc, PlatosTaqueria platoTaqueria) {
        this.opc = opc;
        if (opc == 1) {
            btnCelda = new Button("Editar");
            btnCelda.setOnAction(event -> {
                objPDAO = CellCustomPlato.this.getTableView().getItems().get(CellCustomPlato.this.getIndex());
                platoTaqueria.actualizarFormulario(objPDAO); // Llamar al método actualizarFormulario de PlatoTaqueria
            });
        } else {
            btnCelda = new Button("Borrar");
            btnCelda.setOnAction(event -> {
                Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                alerta.setTitle("Mensaje del Sistema");
                alerta.setHeaderText("Confirmar acción");
                alerta.setContentText("¿Realmente deseas borrar este plato?");
                Optional<ButtonType> result = alerta.showAndWait();

                if (result.get() == ButtonType.OK) {
                    objPDAO = CellCustomPlato.this.getTableView().getItems().get(CellCustomPlato.this.getIndex());
                    objPDAO.ELIMINAR();
                    CellCustomPlato.this.getTableView().setItems(objPDAO.SELECCIONAR());
                    CellCustomPlato.this.getTableView().refresh();
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