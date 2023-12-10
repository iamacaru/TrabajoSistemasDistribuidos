import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 55555);
             BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        	 Scanner sc = new Scanner(System.in)){
        	
        	// Recibimos la info inicial (las mesas disponibles)
            String linea = input.readLine();
            while (!linea.equals("Para1")) {
                System.out.println(linea);
                linea = input.readLine();
            }
            
            // Hacemos que el usuario elija una mesa válida
            int n = Integer.parseInt(input.readLine());
            linea = input.readLine();
            int i = 0;
            while (i < 1 || i > n) {
                System.out.println(linea);
                String intento = sc.nextLine();
                output.write(intento + "\n");
                output.flush();
                try {
                    i = Integer.parseInt(intento);
                } catch (NumberFormatException e) {
                    i = -1;
                }
                linea = input.readLine();
            }
            
            // Recibimos info prepartida (el estado de la mesa, qué jugador es el usuario, mensaje de inicio de partida...)
            boolean soyX = false;
            while (!linea.equals("Para2")) {
                System.out.println(linea);
                if  (linea.contains("Tú jugaras con")) {
                	soyX = linea.contains("X");
                }
                linea = input.readLine();
            }
            
            // Nos ayudará a printear el tablero, que puede variar de tamaño
            int tamañoTablero = Integer.parseInt(input.readLine());
            linea = input.readLine();
            
            // Se empieza a jugar
            while (!linea.equals("Para3")) {
            	i = 0;
            	// Printea el tablero en cada turno
            	while (i < tamañoTablero) {
            		System.out.println(linea);
            		linea = input.readLine();
            		i++;
            	}
            	
            	// Esta sección se compone de 4 ifs, las cuatro posibilidades de cada turno: turno de X y es el cliente de X, turno de X y es el cliente de O y lo mismo con O
            	
            	// En este if se entra cuando sea el turno de X y estamos en el cliente de X
            	// Manejamos el turno de X pidiéndole que ingrese un movimiento válido y printeando un separador al final de su turno
                if (linea.contains("Es el turno de X") && soyX) {
                	System.out.println(linea);
                	linea = input.readLine();
                    while (!linea.equals("__________________________________________________")) {
                        System.out.println(linea);
                        linea = sc.nextLine();
                        output.write(linea + "\n");
                        output.flush();
                        linea = input.readLine();
                    }
                    System.out.println(linea);
                // En este if se entra cuando sea el turno de X y estamos en el cliente de O
                // Manejamos el turno de X desde la perspectiva de O esperando a que X juegue, que es cuando se printea que ya ha jugado y de seguido un separador
                } else if (linea.contains("Es el turno de X")) {
                	System.out.println(linea);
                	linea = input.readLine();
                	while (!linea.equals("El jugador X ha colocado ficha")) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        linea = input.readLine();
                    }
                	System.out.println(linea);
                	linea = input.readLine();
                	System.out.println(linea);
                // En este if se entra cuando sea el turno de O y estamos en el cliente de O
                // Manejamos el turno de O pidiéndole que ingrese un movimiento válido y printeando un separador al final de su turno
                } else if (linea.contains("Es el turno de O") && !soyX) {
                	System.out.println(linea);
                	linea = input.readLine();
                    while (!linea.equals("__________________________________________________")) {
                        System.out.println(linea);
                        linea = sc.nextLine();
                        output.write(linea + "\n");
                        output.flush();
                        linea = input.readLine();
                    }
                    System.out.println(linea);
                // En este if se entra cuando sea el turno de O y estamos en el cliente de X
                // Manejamos el turno de O desde la perspectiva de X esperando a que O juegue, que es cuando se printea que ya ha jugado y de seguido un separador
                } else if (linea.contains("Es el turno de O")) {
                	System.out.println(linea);
                	linea = input.readLine();
                	while (!linea.equals("El jugador O ha colocado ficha")) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        linea = input.readLine();
                    }
                	System.out.println(linea);
                	linea = input.readLine();
                	System.out.println(linea);
                }
                linea = input.readLine();
            }
            
            // El while anterior maneja todas las jugadas. Al final de él volvemos a mostrar el tablero y el resultado de la partida
            i = 0;
        	while (i < tamañoTablero) {
        		linea = input.readLine();
        		System.out.println(linea);
        		i++;
        	}
        	
        	linea = input.readLine();
    		System.out.println(linea);
    		linea = input.readLine();
    		System.out.println(linea);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
