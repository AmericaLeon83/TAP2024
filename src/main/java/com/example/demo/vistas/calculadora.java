//Leon Rodriguez America Patricia
package com.example.demo.vistas;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class calculadora extends Stage {
    private Scene escena;
    private VBox vContenedor;
    private GridPane gdpTeclado;
    private TextField txtPantalla;
    private Button[][] arBotones = new Button[5][4];
    private char[] arEtiquetas = {'7', '8', '9', '/', '4', '5', '6', '*', '1', '2', '3', '-', '0', '.', '=', '+'};
    private String contenidoPantalla = "";
    private boolean despuesIgual = false;
    private boolean esNeg = false;
    private boolean tDecimal = false;
    private double pNum = 0, res = 0 ,sNum = 0;
    private String operaciones = "";

    public calculadora() {
        CrearUI();
        this.setTitle("Mi primer Calculadora :)");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        txtPantalla = new TextField("0");

        // Crear el botón de borrar
        Button btnBorrar = new Button("C");
        btnBorrar.setOnAction(event -> borrarPantalla());

        gdpTeclado = new GridPane();
        CrearTeclado();
        vContenedor = new VBox(txtPantalla, btnBorrar, gdpTeclado); // Agregar btnBorrar aquí
        vContenedor.setSpacing(5);
        escena = new Scene(vContenedor, 200, 200);
        escena.getStylesheets().add(getClass().getResource("/Estilos/Calculadora.css").toString());
    }

    private void CrearTeclado() {
        int pos = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                arBotones[i][j] = new Button(arEtiquetas[pos] + "");
                arBotones[i][j].setPrefSize(50, 50);
                int finalPos = pos;
                arBotones[i][j].setOnAction(event -> setValue(arEtiquetas[finalPos]));
                gdpTeclado.add(arBotones[i][j], j, i);
                if (arEtiquetas[pos] == '+' || arEtiquetas[pos] == '-' || arEtiquetas[pos] == '*' || arEtiquetas[pos] == '/') {
                    arBotones[i][j].setId("color-operador");
                }
                pos++;
            }
        }
    }
    private void setValue(char symbol) {
        if (txtPantalla.getText().equals("Error")) {
            resetearCalculadora();
        }
        if (despuesIgual && Character.isDigit(symbol) || symbol == '.') {
            resetearDespuesDeIgual();
        }

        if (esDespuesDeIgual(symbol)) {
            resetearDespuesDeIgual();
        }
        if (esDigito(symbol)) {
            manejarEntradaDigito(symbol);
        } else if (esMenos(symbol)) {
            manejarEntradaMenos();
        } else if (esOperador(symbol)) {
            manejarEntradaOperador(symbol);
        } else if (esIgual(symbol)) {
            manejarEntradaIgual();
        }
    }


    private boolean esDespuesDeIgual(char symbol) {
        return despuesIgual && Character.isDigit(symbol) || symbol == '.';
    }

    private boolean esDigito(char symbol) {
        return Character.isDigit(symbol) || symbol == '.';
    }

    private boolean esMenos(char symbol) {
        return symbol == '-';
    }

    private boolean esOperador(char symbol) {
        return "+-*/".indexOf(symbol) >= 0;
    }

    private boolean esIgual(char symbol) {
        return symbol == '=';
    }

    private void resetearCalculadora() {
        txtPantalla.setText("0");
        contenidoPantalla = "";
        pNum = 0;
        sNum = 0;
        operaciones = "";
        tDecimal = false;
        despuesIgual = false;
    }

    private void resetearDespuesDeIgual() {
        txtPantalla.setText("0");
        contenidoPantalla = "";
        pNum = res;
        operaciones = "";
        tDecimal = false;
        despuesIgual = false;
    }

    private void manejarEntradaDigito(char symbol) {
        if (symbol == '.' && tDecimal) {
            txtPantalla.setText("Error");
            return;
        }

        if (symbol == '.') {
            tDecimal = true;
        }

        if (contenidoPantalla.isEmpty() && symbol == '.') {
            contenidoPantalla = "0.";
        } else {
            contenidoPantalla += symbol;
        }

        txtPantalla.setText(contenidoPantalla);
    }
    private void manejarEntradaMenos() {
        if (contenidoPantalla.isEmpty()) {
            esNeg = !esNeg;
            contenidoPantalla = esNeg ? "-" : "";
            pNum = 0;
        } else {
            pNum = Double.parseDouble(contenidoPantalla);
            operaciones = "-";
            contenidoPantalla = "";
            esNeg = false;
        }
    }

    private void manejarEntradaOperador(char symbol) {
        if (contenidoPantalla.isEmpty()) {
            if ("+-*/".indexOf(symbol) >= 0) {
                operaciones = symbol + "";
            } else if (despuesIgual) {
                pNum = res;
                operaciones = symbol + "";
                despuesIgual = false;
            } else {
                txtPantalla.setText("Error");
            }
        } else {
            pNum = Double.parseDouble(contenidoPantalla);
            operaciones = symbol + "";
            contenidoPantalla = "";
            tDecimal = false;
        }
    }

    private void manejarEntradaIgual() {
        if (contenidoPantalla.isEmpty() || operaciones.isEmpty()) {
            txtPantalla.setText("Error");
            return;
        }
        sNum = Double.parseDouble(contenidoPantalla);
        Calcular();
        txtPantalla.setText(txtPantalla.getText().equals("Error") ? "Error" : String.valueOf(res));
        contenidoPantalla = "";
        operaciones = "";
        tDecimal = false;
        despuesIgual = true;
    }

    private void Calcular() {
        if (esError()) {
            return;
        }
        if (esDivisionPorCero()) {
            return;
        }
        if (esDivisionCeroPorCero()) {
            return;
        }
        realizarOperacion();
        mostrarResultado();
    }

    private boolean esError() {
        return "Error".equals(txtPantalla.getText());
    }

    private boolean esDivisionPorCero() {
        if ("/".equals(operaciones) && sNum == 0) {
            txtPantalla.setText("Error");
            return true;
        }
        return false;
    }

    private boolean esDivisionCeroPorCero() {
        if ("/".equals(operaciones) && pNum == 0) {
            res = 0;
            txtPantalla.setText("0");
            return true;
        }
        return false;
    }

    private void realizarOperacion() {
        switch (operaciones) {
            case "+":
                res = pNum + sNum;
                break;
            case "-":
                res = pNum - sNum;
                break;
            case "*":
                res = pNum * sNum;
                break;
            case "/":
                res = pNum / sNum;
                break;
            default:
                txtPantalla.setText("Error");
                return;
        }
    }
    private void mostrarResultado() {
        if (res % 1 == 0) {
            txtPantalla.setText(String.format("%.0f", res));
        } else {
            txtPantalla.setText(String.valueOf(res));
        }
    }
    private void borrarPantalla() {
        txtPantalla.setText("0");
        contenidoPantalla = "";
        pNum = 0;
        sNum = 0;
        operaciones = "";
        tDecimal = false;
        despuesIgual = false;
        esNeg = false;
    }
}
