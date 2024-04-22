package com.example.demo.vistas;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.example.demo.modelos.PostresDAO;

public class PostresForms extends Stage {

    private Scene escena;
    private VBox vBox;
    private TextField txtNombre, txtDescripcion, txtPrecio;
    private Button btnGuardar;
    private PostresDAO objPDAO;
    private TableView<PostresDAO> tbvPostres;

    public PostresForms(TableView<PostresDAO> tbvPostres, PostresDAO objPDAO){
        this.tbvPostres = tbvPostres;
        if( objPDAO != null ) {
            this.objPDAO = objPDAO;             // La acción es una actualización
        } else {
            this.objPDAO = new PostresDAO();  // La acción es una inserción
            // Inicializar idPostre como 0 para un nuevo postre
            this.objPDAO.setIdPostre(0);
        }
        CrearUI();
        this.setTitle("Formulario De Postres");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {

        txtNombre = new TextField();
        txtNombre.setPromptText("Nombre Del Postre");
        txtNombre.setText(objPDAO.getNombrePostre());

        txtDescripcion = new TextField();
        txtDescripcion.setPromptText("Descripción Del Postre");
        txtDescripcion.setText(objPDAO.getDescripcion());

        txtPrecio = new TextField();
        txtPrecio.setPromptText("Precio Del Postre");
        txtPrecio.setText(Double.toString(objPDAO.getPrecio()));

        btnGuardar = new Button("Guardar");

        btnGuardar.setOnAction(event -> {
            objPDAO.setNombrePostre(txtNombre.getText());
            objPDAO.setDescripcion(txtDescripcion.getText());
            objPDAO.setPrecio(Double.parseDouble(txtPrecio.getText()));

            if( objPDAO.getIdPostre() > 0 )
                objPDAO.ACTUALIZAR();
            else
                objPDAO.INSERTAR();

            tbvPostres.setItems(objPDAO.SELECCIONAR());
            tbvPostres.refresh();

            this.close();
        });

        vBox = new VBox();
        vBox.setSpacing(10.0);
        vBox.setPadding(new Insets(10.0));
        vBox.getChildren().addAll(txtNombre, txtDescripcion, txtPrecio, btnGuardar);
        escena = new Scene(vBox, 300 ,250);
    }
}
