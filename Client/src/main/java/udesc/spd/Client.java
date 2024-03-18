package udesc.spd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try (Scanner kb = new Scanner(System.in);
             Socket socket = new Socket("localhost", 5555);
             PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            boolean socketAberto = true;
            while (socketAberto) {
                System.out.println("Insira uma mensagem para o servidor");
                String msg = kb.nextLine();

                // Verifica se a mensagem está no formato correto
                if (!isValidMessage(msg)) {
                    System.out.println("Formato de mensagem inválido. Por favor, insira novamente.");
                    continue;
                }

                output.println(msg);

                String serverResponse = input.readLine();
                System.out.println(serverResponse);

                if (serverResponse.equals("FIM")) {
                    System.out.println("Encerrada a comunicação com: " + socket.getInetAddress());
                    socketAberto = false;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean isValidMessage(String message) {
        // Verifica se a mensagem está no formato esperado
        // Neste exemplo, aceitamos qualquer mensagem não vazia
        return !message.isEmpty();
    }
}
