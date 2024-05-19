package com.example.demo.vistas;


import com.example.demo.Componentes.impresion;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class simuladorimmprecion  extends Stage {

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

    public simuladorimmprecion (String s, String nombre_de_archivo, int número_de_hojas, String hora_de_acceso_del_archivo) {
        Crear_UI();
        Hilo_del_simulador = new SimuladorHilo();
        Simulador_activo = false;
        this.setTitle("Simulador de impresión");
        this.setScene(new Scene(new VBox(Tabla_de_tareas, Barra_de_progreso, Agregar_tarea, Control_del_simulador)));
        this.show();
    }

    private void Crear_UI() {
        Tabla_de_tareas = new TableView<>();
        Lista_de_tareas = FXCollections.observableArrayList();
        Tabla_de_tareas.setItems(Lista_de_tareas);
        Número_de_archivo = new TableColumn<>("Número de archivo");
        Número_de_archivo.setCellValueFactory(new PropertyValueFactory<>("Número_de_archivo"));
        Nombre = new TableColumn<>("Nombre de archivo");
        Nombre.setCellValueFactory(new PropertyValueFactory<>("Nombre_de_archivo"));
        Hojas = new TableColumn<>("Número de hojas");
        Hojas.setCellValueFactory(new PropertyValueFactory<>("Número_de_hojas"));
        Hora_de_acceso = new TableColumn<>("Hora de acceso");
        Hora_de_acceso.setCellValueFactory(new PropertyValueFactory<>("Hora_de_acceso"));
        Tabla_de_tareas.getColumns().addAll(Número_de_archivo, Nombre, Hojas, Hora_de_acceso);
        Barra_de_progreso = new ProgressBar(0);
        Agregar_tarea = new Button("Agregar tarea");
        Agregar_tarea.setOnAction(event -> agregarTarea());
        Control_del_simulador = new Button("Iniciar simulador");
        Control_del_simulador.setOnAction(event -> controlarSimulador());

    }

    private void agregarTarea() {
        Aleatorio = new Random();
        Formato_de_fecha = new SimpleDateFormat("yyyyMMddHHmmss");
        Nombre_de_archivo = "Archivo_" + Formato_de_fecha.format(new Date()) + ".txt";
        Número_de_hojas = Aleatorio.nextInt(50) + 1;
        Hora_de_acceso_del_archivo = new SimpleDateFormat("HH:mm:ss").format(new Date());
        Tarea = new impresion(String.valueOf(Lista_de_tareas.size() + 1), Nombre_de_archivo, Número_de_hojas, Hora_de_acceso_del_archivo);

        Platform.runLater(() -> {
            Lista_de_tareas.add(Tarea);
            Tabla_de_tareas.setItems(Lista_de_tareas);  // Actualiza la lista completa
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
                        try {
                            Thread.sleep(100);
                            final double progreso = (i + 1) / (double) Tarea_actual.getNúmero_de_hojas();
                            Platform.runLater(() -> Barra_de_progreso.setProgress(progreso));
                        } catch (InterruptedException e) {
                            break;
                        }
                    }
                    Platform.runLater(() -> Lista_de_tareas.remove(Tarea_actual));
                }
            }
        }
    }
}



