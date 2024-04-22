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
import com.example.demo.Componentes.CellCustomSnacksTapas;
import com.example.demo.modelos.SnacksTapasDAO;

public class SnacksTapasTaqueria extends Stage{

    private Scene escena;
    private TableView<SnacksTapasDAO> tbvSnacksTapas;
    private Button btnAgregar;
    private VBox vBox;
    private SnacksTapasDAO snacksTapasDAO;

    public SnacksTapasTaqueria(){
        snacksTapasDAO = new SnacksTapasDAO();
        CrearUI();
        this.setTitle("Snacks y Tapas Taqueria :)");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {

        tbvSnacksTapas = new TableView<>();
        btnAgregar = new Button("Nuevo Snack o Tapa");
        snacksTapasDAO = new SnacksTapasDAO();
        btnAgregar.setOnAction(event -> {
            new SnacksTapasForms(tbvSnacksTapas, null);
        });

        vBox = new VBox();
        vBox.setSpacing(10.0);
        vBox.setPadding(new Insets(10.0));
        vBox.getChildren().addAll(tbvSnacksTapas, btnAgregar);
        escena = new Scene(vBox, 408, 300);
        CrearTabla();
    }

    private void CrearTabla() {
        TableColumn<SnacksTapasDAO, Integer> columnaIdSnackTapas = new TableColumn<>("ID");
        columnaIdSnackTapas.setCellValueFactory(new PropertyValueFactory<>("idSnackTapas"));

        TableColumn<SnacksTapasDAO, String> columnaNombreSnackTapas = new TableColumn<>("NOMBRE");
        columnaNombreSnackTapas.setCellValueFactory(new PropertyValueFactory<>("nombreSnackTapas"));

        TableColumn<SnacksTapasDAO, String> columnaDescripcion = new TableColumn<>("DESCRIPCIÓN");
        columnaDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));

        TableColumn<SnacksTapasDAO, Double> columnaPrecio = new TableColumn<>("PRECIO");
        columnaPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));

        TableColumn<SnacksTapasDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(new Callback<TableColumn<SnacksTapasDAO, String>, TableCell<SnacksTapasDAO, String>>() {
            @Override
            public TableCell<SnacksTapasDAO, String> call(TableColumn<SnacksTapasDAO, String> param) {
                return new CellCustomSnacksTapas(1);
            }
        });

        TableColumn<SnacksTapasDAO, String> tbcBorrar = new TableColumn<>("Borrar");
        tbcBorrar.setCellFactory(new Callback<TableColumn<SnacksTapasDAO, String>, TableCell<SnacksTapasDAO, String>>() {
            @Override
            public TableCell<SnacksTapasDAO, String> call(TableColumn<SnacksTapasDAO, String> param) {
                return new CellCustomSnacksTapas(2);
            }
        });

        tbvSnacksTapas.getColumns().addAll(columnaIdSnackTapas, columnaNombreSnackTapas, columnaDescripcion, columnaPrecio, tbcEditar, tbcBorrar);
        tbvSnacksTapas.setItems(snacksTapasDAO.seleccionar());
    }
}
