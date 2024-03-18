package udesc.spd;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client2 {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        try {
            Socket socket = new Socket("192.168.3.9",5555);
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
            boolean socketAberto = true;


            while (socketAberto) {
                System.out.println("Insira uma mensagem para o servidor");
                String msg = kb.nextLine();
                output.writeUTF(msg);
                output.flush();


                String serverResponse = input.readUTF();
                System.out.println(serverResponse);


                if(msg.equals("fim")){
                    System.out.println("Encerrado a comunicação com: "+ socket.getInetAddress());
                    output.close();
                    input.close();
                    socket.close();
                    socketAberto = false;
                }
            }




        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}