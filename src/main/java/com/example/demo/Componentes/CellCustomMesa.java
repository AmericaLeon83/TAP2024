package com.example.demo.Componentes;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import com.example.demo.modelos.MesaDAO;
import com.example.demo.vistas.MesaForms;

import java.util.Optional;

public class CellCustomMesa extends TableCell<MesaDAO, String> {
    private Button btnCelda;
    private int opc;
    private MesaDAO objMDAO;

    public CellCustomMesa(int opc) {
        this.opc = opc;
        if (opc == 1) {
            btnCelda = new Button("Editar");
            btnCelda.setOnAction(event -> {
                objMDAO = CellCustomMesa.this.getTableView().getItems().get(CellCustomMesa.this.getIndex());
                new MesaForms(CellCustomMesa.this.getTableView(), objMDAO);
            });
        } else {
            btnCelda = new Button("Borrar");
            btnCelda.setOnAction(event -> {
                Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                alerta.setTitle("Mensaje del Sistema");
                alerta.setHeaderText("Confirmar acción");
                alerta.setContentText("¿Realmente deseas borrar esta mesa?");
                Optional<ButtonType> result = alerta.showAndWait();

                if (result.get() == ButtonType.OK) {
                    objMDAO = CellCustomMesa.this.getTableView().getItems().get(CellCustomMesa.this.getIndex());
                    objMDAO.eliminar(); // Método para eliminar una mesa en la clase MesaDAO
                    CellCustomMesa.this.getTableView().setItems(objMDAO.seleccionar()); // Método para seleccionar todas las mesas en la clase MesaDAO
                    CellCustomMesa.this.getTableView().refresh();
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
