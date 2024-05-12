package com.example.demo.Componentes;


import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;

import com.example.demo.modelos.EmpleadoDAO;
import com.example.demo.vistas.EmpleadoTaqueria;

import java.util.Optional;

public class CellCustomEmpleado extends TableCell<EmpleadoDAO, String> {
    private Button btnCelda;
    private int opc;
    private EmpleadoDAO objEDAO;

    public CellCustomEmpleado(int opc, EmpleadoTaqueria empleadoTaqueria) {
        this.opc = opc;
        if (opc == 1) {
            btnCelda = new Button("Editar");
            btnCelda.setOnAction(event -> {
                objEDAO = CellCustomEmpleado.this.getTableView().getItems().get(CellCustomEmpleado.this.getIndex());
                empleadoTaqueria.actualizarFormulario(objEDAO); // Llamar al método actualizarFormulario de EmpleadoTaqueria
            });
        } else {
            btnCelda = new Button("Borrar");
            btnCelda.setOnAction(event -> {
                Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                alerta.setTitle("Mensaje del Sistema");
                alerta.setHeaderText("Confirmar acción");
                alerta.setContentText("¿Realmente deseas borrar este empleado?");
                Optional<ButtonType> result = alerta.showAndWait();

                if (result.get() == ButtonType.OK) {
                    objEDAO = CellCustomEmpleado.this.getTableView().getItems().get(CellCustomEmpleado.this.getIndex());
                    objEDAO.ELIMINAR();
                    CellCustomEmpleado.this.getTableView().setItems(objEDAO.SELECCIONAR());
                    CellCustomEmpleado.this.getTableView().refresh();
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

