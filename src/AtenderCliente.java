package src;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class AtenderCliente implements Runnable {
    private Socket cliente;

    public AtenderCliente(Socket cliente) {
        this.cliente = cliente;
    }

    public void run() {
    	Mesa mesaElegida = null;
        try (BufferedReader entradaCliente = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
             BufferedWriter salidaCliente = new BufferedWriter(new OutputStreamWriter(cliente.getOutputStream()))) {
        	
        	// Mostramos al cliente las mesas
        	salidaCliente.write("Lista de Mesas Disponibles:\n");
            salidaCliente.flush();
            
            List<Mesa> mesasDisponibles = Servidor.getMesasDisponibles();

            salidaCliente.write("Lista de Mesas Disponibles:\n");
            salidaCliente.flush();
            int numeroMesa = 1;
            for (Mesa mesa : mesasDisponibles) {
                salidaCliente.write("  " + numeroMesa + ".- Mesa " + numeroMesa + ": " + mesa.getDescrripcion() + "\n" + "     Jugador 1: " + (mesa.getJugador1() != null ? mesa.getNomJugador1() : "-") +
                        "   Jugador 2: " + (mesa.getJugador2() != null ? mesa.getNomJugador2() + "\n" : "-\n"));
                numeroMesa++;
                salidaCliente.flush();
            }
            
            salidaCliente.write("------------------------------------------------------\n");
            salidaCliente.flush();

            salidaCliente.write("Para1\n");
            salidaCliente.flush();

            // Tratamos la recepción de los intentos del usuario por elegir una mesa
            salidaCliente.write(mesasDisponibles.size() + "\n");
            salidaCliente.flush();

            int n = 0;
            salidaCliente.write("Ingresa el número de la mesa a la que deseas unirte:\n");
            salidaCliente.flush();
            do {
                try {
                    n = Integer.parseInt(entradaCliente.readLine());
                    if (n < 1 || n > mesasDisponibles.size()) {
                    	salidaCliente.write("Mesa no válida. Intenta de nuevo:\n");
                        salidaCliente.flush();
                	}
                } catch (NumberFormatException e) {
                    n = -1;
                    salidaCliente.write("Mesa no válida. Intenta de nuevo:\n");
                    salidaCliente.flush();
                }
            } while (n < 1 || n > mesasDisponibles.size());
            mesaElegida = mesasDisponibles.get(n - 1);
            
            // Añadimos el cliente a la mesa seleccionada
            mesaElegida.agregarJugador(cliente);
            
            salidaCliente.write("------------------------------------------------------\n");
            salidaCliente.flush();
            
            // Mandamos info varia prepartida
            salidaCliente.write("Te has unido a la mesa " + n + "\n");
            salidaCliente.flush();

            if (mesaElegida.hayEspacio()) {
                salidaCliente.write("Esperando a que alguien más se una.\n");
                salidaCliente.flush();
                // Dormimos el hilo en varias iteraciones hasta que se una otro jugador
                while (mesaElegida.hayEspacio()) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                
                salidaCliente.write("Otro jugador se ha unido.\n");
                salidaCliente.flush();
            }
            
            salidaCliente.write("------------------------------------------------------\n");
            salidaCliente.flush();

            salidaCliente.write("¡Vamos a jugar!\n");
            salidaCliente.flush();
            
            salidaCliente.write("------------------------------------------------------\n");
            salidaCliente.flush();
            
            // El jugador que jugará con las fichas X será siempre el primero que entre
            if (mesaElegida.getNomJugador1().equals(cliente.getInetAddress().getHostAddress() + "   " + cliente.getPort())) {
            	salidaCliente.write("Tú jugaras con 'X'\n");
                salidaCliente.flush();
            } else {
            	salidaCliente.write("Tú jugaras con 'O'\n");
                salidaCliente.flush();
            }
            
            salidaCliente.write("______________________________________________________\n");
            salidaCliente.flush();
            
            salidaCliente.write("Para2\n");
            salidaCliente.flush();
            
            // Esto servirá para ayudar al cliente a mostrar bien los tableros, ya que cada mesa tiene distintos tamaños
            salidaCliente.write(mesaElegida.getJuego().getTamaño() + "\n");
            salidaCliente.flush();
            
            // Se jugará hasta que haya un ganador
            while (!mesaElegida.getJuego().getGameOver()) {
            	// Mandamos una representación del tablero en cada iteración
            	salidaCliente.write(mesaElegida.getJuego().verTablero());
                salidaCliente.flush();
                // Indicamos de quién es el turno en cada jugada
                salidaCliente.write("Es el turno de " + mesaElegida.getJuego().getJugadorActual() + "\n");
                salidaCliente.flush();
                
                // De igual manera que en la clase Cliente, tenemos 4 ifs para tratar lo que debe hacer cada AtenderCliente en cada jugada, accediendo únicamente a una opción en cada turno
                
                // Las condiciones de cada if son similares a las de los clientes
                if (mesaElegida.getNomJugador1().equals(cliente.getInetAddress().getHostAddress() + "   " + cliente.getPort()) && mesaElegida.getJuego().getJugadorActual() == 'X') {
                	int columna = 0;
                	// Solicitamos un movimiento hasta que se ingrese uno válido
                	salidaCliente.write("Ingresa la columna:\n");
                    salidaCliente.flush();
                	do {
                        try {
                        	columna = Integer.parseInt(entradaCliente.readLine()) - 1;
                        	if (!mesaElegida.getJuego().movimientoValido(columna)) {
                        		salidaCliente.write("Movimiento no válido. Intenta de nuevo:\n");
                                salidaCliente.flush();
                        	}
                        } catch (NumberFormatException e) {
                        	columna = -1;
                        	salidaCliente.write("Movimiento no válido. Intenta de nuevo:\n");
                            salidaCliente.flush();
                        }
                    } while (!mesaElegida.getJuego().movimientoValido(columna));
                    // Modificamos la mesa en función de la jugada
                    mesaElegida.getJuego().colocarFicha(columna);
                    mesaElegida.getJuego().comprobarGanador();
                    if (!mesaElegida.getJuego().getGameOver()) {
                    	mesaElegida.getJuego().cambiarJugador();
                    }
                    salidaCliente.write("__________________________________________________\n");
                    salidaCliente.flush();
                    mesaElegida.getJuego().setSeHaJugado(true);
                    // Ponemos el hilo a dormir un instante, ya que en el if de abajo (en el que se encontrará el otro jugado cuando el primer este en este) ponemos el hilo a dormir reiteradas veces hasta que
                    // el primero haya jugado y es posible que justo se le mande a dormir cuando el de arriba ya ha jugado, por lo atnto el primer jugador seguirá ejecutando el código, irá a la iteración de
                    // la siguite jugada y encontrará el booleando que le permite seguir en el esta erróneo. Por eso lo pausamo ese instante.
                    try {
                        Thread.sleep(1100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else if (mesaElegida.getNomJugador2().equals(cliente.getInetAddress().getHostAddress() + "   " + cliente.getPort()) && mesaElegida.getJuego().getJugadorActual() == 'X') {
                	// Hacemos esperar hasta que el jugador X haya jugado. Esto se hace durmiendo el hilo como hemos explicado arriba
                	salidaCliente.write("Todavia no\n");
                    salidaCliente.flush();
                    int i = 0;
                	while (!mesaElegida.getJuego().getSeHaJugado()) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        salidaCliente.write("Todavia no " + i + "\n");
                        salidaCliente.flush();
                        i++;
                    }
                	mesaElegida.getJuego().setSeHaJugado(false);
                	
                	salidaCliente.write("El jugador X ha colocado ficha\n");
                    salidaCliente.flush();
                    salidaCliente.write("__________________________________________________\n");
                    salidaCliente.flush();
                // El mismo caso, pero del otro lado
                } else if (mesaElegida.getNomJugador2().equals(cliente.getInetAddress().getHostAddress() + "   " + cliente.getPort()) && mesaElegida.getJuego().getJugadorActual() == 'O') {
                	int columna = 0;
                	salidaCliente.write("Ingresa la columna:\n");
                    salidaCliente.flush();
                	do {
                        try {
                        	columna = Integer.parseInt(entradaCliente.readLine()) - 1;
                        	if (!mesaElegida.getJuego().movimientoValido(columna)) {
                        		salidaCliente.write("Movimiento no válido. Intenta de nuevo:\n");
                                salidaCliente.flush();
                        	}
                        } catch (NumberFormatException e) {
                        	columna = -1;
                        	salidaCliente.write("Movimiento no válido. Intenta de nuevo:\n");
                            salidaCliente.flush();
                        }
                    } while (!mesaElegida.getJuego().movimientoValido(columna));
                    
                    mesaElegida.getJuego().colocarFicha(columna);
                    mesaElegida.getJuego().comprobarGanador();
                    if (!mesaElegida.getJuego().getGameOver()) {
                    	mesaElegida.getJuego().cambiarJugador();
                    }
                    salidaCliente.write("__________________________________________________\n");
                    salidaCliente.flush();
                    mesaElegida.getJuego().setSeHaJugado(true);
                    try {
                        Thread.sleep(1100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                // Más de los mismo
                } else if (mesaElegida.getNomJugador1().equals(cliente.getInetAddress().getHostAddress() + "   " + cliente.getPort()) && mesaElegida.getJuego().getJugadorActual() == 'O') {
                	salidaCliente.write("Todavia no\n");
                    salidaCliente.flush();
                    int i = 0;
                	while (!mesaElegida.getJuego().getSeHaJugado()) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        salidaCliente.write("Todavia no " + i + "\n");
                        salidaCliente.flush();
                        i++;
                    }
                	mesaElegida.getJuego().setSeHaJugado(false);
                	
                	salidaCliente.write("El jugador O ha colocado ficha\n");
                    salidaCliente.flush();
                    salidaCliente.write("__________________________________________________\n");
                    salidaCliente.flush();
                }
            }
            
            salidaCliente.write("Para3\n");
            salidaCliente.flush();
            
            // Se manda la representación del tablero una última vez junto con el resultado
            salidaCliente.write(mesaElegida.getJuego().verTablero() + "\n");
            salidaCliente.flush();
            if (mesaElegida.getJuego().getHayGanador()) {
            	salidaCliente.write("¡Juego terminado! El Jugador " + mesaElegida.getJuego().getJugadorActual() + " es el ganador.\n");
                salidaCliente.flush();
            } else {
            	salidaCliente.write("¡Empate! El tablero está lleno.\n");
                salidaCliente.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        	// Reiniciamos al final. Se manda esperar 2 segundos ya que en algunas pruebas se reiniciaba la mesa antes de mostrar el resutado final
        	if (mesaElegida != null) {
        		try {
    				Thread.sleep(2000);
    			} catch (InterruptedException e) {
    				e.printStackTrace();
    			}
        		mesaElegida.reiniciarMesa();
        	}
        }
    }
}
