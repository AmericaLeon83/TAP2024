package com.example.demo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import com.example.demo.modelos.Conexion;
import com.example.demo.modelos.OrdenDAO;
import com.example.demo.vistas.MenuTablas;

public class Main extends Application {
    private Scene escena;
    private VBox vBox;
    private Label lblTexto;
    private Button btnMaria;
    private HBox hBox;
    public static int BaseDeDatos = 0;

    @Override
    public void start(Stage primaryStage) {

        try {
            // Conexión con la base de datos
            java.sql.Connection conn = java.sql.DriverManager.getConnection("jdbc:mysql://localhost:3306/taqueria2", "adminTacos2", "123");
            java.sql.Statement stmt = conn.createStatement();

            // Borrar todos los registros de la tabla de órdenes
            stmt.executeUpdate("DELETE FROM orden");
            // Reiniciar los IDs de la tabla de órdenes
            stmt.executeUpdate("ALTER TABLE orden AUTO_INCREMENT = 1");
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }


        // Crear una nueva instancia de OrdenDAO
        OrdenDAO ordenDAO = new OrdenDAO();

        // Llamar al método para borrar todos los registros
        ordenDAO.BORRAR_TODOS();
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

        vBox.setAlignment(Pos.CENTER);
        hBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);
        hBox.setSpacing(10);
        vBox.setPadding(new Insets(10, 5, 5, 5));
        lblTexto.setFont(Font.font("Candara", FontWeight.BOLD, 20));
        lblTexto.setTextFill(Color.WHITE);
        btnMaria.setFont(Font.font("Candara", FontWeight.BOLD, 15));
        btnMaria.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-padding: 10px 20px; -fx-border-radius: 5px;");

        btnMaria.setOnAction(event -> mostrarVentanaLogin());

        // Agregar una imagen desde Internet
        ImageView imageView = new ImageView(new Image("https://cdn-icons-png.flaticon.com/512/1717/1717466.png"));
        imageView.setFitWidth(100); // Ajustar el ancho de la imagen
        imageView.setPreserveRatio(true); // Mantener la relación de aspecto

        hBox.getChildren().addAll(imageView, btnMaria); // Agregar la imagen al HBox
        VBox.setMargin(hBox, new Insets(20, 0, 0, 0));
        vBox.getChildren().addAll(lblTexto, hBox);
        vBox.setBackground(new Background(new BackgroundFill(Color.rgb(51, 122, 183), CornerRadii.EMPTY, Insets.EMPTY)));
        escena = new Scene(vBox, 550, 200);
    }
    public void reiniciarTablaOrden() {
        // Crear una nueva instancia de OrdenDAO
        OrdenDAO ordenDAO = new OrdenDAO();

        // Llamar al método para borrar todos los registros
        ordenDAO.BORRAR_TODOS();
    }
    private void mostrarVentanaLogin() {
        Stage ventanaLogin = new Stage();
        ventanaLogin.setTitle("Inicio de sesión");
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));
        vbox.setBackground(new Background(new BackgroundFill(Color.rgb(255, 255, 255), CornerRadii.EMPTY, Insets.EMPTY)));

        Label lblUsuario = new Label("Usuario");
        lblUsuario.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        lblUsuario.setTextFill(Color.rgb(51, 122, 183));

        TextField usuarioField = new TextField();
        usuarioField.setText("adminTacos"); // Predefinido con "adminTacos"
        usuarioField.setMaxWidth(200);

        Label lblContrasenia = new Label("Contraseña");
        lblContrasenia.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        lblContrasenia.setTextFill(Color.rgb(51, 122, 183));

        PasswordField contraseniaField = new PasswordField();
        contraseniaField.setText("123"); // Predefinido con "123"
        contraseniaField.setMaxWidth(200);

        Button btnIniciarSesion = new Button("Iniciar sesión");
        btnIniciarSesion.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        btnIniciarSesion.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-padding: 10px 20px; -fx-border-radius: 5px;");
        btnIniciarSesion.setOnAction(event -> {
            String usuario = usuarioField.getText();
            String contrasenia = contraseniaField.getText();
            if (validarCredenciales(usuario, contrasenia)) {
                ventanaLogin.close();
                Conexion.crearConexion();
                new MenuTablas();
                BaseDeDatos = 1;
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error de inicio de sesión");
                alert.setHeaderText(null);
                alert.setContentText("Usuario o contraseña incorrectos.");
                alert.showAndWait();
            }
        });

        vbox.getChildren().addAll(lblUsuario, usuarioField, lblContrasenia, contraseniaField, btnIniciarSesion);
        Scene scene = new Scene(vbox, 300, 200);
        ventanaLogin.setScene(scene);
        ventanaLogin.show();
    }
    private boolean validarCredenciales(String usuario, String contrasenia) {
        // Aquí puedes implementar la lógica de validación de credenciales
        // Por ejemplo, verificar en una base de datos si las credenciales son válidas
        // Por ahora, solo devolvemos true si el usuario es "admin" y la contraseña es "admin"
        return usuario.equals("adminTacos") && contrasenia.equals("123");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
