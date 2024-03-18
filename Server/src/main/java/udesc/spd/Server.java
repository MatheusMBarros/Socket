package udesc.spd;

import udesc.spd.Pessoa;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    private static ArrayList<Pessoa> pessoas = new ArrayList<>(); // Lista de Pessoa
    private TimeFutebol time = new TimeFutebol("Atlético de Ibirama", 1994 ,"Ibirama");

    private ServerSocket serverSocket;

    public void createServer(int port) throws IOException {
        // Obtenha o endereço IP associado à sua interface de rede
        InetAddress ipAddress = InetAddress.getByName("localhost");
        // Crie o servidor socket no endereço IP específico e porta especificada
        serverSocket = new ServerSocket(port, 0, ipAddress);
        serverSocket.setReuseAddress(true);
    }

    private void closeSocket(Socket s) throws IOException {
        s.close();
    }

    private Socket waitConnection() throws IOException {
        return serverSocket.accept();
    }

    public String insert(String nome, String cpf , String rua, String posicao){
        boolean cpfExiste = false;
        for(Pessoa p : pessoas){
            if(p.getCpf().equals(cpf)) {
                cpfExiste = true;
            }
        }
        if(!cpfExiste){
            Pessoa novaPessoa = new Jogador(nome,cpf,rua,posicao);
            time.addSocio(novaPessoa);
            return "pessoa adicionada com sucesso";
        }else{
            return "Ja existe uma pessoa com esse CPF cadastrado";
        }

    }
    public String insertStaff(String nome, String cpf , String rua){
        boolean cpfExiste = false;
        for(Pessoa p : pessoas){
            if(p.getCpf().equals(cpf)) {
                cpfExiste = true;
            }
        }
        if(!cpfExiste){
            Pessoa novaPessoa = new Pessoa(nome,cpf,rua);
            time.addSocio(novaPessoa);
            pessoas.add(novaPessoa);
            return "pessoa adicionada com sucesso";
        }else{
            return "Ja existe uma pessoa com esse CPF cadastrado";
        }

    }

    public String listPessoas() {
        StringBuilder result = new StringBuilder();
        result.append(pessoas.size()).append("\n");
        if(!pessoas.isEmpty()){
            for (Pessoa p : pessoas) {
                result.append(p.toString()).append("\n");
            }
        } else {
            result.append("Nenhuma pessoa cadastrada\n");
        }
        return result.toString();
    }


    public String getPessoa(String cpf) {
        if (pessoas.isEmpty()) {
            return "Sem pessoas cadastradas";
        }
        for (Pessoa p : pessoas) {
            if (p.getCpf().equals(cpf)) {
                return p.toString();
            }
        }
        return "Nenhuma pessoa encontrada com esse cpf";
    }

    public String updatePessoa( String nome ,String cpf, String rua){
        for (Pessoa pessoa : pessoas){
            if(pessoa.getCpf().equals(cpf)){
                pessoa.setNome(nome);
                pessoa.setRua(rua);
                return "Pessoa atualizada com sucesso";
            }
        }
        return "Pessoa não encontrada";
    }

    public String deletePessoa(String cpf){
        for (Pessoa pessoa : pessoas){
            if(pessoa.getCpf().equals(cpf)){
                pessoas.remove(pessoa);
                return "Pessoa removida com sucesso";
            }
        }
        return "Pessoa não encontrada";

    }



    private void connection(Socket socket) {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

            boolean conexaoAberta = true;
            while (conexaoAberta) {
                // Recebendo do cliente
                String msg = input.readLine();
                if (msg == null) {
                    break;
                }
                String[] corte = msg.split(";");
                String cargo = corte[0].toUpperCase();
                String metodo = corte[1].toUpperCase();

                if(cargo.equals("JOGADOR")){
                }
                // INSERT
                if (metodo.equals("INSERT")) {
                    if (corte.length == 4) {
                        output.println(insertPessoa(corte[2], corte[3], corte[4]));
                    } else {
                        output.println("Número incorreto de argumentos para INSERT");
                    }
                }
                // GET
                else if (metodo.equals("GET")) {
                    if (corte.length == 2) {
                        output.println(getPessoa(corte[2]));
                    } else {
                        output.println("Número incorreto de argumentos para GET");
                    }
                }
                // LIST
                else if (metodo.equals("LIST")) {
                    output.println(listPessoas());
                }
                // UPDATE
                else if (metodo.equals("UPDATE")) {
                    if (corte.length == 4) {
                        output.println(updatePessoa(corte[2], corte[3], corte[4]));
                    } else {
                        output.println("Número incorreto de argumentos para UPDATE");
                    }
                }
                // DELETE
                else if (metodo.equals("DELETE")) {
                    if (corte.length == 2) {
                        output.println(deletePessoa(corte[2]));
                    } else {
                        output.println("Número incorreto de argumentos para DELETE");
                    }
                }
                // FIM
                else if (metodo.equals("FIM")) {
                    System.out.println("Comunicação encerrada");
                    output.println("FIM");
                    conexaoAberta = false;
                }
                // Método inválido
                else {
                    output.println("Método inválido");
                }
            }
        } catch (IOException e) {
            System.out.println("Problema com o cliente: " + socket.getInetAddress());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    public static void main(String args[]) {
        try {
            Server server = new Server();
            System.out.println("Aguardando Conexão...");
            server.createServer(5555);
            System.out.println("SOCKET INICIADO NO IP: " + server.serverSocket.getInetAddress());
            while (true) {
                Socket socket = server.waitConnection(); // Protocolo para gerenciar a mensagem;
                System.out.println("Cliente Conectado: " + socket.getInetAddress());
                server.connection(socket);
                System.out.println("Cliente Finalizado ");
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}