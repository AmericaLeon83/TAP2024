package com.example.demo.Componentes;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import com.example.demo.modelos.EmpleadosDAO;
import com.example.demo.vistas.EmpleadosForms;


import java.util.Optional;

public class CellCustomeEmpleados extends TableCell<EmpleadosDAO, String> {
    private Button btnCelda;
    private  int opc;
    private EmpleadosDAO objEmpDAO;

    public CellCustomeEmpleados(int opc){
        this.opc = opc;
        if( opc == 1) {
            btnCelda = new Button("Editar");
            btnCelda.setOnAction(event -> {
                objEmpDAO = CellCustomeEmpleados.this.getTableView().getItems().get(CellCustomeEmpleados.this.getIndex());
                new EmpleadosForms(CellCustomeEmpleados.this.getTableView(), objEmpDAO);
            });
        }else{
            btnCelda = new Button("Borrar");
            btnCelda.setOnAction(event -> {
                Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                alerta.setTitle("Mensaje del Sistema");
                alerta.setHeaderText("Confirmar De La Acción");
                alerta.setContentText("¿Realmente Deseas Borrar Este Empleado?");
                Optional<ButtonType> result = alerta.showAndWait();

                if(result.get() == ButtonType.OK ){
                    objEmpDAO = CellCustomeEmpleados.this.getTableView().getItems().get(CellCustomeEmpleados.this.getIndex());
                    objEmpDAO.ELIMINAREmpleados();
                    CellCustomeEmpleados.this.getTableView().setItems(objEmpDAO.SELECCIONAREmpleados());
                    CellCustomeEmpleados.this.getTableView().refresh();
                }
            });
        }
    }

    @Override
    protected void updateItem(String item,boolean empty){
        super.updateItem(item,empty);
        if(!empty){
            setGraphic(btnCelda);
        }
    }
}
