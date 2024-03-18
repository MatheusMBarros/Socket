package udesc.spd;

public class Pessoa {
    private String nome;
    private String cpf;

    private String rua;

    public Pessoa(String nome, String cpf, String rua) {
        this.nome = nome;
        this.cpf = cpf;
        this.rua = rua;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }


    @Override
    public String toString() {
        return nome+";"+ cpf+";"+rua;
    }
}
