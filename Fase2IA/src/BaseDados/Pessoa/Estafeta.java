package BaseDados.Pessoa;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import BaseDados.Veiculo.Bicicleta;
import BaseDados.Veiculo.Carro;
import BaseDados.Veiculo.Mota;
import BaseDados.Veiculo.Veiculo;

public class Estafeta {
    
    
    public String nome;
    public Veiculo veiculo;
    public Boolean emUso;
 

    public Estafeta(String nome1,Veiculo v1,Boolean emUso1)
    {
        nome = nome1;
        veiculo = v1;
        emUso = emUso1;
    }


    public Estafeta(String nome1,String v1)
    {
        nome = nome1;

        switch (v1) {
            case "mota":
                veiculo = new Mota();
                break;
            case "carro":
                veiculo = new Carro();
                break;
            default:
                veiculo = new Bicicleta();
                break;
        }
    }

    public float GetVelocidadeMedia(int Peso)
    {
        return veiculo.getVelocidadeMedia(Peso);
    }
}
