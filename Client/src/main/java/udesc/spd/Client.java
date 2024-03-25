package udesc.spd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try (Scanner kb = new Scanner(System.in)) {
            System.out.println("Digite o endereço IP do servidor:");
            String serverIP = kb.nextLine();

            try (Socket socket = new Socket(serverIP, 1234);
                 PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                boolean socketAberto = true;
                while (socketAberto) {
                    System.out.println("Insira uma mensagem para o servidor:");
                    String msg = kb.nextLine();

                    // Verifica se a mensagem está no formato correto
                    if (!isValidMessage(msg)) {
                        System.out.println("Formato de mensagem inválido. Por favor, insira novamente.");
                        continue;
                    }

                    output.println(msg);

                    String serverResponse = input.readLine();
                    System.out.println(serverResponse);

                    // Imprime a lista completa de pessoas se o método for "LIST"
                    if (msg.toUpperCase().startsWith("LIST")) {
                        int lines = Integer.parseInt(serverResponse);
                        for (int i = 0; i < lines; i++) {
                            System.out.println(input.readLine());
                        }
                    }

                    if (serverResponse.equals("FIM")) {
                        System.out.println("Encerrada a comunicação com: " + socket.getInetAddress());
                        socketAberto = false;
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean isValidMessage(String message) {
        return !message.isEmpty();
    }
}
