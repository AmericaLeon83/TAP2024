package com.example.demo.vistas;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class memorama extends Stage {

    private Scene escena;
    private Label lblPares, lblJugador1, lblJugador2, lblTiempoTotal, lblTiempoTurno1, lblTiempoTurno2, lblScore1, lblScore2, lblPuntos1, lblPuntos2, lblPuntosValor1, lblPuntosValor2;
    private GridPane gdpJuego;
    private Button btnJugar;
    private VBox vbxPrincipal, vbxJugadores;
    private HBox hbxTiempo, hbxJuego, hbxJugador1, hbxJugador2, hbxTiempoTotal, hbxTiempoJugador1, hbxTiempoJugador2, hbxPuntosJugador1, hbxPuntosJugador2;
    private Button[][] arBtnCartas;
    private Timeline timeline, timelineVolteo;
    private Integer timeSeconds = 0, tiempoTurno = 5;
    private int jugadorActual = 1;
    private int intentosJugador1 = 0;
    private int intentosJugador2 = 0;
    private int cartasVolteadasJugadorActual = 0;
    private int carta1Jugador1 = -1;
    private int carta2Jugador1 = -1;
    private int carta1Jugador2 = -1;
    private int carta2Jugador2 = -1;
    private String imagenCarta1Jugador1;
    private String imagenCarta2Jugador1;
    private String imagenCarta1Jugador2;
    private String imagenCarta2Jugador2;
    private boolean[][] cartaVolteada;
    private boolean[] parejasVolteadas = new boolean[16]; // Para asegurarnos de que cada par se voltee solo una vez
    private boolean[][] parejasEncontradas; // Nueva variable para rastrear las parejas encontradas
    private TextField txtNumPares; // Nuevo TextField para el número de pares
    private int paresEncontradosJugador1 = 0;
    private int paresEncontradosJugador2 = 0;
    private List<String> nombresImagenes; // Lista para almacenar los nombres de las imágenes
    private boolean primerTurnoJugador2 = true; // Variable para rastrear el primer turno del jugador 2
    public memorama() {
        crearUI();
        this.setTitle("Memorama :)");
        this.setScene(escena);
        this.show();
    }

    private void crearUI() {
        lblPares = new Label(":");
        btnJugar = new Button("Iniciar Juego");
        lblTiempoTotal = new Label("Tiempo Total: 00:00");
        lblTiempoTurno1 = new Label(" -> 00:00");
        lblTiempoTurno2 = new Label(" -> 00:00");
        // En el método crearUI(), mostrar el número de pares encontrados para cada jugador
        lblJugador1 = new Label("Jugador 1: 0 Pares");
        lblJugador2 = new Label("Jugador 2: 0 Pares");

        lblPuntos1 = new Label("Puntos: ");
        lblPuntos2 = new Label("Puntos: ");
        lblPuntosValor1 = new Label("0");
        lblPuntosValor2 = new Label("0");
        hbxTiempoTotal = new HBox(lblTiempoTotal);
        hbxTiempoJugador1 = new HBox(lblTiempoTurno1);
        hbxTiempoJugador2 = new HBox(lblTiempoTurno2);
        hbxPuntosJugador1 = new HBox(lblPuntos1, lblPuntosValor1);
        hbxPuntosJugador2 = new HBox(lblPuntos2, lblPuntosValor2);
        hbxTiempo = new HBox(lblPares, btnJugar, hbxTiempoTotal);
        parejasEncontradas = new boolean[3][10]; // Inicializa parejasEncontradas en el mismo lugar donde inicializas cartaVolteada
        txtNumPares = new TextField(); // Inicializa el TextField
        txtNumPares.setPromptText("Ingresa el número de pares"); // Establece el texto de sugerencia

        lblJugador1 = new Label("Jugador 1: 0");
        lblScore1 = new Label("0");
        hbxJugador1 = new HBox(lblJugador1, lblScore1, hbxTiempoJugador1);

        lblJugador2 = new Label("Jugador 2: 0");
        lblScore2 = new Label("0");
        hbxJugador2 = new HBox(lblJugador2, lblScore2, hbxTiempoJugador2);

        vbxJugadores = new VBox(hbxJugador1, hbxPuntosJugador1, hbxJugador2, hbxPuntosJugador2);
        gdpJuego = new GridPane();
        hbxJuego = new HBox(gdpJuego, vbxJugadores);

        vbxPrincipal = new VBox(hbxTiempo, hbxJuego);
        vbxPrincipal.getChildren().add(txtNumPares);
        escena = new Scene(vbxPrincipal, 700, 600);
        escena.getStylesheets().add(getClass().getResource("/Estilos/memorama.css").toString());
        // Inicializar el Timeline para el tiempo total de juego
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                timeSeconds++;
                // Actualizar el label del tiempo total
                lblTiempoTotal.setText(String.format("Tiempo Total: %02d:%02d", timeSeconds / 60, timeSeconds % 60));
                // Verificar si todas las cartas están volteadas
                if (todasLasCartasVolteadas()) {
                    timeline.stop();
                }
                // Controlar el tiempo de turno de cada jugador
                if (timeSeconds % 10 == 0 && cartasVolteadasJugadorActual < 2) {
                    cambiarTurno();
                }
            }
        }));

        // Inicializar el Timeline para el tiempo de volteo de las cartas
        timelineVolteo = new Timeline();
        timelineVolteo.setCycleCount(1); // Solo se ejecutará una vez
        timelineVolteo.getKeyFrames().add(new KeyFrame(Duration.seconds(tiempoTurno), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Cambiar al siguiente jugador cuando se agote el tiempo de volteo
                cambiarTurno();
            }
        }));

        // Iniciar el Timeline cuando se inicia el juego
        btnJugar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int numPares = Integer.parseInt(txtNumPares.getText()); // Obtiene el número de pares del TextField
                if (numPares >= 3 && numPares <= 15) { // Verifica si el número de pares está dentro del rango permitido
                    nombresImagenes = new ArrayList<>(); // Inicializa la lista de nombres de imágenes
                    crearGridPane(numPares); // Crea el GridPane con el número de pares especificado
                    timeline.playFromStart();
                    activarJugador(jugadorActual);
                    iniciarTemporizadorTurno();
                } else {
                    // Mensaje de error si el número de pares no está dentro del rango permitido
                    System.out.println("El número de pares debe estar entre 3 y 15.");
                }
            }
        });
    }

    private void crearGridPane(int numPares) {
        int numTotalCartas = numPares * 2;
        int numFilas = 3; // Número predeterminado de filas
        int numColumnas = (int) Math.ceil((double) numTotalCartas / 3); // Calcular el número de columnas

        // Calcular el ancho y el alto de la ventana
        int anchoVentana = numColumnas * 100 + (numColumnas - 1) * 10 + 20; // Ancho de la carta más un margen de 10px entre cada carta, con un margen adicional de 20px para la disposición
        int altoVentana = numFilas * 150 + (numFilas - 1) * 10 + 100; // Alto de la carta más un margen de 10px entre cada carta, con un margen adicional de 100px para la disposición

        // Establecer el tamaño de la ventana
        escena.getWindow().setWidth(anchoVentana);
        escena.getWindow().setHeight(altoVentana);
        gdpJuego.getColumnConstraints().clear(); // Limpiar las restricciones de columnas existentes
        gdpJuego.getRowConstraints().clear(); // Limpiar las restricciones de filas existentes
        gdpJuego.getChildren().clear(); // Limpiar los nodos existentes en el GridPane

        for (int i = 0; i < numColumnas; i++) {
            ColumnConstraints columnConstraints = new ColumnConstraints(); // Crear nuevas restricciones de columna
            columnConstraints.setPercentWidth(100.0 / numColumnas); // Establecer el ancho porcentual de la columna
            gdpJuego.getColumnConstraints().add(columnConstraints); // Agregar las restricciones de columna al GridPane
        }

        for (int i = 0; i < numFilas; i++) {
            RowConstraints rowConstraints = new RowConstraints(); // Crear nuevas restricciones de fila
            rowConstraints.setPercentHeight(100.0 / numFilas); // Establecer el alto porcentual de la fila
            gdpJuego.getRowConstraints().add(rowConstraints); // Agregar las restricciones de fila al GridPane
        }

        nombresImagenes.clear(); // Limpiar la lista de nombres de imágenes antes de añadir nuevos nombres

        for (int i = 1; i <= numPares; i++) { // Generar solo el número especificado de pares de cartas
            nombresImagenes.add(i + ".png");
            nombresImagenes.add(i + ".png");
        }
        Collections.shuffle(nombresImagenes);

        arBtnCartas = new Button[numFilas][numColumnas]; // Matriz de botones para las cartas
        cartaVolteada = new boolean[numFilas][numColumnas]; // Matriz de estado volteado para las cartas

        // Restablecer el índice de la carta actual
        int cartaIndex = 0;

        for (int i = 0; i < numFilas; i++) {
            for (int j = 0; j < numColumnas; j++) {
                if (cartaIndex < numTotalCartas) { // Agregar una condición para evitar un índice fuera de rango
                    String nombreImagen = nombresImagenes.get(cartaIndex);
                    Button btnCarta = new Button();
                    ImageView imvCarta = new ImageView(
                            getClass().getResource("/images/0.png").toString()
                    );
                    imvCarta.setFitHeight(150);
                    imvCarta.setFitWidth(100);
                    btnCarta.setGraphic(imvCarta);
                    btnCarta.setPrefSize(100, 150);
                    arBtnCartas[i][j] = btnCarta;
                    gdpJuego.add(btnCarta, j, i);

                    final int fila = i;
                    final int columna = j;
                    btnCarta.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            if (!cartaVolteada[fila][columna]) { // Si la carta no está volteada
                                // Cambiar la imagen de "0.png" a la siguiente imagen revelada
                                ImageView nuevaVista = new ImageView(getClass().getResource("/images/" + nombreImagen).toString());
                                nuevaVista.setFitHeight(150);
                                nuevaVista.setFitWidth(100);
                                btnCarta.setGraphic(nuevaVista);
                                cartaVolteada[fila][columna] = true; // Marcar la carta como volteada

                                // Incrementar el recuento de cartas volteadas por el jugador actual
                                cartasVolteadasJugadorActual++;

                                // Verificar si se han volteado dos cartas
                                if (cartasVolteadasJugadorActual == 2) {
                                    registrarIntento(); // Registrar el intento del jugador
                                    comprobarPares(); // Comprobar si se ha formado un par
                                    cartasVolteadasJugadorActual = 0; // Reiniciar el recuento de cartas volteadas por el jugador actual
                                }
                            }
                        }
                    });

                    // Incrementar el índice de la carta actual
                    cartaIndex++;
                }
            }
        }
    }

    // YA SE LOGRARON BLOQUEAR LAS CARTAS QUE SE ECUENTRAN COMO PAR Y LAS IMPAR SI SE VOLTEAN
    //PERO AHI VECES QUE SE SALTA EL TURNO DEL JUGADOR Y NO REGISTRA EL PAR QUE ENCONTRO ES DECIR VOLTEA DOS CARTAS EN SU TURNO
    //ESTAS SON IGUALES PERO NO SE REGISTRA Y AHOTOMATICAMENTE PASA AL SIGUIENTE GUGADOR ES DICIR EL JUEGO NO ESTA SIENDO JUSTO PARA
    //LOS JUGADORES.

    // Método para comprobar si se han formado pares
    private void comprobarPares() {
        // Obtener las posiciones de las cartas seleccionadas por el jugador actual
        int[] cartasSeleccionadas = obtenerCartasSeleccionadas();
        int carta1 = cartasSeleccionadas[0];
        int carta2 = cartasSeleccionadas[1];

        // Obtener los nombres de las imágenes de las cartas seleccionadas
        String imagenCarta1 = nombresImagenes.get(carta1);
        String imagenCarta2 = nombresImagenes.get(carta2);

        if (imagenCarta1.equals(imagenCarta2)) {
            // Si las imágenes son iguales, se forma un par
            // Marcar las cartas como ya encontradas
            parejasEncontradas[carta1 / arBtnCartas[0].length][carta1 % arBtnCartas[0].length] = true;
            parejasEncontradas[carta2 / arBtnCartas[0].length][carta2 % arBtnCartas[0].length] = true;

            // Voltear las cartas que forman la pareja
            voltearParejaCartas(carta1, carta2);

            // Desactivar los botones correspondientes para que no se puedan voltear nuevamente
            arBtnCartas[carta1 / arBtnCartas[0].length][carta1 % arBtnCartas[0].length].setDisable(true);
            arBtnCartas[carta2 / arBtnCartas[0].length][carta2 % arBtnCartas[0].length].setDisable(true);

            if (jugadorActual == 1) {
                paresEncontradosJugador1++; // Incrementar el contador de pares del jugador 1
                lblJugador1.setText("Jugador 1: " + paresEncontradosJugador1 + " Pares"); // Actualizar el label del jugador 1
                lblPuntosValor1.setText(Integer.toString(paresEncontradosJugador1)); // Actualizar los puntos del jugador 1
                int puntos = Integer.parseInt(lblScore1.getText()) + 1; // Incrementar los puntos del jugador 1
                lblScore1.setText(Integer.toString(puntos)); // Actualizar el label de puntos del jugador 1
            } else {
                paresEncontradosJugador2++; // Incrementar el contador de pares del jugador 2
                lblJugador2.setText("Jugador 2: " + paresEncontradosJugador2 + " Pares"); // Actualizar el label del jugador 2
                lblPuntosValor2.setText(Integer.toString(paresEncontradosJugador2)); // Actualizar los puntos del jugador 2
                int puntos = Integer.parseInt(lblScore2.getText()) + 1; // Incrementar los puntos del jugador 2
                lblScore2.setText(Integer.toString(puntos)); // Actualizar el label de puntos del jugador 2
            }

            // Verificar si todas las parejas han sido encontradas
            if (todasLasParejasEncontradas()) {
                // Detener el temporizador total del juego
                timeline.stop();
                // Mostrar un mensaje de fin de juego
                System.out.println("¡Fin del juego!");
            } else {
                // Cambiar al siguiente jugador si no se ha completado el juego
                cambiarTurno();
                // Iniciar el temporizador del nuevo turno
                iniciarTemporizadorTurno();
            }
        } else {
            // Si las imágenes no son iguales, voltear las cartas nuevamente después de un breve tiempo
            voltearCartasNoEncontradas();
        }
    }


    // Método para verificar si todas las parejas han sido encontradas
    private boolean todasLasParejasEncontradas() {
        for (boolean[] fila : parejasEncontradas) {
            for (boolean parejaEncontrada : fila) {
                if (!parejaEncontrada) {
                    return false; // Devuelve falso si hay al menos una pareja que no ha sido encontrada
                }
            }
        }
        return true; // Devuelve verdadero si todas las parejas han sido encontradas
    }

    // Método para voltear las cartas que forman una pareja
    private void voltearParejaCartas(int carta1, int carta2) {
        int fila1 = carta1 / arBtnCartas[0].length;
        int columna1 = carta1 % arBtnCartas[0].length;
        int fila2 = carta2 / arBtnCartas[0].length;
        int columna2 = carta2 % arBtnCartas[0].length;

        // Voltear solo las cartas que forman parte del par encontrado
        ImageView imageView1 = new ImageView(getClass().getResource("/images/" + nombresImagenes.get(carta1)).toString());
        imageView1.setFitWidth(100);
        imageView1.setFitHeight(150);
        arBtnCartas[fila1][columna1].setGraphic(imageView1);

        ImageView imageView2 = new ImageView(getClass().getResource("/images/" + nombresImagenes.get(carta2)).toString());
        imageView2.setFitWidth(100);
        imageView2.setFitHeight(150);
        arBtnCartas[fila2][columna2].setGraphic(imageView2);
    }


    // Método para voltear las cartas no encontradas
    private void voltearCartasNoEncontradas() {
        // Crear un nuevo Timeline para voltear las cartas no encontradas después de un breve tiempo
        Timeline voltearCartasTimeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
            // Voltear solo las cartas que no forman parte del par encontrado
            for (int i = 0; i < arBtnCartas.length; i++) {
                for (int j = 0; j < arBtnCartas[i].length; j++) {
                    if (!parejasVolteadas[i * arBtnCartas[i].length + j] && cartaVolteada[i][j]) {
                        cartaVolteada[i][j] = false;
                        ImageView imageView = new ImageView(getClass().getResource("/images/0.png").toString());
                        imageView.setFitWidth(100);
                        imageView.setFitHeight(150);
                        arBtnCartas[i][j].setGraphic(imageView);
                    }
                }
            }

            // Cambiar al siguiente jugador y activar su turno
            cambiarTurno();
            activarJugador(jugadorActual);
            iniciarTemporizadorTurno();
        }));
        voltearCartasTimeline.setCycleCount(1); // Establecer el número de ciclos del Timeline
        voltearCartasTimeline.play(); // Iniciar el Timeline
    }

    // Método para obtener las posiciones de las cartas seleccionadas por el jugador actual
    private int[] obtenerCartasSeleccionadas() {
        int carta1 = -1;
        int carta2 = -1;

        // Buscar las cartas seleccionadas por el jugador actual
        for (int i = 0; i < arBtnCartas.length; i++) {
            for (int j = 0; j < arBtnCartas[i].length; j++) {
                if (cartaVolteada[i][j]) {
                    if (carta1 == -1) {
                        carta1 = i * arBtnCartas[i].length + j;
                    } else {
                        carta2 = i * arBtnCartas[i].length + j;
                    }
                }
            }
        }

        return new int[]{carta1, carta2}; // Devolver las posiciones de las cartas seleccionadas
    }

    // Método para iniciar el temporizador del turno actual
    private void iniciarTemporizadorTurno() {
        if (jugadorActual == 1) {
            iniciarTemporizador(lblTiempoTurno1); // Iniciar temporizador para el jugador 1
        } else {
            iniciarTemporizador(lblTiempoTurno2); // Iniciar temporizador para el jugador 2
        }
    }

    // Método para iniciar el temporizador de un turno
    private void iniciarTemporizador(Label lblTiempoTurno) {
        tiempoTurno = 10; // Duración predeterminada del temporizador
        timelineVolteo.playFromStart(); // Iniciar el Timeline para el tiempo de volteo de las cartas

        timelineVolteo.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Mostrar el tiempo restante del turno como "00:00"
                lblTiempoTurno.setText("00:00");
            }
        });

        // Ajustar la duración del KeyFrame para que sea de 10 segundos
        timelineVolteo.getKeyFrames().setAll(new KeyFrame(Duration.seconds(tiempoTurno), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Cambiar al siguiente jugador cuando se agote el tiempo de volteo
                cambiarTurno();
            }
        }));

        // Actualizar el tiempo restante del turno en el Label
        timelineVolteo.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
            int segundosRestantes = (int) (tiempoTurno - newTime.toSeconds());
            lblTiempoTurno.setText(String.format("%02d:%02d", segundosRestantes / 60, segundosRestantes % 60));
        });

        // Agregar espacios adicionales entre el temporizador y el puntaje del jugador
        lblTiempoTurno.setPadding(new Insets(0, 10, 0, 10)); // Ajusta el valor según sea necesario
    }

    // Método para verificar si todas las cartas están volteadas
    private boolean todasLasCartasVolteadas() {
        for (int i = 0; i < cartaVolteada.length; i++) {
            for (int j = 0; j < cartaVolteada[i].length; j++) {
                if (!cartaVolteada[i][j]) {
                    return false; // Devuelve falso si hay al menos una carta sin voltear
                }
            }
        }
        return true; // Devuelve verdadero si todas las cartas están volteadas
    }

    // Método para registrar el intento del jugador actual
    private void registrarIntento() {
        if (jugadorActual == 1) {
            intentosJugador1++; // Incrementa los intentos del jugador 1
        } else {
            intentosJugador2++; // Incrementa los intentos del jugador 2
        }
    }
    // Método para cambiar al siguiente jugador
    private void cambiarTurno() {
        if (jugadorActual == 1) {
            jugadorActual = 2; // Cambia al jugador 2
        } else {
            jugadorActual = 1; // Cambia al jugador 1
        }
        detenerTemporizador(); // Detiene el temporizador del turno actual
        activarJugador(jugadorActual); // Activa el turno del nuevo jugador
        iniciarTemporizadorTurno(); // Inicia el temporizador del nuevo turno

        // Si es el primer turno del jugador 2, realiza las mismas acciones que el primer turno del jugador 1
        if (jugadorActual == 2 && primerTurnoJugador2) {
            // Reiniciar el tiempo de turno del jugador 2
            iniciarTemporizador(lblTiempoTurno2);
            primerTurnoJugador2 = false; // Marcar que ya no es el primer turno del jugador 2
            // Activar el temporizador de volteo de cartas para el jugador 2
            timelineVolteo.playFromStart();
        }
    }


    // Método para detener el temporizador del turno actual
    private void detenerTemporizador() {
        timelineVolteo.stop(); // Detiene el Timeline del tiempo de volteo de las cartas
    }

    // Método para activar el jugador actual
    private void activarJugador(int jugador) {
        if (jugador == 1) {
            lblJugador1.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
            lblJugador2.setStyle("-fx-text-fill: red;");
        } else {
            lblJugador2.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
            lblJugador1.setStyle("-fx-text-fill: red;");
        }
    }

    public static void main(String[] args) {
        new memorama2();
    }
}
/*package com.example.demo.vistas;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class memorama extends Stage {

    private Scene escena;
    private Label lblPares, lblJugador1, lblJugador2, lblTiempoTotal, lblTiempoTurno1, lblTiempoTurno2, lblScore1, lblScore2, lblPuntos1, lblPuntos2, lblPuntosValor1, lblPuntosValor2;
    private GridPane gdpJuego;
    private Button btnJugar;
    private VBox vbxPrincipal, vbxJugadores;
    private HBox hbxTiempo, hbxJuego, hbxJugador1, hbxJugador2, hbxTiempoTotal, hbxTiempoJugador1, hbxTiempoJugador2, hbxPuntosJugador1, hbxPuntosJugador2;
    private Button[][] arBtnCartas;
    private Timeline timeline, timelineVolteo;
    private Integer timeSeconds = 0, tiempoTurno = 5;
    private int jugadorActual = 1;
    private int intentosJugador1 = 0;
    private int intentosJugador2 = 0;
    private int cartasVolteadasJugadorActual = 0;
    private int carta1Jugador1 = -1;
    private int carta2Jugador1 = -1;
    private int carta1Jugador2 = -1;
    private int carta2Jugador2 = -1;
    private String imagenCarta1Jugador1;
    private String imagenCarta2Jugador1;
    private String imagenCarta1Jugador2;
    private String imagenCarta2Jugador2;
    private boolean[][] cartaVolteada;
    private boolean[] parejasVolteadas = new boolean[16]; // Para asegurarnos de que cada par se voltee solo una vez
    private boolean[][] parejasEncontradas; // Nueva variable para rastrear las parejas encontradas
    private TextField txtNumPares; // Nuevo TextField para el número de pares

    public memorama() {
        crearUI();
        this.setTitle("Memorama :)");
        this.setScene(escena);
        this.show();
    }

    private void crearUI() {
        lblPares = new Label(":");
        btnJugar = new Button("Iniciar Juego");
        lblTiempoTotal = new Label("Tiempo Total: 00:00");
        lblTiempoTurno1 = new Label(" -> 00:00");
        lblTiempoTurno2 = new Label(" -> 00:00");
        lblPuntos1 = new Label("Puntos: ");
        lblPuntos2 = new Label("Puntos: ");
        lblPuntosValor1 = new Label("0");
        lblPuntosValor2 = new Label("0");
        hbxTiempoTotal = new HBox(lblTiempoTotal);
        hbxTiempoJugador1 = new HBox(lblTiempoTurno1);
        hbxTiempoJugador2 = new HBox(lblTiempoTurno2);
        hbxPuntosJugador1 = new HBox(lblPuntos1, lblPuntosValor1);
        hbxPuntosJugador2 = new HBox(lblPuntos2, lblPuntosValor2);
        hbxTiempo = new HBox(lblPares, btnJugar, hbxTiempoTotal);
        parejasEncontradas = new boolean[3][10]; // Inicializa parejasEncontradas en el mismo lugar donde inicializas cartaVolteada
        txtNumPares = new TextField(); // Inicializa el TextField
        txtNumPares.setPromptText("Ingresa el número de pares"); // Establece el texto de sugerencia

        lblJugador1 = new Label("Jugador 1: 0");
        lblScore1 = new Label("0");
        hbxJugador1 = new HBox(lblJugador1, lblScore1, hbxTiempoJugador1);

        lblJugador2 = new Label("Jugador 2: 0");
        lblScore2 = new Label("0");
        hbxJugador2 = new HBox(lblJugador2, lblScore2, hbxTiempoJugador2);

        vbxJugadores = new VBox(hbxJugador1, hbxPuntosJugador1, hbxJugador2, hbxPuntosJugador2);
        gdpJuego = new GridPane();
        hbxJuego = new HBox(gdpJuego, vbxJugadores);

        vbxPrincipal = new VBox(hbxTiempo, hbxJuego);
        vbxPrincipal.getChildren().add(txtNumPares);
        escena = new Scene(vbxPrincipal, 700, 600);
        escena.getStylesheets().add(getClass().getResource("/Estilos/memorama.css").toString());
        // Inicializar el Timeline para el tiempo total de juego
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                timeSeconds++;
                // Actualizar el label del tiempo total
                lblTiempoTotal.setText(String.format("Tiempo Total: %02d:%02d", timeSeconds / 60, timeSeconds % 60));
                // Verificar si todas las cartas están volteadas
                if (todasLasCartasVolteadas()) {
                    timeline.stop();
                }
                // Controlar el tiempo de turno de cada jugador
                if (timeSeconds % 10 == 0 && cartasVolteadasJugadorActual < 2) {
                    cambiarTurno();
                }
            }
        }));

        // Inicializar el Timeline para el tiempo de volteo de las cartas
        timelineVolteo = new Timeline();
        timelineVolteo.setCycleCount(1); // Solo se ejecutará una vez
        timelineVolteo.getKeyFrames().add(new KeyFrame(Duration.seconds(tiempoTurno), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Cambiar al siguiente jugador cuando se agote el tiempo de volteo
                cambiarTurno();
            }
        }));

        // Iniciar el Timeline cuando se inicia el juego
        btnJugar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int numPares = Integer.parseInt(txtNumPares.getText()); // Obtiene el número de pares del TextField
                if (numPares >= 3 && numPares <= 15) { // Verifica si el número de pares está dentro del rango permitido
                    crearGridPane(numPares); // Crea el GridPane con el número de pares especificado
                    timeline.playFromStart();
                    activarJugador(jugadorActual);
                    iniciarTemporizadorTurno();
                } else {
                    // Mensaje de error si el número de pares no está dentro del rango permitido
                    System.out.println("El número de pares debe estar entre 3 y 15.");
                }
            }
        });
    }

    private void crearGridPane(int numPares) {
        int numTotalCartas = numPares * 2;
        int numFilas = 3; // Número predeterminado de filas
        int numColumnas = (int) Math.ceil((double) numTotalCartas / 3); // Calcular el número de columnas

        // Calcular el ancho y el alto de la ventana
        int anchoVentana = numColumnas * 100 + (numColumnas - 1) * 10 + 20; // Ancho de la carta más un margen de 10px entre cada carta, con un margen adicional de 20px para la disposición
        int altoVentana = numFilas * 150 + (numFilas - 1) * 10 + 100; // Alto de la carta más un margen de 10px entre cada carta, con un margen adicional de 100px para la disposición

        // Establecer el tamaño de la ventana
        escena.getWindow().setWidth(anchoVentana);
        escena.getWindow().setHeight(altoVentana);
        gdpJuego.getColumnConstraints().clear(); // Limpiar las restricciones de columnas existentes
        gdpJuego.getRowConstraints().clear(); // Limpiar las restricciones de filas existentes
        gdpJuego.getChildren().clear(); // Limpiar los nodos existentes en el GridPane

        for (int i = 0; i < numColumnas; i++) {
            ColumnConstraints columnConstraints = new ColumnConstraints(); // Crear nuevas restricciones de columna
            columnConstraints.setPercentWidth(100.0 / numColumnas); // Establecer el ancho porcentual de la columna
            gdpJuego.getColumnConstraints().add(columnConstraints); // Agregar las restricciones de columna al GridPane
        }

        for (int i = 0; i < numFilas; i++) {
            RowConstraints rowConstraints = new RowConstraints(); // Crear nuevas restricciones de fila
            rowConstraints.setPercentHeight(100.0 / numFilas); // Establecer el alto porcentual de la fila
            gdpJuego.getRowConstraints().add(rowConstraints); // Agregar las restricciones de fila al GridPane
        }

        List<String> nombresImagenes = new ArrayList<>();
        for (int i = 1; i <= numPares; i++) { // Generar solo el número especificado de pares de cartas
            nombresImagenes.add(i + ".png");
            nombresImagenes.add(i + ".png");
        }
        Collections.shuffle(nombresImagenes);

        arBtnCartas = new Button[numFilas][numColumnas]; // Matriz de botones para las cartas
        cartaVolteada = new boolean[numFilas][numColumnas]; // Matriz de estado volteado para las cartas

        // Restablecer el índice de la carta actual
        int cartaIndex = 0;

        for (int i = 0; i < numFilas; i++) {
            for (int j = 0; j < numColumnas; j++) {
                if (cartaIndex < numTotalCartas) { // Agregar una condición para evitar un índice fuera de rango
                    String nombreImagen = nombresImagenes.get(cartaIndex);
                    Button btnCarta = new Button();
                    ImageView imvCarta = new ImageView(
                            getClass().getResource("/images/0.png").toString()
                    );
                    imvCarta.setFitHeight(150);
                    imvCarta.setFitWidth(100);
                    btnCarta.setGraphic(imvCarta);
                    btnCarta.setPrefSize(100, 150);
                    arBtnCartas[i][j] = btnCarta;
                    gdpJuego.add(btnCarta, j, i);

                    final int fila = i;
                    final int columna = j;
                    btnCarta.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            if (!cartaVolteada[fila][columna]) { // Si la carta no está volteada
                                // Cambiar la imagen de "0.png" a la siguiente imagen revelada
                                ImageView nuevaVista = new ImageView(getClass().getResource("/images/" + nombreImagen).toString());
                                nuevaVista.setFitHeight(150);
                                nuevaVista.setFitWidth(100);
                                btnCarta.setGraphic(nuevaVista);
                                cartaVolteada[fila][columna] = true; // Marcar la carta como volteada

                                // Incrementar el recuento de cartas volteadas por el jugador actual
                                cartasVolteadasJugadorActual++;

                                // Verificar si se han volteado dos cartas
                                if (cartasVolteadasJugadorActual == 2) {
                                    registrarIntento(); // Registrar el intento del jugador
                                    comprobarPares(); // Comprobar si se ha formado un par
                                    cartasVolteadasJugadorActual = 0; // Reiniciar el recuento de cartas volteadas por el jugador actual
                                }
                            }
                        }
                    });

                    // Incrementar el índice de la carta actual
                    cartaIndex++;
                }
            }
        }
    }

    private void comprobarPares() {
        if (jugadorActual == 1) {
            if (carta1Jugador1 != -1 && carta2Jugador1 != -1) {
                if (imagenCarta1Jugador1.equals(imagenCarta2Jugador1)) {
                    // Si las imágenes son iguales, se aumenta el puntaje del jugador 1 y se actualiza el label
                    int puntos = Integer.parseInt(lblScore1.getText()) + 1;
                    lblScore1.setText(Integer.toString(puntos));
                } else {
                    // Si las imágenes no son iguales, se voltea de nuevo las cartas y se actualiza el puntaje
                    cartaVolteada[carta1Jugador1 / 10][carta1Jugador1 % 10] = false;
                    cartaVolteada[carta2Jugador1 / 10][carta2Jugador1 % 10] = false;
                    arBtnCartas[carta1Jugador1 / 10][carta1Jugador1 % 10].setGraphic(new ImageView(getClass().getResource("/images/0.png").toString()));
                    arBtnCartas[carta2Jugador1 / 10][carta2Jugador1 % 10].setGraphic(new ImageView(getClass().getResource("/images/0.png").toString()));
                }
            }
        } else {
            if (carta1Jugador2 != -1 && carta2Jugador2 != -1) {
                if (imagenCarta1Jugador2.equals(imagenCarta2Jugador2)) {
                    // Si las imágenes son iguales, se aumenta el puntaje del jugador 2 y se actualiza el label
                    int puntos = Integer.parseInt(lblScore2.getText()) + 1;
                    lblScore2.setText(Integer.toString(puntos));
                } else {
                    // Si las imágenes no son iguales, se voltea de nuevo las cartas y se actualiza el puntaje
                    cartaVolteada[carta1Jugador2 / 10][carta1Jugador2 % 10] = false;
                    cartaVolteada[carta2Jugador2 / 10][carta2Jugador2 % 10] = false;
                    arBtnCartas[carta1Jugador2 / 10][carta1Jugador2 % 10].setGraphic(new ImageView(getClass().getResource("/images/0.png").toString()));
                    arBtnCartas[carta2Jugador2 / 10][carta2Jugador2 % 10].setGraphic(new ImageView(getClass().getResource("/images/0.png").toString()));
                }
            }
        }
        // Llamar al método para voltear las cartas no encontradas
        voltearCartasNoEncontradas();
    }

    private void voltearCartasNoEncontradas() {
            // Crear un nuevo Timeline para voltear las cartas no encontradas después de un breve tiempo
            Timeline voltearCartasTimeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
                // Voltear todas las cartas
                for (int i = 0; i < arBtnCartas.length; i++) {
                    for (int j = 0; j < arBtnCartas[i].length; j++) {
                        if (cartaVolteada[i][j]) {
                            cartaVolteada[i][j] = false;
                            ImageView imageView = new ImageView(getClass().getResource("/images/0.png").toString());
                            imageView.setFitWidth(100);
                            imageView.setFitHeight(150);
                            arBtnCartas[i][j].setGraphic(imageView);
                        }
                    }
                }

            // Marcar las parejas encontradas
            for (int i = 0; i < parejasVolteadas.length; i++) {
                if (parejasVolteadas[i]) {
                    cartaVolteada[i / 10][i % 10] = true;
                }
            }

            // Cambiar al siguiente jugador y activar su turno
            cambiarTurno();
            activarJugador(jugadorActual);
            iniciarTemporizadorTurno();
            }));
            voltearCartasTimeline.setCycleCount(1);
            voltearCartasTimeline.play();
        }


    private void iniciarTemporizadorTurno() {
        if (jugadorActual == 1) {
            iniciarTemporizador(lblTiempoTurno1);
        } else {
            iniciarTemporizador(lblTiempoTurno2);
        }
    }

    private void iniciarTemporizador(Label lblTiempoTurno) {
        tiempoTurno = 10; // Camb
        timelineVolteo.playFromStart();
        timelineVolteo.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Mostrar el tiempo restante del turno como "00:00"
                lblTiempoTurno.setText("00:00");
            }
        });

        // Ajusta la duración del KeyFrame para que sea de 10 segundos
        timelineVolteo.getKeyFrames().setAll(new KeyFrame(Duration.seconds(tiempoTurno), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Cambiar al siguiente jugador cuando se agote el tiempo de volteo
                cambiarTurno();
            }
        }));

        // Actualizar el tiempo restante del turno en el Label
        timelineVolteo.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
            int segundosRestantes = (int) (tiempoTurno - newTime.toSeconds());
            lblTiempoTurno.setText(String.format("%02d:%02d", segundosRestantes / 60, segundosRestantes % 60));
        });

        // Agregar espacios adicionales entre el temporizador y el puntaje del jugador
        lblTiempoTurno.setPadding(new Insets(0, 10, 0, 10)); // Ajusta el valor según sea necesario
    }

    private boolean todasLasCartasVolteadas() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 10; j++) {
                if (!cartaVolteada[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // Método para cambiar el turno de los jugadores
    private void cambiarTurno() {
        jugadorActual = (jugadorActual == 1) ? 2 : 1;
        activarJugador(jugadorActual);
        iniciarTemporizadorTurno();
        cartasVolteadasJugadorActual = 0; // Reiniciar el contador de cartas volteadas para el nuevo jugador
        // Reiniciar índices y nombres de imágenes de las cartas volteadas en el turno
        carta1Jugador1 = -1;
        carta2Jugador1 = -1;
        carta1Jugador2 = -1;
        carta2Jugador2 = -1;
        imagenCarta1Jugador1 = null;
        imagenCarta2Jugador1 = null;
        imagenCarta1Jugador2 = null;
        imagenCarta2Jugador2 = null;
    }

    // Método para registrar el intento de un jugador
    private void registrarIntento() {
        if (jugadorActual == 1) {
            intentosJugador1++;
            lblJugador1.setText("Jugador 1: " + intentosJugador1);
        } else {
            intentosJugador2++;
            lblJugador2.setText("Jugador 2: " + intentosJugador2);
        }
    }

    // Método para activar el jugador actual
    private void activarJugador(int jugador) {
        if (jugador == 1) {
            lblJugador1.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
            lblJugador2.setStyle("-fx-text-fill: red;");
        } else {
            lblJugador2.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
            lblJugador1.setStyle("-fx-text-fill: red;");
        }
    }

}*/
/*
//pruebas
package com.example.demo.vistas;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class memorama extends Stage {

    private Scene escena;
    private Label lblPares, lblJugador1, lblJugador2, lblTiempoTotal, lblTiempoTurno1, lblTiempoTurno2, lblScore1, lblScore2, lblPuntos1, lblPuntos2, lblPuntosValor1, lblPuntosValor2;
    private GridPane gdpJuego;
    private Button btnJugar;
    private VBox vbxPrincipal, vbxJugadores;
    private HBox hbxTiempo, hbxJuego, hbxJugador1, hbxJugador2, hbxTiempoTotal, hbxTiempoJugador1, hbxTiempoJugador2, hbxPuntosJugador1, hbxPuntosJugador2;
    private Button[][] arBtnCartas;
    private Timeline timeline, timelineVolteo;
    private Integer timeSeconds = 0, tiempoTurno = 5;
    private int jugadorActual = 1;
    private int intentosJugador1 = 0;
    private int intentosJugador2 = 0;
    private int cartasVolteadasJugadorActual = 0;
    private int carta1Jugador1 = -1;
    private int carta2Jugador1 = -1;
    private int carta1Jugador2 = -1;
    private int carta2Jugador2 = -1;
    private String imagenCarta1Jugador1;
    private String imagenCarta2Jugador1;
    private String imagenCarta1Jugador2;
    private String imagenCarta2Jugador2;
    private boolean[][] cartaVolteada;
    private boolean[] parejasVolteadas = new boolean[16]; // Para asegurarnos de que cada par se voltee solo una vez
    private boolean[][] parejasEncontradas; // Nueva variable para rastrear las parejas encontradas
    private TextField txtNumPares; // Nuevo TextField para el número de pares
    public memorama() {
        crearUI();
        this.setTitle("Memorama :)");
        this.setScene(escena);
        this.show();
    }

    private void crearUI() {
        lblPares = new Label("Número de Pares:");
        btnJugar = new Button("Iniciar Juego");
        lblTiempoTotal = new Label("Tiempo Total: 00:00");
        lblTiempoTurno1 = new Label(" -> 00:00");
        lblTiempoTurno2 = new Label(" -> 00:00");
        lblPuntos1 = new Label("Puntos: ");
        lblPuntos2 = new Label("Puntos: ");
        lblPuntosValor1 = new Label("0");
        lblPuntosValor2 = new Label("0");
        hbxTiempoTotal = new HBox(lblTiempoTotal);
        hbxTiempoJugador1 = new HBox(lblTiempoTurno1);
        hbxTiempoJugador2 = new HBox(lblTiempoTurno2);
        hbxPuntosJugador1 = new HBox(lblPuntos1, lblPuntosValor1);
        hbxPuntosJugador2 = new HBox(lblPuntos2, lblPuntosValor2);
        hbxTiempo = new HBox(lblPares, btnJugar, hbxTiempoTotal);
        parejasEncontradas = new boolean[3][10]; // Inicializa parejasEncontradas en el mismo lugar donde inicializas cartaVolteada
        txtNumPares = new TextField(); // Inicializa el TextField
        txtNumPares.setPromptText("Ingresa el número de pares"); // Establece el texto de sugerencia

        lblJugador1 = new Label("Jugador 1: 0");
        lblScore1 = new Label("0");
        hbxJugador1 = new HBox(lblJugador1, lblScore1, hbxTiempoJugador1);

        lblJugador2 = new Label("Jugador 2: 0");
        lblScore2 = new Label("0");
        hbxJugador2 = new HBox(lblJugador2, lblScore2, hbxTiempoJugador2);

        vbxJugadores = new VBox(hbxJugador1, hbxPuntosJugador1, hbxJugador2, hbxPuntosJugador2);
        gdpJuego = new GridPane();
        revolverCartas();
        hbxJuego = new HBox(gdpJuego, vbxJugadores);

        vbxPrincipal = new VBox(hbxTiempo, hbxJuego);
        vbxPrincipal.getChildren().add(txtNumPares);
        escena = new Scene(vbxPrincipal, 1400, 600);

        // Inicializar el Timeline para el tiempo total de juego
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                timeSeconds++;
                // Actualizar el label del tiempo total
                lblTiempoTotal.setText(String.format("Tiempo Total: %02d:%02d", timeSeconds / 60, timeSeconds % 60));
                // Verificar si todas las cartas están volteadas
                if (todasLasCartasVolteadas()) {
                    timeline.stop();
                }
                // Controlar el tiempo de turno de cada jugador
                if (timeSeconds % 10 == 0 && cartasVolteadasJugadorActual < 2) {
                    cambiarTurno();
                }
            }
        }));

        // Inicializar el Timeline para el tiempo de volteo de las cartas
        timelineVolteo = new Timeline();
        timelineVolteo.setCycleCount(1); // Solo se ejecutará una vez
        timelineVolteo.getKeyFrames().add(new KeyFrame(Duration.seconds(tiempoTurno), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Cambiar al siguiente jugador cuando se agote el tiempo de volteo
                cambiarTurno();
            }
        }));

        // Iniciar el Timeline cuando se inicia el juego
        btnJugar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int numPares = Integer.parseInt(txtNumPares.getText()); // Obtiene el número de pares del TextField
                timeline.playFromStart();
                activarJugador(jugadorActual);
                iniciarTemporizadorTurno();
            }
        });
    }

    private void revolverCartas() {
        List<String> nombresImagenes = new ArrayList<>();
        for (int i = 1; i <= 15; i++) {
            nombresImagenes.add(i + ".png");
            nombresImagenes.add(i + ".png");
        }
        Collections.shuffle(nombresImagenes);

        arBtnCartas = new Button[3][10];
        cartaVolteada = new boolean[3][10];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 10; j++) {
                int indiceImagen = i * 10 + j;
                String nombreImagen = nombresImagenes.get(indiceImagen);
                Button btnCarta = new Button();
                ImageView imvCarta = new ImageView(
                        getClass().getResource("/images/0.png").toString()
                );
                imvCarta.setFitHeight(150);
                imvCarta.setFitWidth(100);
                btnCarta.setGraphic(imvCarta);
                btnCarta.setPrefSize(100, 150);
                arBtnCartas[i][j] = btnCarta;
                gdpJuego.add(btnCarta, j, i);

                final int fila = i;
                final int columna = j;
                btnCarta.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if (!cartaVolteada[fila][columna] && !parejasEncontradas[fila][columna]) { // Agrega la verificación de parejasEncontradas
                            // Cambiar la imagen de "0.png" a la siguiente imagen revelada
                            ImageView nuevaVista = new ImageView(getClass().getResource("/images/" + nombreImagen).toString());
                            nuevaVista.setFitHeight(150);
                            nuevaVista.setFitWidth(100);
                            btnCarta.setGraphic(nuevaVista);
                            cartaVolteada[fila][columna] = true; // Marcar la carta como volteada
                            cartasVolteadasJugadorActual++;
                            // Verificar si se ha volteado un par
                            if (cartasVolteadasJugadorActual == 2) {
                                registrarIntento();
                                comprobarPares();
                                cartasVolteadasJugadorActual = 0; // Reiniciar el contador de cartas volteadas
                            }
                        }
                    }
                });
            }
        }
    }

    private void comprobarPares() {
        if (jugadorActual == 1) {
            if (carta1Jugador1 != -1 && carta2Jugador1 != -1) {
                if (imagenCarta1Jugador1.equals(imagenCarta2Jugador1)) {
                    parejasEncontradas[carta1Jugador1 / 10][carta1Jugador1 % 10] = true; // Marca las cartas en parejasEncontradas
                    parejasEncontradas[carta2Jugador1 / 10][carta2Jugador1 % 10] = true; // Marca las cartas en parejasEncontradas
                    noVoltearCartasPar(); // Llama al nuevo método aquí
                    // Si las imágenes son iguales, se aumenta el puntaje del jugador 1 y se actualiza el label
                    int puntos = Integer.parseInt(lblScore1.getText()) + 1;
                    lblScore1.setText(Integer.toString(puntos));
                } else {
                    // Si las imágenes no son iguales, se voltea de nuevo las cartas y se actualiza el puntaje
                    cartaVolteada[carta1Jugador1 / 10][carta1Jugador1 % 10] = false;
                    cartaVolteada[carta2Jugador1 / 10][carta2Jugador1 % 10] = false;
                    arBtnCartas[carta1Jugador1 / 10][carta1Jugador1 % 10].setGraphic(new ImageView(getClass().getResource("/images/0.png").toString()));
                    arBtnCartas[carta2Jugador1 / 10][carta2Jugador1 % 10].setGraphic(new ImageView(getClass().getResource("/images/0.png").toString()));
                }
            }
        } else {
            if (carta1Jugador2 != -1 && carta2Jugador2 != -1) {
                if (imagenCarta1Jugador2.equals(imagenCarta2Jugador2)) {
                    parejasEncontradas[carta1Jugador2 / 10][carta1Jugador2 % 10] = true; // Marca las cartas en parejasEncontradas
                    parejasEncontradas[carta2Jugador2 / 10][carta2Jugador2 % 10] = true; // Marca las cartas en parejasEncontradas
                    noVoltearCartasPar(); // Llama al nuevo método aquí
                    // Si las imágenes son iguales, se aumenta el puntaje del jugador 2 y se actualiza el label
                    int puntos = Integer.parseInt(lblScore2.getText()) + 1;
                    lblScore2.setText(Integer.toString(puntos));
                } else {
                    // Si las imágenes no son iguales, se voltea de nuevo las cartas y se actualiza el puntaje
                    cartaVolteada[carta1Jugador2 / 10][carta1Jugador2 % 10] = false;
                    cartaVolteada[carta2Jugador2 / 10][carta2Jugador2 % 10] = false;
                    arBtnCartas[carta1Jugador2 / 10][carta1Jugador2 % 10].setGraphic(new ImageView(getClass().getResource("/images/0.png").toString()));
                    arBtnCartas[carta2Jugador2 / 10][carta2Jugador2 % 10].setGraphic(new ImageView(getClass().getResource("/images/0.png").toString()));
                }
            }
        }
        // Llamar al método para voltear las cartas no encontradas
        voltearCartasNoEncontradas();
    }
    private void voltearCartasNoEncontradas() {
        // Crear un nuevo Timeline para voltear las cartas no encontradas después de un breve tiempo
        Timeline voltearCartasTimeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
            // Voltear todas las cartas que no forman una pareja
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 10; j++) {
                    if (cartaVolteada[i][j] && !parejasEncontradas[i][j]) { // Agrega la verificación de parejasEncontradas
                        cartaVolteada[i][j] = false;
                        ImageView imageView = new ImageView(getClass().getResource("/images/0.png").toString());
                        imageView.setFitWidth(100); // Ajusta el ancho deseado
                        imageView.setFitHeight(150); // Ajusta el alto deseado
                        arBtnCartas[i][j].setGraphic(imageView);
                    }
                }
            }

            // Cambiar al siguiente jugador y activar su turno
            cambiarTurno();
            activarJugador(jugadorActual);
            iniciarTemporizadorTurno();
        }));
        voltearCartasTimeline.setCycleCount(1); // Se ejecutará una vez
        voltearCartasTimeline.play();
    }
    private void noVoltearCartasPar() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 10; j++) {
                if (parejasEncontradas[i][j]) {
                    cartaVolteada[i][j] = true;
                    ImageView imageView = new ImageView(getClass().getResource("/images/" + arBtnCartas[i][j].getGraphic().toString()).toString());
                    imageView.setFitWidth(100); // Ajusta el ancho deseado
                    imageView.setFitHeight(150); // Ajusta el alto deseado
                    arBtnCartas[i][j].setGraphic(imageView);
                }
            }
        }
    }
    */
/////////////////////////
/*
    private void voltearCartasNoEncontradas() {
        // Crear un nuevo Timeline para voltear las cartas no encontradas después de un breve tiempo
        Timeline voltearCartasTimeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
            // Voltear todas las cartas
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 10; j++) {
                    if (cartaVolteada[i][j] && !parejasEncontradas[i][j]) { // Agrega la verificación de parejasEncontradas
                        cartaVolteada[i][j] = false;
                        ImageView imageView = new ImageView(getClass().getResource("/images/0.png").toString());
                        imageView.setFitWidth(100); // Ajusta el ancho deseado
                        imageView.setFitHeight(150); // Ajusta el alto deseado
                        arBtnCartas[i][j].setGraphic(imageView);
                    }
                }
            }

            // Marcar las parejas encontradas
            for (int i = 0; i < parejasVolteadas.length; i++) {
                if (parejasVolteadas[i]) {
                    cartaVolteada[i / 10][i % 10] = true;
                }
            }

            // Cambiar al siguiente jugador y activar su turno
            cambiarTurno();
            activarJugador(jugadorActual);
            iniciarTemporizadorTurno();
        }));
        voltearCartasTimeline.setCycleCount(1); // Se ejecutará una vez
        voltearCartasTimeline.play();
    }


    private void iniciarTemporizadorTurno() {
        if (jugadorActual == 1) {
            iniciarTemporizador(lblTiempoTurno1);
        } else {
            iniciarTemporizador(lblTiempoTurno2);
        }
    }

    private void iniciarTemporizador(Label lblTiempoTurno) {
        tiempoTurno = 10; // Camb
        timelineVolteo.playFromStart();
        timelineVolteo.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Mostrar el tiempo restante del turno como "00:00"
                lblTiempoTurno.setText("00:00");
            }
        });

        // Ajusta la duración del KeyFrame para que sea de 10 segundos
        timelineVolteo.getKeyFrames().setAll(new KeyFrame(Duration.seconds(tiempoTurno), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Cambiar al siguiente jugador cuando se agote el tiempo de volteo
                cambiarTurno();
            }
        }));

        // Actualizar el tiempo restante del turno en el Label
        timelineVolteo.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
            int segundosRestantes = (int) (tiempoTurno - newTime.toSeconds());
            lblTiempoTurno.setText(String.format("%02d:%02d", segundosRestantes / 60, segundosRestantes % 60));
        });

        // Agregar espacios adicionales entre el temporizador y el puntaje del jugador
        lblTiempoTurno.setPadding(new Insets(0, 10, 0, 10)); // Ajusta el valor según sea necesario
    }

    private boolean todasLasCartasVolteadas() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 10; j++) {
                if (!cartaVolteada[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // Método para cambiar el turno de los jugadores
    private void cambiarTurno() {
        jugadorActual = (jugadorActual == 1) ? 2 : 1;
        activarJugador(jugadorActual);
        iniciarTemporizadorTurno();
        cartasVolteadasJugadorActual = 0; // Reiniciar el contador de cartas volteadas para el nuevo jugador
        // Reiniciar índices y nombres de imágenes de las cartas volteadas en el turno
        carta1Jugador1 = -1;
        carta2Jugador1 = -1;
        carta1Jugador2 = -1;
        carta2Jugador2 = -1;
        imagenCarta1Jugador1 = null;
        imagenCarta2Jugador1 = null;
        imagenCarta1Jugador2 = null;
        imagenCarta2Jugador2 = null;
    }

    // Método para registrar el intento de un jugador
    private void registrarIntento() {
        if (jugadorActual == 1) {
            intentosJugador1++;
            lblJugador1.setText("Jugador 1: " + intentosJugador1);
        } else {
            intentosJugador2++;
            lblJugador2.setText("Jugador 2: " + intentosJugador2);
        }
    }

    // Método para activar el jugador actual
    private void activarJugador(int jugador) {
        if (jugador == 1) {
            lblJugador1.setStyle("-fx-font-weight: bold");
            lblJugador2.setStyle("");
        } else {
            lblJugador1.setStyle("");
            lblJugador2.setStyle("-fx-font-weight: bold");
        }
    }
}
*/

/*
//SIII ESTA YA ES LA CASCARA DEL MEMORAMA FALTA HACER QUE SOLO EL PAR ENCONTRADO SE QUEDE VOLTIADO Y EL LADO NO PAR SIGA REGRESANDO A SU POCICION ORIGINAL
package com.example.demo.vistas;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class memorama extends Stage {

    private Scene escena;
    private Label lblPares, lblJugador1, lblJugador2, lblTiempoTotal, lblTiempoTurno1, lblTiempoTurno2, lblScore1, lblScore2, lblPuntos1, lblPuntos2, lblPuntosValor1, lblPuntosValor2;
    private GridPane gdpJuego;
    private Button btnJugar;
    private VBox vbxPrincipal, vbxJugadores;
    private HBox hbxTiempo, hbxJuego, hbxJugador1, hbxJugador2, hbxTiempoTotal, hbxTiempoJugador1, hbxTiempoJugador2, hbxPuntosJugador1, hbxPuntosJugador2;
    private Button[][] arBtnCartas;
    private Timeline timeline, timelineVolteo;
    private Integer timeSeconds = 0, tiempoTurno = 5;
    private int jugadorActual = 1;
    private int intentosJugador1 = 0;
    private int intentosJugador2 = 0;
    private int cartasVolteadasJugadorActual = 0;
    private int carta1Jugador1 = -1;
    private int carta2Jugador1 = -1;
    private int carta1Jugador2 = -1;
    private int carta2Jugador2 = -1;
    private String imagenCarta1Jugador1;
    private String imagenCarta2Jugador1;
    private String imagenCarta1Jugador2;
    private String imagenCarta2Jugador2;
    private boolean[][] cartaVolteada;
    private boolean[] parejasVolteadas = new boolean[16]; // Para asegurarnos de que cada par se voltee solo una vez

    public memorama() {
        crearUI();
        this.setTitle("Memorama :)");
        this.setScene(escena);
        this.show();
    }

    private void crearUI() {
        lblPares = new Label("Número de Pares:");
        btnJugar = new Button("Iniciar Juego");
        lblTiempoTotal = new Label("Tiempo Total: 00:00");
        lblTiempoTurno1 = new Label(" -> 00:00");
        lblTiempoTurno2 = new Label(" -> 00:00");
        lblPuntos1 = new Label("Puntos: ");
        lblPuntos2 = new Label("Puntos: ");
        lblPuntosValor1 = new Label("0");
        lblPuntosValor2 = new Label("0");
        hbxTiempoTotal = new HBox(lblTiempoTotal);
        hbxTiempoJugador1 = new HBox(lblTiempoTurno1);
        hbxTiempoJugador2 = new HBox(lblTiempoTurno2);
        hbxPuntosJugador1 = new HBox(lblPuntos1, lblPuntosValor1);
        hbxPuntosJugador2 = new HBox(lblPuntos2, lblPuntosValor2);
        hbxTiempo = new HBox(lblPares, btnJugar, hbxTiempoTotal);

        lblJugador1 = new Label("Jugador 1: 0");
        lblScore1 = new Label("0");
        hbxJugador1 = new HBox(lblJugador1, lblScore1, hbxTiempoJugador1);

        lblJugador2 = new Label("Jugador 2: 0");
        lblScore2 = new Label("0");
        hbxJugador2 = new HBox(lblJugador2, lblScore2, hbxTiempoJugador2);

        vbxJugadores = new VBox(hbxJugador1, hbxPuntosJugador1, hbxJugador2, hbxPuntosJugador2);
        gdpJuego = new GridPane();
        revolverCartas();
        hbxJuego = new HBox(gdpJuego, vbxJugadores);

        vbxPrincipal = new VBox(hbxTiempo, hbxJuego);

        escena = new Scene(vbxPrincipal, 1400, 600);

        // Inicializar el Timeline para el tiempo total de juego
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                timeSeconds++;
                // Actualizar el label del tiempo total
                lblTiempoTotal.setText(String.format("Tiempo Total: %02d:%02d", timeSeconds / 60, timeSeconds % 60));
                // Verificar si todas las cartas están volteadas
                if (todasLasCartasVolteadas()) {
                    timeline.stop();
                }
                // Controlar el tiempo de turno de cada jugador
                if (timeSeconds % 10 == 0 && cartasVolteadasJugadorActual < 2) {
                    cambiarTurno();
                }
            }
        }));

        // Inicializar el Timeline para el tiempo de volteo de las cartas
        timelineVolteo = new Timeline();
        timelineVolteo.setCycleCount(1); // Solo se ejecutará una vez
        timelineVolteo.getKeyFrames().add(new KeyFrame(Duration.seconds(tiempoTurno), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Cambiar al siguiente jugador cuando se agote el tiempo de volteo
                cambiarTurno();
            }
        }));

        // Iniciar el Timeline cuando se inicia el juego
        btnJugar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                timeline.playFromStart();
                activarJugador(jugadorActual);
                iniciarTemporizadorTurno();
            }
        });
    }

    private void revolverCartas() {
        List<String> nombresImagenes = new ArrayList<>();
        for (int i = 1; i <= 15; i++) {
            nombresImagenes.add(i + ".png");
            nombresImagenes.add(i + ".png");
        }
        Collections.shuffle(nombresImagenes);

        arBtnCartas = new Button[3][10];
        cartaVolteada = new boolean[3][10];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 10; j++) {
                int indiceImagen = i * 10 + j;
                String nombreImagen = nombresImagenes.get(indiceImagen);
                Button btnCarta = new Button();
                ImageView imvCarta = new ImageView(
                        getClass().getResource("/images/0.png").toString()
                );
                imvCarta.setFitHeight(150);
                imvCarta.setFitWidth(100);
                btnCarta.setGraphic(imvCarta);
                btnCarta.setPrefSize(100, 150);
                arBtnCartas[i][j] = btnCarta;
                gdpJuego.add(btnCarta, j, i);

                final int fila = i;
                final int columna = j;
                btnCarta.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if (!cartaVolteada[fila][columna]) { // Si la carta no está volteada
                            // Cambiar la imagen de "0.png" a la siguiente imagen revelada
                            ImageView nuevaVista = new ImageView(getClass().getResource("/images/" + nombreImagen).toString());
                            nuevaVista.setFitHeight(150);
                            nuevaVista.setFitWidth(100);
                            btnCarta.setGraphic(nuevaVista);
                            cartaVolteada[fila][columna] = true; // Marcar la carta como volteada
                            cartasVolteadasJugadorActual++;
                            // Verificar si se ha volteado un par
                            if (cartasVolteadasJugadorActual == 2) {
                                registrarIntento();
                                comprobarPares();
                                cartasVolteadasJugadorActual = 0; // Reiniciar el contador de cartas volteadas
                            }
                        }
                    }
                });
            }
        }
    }

    private void comprobarPares() {
        if (jugadorActual == 1) {
            if (carta1Jugador1 != -1 && carta2Jugador1 != -1) {
                if (imagenCarta1Jugador1.equals(imagenCarta2Jugador1)) {
                    // Si las imágenes son iguales, se aumenta el puntaje del jugador 1 y se actualiza el label
                    int puntos = Integer.parseInt(lblScore1.getText()) + 1;
                    lblScore1.setText(Integer.toString(puntos));
                } else {
                    // Si las imágenes no son iguales, se voltea de nuevo las cartas y se actualiza el puntaje
                    cartaVolteada[carta1Jugador1 / 10][carta1Jugador1 % 10] = false;
                    cartaVolteada[carta2Jugador1 / 10][carta2Jugador1 % 10] = false;
                    arBtnCartas[carta1Jugador1 / 10][carta1Jugador1 % 10].setGraphic(new ImageView(getClass().getResource("/images/0.png").toString()));
                    arBtnCartas[carta2Jugador1 / 10][carta2Jugador1 % 10].setGraphic(new ImageView(getClass().getResource("/images/0.png").toString()));
                }
            }
        } else {
            if (carta1Jugador2 != -1 && carta2Jugador2 != -1) {
                if (imagenCarta1Jugador2.equals(imagenCarta2Jugador2)) {
                    // Si las imágenes son iguales, se aumenta el puntaje del jugador 2 y se actualiza el label
                    int puntos = Integer.parseInt(lblScore2.getText()) + 1;
                    lblScore2.setText(Integer.toString(puntos));
                } else {
                    // Si las imágenes no son iguales, se voltea de nuevo las cartas y se actualiza el puntaje
                    cartaVolteada[carta1Jugador2 / 10][carta1Jugador2 % 10] = false;
                    cartaVolteada[carta2Jugador2 / 10][carta2Jugador2 % 10] = false;
                    arBtnCartas[carta1Jugador2 / 10][carta1Jugador2 % 10].setGraphic(new ImageView(getClass().getResource("/images/0.png").toString()));
                    arBtnCartas[carta2Jugador2 / 10][carta2Jugador2 % 10].setGraphic(new ImageView(getClass().getResource("/images/0.png").toString()));
                }
            }
        }
        // Llamar al método para voltear las cartas no encontradas
        voltearCartasNoEncontradas();
    }

    private void voltearCartasNoEncontradas() {
        // Crear un nuevo Timeline para voltear las cartas no encontradas después de un breve tiempo
        Timeline voltearCartasTimeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
            // Voltear todas las cartas
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 10; j++) {
                    if (cartaVolteada[i][j]) {
                        cartaVolteada[i][j] = false;
                       // arBtnCartas[i][j].setGraphic(new ImageView(getClass().getResource("/images/0.png").toString()));
                        ImageView imageView = new ImageView(getClass().getResource("/images/0.png").toString());
                        imageView.setFitWidth(100); // Ajusta el ancho deseado
                        imageView.setFitHeight(150); // Ajusta el alto deseado
                        arBtnCartas[i][j].setGraphic(imageView);
                    }
                }
            }

            // Marcar las parejas encontradas
            for (int i = 0; i < parejasVolteadas.length; i++) {
                if (parejasVolteadas[i]) {
                    cartaVolteada[i / 10][i % 10] = true;
                }
            }

            // Cambiar al siguiente jugador y activar su turno
            cambiarTurno();
            activarJugador(jugadorActual);
            iniciarTemporizadorTurno();
        }));
        voltearCartasTimeline.setCycleCount(1); // Se ejecutará una vez
        voltearCartasTimeline.play();
    }


    private void iniciarTemporizadorTurno() {
        if (jugadorActual == 1) {
            iniciarTemporizador(lblTiempoTurno1);
        } else {
            iniciarTemporizador(lblTiempoTurno2);
        }
    }

    private void iniciarTemporizador(Label lblTiempoTurno) {
        tiempoTurno = 10; // Camb
        timelineVolteo.playFromStart();
        timelineVolteo.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Mostrar el tiempo restante del turno como "00:00"
                lblTiempoTurno.setText("00:00");
            }
        });

        // Ajusta la duración del KeyFrame para que sea de 10 segundos
        timelineVolteo.getKeyFrames().setAll(new KeyFrame(Duration.seconds(tiempoTurno), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Cambiar al siguiente jugador cuando se agote el tiempo de volteo
                cambiarTurno();
            }
        }));

        // Actualizar el tiempo restante del turno en el Label
        timelineVolteo.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
            int segundosRestantes = (int) (tiempoTurno - newTime.toSeconds());
            lblTiempoTurno.setText(String.format("%02d:%02d", segundosRestantes / 60, segundosRestantes % 60));
        });

        // Agregar espacios adicionales entre el temporizador y el puntaje del jugador
        lblTiempoTurno.setPadding(new Insets(0, 10, 0, 10)); // Ajusta el valor según sea necesario
    }

    private boolean todasLasCartasVolteadas() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 10; j++) {
                if (!cartaVolteada[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // Método para cambiar el turno de los jugadores
    private void cambiarTurno() {
        jugadorActual = (jugadorActual == 1) ? 2 : 1;
        activarJugador(jugadorActual);
        iniciarTemporizadorTurno();
        cartasVolteadasJugadorActual = 0; // Reiniciar el contador de cartas volteadas para el nuevo jugador
        // Reiniciar índices y nombres de imágenes de las cartas volteadas en el turno
        carta1Jugador1 = -1;
        carta2Jugador1 = -1;
        carta1Jugador2 = -1;
        carta2Jugador2 = -1;
        imagenCarta1Jugador1 = null;
        imagenCarta2Jugador1 = null;
        imagenCarta1Jugador2 = null;
        imagenCarta2Jugador2 = null;
    }

    // Método para registrar el intento de un jugador
    private void registrarIntento() {
        if (jugadorActual == 1) {
            intentosJugador1++;
            lblJugador1.setText("Jugador 1: " + intentosJugador1);
        } else {
            intentosJugador2++;
            lblJugador2.setText("Jugador 2: " + intentosJugador2);
        }
    }

    // Método para activar el jugador actual
    private void activarJugador(int jugador) {
        if (jugador == 1) {
            lblJugador1.setStyle("-fx-font-weight: bold");
            lblJugador2.setStyle("");
        } else {
            lblJugador1.setStyle("");
            lblJugador2.setStyle("-fx-font-weight: bold");
        }
    }
}
*/
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~`
//AL MENOS YA LAS VOLTEA
/*
package com.example.demo.vistas;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class memorama extends Stage {

    private Scene escena;
    private Label lblPares, lblJugador1, lblJugador2, lblTiempoTotal, lblTiempoTurno1, lblTiempoTurno2, lblScore1, lblScore2, lblPuntos1, lblPuntos2, lblPuntosValor1, lblPuntosValor2;
    private GridPane gdpJuego;
    private Button btnJugar;
    private VBox vbxPrincipal, vbxJugadores;
    private HBox hbxTiempo, hbxJuego, hbxJugador1, hbxJugador2, hbxTiempoTotal, hbxTiempoJugador1, hbxTiempoJugador2, hbxPuntosJugador1, hbxPuntosJugador2;
    private Button[][] arBtnCartas;
    private Timeline timeline, timelineVolteo;
    private Integer timeSeconds = 0, tiempoTurno = 5;
    private int jugadorActual = 1;
    private int intentosJugador1 = 0;
    private int intentosJugador2 = 0;
    private int cartasVolteadasJugadorActual = 0;
    private int carta1Jugador1 = -1;
    private int carta2Jugador1 = -1;
    private int carta1Jugador2 = -1;
    private int carta2Jugador2 = -1;
    private String imagenCarta1Jugador1;
    private String imagenCarta2Jugador1;
    private String imagenCarta1Jugador2;
    private String imagenCarta2Jugador2;
    private boolean[][] cartaVolteada;
    private boolean[] parejasVolteadas = new boolean[16]; // Para asegurarnos de que cada par se voltee solo una vez

    public memorama() {
        CrearUI();
        this.setTitle("Memorama :)");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {

        lblPares = new Label("Número de Pares:");
        btnJugar = new Button("Iniciar Juego");
        lblTiempoTotal = new Label("Tiempo Total: 00:00");
        lblTiempoTurno1 = new Label(" -> 00:00");
        lblTiempoTurno2 = new Label(" -> 00:00");
        lblPuntos1 = new Label("Puntos: ");
        lblPuntos2 = new Label("Puntos: ");
        lblPuntosValor1 = new Label("0");
        lblPuntosValor2 = new Label("0");
        hbxTiempoTotal = new HBox(lblTiempoTotal);
        hbxTiempoJugador1 = new HBox(lblTiempoTurno1);
        hbxTiempoJugador2 = new HBox(lblTiempoTurno2);
        hbxPuntosJugador1 = new HBox(lblPuntos1, lblPuntosValor1);
        hbxPuntosJugador2 = new HBox(lblPuntos2, lblPuntosValor2);
        hbxTiempo = new HBox(lblPares, btnJugar, hbxTiempoTotal);

        lblJugador1 = new Label("Jugador 1: 0");
        lblScore1 = new Label("0");
        hbxJugador1 = new HBox(lblJugador1, lblScore1, hbxTiempoJugador1);

        lblJugador2 = new Label("Jugador 2: 0");
        lblScore2 = new Label("0");
        hbxJugador2 = new HBox(lblJugador2, lblScore2, hbxTiempoJugador2);

        vbxJugadores = new VBox(hbxJugador1, hbxPuntosJugador1, hbxJugador2, hbxPuntosJugador2);
        gdpJuego = new GridPane();
        RevolverCartas();
        hbxJuego = new HBox(gdpJuego, vbxJugadores);

        vbxPrincipal = new VBox(hbxTiempo, hbxJuego);

        escena = new Scene(vbxPrincipal, 1400, 600);

        // Inicializar el Timeline para el tiempo total de juego
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                timeSeconds++;
                // Actualizar el label del tiempo total
                lblTiempoTotal.setText(String.format("Tiempo Total: %02d:%02d", timeSeconds / 60, timeSeconds % 60));
                // Verificar si todas las cartas están volteadas
                if (todasLasCartasVolteadas()) {
                    timeline.stop();
                }
                // Controlar el tiempo de turno de cada jugador
                if (timeSeconds % 10 == 0 && cartasVolteadasJugadorActual < 2) {
                    cambiarTurno();
                }
            }
        }));

        // Inicializar el Timeline para el tiempo de volteo de las cartas
        timelineVolteo = new Timeline();
        timelineVolteo.setCycleCount(1); // Solo se ejecutará una vez
        timelineVolteo.getKeyFrames().add(new KeyFrame(Duration.seconds(tiempoTurno), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Cambiar al siguiente jugador cuando se agote el tiempo de volteo
                cambiarTurno();
            }
        }));

        // Iniciar el Timeline cuando se inicia el juego
        btnJugar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                timeline.playFromStart();
                activarJugador(jugadorActual);
                iniciarTemporizadorTurno();
            }
        });
    }

    private void RevolverCartas() {
        List<String> nombresImagenes = new ArrayList<>();
        for (int i = 1; i <= 15; i++) {
            nombresImagenes.add(i + ".png");
            nombresImagenes.add(i + ".png");
        }
        Collections.shuffle(nombresImagenes);

        arBtnCartas = new Button[3][10];
        cartaVolteada = new boolean[3][10];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 10; j++) {
                int indiceImagen = i * 10 + j;
                   String nombreImagen = nombresImagenes.get(indiceImagen);
                Button btnCarta = new  Button();
                ImageView imvCarta = new ImageView(
                        getClass().getResource("/images/0.png").toString()
                );
                imvCarta.setFitHeight(150);
                imvCarta.setFitWidth(100);
                btnCarta.setGraphic(imvCarta);
                btnCarta.setPrefSize(100, 150);
                arBtnCartas[i][j] = btnCarta;
                gdpJuego.add(btnCarta, j, i);

                final int fila = i;
                final int columna = j;
                btnCarta.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if (!cartaVolteada[fila][columna]) { // Si la carta no está volteada
                            // Cambiar la imagen de "0.png" a la siguiente imagen revelada
                            ImageView nuevaVista = new ImageView(getClass().getResource("/images/" + nombreImagen).toString());
                            nuevaVista.setFitHeight(150);
                            nuevaVista.setFitWidth(100);
                            btnCarta.setGraphic(nuevaVista);
                            cartaVolteada[fila][columna] = true; // Marcar la carta como volteada
                            cartasVolteadasJugadorActual++;
                            // Verificar si se ha volteado un par
                            if (cartasVolteadasJugadorActual == 2) {
                                registrarIntento();
                                comprobarPares();
                                cartasVolteadasJugadorActual = 0; // Reiniciar el contador de cartas volteadas
                            }
                        }
                    }
                });
            }
        }
    }

    private void comprobarPares() {
        if (jugadorActual == 1) {
            if (carta1Jugador1 != -1 && carta2Jugador1 != -1) {
                if (imagenCarta1Jugador1.equals(imagenCarta2Jugador1)) {
                    // Si las imágenes son iguales, se aumenta el puntaje del jugador 1 y se actualiza el label
                    int puntos = Integer.parseInt(lblScore1.getText()) + 1;
                    lblScore1.setText(Integer.toString(puntos));
                } else {
                    // Si las imágenes no son iguales, se voltea de nuevo las cartas y se actualiza el puntaje
                    cartaVolteada[carta1Jugador1 / 10][carta1Jugador1 % 10] = false;
                    cartaVolteada[carta2Jugador1 / 10][carta2Jugador1 % 10] = false;
                    arBtnCartas[carta1Jugador1 / 10][carta1Jugador1 % 10].setGraphic(new ImageView(getClass().getResource("/images/0.png").toString()));
                    arBtnCartas[carta2Jugador1 / 10][carta2Jugador1 % 10].setGraphic(new ImageView(getClass().getResource("/images/0.png").toString()));
                }
            }
        } else {
            if (carta1Jugador2 != -1 && carta2Jugador2 != -1) {
                if (imagenCarta1Jugador2.equals(imagenCarta2Jugador2)) {
                    // Si las imágenes son iguales, se aumenta el puntaje del jugador 2 y se actualiza el label
                    int puntos = Integer.parseInt(lblScore2.getText()) + 1;
                    lblScore2.setText(Integer.toString(puntos));
                } else {
                    // Si las imágenes no son iguales, se voltea de nuevo las cartas y se actualiza el puntaje
                    cartaVolteada[carta1Jugador2 / 10][carta1Jugador2 % 10] = false;
                    cartaVolteada[carta2Jugador2 / 10][carta2Jugador2 % 10] = false;
                    arBtnCartas[carta1Jugador2 / 10][carta1Jugador2 % 10].setGraphic(new ImageView(getClass().getResource("/images/0.png").toString()));
                    arBtnCartas[carta2Jugador2 / 10][carta2Jugador2 % 10].setGraphic(new ImageView(getClass().getResource("/images/0.png").toString()));
                }
            }
        }
        // Llamar al método para voltear las cartas no encontradas
        voltearCartasNoEncontradas();
    }

    private void voltearCartasNoEncontradas() {
        // Crear un nuevo Timeline para voltear las cartas no encontradas después de un breve tiempo
        Timeline voltearCartasTimeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
            // Voltear las cartas no encontradas
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 10; j++) {
                    if (cartaVolteada[i][j] && !parejasVolteadas[i * 10 + j]) {
                        // Si la carta está volteada pero su pareja no ha sido encontrada, volverla a voltear
                        cartaVolteada[i][j] = false;
                        arBtnCartas[i][j].setGraphic(new ImageView(getClass().getResource("/images/0.png").toString()));
                    }
                }
            }
            // Cambiar al siguiente jugador y activar su turno
            cambiarTurno();
            activarJugador(jugadorActual);
            iniciarTemporizadorTurno();
        }));
        voltearCartasTimeline.setCycleCount(1); // Se ejecutará una vez
        voltearCartasTimeline.play();
    }

    private void iniciarTemporizadorTurno() {
        if (jugadorActual == 1) {
            iniciarTemporizador(lblTiempoTurno1);
        } else {
            iniciarTemporizador(lblTiempoTurno2);
        }
    }

    private void iniciarTemporizador(Label lblTiempoTurno) {
        tiempoTurno = 10; // Cambia el tiempo del turno a 10 segundos
        timelineVolteo.playFromStart();
        timelineVolteo.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Mostrar el tiempo restante del turno como "00:00"
                lblTiempoTurno.setText("00:00");
            }
        });

        // Ajusta la duración del KeyFrame para que sea de 10 segundos
        timelineVolteo.getKeyFrames().setAll(new KeyFrame(Duration.seconds(tiempoTurno), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Cambiar al siguiente jugador cuando se agote el tiempo de volteo
                cambiarTurno();
            }
        }));

        // Actualizar el tiempo restante del turno en el Label
        timelineVolteo.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
            int segundosRestantes = (int) (tiempoTurno - newTime.toSeconds());
            lblTiempoTurno.setText(String.format("%02d:%02d", segundosRestantes / 60, segundosRestantes % 60));
        });

        // Agregar espacios adicionales entre el temporizador y el puntaje del jugador
        lblTiempoTurno.setPadding(new Insets(0, 10, 0, 10)); // Ajusta el valor según sea necesario
    }

    private boolean todasLasCartasVolteadas() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 10; j++) {
                if (!cartaVolteada[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // Método para cambiar el turno de los jugadores
    private void cambiarTurno() {
        jugadorActual = (jugadorActual == 1) ? 2 : 1;
        activarJugador(jugadorActual);
        iniciarTemporizadorTurno();
        cartasVolteadasJugadorActual = 0; // Reiniciar el contador de cartas volteadas para el nuevo jugador
        // Reiniciar índices y nombres de imágenes de las cartas volteadas en el turno
        carta1Jugador1 = -1;
        carta2Jugador1 = -1;
        carta1Jugador2 = -1;
        carta2Jugador2 = -1;
        imagenCarta1Jugador1 = null;
        imagenCarta2Jugador1 = null;
        imagenCarta1Jugador2 = null;
        imagenCarta2Jugador2 = null;
    }

    // Método para registrar el intento de un jugador
    private void registrarIntento() {
        if (jugadorActual == 1) {
            intentosJugador1++;
            lblJugador1.setText("Jugador 1: " + intentosJugador1);
        } else {
            intentosJugador2++;
            lblJugador2.setText("Jugador 2: " + intentosJugador2);
        }
    }

    // Método para activar el jugador actual
    private void activarJugador(int jugador) {
        if (jugador == 1) {
            lblJugador1.setStyle("-fx-font-weight: bold");
            lblJugador2.setStyle("");
        } else {
            lblJugador1.setStyle("");
            lblJugador2.setStyle("-fx-font-weight: bold");
        }
    }
}

*/

/*
package com.example.demo.vistas;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class memorama extends Stage {

    private Scene escena;
    private Label lblPares, lblJugador1, lblJugador2, lblTiempoTotal, lblTiempoTurno1, lblTiempoTurno2, lblScore1, lblScore2, lblPuntos1, lblPuntos2, lblPuntosValor1, lblPuntosValor2;
    private GridPane gdpJuego;
    private Button btnJugar;
    private VBox vbxPrincipal, vbxJugadores;
    private HBox hbxTiempo, hbxJuego, hbxJugador1, hbxJugador2, hbxTiempoTotal, hbxTiempoJugador1, hbxTiempoJugador2, hbxPuntosJugador1, hbxPuntosJugador2;
    private Button[][] arBtnCartas;
    private Timeline timeline, timelineVolteo;
    private Integer timeSeconds = 0, tiempoTurno = 5;
    private int jugadorActual = 1;
    private int intentosJugador1 = 0;
    private int intentosJugador2 = 0;
    private int cartasVolteadasJugadorActual = 0;
    private int carta1Jugador1 = -1;
    private int carta2Jugador1 = -1;
    private int carta1Jugador2 = -1;
    private int carta2Jugador2 = -1;
    private String imagenCarta1Jugador1;
    private String imagenCarta2Jugador1;
    private String imagenCarta1Jugador2;
    private String imagenCarta2Jugador2;
    private boolean[][] cartaVolteada;
    private boolean[] parejasVolteadas = new boolean[16]; // Para asegurarnos de que cada par se voltee solo una vez

    public memorama() {
        CrearUI();
        this.setTitle("Memorama :)");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {

        lblPares = new Label("Número de Pares:");
        btnJugar = new Button("Iniciar Juego");
        lblTiempoTotal = new Label("Tiempo Total: 00:00");
        lblTiempoTurno1 = new Label(" -> 00:00");
        lblTiempoTurno2 = new Label(" -> 00:00");
        lblPuntos1 = new Label("Puntos: ");
        lblPuntos2 = new Label("Puntos: ");
        lblPuntosValor1 = new Label("0");
        lblPuntosValor2 = new Label("0");
        hbxTiempoTotal = new HBox(lblTiempoTotal);
        hbxTiempoJugador1 = new HBox(lblTiempoTurno1);
        hbxTiempoJugador2 = new HBox(lblTiempoTurno2);
        hbxPuntosJugador1 = new HBox(lblPuntos1, lblPuntosValor1);
        hbxPuntosJugador2 = new HBox(lblPuntos2, lblPuntosValor2);
        hbxTiempo = new HBox(lblPares, btnJugar, hbxTiempoTotal);

        lblJugador1 = new Label("Jugador 1: 0");
        lblScore1 = new Label("0");
        hbxJugador1 = new HBox(lblJugador1, lblScore1, hbxTiempoJugador1);

        lblJugador2 = new Label("Jugador 2: 0");
        lblScore2 = new Label("0");
        hbxJugador2 = new HBox(lblJugador2, lblScore2, hbxTiempoJugador2);

        vbxJugadores = new VBox(hbxJugador1, hbxPuntosJugador1, hbxJugador2, hbxPuntosJugador2);
        gdpJuego = new GridPane();
        RevolverCartas();
        hbxJuego = new HBox(gdpJuego, vbxJugadores);

        vbxPrincipal = new VBox(hbxTiempo, hbxJuego);

        escena = new Scene(vbxPrincipal, 1400, 600);

        // Inicializar el Timeline para el tiempo total de juego
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                timeSeconds++;
                // Actualizar el label del tiempo total
                lblTiempoTotal.setText(String.format("Tiempo Total: %02d:%02d", timeSeconds / 60, timeSeconds % 60));
                // Verificar si todas las cartas están volteadas
                if (todasLasCartasVolteadas()) {
                    timeline.stop();
                }
                // Controlar el tiempo de turno de cada jugador
                if (timeSeconds % 10 == 0 && cartasVolteadasJugadorActual < 2) {
                    cambiarTurno();
                }
            }
        }));

        // Inicializar el Timeline para el tiempo de volteo de las cartas
        timelineVolteo = new Timeline();
        timelineVolteo.setCycleCount(1); // Solo se ejecutará una vez
        timelineVolteo.getKeyFrames().add(new KeyFrame(Duration.seconds(tiempoTurno), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Cambiar al siguiente jugador cuando se agote el tiempo de volteo
                cambiarTurno();
            }
        }));

        // Iniciar el Timeline cuando se inicia el juego
        btnJugar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                timeline.playFromStart();
                activarJugador(jugadorActual);
                iniciarTemporizadorTurno();
            }
        });
    }

    private void RevolverCartas() {
        List<String> nombresImagenes = new ArrayList<>();
        for (int i = 1; i <= 15; i++) {
            nombresImagenes.add(i + ".png");
            nombresImagenes.add(i + ".png");
        }
        Collections.shuffle(nombresImagenes);

        arBtnCartas = new Button[3][10];
        cartaVolteada = new boolean[3][10];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 10; j++) {
                int indiceImagen = i * 10 + j;
                String nombreImagen = nombresImagenes.get(indiceImagen);
                Button btnCarta = new  Button();
                ImageView imvCarta = new ImageView(
                        getClass().getResource("/images/0.png").toString()
                );
                imvCarta.setFitHeight(150);
                imvCarta.setFitWidth(100);
                btnCarta.setGraphic(imvCarta);
                btnCarta.setPrefSize(100, 150);
                arBtnCartas[i][j] = btnCarta;
                gdpJuego.add(btnCarta, j, i);

                final int fila = i;
                final int columna = j;
                btnCarta.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if (!cartaVolteada[fila][columna]) { // Si la carta no está volteada
                            // Cambiar la imagen de "0.png" a la siguiente imagen revelada
                            ImageView nuevaVista = new ImageView(getClass().getResource("/images/" + nombreImagen).toString());
                            nuevaVista.setFitHeight(150);
                            nuevaVista.setFitWidth(100);
                            btnCarta.setGraphic(nuevaVista);
                            cartaVolteada[fila][columna] = true; // Marcar la carta como volteada
                            cartasVolteadasJugadorActual++;
                            // Verificar si se ha volteado un par
                            if (cartasVolteadasJugadorActual == 2) {
                                registrarIntento();
                                comprobarPares();
                                cartasVolteadasJugadorActual = 0; // Reiniciar el contador de cartas volteadas
                            }
                        }
                    }
                });
            }
        }
    }

    private void comprobarPares() {
        if (jugadorActual == 1) {
            if (carta1Jugador1 != -1 && carta2Jugador1 != -1) {
                if (imagenCarta1Jugador1.equals(imagenCarta2Jugador1)) {
                    // Si las imágenes son iguales, se aumenta el puntaje del jugador 1 y se actualiza el label
                    int puntos = Integer.parseInt(lblScore1.getText()) + 1;
                    lblScore1.setText(Integer.toString(puntos));
                } else {
                    // Si las imágenes no son iguales, se voltea de nuevo las cartas y se actualiza el puntaje
                    cartaVolteada[carta1Jugador1 / 10][carta1Jugador1 % 10] = false;
                    cartaVolteada[carta2Jugador1 / 10][carta2Jugador1 % 10] = false;
                    arBtnCartas[carta1Jugador1 / 10][carta1Jugador1 % 10].setGraphic(new ImageView(getClass().getResource("/images/0.png").toString()));
                    arBtnCartas[carta2Jugador1 / 10][carta2Jugador1 % 10].setGraphic(new ImageView(getClass().getResource("/images/0.png").toString()));
                }
            }
        } else {
            if (carta1Jugador2 != -1 && carta2Jugador2 != -1) {
                if (imagenCarta1Jugador2.equals(imagenCarta2Jugador2)) {
                    // Si las imágenes son iguales, se aumenta el puntaje del jugador 2 y se actualiza el label
                    int puntos = Integer.parseInt(lblScore2.getText()) + 1;
                    lblScore2.setText(Integer.toString(puntos));
                } else {
                    // Si las imágenes no son iguales, se voltea de nuevo las cartas y se actualiza el puntaje
                    cartaVolteada[carta1Jugador2 / 10][carta1Jugador2 % 10] = false;
                    cartaVolteada[carta2Jugador2 / 10][carta2Jugador2 % 10] = false;
                    arBtnCartas[carta1Jugador2 / 10][carta1Jugador2 % 10].setGraphic(new ImageView(getClass().getResource("/images/0.png").toString()));
                    arBtnCartas[carta2Jugador2 / 10][carta2Jugador2 % 10].setGraphic(new ImageView(getClass().getResource("/images/0.png").toString()));
                }
            }
        }
    }


    private void iniciarTemporizadorTurno() {
        if (jugadorActual == 1) {
            iniciarTemporizador(lblTiempoTurno1);
        } else {
            iniciarTemporizador(lblTiempoTurno2);
        }
    }

    private void iniciarTemporizador(Label lblTiempoTurno) {
        tiempoTurno = 10; // Cambia el tiempo del turno a 10 segundos
        timelineVolteo.playFromStart();
        timelineVolteo.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Mostrar el tiempo restante del turno como "00:00"
                lblTiempoTurno.setText("00:00");
            }
        });

        // Ajusta la duración del KeyFrame para que sea de 10 segundos
        timelineVolteo.getKeyFrames().setAll(new KeyFrame(Duration.seconds(tiempoTurno), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Cambiar al siguiente jugador cuando se agote el tiempo de volteo
                cambiarTurno();
            }
        }));

        // Actualizar el tiempo restante del turno en el Label
        timelineVolteo.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
            int segundosRestantes = (int) (tiempoTurno - newTime.toSeconds());
            lblTiempoTurno.setText(String.format("%02d:%02d", segundosRestantes / 60, segundosRestantes % 60));
        });

        // Agregar espacios adicionales entre el temporizador y el puntaje del jugador
        lblTiempoTurno.setPadding(new Insets(0, 10, 0, 10)); // Ajusta el valor según sea necesario
    }

    private boolean todasLasCartasVolteadas() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 10; j++) {
                if (!cartaVolteada[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // Método para cambiar el turno de los jugadores
    private void cambiarTurno() {
        jugadorActual = (jugadorActual == 1) ? 2 : 1;
        activarJugador(jugadorActual);
        iniciarTemporizadorTurno();
        cartasVolteadasJugadorActual = 0; // Reiniciar el contador de cartas volteadas para el nuevo jugador
        // Reiniciar índices y nombres de imágenes de las cartas volteadas en el turno
        carta1Jugador1 = -1;
        carta2Jugador1 = -1;
        carta1Jugador2 = -1;
        carta2Jugador2 = -1;
        imagenCarta1Jugador1 = null;
        imagenCarta2Jugador1 = null;
        imagenCarta1Jugador2 = null;
        imagenCarta2Jugador2 = null;
    }

    // Método para registrar el intento de un jugador
    private void registrarIntento() {
        if (jugadorActual == 1) {
            intentosJugador1++;
            lblJugador1.setText("Jugador 1: " + intentosJugador1);
        } else {
            intentosJugador2++;
            lblJugador2.setText("Jugador 2: " + intentosJugador2);
        }
    }

    // Método para activar el jugador actual
    private void activarJugador(int jugador) {
        if (jugador == 1) {
            lblJugador1.setStyle("-fx-font-weight: bold");
            lblJugador2.setStyle("");
        } else {
            lblJugador1.setStyle("");
            lblJugador2.setStyle("-fx-font-weight: bold");
        }
    }
}

*/





//SE AGREGARON LOS SCORE PERO AUN NO LES DOY FUNCION
/*
package com.example.demo.vistas;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class memorama extends Stage {

    private Scene escena;
    private Label lblPares, lblJugador1, lblJugador2, lblTiempoTotal, lblTiempoTurno1, lblTiempoTurno2, lblScore1, lblScore2,lblPuntos1, lblPuntosValor1, lblPuntos2, lblPuntosValor2;
    private GridPane gdpJuego;
    private Button btnJugar;
    private VBox vbxPrincipal, vbxJugadores;
    private HBox hbxTiempo, hbxJuego, hbxJugador1, hbxJugador2, hbxTiempoTotal, hbxTiempoJugador1, hbxTiempoJugador2, hbxPuntosJugador1, hbxPuntosJugador2;
    private Button[][] arBtnCartas;
    private Timeline timeline, timelineVolteo;
    private Integer timeSeconds = 0, tiempoTurno = 5;
    private int jugadorActual = 1;
    private int intentosJugador1 = 0;
    private int intentosJugador2 = 0;
    private int cartasVolteadasJugadorActual = 0;
    private boolean[][] cartaVolteada;
    private boolean[] parejasVolteadas = new boolean[16]; // Para asegurarnos de que cada par se voltee solo una vez

    public memorama() {
        CrearUI();
        this.setTitle("Memorama :)");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {

        lblPares = new Label("Número de Pares:");
        btnJugar = new Button("Iniciar Juego");
        lblTiempoTotal = new Label("Tiempo Total: 00:00");
        lblTiempoTurno1 = new Label(" -> 00:00");
        lblTiempoTurno2 = new Label(" -> 00:00");
        hbxTiempoTotal = new HBox(lblTiempoTotal);
        hbxTiempoJugador1 = new HBox(lblTiempoTurno1);
        hbxTiempoJugador2 = new HBox(lblTiempoTurno2);
        hbxTiempo = new HBox(lblPares, btnJugar, hbxTiempoTotal);

        lblJugador1 = new Label("Jugador 1: 0");
        lblScore1 = new Label("0");
        hbxJugador1 = new HBox(lblJugador1, lblScore1, hbxTiempoJugador1);
        lblPuntos1 = new Label("SCORE :");
        lblPuntosValor1 = new Label("0");
        hbxPuntosJugador1 = new HBox(lblPuntos1, lblPuntosValor1);

        lblJugador2 = new Label("SCORE : 0");
        lblScore2 = new Label("0");
        hbxJugador2 = new HBox(lblJugador2, lblScore2, hbxTiempoJugador2);
        lblPuntos2 = new Label("Puntos Jugador 2:");
        lblPuntosValor2 = new Label("0");
        hbxPuntosJugador2 = new HBox(lblPuntos2, lblPuntosValor2);

        // Organizar los elementos en el VBox de jugadores
        vbxJugadores = new VBox(hbxJugador1, hbxPuntosJugador1, hbxJugador2, hbxPuntosJugador2);
        gdpJuego = new GridPane();
        RevolverCartas();
        hbxJuego = new HBox(gdpJuego, vbxJugadores);

        vbxPrincipal = new VBox(hbxTiempo, hbxJuego);

        escena = new Scene(vbxPrincipal, 1400, 600);

        // Inicializar el Timeline para el tiempo total de juego
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                timeSeconds++;
                // Actualizar el label del tiempo total
                lblTiempoTotal.setText(String.format("Tiempo Total: %02d:%02d", timeSeconds / 60, timeSeconds % 60));
                // Verificar si todas las cartas están volteadas
                if (todasLasCartasVolteadas()) {
                    timeline.stop();
                }
                // Controlar el tiempo de turno de cada jugador
                if (timeSeconds % 10 == 0 && cartasVolteadasJugadorActual < 2) {
                    cambiarTurno();
                }
            }
        }));

        // Inicializar el Timeline para el tiempo de volteo de las cartas
        timelineVolteo = new Timeline();
        timelineVolteo.setCycleCount(1); // Solo se ejecutará una vez
        timelineVolteo.getKeyFrames().add(new KeyFrame(Duration.seconds(tiempoTurno), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Cambiar al siguiente jugador cuando se agote el tiempo de volteo
                cambiarTurno();
            }
        }));

        // Iniciar el Timeline cuando se inicia el juego
        btnJugar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                timeline.playFromStart();
                activarJugador(jugadorActual);
                iniciarTemporizadorTurno();
            }
        });
    }

    private void RevolverCartas() {
        List<String> nombresImagenes = new ArrayList<>();
        for (int i = 1; i <= 15; i++) {
            nombresImagenes.add(i + ".png");
            nombresImagenes.add(i + ".png");
        }
        Collections.shuffle(nombresImagenes);

        arBtnCartas = new Button[3][10];
        cartaVolteada = new boolean[3][10];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 10; j++) {
                int indiceImagen = i * 10 + j;
                String nombreImagen = nombresImagenes.get(indiceImagen);
                Button btnCarta = new Button();
                ImageView imvCarta = new ImageView(
                        getClass().getResource("/images/0.png").toString()
                );
                imvCarta.setFitHeight(150);
                imvCarta.setFitWidth(100);
                btnCarta.setGraphic(imvCarta);
                btnCarta.setPrefSize(100, 150);
                arBtnCartas[i][j] = btnCarta;
                gdpJuego.add(btnCarta, j, i);

                final int fila = i;
                final int columna = j;
                btnCarta.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if (!cartaVolteada[fila][columna]) { // Si la carta no está volteada
                            // Cambiar la imagen de "0.png" a la siguiente imagen revelada
                            ImageView nuevaVista = new ImageView(getClass().getResource("/images/" + nombreImagen).toString());
                            nuevaVista.setFitHeight(150);
                            nuevaVista.setFitWidth(100);
                            btnCarta.setGraphic(nuevaVista);
                            cartaVolteada[fila][columna] = true; // Marcar la carta como volteada
                            cartasVolteadasJugadorActual++;
                            // Verificar si se ha volteado un par
                            if (cartasVolteadasJugadorActual == 2) {
                                registrarIntento();
                                cartasVolteadasJugadorActual = 0; // Reiniciar el contador de cartas volteadas
                            }
                        }
                    }
                });
            }
        }
    }

    private void iniciarTemporizadorTurno() {
        if (jugadorActual == 1) {
            iniciarTemporizador(lblTiempoTurno1);
        } else {
            iniciarTemporizador(lblTiempoTurno2);
        }
    }

    private void iniciarTemporizador(Label lblTiempoTurno) {
        tiempoTurno = 10; // Cambia el tiempo del turno a 10 segundos
        timelineVolteo.playFromStart();
        timelineVolteo.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Mostrar el tiempo restante del turno como "00:00"
                lblTiempoTurno.setText("00:00");
            }
        });

        // Ajusta la duración del KeyFrame para que sea de 10 segundos
        timelineVolteo.getKeyFrames().setAll(new KeyFrame(Duration.seconds(tiempoTurno), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Cambiar al siguiente jugador cuando se agote el tiempo de volteo
                cambiarTurno();
            }
        }));

        // Actualizar el tiempo restante del turno en el Label
        timelineVolteo.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
            int segundosRestantes = (int) (tiempoTurno - newTime.toSeconds());
            lblTiempoTurno.setText(String.format("%02d:%02d", segundosRestantes / 60, segundosRestantes % 60));
        });

        // Agregar espacios adicionales entre el temporizador y el puntaje del jugador
        lblTiempoTurno.setPadding(new Insets(0, 10, 0, 10)); // Ajusta el valor según sea necesario
    }

    private boolean todasLasCartasVolteadas() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 10; j++) {
                if (!cartaVolteada[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // Método para cambiar el turno de los jugadores
    private void cambiarTurno() {
        jugadorActual = (jugadorActual == 1) ? 2 : 1;
        activarJugador(jugadorActual);
        iniciarTemporizadorTurno();
        cartasVolteadasJugadorActual = 0; // Reiniciar el contador de cartas volteadas para el nuevo jugador
    }

    // Método para registrar el intento de un jugador
    private void registrarIntento() {
        if (jugadorActual == 1) {
            intentosJugador1++;
            lblJugador1.setText("Jugador 1: " + intentosJugador1);
        } else {
            intentosJugador2++;
            lblJugador2.setText("Jugador 2: " + intentosJugador2);
        }
    }

    // Método para activar el jugador actual
    private void activarJugador(int jugador) {
        if (jugador == 1) {
            lblJugador1.setStyle("-fx-font-weight: bold");
            lblJugador2.setStyle("");
        } else {
            lblJugador1.setStyle("");
            lblJugador2.setStyle("-fx-font-weight: bold");
        }
    }
}

*/


//AQUI YA TIENE LOS ESPACIOS ENTRE EL SCORE Y EL TIEMPO SE TURNO Y EL TIEMPO EN 10 SEGUNDOS POR TURNO
/*package com.example.demo.vistas;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class memorama extends Stage {

    private Scene escena;
    private Label lblPares, lblJugador1, lblJugador2, lblTiempoTotal, lblTiempoTurno1, lblTiempoTurno2, lblScore1, lblScore2;
    private GridPane gdpJuego;
    private Button btnJugar;
    private VBox vbxPrincipal, vbxJugadores;
    private HBox hbxTiempo, hbxJuego, hbxJugador1, hbxJugador2, hbxTiempoTotal, hbxTiempoJugador1, hbxTiempoJugador2;
    private Button[][] arBtnCartas;
    private Timeline timeline, timelineVolteo;
    private Integer timeSeconds = 0, tiempoTurno = 5;
    private int jugadorActual = 1;
    private int intentosJugador1 = 0;
    private int intentosJugador2 = 0;
    private int cartasVolteadasJugadorActual = 0;
    private boolean[][] cartaVolteada;
    private boolean[] parejasVolteadas = new boolean[16]; // Para asegurarnos de que cada par se voltee solo una vez

    public memorama() {
        CrearUI();
        this.setTitle("Memorama :)");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {

        lblPares = new Label("Número de Pares:");
        btnJugar = new Button("Iniciar Juego");
        lblTiempoTotal = new Label("Tiempo Total: 00:00");
        lblTiempoTurno1 = new Label(" -> 00:00");
        lblTiempoTurno2 = new Label(" -> 00:00");
        hbxTiempoTotal = new HBox(lblTiempoTotal);
        hbxTiempoJugador1 = new HBox(lblTiempoTurno1);
        hbxTiempoJugador2 = new HBox(lblTiempoTurno2);
        hbxTiempo = new HBox(lblPares, btnJugar, hbxTiempoTotal);

        lblJugador1 = new Label("Jugador 1: 0");
        lblScore1 = new Label("0");
        hbxJugador1 = new HBox(lblJugador1, lblScore1, hbxTiempoJugador1);

        lblJugador2 = new Label("Jugador 2: 0");
        lblScore2 = new Label("0");
        hbxJugador2 = new HBox(lblJugador2, lblScore2, hbxTiempoJugador2);

        vbxJugadores = new VBox(hbxJugador1, hbxJugador2);
        gdpJuego = new GridPane();
        RevolverCartas();
        hbxJuego = new HBox(gdpJuego, vbxJugadores);

        vbxPrincipal = new VBox(hbxTiempo, hbxJuego);

        escena = new Scene(vbxPrincipal, 1400, 600);

        // Inicializar el Timeline para el tiempo total de juego
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                timeSeconds++;
                // Actualizar el label del tiempo total
                lblTiempoTotal.setText(String.format("Tiempo Total: %02d:%02d", timeSeconds / 60, timeSeconds % 60));
                // Verificar si todas las cartas están volteadas
                if (todasLasCartasVolteadas()) {
                    timeline.stop();
                }
                // Controlar el tiempo de turno de cada jugador
                if (timeSeconds % 10 == 0 && cartasVolteadasJugadorActual < 2) {
                    cambiarTurno();
                }
            }
        }));

        // Inicializar el Timeline para el tiempo de volteo de las cartas
        timelineVolteo = new Timeline();
        timelineVolteo.setCycleCount(1); // Solo se ejecutará una vez
        timelineVolteo.getKeyFrames().add(new KeyFrame(Duration.seconds(tiempoTurno), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Cambiar al siguiente jugador cuando se agote el tiempo de volteo
                cambiarTurno();
            }
        }));

        // Iniciar el Timeline cuando se inicia el juego
        btnJugar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                timeline.playFromStart();
                activarJugador(jugadorActual);
                iniciarTemporizadorTurno();
            }
        });
    }

    private void RevolverCartas() {
        List<String> nombresImagenes = new ArrayList<>();
        for (int i = 1; i <= 15; i++) {
            nombresImagenes.add(i + ".png");
            nombresImagenes.add(i + ".png");
        }
        Collections.shuffle(nombresImagenes);

        arBtnCartas = new Button[3][10];
        cartaVolteada = new boolean[3][10];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 10; j++) {
                int indiceImagen = i * 10 + j;
                String nombreImagen = nombresImagenes.get(indiceImagen);
                Button btnCarta = new Button();
                ImageView imvCarta = new ImageView(
                        getClass().getResource("/images/0.png").toString()
                );
                imvCarta.setFitHeight(150);
                imvCarta.setFitWidth(100);
                btnCarta.setGraphic(imvCarta);
                btnCarta.setPrefSize(100, 150);
                arBtnCartas[i][j] = btnCarta;
                gdpJuego.add(btnCarta, j, i);

                final int fila = i;
                final int columna = j;
                btnCarta.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if (!cartaVolteada[fila][columna]) { // Si la carta no está volteada
                            // Cambiar la imagen de "0.png" a la siguiente imagen revelada
                            ImageView nuevaVista = new ImageView(getClass().getResource("/images/" + nombreImagen).toString());
                            nuevaVista.setFitHeight(150);
                            nuevaVista.setFitWidth(100);
                            btnCarta.setGraphic(nuevaVista);
                            cartaVolteada[fila][columna] = true; // Marcar la carta como volteada
                            cartasVolteadasJugadorActual++;
                            // Verificar si se ha volteado un par
                            if (cartasVolteadasJugadorActual == 2) {
                                registrarIntento();
                                cartasVolteadasJugadorActual = 0; // Reiniciar el contador de cartas volteadas
                            }
                        }
                    }
                });
            }
        }
    }

    private void iniciarTemporizadorTurno() {
        if (jugadorActual == 1) {
            iniciarTemporizador(lblTiempoTurno1);
        } else {
            iniciarTemporizador(lblTiempoTurno2);
        }
    }

    private void iniciarTemporizador(Label lblTiempoTurno) {
        tiempoTurno = 10; // Cambia el tiempo del turno a 10 segundos
        timelineVolteo.playFromStart();
        timelineVolteo.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Mostrar el tiempo restante del turno como "00:00"
                lblTiempoTurno.setText("00:00");
            }
        });

        // Ajusta la duración del KeyFrame para que sea de 10 segundos
        timelineVolteo.getKeyFrames().setAll(new KeyFrame(Duration.seconds(tiempoTurno), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Cambiar al siguiente jugador cuando se agote el tiempo de volteo
                cambiarTurno();
            }
        }));

        // Actualizar el tiempo restante del turno en el Label
        timelineVolteo.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
            int segundosRestantes = (int) (tiempoTurno - newTime.toSeconds());
            lblTiempoTurno.setText(String.format("%02d:%02d", segundosRestantes / 60, segundosRestantes % 60));
        });

        // Agregar espacios adicionales entre el temporizador y el puntaje del jugador
        lblTiempoTurno.setPadding(new Insets(0, 10, 0, 10)); // Ajusta el valor según sea necesario
    }

    private boolean todasLasCartasVolteadas() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 10; j++) {
                if (!cartaVolteada[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // Método para cambiar el turno de los jugadores
    private void cambiarTurno() {
        jugadorActual = (jugadorActual == 1) ? 2 : 1;
        activarJugador(jugadorActual);
        iniciarTemporizadorTurno();
        cartasVolteadasJugadorActual = 0; // Reiniciar el contador de cartas volteadas para el nuevo jugador
    }

    // Método para registrar el intento de un jugador
    private void registrarIntento() {
        if (jugadorActual == 1) {
            intentosJugador1++;
            lblJugador1.setText("Jugador 1: " + intentosJugador1);
        } else {
            intentosJugador2++;
            lblJugador2.setText("Jugador 2: " + intentosJugador2);
        }
    }

    // Método para activar el jugador actual
    private void activarJugador(int jugador) {
        if (jugador == 1) {
            lblJugador1.setStyle("-fx-font-weight: bold");
            lblJugador2.setStyle("");
        } else {
            lblJugador1.setStyle("");
            lblJugador2.setStyle("-fx-font-weight: bold");
        }
    }
}
*/
/*
//AHORA EN ESTE CODIGO YA SE MUESTRA EN TIEMPO DEL TURNO JUNTO CON TODO LO ANTERIOR
package com.example.demo.vistas;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class memorama extends Stage {

    private Scene escena;
    private Label lblPares, lblJugador1, lblJugador2, lblTiempo, lblTiempoTurno1, lblTiempoTurno2, lblScore1, lblScore2;
    private GridPane gdpJuego;
    private Button btnJugar;
    private VBox vbxPrincipal, vbxJugadores;
    private HBox hbxTiempo, hbxJuego, hbxJugador1, hbxJugador2;
    private Button[][] arBtnCartas;
    private Timeline timeline, timelineVolteo;
    private Integer timeSeconds = 0, tiempoTurno = 5;
    private int jugadorActual = 1;
    private int intentosJugador1 = 0;
    private int intentosJugador2 = 0;
    private int cartasVolteadasJugadorActual = 0;
    private boolean[][] cartaVolteada;
    private boolean[] parejasVolteadas = new boolean[16]; // Para asegurarnos de que cada par se voltee solo una vez

    public memorama() {
        CrearUI();
        this.setTitle("Memorama :)");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {

        lblPares = new Label("Número de Pares:");
        btnJugar = new Button("Iniciar Juego");
        lblTiempo = new Label("00:00");
        lblTiempoTurno1 = new Label("00:00");
        lblTiempoTurno2 = new Label("00:00");
        hbxTiempo = new HBox(lblPares, btnJugar, lblTiempo);

        lblJugador1 = new Label("Jugador 1: 0");
        lblScore1 = new Label("0");
        hbxJugador1 = new HBox(lblJugador1, lblScore1, lblTiempoTurno1);

        lblJugador2 = new Label("Jugador 2: 0");
        lblScore2 = new Label("0");
        hbxJugador2 = new HBox(lblJugador2, lblScore2, lblTiempoTurno2);

        vbxJugadores = new VBox(hbxJugador1, hbxJugador2);
        gdpJuego = new GridPane();
        RevolverCartas();
        hbxJuego = new HBox(gdpJuego, vbxJugadores);

        vbxPrincipal = new VBox(hbxTiempo, hbxJuego);

        escena = new Scene(vbxPrincipal, 1300, 600);

        // Inicializar el Timeline para el tiempo total de juego
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                timeSeconds++;
                // Actualizar el label del tiempo
                lblTiempo.setText(String.format("%02d:%02d", timeSeconds / 60, timeSeconds % 60));
                // Verificar si todas las cartas están volteadas
                if (todasLasCartasVolteadas()) {
                    timeline.stop();
                }
                // Controlar el tiempo de turno de cada jugador
                if (timeSeconds % 5 == 0 && cartasVolteadasJugadorActual < 2) {
                    cambiarTurno();
                }
            }
        }));

        // Inicializar el Timeline para el tiempo de volteo de las cartas
        timelineVolteo = new Timeline();
        timelineVolteo.setCycleCount(1); // Solo se ejecutará una vez
        timelineVolteo.getKeyFrames().add(new KeyFrame(Duration.seconds(tiempoTurno), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Cambiar al siguiente jugador cuando se agote el tiempo de volteo
                cambiarTurno();
            }
        }));

        // Iniciar el Timeline cuando se inicia el juego
        btnJugar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                timeline.playFromStart();
                activarJugador(jugadorActual);
                iniciarTemporizadorTurno();
            }
        });
    }
    // private boolean[][] cartaVolteada;

    private void RevolverCartas() {
        List<String> nombresImagenes = new ArrayList<>();
        for (int i = 1; i <= 15; i++) {
            nombresImagenes.add(i + ".png");
            nombresImagenes.add(i + ".png");
        }
        Collections.shuffle(nombresImagenes);

        arBtnCartas = new Button[3][10];
        cartaVolteada = new boolean[3][10];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 10; j++) {
                int indiceImagen = i * 10 + j;
                String nombreImagen = nombresImagenes.get(indiceImagen);
                Button btnCarta = new Button();
                ImageView imvCarta = new ImageView(
                        getClass().getResource("/images/0.png").toString()
                );
                imvCarta.setFitHeight(150);
                imvCarta.setFitWidth(100);
                btnCarta.setGraphic(imvCarta);
                btnCarta.setPrefSize(100, 150);
                arBtnCartas[i][j] = btnCarta;
                gdpJuego.add(btnCarta, j, i);

                final int fila = i;
                final int columna = j;
                btnCarta.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if (!cartaVolteada[fila][columna]) { // Si la carta no está volteada
                            // Cambiar la imagen de "0.png" a la siguiente imagen revelada
                            ImageView nuevaVista = new ImageView(getClass().getResource("/images/" + nombreImagen).toString());
                            nuevaVista.setFitHeight(150);
                            nuevaVista.setFitWidth(100);
                            btnCarta.setGraphic(nuevaVista);
                            cartaVolteada[fila][columna] = true; // Marcar la carta como volteada
                            cartasVolteadasJugadorActual++;
                            // Verificar si se ha volteado un par
                            if (cartasVolteadasJugadorActual == 2) {
                                registrarIntento();
                                cartasVolteadasJugadorActual = 0; // Reiniciar el contador de cartas volteadas
                            }
                        }
                    }
                });
            }
        }
    }

    private void iniciarTemporizadorTurno() {
        if (jugadorActual == 1) {
            iniciarTemporizador(lblTiempoTurno1);
        } else {
            iniciarTemporizador(lblTiempoTurno2);
        }
    }

    private void iniciarTemporizador(Label lblTiempoTurno) {
        tiempoTurno = 5;
        timelineVolteo.playFromStart();
        timelineVolteo.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Mostrar el tiempo restante del turno como "00:00"
                lblTiempoTurno.setText("00:00");
            }
        });

        // Actualizar el tiempo restante del turno en el Label
        timelineVolteo.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
            int segundosRestantes = (int) (tiempoTurno - newTime.toSeconds());
            lblTiempoTurno.setText(String.format("%02d:%02d", segundosRestantes / 60, segundosRestantes % 60));
        });
    }

    private boolean todasLasCartasVolteadas() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 10; j++) {
                if (!cartaVolteada[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // Método para cambiar el turno de los jugadores
    private void cambiarTurno() {
        jugadorActual = (jugadorActual == 1) ? 2 : 1;
        activarJugador(jugadorActual);
        iniciarTemporizadorTurno();
        cartasVolteadasJugadorActual = 0; // Reiniciar el contador de cartas volteadas para el nuevo jugador
    }

    // Método para registrar el intento de un jugador
    private void registrarIntento() {
        if (jugadorActual == 1) {
            intentosJugador1++;
            lblJugador1.setText("Jugador 1: " + intentosJugador1);
        } else {
            intentosJugador2++;
            lblJugador2.setText("Jugador 2: " + intentosJugador2);
        }
    }

    // Método para activar el jugador actual
    private void activarJugador(int jugador) {
        if (jugador == 1) {
            lblJugador1.setStyle("-fx-font-weight: bold");
            lblJugador2.setStyle("");
        } else {
            lblJugador1.setStyle("");
            lblJugador2.setStyle("-fx-font-weight: bold");
        }
    }
}
*/
/*
    // Resto del código...
private String obtenerSiguienteImagen() {
        // Lógica para obtener la siguiente imagen revelada (puedes ajustarla según tus necesidades)
        // Aquí puedes usar un contador o cualquier otra lógica que prefieras
        // En este ejemplo, se asume que se selecciona aleatoriamente una imagen entre "1.png" y "15.png"
        int indice = (int) (Math.random() * 15) + 1;
        return indice + ".png";
    }
}*/
/*
//ESTE CODIGO YA MARCA LOS INTENTOS DE CADA JUGADOR AL VOLTEAR UN PAR DE IMAGENES
package com.example.demo.vistas;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class memorama extends Stage {

    private Scene escena;
    private Label lblPares, lblJugador1, lblJugador2, lblTiempo, lblScore1, lblScore2;
    private TextField txtPares;
    private GridPane gdpJuego;
    private Button btnJugar;
    private VBox vbxPrincipal, vbxJugadores;
    private HBox hbxTiempo, hbxJuego, hbxJugador1, hbxJugador2;
    private Button[][] arBtnCartas;
    private Timeline timeline;
    private Integer timeSeconds = 0;
    private int jugadorActual = 1;
    private int intentosJugador1 = 0;
    private int intentosJugador2 = 0;
    private int cartasVolteadasJugadorActual = 0;
    private boolean[] parejasVolteadas = new boolean[16]; // Para asegurarnos de que cada par se voltee solo una vez

    public memorama(){
        CrearUI();
        this.setTitle("Memorama :)");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {

        lblPares = new Label("Número de Pares:");
        btnJugar = new Button("Iniciar Juego");
        lblTiempo = new Label("00:00");
        hbxTiempo = new HBox(lblPares, btnJugar, lblTiempo);

        lblJugador1 = new Label("Jugador 1: 0");
        lblScore1 = new Label("0");
        hbxJugador1 = new HBox(lblJugador1, lblScore1);

        lblJugador2 = new Label("Jugador 2: 0");
        lblScore2 = new Label("0");
        hbxJugador2 = new HBox(lblJugador2, lblScore2);

        vbxJugadores = new VBox(hbxJugador1, hbxJugador2);
        gdpJuego = new GridPane();
        RevolverCartas();
        hbxJuego = new HBox(gdpJuego,vbxJugadores);

        vbxPrincipal = new VBox(hbxTiempo, hbxJuego);

        escena = new Scene(vbxPrincipal, 1300, 600);

        // Inicializar el Timeline
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                timeSeconds++;
                // Actualizar el label del tiempo
                lblTiempo.setText(String.format("%02d:%02d", timeSeconds / 60, timeSeconds % 60));
                // Verificar si todas las cartas están volteadas
                if (todasLasCartasVolteadas()) {
                    timeline.stop();
                }
                // Controlar el tiempo de turno de cada jugador
                if (timeSeconds % 5 == 0 && cartasVolteadasJugadorActual < 2) {
                    cambiarTurno();
                }
            }
        }));

        // Iniciar el Timeline cuando se inicia el juego
        btnJugar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                timeline.playFromStart();
                activarJugador(jugadorActual);
            }
        });
    }

    private boolean[][] cartaVolteada;

    private void RevolverCartas() {
        List<String> nombresImagenes = new ArrayList<>();
        for (int i = 1; i <= 15; i++) {
            nombresImagenes.add(i + ".png");
            nombresImagenes.add(i + ".png");
        }
        Collections.shuffle(nombresImagenes);

        arBtnCartas = new Button[3][10];
        cartaVolteada = new boolean[3][10];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 10; j++) {
                int indiceImagen = i * 10 + j;
                String nombreImagen = nombresImagenes.get(indiceImagen);
                Button btnCarta = new Button();
                ImageView imvCarta = new ImageView(
                        getClass().getResource("/images/0.png").toString()
                );
                imvCarta.setFitHeight(150);
                imvCarta.setFitWidth(100);
                btnCarta.setGraphic(imvCarta);
                btnCarta.setPrefSize(100, 150);
                arBtnCartas[i][j] = btnCarta;
                gdpJuego.add(btnCarta, j, i);

                final int fila = i;
                final int columna = j;
                btnCarta.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if (!cartaVolteada[fila][columna]) { // Si la carta no está volteada
                            // Cambiar la imagen de "0.png" a la siguiente imagen revelada
                            ImageView nuevaVista = new ImageView(getClass().getResource("/images/" + nombreImagen).toString());
                            nuevaVista.setFitHeight(150);
                            nuevaVista.setFitWidth(100);
                            btnCarta.setGraphic(nuevaVista);
                            cartaVolteada[fila][columna] = true; // Marcar la carta como volteada
                            cartasVolteadasJugadorActual++;
                            // Verificar si se ha volteado un par
                            if (cartasVolteadasJugadorActual == 2) {
                                registrarIntento();
                                cartasVolteadasJugadorActual = 0; // Reiniciar el contador de cartas volteadas
                            }
                        }
                    }
                });
            }
        }
    }

    // Método para verificar si todas las cartas están volteadas
    private boolean todasLasCartasVolteadas() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 10; j++) {
                if (!cartaVolteada[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // Método para cambiar el turno de los jugadores
    private void cambiarTurno() {
        jugadorActual = (jugadorActual == 1) ? 2 : 1;
        activarJugador(jugadorActual);
    }

    // Método para registrar el intento de un jugador
    private void registrarIntento() {
        if (jugadorActual == 1) {
            intentosJugador1++;
            lblJugador1.setText("Jugador 1: " + intentosJugador1);
        } else {
            intentosJugador2++;
            lblJugador2.setText("Jugador 2: " + intentosJugador2);
        }
    }
// Método para activar el jugador actual
    private void activarJugador(int jugador) {
        if (jugador == 1) {
            lblJugador1.setStyle("-fx-font-weight: bold");
            lblJugador2.setStyle("");
        } else {
            lblJugador1.setStyle("");
            lblJugador2.setStyle("-fx-font-weight: bold");
        }
    }

private String obtenerSiguienteImagen() {
        // Lógica para obtener la siguiente imagen revelada (puedes ajustarla según tus necesidades)
        // Aquí puedes usar un contador o cualquier otra lógica que prefieras
        // En este ejemplo, se asume que se selecciona aleatoriamente una imagen entre "1.png" y "15.png"
        int indice = (int)(Math.random() * 15) + 1;
        return indice + ".png";
    }
}
*/
//````````````````````````````````````````````````````````
/*
//puedes darme el liempo que aparesca en una label con los dijitos 00.00 y de aqui empiecen los segundos
/;;
*/

//ahora ayudame a iniciar el timer en timer de tiempo hasta que finalice el juego osea cuando ya se ahigan descubierto todas
// las imagenes disponibles
/*
package com.example.demo.vistas;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class memorama extends Stage {

    private Scene escena;
    private Label lblPares, lblJugador1, lblJugador2, lblTiempo, lblScore1, lblScore2;
    private TextField txtPares;
    private GridPane gdpJuego;
    private Button btnJugar;
    private VBox vbxPrincipal, vbxJugadores;
    private HBox hbxTiempo, hbxJuego, hbxJugador1, hbxJugador2;
    private Button[][] arBtnCartas;
    private Timeline timeline;
    private Integer timeSeconds = 0;

    public memorama(){
        CrearUI();
        this.setTitle("Memorama :)");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {

        lblPares = new Label("Número de Pares:");
        btnJugar = new Button("Iniciar Juego");
        lblTiempo = new Label("00:00");
        hbxTiempo = new HBox(lblPares, btnJugar, lblTiempo);

        lblJugador1 = new Label("Jugador 1");
        lblScore1 = new Label("0");
        hbxJugador1 = new HBox(lblJugador1, lblScore1);

        lblJugador2 = new Label("Jugador 2");
        lblScore2 = new Label("0");
        hbxJugador2 = new HBox(lblJugador2, lblScore2);

        vbxJugadores = new VBox(hbxJugador1, hbxJugador2);
        gdpJuego = new GridPane();
        RevolverCartas();
        hbxJuego = new HBox(gdpJuego,vbxJugadores);

        vbxPrincipal = new VBox(hbxTiempo, hbxJuego);

        escena = new Scene(vbxPrincipal, 1300, 600);

        // Inicializar el Timeline
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                timeSeconds++;
                // Actualizar el label del tiempo
                lblTiempo.setText(timeSeconds.toString());
                // Verificar si todas las cartas están volteadas
                if (todasLasCartasVolteadas()) {
                    timeline.stop();
                }
            }
        }));

        // Iniciar el Timeline cuando se inicia el juego
        btnJugar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                timeline.playFromStart();
            }
        });
    }

    private boolean[][] cartaVolteada;

    private void RevolverCartas() {
        List<String> nombresImagenes = new ArrayList<>();
        for (int i = 1; i <= 15; i++) {
            nombresImagenes.add(i + ".png");
            nombresImagenes.add(i + ".png");
        }
        Collections.shuffle(nombresImagenes);

        arBtnCartas = new Button[3][10];
        cartaVolteada = new boolean[3][10];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 10; j++) {
                int indiceImagen = i * 10 + j;
                String nombreImagen = nombresImagenes.get(indiceImagen);
                Button btnCarta = new Button();
                ImageView imvCarta = new ImageView(
                        getClass().getResource("/images/0.png").toString()
                );
                imvCarta.setFitHeight(150);
                imvCarta.setFitWidth(100);
                btnCarta.setGraphic(imvCarta);
                btnCarta.setPrefSize(100, 150);
                arBtnCartas[i][j] = btnCarta;
                gdpJuego.add(btnCarta, j, i);

                final int fila = i;
                final int columna = j;
                btnCarta.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if (!cartaVolteada[fila][columna]) { // Si la carta no está volteada
                            // Cambiar la imagen de "0.png" a la siguiente imagen revelada
                            ImageView nuevaVista = new ImageView(getClass().getResource("/images/" + nombreImagen).toString());
                            nuevaVista.setFitHeight(150);
                            nuevaVista.setFitWidth(100);
                            btnCarta.setGraphic(nuevaVista);
                            cartaVolteada[fila][columna] = true; // Marcar la carta como volteada
                        } else {
                            // Voltear la carta nuevamente a "0.png"
                            ImageView nuevaVista = new ImageView(getClass().getResource("/images/0.png").toString());
                            nuevaVista.setFitHeight(150);
                            nuevaVista.setFitWidth(100);
                            btnCarta.setGraphic(nuevaVista);
                            cartaVolteada[fila][columna] = false; // Marcar la carta como no volteada
                        }
                    }
                });
            }
        }
    }

    // Método para verificar si todas las cartas están volteadas
    private boolean todasLasCartasVolteadas() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 10; j++) {
                if (!cartaVolteada[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
    private String obtenerSiguienteImagen() {
        // Lógica para obtener la siguiente imagen revelada (puedes ajustarla según tus necesidades)
        // Aquí puedes usar un contador o cualquier otra lógica que prefieras
        // En este ejemplo, se asume que se selecciona aleatoriamente una imagen entre "1.png" y "15.png"
        int indice = (int)(Math.random() * 15) + 1;
        return indice + ".png";
    }
}
*/

//esta ya voltea 30 imagenes y solo aparecesn 2 pares de las imagenes
/*
package com.example.demo.vistas;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class memorama extends Stage {

    private Scene escena;
    private Label lblPares, lblJugador1, lblJugador2, lblTiempo, lblScore1, lblScore2;
    private TextField txtPares;
    private GridPane gdpJuego;
    private Button btnJugar;
    private VBox vbxPrincipal, vbxJugadores;
    private HBox hbxTiempo, hbxJuego, hbxJugador1, hbxJugador2;
    private Button[][] arBtnCartas; // Declaración del arreglo

    public memorama(){
        CrearUI();
        this.setTitle("Memorama :)");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {

        lblPares = new Label("Número de Pares:");
        btnJugar = new Button("Iniciar Juego");
        lblTiempo = new Label("00:00");
        hbxTiempo = new HBox(lblPares, btnJugar, lblTiempo);

        lblJugador1 = new Label("Jugador 1");
        lblScore1 = new Label("0");
        hbxJugador1 = new HBox(lblJugador1, lblScore1);

        lblJugador2 = new Label("Jugador 2");
        lblScore2 = new Label("0");
        hbxJugador2 = new HBox(lblJugador2, lblScore2);

        vbxJugadores = new VBox(hbxJugador1, hbxJugador2);
        gdpJuego = new GridPane();
        RevolverCartas();
        hbxJuego = new HBox(gdpJuego,vbxJugadores);

        vbxPrincipal = new VBox(hbxTiempo, hbxJuego);

        escena = new Scene(vbxPrincipal, 1300, 600);
    }

    private boolean[][] cartaVolteada; // Matriz para almacenar el estado de cada carta

    private void RevolverCartas() {
        List<String> nombresImagenes = new ArrayList<>();
        for (int i = 1; i <= 15; i++) {
            nombresImagenes.add(i + ".png");
            nombresImagenes.add(i + ".png");
        }
        Collections.shuffle(nombresImagenes);

        arBtnCartas = new Button[3][10];
        cartaVolteada = new boolean[3][10]; // Inicializar la matriz de estado

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 10; j++) {
                int indiceImagen = i * 10 + j;
                String nombreImagen = nombresImagenes.get(indiceImagen);
                Button btnCarta = new Button();
                ImageView imvCarta = new ImageView(
                        getClass().getResource("/images/0.png").toString()
                );
                imvCarta.setFitHeight(150);
                imvCarta.setFitWidth(100);
                btnCarta.setGraphic(imvCarta);
                btnCarta.setPrefSize(100, 150);
                arBtnCartas[i][j] = btnCarta;
                gdpJuego.add(btnCarta, j, i);

                final int fila = i;
                final int columna = j;
                btnCarta.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if (!cartaVolteada[fila][columna]) { // Si la carta no está volteada
                            // Cambiar la imagen de "0.png" a la siguiente imagen revelada
                            ImageView nuevaVista = new ImageView(getClass().getResource("/images/" + nombreImagen).toString());
                            nuevaVista.setFitHeight(150);
                            nuevaVista.setFitWidth(100);
                            btnCarta.setGraphic(nuevaVista);
                            cartaVolteada[fila][columna] = true; // Marcar la carta como volteada
                        } else {
                            // Voltear la carta nuevamente a "0.png"
                            ImageView nuevaVista = new ImageView(getClass().getResource("/images/0.png").toString());
                            nuevaVista.setFitHeight(150);
                            nuevaVista.setFitWidth(100);
                            btnCarta.setGraphic(nuevaVista);
                            cartaVolteada[fila][columna] = false; // Marcar la carta como no volteada
                        }
                    }
                });
            }
        }
    }
    private String obtenerSiguienteImagen() {
        // Lógica para obtener la siguiente imagen revelada (puedes ajustarla según tus necesidades)
        // Aquí puedes usar un contador o cualquier otra lógica que prefieras
        // En este ejemplo, se asume que se selecciona aleatoriamente una imagen entre "1.png" y "15.png"
        int indice = (int)(Math.random() * 15) + 1;
        return indice + ".png";
    }
}
*/

// ESTE YA VOLVEA LAS IMAGENES EN CASO DE QUE NO FUNCIONEN------------------------------
/*
package com.example.demo.vistas;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class memorama extends Stage {

    private Scene escena;
    private Label lblPares, lblJugador1, lblJugador2, lblTiempo, lblScore1, lblScore2;
    private TextField txtPares;
    private GridPane gdpJuego;
    private Button btnJugar;
    private VBox vbxPrincipal, vbxJugadores;
    private HBox hbxTiempo, hbxJuego, hbxJugador1, hbxJugador2;
    private Button[][] arBtnCartas; // Declaración del arreglo

    public memorama(){
        CrearUI();
        this.setTitle("Memorama :)");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {

        lblPares = new Label("Número de Pares:");
        btnJugar = new Button("Iniciar Juego");
        lblTiempo = new Label("00:00");
        hbxTiempo = new HBox(lblPares, btnJugar, lblTiempo);

        lblJugador1 = new Label("Jugador 1");
        lblScore1 = new Label("0");
        hbxJugador1 = new HBox(lblJugador1, lblScore1);

        lblJugador2 = new Label("Jugador 2");
        lblScore2 = new Label("0");
        hbxJugador2 = new HBox(lblJugador2, lblScore2);

        vbxJugadores = new VBox(hbxJugador1, hbxJugador2);
        gdpJuego = new GridPane();
        RevolverCartas();
        hbxJuego = new HBox(gdpJuego,vbxJugadores);

        vbxPrincipal = new VBox(hbxTiempo, hbxJuego);

        escena = new Scene(vbxPrincipal, 400, 300);
    }

    private void RevolverCartas() {
        List<String> nombresImagenes = new ArrayList<>();
        for (int i = 0; i <= 15; i++) {
            nombresImagenes.add(i + ".png");
        }
        Collections.shuffle(nombresImagenes);

        arBtnCartas = new Button[3][5]; // Inicialización del arreglo

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                int indiceImagen = i * 5 + j;
                String nombreImagen = nombresImagenes.get(indiceImagen);
                Button btnCarta = new Button();
                ImageView imvCarta = new ImageView(
                        getClass().getResource("/images/0.png").toString()
                );
                imvCarta.setFitHeight(150);
                imvCarta.setFitWidth(100);
                btnCarta.setGraphic(imvCarta);
                btnCarta.setPrefSize(100, 150);
                arBtnCartas[i][j] = btnCarta; // Asignación al arreglo
                gdpJuego.add(btnCarta, j, i);

                final int fila = i;
                final int columna = j;
                btnCarta.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Image imagenActual = ((ImageView) btnCarta.getGraphic()).getImage();
                        if (imagenActual.getUrl().contains("0.png")) {
                            // Cambiar la imagen de "0.png" a la siguiente imagen revelada
                            String nuevaImagen = obtenerSiguienteImagen();
                            ImageView nuevaVista = new ImageView(getClass().getResource("/images/" + nuevaImagen).toString());
                            nuevaVista.setFitHeight(150);
                            nuevaVista.setFitWidth(100);
                            btnCarta.setGraphic(nuevaVista);
                        } else {
                            // Voltear la carta nuevamente a "0.png"
                            ImageView nuevaVista = new ImageView(getClass().getResource("/images/0.png").toString());
                            nuevaVista.setFitHeight(150);
                            nuevaVista.setFitWidth(100);
                            btnCarta.setGraphic(nuevaVista);
                        }
                    }
                });
            }
        }
    }

    private String obtenerSiguienteImagen() {
        // Lógica para obtener la siguiente imagen revelada (puedes ajustarla según tus necesidades)
        // Aquí puedes usar un contador o cualquier otra lógica que prefieras
        // En este ejemplo, se asume que se selecciona aleatoriamente una imagen entre "1.png" y "15.png"
        int indice = (int)(Math.random() * 15) + 1;
        return indice + ".png";
    }
}
*/


//15 imagenes original
/*package com.example.demo.vistas;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class memorama extends Stage {

    private Scene escena;
    private Label lblPares, lblJugador1, lblJugador2, lblTiempo, lblScore1, lblScore2;
    private TextField txtPares;
    private GridPane gdpJuego;
    private Button btnJugar;
    private VBox vbxPrincipal, vbxJugadores;
    private HBox hbxTiempo, hbxJuego, hbxJugador1, hbxJugador2;

    public memorama(){
        CrearUI();
        this.setTitle("Memorama :)");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {

        lblPares = new Label("Número de Pares:");
        btnJugar = new Button("Inicar Juego");
        lblTiempo = new Label("00:00");
        hbxTiempo = new HBox(lblPares, btnJugar, lblTiempo);

        lblJugador1 = new Label("Jugador 1");
        lblScore1 = new Label("0");
        hbxJugador1 = new HBox(lblJugador1, lblScore1);

        lblJugador2 = new Label("Jugador 2");
        lblScore2 = new Label("0");
        hbxJugador2 = new HBox(lblJugador2, lblScore2);

        vbxJugadores = new VBox(hbxJugador1, hbxJugador2);
        gdpJuego = new GridPane();
        RevolverCartas();
        hbxJuego = new HBox(gdpJuego,vbxJugadores);

        vbxPrincipal = new VBox(hbxTiempo, hbxJuego);

        escena = new Scene(vbxPrincipal, 400, 300);
    }

    private void RevolverCartas() {
     String[] arImagenes = {
                "0.png", "1.png", "2.png", "3.png", "4.png",
                "5.png", "6.png", "7.png", "8.png", "9.png",
                "10.png", "11.png", "12.png", "13.png", "14.png"
        };

        Button[][] arBtnCartas = new Button[3][5]; // Cambiado a 3x5 para 15 imágenes

        ImageView imvCarta;
        int cont = 0;

        for (int i = 0; i < arImagenes.length; i++) {
            int posx, posy;
            do {
                posx = (int)(Math.random() * 3);
                posy = (int)(Math.random() * 5);
            } while (arBtnCartas[posx][posy] != null);

            arBtnCartas[posx][posy] = new Button();
            imvCarta = new ImageView(
                    getClass().getResource("/images/" + arImagenes[i]).toString()
            );
            imvCarta.setFitHeight(150);
            imvCarta.setFitWidth(100);
            arBtnCartas[posx][posy].setGraphic(imvCarta);
            arBtnCartas[posx][posy].setPrefSize(100, 150);
            gdpJuego.add(arBtnCartas[posx][posy], posy, posx);

            cont++;
        }
    }

}*/
/*
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;

public class memorama extends Stage {

    private Scene escena;
    private Label lblTimer;
    private Timeline timeline;
    private int segundos = 0;

    private GridPane gdpJuego;

    public memorama() {
        CrearUI();
        this.setTitle("Memorama :)");
        this.setScene(escena);
        this.show();

        iniciarTemporizador();
    }

    private void CrearUI() {
        lblTimer = new Label("Tiempo: 0 segundos");

        GridPane gridPaneImagenes = new GridPane();
        gridPaneImagenes.setHgap(10);
        gridPaneImagenes.setVgap(10);

        gdpJuego = new GridPane();
        gdpJuego.setHgap(10);
        gdpJuego.setVgap(10);

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 5; j++) {
                Image imagen = new Image("file:path_to_your_image/image" + (i * 5 + j) + ".jpg");
                ImageView imageView = new ImageView(imagen);
                imageView.setFitWidth(100);
                imageView.setFitHeight(100);
                Button button = new Button();
                button.setGraphic(imageView);
                button.setPrefSize(100, 100);
                gdpJuego.add(button, j, i);
            }
        }

        VBox vBoxMain = new VBox(lblTimer, gdpJuego);
        escena = new Scene(vBoxMain, 800, 400);
    }

    private void iniciarTemporizador() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            segundos++;
            lblTimer.setText("Tiempo: " + segundos + " segundos");
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
}
*/

