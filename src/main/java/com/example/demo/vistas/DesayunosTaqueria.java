package com.example.demo.vistas;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import com.example.demo.Componentes.CellCustomDesayuno;
import com.example.demo.modelos.DesayunosDAO;

public class DesayunosTaqueria extends Stage{ // Cambiado de BebidasTaqueria a DesayunosTaqueria

    private Scene escena;
    private TableView<DesayunosDAO> tbvDesayunos; // Cambiado de tbvBebidas a tbvDesayunos
    private Button btnAgregar;
    private VBox vBox;
    private DesayunosDAO desayunoDAO; // Cambiado de bebidaDAO a desayunoDAO

    public DesayunosTaqueria(){
        desayunoDAO = new DesayunosDAO(); // Cambiado de bebidaDAO a desayunoDAO
        CrearUI();
        this.setTitle("Desayunos Taqueria :)"); // Cambiado de "Bebidas" a "Desayunos"
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {

        tbvDesayunos = new TableView<>();
        btnAgregar = new Button("Nuevo Desayuno"); // Cambiado de "Nueva Bebida" a "Nuevo Desayuno"
        desayunoDAO = new DesayunosDAO(); // Cambiado de bebidaDAO a desayunoDAO
        btnAgregar.setOnAction(event -> {
            new DesayunosForms(tbvDesayunos, null); // Cambiado de BebidasForms a DesayunosForms
        });

        vBox = new VBox();
        vBox.setSpacing(10.0);
        vBox.setPadding(new Insets(10.0));
        vBox.getChildren().addAll(tbvDesayunos, btnAgregar);
        escena = new Scene(vBox, 408, 300);
        CrearTabla();
    }

    private void CrearTabla() {
        TableColumn<DesayunosDAO, Integer> columnaIdDesayuno = new TableColumn<>("ID"); // Cambiado de "ID" a "ID"
        columnaIdDesayuno.setCellValueFactory(new PropertyValueFactory<>("idDesayuno")); // Cambiado de "idBebida" a "idDesayuno"

        TableColumn<DesayunosDAO, String> columnaNombreDesayuno = new TableColumn<>("NOMBRE"); // Cambiado de "NOMBRE" a "NOMBRE"
        columnaNombreDesayuno.setCellValueFactory(new PropertyValueFactory<>("nombreDesayuno")); // Cambiado de "nombreBebida" a "nombreDesayuno"

        TableColumn<DesayunosDAO, String> columnaDescripcion = new TableColumn<>("DESCRIPCIÓN"); // Cambiado de "DESCRIPCIÓN" a "DESCRIPCIÓN"
        columnaDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion")); // Cambiado de "descripcion" a "descripcion"

        TableColumn<DesayunosDAO, Double> columnaPrecio = new TableColumn<>("PRECIO"); // Cambiado de "PRECIO" a "PRECIO"
        columnaPrecio.setCellValueFactory(new PropertyValueFactory<>("precio")); // Cambiado de "precio" a "precio"

        TableColumn<DesayunosDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(new Callback<TableColumn<DesayunosDAO, String>, TableCell<DesayunosDAO, String>>() {
            @Override
            public TableCell<DesayunosDAO, String> call(TableColumn<DesayunosDAO, String> param) {
                return new CellCustomDesayuno(1); // Cambiado de CellCustomBebida a CellCustomDesayuno
            }
        });

        TableColumn<DesayunosDAO, String> tbcBorrar = new TableColumn<>("Borrar");
        tbcBorrar.setCellFactory(new Callback<TableColumn<DesayunosDAO, String>, TableCell<DesayunosDAO, String>>() {
            @Override
            public TableCell<DesayunosDAO, String> call(TableColumn<DesayunosDAO, String> param) {
                return new CellCustomDesayuno(2); // Cambiado de CellCustomBebida a CellCustomDesayuno
            }
        });

        tbvDesayunos.getColumns().addAll(columnaIdDesayuno, columnaNombreDesayuno, columnaDescripcion, columnaPrecio, tbcEditar, tbcBorrar);
        tbvDesayunos.setItems(desayunoDAO.SELECCIONAR()); // Cambiado de bebidaDAO a desayunoDAO
    }
}
