import java.util.Scanner;

public class Conecta4 {
    private char[][] tablero;
    int filas;
    int columnas;
    private int fichasAConectar;
    private char jugadorActual;
    private boolean gameOver;

    public Conecta4(char[][] tablero, int fichasAConectar) {
        this.tablero = tablero;
        this.filas = tablero.length;
        this.columnas = tablero[0].length;
        this.fichasAConectar = fichasAConectar;
        this.jugadorActual = 'X';
        this.gameOver = false;
    }

    public void jugar() {
        Scanner scanner = new Scanner(System.in);

        while (!this.gameOver) {
            verTablero();
            System.out.println("Es el turno del Jugador " + this.jugadorActual);
            System.out.print("Ingresa la columna: ");

            int columna = scanner.nextInt() - 1;

            if (movimientoValido(columna)) {
                colocarFicha(columna);
                comprobarGanador();
                if (!this.gameOver) {
                    cambiarJugador();
                }
            } else {
                System.out.println("Movimiento no válido. Intenta de nuevo.");
            }
        }

        verTablero();
        System.out.println("¡Juego terminado! El Jugador " + this.jugadorActual + " es el ganador.");
    }

    public void verTablero() {
        /*
        String separador = "";
        switch (this.columnas) {
            case 8:
                separador = "\n------------------------------------";
                break;
            case 9:
                separador = "\n----------------------------------------";
                break;
            case 10:
                separador = "\n---------------------------------------------";
                break;
            default:
                separador = "\n--------------------------------";
        }
        */
        System.out.print("|");
        for (int i = 0; i < this.columnas; i++) {
            System.out.print(" " + (i+1) + " |");
        }
        System.out.println();

        //System.out.println(separador);
        for (int i = 0; i < 1 + this.columnas * 4; i++) {
            System.out.print("-");
        }
        System.out.println();

        for (int i = 0; i < filas; i++) {
            System.out.print("|");
            for (int j = 0; j < this.columnas; j++) {
                System.out.print(" " + tablero[i][j] + " ");
                System.out.print("|");
            }
            System.out.println();

            //System.out.println(separador);
            for (int j = 0; j < 1 + this.columnas * 4; j++) {
                System.out.print("-");
            }
            System.out.println();
        }
    }

    private boolean movimientoValido(int columna) {
        return columna >= 0 && columna < this.columnas && this.tablero[0][columna] == ' ';
    }

    private void colocarFicha(int columna) {
        for (int i = this.filas - 1; i >= 0; i--) {
            if (this.tablero[i][columna] == ' ') {
                this.tablero[i][columna] = this.jugadorActual;
                break;
            }
        }
    }

    private void cambiarJugador() {
        this.jugadorActual = (this.jugadorActual == 'X') ? 'O' : 'X';
    }

    private void comprobarGanador() {
        for (int row = 0; row < this.filas; row++) {
            for (int col = 0; col < this.columnas - (this.fichasAConectar - 1); col++) {
                switch (this.fichasAConectar) {
                    case 4:
                        if (comprobar4Fichas(tablero[row][col], tablero[row][col + 1], tablero[row][col + 2], tablero[row][col + 3])) {
                            gameOver = true;
                            return;
                        }
                        //gameOver = tablero[row][col] != ' ' && tablero[row][col] == tablero[row][col + 1] && tablero[row][col + 1] == tablero[row][col + 2] && tablero[row][col + 2] == tablero[row][col + 3];
                        break;
                    case 5:
                        if (comprobar5Fichas(tablero[row][col], tablero[row][col + 1], tablero[row][col + 2], tablero[row][col + 3], tablero[row][col + 4])) {
                            gameOver = true;
                            return;
                        }
                        //gameOver = tablero[row][col] != ' ' && tablero[row][col] == tablero[row][col + 1] && tablero[row][col + 1] == tablero[row][col + 2] && tablero[row][col + 2] == tablero[row][col + 3] && tablero[row][col + 3] == tablero[row][col + 4];
                        break;
                    case 6:
                        if (comprobar6Fichas(tablero[row][col], tablero[row][col + 1], tablero[row][col + 2], tablero[row][col + 3], tablero[row][col + 4], tablero[row][col + 5])) {
                            gameOver = true;
                            return;
                        }
                        //gameOver = tablero[row][col] != ' ' && tablero[row][col] == tablero[row][col + 1] && tablero[row][col + 1] == tablero[row][col + 2] && tablero[row][col + 2] == tablero[row][col + 3] && tablero[row][col + 3] == tablero[row][col + 4] && tablero[row][col + 4] == tablero[row][col + 5];
                        break;
                }
                if (gameOver) {
                    return;
                }
            }
        }

        for (int row = 0; row < this.filas - (this.fichasAConectar - 1); row++) {
            for (int col = 0; col < 7; col++) {
                switch (this.fichasAConectar) {
                    case 4:
                        if (comprobar4Fichas(tablero[row][col], tablero[row + 1][col], tablero[row + 2][col], tablero[row + 3][col])) {
                            gameOver = true;
                            return;
                        }
                        //gameOver = tablero[row][col] != ' ' && tablero[row][col] == tablero[row + 1][col] && tablero[row + 1][col] == tablero[row + 2][col] && tablero[row + 2][col] == tablero[row + 3][col];
                        break;
                    case 5:
                        if (comprobar5Fichas(tablero[row][col], tablero[row + 1][col], tablero[row + 2][col], tablero[row + 3][col], tablero[row + 4][col])) {
                            gameOver = true;
                            return;
                        }
                        //gameOver = tablero[row][col] != ' ' && tablero[row][col] == tablero[row + 1][col] && tablero[row + 1][col] == tablero[row + 2][col] && tablero[row + 2][col] == tablero[row + 3][col] && tablero[row + 3][col] == tablero[row + 4][col];
                        break;
                    case 6:
                        if (comprobar6Fichas(tablero[row][col], tablero[row + 1][col], tablero[row + 2][col], tablero[row + 3][col], tablero[row + 4][col], tablero[row + 5][col])) {
                            gameOver = true;
                            return;
                        }
                        //gameOver = tablero[row][col] != ' ' && tablero[row][col] == tablero[row + 1][col] && tablero[row + 1][col] == tablero[row + 2][col] && tablero[row + 2][col] == tablero[row + 3][col] && tablero[row + 3][col] == tablero[row + 4][col] && tablero[row + 4][col] == tablero[row + 5][col];
                        break;
                }
                if (gameOver) {
                    return;
                }
            }
        }

        for (int row = 0; row < this.filas - (this.fichasAConectar - 1); row++) {
            for (int col = 0; col < this.columnas - (this.fichasAConectar - 1); col++) {
                switch (this.fichasAConectar) {
                    case 4:
                        if (comprobar4Fichas(tablero[row][col], tablero[row + 1][col + 1], tablero[row + 2][col + 2], tablero[row + 3][col + 3])) {
                            gameOver = true;
                            return;
                        }
                        //gameOver = tablero[row][col] != ' ' && tablero[row][col] == tablero[row + 1][col + 1] && tablero[row + 1][col + 1] == tablero[row + 2][col + 2] && tablero[row + 2][col + 2] == tablero[row + 3][col + 3];
                        break;
                    case 5:
                        if (comprobar5Fichas(tablero[row][col], tablero[row + 1][col + 1], tablero[row + 2][col + 2], tablero[row + 3][col + 3], tablero[row + 4][col + 4])) {
                            gameOver = true;
                            return;
                        }
                        //gameOver = tablero[row][col] != ' ' && tablero[row][col] == tablero[row + 1][col + 1] && tablero[row + 1][col + 1] == tablero[row + 2][col + 2] && tablero[row + 2][col + 2] == tablero[row + 3][col + 3] && tablero[row + 3][col + 3] == tablero[row + 4][col + 4];
                        break;
                    case 6:
                        if (comprobar6Fichas(tablero[row][col], tablero[row + 1][col + 1], tablero[row + 2][col + 2], tablero[row + 3][col + 3], tablero[row + 4][col + 4], tablero[row + 5][col + 5])) {
                            gameOver = true;
                            return;
                        }
                        //gameOver = tablero[row][col] != ' ' && tablero[row][col] == tablero[row + 1][col + 1] && tablero[row + 1][col + 1] == tablero[row + 2][col + 2] && tablero[row + 2][col + 2] == tablero[row + 3][col + 3] && tablero[row + 3][col + 3] == tablero[row + 4][col + 4] && tablero[row + 4][col + 4] == tablero[row + 5][col + 5];
                        break;
                }
                if (gameOver) {
                    return;
                }
            }
        }

        for (int row = 0; row < this.filas - (this.fichasAConectar - 1); row++) {
            for (int col = this.fichasAConectar - 1; col < this.columnas; col++) {
                switch (this.fichasAConectar) {
                    case 4:
                        if (comprobar4Fichas(tablero[row][col], tablero[row + 1][col - 1], tablero[row + 2][col - 2], tablero[row + 3][col - 3])) {
                            gameOver = true;
                            return;
                        }
                        //gameOver = tablero[row][col] != ' ' && tablero[row][col] == tablero[row + 1][col - 1] && tablero[row + 1][col - 1] == tablero[row + 2][col - 2] && tablero[row + 2][col - 2] == tablero[row + 3][col - 3];
                        break;
                    case 5:
                        if (comprobar5Fichas(tablero[row][col], tablero[row + 1][col - 1], tablero[row + 2][col - 2], tablero[row + 3][col - 3], tablero[row + 4][col - 4])) {
                            gameOver = true;
                            return;
                        }
                        //gameOver = tablero[row][col] != ' ' && tablero[row][col] == tablero[row + 1][col - 1] && tablero[row + 1][col - 1] == tablero[row + 2][col - 2] && tablero[row + 2][col - 2] == tablero[row + 3][col - 3] && tablero[row + 3][col - 3] == tablero[row + 4][col - 4];
                        break;
                    case 6:
                        if (comprobar6Fichas(tablero[row][col], tablero[row + 1][col - 1], tablero[row + 2][col - 2], tablero[row + 3][col - 3], tablero[row + 4][col - 4], tablero[row + 5][col - 5])) {
                            gameOver = true;
                            return;
                        }
                        //gameOver = tablero[row][col] != ' ' && tablero[row][col] == tablero[row + 1][col - 1] && tablero[row + 1][col - 1] == tablero[row + 2][col - 2] && tablero[row + 2][col - 2] == tablero[row + 3][col - 3] && tablero[row + 3][col - 3] == tablero[row + 4][col - 4] && tablero[row + 4][col - 4] == tablero[row + 5][col - 5];
                        break;
                }
                if (gameOver) {
                    return;
                }
            }
        }

        if (tableroLleno()) {
            System.out.println("¡Empate! El tablero está lleno.");
            gameOver = true;
        }
    }

    private boolean comprobar4Fichas(char a, char b, char c, char d) {
        return a != ' ' && a == b && b == c && c == d;
    }

    private boolean comprobar5Fichas(char a, char b, char c, char d, char e) {
        return a != ' ' && a == b && b == c && c == d && d == e;
    }

    private boolean comprobar6Fichas(char a, char b, char c, char d, char e, char f) {
        return a != ' ' && a == b && b == c && c == d && d == e && e == f;
    }

    private boolean tableroLleno() {
        for (int fila = 0; fila < this.filas; fila++) {
            for (int columna = 0; columna < this.columnas; columna++) {
                if (tablero[fila][columna] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }
}