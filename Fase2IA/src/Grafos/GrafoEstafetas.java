package Grafos;

import java.util.List;

import BaseDados.Nodo.Rua;
import BaseDados.Pessoa.Estafeta;

public class GrafoEstafetas extends Grafo{
    List<Estafeta> allEstafetas;
    Grafo g;
    public GrafoEstafetas(List<Estafeta> all,Grafo g)
    {
        allEstafetas = all;
        this.g = g;
    }

    public float CostToRua(List<Rua> caminhos,Rua r)
    {
        if (caminhos.size() == 0) return -1f;

        Rua current= caminhos.get(0);
        float cost = 0f;
        for (int i = 1; (! current.ruaNome.equals(r.ruaNome) ) && i < caminhos.size() ; i++) {
            current = caminhos.get(i);
        }
        return cost;
    }
}
