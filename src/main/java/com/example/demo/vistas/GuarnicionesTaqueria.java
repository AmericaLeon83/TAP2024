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
import com.example.demo.Componentes.CellCustomGuarnicion;
import com.example.demo.modelos.GuarnicionesDAO;

public class GuarnicionesTaqueria extends Stage{

    private Scene escena;
    private TableView<GuarnicionesDAO> tbvGuarniciones;
    private Button btnAgregar;
    private VBox vBox;
    private GuarnicionesDAO guarnicionDAO;

    public GuarnicionesTaqueria(){
        guarnicionDAO = new GuarnicionesDAO();
        CrearUI();
        this.setTitle("Guarniciones Taqueria :)");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {

        tbvGuarniciones = new TableView<>();
        btnAgregar = new Button("Nueva Guarnición");
        guarnicionDAO = new GuarnicionesDAO();
        btnAgregar.setOnAction(event -> {
            new GuarnicionesForms(tbvGuarniciones, null);
        });

        vBox = new VBox();
        vBox.setSpacing(10.0);
        vBox.setPadding(new Insets(10.0));
        vBox.getChildren().addAll(tbvGuarniciones, btnAgregar);
        escena = new Scene(vBox, 408, 300);
        CrearTabla();
    }

    private void CrearTabla() {
        TableColumn<GuarnicionesDAO, Integer> columnaIdGuarnicion = new TableColumn<>("ID");
        columnaIdGuarnicion.setCellValueFactory(new PropertyValueFactory<>("idGuarnicion"));

        TableColumn<GuarnicionesDAO, String> columnaNombreGuarnicion = new TableColumn<>("NOMBRE");
        columnaNombreGuarnicion.setCellValueFactory(new PropertyValueFactory<>("nombreGuarnicion"));

        TableColumn<GuarnicionesDAO, String> columnaDescripcion = new TableColumn<>("DESCRIPCIÓN");
        columnaDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));

        TableColumn<GuarnicionesDAO, Double> columnaPrecio = new TableColumn<>("PRECIO");
        columnaPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));

        TableColumn<GuarnicionesDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(new Callback<TableColumn<GuarnicionesDAO, String>, TableCell<GuarnicionesDAO, String>>() {
            @Override
            public TableCell<GuarnicionesDAO, String> call(TableColumn<GuarnicionesDAO, String> param) {
                return new CellCustomGuarnicion(1);
            }
        });

        TableColumn<GuarnicionesDAO, String> tbcBorrar = new TableColumn<>("Borrar");
        tbcBorrar.setCellFactory(new Callback<TableColumn<GuarnicionesDAO, String>, TableCell<GuarnicionesDAO, String>>() {
            @Override
            public TableCell<GuarnicionesDAO, String> call(TableColumn<GuarnicionesDAO, String> param) {
                return new CellCustomGuarnicion(2);
            }
        });

        tbvGuarniciones.getColumns().addAll(columnaIdGuarnicion, columnaNombreGuarnicion, columnaDescripcion, columnaPrecio, tbcEditar, tbcBorrar);
        tbvGuarniciones.setItems(guarnicionDAO.SELECCIONAR());
    }
}
