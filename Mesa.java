import java.net.Socket;

public class Mesa {
    private ConectaX juego;
    private Socket jugador1;
    private Socket jugador2;
    private String nomJugador1;
    private String nomJugador2;
    private String descripcion;

    public Mesa(int filas, int columnas, int fichasAConectar) {
        this.juego = new ConectaX(filas, columnas, fichasAConectar);
        this.nomJugador1 = "";
        this.nomJugador2 = "";
        this.descripcion = filas + "x" + columnas + " (conecta " + fichasAConectar + ")";
    }

    public ConectaX getJuego() {
        return juego;
    }

    public Socket getJugador1() {
        return jugador1;
    }

    public Socket getJugador2() {
        return jugador2;
    }

    public String getNomJugador1() {
        return nomJugador1;
    }

    public String getNomJugador2() {
        return nomJugador2;
    }

    public String getDescrripcion() {
        return this.descripcion;
    }

    public boolean hayEspacio() {
        return jugador1 == null || jugador2 == null;
    }

    public void agregarJugador(Socket jugador) {
        if (jugador1 == null) {
            jugador1 = jugador;
            nomJugador1 = jugador.getInetAddress().getHostAddress() + "   " + jugador.getPort();
        } else if (jugador2 == null) {
            jugador2 = jugador;
            nomJugador2 = jugador.getInetAddress().getHostAddress() + "   " + jugador.getPort();
        }
    }
    
    public void reiniciarMesa() {
    	this.juego.reiniciarConectaX();
        this.jugador1 = null;
        this.jugador2 = null;
        this.nomJugador1 = "";
        this.nomJugador2 = "";
    }
}
