package com.example.demo.vistas;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MenuTablas extends Stage {

    private Scene escena;
    private VBox vBox;
    private GridPane gridPane;
    private Button btnClientes, btnEmpleados, btnOrdenes, btnPlatillos, btnDetPlatillo, btnDetOrden, btnInsumos;

    public MenuTablas() {
        CrearUI();
        this.setTitle("Menu De La Taqueria :)");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        btnClientes = createButtonWithIcon("https://cdn-icons-png.flaticon.com/512/6009/6009864.png", "Tabla Clientes");
        btnEmpleados = createButtonWithIcon("https://cdn-icons-png.flaticon.com/128/1830/1830878.png", "Tabla Empleados");
        btnOrdenes = createButtonWithIcon("https://cdn-icons-png.flaticon.com/512/2082/2082194.png", "Tabla Ordenes");
        btnPlatillos = createButtonWithIcon("https://cdn-icons-png.flaticon.com/128/1046/1046849.png", "Tabla Platillos");
        btnDetOrden = createButtonWithIcon("https://cdn-icons-png.flaticon.com/128/1980/1980997.png", "Tabla Detalle De Orden");
        btnDetPlatillo = createButtonWithIcon("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRjChLaLhPfSopC_TH5lyN5YviE9BqPrmYLhTfyXIxIl5eRNAWU", "Tabla Detalle De Platillos");
        btnInsumos = createButtonWithIcon("https://cdn-icons-png.flaticon.com/512/917/917940.png", "Tabla Bebidas");

        //btnClientes.setOnAction(event -> new ClienteTaqueria());
        btnEmpleados.setOnAction(action -> new EmpleadoTaqueria());
       // btnOrdenes.setOnAction(action -> new OrdenesTaqueria());
       // btnPlatillos.setOnAction(action -> new PlatilloTaqueria());
       // btnDetOrden.setOnAction(action -> new DetallesOrdenTaqueria());
       // btnDetPlatillo.setOnAction(action -> new DetallesPlatilloTaqueria());
        btnInsumos.setOnAction(action -> new BebidasTaqueria());

        gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setVgap(20);
        gridPane.setHgap(20);
        gridPane.addRow(0, btnClientes, btnEmpleados, btnOrdenes);
        gridPane.addRow(1, btnPlatillos, btnDetOrden, btnInsumos);
        gridPane.add(btnDetPlatillo, 0, 2, 3, 1);

        escena = new Scene(gridPane, 420, 260);
    }

    private Button createButtonWithIcon(String iconUrl, String buttonText) {
        Button button = new Button();
        ImageView imageView = new ImageView(new Image(iconUrl));
        imageView.setFitWidth(40); // Ajustar el ancho del icono
        imageView.setPreserveRatio(true); // Mantener la relación de aspecto del icono
        Label label = new Label(buttonText);
        VBox vbox = new VBox();
        vbox.getChildren().addAll(imageView, label);
        vbox.setAlignment(javafx.geometry.Pos.CENTER);
        button.setGraphic(vbox);
        return button;
    }
}
