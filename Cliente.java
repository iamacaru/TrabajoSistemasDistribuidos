import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 55555);
             BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        	 Scanner sc = new Scanner(System.in)){
            String linea = input.readLine();
            while (!linea.equals("Para1")) {
                System.out.println(linea);
                linea = input.readLine();
            }
            
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
            
            boolean soyX = false;
            while (!linea.equals("Para2")) {
                System.out.println(linea);
                if  (linea.contains("Tú jugaras con")) {
                	soyX = linea.contains("X");
                }
                linea = input.readLine();
            }
            
            int tamañoTablero = Integer.parseInt(input.readLine());
            linea = input.readLine();
            while (!linea.equals("Para3")) {
            	i = 0;
            	while (i < tamañoTablero) {
            		System.out.println(linea);
            		linea = input.readLine();
            		i++;
            	}
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
