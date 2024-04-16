/*
NO HACE
AHORA OTRA VEZ NO SE ESTA REGISTRANDO EL PUNTO PARA EL SEGUNDO TURNO NO
IMPORTA EL JUGADOR NI TAMPOCO SE ESTA RESPETANDO LA REGLA DE NUEVO QUE SE
 QUEDEN VOLTEADAS SI YA SE ENCONTRO EL PAR DE LA IMAGEN QUE SE ENCONTRO
 AHORA QUIERO QUE SE QUEDE VOLTEADO SOLO EL PAR QUE YA SE ENCONTRO POR
 CUALQUIERA DE LOS DOS JUGADORES
 */

package com.example.demo.vistas;
///import com.example.demo.vistas.memorama;
import com.example.demo.modelos.Conexion;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.sql.Connection;
import java.io.IOException;

public class HelloApplication extends Application {

    private MenuBar mnbPrincipal;
    private Menu menParcial1, menParcial2, menSalir;
    private MenuItem mitCalculadora,mitMemorama,mitMemorama2,mitCuadroMagico, mitSalir,mitEmpleado;
    private BorderPane bdpPanel;
    @Override
    public void start(Stage stage) throws IOException {
        CrearMenu();
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        bdpPanel = new BorderPane();
        bdpPanel.setTop(mnbPrincipal);
        Scene scene = new Scene(bdpPanel);
        scene.getStylesheets()
                .add(getClass().getResource("/estilos/main.css").toString());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        stage.setMaximized(true);

        Conexion.crearConexion();
    }

    private void CrearMenu() {

        /* Menu primer parcial */
        mitCalculadora = new MenuItem("Calculadora");
        mitCalculadora.setOnAction(actionEvent -> new calculadora());

 mitMemorama = new MenuItem("Memorama");
 mitMemorama.setOnAction(actionEvent -> new memorama());

        mitMemorama2 = new MenuItem("MemoramaPRUEBASORIGINAL");
        mitMemorama2.setOnAction(actionEvent -> new memorama2());

        mitCuadroMagico = new MenuItem("CuadroMagico");
        mitCuadroMagico.setOnAction(actionEvent -> new cuadroMagico());

        mitEmpleado = new MenuItem("Empleado Taqueria");
        mitEmpleado.setOnAction(actionEvent -> new EmpleadoTaqueria());

        menParcial1 = new Menu("Primer Parcial");
       // menParcial1.getItems().addAll(mitCalculadora);
        menParcial1 = new Menu("Primer Parcial");
        menParcial1.getItems().addAll(mitCalculadora);
        menParcial1.getItems().addAll(mitMemorama);
        menParcial1.getItems().addAll(mitMemorama2);
        menParcial1.getItems().addAll(mitCuadroMagico);
        menParcial1.getItems().addAll(mitEmpleado);


        /* Menu segundo parcial */
        menParcial2 = new Menu("Segundo Parcial");

        /* Menu salir */
        mitSalir = new MenuItem("Salir");
        menSalir = new Menu("Salir");
        menSalir.getItems().add(mitSalir);
        mitSalir.setOnAction(event -> System.exit(0));

        mnbPrincipal = new MenuBar();
        mnbPrincipal.getMenus().addAll(menParcial1,menParcial2,menSalir);

    }

    public static void main(String[] args) {
        launch();
    }
}