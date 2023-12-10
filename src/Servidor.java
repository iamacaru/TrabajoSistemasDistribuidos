package src;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Servidor {

    private static List<Mesa> mesas = new ArrayList<>();

    public static void main(String[] args) {
    	mesas.add(new Mesa(6, 7, 4));
        mesas.add(new Mesa(6, 7, 4));
        mesas.add(new Mesa(6, 7, 4));
        mesas.add(new Mesa(6, 7, 4));
        mesas.add(new Mesa(7, 7, 4));
        mesas.add(new Mesa(7, 8, 4));
        mesas.add(new Mesa(8, 8, 5));
        mesas.add(new Mesa(8, 9, 5));
        mesas.add(new Mesa(9, 9, 6));
        mesas.add(new Mesa(10, 10, 6));
        
        ExecutorService pool = Executors.newCachedThreadPool();
        
        try (ServerSocket serverSocket = new ServerSocket(55555)) {
            System.out.println("Lobby de Conecta en X abierto");
            System.out.println("------------------------------------------------------");
            Socket cliente;
            while (true) {
            	try {
            		cliente = serverSocket.accept();
                    System.out.println("El jugador (" + cliente.getInetAddress().getHostAddress() + "   " + cliente.getPort() + ") se ha conectado al loby");
                    System.out.println("------------------------------------------------------");
                    pool.execute(new AtenderCliente(cliente));
            	} catch (IOException e) {
            		e.printStackTrace();
            	}
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        	pool.shutdown();
        }
    } 
    
    public static List<Mesa> getMesasDisponibles() {
        List<Mesa> mesasDisponibles = new ArrayList<>();
        for (Mesa mesa : mesas) {
            if (mesa.hayEspacio()) {
                mesasDisponibles.add(mesa);
            }
        }
        return mesasDisponibles;
    }
}
