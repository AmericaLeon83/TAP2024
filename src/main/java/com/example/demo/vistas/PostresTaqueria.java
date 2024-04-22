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
import com.example.demo.Componentes.CellCustomPostre;
import com.example.demo.modelos.PostresDAO;

public class PostresTaqueria extends Stage{

    private Scene escena;
    private TableView<PostresDAO> tbvPostres;
    private Button btnAgregar;
    private VBox vBox;
    private PostresDAO postreDAO;

    public PostresTaqueria(){
        postreDAO = new PostresDAO();
        CrearUI();
        this.setTitle("Postres Taqueria :)");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {

        tbvPostres = new TableView<>();
        btnAgregar = new Button("Nuevo Postre");
        postreDAO = new PostresDAO();
        btnAgregar.setOnAction(event -> {
            new PostresForms(tbvPostres, null);
        });

        vBox = new VBox();
        vBox.setSpacing(10.0);
        vBox.setPadding(new Insets(10.0));
        vBox.getChildren().addAll(tbvPostres, btnAgregar);
        escena = new Scene(vBox, 408, 300);
        CrearTabla();
    }

    private void CrearTabla() {
        TableColumn<PostresDAO, Integer> columnaIdPostre = new TableColumn<>("ID");
        columnaIdPostre.setCellValueFactory(new PropertyValueFactory<>("idPostre"));

        TableColumn<PostresDAO, String> columnaNombrePostre = new TableColumn<>("NOMBRE");
        columnaNombrePostre.setCellValueFactory(new PropertyValueFactory<>("nombrePostre"));

        TableColumn<PostresDAO, String> columnaDescripcion = new TableColumn<>("DESCRIPCIÓN");
        columnaDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));

        TableColumn<PostresDAO, Double> columnaPrecio = new TableColumn<>("PRECIO");
        columnaPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));

        TableColumn<PostresDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(new Callback<TableColumn<PostresDAO, String>, TableCell<PostresDAO, String>>() {
            @Override
            public TableCell<PostresDAO, String> call(TableColumn<PostresDAO, String> param) {
                return new CellCustomPostre(1);
            }
        });

        TableColumn<PostresDAO, String> tbcBorrar = new TableColumn<>("Borrar");
        tbcBorrar.setCellFactory(new Callback<TableColumn<PostresDAO, String>, TableCell<PostresDAO, String>>() {
            @Override
            public TableCell<PostresDAO, String> call(TableColumn<PostresDAO, String> param) {
                return new CellCustomPostre(2);
            }
        });

        tbvPostres.getColumns().addAll(columnaIdPostre, columnaNombrePostre, columnaDescripcion, columnaPrecio, tbcEditar, tbcBorrar);
        tbvPostres.setItems(postreDAO.SELECCIONAR());
    }
}
