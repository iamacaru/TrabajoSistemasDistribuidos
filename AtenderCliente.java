import java.io.*;
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
             BufferedWriter salidaCliente = new BufferedWriter(new OutputStreamWriter(cliente.getOutputStream()))) {

            List<Mesa> mesasDisponibles = servidor.getMesasDisponibles();

            salidaCliente.write("Lista de Mesas Disponibles:\n");
            salidaCliente.flush();
            int numeroMesa = 1;
            for (Mesa mesa : mesasDisponibles) {
                salidaCliente.write(numeroMesa + "- Mesa " + numeroMesa + ":\n" + "   Jugador 1: " + (mesa.getJugador1() != null ? mesa.getNomJugador1() : "-") +
                        "   Jugador 2: " + (mesa.getJugador2() != null ? mesa.getNomJugador2() + "\n" : "-\n"));
                numeroMesa++;
                salidaCliente.flush();
            }

            salidaCliente.write("Para1\n");
            salidaCliente.flush();

            salidaCliente.write(mesasDisponibles.size() + "\n");
            salidaCliente.flush();

            int n = 0;
            do {
                salidaCliente.write("Ingresa el número de la mesa a la que deseas unirte:\n");
                salidaCliente.flush();

                try {
                    n = Integer.parseInt(entradaCliente.readLine());
                } catch (NumberFormatException e) {
                    n = -1;
                }
            } while (n < 1 || n > mesasDisponibles.size());
            Mesa mesaElegida = mesasDisponibles.get(n - 1);

            servidor.agregarJugadorAMesa(cliente, mesaElegida);

            salidaCliente.write("Te has unido a la mesa " + n + "\n");
            salidaCliente.flush();

            salidaCliente.write("Para2\n");
            salidaCliente.flush();

            if (mesaElegida.hayEspacio()) {
                salidaCliente.write("Esperando a que alguien más se una.\n");
                salidaCliente.flush();
            }

            // Completar

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
