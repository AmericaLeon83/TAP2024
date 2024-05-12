//PENDIENTE

package com.example.demo.vistas;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.scene.control.Alert.AlertType;

public class memorama2 extends Stage {

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
    private boolean[][] cartaVolteada;
    private boolean[] parejasVolteadas = new boolean[30]; // Para asegurarnos de que cada par se voltee solo una vez
    private boolean[][] parejasEncontradas; // Nueva variable para rastrear las parejas encontradas
    private TextField txtNumPares; // Nuevo TextField para el número de pares
    private int paresEncontradosJugador1 = 0;
    private int paresEncontradosJugador2 = 0;
    private List<String> nombresImagenes; // Lista para almacenar los nombres de las imágenes
    private boolean primerTurnoJugador2 = true; // Variable para rastrear el primer turno del jugador 2
    // Agrega una variable para contar el número total de pares encontrados
    private int totalParesEncontrados = 0;
    public memorama2() {
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
        parejasEncontradas = new boolean[3][15]; // Inicializa parejasEncontradas en el mismo lugar donde inicializas cartaVolteada antes 10

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

            totalParesEncontrados++;

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
            if (totalParesEncontrados == nombresImagenes.size() / 2) {
            // Verificar si todas las parejas han sido encontradas
            //if (todasLasParejasEncontradas()) {
                // Detener el temporizador total del juego
                timeline.stop();

                // Mostrar el ganador
                mostrarGanador();
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
//----------------
private void mostrarGanador() {
    // Detener el temporizador total
    timeline.stop();

    // Crear un mensaje modal
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Fin del juego");
    alert.setHeaderText(null);

    // Determinar el ganador
    if (paresEncontradosJugador1 > paresEncontradosJugador2) {
        alert.setContentText("¡El Jugador 1 ha ganado con " + paresEncontradosJugador1 + " pares!");
    } else if (paresEncontradosJugador1 < paresEncontradosJugador2) {
        alert.setContentText("¡El Jugador 2 ha ganado con " + paresEncontradosJugador2 + " pares!");
    } else {
        alert.setContentText("¡Es un empate!");
    }

    // Mostrar el mensaje modal
    alert.showAndWait();
}

    //---------------------

    // Método para verificar si todas las parejas han sido encontradas
    private boolean todasLasParejasEncontradas() {
        for (boolean[] fila : parejasEncontradas) {
            for (boolean parejaEncontrada : fila) {
                if (!parejaEncontrada) {
                    return false; // Devuelve falso si hay al menos una pareja que no ha sido encontrada
                }
            }
        }
        return totalParesEncontrados == nombresImagenes.size() / 2;
       // return true; // Devuelve verdadero si todas las parejas han sido encontradas
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
    // Método para activar el jugador actual
    private void activarJugador(int jugador) {
        if (jugador == 1) {
            lblJugador1.setStyle("-fx-text-fill: green; -fx-font-weight: bold; -fx-font-size: 20px; -fx-font-family: 'Arial';");
            lblJugador2.setStyle("-fx-text-fill: red; -fx-font-weight: normal; -fx-font-size: 16px; -fx-font-family: 'Arial';");
        } else {
            lblJugador2.setStyle("-fx-text-fill: green; -fx-font-weight: bold; -fx-font-size: 20px; -fx-font-family: 'Arial';");
            lblJugador1.setStyle("-fx-text-fill: red; -fx-font-weight: normal; -fx-font-size: 16px; -fx-font-family: 'Arial';");
        }
    }
    /*private void activarJugador(int jugador) {
        if (jugador == 1) {
            lblJugador1.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
            lblJugador2.setStyle("-fx-text-fill: red;");
        } else {
            lblJugador2.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
            lblJugador1.setStyle("-fx-text-fill: red;");
        }
    }*/


}


/*package com.example.demo.vistas;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class memorama2 extends Stage {

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
    public memorama2() {
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
                mostrarMensajeGanador();
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


    private boolean todasLasCartasBloqueadas() {
        for (int i = 0; i < arBtnCartas.length; i++) {
            for (int j = 0; j < arBtnCartas[i].length; j++) {
                if (!arBtnCartas[i][j].isDisable()) {
                    return false; // Si hay al menos una carta no bloqueada, retornar falso
                }
            }
        }
        return true; // Si todas las cartas están bloqueadas, retornar verdadero
    }

    private void mostrarMensajeGanador() {
        // Verificar quién ganó basado en los puntos acumulados por cada jugador
        if (paresEncontradosJugador1 > paresEncontradosJugador2) {
            mostrarMensajeGanador("Jugador 1");
        } else if (paresEncontradosJugador2 > paresEncontradosJugador1) {
            mostrarMensajeGanador("Jugador 2");
        } else {
            // mostrarMensajeEmpate();
        }
    }

    private void mostrarMensajeGanador(String jugador) {
        Stage ventanaMensaje = new Stage();
        ventanaMensaje.initModality(Modality.APPLICATION_MODAL);
        ventanaMensaje.setTitle("¡Felicidades!");
        Label mensaje = new Label("¡Felicidades, " + jugador + ", has ganado!");
        Button botonCerrar = new Button("Cerrar");
        botonCerrar.setOnAction(e -> ventanaMensaje.close());
        VBox layout = new VBox(10);
        layout.getChildren().addAll(mensaje, botonCerrar);
        layout.setAlignment(Pos.CENTER);
        Scene escena = new Scene(layout, 300, 150);
        ventanaMensaje.setScene(escena);
        ventanaMensaje.showAndWait();
    }
    private boolean todasLasParejasEncontradas() {
        for (int i = 0; i < parejasEncontradas.length; i++) {
            for (int j = 0; j < parejasEncontradas[i].length; j++) {
                if (!parejasEncontradas[i][j]) {
                    return false; // Si hay al menos una pareja no encontrada, retornar falso
                }
            }
        }
        return true; // Si todas las parejas están encontradas, retornar verdadero
    }

    // |||||||||||||||||||||||||||||||||| SE VE ACEPTABLE PARA ENTREGAR |||||||||||||||||||||||||||||||||||||
    /*private void comprobarPares() {
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

            // Verificar si todas las cartas están bloqueadas
            if (todasLasCartasBloqueadas()) {
                // Detener el temporizador total del juego
                timeline.stop();
                // Mostrar un mensaje de fin de juego
                mostrarMensajeGanador();
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

    private boolean todasLasCartasBloqueadas() {
        for (int i = 0; i < arBtnCartas.length; i++) {
            for (int j = 0; j < arBtnCartas[i].length; j++) {
                if (!arBtnCartas[i][j].isDisable()) {
                    return false; // Si hay al menos una carta no bloqueada, retornar falso
                }
            }
        }
        return true; // Si todas las cartas están bloqueadas, retornar verdadero
    }

    private void mostrarMensajeGanador() {
        // Verificar quién ganó basado en los puntos acumulados por cada jugador
        if (paresEncontradosJugador1 > paresEncontradosJugador2) {
            mostrarMensajeGanador("Jugador 1");
        } else if (paresEncontradosJugador2 > paresEncontradosJugador1) {
            mostrarMensajeGanador("Jugador 2");
        } else {
            // mostrarMensajeEmpate();
        }
    }

    private void mostrarMensajeGanador(String jugador) {
        Stage ventanaMensaje = new Stage();
        ventanaMensaje.initModality(Modality.APPLICATION_MODAL);
        ventanaMensaje.setTitle("¡Felicidades!");
        Label mensaje = new Label("¡Felicidades, " + jugador + ", has ganado!");
        Button botonCerrar = new Button("Cerrar");
        botonCerrar.setOnAction(e -> ventanaMensaje.close());
        VBox layout = new VBox(10);
        layout.getChildren().addAll(mensaje, botonCerrar);
        layout.setAlignment(Pos.CENTER);
        Scene escena = new Scene(layout, 300, 150);
        ventanaMensaje.setScene(escena);
        ventanaMensaje.showAndWait();
    }
*/


    //   |||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
   /* private void comprobarPares() {
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
                mostrarMensajeGanador();
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

    private boolean todasLasParejasEncontradas() {
        for (int i = 0; i < parejasEncontradas.length; i++) {
            for (int j = 0; j < parejasEncontradas[i].length; j++) {
                if (!parejasEncontradas[i][j]) {
                    return false; // Si hay al menos una pareja no encontrada, retornar falso
                }
            }
        }
        return true; // Si todas las parejas están encontradas, retornar verdadero
    }

    private void mostrarMensajeGanador() {
        String ganador;
        if (paresEncontradosJugador1 > paresEncontradosJugador2) {
            ganador = "Jugador 1";
        } else if (paresEncontradosJugador2 > paresEncontradosJugador1) {
            ganador = "Jugador 2";
        } else {
            ganador = "¡Empate!";
        }
        Platform.runLater(() -> {
            Stage ventanaMensaje = new Stage();
            ventanaMensaje.initModality(Modality.APPLICATION_MODAL);
            ventanaMensaje.setTitle("¡Felicidades!");
            Label mensaje = new Label("¡Felicidades, " + ganador + ", has ganado!");
            Button botonCerrar = new Button("Cerrar");
            botonCerrar.setOnAction(e -> {
                ventanaMensaje.close();
                // Aquí puedes reiniciar el juego si lo deseas
            });
            VBox layout = new VBox(10);
            layout.getChildren().addAll(mensaje, botonCerrar);
            layout.setAlignment(Pos.CENTER);
            Scene escena = new Scene(layout, 300, 150);
            ventanaMensaje.setScene(escena);
            ventanaMensaje.showAndWait();
        });
    }*/

 /*   // Método para comprobar si se han formado pares
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
    }*/

    // Método para voltear las cartas que forman una pareja
  /*  private void voltearParejaCartas(int carta1, int carta2) {
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


*/






























//|||||||| LO QUE LLEVO QUE VA CORRECTO \\\\\\\\\\\\\\\\\\
/* package com.example.demo.vistas;

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

public class memorama2 extends Stage {

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
    public memorama2() {
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
*/
//|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
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
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class memorama2 extends Stage {

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
    public memorama2() {
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

                // Voltear las cartas que forman la pareja
                voltearParejaCartas(carta1, carta2);

                // Desactivar los botones correspondientes para que no se puedan voltear nuevamente
                arBtnCartas[carta1 / arBtnCartas[0].length][carta1 % arBtnCartas[0].length].setDisable(true);
                arBtnCartas[carta2 / arBtnCartas[0].length][carta2 % arBtnCartas[0].length].setDisable(true);
            }

            // Marcar las cartas como ya encontradas
            cartaVolteada[carta1 / arBtnCartas[0].length][carta1 % arBtnCartas[0].length] = true;
            cartaVolteada[carta2 / arBtnCartas[0].length][carta2 % arBtnCartas[0].length] = true;
        } else {
            // Si las imágenes no son iguales, voltear las cartas nuevamente después de un breve tiempo
            voltearCartasNoEncontradas();
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
*/
/*
AQUI EL UNICO PROBLEMA ESQUE NO SE REGISTRA
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

public class memorama2 extends Stage {

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
    public memorama2() {
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
            parejasVolteadas[carta1] = true; // Marcar la pareja como encontrada
            parejasVolteadas[carta2] = true; // Marcar la pareja como encontrada

            // Voltear las cartas que forman la pareja
            voltearParejaCartas(carta1, carta2);
        } else {
            // Si las imágenes no son iguales, voltear las cartas nuevamente después de un breve tiempo
            voltearCartasNoEncontradas();
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
    }



    // Método para verificar si todas las parejas han sido encontradas
    private boolean todasLasParejasEncontradas() {
        for (boolean parejaEncontrada : parejasVolteadas) {
            if (!parejaEncontrada) {
                return false; // Devuelve falso si hay al menos una pareja que no ha sido encontrada
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

        // Voltear la primera carta
        ImageView imageView1 = new ImageView(getClass().getResource("/images/" + nombresImagenes.get(carta1)).toString());
        imageView1.setFitWidth(100);
        imageView1.setFitHeight(150);
        arBtnCartas[fila1][columna1].setGraphic(imageView1);

        // Voltear la segunda carta
        ImageView imageView2 = new ImageView(getClass().getResource("/images/" + nombresImagenes.get(carta2)).toString());
        imageView2.setFitWidth(100);
        imageView2.setFitHeight(150);
        arBtnCartas[fila2][columna2].setGraphic(imageView2);
    }

    // Método para desactivar una carta después de que se encuentre un par
    private void desactivarCarta(Button carta) {
        carta.setDisable(true);
    }

    // Método para cambiar la imagen de una carta para mostrarla volteada
    private void cambiarImagenCartaVolteada(Button btnCarta, String nombreImagen) {
        ImageView nuevaVista = new ImageView(getClass().getResource("/images/" + nombreImagen).toString());
        nuevaVista.setFitHeight(150);
        nuevaVista.setFitWidth(100);
        btnCarta.setGraphic(nuevaVista);
    }

    // Método para registrar el punto para el jugador actual
    private void registrarPuntoParaJugadorActual() {
        int puntos;
        if (jugadorActual == 1) {
            puntos = Integer.parseInt(lblScore1.getText()) + 1; // Incrementar los puntos del jugador 1
            lblScore1.setText(Integer.toString(puntos)); // Actualizar el label de puntos del jugador 1
        } else {
            puntos = Integer.parseInt(lblScore2.getText()) + 1; // Incrementar los puntos del jugador 2
            lblScore2.setText(Integer.toString(puntos)); // Actualizar el label de puntos del jugador 2
        }
    }

    // Método para voltear las cartas no encontradas
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
*/

/* AQUI ME QUEDE EN EL OTRO CHAT SI SE QUEDAN
 LAS IMAGENES ARRIBA CUANDO SE ENCUENTRA PAR Y SI SE REGISTRA PUNTO PERO SOLO EN EL
  PRIMER JUGADOR Y EN EL PRIMER TURNO
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

public class memorama2 extends Stage {

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

    public memorama2() {
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

    // Método para comprobar si se han formado pares
    private void comprobarPares() {
        // Obtener las posiciones de las cartas seleccionadas por el jugador actual
        int[] cartasSeleccionadas = obtenerCartasSeleccionadas();
        int carta1 = cartasSeleccionadas[0];
        int carta2 = cartasSeleccionadas[1];

        // Obtener los nombres de las imágenes de las cartas seleccionadas
        String imagenCarta1 = nombresImagenes.get(carta1);
        String imagenCarta2 = nombresImagenes.get(carta2);

        // Verificar si las cartas forman un par y si ese par no ha sido encontrado previamente
        if (imagenCarta1.equals(imagenCarta2) && !parejasEncontradas[carta1 / 10][carta1 % 10]) {
            // Marcar la pareja como encontrada
            parejasEncontradas[carta1 / 10][carta1 % 10] = true;
            parejasEncontradas[carta2 / 10][carta2 % 10] = true;

            // Incrementar el contador de pares del jugador actual
            if (jugadorActual == 1) {
                paresEncontradosJugador1++;
                lblJugador1.setText("Jugador 1: " + paresEncontradosJugador1 + " Pares");
                lblPuntosValor1.setText(Integer.toString(paresEncontradosJugador1));
            } else {
                paresEncontradosJugador2++;
                lblJugador2.setText("Jugador 2: " + paresEncontradosJugador2 + " Pares");
                lblPuntosValor2.setText(Integer.toString(paresEncontradosJugador2));
            }

            // Incrementar los puntos del jugador actual
            int puntos = jugadorActual == 1 ? Integer.parseInt(lblScore1.getText()) + 1 : Integer.parseInt(lblScore2.getText()) + 1;
            if (jugadorActual == 1) {
                lblScore1.setText(Integer.toString(puntos));
            } else {
                lblScore2.setText(Integer.toString(puntos));
            }

            // Deshabilitar los botones de las cartas que formaron el par
            arBtnCartas[carta1 / 10][carta1 % 10].setDisable(true);
            arBtnCartas[carta2 / 10][carta2 % 10].setDisable(true);
        } else {
            // Si las imágenes no son iguales o si el par ya ha sido encontrado, voltear las cartas nuevamente
            voltearCartasNoEncontradas();
        }
    }

    // Método para voltear las cartas no encontradas
    private void voltearCartasNoEncontradas() {
        // Crear un nuevo Timeline para voltear las cartas no encontradas después de un breve tiempo
        Timeline voltearCartasTimeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
            // Voltear todas las cartas
            for (int i = 0; i < arBtnCartas.length; i++) {
                for (int j = 0; j < arBtnCartas[i].length; j++) {
                    if (cartaVolteada[i][j] && !parejasVolteadas[i * 10 + j]) {
                        cartaVolteada[i][j] = false;
                        ImageView imageView = new ImageView(getClass().getResource("/images/0.png").toString());
                        imageView.setFitWidth(100);
                        imageView.setFitHeight(150);
                        arBtnCartas[i][j].setGraphic(imageView);
                    }
                }
            }

            // Actualizar la lógica de cambio de turno para mantener la consistencia
            cambiarTurno();
            activarJugador(jugadorActual);
            iniciarTemporizadorTurno();
        }));
        voltearCartasTimeline.setCycleCount(1);
        voltearCartasTimeline.play();
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
    }

    // Método para detener el temporizador del turno actual
    private void detenerTemporizador() {
        timelineVolteo.stop(); // Detiene el Timeline del tiempo de volteo de las cartas
    }

    // Método para activar el turno de un jugador
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
}*/



/* A   QUI YA SE REGISTRA EL PUNTO DE PAR ENCONTRADO

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

public class memorama2 extends Stage {

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

    public memorama2() {
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
            parejasVolteadas[carta1] = true; // Marcar la pareja como encontrada
            parejasVolteadas[carta2] = true; // Marcar la pareja como encontrada
        } else {
            // Si las imágenes no son iguales, voltear las cartas nuevamente después de un breve tiempo
            voltearCartasNoEncontradas();
        }
    }

    // Método para voltear las cartas no encontradas
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
    }

    // Método para detener el temporizador del turno actual
    private void detenerTemporizador() {
        timelineVolteo.stop(); // Detiene el Timeline del tiempo de volteo de las cartas
    }

    // Método para activar el turno de un jugador
    private void activarJugador(int jugador) {
        lblJugador1.setStyle("-fx-text-fill: black"); // Restaura el color del texto del jugador 1
        lblJugador2.setStyle("-fx-text-fill: black"); // Restaura el color del texto del jugador 2
        if (jugador == 1) {
            lblJugador1.setStyle("-fx-text-fill: red"); // Cambia el color del texto del jugador 1 para indicar su turno
        } else {
            lblJugador2.setStyle("-fx-text-fill: red"); // Cambia el color del texto del jugador 2 para indicar su turno
        }
    }

    public static void main(String[] args) {
        new memorama2();
    }
}

*/


/*
//\\\\\\\\\ ESTE ES EL ORIGINAL ACTUAL CON ESTOS CAMBIOS /////////////
//TODAS LAS CARTAS VOLTEAN Y VUELVEN A SU POSICION ACTUAL
//LOS PARES SE MUESTRAN CORRECTAMENTE
//YA SE ASIGNA EL NUMERO DE LOS PARES QUE QUIERO
//     -NO SE AJUSTA LA PANTALLA AL TAMANIO DE LOS PARES EN PANTALLA
//     -EL TEXTFIEL LO QUIERO ARRIBA Y NO ABAJO
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

public class memorama2 extends Stage {

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

    public memorama2() {
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


/*   ||||||||||||||||||||||||||||||||||ORIGINAL||||||||||||||||||||||||||||||||||||||||||||||||
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

public class memorama2 extends Stage {

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

    public memorama2() {
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
