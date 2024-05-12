package com.example.demo.vistas;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import com.example.demo.Componentes.CellCustomPlato;
import com.example.demo.modelos.PlatosDAO;
public class PlatosTaqueria extends Stage{

    private Scene escena;
    private TableView<PlatosDAO> tbvPlatos;
    private Button btnAgregar;
    private GridPane gridPane;
    private PlatosDAO platDAO;
    private PlatosForms platosForms;

    public PlatosTaqueria() {
        platDAO = new PlatosDAO();
        platosForms = new PlatosForms(tbvPlatos, null); // Crear el formulario de plato

        CrearUI();
        this.setTitle("Platos Taqueria :)");
        this.setScene(escena);
        this.show();
    }
    private void CrearUI() {
        tbvPlatos = new TableView<>();
        btnAgregar = new Button("Nuevo Plato");
        platDAO = new PlatosDAO();
        btnAgregar.setOnAction(event -> {
            platosForms = new PlatosForms(tbvPlatos, null); // Actualizar el formulario de plato
            gridPane.add(platosForms, 1, 0); // Añadir el formulario de plato al GridPane

        });
        tbvPlatos.setStyle("-fx-background-color: #fffed1;" +
                "-fx-table-cell-border-color: transparent;" +
                "-fx-padding: 10;" +
                "-fx-font-size: 14;" +
                "-fx-font-family: 'Arial';");
        gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10, 10, 10, 10));

        // Agregar la tabla y el formulario de plato al GridPane
        gridPane.add(tbvPlatos, 0, 0);
        gridPane.add(btnAgregar, 0, 1);

        escena = new Scene(gridPane, 800, 450);
        CrearTabla();
    }

    public void actualizarFormulario(PlatosDAO plato) {
        platosForms = new PlatosForms(tbvPlatos, plato); // Actualizar el formulario de plato con los datos del plato seleccionado
        gridPane.add(platosForms, 1, 0); // Añadir el formulario de plato al GridPane
    }

    private void CrearTabla() {
        TableColumn<PlatosDAO, Integer> columnaIdPlato = new TableColumn<>("ID");
        columnaIdPlato.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<PlatosDAO, String> columnaNombrePlato = new TableColumn<>("NOMBRE");
        columnaNombrePlato.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<PlatosDAO, Double> columnaPrecioPlato = new TableColumn<>("PRECIO");
        columnaPrecioPlato.setCellValueFactory(new PropertyValueFactory<>("precio"));

        TableColumn<PlatosDAO, String> columnaFechaPlato = new TableColumn<>("FECHA");
        columnaFechaPlato.setCellValueFactory(new PropertyValueFactory<>("fecha"));

        TableColumn<PlatosDAO, String> columnaCategoriaPlato = new TableColumn<>("CATEGORIA");
        columnaCategoriaPlato.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        PlatosTaqueria platosTaqueria = this; // Guardar una referencia a this

        TableColumn<PlatosDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(new Callback<TableColumn<PlatosDAO, String>, TableCell<PlatosDAO, String>>() {
            @Override
            public TableCell<PlatosDAO, String> call(TableColumn<PlatosDAO, String> param) {
                return new CellCustomPlato(1, platosTaqueria); // Pasar la referencia a PlatosTaqueria al constructor de CellCustomPlato
            }
        });

        TableColumn<PlatosDAO, String> tbcBorrar = new TableColumn<>("Borrar");
        tbcBorrar.setCellFactory(new Callback<TableColumn<PlatosDAO, String>, TableCell<PlatosDAO, String>>() {
            @Override
            public TableCell<PlatosDAO, String> call(TableColumn<PlatosDAO, String> param) {
                return new CellCustomPlato(2, platosTaqueria); // Pasar la referencia a PlatosTaqueria al constructor de CellCustomPlato
            }
        });

        tbvPlatos.getColumns().addAll(columnaIdPlato, columnaNombrePlato, columnaPrecioPlato, columnaFechaPlato, tbcEditar, tbcBorrar);
        tbvPlatos.setItems(platDAO.SELECCIONAR());
    }
}

/*package sample.views;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import sample.components.CellCustomPlato;
import sample.models.PlatosDAO;
import sample.views.PlatosForms;
public class PlatosTaqueria extends Stage {

    private Scene escena;
    private TableView<PlatosDAO> tbvPlatos;
    private Button btnAgregar;
    private GridPane gridPane;
    private PlatosDAO platoDAO;
    private PlatosForms platosForms;

    public PlatosTaqueria() {
        platoDAO = new PlatosDAO();

        CrearUI();
        this.setTitle("Platos Taqueria :)");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        tbvPlatos = new TableView<>();
        btnAgregar = new Button("Nuevo Plato");
        platoDAO = new PlatosDAO();
        btnAgregar.setOnAction(event -> {
            platosForms = new PlatosForms(tbvPlatos, null); // Actualizar el formulario de plato
            gridPane.add(platosForms, 1, 0); // Añadir el formulario de plato al GridPane
        });
        tbvPlatos.setStyle("-fx-background-color: #fffed1;" +
                "-fx-table-cell-border-color: transparent;" +
                "-fx-padding: 10;" +
                "-fx-font-size: 14;" +
                "-fx-font-family: 'Arial';");
        gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10, 10, 10, 10));

        // Agregar la tabla y el formulario de plato al GridPane
        gridPane.add(tbvPlatos, 0, 0);
        gridPane.add(btnAgregar, 0, 1);

        escena = new Scene(gridPane, 800, 450);
        CrearTabla();

        platosForms = new PlatosForms(tbvPlatos, null); // Crear el formulario de plato
    }

    public void actualizarFormulario(PlatosDAO plato) {
        platosForms = new PlatosForms(tbvPlatos, plato); // Actualizar el formulario de plato con los datos del plato seleccionado
        gridPane.add(platosForms, 1, 0); // Añadir el formulario de plato al GridPane
    }


/*public class PlatosTaqueria extends Stage {

    private Scene escena;
    private TableView<PlatosDAO> tbvPlatos;
    private Button btnAgregar;
    private GridPane gridPane;
    private PlatosDAO platoDAO;
    private PlatosForms platosForms;

    public PlatosTaqueria() {
        platoDAO = new PlatosDAO();
        platosForms = new PlatosForms(tbvPlatos, null); // Crear el formulario de plato

        CrearUI();
        this.setTitle("Platos Taqueria :)");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        tbvPlatos = new TableView<>();
        btnAgregar = new Button("Nuevo Plato");
        platoDAO = new PlatosDAO();
        btnAgregar.setOnAction(event -> {
            platosForms = new PlatosForms(tbvPlatos, null); // Actualizar el formulario de plato
            gridPane.add(platosForms, 1, 0); // Añadir el formulario de plato al GridPane
        });
        tbvPlatos.setStyle("-fx-background-color: #fffed1;" +
                "-fx-table-cell-border-color: transparent;" +
                "-fx-padding: 10;" +
                "-fx-font-size: 14;" +
                "-fx-font-family: 'Arial';");
        gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10, 10, 10, 10));

        // Agregar la tabla y el formulario de plato al GridPane
        gridPane.add(tbvPlatos, 0, 0);
        gridPane.add(btnAgregar, 0, 1);

        escena = new Scene(gridPane, 800, 450);
        CrearTabla();
    }

    public void actualizarFormulario(PlatosDAO plato) {
        platosForms = new PlatosForms(tbvPlatos, plato); // Actualizar el formulario de plato con los datos del plato seleccionado
        gridPane.add(platosForms, 1, 0); // Añadir el formulario de plato al GridPane
    }*/

    /*private void CrearTabla() {
        TableColumn<PlatosDAO, Integer> columnaIdPlato = new TableColumn<>("ID");
        columnaIdPlato.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<PlatosDAO, String> columnaNombrePlato = new TableColumn<>("NOMBRE");
        columnaNombrePlato.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnaNombrePlato.setCellFactory(column -> {
            return new TableCell<PlatosDAO, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        setText(item);

                        PlatosDAO plato = getTableView().getItems().get(getIndex());
                        String iconURL = IconManager.getIconURL(plato.getNombre(), plato.getCategoria());
                        if (iconURL != null) {
                            ImageView iconView = new ImageView(new Image(iconURL));
                            iconView.setFitHeight(20);
                            iconView.setFitWidth(20);
                            setGraphic(iconView);
                        } else {
                            setGraphic(null);
                        }
                    }
                }
            };
        });

        TableColumn<PlatosDAO, Double> columnaPrecioPlato = new TableColumn<>("PRECIO");
        columnaPrecioPlato.setCellValueFactory(new PropertyValueFactory<>("precio"));

        TableColumn<PlatosDAO, String> columnaFechaPlato = new TableColumn<>("FECHA");
        columnaFechaPlato.setCellValueFactory(new PropertyValueFactory<>("fecha"));

        PlatosTaqueria platosTaqueria = this; // Guardar una referencia a this

        TableColumn<PlatosDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(new Callback<TableColumn<PlatosDAO, String>, TableCell<PlatosDAO, String>>() {
            @Override
            public TableCell<PlatosDAO, String> call(TableColumn<PlatosDAO, String> param) {
                return new CellCustomPlato(1, platosTaqueria); // Pasar la referencia a PlatosTaqueria al constructor de CellCustomPlato
            }
        });

        TableColumn<PlatosDAO, String> tbcBorrar = new TableColumn<>("Borrar");
        tbcBorrar.setCellFactory(new Callback<TableColumn<PlatosDAO, String>, TableCell<PlatosDAO, String>>() {
            @Override
            public TableCell<PlatosDAO, String> call(TableColumn<PlatosDAO, String> param) {
                return new CellCustomPlato(2, platosTaqueria); // Pasar la referencia a PlatosTaqueria al constructor de CellCustomPlato
            }
        });

        tbvPlatos.getColumns().addAll(columnaIdPlato, columnaNombrePlato, columnaPrecioPlato, columnaFechaPlato, tbcEditar, tbcBorrar);
        tbvPlatos.setItems(platoDAO.SELECCIONAR());
    }
}

/*package sample.views;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import sample.components.CellCustomPlato;
import sample.models.PlatosDAO;
import sample.views.PlatosForms;

public class PlatosTaqueria extends Stage{

    private Scene escena;
    private TableView<PlatosDAO> tbvPlatos;
    private Button btnAgregar;
    private GridPane gridPane;
    private PlatosDAO platoDAO;
    private PlatosForms platosForms;

    public PlatosTaqueria() {
        platoDAO = new PlatosDAO();
        platosForms = new PlatosForms(tbvPlatos, null); // Crear el formulario de plato

        CrearUI();
        this.setTitle("Platos Taqueria :)");
        this.setScene(escena);
        this.show();
    }
    private void CrearUI() {
        tbvPlatos = new TableView<>();
        btnAgregar = new Button("Nuevo Plato");
        platoDAO = new PlatosDAO();
        btnAgregar.setOnAction(event -> {
            platosForms = new PlatosForms(tbvPlatos, null); // Actualizar el formulario de plato
            gridPane.add(platosForms, 1, 0); // Añadir el formulario de plato al GridPane

        });
        tbvPlatos.setStyle("-fx-background-color: #fffed1;" +
                "-fx-table-cell-border-color: transparent;" +
                "-fx-padding: 10;" +
                "-fx-font-size: 14;" +
                "-fx-font-family: 'Arial';");
        gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10, 10, 10, 10));

        // Agregar la tabla y el formulario de plato al GridPane
        gridPane.add(tbvPlatos, 0, 0);
        gridPane.add(btnAgregar, 0, 1);

        escena = new Scene(gridPane, 800, 450);
        CrearTabla();
    }

    public void actualizarFormulario(PlatosDAO plato) {
        platosForms = new PlatosForms(tbvPlatos, plato); // Actualizar el formulario de plato con los datos del plato seleccionado
        gridPane.add(platosForms, 1, 0); // Añadir el formulario de plato al GridPane
    }

    private void CrearTabla() {
        TableColumn<PlatosDAO, Integer> columnaIdPlato = new TableColumn<>("ID");
        columnaIdPlato.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<PlatosDAO, String> columnaNombrePlato = new TableColumn<>("NOMBRE");
        columnaNombrePlato.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<PlatosDAO, Double> columnaPrecioPlato = new TableColumn<>("PRECIO");
        columnaPrecioPlato.setCellValueFactory(new PropertyValueFactory<>("precio"));

        TableColumn<PlatosDAO, String> columnaFechaPlato = new TableColumn<>("FECHA");
        columnaFechaPlato.setCellValueFactory(new PropertyValueFactory<>("fecha"));

        PlatosTaqueria platosTaqueria = this; // Guardar una referencia a this

        TableColumn<PlatosDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(new Callback<TableColumn<PlatosDAO, String>, TableCell<PlatosDAO, String>>() {
            @Override
            public TableCell<PlatosDAO, String> call(TableColumn<PlatosDAO, String> param) {
                return new CellCustomPlato(1, platosTaqueria); // Pasar la referencia a PlatosTaqueria al constructor de CellCustomPlato
            }

        });

        TableColumn<PlatosDAO, String> tbcBorrar = new TableColumn<>("Borrar");
        tbcBorrar.setCellFactory(new Callback<TableColumn<PlatosDAO, String>, TableCell<PlatosDAO, String>>() {
            @Override
            public TableCell<PlatosDAO, String> call(TableColumn<PlatosDAO, String> param) {
                return new CellCustomPlato(2, platosTaqueria); // Pasar la referencia a PlatosTaqueria al constructor de CellCustomPlato
            }

        });

        tbvPlatos.getColumns().addAll(columnaIdPlato, columnaNombrePlato, columnaPrecioPlato, columnaFechaPlato, tbcEditar, tbcBorrar);
        tbvPlatos.setItems(platoDAO.SELECCIONAR());
    }
}
*/