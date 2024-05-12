package com.example.demo.vistas;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import com.example.demo.modelos.UsuariosDAO;
public class UsuariosForms extends VBox {
    private Label lblTitulo; // Nuevo campo para el título
    private TextField txtNombre, txtCorreo, txtPass, txtRol;
    private Button btnGuardar;
    private UsuariosDAO objUDAO;
    private TableView<UsuariosDAO> tbvUsuarios;

    public UsuariosForms(TableView<UsuariosDAO> tbvUsuarios, UsuariosDAO objUDAO){
        this.tbvUsuarios = tbvUsuarios;
        if( objUDAO != null ) {
            this.objUDAO = objUDAO;             // La acción es una actualización
        } else {
            this.objUDAO = new UsuariosDAO();  // La acción es una inserción
            // Inicializar id como 0 para un nuevo usuario
            this.objUDAO.setId(0);
        }
        CrearUI();
    }

    private void CrearUI() {
        lblTitulo = new Label("Nuevo Usuario"); // Crear el título
        lblTitulo.setStyle("-fx-font-size: 20; -fx-font-weight: bold;"); // Estilo del título
        txtNombre = new TextField();
        txtNombre.setPromptText("Nombre Del Usuario");
        txtNombre.setText(objUDAO.getNombre());

        txtCorreo = new TextField();
        txtCorreo.setPromptText("Correo Del Usuario");
        txtCorreo.setText(objUDAO.getCorreo());

        txtPass = new TextField();
        txtPass.setPromptText("Contraseña Del Usuario");
        txtPass.setText(objUDAO.getPass());

        txtRol = new TextField();
        txtRol.setPromptText("Rol Del Usuario");
        txtRol.setText(objUDAO.getRol());

        btnGuardar = new Button("Guardar");

        this.setStyle("-fx-background-color: #f1ffc8;" +
                "-fx-padding: 20;" +
                "-fx-border-color: #ffdecc;" +
                "-fx-border-radius: 10;" +
                "-fx-font-size: 14;" +
                "-fx-font-family: 'Arial';");

        btnGuardar.setOnAction(event -> {
            // Guardar los datos del formulario en la base de datos
            objUDAO.setNombre(txtNombre.getText());
            objUDAO.setCorreo(txtCorreo.getText());
            objUDAO.setPass(txtPass.getText());
            objUDAO.setRol(txtRol.getText());

            if (objUDAO.getId() > 0)
                objUDAO.ACTUALIZAR();
            else
                objUDAO.INSERTAR();

            // Actualizar la lista observable de la tabla con los datos de la base de datos
            tbvUsuarios.setItems(objUDAO.SELECCIONAR());
        });

        this.setSpacing(10.0);
        this.getChildren().addAll(lblTitulo,txtNombre, txtCorreo, txtPass, txtRol, btnGuardar);
    }
}
