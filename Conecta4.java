import java.util.Scanner;

public class Conecta4 {
    private final char[][] tablero;
    int filas;
    int columnas;
    private final int fichasAConectar;
    private char jugadorActual;
    private boolean gameOver;

    public Conecta4(int filas, int columnas, int fichasAConectar) {
        this.filas = filas;
        this.columnas = columnas;
        this.fichasAConectar = fichasAConectar;
        this.jugadorActual = 'X';
        this.gameOver = false;
        this.tablero = new char[filas][columnas];
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                this.tablero[i][j] = ' ';
            }
        }
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
        System.out.print("|");
        for (int i = 0; i < this.columnas; i++) {
            System.out.print(" " + (i+1) + " |");
        }
        System.out.println();

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
        for (int fil = 0; fil < this.filas; fil++) {
            for (int col = 0; col < this.columnas - (this.fichasAConectar - 1); col++) {
                switch (this.fichasAConectar) {
                    case 4:
                        if (comprobar4Fichas(tablero[fil][col], tablero[fil][col + 1], tablero[fil][col + 2], tablero[fil][col + 3])) {
                            gameOver = true;
                            return;
                        }
                        break;
                    case 5:
                        if (comprobar5Fichas(tablero[fil][col], tablero[fil][col + 1], tablero[fil][col + 2], tablero[fil][col + 3], tablero[fil][col + 4])) {
                            gameOver = true;
                            return;
                        }
                        break;
                    case 6:
                        if (comprobar6Fichas(tablero[fil][col], tablero[fil][col + 1], tablero[fil][col + 2], tablero[fil][col + 3], tablero[fil][col + 4], tablero[fil][col + 5])) {
                            gameOver = true;
                            return;
                        }
                        break;
                }
                if (gameOver) {
                    return;
                }
            }
        }

        for (int fil = 0; fil < this.filas - (this.fichasAConectar - 1); fil++) {
            for (int col = 0; col < 7; col++) {
                switch (this.fichasAConectar) {
                    case 4:
                        if (comprobar4Fichas(tablero[fil][col], tablero[fil + 1][col], tablero[fil + 2][col], tablero[fil + 3][col])) {
                            gameOver = true;
                            return;
                        }
                        break;
                    case 5:
                        if (comprobar5Fichas(tablero[fil][col], tablero[fil + 1][col], tablero[fil + 2][col], tablero[fil + 3][col], tablero[fil + 4][col])) {
                            gameOver = true;
                            return;
                        }
                        break;
                    case 6:
                        if (comprobar6Fichas(tablero[fil][col], tablero[fil + 1][col], tablero[fil + 2][col], tablero[fil + 3][col], tablero[fil + 4][col], tablero[fil + 5][col])) {
                            gameOver = true;
                            return;
                        }
                        break;
                }
                if (gameOver) {
                    return;
                }
            }
        }

        for (int fil = 0; fil < this.filas - (this.fichasAConectar - 1); fil++) {
            for (int col = 0; col < this.columnas - (this.fichasAConectar - 1); col++) {
                switch (this.fichasAConectar) {
                    case 4:
                        if (comprobar4Fichas(tablero[fil][col], tablero[fil + 1][col + 1], tablero[fil + 2][col + 2], tablero[fil + 3][col + 3])) {
                            gameOver = true;
                            return;
                        }
                        break;
                    case 5:
                        if (comprobar5Fichas(tablero[fil][col], tablero[fil + 1][col + 1], tablero[fil + 2][col + 2], tablero[fil + 3][col + 3], tablero[fil + 4][col + 4])) {
                            gameOver = true;
                            return;
                        }
                        break;
                    case 6:
                        if (comprobar6Fichas(tablero[fil][col], tablero[fil + 1][col + 1], tablero[fil + 2][col + 2], tablero[fil + 3][col + 3], tablero[fil + 4][col + 4], tablero[fil + 5][col + 5])) {
                            gameOver = true;
                            return;
                        }
                        break;
                }
                if (gameOver) {
                    return;
                }
            }
        }

        for (int fil = 0; fil < this.filas - (this.fichasAConectar - 1); fil++) {
            for (int col = this.fichasAConectar - 1; col < this.columnas; col++) {
                switch (this.fichasAConectar) {
                    case 4:
                        if (comprobar4Fichas(tablero[fil][col], tablero[fil + 1][col - 1], tablero[fil + 2][col - 2], tablero[fil + 3][col - 3])) {
                            gameOver = true;
                            return;
                        }
                        break;
                    case 5:
                        if (comprobar5Fichas(tablero[fil][col], tablero[fil + 1][col - 1], tablero[fil + 2][col - 2], tablero[fil + 3][col - 3], tablero[fil + 4][col - 4])) {
                            gameOver = true;
                            return;
                        }
                        break;
                    case 6:
                        if (comprobar6Fichas(tablero[fil][col], tablero[fil + 1][col - 1], tablero[fil + 2][col - 2], tablero[fil + 3][col - 3], tablero[fil + 4][col - 4], tablero[fil + 5][col - 5])) {
                            gameOver = true;
                            return;
                        }
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
