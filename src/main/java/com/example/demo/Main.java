package com.example.demo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import com.example.demo.modelos.Conexion;
import com.example.demo.vistas.MenuTablas;


public class Main extends Application {

    private Scene escena;
    private VBox vBox;
    private Label lblTexto;
    private Button btnMaria;
    private HBox hBox;
    public static int BaseDeDatos = 0;

    @Override
    public void start(Stage primaryStage){

        // primaryStage.addEventHandler(WindowEvent.WINDOW_SHOWING,this);
        CrearUI();
        primaryStage.setScene(escena);
        primaryStage.setTitle("Bases De Datos");

        // Agregar un icono a la ventana
        primaryStage.getIcons().add(new Image("https://img.freepik.com/vector-gratis/plantilla-logotipo-taqueria-diseno-plano_23-2149557476.jpg"));

        primaryStage.show();
    }

    private void CrearUI() {

        vBox = new VBox();
        hBox = new HBox();
        btnMaria = new Button("Entrar");
        lblTexto = new Label("BIENVENIDO(A) AL SISTEMA DE LA BASE DE DATOS TAQUERIA");

        vBox.setAlignment(Pos.TOP_CENTER);
        hBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);
        hBox.setSpacing(10);
        vBox.setPadding(new Insets(10, 5, 5, 5));
        lblTexto.setFont(Font.font("Candara", 20));
        btnMaria.setFont(Font.font("Candara", 15));

        btnMaria.setOnAction(event -> {
            Conexion.crearConexion();
            new MenuTablas();
            BaseDeDatos = 1;
        });

        // Agregar una imagen desde Internet
        ImageView imageView = new ImageView(new Image("https://cdn-icons-png.flaticon.com/512/1717/1717466.png"));
        imageView.setFitWidth(100); // Ajustar el ancho de la imagen
        imageView.setPreserveRatio(true); // Mantener la relación de aspecto

        hBox.getChildren().addAll(imageView, btnMaria); // Agregar la imagen al HBox
        vBox.getChildren().addAll(lblTexto, hBox);
        escena = new Scene(vBox, 550, 200);

    }

    public static void main(String[] args) {
        launch(args);
    }
}
