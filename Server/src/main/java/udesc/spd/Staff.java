package udesc.spd;

public class Staff extends Pessoa {
    private String cargo;

    public Staff(String nome, String cpf, String rua , String cargo) {
        super(nome, cpf, rua);
        this.cargo = cargo;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    @Override
    public String toString() {
        return
                "STAFF"+";"+super.getNome()+";"+ super.getCpf()+";"+super.getRua()+";"+this.getCargo();
    }
}
