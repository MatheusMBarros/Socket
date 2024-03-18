package udesc.spd;

import java.util.ArrayList;

public class TimeFutebol {
    private static ArrayList<Pessoa> pessoas;


    private String nomeTime;
    private int fundacao;
    private String cidade;

    public TimeFutebol(String nomeTime, int fundacao, String cidade) {
        this.nomeTime = nomeTime;
        this.fundacao = fundacao;
        this.cidade = cidade;
        this.pessoas = new ArrayList<Pessoa>();

    }

    public String listarPessoas(){
        String resp = "";
        for(Pessoa p : pessoas){
            resp+=p.toString()+"/n";
        }
        return resp;
    }

    public void addPessoa(Jogador p){
        pessoas.add(p);
    }
    public void removeJogador(Jogador p ){pessoas.remove(p);}


    @Override
    public String toString() {
        return "TimeFutebol{" +
                "nomeTime='" + nomeTime + '\'' +
                ", fundacao=" + fundacao +
                ", cidade='" + cidade + '\'' +
                '}';
    }
}
