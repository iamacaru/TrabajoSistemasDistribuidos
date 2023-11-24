import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Servidor {

    private List<Mesa> mesas;

    public Servidor() {
        mesas = new ArrayList<>();
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
    }

    public List<Mesa> getMesasDisponibles() {
        List<Mesa> mesasDisponibles = new ArrayList<>();
        for (Mesa mesa : mesas) {
            if (mesa.hayEspacio()) {
                mesasDisponibles.add(mesa);
            }
        }
        return mesasDisponibles;
    }

    public void agregarJugadorAMesa(Socket cliente, Mesa mesa) {
        mesa.agregarJugador(cliente);

        //Completar

    }

    public void iniciar() {
        ExecutorService pool = Executors.newCachedThreadPool();
        try (ServerSocket serverSocket = new ServerSocket(55555)) {
            System.out.println("Servidor iniciado, esperando conexiones.");
            Socket cliente;
            while (true) {
                cliente = serverSocket.accept();
                System.out.println("Nuevo jugador conectado");
                pool.execute(new AtenderCliente(cliente, this));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Servidor servidor = new Servidor();
        servidor.iniciar();
    }
}
