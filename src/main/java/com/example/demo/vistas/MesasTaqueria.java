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

import com.example.demo.Componentes.CellCustomMesa;
import com.example.demo.modelos.MesaDAO;

public class MesasTaqueria extends Stage{

    private Scene escena;
    private TableView<MesaDAO> tbvMesas;
    private Button btnAgregar;
    private GridPane gridPane;
    private MesaDAO mesaDAO;
    private MesaForms mesasForms;

    public MesasTaqueria() {
        mesaDAO = new MesaDAO();
        mesasForms = new MesaForms(tbvMesas, null); // Crear el formulario de mesa

        CrearUI();
        this.setTitle("Mesas Taqueria :)");
        this.setScene(escena);
        this.show();
    }
    private void CrearUI() {
        tbvMesas = new TableView<>();
        btnAgregar = new Button("Nueva Mesa");
        mesaDAO = new MesaDAO();
        btnAgregar.setOnAction(event -> {
            mesasForms = new MesaForms(tbvMesas, null); // Actualizar el formulario de mesa
            gridPane.add(mesasForms, 1, 0); // Añadir el formulario de mesa al GridPane

        });
        tbvMesas.setStyle("-fx-background-color: #fffed1;" +
                "-fx-table-cell-border-color: transparent;" +
                "-fx-padding: 10;" +
                "-fx-font-size: 14;" +
                "-fx-font-family: 'Arial';");
        gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10, 10, 10, 10));

        // Agregar la tabla y el formulario de mesa al GridPane
        gridPane.add(tbvMesas, 0, 0);
        gridPane.add(btnAgregar, 0, 1);

        escena = new Scene(gridPane, 800, 450);
        CrearTabla();
    }

    public void actualizarFormulario(MesaDAO mesa) {
        mesasForms = new MesaForms(tbvMesas, mesa); // Actualizar el formulario de mesa con los datos de la mesa seleccionada
        gridPane.add(mesasForms, 1, 0); // Añadir el formulario de mesa al GridPane
    }

    private void CrearTabla() {
        TableColumn<MesaDAO, Integer> columnaIdMesa = new TableColumn<>("ID");
        columnaIdMesa.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<MesaDAO, Integer> columnaNumeroMesa = new TableColumn<>("Número de Mesa");
        columnaNumeroMesa.setCellValueFactory(new PropertyValueFactory<>("numeroMesa"));

        TableColumn<MesaDAO, Integer> columnaCapacidad = new TableColumn<>("Capacidad");
        columnaCapacidad.setCellValueFactory(new PropertyValueFactory<>("capacidad"));

        TableColumn<MesaDAO, String> columnaEstado = new TableColumn<>("Estado");
        columnaEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        MesasTaqueria mesasTaqueria = this; // Guardar una referencia a this

        TableColumn<MesaDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(new Callback<TableColumn<MesaDAO, String>, TableCell<MesaDAO, String>>() {
            @Override
            public TableCell<MesaDAO, String> call(TableColumn<MesaDAO, String> param) {
                return new CellCustomMesa(1, mesasTaqueria); // Pasar la referencia a MesasTaqueria al constructor de CellCustomMesa
            }
        });

        TableColumn<MesaDAO, String> tbcBorrar = new TableColumn<>("Borrar");
        tbcBorrar.setCellFactory(new Callback<TableColumn<MesaDAO, String>, TableCell<MesaDAO, String>>() {
            @Override
            public TableCell<MesaDAO, String> call(TableColumn<MesaDAO, String> param) {
                return new CellCustomMesa(2, mesasTaqueria); // Pasar la referencia a MesasTaqueria al constructor de CellCustomMesa
            }
        });

        tbvMesas.getColumns().addAll(columnaIdMesa, columnaNumeroMesa, columnaCapacidad, columnaEstado, tbcEditar, tbcBorrar);
        tbvMesas.setItems(mesaDAO.SELECCIONAR());
    }
}
