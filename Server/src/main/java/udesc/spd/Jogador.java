package udesc.spd;

public class Jogador extends Pessoa {
    private String posicao;

    public Jogador(String nome, String cpf, String rua , String posicao) {
        super(nome, cpf, rua);
        this.posicao = posicao;
    }

    public String getPosicao() {
        return posicao;
    }

    public void setPosicao(String posicao) {
        this.posicao = posicao;
    }

    @Override
    public String toString() {
        return "Jogador{" +
                "nome=" + super.getNome() +
                "cpf=" + super.getCpf() +
                "posicao='" + posicao + '\'' +
                '}';
    }
}
