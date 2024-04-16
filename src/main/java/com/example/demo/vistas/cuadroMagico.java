//LEON RODRIGUEZ AMERICA PATRICIA
package com.example.demo.vistas;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Label;

public class cuadroMagico extends Stage {
    private Scene escena;
    private Label lblMensaje;
    private TextField txtTamanioCuadro;
    private Button btnCalcular;
    private Button btnBorrar;
    private GridPane gdpCuadroMagico;
    private final String archivoCuadroMagico = "cuadro_magico.dat";

    public cuadroMagico() {
        // Crear Label para el mensaje
        lblMensaje = new Label("Los números impares mayores o iguales a 3 son 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29, 31, ...");

        // Crear TextField para el tamaño del cuadro
        txtTamanioCuadro = new TextField();
        txtTamanioCuadro.setPromptText("Introduce el tamaño del cuadro");

        // Crear botón para calcular el cuadro mágico
        btnCalcular = new Button("Calcular cuadro mágico");
        btnCalcular.setOnAction(this::calcularCuadroMagico);
        btnCalcular.setId("btnCalcular");

        // Crear botón para borrar el cuadro mágico
        btnBorrar = new Button("Borrar cuadro mágico");
        btnBorrar.setOnAction(this::borrarCuadroMagico);
        btnBorrar.setId("btnBorrar");

        // Crear GridPane para mostrar el cuadro mágico
        gdpCuadroMagico = new GridPane();
        gdpCuadroMagico.setAlignment(Pos.CENTER);
        gdpCuadroMagico.setHgap(10); // Espacio horizontal entre las celdas
        gdpCuadroMagico.setVgap(10); // Espacio vertical entre las celdas

        // Crear contenedor principal
        VBox vContenedorPrincipal = new VBox(lblMensaje, txtTamanioCuadro, btnCalcular, btnBorrar, gdpCuadroMagico);

        vContenedorPrincipal.setSpacing(10);
        vContenedorPrincipal.setAlignment(Pos.CENTER);

        // Crear escena y añadir estilos
        escena = new Scene(vContenedorPrincipal, 800, 600);
        // Agregar el archivo CSS al estilo de la escena
        escena.getStylesheets().add(getClass().getResource("/Estilos/CuadroMagico.css").toString());
        this.setTitle("Cuadro Mágico");
        this.setScene(escena);
        this.show();
    }

    // Método para calcular el cuadro mágico y mostrarlo en el GridPane
    private void calcularCuadroMagico(ActionEvent event) {
        // Obtener el tamaño del cuadro del TextField
        int tamanio = Integer.parseInt(txtTamanioCuadro.getText());

        // Verificar que el tamaño del cuadro sea válido
        if (tamanio < 3 || tamanio % 2 == 0) {
            System.out.println("El tamaño del cuadro debe ser un número impar mayor o igual a 3.");
            return;
        }

        // Limpiar el GridPane
        gdpCuadroMagico.getChildren().clear();

        // Calcular el cuadro mágico
        generarCuadroMagico(tamanio);

        // Escribir el cuadro mágico en el archivo de acceso aleatorio
        escribirCuadroMagicoEnArchivo(tamanio);
    }

    // Método para borrar el cuadro mágico
    private void borrarCuadroMagico(ActionEvent event) {
        // Limpiar el GridPane y el TextField
        gdpCuadroMagico.getChildren().clear();
        txtTamanioCuadro.clear();
    }

    private void generarCuadroMagico(int tamanio) {
        int numero = 1;
        int fila = 0;
        int columna = tamanio / 2;

        while (numero <= tamanio * tamanio) {
            Text textoNumero = new Text(String.valueOf(numero));
            StackPane celda = new StackPane();
            celda.setStyle("-fx-border-color: #2F4F4F; -fx-background-color: #76D7C4;");
            celda.getChildren().add(textoNumero);
            gdpCuadroMagico.add(celda, columna, fila);

            // Calcular la posición siguiente
            int nuevaFila = (fila - 1 + tamanio) % tamanio;
            int nuevaColumna = (columna + 1) % tamanio;

            // Verificar si la siguiente celda está ocupada
            if (gdpCuadroMagico.getChildren().contains(getCelda(gdpCuadroMagico, nuevaFila, nuevaColumna))) {
                fila = (fila + 1) % tamanio;
            } else {
                fila = nuevaFila;
                columna = nuevaColumna;
            }
            numero++;
        }
    }

    // Método para obtener una celda específica del GridPane
    private StackPane getCelda(GridPane gridPane, int fila, int columna) {
        for (int i = 0; i < gridPane.getChildren().size(); i++) {
            if (GridPane.getRowIndex(gridPane.getChildren().get(i)) == fila
                    && GridPane.getColumnIndex(gridPane.getChildren().get(i)) == columna) {
                return (StackPane) gridPane.getChildren().get(i);
            }
        }
        return null;
    }
    // Método para escribir el cuadro mágico en un archivo de acceso aleatorio
    private void escribirCuadroMagicoEnArchivo(int tamanio) {
        try {
            RandomAccessFile archivo = new RandomAccessFile(new File(archivoCuadroMagico), "rw");

            for (int fila = 0; fila < tamanio; fila++) {
                for (int columna = 0; columna < tamanio; columna++) {
                    StackPane celda = getCelda(gdpCuadroMagico, fila, columna);
                    int numero = Integer.parseInt(((Text) celda.getChildren().get(0)).getText());
                    archivo.writeInt(numero);
                }
            }
            archivo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

/*import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Label;

public class cuadroMagico extends Stage {
    private Scene escena;
    private Label lblMensaje;
    private TextField txtTamanioCuadro;
    private Button btnCalcular;
    private Button btnBorrar;
    private GridPane gdpCuadroMagico;

    public cuadroMagico() {
        // Crear Label para el mensaje
        lblMensaje = new Label("Los números impares mayores o iguales a 3 son 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29, 31, ...");

        // Crear TextField para el tamaño del cuadro
        txtTamanioCuadro = new TextField();
        txtTamanioCuadro.setPromptText("Introduce el tamaño del cuadro");

        // Crear botón para calcular el cuadro mágico
        btnCalcular = new Button("Calcular cuadro mágico");
        btnCalcular.setOnAction(this::calcularCuadroMagico);
        // Crear botón para borrar el cuadro mágico
        btnBorrar = new Button("Borrar cuadro mágico");
        btnBorrar.setOnAction(this::borrarCuadroMagico);


        // Crear GridPane para mostrar el cuadro mágico
        gdpCuadroMagico = new GridPane();
        gdpCuadroMagico.setAlignment(Pos.CENTER);
        gdpCuadroMagico.setHgap(10); // Espacio horizontal entre las celdas
        gdpCuadroMagico.setVgap(10); // Espacio vertical entre las celdas

        // Crear contenedor principal
        VBox vContenedorPrincipal = new VBox(lblMensaje, txtTamanioCuadro, btnCalcular, btnBorrar, gdpCuadroMagico);

        vContenedorPrincipal.setSpacing(10);
        vContenedorPrincipal.setAlignment(Pos.CENTER);

        // Crear escena y añadir estilos
        escena = new Scene(vContenedorPrincipal, 800, 600);
        // Agregar el archivo CSS al estilo de la escena
        escena.getStylesheets().add(getClass().getResource("/Estilos/CuadroMagico.css").toString());
        this.setTitle("Cuadro Mágico");
        this.setScene(escena);
        this.show();
    }

    // Método para calcular el cuadro mágico y mostrarlo en el GridPane
    private void calcularCuadroMagico(ActionEvent event) {
        // Obtener el tamaño del cuadro del TextField
        int tamanio = Integer.parseInt(txtTamanioCuadro.getText());

        // Verificar que el tamaño del cuadro sea válido
        if (tamanio < 3 || tamanio % 2 == 0) {
            System.out.println("El tamaño del cuadro debe ser un número impar mayor o igual a 3.");
            return;
        }

        // Limpiar el GridPane
        gdpCuadroMagico.getChildren().clear();

        // Calcular el cuadro mágico
        generarCuadroMagico(tamanio);
    }

    // Método para borrar el cuadro mágico
    private void borrarCuadroMagico(ActionEvent event) {
        // Limpiar el GridPane y el TextField
        gdpCuadroMagico.getChildren().clear();
        txtTamanioCuadro.clear();
    }

    private void generarCuadroMagico(int tamanio) {
        int numero = 1;
        int fila = 0;
        int columna = tamanio / 2;

        while (numero <= tamanio * tamanio) {
            Text textoNumero = new Text(String.valueOf(numero));
            StackPane celda = new StackPane();
            celda.setStyle("-fx-border-color: black; -fx-background-color: #76D7C4;");
            celda.getChildren().add(textoNumero);
            gdpCuadroMagico.add(celda, columna, fila);

            // Calcular la posición siguiente
            int nuevaFila = (fila - 1 + tamanio) % tamanio;
            int nuevaColumna = (columna + 1) % tamanio;

            // Verificar si la siguiente celda está ocupada
            if (gdpCuadroMagico.getChildren().contains(getCelda(gdpCuadroMagico, nuevaFila, nuevaColumna))) {
                fila = (fila + 1) % tamanio;
            } else {
                fila = nuevaFila;
                columna = nuevaColumna;
            }
            numero++;
        }
    }

    // Método para obtener una celda específica del GridPane
    private StackPane getCelda(GridPane gridPane, int fila, int columna) {
        for (int i = 0; i < gridPane.getChildren().size(); i++) {
            if (GridPane.getRowIndex(gridPane.getChildren().get(i)) == fila
                    && GridPane.getColumnIndex(gridPane.getChildren().get(i)) == columna) {
                return (StackPane) gridPane.getChildren().get(i);
            }
        }
        return null;
    }
}*/
