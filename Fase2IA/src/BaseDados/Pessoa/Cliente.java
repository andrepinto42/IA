package BaseDados.Pessoa;

import BaseDados.Nodo.Rua;

public class Cliente {
    public String nome;
    public Rua rua;

    public Cliente(String nome, Rua rua) {
        this.nome = nome;
        this.rua = rua;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
