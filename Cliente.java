import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 55555);
             BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))){
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
                Scanner sc = new Scanner(System.in);
                i = sc.nextInt();
                output.write(i + "\n");
                output.flush();
            }
            linea = input.readLine();
            while (!linea.equals("Para2")) {
                System.out.println(linea);
                linea = input.readLine();
            }
            // Completar

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
