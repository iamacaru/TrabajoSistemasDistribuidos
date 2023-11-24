import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class AtenderCliente implements Runnable {
    private Socket cliente;
    private Servidor servidor;

    public AtenderCliente(Socket cliente, Servidor servidor) {
        this.cliente = cliente;
        this.servidor = servidor;
    }

    public void run() {
        try (BufferedReader entradaCliente = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
             PrintWriter salidaCliente = new PrintWriter(cliente.getOutputStream(), true)) {

            List<Mesa> mesasDisponibles = servidor.getMesasDisponibles();

            salidaCliente.println("Lista de Mesas Disponibles:");
            int numeroMesa = 1;
            for (Mesa mesa : mesasDisponibles) {
                salidaCliente.println(numeroMesa + "- Mesa " + numeroMesa + ":\n" + "   Jugador 1: " + (mesa.getJugador1() != null ? mesa.getNomJugador1() : "-") +
                        "   Jugador 2: " + (mesa.getJugador2() != null ? mesa.getNomJugador2() : "-"));
                numeroMesa++;
            }
            salidaCliente.println("Elige el número de la mesa a la que deseas unirte:");

            int n = 0;
            do {
                salidaCliente.println("Ingresa el número de la mesa: ");
                try {
                    n = Integer.parseInt(entradaCliente.readLine());
                } catch (NumberFormatException e) {
                    n = -1;
                }
            } while (n < 1 || n > mesasDisponibles.size());
            Mesa mesaElegida = mesasDisponibles.get(n - 1);

            servidor.agregarJugadorAMesa(cliente, mesaElegida);

            // Completar

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
