package udesc.spd;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    private static ArrayList<Pessoa> pessoasArray ;

    private ArrayList<TimeFutebol> times = new ArrayList<>();

    private ServerSocket serverSocket;

    public void createServer(int port) throws IOException {
        InetAddress ipAddress = InetAddress.getByName("0.0.0.0");
        serverSocket = new ServerSocket(port, 0, ipAddress);
        serverSocket.setReuseAddress(true);
    }

    private void closeSocket(Socket s) throws IOException {
        s.close();
    }

    private Socket waitConnection() throws IOException {
        return serverSocket.accept();
    }

    public void popularPessoas(String nomeTime){// Procurar o time pelo nome
        TimeFutebol time = null;
        for (TimeFutebol t : times) {
            if (t.getNomeTime().equalsIgnoreCase(nomeTime)) {
                time = t;
                break;
            }
        }
        // Se o time for encontrado, listar os integrantes
        if (time != null) {
            pessoasArray = time.getPessoas();
        }

    }

    public String insertTime(String nomeTime, int fundacao, String cidade) {
        TimeFutebol novoTime = new TimeFutebol(nomeTime, fundacao, cidade);
        times.add(novoTime);
        return "time adicionado com sucesso";
    }

    public String insertJogador(String nome, String cpf, String rua, String posicao, String nomeTime) {
        boolean cpfExiste = false;
        for (Pessoa p : pessoasArray) {
            if (p.getCpf().equals(cpf)) {
                cpfExiste = true;
            }
        }
        if (!cpfExiste) {
            // Procura o time pelo nome
            TimeFutebol timeJogador = null;
            for (TimeFutebol t : times) {
                if (t.getNomeTime().equalsIgnoreCase(nomeTime)) {
                    timeJogador = t;
                    break;
                }
            }
            if (timeJogador != null) {
                Pessoa novoJogador = new Jogador(nome, cpf, rua, posicao);
                timeJogador.addPessoa(novoJogador);
                return "Jogador adicionado com sucesso ao time " + nomeTime;
            } else {
                return "Time não encontrado.";
            }
        } else {
            return "Já existe uma pessoa com esse CPF cadastrado";
        }
    }

    public String insertStaff(String nome, String cpf, String rua, String cargo, String nomeTime) {
        boolean cpfExiste = false;
        for (Pessoa p : pessoasArray) {
            if (p.getCpf().equals(cpf)) {
                cpfExiste = true;
            }
        }
        if (!cpfExiste) {
            // Procura o time pelo nome
            TimeFutebol timeStaff = null;
            for (TimeFutebol t : times) {
                if (t.getNomeTime().equalsIgnoreCase(nomeTime)) {
                    timeStaff = t;
                    break;
                }
            }
            if (timeStaff != null) {
                Pessoa newStaff = new Staff(nome, cpf, rua, cargo);
                timeStaff.addPessoa(newStaff);
                return "Staff adicionado com sucesso ao time " + nomeTime;
            } else {
                return "Time não encontrado.";
            }
        } else {
            return "Já existe uma pessoa com esse CPF cadastrado";
        }
    }


    public String listPessoas(String nomeTime) {
        // Procurar o time pelo nome
        TimeFutebol time = null;
        for (TimeFutebol t : times) {
            if (t.getNomeTime().equalsIgnoreCase(nomeTime)) {
                time = t;
                break;
            }
        }

        // Se o time for encontrado, listar os integrantes
        if (time != null) {
            ArrayList<Pessoa> pessoas = time.getPessoas();
            StringBuilder response = new StringBuilder();
            response.append(pessoas.size()).append("\n"); // Envia o número de pessoas na lista
            for (Pessoa pessoa : pessoas) {
                response.append(pessoa.toString()).append("\n"); // Envia cada pessoa individualmente
            }
            return response.toString();
        } else {
            return "Time não encontrado";
        }
    }



    public String getPessoa(String cpf) {
        String pessoa = "";
        if (!pessoasArray.isEmpty()) {
            for (Pessoa p : pessoasArray) {
                if (p.getCpf().equals(cpf)) {
                    pessoa = p.toString();
                } else pessoa = "User not found";
            }
        }
        return pessoa;
    }

    public String updatePessoa(String nome, String cpf, String rua) {
        for (Pessoa pessoa : pessoasArray) {
            if (pessoa.getCpf().equals(cpf)) {
                pessoa.setNome(nome);
                pessoa.setRua(rua);
                return "Pessoa atualizada com sucesso";
            }
        }
        return "Pessoa não encontrada";
    }

    public String deletePessoa(String cpf) {
        for (Pessoa pessoa : pessoasArray) {
            if (pessoa.getCpf().equals(cpf)) {
                pessoasArray.remove(pessoa);
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
                String msg = input.readLine();
                if (msg == null) {
                    break;
                }
                //metododo;tipo;nome;cpf;rua;cargo/posicao;time
                //insert;time;nome;fundacao;cidade
                String[] corte = msg.split(";");

                if (corte.length > 3) {
                    String metodo = corte[0].toUpperCase();
                    String tipo = corte[1].toUpperCase();

                    if (tipo.equals("STAFF")) {
                        if (metodo.equals("INSERT")) {
                            output.println(insertStaff(corte[2], corte[3], corte[4], corte[5] , corte[6]));
                            output.flush();
                        }
                    }

                    else if (tipo.equals("JOGADOR")) {
                        if (metodo.equals("INSERT")) {
                            output.println(insertJogador(corte[2], corte[3], corte[4], corte[5],corte[6]));
                            output.flush();
                        }
                    }

                    else if (tipo.equals("TIME")) {
                        if (metodo.equals("INSERT")) {
                            int anoFunda = Integer.parseInt(corte[3]);
                            output.println(insertTime(corte[2],anoFunda, corte[4]));
                            output.flush();
                        }
                        if (metodo.equals("LIST")) {
                            popularPessoas(corte[4]);
                            output.println(listPessoas(corte[4]));
                                output.flush();
                        }
                    }

                    else if (metodo.equals("UPDATE")) {
                        output.println(updatePessoa(corte[2], corte[3], corte[4]));
                        output.flush();
                    }
                }

                if (corte.length == 3) {
                    //delete/get;cpf;time
                    String metodo = corte[0].toUpperCase();
                    String cpf = corte[1].toUpperCase();
                    String time = corte[2];
                    popularPessoas(time);
                    if (metodo.equals("GET")) {
                        output.println(getPessoa(cpf));
                        output.flush();
                    } else if (metodo.equals("DELETE")) {
                        output.println(deletePessoa(cpf));
                        output.flush();
                    }
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
            InetAddress ipAddress = InetAddress.getByName("0.0.0.0");
            String ip = ipAddress.toString();
            System.out.println("Aguardando Conexão...");
            server.createServer(1234);
            System.out.println("SOCKET INICIADO NO IP: " + ip);
            while (true) {
                Socket socket = server.waitConnection();
                System.out.println("Cliente Conectado: " + socket.getInetAddress());
                // Inicia uma nova thread para lidar com a conexão
                new Thread(() -> server.connection(socket)).start();
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
