package com.example.demo.Componentes;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import com.example.demo.modelos.DesayunosDAO;
import com.example.demo.vistas.DesayunosForms;

import java.util.Optional;

public class CellCustomDesayuno extends TableCell<DesayunosDAO, String> { // Cambiado de BebidasDAO a DesayunosDAO
    private Button btnCelda;
    private int opc;
    private DesayunosDAO objDDAO; // Cambiado de objBDAO a objDDAO

    public CellCustomDesayuno(int opc) {
        this.opc = opc;
        if (opc == 1) {
            btnCelda = new Button("Editar");
            btnCelda.setOnAction(event -> {
                objDDAO = CellCustomDesayuno.this.getTableView().getItems().get(CellCustomDesayuno.this.getIndex());
                new DesayunosForms(CellCustomDesayuno.this.getTableView(), objDDAO); // Cambiado de BebidasForms a DesayunosForms
            });
        } else {
            btnCelda = new Button("Borrar");
            btnCelda.setOnAction(event -> {
                Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                alerta.setTitle("Mensaje del Sistema");
                alerta.setHeaderText("Confirmar acción");
                alerta.setContentText("¿Realmente deseas borrar este desayuno?"); // Cambiado de bebida a desayuno
                Optional<ButtonType> result = alerta.showAndWait();

                if (result.get() == ButtonType.OK) {
                    objDDAO = CellCustomDesayuno.this.getTableView().getItems().get(CellCustomDesayuno.this.getIndex());
                    objDDAO.ELIMINAR();
                    CellCustomDesayuno.this.getTableView().setItems(objDDAO.SELECCIONAR());
                    CellCustomDesayuno.this.getTableView().refresh();
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
