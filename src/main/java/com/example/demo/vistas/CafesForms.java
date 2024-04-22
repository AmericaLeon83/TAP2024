package com.example.demo.vistas;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.example.demo.modelos.CafesDAO;


public class CafesForms extends Stage {

    private Scene scene;
    private VBox vBox;
    private TextField txtNombre, txtDescripcion, txtPrecio;
    private Button btnGuardar;
    private CafesDAO objCafeDAO;
    private TableView<CafesDAO> tbvCafes;

    public CafesForms(TableView<CafesDAO> tbvCafes, CafesDAO objCafeDAO){
        this.tbvCafes = tbvCafes;
        if( objCafeDAO != null ) {
            this.objCafeDAO = objCafeDAO;             // La acción es una actualización
        } else {
            this.objCafeDAO = new CafesDAO();  // La acción es una inserción
            // Inicializar idCafe como 0 para un nuevo café
            this.objCafeDAO.setIdCafe(0);
        }
        crearUI();
        this.setTitle("Formulario De Cafés");
        this.setScene(scene);
        this.show();
    }

    private void crearUI() {

        txtNombre = new TextField();
        txtNombre.setPromptText("Nombre Del Café");
        txtNombre.setText(objCafeDAO.getNombreCafe());

        txtDescripcion = new TextField();
        txtDescripcion.setPromptText("Descripción Del Café");
        txtDescripcion.setText(objCafeDAO.getDescripcion());

        txtPrecio = new TextField();
        txtPrecio.setPromptText("Precio Del Café");
        txtPrecio.setText(Double.toString(objCafeDAO.getPrecio()));

        btnGuardar = new Button("Guardar");

        btnGuardar.setOnAction(event -> {
            objCafeDAO.setNombreCafe(txtNombre.getText());
            objCafeDAO.setDescripcion(txtDescripcion.getText());
            objCafeDAO.setPrecio(Double.parseDouble(txtPrecio.getText()));

            if( objCafeDAO.getIdCafe() > 0 )
                objCafeDAO.actualizar();
            else
                objCafeDAO.insertar();

            tbvCafes.setItems(objCafeDAO.seleccionar());
            tbvCafes.refresh();

            this.close();
        });

        vBox = new VBox();
        vBox.setSpacing(10.0);
        vBox.setPadding(new Insets(10.0));
        vBox.getChildren().addAll(txtNombre, txtDescripcion, txtPrecio, btnGuardar);
        scene = new Scene(vBox, 300 ,250);
    }
}
