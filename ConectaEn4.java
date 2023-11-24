import java.util.Scanner;

public class ConectaEn4 {
    private char[][] tablero;
    private char jugadorActual;
    private boolean gameOver;

    public ConectaEn4() {                                             //cambiar
        tablero = new char[][]{
                {' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' ', ' '}
        };
        jugadorActual = 'X';
        gameOver = false;
    }

    public void jugar() {
        Scanner scanner = new Scanner(System.in);

        while (!gameOver) {
            verTablero();
            System.out.println("Es el turno del Jugador " + jugadorActual);
            System.out.print("Ingrese la columna : ");                                             //cambiar

            int columna = scanner.nextInt() - 1;

            if (movimientoValido(columna)) {
                colocarFicha(columna);
                comprobarGanador();
                cambiarJugador();
            } else {
                System.out.println("Movimiento no válido. Intenta de nuevo.");
            }
        }

        verTablero();
        System.out.println("¡Juego terminado! El Jugador " + jugadorActual + " es el ganador.");
    }

    private void verTablero() {
        System.out.println("   1   2   3   4   5   6   7");                                             //cambiar
        System.out.println("  -----------------------------");

        for (int i = 0; i < 6; i++) {
            System.out.print((i + 1) + " |");                                             //cambiar
            for (int j = 0; j < 7; j++) {
                System.out.print(" " + tablero[i][j] + " |");
            }
            System.out.println();
            System.out.println("  -----------------------------");
        }
    }

    private boolean movimientoValido(int columna) {
        return columna >= 0 && columna < 7 && tablero[0][columna] == ' ';                                             //cambiar
    }

    private void colocarFicha(int column) {                                             //cambiar
        for (int i = 5; i >= 0; i--) {
            if (tablero[i][column] == ' ') {
                tablero[i][column] = jugadorActual;
                break;
            }
        }
    }

    private void cambiarJugador() {
        jugadorActual = (jugadorActual == 'X') ? 'O' : 'X';
    }

    private void comprobarGanador() {                                             //hacer

    }
}
