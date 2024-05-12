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

import com.example.demo.Componentes.CellCustomUsuario;
import com.example.demo.modelos.UsuariosDAO;
public class UsuarioTaqueria extends Stage{

    private Scene escena;
    private TableView<UsuariosDAO> tbvUsuarios;
    private Button btnAgregar;
    private GridPane gridPane;
    private UsuariosDAO usrDAO;
    private UsuariosForms usuariosForms;

    public UsuarioTaqueria() {
        usrDAO = new UsuariosDAO();
        usuariosForms = new UsuariosForms(tbvUsuarios, null); // Crear el formulario de usuario

        CrearUI();
        this.setTitle("Usuarios Taqueria :)");
        this.setScene(escena);
        this.show();
    }
    private void CrearUI() {
        tbvUsuarios = new TableView<>();
        btnAgregar = new Button("Nuevo Usuario");
        usrDAO = new UsuariosDAO();
        btnAgregar.setOnAction(event -> {
            usuariosForms = new UsuariosForms(tbvUsuarios, null); // Actualizar el formulario de usuario
            gridPane.add(usuariosForms, 1, 0); // Añadir el formulario de usuario al GridPane

        });
        tbvUsuarios.setStyle("-fx-background-color: #fffed1;" +
                "-fx-table-cell-border-color: transparent;" +
                "-fx-padding: 10;" +
                "-fx-font-size: 14;" +
                "-fx-font-family: 'Arial';");
        gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10, 10, 10, 10));

        // Agregar la tabla y el formulario de usuario al GridPane
        gridPane.add(tbvUsuarios, 0, 0);
        gridPane.add(btnAgregar, 0, 1);

        escena = new Scene(gridPane, 800, 450);
        CrearTabla();
    }

    public void actualizarFormulario(UsuariosDAO usuario) {
        usuariosForms = new UsuariosForms(tbvUsuarios, usuario); // Actualizar el formulario de usuario con los datos del usuario seleccionado
        gridPane.add(usuariosForms, 1, 0); // Añadir el formulario de usuario al GridPane
    }

    private void CrearTabla() {
        TableColumn<UsuariosDAO, Integer> columnaIdUsuario = new TableColumn<>("ID");
        columnaIdUsuario.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<UsuariosDAO, String> columnaNombreUsuario = new TableColumn<>("NOMBRE");
        columnaNombreUsuario.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<UsuariosDAO, String> columnaCorreoUsuario = new TableColumn<>("CORREO");
        columnaCorreoUsuario.setCellValueFactory(new PropertyValueFactory<>("correo"));

        TableColumn<UsuariosDAO, String> columnaPassUsuario = new TableColumn<>("CONTRASEÑA");
        columnaPassUsuario.setCellValueFactory(new PropertyValueFactory<>("pass"));

        TableColumn<UsuariosDAO, String> columnaRolUsuario = new TableColumn<>("ROL");
        columnaRolUsuario.setCellValueFactory(new PropertyValueFactory<>("rol"));
        UsuarioTaqueria usuarioTaqueria = this; // Guardar una referencia a this

        TableColumn<UsuariosDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(new Callback<TableColumn<UsuariosDAO, String>, TableCell<UsuariosDAO, String>>() {
            @Override
            public TableCell<UsuariosDAO, String> call(TableColumn<UsuariosDAO, String> param) {
                return new CellCustomUsuario(1, usuarioTaqueria); // Pasar la referencia a UsuarioTaqueria al constructor de CellCustomUsuario
            }
        });

        TableColumn<UsuariosDAO, String> tbcBorrar = new TableColumn<>("Borrar");
        tbcBorrar.setCellFactory(new Callback<TableColumn<UsuariosDAO, String>, TableCell<UsuariosDAO, String>>() {
            @Override
            public TableCell<UsuariosDAO, String> call(TableColumn<UsuariosDAO, String> param) {
                return new CellCustomUsuario(2, usuarioTaqueria); // Pasar la referencia a UsuarioTaqueria al constructor de CellCustomUsuario
            }
        });

        tbvUsuarios.getColumns().addAll(columnaIdUsuario, columnaNombreUsuario, columnaCorreoUsuario, columnaPassUsuario, columnaRolUsuario, tbcEditar, tbcBorrar);
        tbvUsuarios.setItems(usrDAO.SELECCIONAR());



    }
}
