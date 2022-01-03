import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import BaseDados.Encomendas.Pedido;
import BaseDados.Nodo.Rua;
import BaseDados.Pessoa.Cliente;
import BaseDados.Pessoa.Estafeta;
import Grafos.Grafo;
import Procura.BreadthFirst;
import Procura.DepthFirst;

public class DataBase {

    public Grafo g;
    public  List<Pedido> allPedidos;
    public Map<String,Estafeta> allEstafetas;
    public DataBase()
    {

    }
    public  Grafo BuildGrafo()
    {
        Rua r0 = new Rua("Green Destribution");
        Rua r1 = new Rua("rua dos tremoços");
        Rua r2 = new Rua("rua das macas");
        Rua r3 = new Rua("rua dos dinossauros");
        Rua r4 = new Rua("rua de abril");
        Rua r5 = new Rua("rua do Jose");
        Rua r6 = new Rua("rua da Africa");

        Grafo g = new Grafo();

        g.setMainRua(r0);

        g.addRua(r0);
        g.addRua(r1);
        g.addRua(r2);
        g.addRua(r3);
        g.addRua(r4);
        g.addRua(r5);
        g.addRua(r6);

        g.addEdge(r0, r1,2f);
        g.addEdge(r0, r2,5f);
        g.addEdge(r3, r2,6f);
        g.addEdge(r5, r2,1f);
        g.addEdge(r3, r4,3f);
        g.addEdge(r6, r4,2f);
        g.addEdge(r6, r3,1f);
        g.addEdge(r5, r3,7f);

        this.g = g;

        // g.GetEverything();
        return g;
    }

    public  List<Pedido> BuildPedidos()
    {
        if ( g == null)
        {
            System.out.println("Grafo nao inicializado");
            return null;
        }

        Rua r1 = g.getAllRuas().get(3);

        Cliente c1 = new Cliente("Tomas",r1);
        Pedido p1 = new Pedido(c1, "pc",100f, LocalDate.now(), 3f);
        
        List<Pedido> lista = new ArrayList<Pedido>();
        lista.add(p1);

        this.allPedidos = lista;
        return lista;
    }

    public Map<String,Estafeta> BuildEstafetas()
    {
        if (g == null)
        return null;
        Map<String,Estafeta> map = new  HashMap<String,Estafeta>();

        Estafeta e1 = new Estafeta("Pinto","carro");
        Estafeta e2 = new Estafeta("Rui","bicicleta");

        map.put(e1.nome, e1);
        map.put(e2.nome, e2);

        this.allEstafetas = map;
        return map;
    }
}
