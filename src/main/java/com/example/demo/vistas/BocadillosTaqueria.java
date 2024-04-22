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
import com.example.demo.Componentes.CellCustomBocadillo;
import com.example.demo.modelos.BocadillosDAO;

public class BocadillosTaqueria extends Stage{

    private Scene escena;
    private TableView<BocadillosDAO> tbvBocadillos;
    private Button btnAgregar;
    private VBox vBox;
    private BocadillosDAO bocadilloDAO;

    public BocadillosTaqueria(){
        bocadilloDAO = new BocadillosDAO();
        CrearUI();
        this.setTitle("Bocadillos Taqueria :)");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {

        tbvBocadillos = new TableView<>();
        btnAgregar = new Button("Nuevo Bocadillo");
        bocadilloDAO = new BocadillosDAO();
        btnAgregar.setOnAction(event -> {
            new BocadillosForms(tbvBocadillos, null);
        });

        vBox = new VBox();
        vBox.setSpacing(10.0);
        vBox.setPadding(new Insets(10.0));
        vBox.getChildren().addAll(tbvBocadillos, btnAgregar);
        escena = new Scene(vBox, 408, 300);
        CrearTabla();
    }

    private void CrearTabla() {
        TableColumn<BocadillosDAO, Integer> columnaIdBocadillo = new TableColumn<>("ID");
        columnaIdBocadillo.setCellValueFactory(new PropertyValueFactory<>("idBocadillo"));

        TableColumn<BocadillosDAO, String> columnaNombreBocadillo = new TableColumn<>("NOMBRE");
        columnaNombreBocadillo.setCellValueFactory(new PropertyValueFactory<>("nombreBocadillo"));

        TableColumn<BocadillosDAO, String> columnaDescripcion = new TableColumn<>("DESCRIPCIÓN");
        columnaDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));

        TableColumn<BocadillosDAO, Double> columnaPrecio = new TableColumn<>("PRECIO");
        columnaPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));

        TableColumn<BocadillosDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(new Callback<TableColumn<BocadillosDAO, String>, TableCell<BocadillosDAO, String>>() {
            @Override
            public TableCell<BocadillosDAO, String> call(TableColumn<BocadillosDAO, String> param) {
                return new CellCustomBocadillo(1);
            }
        });

        TableColumn<BocadillosDAO, String> tbcBorrar = new TableColumn<>("Borrar");
        tbcBorrar.setCellFactory(new Callback<TableColumn<BocadillosDAO, String>, TableCell<BocadillosDAO, String>>() {
            @Override
            public TableCell<BocadillosDAO, String> call(TableColumn<BocadillosDAO, String> param) {
                return new CellCustomBocadillo(2);
            }
        });

        tbvBocadillos.getColumns().addAll(columnaIdBocadillo, columnaNombreBocadillo, columnaDescripcion, columnaPrecio, tbcEditar, tbcBorrar);
        tbvBocadillos.setItems(bocadilloDAO.SELECCIONAR());
    }
}
