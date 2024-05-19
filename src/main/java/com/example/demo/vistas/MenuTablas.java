package com.example.demo.vistas;
import com.aspose.pdf.*;
import com.aspose.pdf.Cell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.example.demo.modelos.OrdenDAO;
import com.example.demo.modelos.PedidoDAO;
import com.example.demo.modelos.PlatosDAO;

import java.io.FileOutputStream;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class MenuTablas extends Stage {

    private Scene escena;
    private VBox vBox;
    private GridPane gridPane;
    private Button btnusuarios, btnEmpleados, btnOrdenes, btnPlatillos, btnInsumos, btnMesass;
    private Button[] btnMesas;
    private Label lblTotal; // Campo para el Label del total
    private Button btnPedido; // Campo para el botón de pedido
    private TableView<OrdenDAO> tbvOrdenes;
    private TableView<OrdenDAO> tblOrdenes;
    private int mesaSeleccionada; // Declarar aquí
    private Label lblMesaSeleccionada;

    public MenuTablas() {
        Locale.setDefault(new Locale("en", "US")); // Establece la configuración regional en inglés (Estados Unidos)
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
            LimpiarGridPane();
            LimpiarVBox();
            MostrarBotonesTablas();
        });

        menuTablas.getItems().add(itemMenuTablas);

        // Crear menú para mesas
        Menu menuMesas = new Menu();
        menuMesas.setGraphic(createMenuIconWithText("https://cdn-icons-png.flaticon.com/128/4001/4001039.png", "Mesas"));


        MenuItem itemMenuMesas = new MenuItem("Mostrar Mesas");
        itemMenuMesas.setOnAction(event -> {
            LimpiarGridPane();
            LimpiarVBox();
            MostrarBotonesMesas();
        });

        menuMesas.getItems().add(itemMenuMesas);

        // Crear menú para categorías
        Menu menuCategorias = new Menu();
        menuCategorias.setGraphic(createMenuIconWithText("https://cdn-icons-png.flaticon.com/128/1357/1357589.png", "Categorías"));

        MenuItem itemMenuCategorias = new MenuItem("Mostrar Categorias");
        itemMenuCategorias.setOnAction(event -> {
            LimpiarGridPane();
            LimpiarVBox();
            MostrarBotonesCategorias();
        });

        menuCategorias.getItems().add(itemMenuCategorias);
        // Crear menú para productos mas vendidos
        Menu menuMasVendidos = new Menu();
        menuMasVendidos.setGraphic(createMenuIconWithText("https://cdn-icons-png.flaticon.com/128/2424/2424721.png", "Más vendidos"));

        MenuItem itemMenuMasVendidos = new MenuItem("Productos Más Vendidos");
        itemMenuMasVendidos.setOnAction(event -> {
            LimpiarGridPane();
            LimpiarVBox();
            MostrarProductosMasVendidos();
        });
        menuMasVendidos.getItems().add(itemMenuMasVendidos);
        // Crear menú para caja
        Menu menuCaja = new Menu();
        menuCaja.setGraphic(createMenuIconWithText("https://cdn-icons-png.flaticon.com/128/4689/4689889.png", "Caja"));

        MenuItem itemMenuCaja = new MenuItem("Mostrar Caja");
        itemMenuCaja.setOnAction(event -> {
            LimpiarGridPane();
            LimpiarVBox();
            MostrarBotonesCaja();
        });

        menuCaja.getItems().add(itemMenuCaja);

        // Crear menú para pedido
        Menu menuPedido = new Menu();
        menuPedido.setGraphic(createMenuIconWithText("https://cdn-icons-png.flaticon.com/128/6384/6384868.png", "Pedido"));

        MenuItem itemMenuPedido = new MenuItem("Mostrar Pedido");
        itemMenuPedido.setOnAction(event -> {
            LimpiarGridPane();
            LimpiarVBox();
            MostrarBotonesPedido(); // Asegúrate de tener este método en tu clase
        });

        menuPedido.getItems().add(itemMenuPedido);
        // Agregar menús a la barra de menú
        menuBar.getMenus().addAll(menuTablas, menuMesas, menuCategorias, menuMasVendidos, menuCaja, menuPedido);

        vBox = new VBox();
        gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setVgap(20);
        gridPane.setHgap(20);

        // Agregar elementos al VBox
        vBox.getChildren().addAll(menuBar, gridPane);
        escena = new Scene(vBox, 790, 430);
    }
    private void MostrarBotonesTablas() {
        LimpiarGridPane();
        // Botones para las tablas
        btnusuarios = createButtonWithIcon("https://cdn-icons-png.flaticon.com/512/6009/6009864.png", "Tabla Usuarios");
        btnEmpleados = createButtonWithIcon("https://cdn-icons-png.flaticon.com/128/1830/1830878.png", "Tabla Empleados");
        btnOrdenes = createButtonWithIcon("https://cdn-icons-png.flaticon.com/512/2082/2082194.png", "Tabla Ordenes");
        btnPlatillos = createButtonWithIcon("https://cdn-icons-png.flaticon.com/128/1046/1046849.png", "Tabla MENU");
        btnInsumos = createButtonWithIcon("https://cdn-icons-png.flaticon.com/512/917/917940.png", "Tabla Pedidos");
        btnMesass = createButtonWithIcon("https://cdn-icons-png.flaticon.com/128/673/673281.png", "Tabla Mesas");

        // Asignar acciones a los botones
        btnusuarios.setOnAction(event -> new UsuarioTaqueria());
        btnEmpleados.setOnAction(action -> new EmpleadoTaqueria());
        btnOrdenes.setOnAction(action -> new OrdenTaqueria());
        btnPlatillos.setOnAction(action -> new PlatosTaqueria());
        btnInsumos.setOnAction(action -> new PedidoTaqueria());
        btnMesass.setOnAction(action -> new MesasTaqueria());

        // Agregar botones al GridPane

        gridPane.addRow(0, btnusuarios, btnEmpleados, btnPlatillos);
        gridPane.addRow(1,  btnMesass, btnOrdenes, btnInsumos);


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


        // Agregar botones al GridPane
        gridPane.addRow(0, btnBebidas, btnDesayunos, btnBocadillos, btnGuarniciones);
        gridPane.addRow(1, btnCafes, btnPostres, btnSnacksTapas, btnPlatillo);
    }

    private void MostrarBotonesMesas() {
        LimpiarGridPane();
        try {
            // Conexión con la base de datos
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/taqueria2", "adminTacos2", "123");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM mesas");

            // Botones para mesas
            btnMesas = new Button[8];
            int count = 0;

            while (rs.next() && count < 8) {
                int id = rs.getInt("id");
                int numeroMesa = rs.getInt("numeroMesa");
                String estado = rs.getString("estado");

                // Crear botón de mesa
                Button btnMesa = new Button("Mesa " + numeroMesa);
                btnMesa.setOnAction(event -> {
                    // Acción al hacer clic en la mesa
                    if (btnMesa.getStyle().equals("-fx-background-color: green")) {
                        btnMesa.setStyle("-fx-background-color: red");
                        ActualizarEstadoMesa(id, "Ocupada");
                        mesaSeleccionada = numeroMesa; // Almacenar el número de la mesa
                        MostrarBotonesCaja();
                    } else {
                        btnMesa.setStyle("-fx-background-color: green");
                        ActualizarEstadoMesa(id, "Libre");
                    }
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

    private void ActualizarEstadoMesa(int id, String estado) {
        try {
            // Conexión con la base de datos
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/taqueria2", "adminTacos2", "123");
            PreparedStatement pstmt = conn.prepareStatement("UPDATE mesas SET estado = ? WHERE id = ?");

            pstmt.setString(1, estado);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();

            // Cerrar la conexión con la base de datos
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void LimpiarVBox() {
        if (vBox.getChildren().contains(lblTotal)) {
            vBox.getChildren().remove(lblTotal);
        }
        if (vBox.getChildren().contains(btnPedido)) {
            vBox.getChildren().remove(btnPedido);
        }
    }
    private void MostrarProductosMasVendidos() {
        LimpiarGridPane();  // Método para limpiar el GridPane antes de añadir los nuevos botones.

        try {
            // Conexión con la base de datos.
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/taqueria2", "adminTacos2", "123");

            // Consulta SQL para obtener los 16 platos más vendidos.
            String query = "SELECT p.id, p.nombre, p.categoria, COUNT(o.id_plato) AS total_ventas " +
                    "FROM platos p " +
                    "JOIN pedidos o ON p.id = o.id_plato " +
                    "GROUP BY p.id, p.nombre, p.categoria " +
                    "ORDER BY total_ventas DESC " +
                    "LIMIT 18";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            // Añadir la etiqueta "Productos más vendidos del día" al GridPane.
            Label label = new Label("Productos más vendidos del día");
            gridPane.add(label, 0, 0, 3, 1);  // Colocar la etiqueta en la primera fila y abarcar cuatro columnas.

            // Variables para posicionar los botones en el GridPane.
            int row = 1;  // Empezar en la fila 1 porque la fila 0 tiene la etiqueta.
            int col = 0;

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String categoria = rs.getString("categoria");
                String iconoURL = IconManager.getIconURL(nombre, categoria);  // Obtener la URL del icono correspondiente.

                // Crear botón con el icono y el nombre del plato.
                Button btnPlato = createButtonWithIcon(iconoURL, nombre);
                btnPlato.setOnAction(event -> {
                    // Acción al hacer clic en el botón (puedes personalizar esta parte).
                    System.out.println("Plato seleccionado: " + nombre);
                });

                // Agregar el botón al GridPane en la posición especificada.
                gridPane.add(btnPlato, col, row);

                // Actualizar la posición en el GridPane.
                col++;
                if (col == 5) {
                    col = 0;
                    row++;
                }
            }

            // Cerrar la conexión con la base de datos.
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


   /* private void MostrarBotonesCaja() {
        LimpiarGridPane();

        // Crear la tabla de ordenes
        TableView<OrdenDAO> tblOrdenes = crearTablaOrdenes();

        // Crear la tabla de platos
        TableView<PlatosDAO> tblPlatos = crearTablaPlatos(tblOrdenes);

        // Agregar las tablas al GridPane
        gridPane.add(tblPlatos, 0, 0, 1, 2);
        gridPane.add(tblOrdenes, 1, 0, 1, 2);

        // Inicializar el Label del total
        lblTotal = new Label();
        vBox.getChildren().add(lblTotal);

        // Inicializar el botón de pedido
        btnPedido = new Button("Realizar pedido");
        btnPedido.setStyle("-fx-font-size: 15px; " +
                "-fx-font-weight: bold; " +
                "-fx-text-fill: #ffffff; " +
                "-fx-background-color: #4CAF50; " +
                "-fx-padding: 10px; " +
                "-fx-border-radius: 5px;");
        vBox.getChildren().add(btnPedido);
        // Agregar un manejador de eventos al botón
        btnPedido.setOnAction(event -> {
            // Obtener los datos de la tabla de órdenes
            ObservableList<OrdenDAO> listaOrdenes = tblOrdenes.getItems();

            // Insertar los datos de la tabla de órdenes en la tabla de pedidos
            for (OrdenDAO orden : listaOrdenes) {
                // Crear un objeto PedidoDAO con los datos de la orden
                PedidoDAO pedido = new PedidoDAO();
                pedido.setNombre(orden.getNombre());
                pedido.setPrecio(orden.getPrecio());
                pedido.setCantidad(orden.getCantidad());
                pedido.setComentario(orden.getComentario());
                pedido.setId_plato(orden.getId_plato());

                // Insertar el pedido en la base de datos
                pedido.INSERTAR(); // Utilizamos el método INSERTAR para insertar el pedido en la base de datos
            }
            float total = 0;
            for (OrdenDAO orden : listaOrdenes) {
                total += orden.getSubtotal();
            }

            generarPDF(listaOrdenes, total);

            OrdenDAO ordenDAO = new OrdenDAO();
            ObservableList<OrdenDAO> nuevaListaOrdenes = FXCollections.observableArrayList();
            tblOrdenes.setItems(nuevaListaOrdenes);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Pedido");
            alert.setHeaderText(null);
            alert.setContentText("Pedido realizado y almacenado en la tabla de pedidos");
            alert.showAndWait();

            actualizarTablaOrdenes(tblOrdenes);
        });

        // Actualizar el total
        actualizarTabla();
    }*/
   private void MostrarBotonesCaja() {
       LimpiarGridPane();

       // Crear la tabla de órdenes
       TableView<OrdenDAO> tblOrdenes = crearTablaOrdenes();

       // Crear la tabla de platos
       TableView<PlatosDAO> tblPlatos = crearTablaPlatos(tblOrdenes);

       // Agregar las tablas al GridPane
       gridPane.add(tblPlatos, 0, 0, 1, 2);
       gridPane.add(tblOrdenes, 1, 0, 1, 2);

       // Inicializar el Label del total
       lblTotal = new Label();
       lblMesaSeleccionada = new Label("Mesa seleccionada: ");
       HBox hBoxTotalMesa = new HBox(10, lblTotal, lblMesaSeleccionada);
       vBox.getChildren().add(hBoxTotalMesa);

       // Inicializar el botón de pedido
       btnPedido = new Button("Realizar pedido");
       btnPedido.setStyle("-fx-font-size: 15px; " +
               "-fx-font-weight: bold; " +
               "-fx-text-fill: #ffffff; " +
               "-fx-background-color: #4CAF50; " +
               "-fx-padding: 10px; " +
               "-fx-border-radius: 5px;");
       vBox.getChildren().add(btnPedido);

       // Agregar un manejador de eventos al botón
       btnPedido.setOnAction(event -> {
           // Obtener los datos de la tabla de órdenes
           ObservableList<OrdenDAO> listaOrdenes = tblOrdenes.getItems();

           // Insertar los datos de la tabla de órdenes en la tabla de pedidos
           for (OrdenDAO orden : listaOrdenes) {
               // Crear un objeto PedidoDAO con los datos de la orden
               PedidoDAO pedido = new PedidoDAO();
               pedido.setNombre(orden.getNombre());
               pedido.setPrecio(orden.getPrecio());
               pedido.setCantidad(orden.getCantidad());
               pedido.setComentario(orden.getComentario());
               pedido.setId_plato(orden.getId_plato());

               // Insertar el pedido en la base de datos
               pedido.INSERTAR(); // Utilizamos el método INSERTAR para insertar el pedido en la base de datos
           }
           float total = 0;
           for (OrdenDAO orden : listaOrdenes) {
               total += orden.getSubtotal();
           }

           generarPDF(listaOrdenes, total);

           OrdenDAO ordenDAO = new OrdenDAO();
           ObservableList<OrdenDAO> nuevaListaOrdenes = FXCollections.observableArrayList();
           tblOrdenes.setItems(nuevaListaOrdenes);

           Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setTitle("Pedido");
           alert.setHeaderText(null);
           alert.setContentText("Pedido realizado y almacenado en la tabla de pedidos");
           alert.showAndWait();

           actualizarTablaOrdenes(tblOrdenes);
       });

       // Inicializar y agregar la sección de mesas
       VBox mesasBox = new VBox();
       Label lblMesas = new Label("Seleccionar Mesa");
       mesasBox.getChildren().add(lblMesas);
       gridPane.add(mesasBox, 2, 0, 1, 2);

       // Obtener las mesas disponibles de la base de datos y añadir botones
       try {
           // Conexión con la base de datos
           Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/taqueria2", "adminTacos2", "123");
           Statement stmt = conn.createStatement();
           ResultSet rs = stmt.executeQuery("SELECT * FROM mesas");

           // Botones para mesas
           while (rs.next()) {
               int id = rs.getInt("id");
               int numeroMesa = rs.getInt("numeroMesa");
               String estado = rs.getString("estado");

               // Crear botón de mesa
               Button btnMesa = new Button("Mesa " + numeroMesa);
               btnMesa.setOnAction(event -> {
                   if (estado.equals("Libre")) {
                       mesaSeleccionada = numeroMesa;
                       lblMesaSeleccionada.setText("Mesa seleccionada: " + mesaSeleccionada);
                   }
               });

               // Establecer el color del botón según el estado de la mesa
               if (estado.equals("Ocupada")) {
                   btnMesa.setStyle("-fx-background-color: red");
                   btnMesa.setDisable(true); // Desactivar el botón si la mesa está ocupada
               } else {
                   btnMesa.setStyle("-fx-background-color: green");
               }

               mesasBox.getChildren().add(btnMesa);
           }

           // Cerrar la conexión con la base de datos
           rs.close();
           stmt.close();
           conn.close();
       } catch (SQLException e) {
           e.printStackTrace();
       }

       // Actualizar el total
       actualizarTabla();
   }
    public void generarPDF(ObservableList<OrdenDAO> listaOrdenes, float total) {
        Document pdfDocument = new Document();
        Page page = pdfDocument.getPages().add();
        // Agregar el logo al PDF
        String logoPath = "src/main/resources/images/taco.png";
        com.aspose.pdf.Image image = new com.aspose.pdf.Image();
        image.setFile(logoPath);
        // Ajustar el tamaño de la imagen
        image.setImageScale(0.3); // Cambia este valor según lo necesites para ajustar el tamaño
        page.getParagraphs().add(image);

        // Agregar título y encabezado
        TextFragment title = new TextFragment("Resumen del Pedido");
        title.getTextState().setFontSize(20);
        title.getTextState().setFontStyle(FontStyles.Bold);
        page.getParagraphs().add(title);
        // Agregar mesa seleccionada
        TextFragment mesaText = new TextFragment("Mesa seleccionada: " + mesaSeleccionada);
        mesaText.getTextState().setFontSize(14);
        mesaText.getTextState().setFontStyle(FontStyles.Bold);
        page.getParagraphs().add(mesaText);

        // Agregar tabla de órdenes
        Table table = new Table();
        table.setColumnWidths("50 100 50 50 100 100");

        // Agregar fila de encabezado
        Row headerRow = table.getRows().add();
        headerRow.getCells().add("ID");
        headerRow.getCells().add("Nombre");
        headerRow.getCells().add("Precio");
        headerRow.getCells().add("Cantidad");
        headerRow.getCells().add("Subtotal");
        headerRow.getCells().add("Comentario");
// Agregar mensaje de agradecimiento
        TextFragment mensajeAgradecimiento = new TextFragment("Gracias por su compra");
        mensajeAgradecimiento.getTextState().setFontSize(14);
        mensajeAgradecimiento.getTextState().setFontStyle(FontStyles.Bold);
        mensajeAgradecimiento.setMargin(new MarginInfo(10, 0, 0, 0)); // Ajusta los márgenes si es necesario
        page.getParagraphs().add(mensajeAgradecimiento);
        // Agregar filas de datos
        for (OrdenDAO orden : listaOrdenes) {
            Row row = table.getRows().add();
            row.getCells().add(String.valueOf(orden.getId()));
            row.getCells().add(orden.getNombre());
            row.getCells().add(String.valueOf(orden.getPrecio()));
            row.getCells().add(String.valueOf(orden.getCantidad()));
            row.getCells().add(String.valueOf(orden.getSubtotal()));
            row.getCells().add(orden.getComentario());
        }

        // Estilizar la tabla
        table.setBorder(new BorderInfo(BorderSide.All, 1f));
        table.setDefaultCellPadding(new MarginInfo(4f, 4f, 4f, 4f));

        // Agregar la tabla a la página
        page.getParagraphs().add(table);

        // Agregar total
        TextFragment totalText = new TextFragment("Total a pagar: " + total);
        totalText.getTextState().setFontSize(14);
        totalText.getTextState().setFontStyle(FontStyles.Bold);
        page.getParagraphs().add(totalText);

        // Guardar el PDF
        try {
            pdfDocument.save("Pedido.pdf");
            System.out.println("PDF creado con éxito.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void actualizarTabla() {
        OrdenDAO ordenDAO = new OrdenDAO();
        float total = ordenDAO.calcularTotal();

        // Verificar si lblTotal ya existe en vBox
        if (lblTotal != null && vBox.getChildren().contains(lblTotal)) {
            // Si lblTotal ya existe, eliminarlo del layout
            vBox.getChildren().remove(lblTotal);
        }

        // Crear un nuevo Label para mostrar el total
        lblTotal = new Label("Total a pagar: " + total);

// Aplicar CSS al Label
        lblTotal.setStyle("-fx-font-size: 15px; " +
                "-fx-font-weight: bold; " +
                "-fx-text-fill: #ff0000; " +
                "-fx-background-color: #ffff00; " +
                "-fx-padding: 10px; " +
                "-fx-border-color: #0000ff; " +
                "-fx-border-width: 5px; " +
                "-fx-border-radius: 10px;");

        // Agregar el Label al layout
        vBox.getChildren().add(lblTotal);
    }
    private TableView<PlatosDAO> crearTablaPlatos(TableView<OrdenDAO> tblOrdenes) {
        TableView<PlatosDAO> tblPlatos = new TableView<>();
        TableColumn<PlatosDAO, Integer> tbcId = new TableColumn<>("ID");
        tbcId.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<PlatosDAO, String> tbcNombre = new TableColumn<>("Nombre");
        tbcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        TableColumn<PlatosDAO, Double> tbcPrecio = new TableColumn<>("Precio");
        tbcPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));

        // Crear una instancia de PlatosDAO y obtener la lista de platos
        PlatosDAO platosDAO = new PlatosDAO();
        ObservableList<PlatosDAO> listaPlatos = platosDAO.SELECCIONAR();

        // Añadir los platos a la tabla
        tblPlatos.setItems(listaPlatos);
        tblPlatos.getColumns().addAll(tbcId, tbcNombre, tbcPrecio);

        // Agregar un RowFactory personalizado para manejar los eventos de clic
        tblPlatos.setRowFactory(tv -> {
            TableRow<PlatosDAO> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton()==MouseButton.PRIMARY
                        && event.getClickCount() == 2) {

                    PlatosDAO clickedRow = row.getItem();
                    // Aquí puedes agregar el plato seleccionado a la tabla detalle_pedidos
                    agregarPlatoAOrden(clickedRow, tblOrdenes);
                }
            });
            return row ;
        });
        return tblPlatos;
    }

    private TableView<OrdenDAO> crearTablaOrdenes() {
        TableColumn<OrdenDAO, Integer> tbcId = new TableColumn<>("ID");
        tbcId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<OrdenDAO, String> tbcNombre = new TableColumn<>("Nombre");
        tbcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<OrdenDAO, Double> tbcPrecio = new TableColumn<>("Precio");
        tbcPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));

        TableColumn<OrdenDAO, Integer> tbcCantidad = new TableColumn<>("Cantidad");
        tbcCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));

        TableColumn<OrdenDAO, Double> tbcSubtotal = new TableColumn<>("Subtotal");
        tbcSubtotal.setCellValueFactory(new PropertyValueFactory<>("subtotal"));

        TableColumn<OrdenDAO, String> tbcComentario = new TableColumn<>("Comentario");
        tbcComentario.setCellValueFactory(new PropertyValueFactory<>("comentario"));

        TableColumn<OrdenDAO, Integer> tbcIdPlato = new TableColumn<>("ID Plato");
        tbcIdPlato.setCellValueFactory(new PropertyValueFactory<>("id_plato"));

        TableView<OrdenDAO> tablaOrdenes = new TableView<>();
        tablaOrdenes.getColumns().add(tbcId);
        tablaOrdenes.getColumns().add(tbcNombre);
        tablaOrdenes.getColumns().add(tbcPrecio);
        tablaOrdenes.getColumns().add(tbcCantidad);
        tablaOrdenes.getColumns().add(tbcSubtotal);
        tablaOrdenes.getColumns().add(tbcComentario);
        tablaOrdenes.getColumns().add(tbcIdPlato);

        return tablaOrdenes;
    }
    private void agregarPlatoAOrden(PlatosDAO plato, TableView<OrdenDAO> tblOrdenes) {
        OrdenDAO orden = new OrdenDAO();
        orden.setNombre(plato.getNombre());
        orden.setPrecio((float) plato.getPrecio());
        orden.setCantidad(1); // Inicialmente, la cantidad es 1
        // No establecemos el subtotal aquí, ya que es una columna calculada en la base de datos
        orden.setId_plato(plato.getId()); // Establecemos el id_plato con el id del plato seleccionado
        orden.INSERTAR(); // Insertar la orden en la base de datos
        actualizarTablaOrdenes(tblOrdenes);
        actualizarTabla(); // Actualizar el total
    }
    private void actualizarTablaOrdenes(TableView<OrdenDAO> tblOrdenes) {
        // Crear una nueva instancia de OrdenDAO y obtener la lista de ordenes
        OrdenDAO ordenDAO = new OrdenDAO();
        ObservableList<OrdenDAO> listaOrdenes = ordenDAO.SELECCIONAR();

        // Actualizar los items de la tabla de ordenes
        tblOrdenes.setItems(listaOrdenes);
    }
    private void MostrarBotonesPedido() {
        LimpiarGridPane(); // Limpiamos el GridPane para mostrar la nueva interfaz

        // Crear la tabla de pedidos
        TableView<PedidoDAO> tblPedidos = crearTablaPedidos();

        // Agregar la tabla de pedidos y el botón de realizar pedido al GridPane
        gridPane.add(tblPedidos, 0, 0);

    }
    private TableView<PedidoDAO> crearTablaPedidos() {

        // private TableView<PedidoDAO> crearTablaPedidos (TableView < OrdenDAO > tblOrdenes) {
        TableView<PedidoDAO> tblPedidos = new TableView<>();
        TableColumn<PedidoDAO, Integer> tbcIdPedido = new TableColumn<>("Pedido (ID)");
        tbcIdPedido.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<PedidoDAO, String> tbcNombre = new TableColumn<>("Nombre");
        tbcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<PedidoDAO, Double> tbcPrecio = new TableColumn<>("Precio");
        tbcPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));

        TableColumn<PedidoDAO, Integer> tbcCantidad = new TableColumn<>("Cantidad");
        tbcCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));

        TableColumn<PedidoDAO, String> tbcComentario = new TableColumn<>("Comentario");
        tbcComentario.setCellValueFactory(new PropertyValueFactory<>("comentario"));

        TableColumn<PedidoDAO, Integer> tbcIdPlato = new TableColumn<>("ID Plato");
        tbcIdPlato.setCellValueFactory(new PropertyValueFactory<>("id_plato"));

        // Crear una instancia de PedidoDAO y obtener la lista de pedidos
        PedidoDAO pedidoDAO = new PedidoDAO();
        ObservableList<PedidoDAO> listaPedidos = pedidoDAO.SELECCIONAR();

        // Añadir los pedidos a la tabla
        tblPedidos.setItems(listaPedidos);
        tblPedidos.getColumns().addAll(tbcIdPedido, tbcNombre, tbcPrecio, tbcCantidad, tbcComentario, tbcIdPlato);
        return tblPedidos;
    }

    //   }

    //-------------------------
    //-------------------------
    private int obtenerIdMesa() {
        // Aquí va el código para obtener el id de la mesa
        // Este es solo un ejemplo, necesitarás reemplazarlo con tu propio código
        return 1;
    }

    private int obtenerNumMesa() {
        // Aquí va el código para obtener el número de la mesa
        // Este es solo un ejemplo, necesitarás reemplazarlo con tu propio código
        return 1;
    }

    private double calcularTotal() {
        // Aquí va el código para calcular el total
        // Este es solo un ejemplo, necesitarás reemplazarlo con tu propio código
        return 0.0;
    }

    private String obtenerUsuario() {
        // Aquí va el código para obtener el usuario
        // Este es solo un ejemplo, necesitarás reemplazarlo con tu propio código
        return "usuario";
    }

    private int crearNuevoPedido(int idMesa, int numMesa, double total, String usuario) {
        // Aquí va el código para crear un nuevo pedido
        // Este es solo un ejemplo, necesitarás reemplazarlo con tu propio código
        return 1;
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


    private void MostrarDesayunos() {
        LimpiarGridPane();
        try {
            // Conexión con la base de datos
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/taqueria2", "adminTacos2", "123");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, nombre, categoria FROM platos WHERE categoria = 'desayuno'");

            // Botones para los desayunos
            int row = 0;
            int col = 0;
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String categoria = rs.getString("categoria");
                String iconoURL = IconManager.getIconURL(nombre, categoria); // Obtener la URL del icono correspondiente

                Button btnBebida = createButtonWithIcon(iconoURL, nombre);
                btnBebida.setOnAction(event -> {

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
    private void MostrarBocadillos() {
        LimpiarGridPane();
        try {
            // Conexión con la base de datos
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/taqueria2", "adminTacos2", "123");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, nombre, categoria FROM platos WHERE categoria = 'bocadillo'");

            // Botones para los bocadillos
            int row = 0;
            int col = 0;
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String categoria = rs.getString("categoria");
                String iconoURL = IconManager.getIconURL(nombre, categoria); // Obtener la URL del icono correspondiente

                Button btnBebida = createButtonWithIcon(iconoURL, nombre);
                btnBebida.setOnAction(event -> {

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
    private void MostrarGuarniciones() {
        LimpiarGridPane();
        try {
            // Conexión con la base de datos
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/taqueria2", "adminTacos2", "123");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, nombre, categoria FROM platos WHERE categoria = 'guarnicion'");

            // Botones para las guarniciones
            int row = 0;
            int col = 0;
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String categoria = rs.getString("categoria");
                String iconoURL = IconManager.getIconURL(nombre, categoria); // Obtener la URL del icono correspondiente

                Button btnBebida = createButtonWithIcon(iconoURL, nombre);
                btnBebida.setOnAction(event -> {

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

    private void MostrarCafes() {
        LimpiarGridPane();
        try {
            // Conexión con la base de datos
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/taqueria2", "adminTacos2", "123");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, nombre, categoria FROM platos WHERE categoria = 'cafe'");

            // Botones para los cafés
            int row = 0;
            int col = 0;
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String categoria = rs.getString("categoria");
                String iconoURL = IconManager.getIconURL(nombre, categoria); // Obtener la URL del icono correspondiente

                Button btnBebida = createButtonWithIcon(iconoURL, nombre);
                btnBebida.setOnAction(event -> {

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

    private void MostrarPostres() {
        LimpiarGridPane();
        try {
            // Conexión con la base de datos
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/taqueria2", "adminTacos2", "123");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, nombre, categoria FROM platos WHERE categoria = 'postre'");

            // Botones para los postres
            int row = 0;
            int col = 0;
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String categoria = rs.getString("categoria");
                String iconoURL = IconManager.getIconURL(nombre, categoria); // Obtener la URL del icono correspondiente

                Button btnBebida = createButtonWithIcon(iconoURL, nombre);
                btnBebida.setOnAction(event -> {

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
    private void MostrarSnacksTapas() {
        LimpiarGridPane();
        try {
            // Conexión con la base de datos
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/taqueria2", "adminTacos2", "123");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, nombre, categoria FROM platos WHERE categoria = 'snackTapas'");

            // Botones para los snacks o tapas
            int row = 0;
            int col = 0;
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String categoria = rs.getString("categoria");
                String iconoURL = IconManager.getIconURL(nombre, categoria); // Obtener la URL del icono correspondiente

                Button btnBebida = createButtonWithIcon(iconoURL, nombre);
                btnBebida.setOnAction(event -> {

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

    private void MostrarBebidas() {
        LimpiarGridPane();
        try {
            // Conexión con la base de datos
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/taqueria2", "adminTacos2", "123");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, nombre, categoria FROM platos WHERE categoria = 'bebida'");

            // Botones para las bebidas
            int row = 0;
            int col = 0;
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String categoria = rs.getString("categoria");
                String iconoURL = IconManager.getIconURL(nombre, categoria); // Obtener la URL del icono correspondiente

                Button btnBebida = createButtonWithIcon(iconoURL, nombre);
                btnBebida.setOnAction(event -> {

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

    private void MostrarPlatillos() {
        LimpiarGridPane();
        try {
            // Conexión con la base de datos
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/taqueria2", "adminTacos2", "123");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, nombre, categoria FROM platos WHERE categoria = 'platillos'");

            // Botones para los platillos
            int row = 0;
            int col = 0;
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String categoria = rs.getString("categoria");
                String iconoURL = IconManager.getIconURL(nombre, categoria); // Obtener la URL del icono correspondiente

                Button btnBebida = createButtonWithIcon(iconoURL, nombre);
                btnBebida.setOnAction(event -> {

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
}

/*import javafx.geometry.HPos;
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
*/

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
