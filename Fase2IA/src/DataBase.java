import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
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
        Rua r0 = new Rua("Green Destribution",0,0);
        Rua r1 = new Rua("rua dos tremoços",2,2);
        Rua r2 = new Rua("rua das macas",-1,2);
        Rua r3 = new Rua("rua dos dinossauros",0,10);
        Rua r4 = new Rua("rua de abril",1,9);
        Rua r5 = new Rua("rua do Jose",-2,3);
        Rua r6 = new Rua("rua da Africa",2,8);

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

        //Insirida para testar ASTAR
        g.addEdge(r1, r3,20f);


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
        Rua r2 = g.getAllRuas().get(4);
        Rua r3 = g.getAllRuas().get(5);


        Cliente c1 = new Cliente("Tomas",r1);
        Cliente c2 = new Cliente("Ze",r2);
        Cliente c3 = new Cliente("Quim",r3);

        Pedido p1 = new Pedido(c1, "pc",100f,5, new Date(), 3f);
        Pedido p2 = new Pedido(c1, "pao",2f,1, new Date(), 2f);
        Pedido p3 = new Pedido(c2, "mesinha de cabeceira",1000f,30, new Date(), 5f);
        Pedido p4 = new Pedido(c2, "mesinha de cabeceira",1000f,30, new Date(), 5f);
        Pedido p5 = new Pedido(c3, "Computador",10f,40, new Date(), 5f);
        
        List<Pedido> lista = new ArrayList<Pedido>();
        lista.add(p1);
        lista.add(p2);
        lista.add(p3);
        lista.add(p4);
        lista.add(p5);

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
