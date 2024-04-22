package com.example.demo.Componentes;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import com.example.demo.modelos.GuarnicionesDAO;
import com.example.demo.vistas.GuarnicionesForms;

import java.util.Optional;

public class CellCustomGuarnicion extends TableCell<GuarnicionesDAO, String> {
    private Button btnCelda;
    private int opc;
    private GuarnicionesDAO objGDAO;

    public CellCustomGuarnicion(int opc) {
        this.opc = opc;
        if (opc == 1) {
            btnCelda = new Button("Editar");
            btnCelda.setOnAction(event -> {
                objGDAO = CellCustomGuarnicion.this.getTableView().getItems().get(CellCustomGuarnicion.this.getIndex());
                new GuarnicionesForms(CellCustomGuarnicion.this.getTableView(), objGDAO);
            });
        } else {
            btnCelda = new Button("Borrar");
            btnCelda.setOnAction(event -> {
                Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                alerta.setTitle("Mensaje del Sistema");
                alerta.setHeaderText("Confirmar acción");
                alerta.setContentText("¿Realmente deseas borrar esta guarnición?");
                Optional<ButtonType> result = alerta.showAndWait();

                if (result.get() == ButtonType.OK) {
                    objGDAO = CellCustomGuarnicion.this.getTableView().getItems().get(CellCustomGuarnicion.this.getIndex());
                    objGDAO.ELIMINAR();
                    CellCustomGuarnicion.this.getTableView().setItems(objGDAO.SELECCIONAR());
                    CellCustomGuarnicion.this.getTableView().refresh();
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
