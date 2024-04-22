package com.example.demo.vistas;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.example.demo.modelos.GuarnicionesDAO;

public class GuarnicionesForms extends Stage {

    private Scene escena;
    private VBox vBox;
    private TextField txtNombre, txtDescripcion, txtPrecio;
    private Button btnGuardar;
    private GuarnicionesDAO objGDAO;
    private TableView<GuarnicionesDAO> tbvGuarniciones;

    public GuarnicionesForms(TableView<GuarnicionesDAO> tbvGuarniciones, GuarnicionesDAO objGDAO){
        this.tbvGuarniciones = tbvGuarniciones;
        if( objGDAO != null ) {
            this.objGDAO = objGDAO;             // La acción es una actualización
        } else {
            this.objGDAO = new GuarnicionesDAO();  // La acción es una inserción
            // Inicializar idGuarnicion como 0 para una nueva guarnición
            this.objGDAO.setIdGuarnicion(0);
        }
        CrearUI();
        this.setTitle("Formulario De Guarniciones");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {

        txtNombre = new TextField();
        txtNombre.setPromptText("Nombre De La Guarnición");
        txtNombre.setText(objGDAO.getNombreGuarnicion());

        txtDescripcion = new TextField();
        txtDescripcion.setPromptText("Descripción De La Guarnición");
        txtDescripcion.setText(objGDAO.getDescripcion());

        txtPrecio = new TextField();
        txtPrecio.setPromptText("Precio De La Guarnición");
        txtPrecio.setText(Double.toString(objGDAO.getPrecio()));

        btnGuardar = new Button("Guardar");

        btnGuardar.setOnAction(event -> {
            objGDAO.setNombreGuarnicion(txtNombre.getText());
            objGDAO.setDescripcion(txtDescripcion.getText());
            objGDAO.setPrecio(Double.parseDouble(txtPrecio.getText()));

            if( objGDAO.getIdGuarnicion() > 0 )
                objGDAO.ACTUALIZAR();
            else
                objGDAO.INSERTAR();

            tbvGuarniciones.setItems(objGDAO.SELECCIONAR());
            tbvGuarniciones.refresh();

            this.close();
        });

        vBox = new VBox();
        vBox.setSpacing(10.0);
        vBox.setPadding(new Insets(10.0));
        vBox.getChildren().addAll(txtNombre, txtDescripcion, txtPrecio, btnGuardar);
        escena = new Scene(vBox, 300 ,250);
    }
}
