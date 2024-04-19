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
//import sample.components.CellCustome;
import com.example.demo.Componentes.CellCustomBebida;
import com.example.demo.modelos.BebidasDAO;

public class BebidasTaqueria extends Stage{

    private Scene escena;
    private TableView<BebidasDAO> tbvBebidas;
    private Button btnAgregar;
    private VBox vBox;
    private BebidasDAO bebidaDAO;

    public BebidasTaqueria(){
        bebidaDAO = new BebidasDAO();
        CrearUI();
        this.setTitle("Bebidas Taqueria :)");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {

        tbvBebidas = new TableView<>();
        btnAgregar = new Button("Nueva Bebida");
        bebidaDAO = new BebidasDAO();
        btnAgregar.setOnAction(event -> {
            new BebidasForms(tbvBebidas, null);
        });

        vBox = new VBox();
        vBox.setSpacing(10.0);
        vBox.setPadding(new Insets(10.0));
        vBox.getChildren().addAll(tbvBebidas, btnAgregar);
        escena = new Scene(vBox, 408, 300);
        CrearTabla();
    }

    private void CrearTabla() {
        TableColumn<BebidasDAO, Integer> columnaIdBebida = new TableColumn<>("ID");
        columnaIdBebida.setCellValueFactory(new PropertyValueFactory<>("idBebida"));

        TableColumn<BebidasDAO, String> columnaNombreBebida = new TableColumn<>("NOMBRE");
        columnaNombreBebida.setCellValueFactory(new PropertyValueFactory<>("nombreBebida"));

        TableColumn<BebidasDAO, String> columnaDescripcion = new TableColumn<>("DESCRIPCIÓN");
        columnaDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));

        TableColumn<BebidasDAO, Double> columnaPrecio = new TableColumn<>("PRECIO");
        columnaPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));

        TableColumn<BebidasDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(new Callback<TableColumn<BebidasDAO, String>, TableCell<BebidasDAO, String>>() {
            @Override
            public TableCell<BebidasDAO, String> call(TableColumn<BebidasDAO, String> param) {
                return new CellCustomBebida(1);
            }
        });

        TableColumn<BebidasDAO, String> tbcBorrar = new TableColumn<>("Borrar");
        tbcBorrar.setCellFactory(new Callback<TableColumn<BebidasDAO, String>, TableCell<BebidasDAO, String>>() {
            @Override
            public TableCell<BebidasDAO, String> call(TableColumn<BebidasDAO, String> param) {
                return new CellCustomBebida(2);
            }
        });

        tbvBebidas.getColumns().addAll(columnaIdBebida, columnaNombreBebida, columnaDescripcion, columnaPrecio, tbcEditar, tbcBorrar);
        tbvBebidas.setItems(bebidaDAO.SELECCIONAR());
    }
}
