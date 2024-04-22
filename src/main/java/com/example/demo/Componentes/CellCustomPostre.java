package com.example.demo.Componentes;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import com.example.demo.modelos.PostresDAO;
import com.example.demo.vistas.PostresForms;

import java.util.Optional;

public class CellCustomPostre extends TableCell<PostresDAO, String> {
    private Button btnCelda;
    private int opc;
    private PostresDAO objPDAO;

    public CellCustomPostre(int opc) {
        this.opc = opc;
        if (opc == 1) {
            btnCelda = new Button("Editar");
            btnCelda.setOnAction(event -> {
                objPDAO = CellCustomPostre.this.getTableView().getItems().get(CellCustomPostre.this.getIndex());
                new PostresForms(CellCustomPostre.this.getTableView(), objPDAO);
            });
        } else {
            btnCelda = new Button("Borrar");
            btnCelda.setOnAction(event -> {
                Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                alerta.setTitle("Mensaje del Sistema");
                alerta.setHeaderText("Confirmar acción");
                alerta.setContentText("¿Realmente deseas borrar este postre?");
                Optional<ButtonType> result = alerta.showAndWait();

                if (result.get() == ButtonType.OK) {
                    objPDAO = CellCustomPostre.this.getTableView().getItems().get(CellCustomPostre.this.getIndex());
                    objPDAO.ELIMINAR();
                    CellCustomPostre.this.getTableView().setItems(objPDAO.SELECCIONAR());
                    CellCustomPostre.this.getTableView().refresh();
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
