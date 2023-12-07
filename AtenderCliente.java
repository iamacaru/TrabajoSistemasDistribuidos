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
            
            mesaElegida.agregarJugador(cliente);
            
            salidaCliente.write("------------------------------------------------------\n");
            salidaCliente.flush();

            salidaCliente.write("Te has unido a la mesa " + n + "\n");
            salidaCliente.flush();

            if (mesaElegida.hayEspacio()) {
                salidaCliente.write("Esperando a que alguien más se una.\n");
                salidaCliente.flush();
                
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
            
            salidaCliente.write(mesaElegida.getJuego().getTamaño() + "\n");
            salidaCliente.flush();
            
            while (!mesaElegida.getJuego().getGameOver()) {
            	salidaCliente.write(mesaElegida.getJuego().verTablero());
                salidaCliente.flush();
                salidaCliente.write("Es el turno de " + mesaElegida.getJuego().getJugadorActual() + "\n");
                salidaCliente.flush();
                if (mesaElegida.getNomJugador1().equals(cliente.getInetAddress().getHostAddress() + "   " + cliente.getPort()) && mesaElegida.getJuego().getJugadorActual() == 'X') {
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
                } else if (mesaElegida.getNomJugador2().equals(cliente.getInetAddress().getHostAddress() + "   " + cliente.getPort()) && mesaElegida.getJuego().getJugadorActual() == 'X') {
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
