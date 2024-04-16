package com.example.demo.vistas;
import com.example.demo.modelos.EmpleadosDAO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EmpleadosForm extends Stage {

    private TableView<EmpleadosDAO> tbvEmpleados;
    private EmpleadosDAO objEmp;
    String[] arPromts = {"Nombre del Empleado","RFC del Empleado","Sueldo del Empleado","Telefono del Empleado","Dirección del Empleado"};
    private Scene escena;
    private TextField[] arTxtCampos = new TextField[5];
    private Button btnGuardar;
    private VBox vbxPrincipal;

    public EmpleadosForm(TableView<EmpleadosDAO> tbvEmp, EmpleadosDAO objEmp) {
        tbvEmpleados = tbvEmp;
        this.objEmp = (objEmp == null ) ? new EmpleadosDAO() : objEmp;
        CrearUI();
        this.setTitle("Inserciones de Usuario");
        this.setScene(escena);
        this.show();
    }
    private void CrearUI() {
        vbxPrincipal = new VBox();
        vbxPrincipal.setPadding(new Insets(10));
        vbxPrincipal.setSpacing(20);
        vbxPrincipal.setAlignment(Pos.CENTER);

        for (int i = 0; i < arTxtCampos.length; i++) {
            arTxtCampos[i] = new TextField();
            arTxtCampos[i].setPromptText(arPromts[i]);
            vbxPrincipal.getChildren().add(arTxtCampos[i]);
        }
        LlenarForm();
        btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction(event -> GuardarEmpleado());
        vbxPrincipal.getChildren().add(btnGuardar);
        escena = new Scene(vbxPrincipal, 200, 300);
    }
    private void LlenarForm() {
        arTxtCampos[0].setText(objEmp.getNomEmpleado());
        arTxtCampos[1].setText(objEmp.getRfcEmpleado());
        arTxtCampos[2].setText(objEmp.getSalario()+"");
        arTxtCampos[3].setText(objEmp.getDireccion());
        arTxtCampos[4].setText(objEmp.getTelefono());
    }


    private void GuardarEmpleado(){
        objEmp.setNomEmpleado(arTxtCampos[0].getText());
        objEmp.setRfcEmpleado(arTxtCampos[1].getText());
        objEmp.setSalario(Float.parseFloat(arTxtCampos[2].getText()));
        objEmp.setDireccion(arTxtCampos[3].getText());
        objEmp.setTelefono(arTxtCampos[4].getText());
        if(objEmp.getIdEmpleado()>0) {
            objEmp.ACTUALIZAR();
        }
        else {
            objEmp.INSERTAR();
        }
        tbvEmpleados.setItems(objEmp.CONSULTAR());
        tbvEmpleados.refresh();

        arTxtCampos[0].clear();
        arTxtCampos[1].clear();
        arTxtCampos[2].clear();
        arTxtCampos[3].clear();
        arTxtCampos[4].clear();
    }
}