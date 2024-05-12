package com.example.demo.Componentes;


import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;

import com.example.demo.modelos.UsuariosDAO;
import com.example.demo.vistas.UsuarioTaqueria;

import java.util.Optional;

public class CellCustomUsuario extends TableCell<UsuariosDAO, String> {
    private Button btnCelda;
    private int opc;
    private UsuariosDAO objUDAO;

    public CellCustomUsuario(int opc, UsuarioTaqueria usuarioTaqueria) {
        this.opc = opc;
        if (opc == 1) {
            btnCelda = new Button("Editar");
            btnCelda.setOnAction(event -> {
                objUDAO = CellCustomUsuario.this.getTableView().getItems().get(CellCustomUsuario.this.getIndex());
                usuarioTaqueria.actualizarFormulario(objUDAO); // Llamar al método actualizarFormulario de UsuarioTaqueria
            });
        } else {
            btnCelda = new Button("Borrar");
            btnCelda.setOnAction(event -> {
                Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                alerta.setTitle("Mensaje del Sistema");
                alerta.setHeaderText("Confirmar acción");
                alerta.setContentText("¿Realmente deseas borrar este usuario?");
                Optional<ButtonType> result = alerta.showAndWait();

                if (result.get() == ButtonType.OK) {
                    objUDAO = CellCustomUsuario.this.getTableView().getItems().get(CellCustomUsuario.this.getIndex());
                    objUDAO.ELIMINAR();
                    CellCustomUsuario.this.getTableView().setItems(objUDAO.SELECCIONAR());
                    CellCustomUsuario.this.getTableView().refresh();
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