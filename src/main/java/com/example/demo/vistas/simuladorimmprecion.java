// LEON RODRIGUEZ AMERICA PATRICIA
// SIMULADOR DE IMPRESION
package com.example.demo.vistas;

import com.example.demo.Componentes.impresion;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class simuladorimmprecion extends Stage {

    private TableView<impresion> Tabla_de_tareas;
    private ProgressBar Barra_de_progreso;
    private Button Agregar_tarea, Control_del_simulador;
    private ObservableList<impresion> Lista_de_tareas;
    private SimuladorHilo Hilo_del_simulador;
    private boolean Simulador_activo;
    private TableColumn<impresion, String> Número_de_archivo, Nombre, Hora_de_acceso;
    private TableColumn<impresion, Integer> Hojas;
    private Random Aleatorio;
    private SimpleDateFormat Formato_de_fecha;
    private String Nombre_de_archivo, Hora_de_acceso_del_archivo;
    private int Número_de_hojas;
    private impresion Tarea, Tarea_actual;

    public simuladorimmprecion() {
        Crear_UI();
        Hilo_del_simulador = new SimuladorHilo();
        Simulador_activo = false;
        this.setTitle("Simulador de impresión");
        this.setScene(new Scene(CrearVistaPrincipal(), 780, 600)); // Ajusta el tamaño de la ventana aquí
        this.getScene().getStylesheets().add(getClass().getResource("/estilos/styles.css").toExternalForm());
        this.show();
    }

    private VBox CrearVistaPrincipal() {
        VBox vbox = new VBox(10, CrearEncabezado(), Tabla_de_tareas, CrearControles());
        vbox.setStyle("-fx-padding: 20;"); // Añade padding para un mejor diseño
        vbox.getStyleClass().add("root-pane"); // Añade la clase de estilo para el fondo
        return vbox;
    }

    private void Crear_UI() {
        Tabla_de_tareas = new TableView<>();
        Lista_de_tareas = FXCollections.observableArrayList();
        Tabla_de_tareas.setItems(Lista_de_tareas);

        Número_de_archivo = new TableColumn<>("Número de archivo");
        Número_de_archivo.setCellValueFactory(new PropertyValueFactory<>("Número_de_archivo"));
        Número_de_archivo.setPrefWidth(150); // Ajusta el ancho de la columna

        Nombre = new TableColumn<>("Nombre de archivo");
        Nombre.setCellValueFactory(new PropertyValueFactory<>("Nombre_de_archivo"));
        Nombre.setPrefWidth(250); // Ajusta el ancho de la columna

        Hojas = new TableColumn<>("Número de hojas");
        Hojas.setCellValueFactory(new PropertyValueFactory<>("Número_de_hojas"));
        Hojas.setPrefWidth(150); // Ajusta el ancho de la columna

        Hora_de_acceso = new TableColumn<>("Hora de acceso");
        Hora_de_acceso.setCellValueFactory(new PropertyValueFactory<>("Hora_de_acceso"));
        Hora_de_acceso.setPrefWidth(150); // Ajusta el ancho de la columna

        Tabla_de_tareas.getColumns().addAll(Número_de_archivo, Nombre, Hojas, Hora_de_acceso);
        Tabla_de_tareas.getStyleClass().add("table-view");

        Barra_de_progreso = new ProgressBar(0);
        Barra_de_progreso.setPrefWidth(400);
        Barra_de_progreso.getStyleClass().add("progress-bar");

        Agregar_tarea = new Button("Agregar tarea");
        Agregar_tarea.setOnAction(event -> agregarTarea());
        Agregar_tarea.getStyleClass().add("button");

        Control_del_simulador = new Button("Iniciar simulador");
        Control_del_simulador.setOnAction(event -> controlarSimulador());
        Control_del_simulador.getStyleClass().add("button");
    }

    private HBox CrearEncabezado() {
        Label encabezado = new Label("Simulador de Impresión");
        encabezado.getStyleClass().add("label-header");

        HBox hbox = new HBox(encabezado);
        hbox.setStyle("-fx-padding: 10 0 10 0; -fx-alignment: center;");
        return hbox;
    }

    private HBox CrearControles() {
        ImageView imageView = new ImageView(new Image("file:src/main/resources/images/imprimir.png")); // Ajusta la ruta según sea necesario
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);
        HBox.setHgrow(Barra_de_progreso, Priority.ALWAYS);

        HBox hBox = new HBox(10, Barra_de_progreso, imageView);
        hBox.setStyle("-fx-alignment: center; -fx-padding: 10;");

        VBox vbox = new VBox(10, hBox, Agregar_tarea, Control_del_simulador);
        vbox.setStyle("-fx-alignment: center;");
        return new HBox(vbox);
    }

    private void agregarTarea() {
        Aleatorio = new Random();
        Formato_de_fecha = new SimpleDateFormat("yyyyMMddHHmmss");
        Nombre_de_archivo = "Document_" + Formato_de_fecha.format(new Date()) + ".txt";
        Número_de_hojas = Aleatorio.nextInt(50) + 1;
        Hora_de_acceso_del_archivo = new SimpleDateFormat("HH:mm:ss").format(new Date());
        Tarea = new impresion(String.valueOf(Lista_de_tareas.size() + 1), Nombre_de_archivo, Número_de_hojas, Hora_de_acceso_del_archivo);

        Platform.runLater(() -> {
            Lista_de_tareas.add(Tarea);
            Tabla_de_tareas.setItems(Lista_de_tareas);
        });
    }

    private void controlarSimulador() {
        if (Simulador_activo) {
            Simulador_activo = false;
            Control_del_simulador.setText("Iniciar simulador");
            Hilo_del_simulador.interrupt();
        } else {
            Simulador_activo = true;
            Control_del_simulador.setText("Detener simulador");
            Hilo_del_simulador = new SimuladorHilo();
            Hilo_del_simulador.start();
        }
    }

    private class SimuladorHilo extends Thread {
        @Override
        public void run() {
            while (!Thread.interrupted()) {
                if (!Lista_de_tareas.isEmpty() && Simulador_activo) {
                    Tarea_actual = Lista_de_tareas.get(0);
                    for (int i = 0; i < Tarea_actual.getNúmero_de_hojas(); i++) {
                        if (Thread.interrupted()) {
                            return;
                        }
                        try {
                            Thread.sleep(100);
                            final double progreso = (i + 1) / (double) Tarea_actual.getNúmero_de_hojas();
                            Platform.runLater(() -> Barra_de_progreso.setProgress(progreso));
                        } catch (InterruptedException e) {
                            return;
                        }
                    }
                    Platform.runLater(() -> Lista_de_tareas.remove(Tarea_actual));
                }
            }
        }
    }
}
