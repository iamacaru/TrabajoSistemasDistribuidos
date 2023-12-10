package principal;

import java.util.Scanner;

public class ConectaX {
    private char[][] tablero;
    private int tamaño;
    private int filas;
    private int columnas;
    private int fichasAConectar;
    private char jugadorActual;
    private boolean gameOver;
    private boolean hayGanador;
    private boolean seHaJugado;
    
    public ConectaX(int filas, int columnas, int fichasAConectar) {
        this.filas = filas;
        this.columnas = columnas;
        this.fichasAConectar = fichasAConectar;
        this.jugadorActual = 'X';
        this.gameOver = false;
        this.hayGanador = false;
        this.seHaJugado = false;
        
        this.tamaño = (filas + 1) * 2;
        this.tablero = new char[filas][columnas];
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                this.tablero[i][j] = ' ';
            }
        }
    }
    
    // Método para probar la clase
    public void jugar() {
        Scanner scanner = new Scanner(System.in);

        while (!this.gameOver) {
            System.out.print(verTablero());
        	verTablero();
            System.out.println("Es el turno del Jugador " + this.jugadorActual);

            int columna = 0;
            do {
            	System.out.print("Ingresa la columna: ");

                try {
                	columna = Integer.parseInt(scanner.nextLine()) - 1;
                	if (!movimientoValido(columna)) {
                		System.out.println("Movimiento no válido. Intenta de nuevo.");
                	}
                } catch (NumberFormatException e) {
                	columna = -1;
                	 System.out.println("Movimiento no válido. Intenta de nuevo.");
                }
            } while (!movimientoValido(columna));

            colocarFicha(columna);
            comprobarGanador();
            if (!this.gameOver) {
                cambiarJugador();
            }
            System.out.println("__________________________________________________");
            System.out.println();
        }
        
        System.out.println(verTablero());
        if (hayGanador) {
        	System.out.println("¡Juego terminado! El Jugador " + this.jugadorActual + " es el ganador.");
        } else {
        	System.out.println("¡Empate! El tablero está lleno.");
        }
    }

    // Devuelve un String con el tablero
    public String verTablero() {
    	String displayTablero = "|";
        for (int i = 0; i < this.columnas; i++) {
        	displayTablero += " " + (i+1) + " |";
        }
        displayTablero += "\n";

        for (int i = 0; i < 1 + this.columnas * 4; i++) {
        	displayTablero += "-";
        }
        displayTablero += "\n";

        for (int i = 0; i < filas; i++) {
        	displayTablero += "|";
            for (int j = 0; j < this.columnas; j++) {
            	displayTablero += " " + this.tablero[i][j] + " |";
            }
            displayTablero += "\n";

            for (int j = 0; j < 1 + this.columnas * 4; j++) {
            	displayTablero += "-";
            }
            displayTablero += "\n";
        }
        return displayTablero;
    }

    // Devuelve un booleano que determina si un movimiento es válido
    public boolean movimientoValido(int columna) {
        return columna >= 0 && columna < this.columnas && this.tablero[0][columna] == ' ';
    }

    // Coloca la ficha en la columna indicada
    public void colocarFicha(int columna) {
        for (int i = this.filas - 1; i >= 0; i--) {
            if (this.tablero[i][columna] == ' ') {
                this.tablero[i][columna] = this.jugadorActual;
                break;
            }
        }
    }

    // Cambia el turno
    public void cambiarJugador() {
        this.jugadorActual = (this.jugadorActual == 'X') ? 'O' : 'X';
    }

    // Comprueba si el tablero contiene algúna combinación ganadora de fichas
    public void comprobarGanador() {
        for (int fil = 0; fil < this.filas; fil++) {
            for (int col = 0; col < this.columnas - (this.fichasAConectar - 1); col++) {
                switch (this.fichasAConectar) {
                    case 4:
                        if (comprobar4Fichas(tablero[fil][col], tablero[fil][col + 1], tablero[fil][col + 2], tablero[fil][col + 3])) {
                            gameOver = true;
                            hayGanador = true;
                            return;
                        }
                        break;
                    case 5:
                        if (comprobar5Fichas(tablero[fil][col], tablero[fil][col + 1], tablero[fil][col + 2], tablero[fil][col + 3], tablero[fil][col + 4])) {
                            gameOver = true;
                            hayGanador = true;
                            return;
                        }
                        break;
                    case 6:
                        if (comprobar6Fichas(tablero[fil][col], tablero[fil][col + 1], tablero[fil][col + 2], tablero[fil][col + 3], tablero[fil][col + 4], tablero[fil][col + 5])) {
                            gameOver = true;
                            hayGanador = true;
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
                            hayGanador = true;
                            return;
                        }
                        break;
                    case 5:
                        if (comprobar5Fichas(tablero[fil][col], tablero[fil + 1][col], tablero[fil + 2][col], tablero[fil + 3][col], tablero[fil + 4][col])) {
                            gameOver = true;
                            hayGanador = true;
                            return;
                        }
                        break;
                    case 6:
                        if (comprobar6Fichas(tablero[fil][col], tablero[fil + 1][col], tablero[fil + 2][col], tablero[fil + 3][col], tablero[fil + 4][col], tablero[fil + 5][col])) {
                            gameOver = true;
                            hayGanador = true;
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
                            hayGanador = true;
                            return;
                        }
                        break;
                    case 5:
                        if (comprobar5Fichas(tablero[fil][col], tablero[fil + 1][col + 1], tablero[fil + 2][col + 2], tablero[fil + 3][col + 3], tablero[fil + 4][col + 4])) {
                            gameOver = true;
                            hayGanador = true;
                            return;
                        }
                        break;
                    case 6:
                        if (comprobar6Fichas(tablero[fil][col], tablero[fil + 1][col + 1], tablero[fil + 2][col + 2], tablero[fil + 3][col + 3], tablero[fil + 4][col + 4], tablero[fil + 5][col + 5])) {
                            gameOver = true;
                            hayGanador = true;
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
                            hayGanador = true;
                            return;
                        }
                        break;
                    case 5:
                        if (comprobar5Fichas(tablero[fil][col], tablero[fil + 1][col - 1], tablero[fil + 2][col - 2], tablero[fil + 3][col - 3], tablero[fil + 4][col - 4])) {
                            gameOver = true;
                            hayGanador = true;
                            return;
                        }
                        break;
                    case 6:
                        if (comprobar6Fichas(tablero[fil][col], tablero[fil + 1][col - 1], tablero[fil + 2][col - 2], tablero[fil + 3][col - 3], tablero[fil + 4][col - 4], tablero[fil + 5][col - 5])) {
                            gameOver = true;
                            hayGanador = true;
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
            gameOver = true;
        }
    }
    
    // Metodo auxiliares de comprobarGanador()
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
    
    // Varios gets
    public int getTamaño() {
    	return this.tamaño;
    }
    
    public char getJugadorActual() {
    	return this.jugadorActual;
    }
    
    public boolean getGameOver() {
    	return this.gameOver;
    }
    
    public boolean getHayGanador() {
    	return this.hayGanador;
    }
    
    public boolean getSeHaJugado() {
    	return this.seHaJugado;
    }
    
    // Un set para saber si el jugador del turno actual ha jugado
    public void setSeHaJugado(boolean v) {
    	this.seHaJugado = v;
    }
    
    // Reinicia el tablero
    public void reiniciarConectaX() {
    	this.jugadorActual = 'X';
        this.gameOver = false;
        this.hayGanador = false;
        this.seHaJugado = false;
        
        this.tablero = new char[this.filas][this.columnas];
        for (int i = 0; i < this.filas; i++) {
            for (int j = 0; j < this.columnas; j++) {
                this.tablero[i][j] = ' ';
            }
        }
    }
}
