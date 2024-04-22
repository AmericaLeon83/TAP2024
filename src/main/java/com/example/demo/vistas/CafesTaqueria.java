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
import com.example.demo.Componentes.CellCustomCafe;
import com.example.demo.modelos.CafesDAO;

public class CafesTaqueria extends Stage {

    private Scene scene;
    private TableView<CafesDAO> tbvCafes;
    private Button btnAgregar;
    private VBox vBox;
    private CafesDAO cafeDAO;

    public CafesTaqueria() {
        cafeDAO = new CafesDAO();
        crearUI();
        this.setTitle("Cafés Taqueria :)");
        this.setScene(scene);
        this.show();
    }

    private void crearUI() {

        tbvCafes = new TableView<>();
        btnAgregar = new Button("Nuevo Café");
        btnAgregar.setOnAction(event -> {
            new CafesForms(tbvCafes, null);
        });

        vBox = new VBox();
        vBox.setSpacing(10.0);
        vBox.setPadding(new Insets(10.0));
        vBox.getChildren().addAll(tbvCafes, btnAgregar);
        scene = new Scene(vBox, 408, 300);
        crearTabla();
    }

    private void crearTabla() {
        TableColumn<CafesDAO, Integer> columnaIdCafe = new TableColumn<>("ID");
        columnaIdCafe.setCellValueFactory(new PropertyValueFactory<>("idCafe"));

        TableColumn<CafesDAO, String> columnaNombreCafe = new TableColumn<>("NOMBRE");
        columnaNombreCafe.setCellValueFactory(new PropertyValueFactory<>("nombreCafe"));

        TableColumn<CafesDAO, String> columnaDescripcion = new TableColumn<>("DESCRIPCIÓN");
        columnaDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));

        TableColumn<CafesDAO, Double> columnaPrecio = new TableColumn<>("PRECIO");
        columnaPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));

        TableColumn<CafesDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(new Callback<TableColumn<CafesDAO, String>, TableCell<CafesDAO, String>>() {
            @Override
            public TableCell<CafesDAO, String> call(TableColumn<CafesDAO, String> param) {
                return new CellCustomCafe(1);
            }
        });

        TableColumn<CafesDAO, String> tbcBorrar = new TableColumn<>("Borrar");
        tbcBorrar.setCellFactory(new Callback<TableColumn<CafesDAO, String>, TableCell<CafesDAO, String>>() {
            @Override
            public TableCell<CafesDAO, String> call(TableColumn<CafesDAO, String> param) {
                return new CellCustomCafe(2);
            }
        });

        tbvCafes.getColumns().addAll(columnaIdCafe, columnaNombreCafe, columnaDescripcion, columnaPrecio, tbcEditar, tbcBorrar);
        tbvCafes.setItems(cafeDAO.seleccionar());
    }
}
