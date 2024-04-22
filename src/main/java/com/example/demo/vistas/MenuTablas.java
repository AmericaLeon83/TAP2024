package com.example.demo.vistas;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;

public class MenuTablas extends Stage {

    private Scene escena;
    private VBox vBox;
    private GridPane gridPane;
    private Button btnClientes, btnEmpleados, btnOrdenes, btnPlatillos, btnDetPlatillo, btnDetOrden, btnInsumos , btnMesass,btnDesayuno, btnBocadillo, btnGuarnicion, btnCafe, btnPostre, btnSnacks;
    private Button[] btnMesas;
    private String usuarioAdmin = "adminTacos";
    private String contraseniaAdmin = "123";
    public MenuTablas() {
        CrearUI();
        this.setTitle("Menu De La Taqueria :)");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        MenuBar menuBar = new MenuBar();

        // Crear menú de tablas
        Menu menuTablas = new Menu();
        menuTablas.setGraphic(createMenuIconWithText("https://cdn-icons-png.flaticon.com/128/1085/1085805.png", "Administración"));

        MenuItem itemMenuTablas = new MenuItem("Mostrar Tablas");
        itemMenuTablas.setOnAction(event -> {
            mostrarVentanaLogin();
            // MostrarBotonesTablas();
        });

        menuTablas.getItems().add(itemMenuTablas);

        // Crear menú para mesas
        Menu menuMesas = new Menu();
        menuMesas.setGraphic(createMenuIconWithText("https://cdn-icons-png.flaticon.com/128/4001/4001039.png", "Mesas"));

        MenuItem itemMenuMesas = new MenuItem("Mostrar Mesas");
        itemMenuMesas.setOnAction(event -> {
            MostrarBotonesMesas();
        });

        menuMesas.getItems().add(itemMenuMesas);

        // Crear menú para categorías
        Menu menuCategorias = new Menu();
        menuCategorias.setGraphic(createMenuIconWithText("https://cdn-icons-png.flaticon.com/128/1357/1357589.png", "Categorías"));

        MenuItem itemMenuCategorias = new MenuItem("Mostrar Categorias");
        itemMenuCategorias.setOnAction(event -> {
            MostrarBotonesCategorias();
        });

        menuCategorias.getItems().add(itemMenuCategorias);

        // Agregar menús a la barra de menú
        menuBar.getMenus().addAll(menuTablas, menuMesas, menuCategorias);

        vBox = new VBox();
        gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setVgap(20);
        gridPane.setHgap(20);

        // Agregar elementos al VBox
        vBox.getChildren().addAll(menuBar, gridPane);
        escena = new Scene(vBox, 750, 380);
    }
    private void mostrarVentanaLogin() {
        Stage ventanaLogin = new Stage();
        ventanaLogin.setTitle("Inicio de sesión");
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(10);
        grid.setHgap(10);

        TextField usuarioField = new TextField();
        usuarioField.setPromptText("Usuario");
        PasswordField contraseniaField = new PasswordField();
        contraseniaField.setPromptText("Contraseña");

        Button btnIniciarSesion = new Button("Iniciar sesión");
        btnIniciarSesion.setOnAction(event -> {
            String usuario = usuarioField.getText();
            String contrasenia = contraseniaField.getText();
            if (usuario.equals(usuarioAdmin) && contrasenia.equals(contraseniaAdmin)) {
                ventanaLogin.close();
                MostrarBotonesTablas(); // Mostrar los botones de administración
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error de inicio de sesión");
                alert.setHeaderText(null);
                alert.setContentText("Usuario o contraseña incorrectos.");
                alert.showAndWait();
            }
        });

        grid.add(new Label("Usuario:"), 0, 0);
        grid.add(usuarioField, 1, 0);
        grid.add(new Label("Contraseña:"), 0, 1);
        grid.add(contraseniaField, 1, 1);
        grid.add(btnIniciarSesion, 1, 2);

        Scene scene = new Scene(grid, 300, 150);
        ventanaLogin.setScene(scene);
        ventanaLogin.show();
    }
    private void MostrarBotonesTablas() {
        LimpiarGridPane();
        // Botones para las tablas
        btnClientes = createButtonWithIcon("https://cdn-icons-png.flaticon.com/512/6009/6009864.png", "Tabla Clientes");
        btnEmpleados = createButtonWithIcon("https://cdn-icons-png.flaticon.com/128/1830/1830878.png", "Tabla Empleados");
        btnOrdenes = createButtonWithIcon("https://cdn-icons-png.flaticon.com/512/2082/2082194.png", "Tabla Ordenes");
        btnPlatillos = createButtonWithIcon("https://cdn-icons-png.flaticon.com/128/1046/1046849.png", "Tabla Platillos");
        btnDetOrden = createButtonWithIcon("https://cdn-icons-png.flaticon.com/128/1980/1980997.png", "Tabla Detalle De Orden");
        btnDetPlatillo = createButtonWithIcon("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRjChLaLhPfSopC_TH5lyN5YviE9BqPrmYLhTfyXIxIl5eRNAWU", "Tabla Detalle De Platillos");
        btnInsumos = createButtonWithIcon("https://cdn-icons-png.flaticon.com/512/917/917940.png", "Tabla Bebidas");
        btnMesass = createButtonWithIcon("https://cdn-icons-png.flaticon.com/128/673/673281.png", "Tabla Mesas");
        btnDesayuno = createButtonWithIcon("https://cdn-icons-png.flaticon.com/128/887/887359.png", "Tabla Desayunos");
        btnBocadillo = createButtonWithIcon("https://cdn-icons-png.flaticon.com/128/1033/1033228.png", "Tabla Bocadillos");
        btnGuarnicion = createButtonWithIcon("https://cdn-icons-png.flaticon.com/128/985/985478.png", "Tabla Guarnicions");
        btnCafe = createButtonWithIcon("https://cdn-icons-png.flaticon.com/128/7438/7438571.png", "Tabla Cafes");
        btnPostre = createButtonWithIcon("https://cdn-icons-png.flaticon.com/128/3597/3597206.png", "Tabla Postres");
        btnSnacks= createButtonWithIcon("https://cdn-icons-png.flaticon.com/128/3597/3597206.png", "Tabla Snacks");
        // Asignar acciones a los botones
        btnClientes.setOnAction(event -> new ClienteTaqueria());
        btnEmpleados.setOnAction(action -> new EmpleadoTaqueria());
        btnOrdenes.setOnAction(action -> new OrdenesTaqueria());
        btnPlatillos.setOnAction(action -> new PlatillosTaqueria());
        btnDetOrden.setOnAction(action -> new DetallesOrdenTaqueria());
        btnDetPlatillo.setOnAction(action -> new DetallesPlatilloTaqueria());
        btnInsumos.setOnAction(action -> new BebidasTaqueria());
        btnMesass.setOnAction(action -> new MesasTaqueria());
        btnDesayuno.setOnAction(action -> new DesayunosTaqueria());
        btnBocadillo.setOnAction(action -> new BocadillosTaqueria());
        btnGuarnicion.setOnAction(action -> new GuarnicionesTaqueria());
        btnCafe.setOnAction(action -> new CafesTaqueria());
        btnPostre.setOnAction(action -> new PostresTaqueria());
        btnSnacks.setOnAction(action -> new SnacksTapasTaqueria());
        // Agregar botones al GridPane

        gridPane.addRow(0, btnClientes, btnEmpleados, btnOrdenes, btnPlatillos, btnDetOrden);
        gridPane.addRow(1, btnInsumos, btnMesass, btnDetPlatillo, btnDesayuno, btnBocadillo);
        gridPane.addRow(2, btnGuarnicion, btnCafe, btnPostre, btnSnacks);

        for (Node child : gridPane.getChildren()) {
            if (child instanceof Button) {
                GridPane.setHalignment(child, HPos.RIGHT);
            }
        }
    }
    private void MostrarBotonesCategorias() {
        LimpiarGridPane();
        // Botones para categorías
        Button btnBebidas = createButtonWithIcon("https://cdn-icons-png.flaticon.com/128/1113/1113278.png", "Bebidas");
        btnBebidas.setOnAction(event -> {
            MostrarBebidas();
        });
        Button btnDesayunos = createButtonWithIcon("https://cdn-icons-png.flaticon.com/128/887/887359.png", "Desayunos");
        btnDesayunos.setOnAction(event -> {
            MostrarDesayunos();
        });
        Button btnBocadillos = createButtonWithIcon("https://cdn-icons-png.flaticon.com/128/5508/5508486.png", "Bocadillos");
        btnBocadillos.setOnAction(event -> {
            MostrarBocadillos();
        });
        Button btnGuarniciones = createButtonWithIcon("https://cdn-icons-png.flaticon.com/128/985/985478.png", "Guarniciones");
        btnGuarniciones.setOnAction(event -> {
            MostrarGuarniciones();
        });
        Button btnCafes = createButtonWithIcon("https://cdn-icons-png.flaticon.com/128/7438/7438571.png", "Cafes");
        btnCafes.setOnAction(event -> {
            MostrarCafes();
        });
        Button btnPostres = createButtonWithIcon("https://cdn-icons-png.flaticon.com/128/5347/5347946.png", "Postres");
        btnPostres.setOnAction(event -> {
            MostrarPostres();
        });
        Button btnSnacksTapas = createButtonWithIcon("https://cdn-icons-png.flaticon.com/128/10164/10164488.png", "Snacks/Tapas");
        btnSnacksTapas.setOnAction(event -> {
            MostrarSnacksTapas();
        });
        Button btnPlatillo = createButtonWithIcon("https://cdn-icons-png.flaticon.com/128/1886/1886807.png", "Platillos");
        btnPlatillo.setOnAction(event -> {
            MostrarPlatillos();
        });

        // Button btnComidas = createButtonWithIcon("https://cdn-icons-png.flaticon.com/128/1886/1886807.png", "Comidas");


        // Agregar botones al GridPane
        gridPane.addRow(0, btnBebidas, btnDesayunos, btnBocadillos, btnGuarniciones);
        gridPane.addRow(1, btnCafes, btnPostres, btnSnacksTapas,btnPlatillo);
    }
    private void MostrarBebidas() {
        LimpiarGridPane();
        try {
            // Conexión con la base de datos
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/taqueria", "adminTacos", "1234");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT idBebida, nombreBebida FROM bebidas");

            // Botones para las bebidas
            int row = 0;
            int col = 0;
            while (rs.next()) {
                int idBebida = rs.getInt("idBebida");
                String nombreBebida = rs.getString("nombreBebida");
                String iconoURL = IconManager.getIconURL(nombreBebida); // Obtener la URL del icono correspondiente
                Button btnBebida = createButtonWithIcon(iconoURL, nombreBebida);
                btnBebida.setOnAction(event -> {
                    // Al seleccionar una bebida, mostrar los clientes disponibles
                    MostrarClientesParaBebida(idBebida);
                });

                // Agregar botón de bebida al GridPane
                gridPane.add(btnBebida, col, row);

                // Actualizar posición en el GridPane
                col++;
                if (col == 4) {
                    col = 0;
                    row++;
                }
            }

            // Cerrar la conexión con la base de datos
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void MostrarClientesParaBebida(int idBebida) {
        // Limpiar el GridPane
        LimpiarGridPane();

        try {
            // Conexión con la base de datos
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/taqueria", "adminTacos", "1234");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT idCliente, nombreCliente FROM cliente");

            // Botones para los clientes
            int row = 0;
            while (rs.next()) {
                int idCliente = rs.getInt("idCliente");
                String nombreCliente = rs.getString("nombreCliente");
                Button btnCliente = new Button(nombreCliente);
                // Crear un ImageView para el icono del cliente
                ImageView iconoCliente = new ImageView(new Image("https://cdn-icons-png.flaticon.com/128/2194/2194222.png"));
                iconoCliente.setFitHeight(30); // Establecer el alto deseado del icono
                iconoCliente.setFitWidth(30); // Establecer el ancho deseado del icono

                // Establecer el icono junto al texto del botón
                btnCliente.setGraphic(iconoCliente);

                btnCliente.setOnAction(event -> {
                    // Al seleccionar un cliente, agregar la bebida al cliente
                    AgregarBebidaACliente(idBebida, idCliente);
                });

                // Agregar botón de cliente al GridPane
                gridPane.add(btnCliente, 0, row);
                row++;
            }

            // Cerrar la conexión con la base de datos
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void AgregarBebidaACliente(int idBebida, int idCliente) {
        try {
            // Conexión con la base de datos
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/taqueria", "adminTacos", "1234");
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO detalles_orden (idPlatillo, idBebida, idCliente, cantidadBebida) VALUES (?, ?, ?, 1)");
            pstmt.setInt(1, idBebida);
            pstmt.setNull(2, Types.INTEGER);
            pstmt.setInt(3, idCliente);

            // Ejecutar la inserción
            int rowsAffected = pstmt.executeUpdate();

            // Verificar si se insertaron correctamente
            if (rowsAffected > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Bebida Agregada");
                alert.setHeaderText(null);
                alert.setContentText("La bebida ha sido agregada al cliente correctamente.");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error al Agregar Bebida");
                alert.setHeaderText(null);
                alert.setContentText("Hubo un error al intentar agregar la bebida al cliente.");
                alert.showAndWait();
            }

            // Cerrar la conexión con la base de datos
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void MostrarDesayunos() {
        LimpiarGridPane();
        try {
            // Conexión con la base de datos
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/taqueria", "adminTacos", "1234");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT idDesayuno, nombreDesayuno FROM desayunos");

            // Botones para los desayunos
            int row = 0;
            int col = 0;
            while (rs.next()) {
                int idDesayuno = rs.getInt("idDesayuno");
                String nombreDesayuno = rs.getString("nombreDesayuno");
                String iconoURL = IconManager.getIconURLD(nombreDesayuno); // Obtener la URL del icono correspondiente para el desayuno
                Button btnDesayuno = createButtonWithIcon(iconoURL, nombreDesayuno);
                btnDesayuno.setOnAction(event -> {
                    // Al seleccionar una bebida, mostrar los clientes disponibles
                    MostrarClientesParaDesayuno(idDesayuno);
                });
                // Agregar botón de desayuno al GridPane
                gridPane.add(btnDesayuno, col, row);

                // Actualizar posición en el GridPane
                col++;
                if (col == 4) {
                    col = 0;
                    row++;
                }
            }

            // Cerrar la conexión con la base de datos
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void MostrarClientesParaDesayuno(int idDesayuno) {
        // Limpiar el GridPane
        LimpiarGridPane();

        try {
            // Conexión con la base de datos
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/taqueria", "adminTacos", "1234");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT idCliente, nombreCliente FROM cliente");

            // Botones para los clientes
            int row = 0;
            while (rs.next()) {
                int idCliente = rs.getInt("idCliente");
                String nombreCliente = rs.getString("nombreCliente");
                Button btnCliente = new Button(nombreCliente);

                // Crear un ImageView para el icono del cliente
                ImageView iconoCliente = new ImageView(new Image("https://cdn-icons-png.flaticon.com/128/2194/2194222.png"));
                iconoCliente.setFitHeight(30); // Establecer el alto deseado del icono
                iconoCliente.setFitWidth(30); // Establecer el ancho deseado del icono

                // Establecer el icono junto al texto del botón
                btnCliente.setGraphic(iconoCliente);

                btnCliente.setOnAction(event -> {
                    // Al seleccionar un cliente, agregar el desayuno al cliente
                    AgregarDesayunoACliente(idDesayuno, idCliente);
                });

                // Agregar botón de cliente al GridPane
                gridPane.add(btnCliente, 0, row);
                row++;
            }

            // Cerrar la conexión con la base de datos
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void AgregarDesayunoACliente(int idDesayuno, int idCliente) {
        try {
            // Conexión con la base de datos
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/taqueria", "adminTacos", "1234");
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO detalles_orden (idPlatillo, idDesayuno, idCliente, cantidadDesayuno) VALUES (?, ?, ?, 1)");
            pstmt.setNull(1, Types.INTEGER);
            pstmt.setInt(2, idDesayuno);
            pstmt.setInt(3, idCliente);

            // Ejecutar la inserción
            int rowsAffected = pstmt.executeUpdate();

            // Verificar si se insertaron correctamente
            if (rowsAffected > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Desayuno Agregado");
                alert.setHeaderText(null);
                alert.setContentText("El desayuno ha sido agregado al cliente correctamente.");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error al Agregar Desayuno");
                alert.setHeaderText(null);
                alert.setContentText("Hubo un error al intentar agregar el desayuno al cliente.");
                alert.showAndWait();
            }

            // Cerrar la conexión con la base de datos
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void MostrarBocadillos() {
        LimpiarGridPane();
        try {
            // Conexión con la base de datos
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/taqueria", "adminTacos", "1234");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT idBocadillo, nombreBocadillo FROM bocadillos");

            // Botones para los bocadillos
            int row = 0;
            int col = 0;
            while (rs.next()) {
                int idBocadillo = rs.getInt("idBocadillo");
                String nombreBocadillo = rs.getString("nombreBocadillo");
                String iconoURL = IconManager.getIconURLB(nombreBocadillo); // Obtener la URL del icono correspondiente
                Button btnBocadillo = createButtonWithIcon(iconoURL, nombreBocadillo);
                btnBocadillo.setOnAction(event -> {
                    // Al seleccionar un bocadillo, mostrar los clientes disponibles
                    MostrarClientesParaBocadillo(idBocadillo);
                });

                // Agregar botón de bocadillo al GridPane
                gridPane.add(btnBocadillo, col, row);

                // Actualizar posición en el GridPane
                col++;
                if (col == 4) {
                    col = 0;
                    row++;
                }
            }

            // Cerrar la conexión con la base de datos
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void MostrarClientesParaBocadillo(int idBocadillo) {
        // Limpiar el GridPane
        LimpiarGridPane();

        try {
            // Conexión con la base de datos
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/taqueria", "adminTacos", "1234");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT idCliente, nombreCliente FROM cliente");

            // Botones para los clientes
            int row = 0;
            while (rs.next()) {
                int idCliente = rs.getInt("idCliente");
                String nombreCliente = rs.getString("nombreCliente");
                Button btnCliente = new Button(nombreCliente);
                // Crear un ImageView para el icono del cliente
                ImageView iconoCliente = new ImageView(new Image("https://cdn-icons-png.flaticon.com/128/2194/2194222.png"));
                iconoCliente.setFitHeight(30); // Establecer el alto deseado del icono
                iconoCliente.setFitWidth(30); // Establecer el ancho deseado del icono

                // Establecer el icono junto al texto del botón
                btnCliente.setGraphic(iconoCliente);
                btnCliente.setOnAction(event -> {
                    // Al seleccionar un cliente, agregar el bocadillo al cliente
                    AgregarBocadilloACliente(idBocadillo, idCliente);
                });

                // Agregar botón de cliente al GridPane
                gridPane.add(btnCliente, 0, row);
                row++;
            }

            // Cerrar la conexión con la base de datos
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void AgregarBocadilloACliente(int idBocadillo, int idCliente) {
        try {
            // Conexión con la base de datos
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/taqueria", "adminTacos", "1234");
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO detalles_orden (idPlatillo, idBebida, idCliente, cantidadBebida) VALUES (?, ?, ?, 1)");
            pstmt.setInt(1, idBocadillo);
            pstmt.setNull(2, Types.INTEGER);
            pstmt.setInt(3, idCliente);

            // Ejecutar la inserción
            int rowsAffected = pstmt.executeUpdate();

            // Verificar si se insertaron correctamente
            if (rowsAffected > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Bocadillo Agregado");
                alert.setHeaderText(null);
                alert.setContentText("El bocadillo ha sido agregado al cliente correctamente.");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error al Agregar Bocadillo");
                alert.setHeaderText(null);
                alert.setContentText("Hubo un error al intentar agregar el bocadillo al cliente.");
                alert.showAndWait();
            }

            // Cerrar la conexión con la base de datos
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void MostrarGuarniciones() {
        LimpiarGridPane();
        try {
            // Conexión con la base de datos
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/taqueria", "adminTacos", "1234");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT idGuarnicion, nombreGuarnicion FROM guarniciones");

            // Botones para las guarniciones
            int row = 0;
            int col = 0;
            while (rs.next()) {
                int idGuarnicion = rs.getInt("idGuarnicion");
                String nombreGuarnicion = rs.getString("nombreGuarnicion");
                String iconoURL = IconManager.getIconURLG(nombreGuarnicion); // Obtener la URL del icono correspondiente
                Button btnGuarnicion = createButtonWithIcon(iconoURL, nombreGuarnicion);

                btnGuarnicion.setOnAction(event -> {
                    // Al seleccionar una guarnición, mostrar los clientes disponibles
                    MostrarClientesParaGuarnicion(idGuarnicion);
                });

                // Agregar botón de guarnición al GridPane
                gridPane.add(btnGuarnicion, col, row);

                // Actualizar posición en el GridPane
                col++;
                if (col == 4) {
                    col = 0;
                    row++;
                }
            }

            // Cerrar la conexión con la base de datos
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void MostrarClientesParaGuarnicion(int idGuarnicion) {
        // Limpiar el GridPane
        LimpiarGridPane();

        try {
            // Conexión con la base de datos
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/taqueria", "adminTacos", "1234");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT idCliente, nombreCliente FROM cliente");

            // Botones para los clientes
            int row = 0;
            while (rs.next()) {
                int idCliente = rs.getInt("idCliente");
                String nombreCliente = rs.getString("nombreCliente");
                Button btnCliente = new Button(nombreCliente);
                // Crear un ImageView para el icono del cliente
                ImageView iconoCliente = new ImageView(new Image("https://cdn-icons-png.flaticon.com/128/2194/2194222.png"));
                iconoCliente.setFitHeight(30); // Establecer el alto deseado del icono
                iconoCliente.setFitWidth(30); // Establecer el ancho deseado del icono

                // Establecer el icono junto al texto del botón
                btnCliente.setGraphic(iconoCliente);
                btnCliente.setOnAction(event -> {
                    // Al seleccionar un cliente, agregar la guarnición al cliente
                    AgregarGuarnicionACliente(idGuarnicion, idCliente);
                });

                // Agregar botón de cliente al GridPane
                gridPane.add(btnCliente, 0, row);
                row++;
            }

            // Cerrar la conexión con la base de datos
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void AgregarGuarnicionACliente(int idGuarnicion, int idCliente) {
        try {
            // Conexión con la base de datos
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/taqueria", "adminTacos", "1234");
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO detalles_orden (idPlatillo, idGuarnicion, idCliente, cantidadGuarnicion) VALUES (?, ?, ?, 1)");
            pstmt.setInt(1, idGuarnicion);
            pstmt.setNull(2, Types.INTEGER);
            pstmt.setInt(3, idCliente);

            // Ejecutar la inserción
            int rowsAffected = pstmt.executeUpdate();

            // Verificar si se insertaron correctamente
            if (rowsAffected > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Guarnición Agregada");
                alert.setHeaderText(null);
                alert.setContentText("La guarnición ha sido agregada al cliente correctamente.");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error al Agregar Guarnición");
                alert.setHeaderText(null);
                alert.setContentText("Hubo un error al intentar agregar la guarnición al cliente.");
                alert.showAndWait();
            }

            // Cerrar la conexión con la base de datos
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void MostrarCafes() {
        LimpiarGridPane();
        try {
            // Conexión con la base de datos
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/taqueria", "adminTacos", "1234");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT idCafe, nombreCafe FROM cafes");

            // Botones para los cafés
            int row = 0;
            int col = 0;
            while (rs.next()) {
                int idCafe = rs.getInt("idCafe");
                String nombreCafe = rs.getString("nombreCafe");
                String iconoURL = IconManager.getIconURLC(nombreCafe); // Obtener la URL del icono correspondiente
                Button btnCafe = createButtonWithIcon(iconoURL, nombreCafe);
                btnCafe.setOnAction(event -> {
                    // Al seleccionar un café, mostrar los clientes disponibles
                    mostrarClientesParaCafe(idCafe);
                });

                // Agregar botón de café al GridPane
                gridPane.add(btnCafe, col, row);

                // Actualizar posición en el GridPane
                col++;
                if (col == 4) {
                    col = 0;
                    row++;
                }
            }

            // Cerrar la conexión con la base de datos
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void mostrarClientesParaCafe(int idCafe) {
        // Limpiar el GridPane
        LimpiarGridPane();

        try {
            // Conexión con la base de datos
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/taqueria", "adminTacos", "1234");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT idCliente, nombreCliente FROM cliente");

            // Botones para los clientes
            int row = 0;
            while (rs.next()) {
                int idCliente = rs.getInt("idCliente");
                String nombreCliente = rs.getString("nombreCliente");
                Button btnCliente = new Button(nombreCliente);
                // Crear un ImageView para el icono del cliente
                ImageView iconoCliente = new ImageView(new Image("https://cdn-icons-png.flaticon.com/128/2194/2194222.png"));
                iconoCliente.setFitHeight(30); // Establecer el alto deseado del icono
                iconoCliente.setFitWidth(30); // Establecer el ancho deseado del icono

                // Establecer el icono junto al texto del botón
                btnCliente.setGraphic(iconoCliente);
                btnCliente.setOnAction(event -> {
                    // Al seleccionar un cliente, agregar el café al cliente
                    agregarCafeACliente(idCafe, idCliente);
                });

                // Agregar botón de cliente al GridPane
                gridPane.add(btnCliente, 0, row);
                row++;
            }

            // Cerrar la conexión con la base de datos
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void agregarCafeACliente(int idCafe, int idCliente) {
        try {
            // Conexión con la base de datos
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/taqueria", "adminTacos", "1234");
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO detalles_orden (idPlatillo, idCafe, idCliente, cantidadCafe) VALUES (?, ?, ?, 1)");
            pstmt.setNull(1, Types.INTEGER);
            pstmt.setInt(2, idCafe);
            pstmt.setInt(3, idCliente);

            // Ejecutar la inserción
            int rowsAffected = pstmt.executeUpdate();

            // Verificar si se insertaron correctamente
            if (rowsAffected > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Café Agregado");
                alert.setHeaderText(null);
                alert.setContentText("El café ha sido agregado al cliente correctamente.");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error al Agregar Café");
                alert.setHeaderText(null);
                alert.setContentText("Hubo un error al intentar agregar el café al cliente.");
                alert.showAndWait();
            }

            // Cerrar la conexión con la base de datos
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void MostrarPostres() {
        LimpiarGridPane();
        try {
            // Conexión con la base de datos
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/taqueria", "adminTacos", "1234");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT idPostre, nombrePostre FROM postres");

            // Botones para los postres
            int row = 0;
            int col = 0;
            while (rs.next()) {
                int idPostre = rs.getInt("idPostre");
                String nombrePostre = rs.getString("nombrePostre");
                String iconoURL = IconManager.getIconURLP(nombrePostre); // Obtener la URL del icono correspondiente
                Button btnPostre = createButtonWithIcon(iconoURL, nombrePostre);
                btnPostre.setOnAction(event -> {
                    // Al seleccionar un postre, mostrar los clientes disponibles
                    MostrarClientesParaPostre(idPostre);
                });

                // Agregar botón de postre al GridPane
                gridPane.add(btnPostre, col, row);

                // Actualizar posición en el GridPane
                col++;
                if (col == 4) {
                    col = 0;
                    row++;
                }
            }

            // Cerrar la conexión con la base de datos
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void MostrarClientesParaPostre(int idPostre) {
        // Limpiar el GridPane
        LimpiarGridPane();

        try {
            // Conexión con la base de datos
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/taqueria", "adminTacos", "1234");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT idCliente, nombreCliente FROM cliente");

            // Botones para los clientes
            int row = 0;
            while (rs.next()) {
                int idCliente = rs.getInt("idCliente");
                String nombreCliente = rs.getString("nombreCliente");
                Button btnCliente = new Button(nombreCliente);
                // Crear un ImageView para el icono del cliente
                ImageView iconoCliente = new ImageView(new Image("https://cdn-icons-png.flaticon.com/128/2194/2194222.png"));
                iconoCliente.setFitHeight(30); // Establecer el alto deseado del icono
                iconoCliente.setFitWidth(30); // Establecer el ancho deseado del icono

                // Establecer el icono junto al texto del botón
                btnCliente.setGraphic(iconoCliente);
                btnCliente.setOnAction(event -> {
                    // Al seleccionar un cliente, agregar el postre al cliente
                    AgregarPostreACliente(idPostre, idCliente);
                });

                // Agregar botón de cliente al GridPane
                gridPane.add(btnCliente, 0, row);
                row++;
            }

            // Cerrar la conexión con la base de datos
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void AgregarPostreACliente(int idPostre, int idCliente) {
        try {
            // Conexión con la base de datos
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/taqueria", "adminTacos", "1234");
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO detalles_orden (idPlatillo, idPostre, idCliente, cantidadPostre) VALUES (?, ?, ?, 1)");
            pstmt.setNull(1, Types.INTEGER);
            pstmt.setInt(2, idPostre);
            pstmt.setInt(3, idCliente);

            // Ejecutar la inserción
            int rowsAffected = pstmt.executeUpdate();

            // Verificar si se insertaron correctamente
            if (rowsAffected > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Postre Agregado");
                alert.setHeaderText(null);
                alert.setContentText("El postre ha sido agregado al cliente correctamente.");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error al Agregar Postre");
                alert.setHeaderText(null);
                alert.setContentText("Hubo un error al intentar agregar el postre al cliente.");
                alert.showAndWait();
            }

            // Cerrar la conexión con la base de datos
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    private void MostrarSnacksTapas() {
        LimpiarGridPane();
        try {
            // Conexión con la base de datos
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/taqueria", "adminTacos", "1234");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT idSnackTapas, nombreSnackTapas FROM snacks_tapas");

            // Botones para los snacks o tapas
            int row = 0;
            int col = 0;
            while (rs.next()) {
                int idSnackTapas = rs.getInt("idSnackTapas");
                String nombreSnackTapas = rs.getString("nombreSnackTapas");
                String iconoURL = IconManager.getIconURLS(nombreSnackTapas); // Obtener la URL del icono correspondiente
                Button btnSnackTapas = createButtonWithIcon(iconoURL, nombreSnackTapas);
                btnSnackTapas.setOnAction(event -> {
                    // Al seleccionar un snack o tapa, mostrar los clientes disponibles
                    MostrarClientesParaSnackTapas(idSnackTapas);
                });

                // Agregar botón de snack o tapa al GridPane
                gridPane.add(btnSnackTapas, col, row);

                // Actualizar posición en el GridPane
                col++;
                if (col == 4) {
                    col = 0;
                    row++;
                }
            }

            // Cerrar la conexión con la base de datos
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void MostrarClientesParaSnackTapas(int idSnackTapas) {
        // Limpiar el GridPane
        LimpiarGridPane();

        try {
            // Conexión con la base de datos
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/taqueria", "adminTacos", "1234");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT idCliente, nombreCliente FROM cliente");

            // Botones para los clientes
            int row = 0;
            while (rs.next()) {
                int idCliente = rs.getInt("idCliente");
                String nombreCliente = rs.getString("nombreCliente");
                Button btnCliente = new Button(nombreCliente);
                // Crear un ImageView para el icono del cliente
                ImageView iconoCliente = new ImageView(new Image("https://cdn-icons-png.flaticon.com/128/2194/2194222.png"));
                iconoCliente.setFitHeight(30); // Establecer el alto deseado del icono
                iconoCliente.setFitWidth(30); // Establecer el ancho deseado del icono

                // Establecer el icono junto al texto del botón
                btnCliente.setGraphic(iconoCliente);
                btnCliente.setOnAction(event -> {
                    // Al seleccionar un cliente, agregar el snack o tapa al cliente
                    AgregarSnackTapasACliente(idSnackTapas, idCliente);
                });

                // Agregar botón de cliente al GridPane
                gridPane.add(btnCliente, 0, row);
                row++;
            }

            // Cerrar la conexión con la base de datos
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void AgregarSnackTapasACliente(int idSnackTapas, int idCliente) {
        try {
            // Conexión con la base de datos
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/taqueria", "adminTacos", "1234");
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO detalles_orden (idPlatillo, idSnackTapas, idCliente, cantidadSnackTapas) VALUES (?, ?, ?, 1)");
            pstmt.setNull(1, Types.INTEGER);
            pstmt.setInt(2, idSnackTapas);
            pstmt.setInt(3, idCliente);

            // Ejecutar la inserción
            int rowsAffected = pstmt.executeUpdate();

            // Verificar si se insertaron correctamente
            if (rowsAffected > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Snack o Tapa Agregado");
                alert.setHeaderText(null);
                alert.setContentText("El snack o tapa ha sido agregado al cliente correctamente.");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error al Agregar Snack o Tapa");
                alert.setHeaderText(null);
                alert.setContentText("Hubo un error al intentar agregar el snack o tapa al cliente.");
                alert.showAndWait();
            }

            // Cerrar la conexión con la base de datos
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void MostrarPlatillos() {
        LimpiarGridPane();
        try {
            // Conexión con la base de datos
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/taqueria", "adminTacos", "1234");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT idPlatillo, nombrePlatillo FROM platillos");

            // Botones para los platillos
            int row = 0;
            int col = 0;
            while (rs.next()) {
                int idPlatillo = rs.getInt("idPlatillo");
                String nombrePlatillo = rs.getString("nombrePlatillo");
                String iconoURL = IconManager.getIconURLPP(nombrePlatillo); // Obtener la URL del icono correspondiente
                Button btnPlatillo = createButtonWithIcon(iconoURL, nombrePlatillo);
                btnPlatillo.setOnAction(event -> {
                    // Al seleccionar un platillo, mostrar los clientes disponibles
                    MostrarClientesParaPlatillo(idPlatillo);
                });

                // Agregar botón de platillo al GridPane
                gridPane.add(btnPlatillo, col, row);

                // Actualizar posición en el GridPane
                col++;
                if (col == 4) {
                    col = 0;
                    row++;
                }
            }

            // Cerrar la conexión con la base de datos
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void MostrarClientesParaPlatillo(int idPlatillo) {
        // Limpiar el GridPane
        LimpiarGridPane();

        try {
            // Conexión con la base de datos
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/taqueria", "adminTacos", "1234");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT idCliente, nombreCliente FROM cliente");

            // Botones para los clientes
            int row = 0;
            while (rs.next()) {
                int idCliente = rs.getInt("idCliente");
                String nombreCliente = rs.getString("nombreCliente");
                Button btnCliente = new Button(nombreCliente);
                // Crear un ImageView para el icono del cliente
                ImageView iconoCliente = new ImageView(new Image("https://cdn-icons-png.flaticon.com/128/2194/2194222.png"));
                iconoCliente.setFitHeight(30); // Establecer el alto deseado del icono
                iconoCliente.setFitWidth(30); // Establecer el ancho deseado del icono

                // Establecer el icono junto al texto del botón
                btnCliente.setGraphic(iconoCliente);
                btnCliente.setOnAction(event -> {
                    // Al seleccionar un cliente, agregar el platillo al cliente
                    AgregarPlatilloACliente(idPlatillo, idCliente);
                });

                // Agregar botón de cliente al GridPane
                gridPane.add(btnCliente, 0, row);
                row++;
            }

            // Cerrar la conexión con la base de datos
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void AgregarPlatilloACliente(int idPlatillo, int idCliente) {
        try {
            // Conexión con la base de datos
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/taqueria", "adminTacos", "1234");
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO detalles_orden (idPlatillo, idBebida, idCliente, cantidadPlatillo) VALUES (?, NULL, ?, 1)");
            pstmt.setInt(1, idPlatillo);
            pstmt.setInt(2, idCliente);

            // Ejecutar la inserción
            int rowsAffected = pstmt.executeUpdate();

            // Verificar si se insertaron correctamente
            if (rowsAffected > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Platillo Agregado");
                alert.setHeaderText(null);
                alert.setContentText("El platillo ha sido agregado al cliente correctamente.");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error al Agregar Platillo");
                alert.setHeaderText(null);
                alert.setContentText("Hubo un error al intentar agregar el platillo al cliente.");
                alert.showAndWait();
            }

            // Cerrar la conexión con la base de datos
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    private void MostrarBotonesMesas() {
        LimpiarGridPane();
        try {
            // Conexión con la base de datos
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/taqueria", "adminTacos", "1234");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM mesas");

            // Botones para mesas
            btnMesas = new Button[8];
            int count = 0;

            while (rs.next() && count < 8) {
                int idMesa = rs.getInt("idMesa");
                int numeroMesa = rs.getInt("numeroMesa");
                int capacidad = rs.getInt("capacidad");
                String estado = rs.getString("estado");

                // Crear imagen del icono de mesa
                ImageView imageView = new ImageView(new Image("https://cdn-icons-png.flaticon.com/128/1237/1237886.png"));
                imageView.setFitWidth(40);
                imageView.setPreserveRatio(true);

                // Crear botón de mesa
                Button btnMesa = new Button("Mesa " + numeroMesa, imageView);
                btnMesa.setOnAction(event -> {
                    // Acción al hacer clic en la mesa
                    SeleccionarClienteParaMesa(idMesa);

                    // Por ejemplo, mostrar información detallada de la mesa
                    System.out.println("Mesa " + numeroMesa + " seleccionada");
                });

                // Establecer el color del botón según el estado de la mesa
                if (estado.equals("Ocupada")) {
                    btnMesa.setStyle("-fx-background-color: red");
                } else {
                    btnMesa.setStyle("-fx-background-color: green");
                }

                btnMesas[count] = btnMesa;
                count++;
            }

            // Agregar botones de mesas al GridPane
            gridPane.addRow(0, btnMesas[0], btnMesas[1], btnMesas[2], btnMesas[3]);
            gridPane.addRow(1, btnMesas[4], btnMesas[5], btnMesas[6], btnMesas[7]);

            // Cerrar la conexión con la base de datos
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para seleccionar un cliente para una mesa
    private void SeleccionarClienteParaMesa(int idMesa) {
        Stage ventanaSeleccionCliente = new Stage();
        ventanaSeleccionCliente.setTitle("Seleccionar Cliente");
        VBox vbox = new VBox();
        vbox.setSpacing(10);

        try {
            // Conexión con la base de datos
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/taqueria", "adminTacos", "1234");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT idCliente, nombreCliente FROM cliente");

            while (rs.next()) {
                int idCliente = rs.getInt("idCliente");
                String nombreCliente = rs.getString("nombreCliente");
                Button btnCliente = new Button(nombreCliente);
                // Crear un ImageView para el icono del cliente
                ImageView iconoCliente = new ImageView(new Image("https://cdn-icons-png.flaticon.com/128/2194/2194222.png"));
                iconoCliente.setFitHeight(30); // Establecer el alto deseado del icono
                iconoCliente.setFitWidth(30); // Establecer el ancho deseado del icono

                // Establecer el icono junto al texto del botón
                btnCliente.setGraphic(iconoCliente);
                btnCliente.setOnAction(event -> {
                    // Acción al seleccionar un cliente
                    AsignarClienteAMesa(idMesa, idCliente);
                    ventanaSeleccionCliente.close();
                });

                vbox.getChildren().add(btnCliente);
            }

            // Cerrar la conexión con la base de datos
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(vbox, 200, 200);
        ventanaSeleccionCliente.setScene(scene);
        ventanaSeleccionCliente.show();
    }

    // Método para asignar un cliente a una mesa
    private void AsignarClienteAMesa(int idMesa, int idCliente) {
        try {
            // Conexión con la base de datos
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/taqueria", "adminTacos", "1234");
            PreparedStatement pstmt = conn.prepareStatement("UPDATE mesas SET idCliente = ? WHERE idMesa = ?");
            pstmt.setInt(1, idCliente);
            pstmt.setInt(2, idMesa);

            // Ejecutar la actualización
            int rowsAffected = pstmt.executeUpdate();

            // Verificar si se actualizó correctamente
            if (rowsAffected > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Cliente Asignado");
                alert.setHeaderText(null);
                alert.setContentText("El cliente ha sido asignado a la mesa correctamente.");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error al Asignar Cliente");
                alert.setHeaderText(null);
                alert.setContentText("Hubo un error al intentar asignar el cliente a la mesa.");
                alert.showAndWait();
            }

            // Cerrar la conexión con la base de datos
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void LimpiarGridPane() {
        gridPane.getChildren().clear();
    }

    private Button createButtonWithIcon(String iconUrl, String buttonText) {
        Button button = new Button();
        ImageView imageView = new ImageView(new Image(iconUrl));
        imageView.setFitWidth(55); // Ajustar el ancho del icono
        imageView.setPreserveRatio(true); // Mantener la relación de aspecto del icono
        Label label = new Label(buttonText);
        VBox vbox = new VBox();
        vbox.getChildren().addAll(imageView, label);
        vbox.setAlignment(javafx.geometry.Pos.CENTER);
        button.setGraphic(vbox);
        return button;
    }

    private HBox createMenuIconWithText(String iconUrl, String text) {
        ImageView imageView = new ImageView(new Image(iconUrl));
        imageView.setFitWidth(55); // Ajustar el ancho del icono
        imageView.setPreserveRatio(true); // Mantener la relación de aspecto del icono
        Label label = new Label(text);
        HBox hbox = new HBox();
        hbox.getChildren().addAll(imageView, label);
        hbox.setAlignment(javafx.geometry.Pos.CENTER);
        return hbox;
    }
}


/*package com.example.demo.vistas;

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

        btnClientes.setOnAction(event -> new ClienteTaqueria());
        btnEmpleados.setOnAction(action -> new EmpleadoTaqueria());
        btnOrdenes.setOnAction(action -> new OrdenesTaqueria());
        btnPlatillos.setOnAction(action -> new PlatilloTaqueria());
        btnDetOrden.setOnAction(action -> new DetallesOrdenTaqueria());
        btnDetPlatillo.setOnAction(action -> new DetallesPlatilloTaqueria());
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
}*/
