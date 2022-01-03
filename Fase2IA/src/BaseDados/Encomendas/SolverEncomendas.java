package BaseDados.Encomendas;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import BaseDados.Nodo.Rua;
import BaseDados.Pessoa.Estafeta;
import Grafos.Grafo;
import Procura.BreadthFirst;
import Procura.DepthFirst;

public class SolverEncomendas {
    
    public static void Solve(List<Pedido> listaPedido, Grafo g,Map<String,Estafeta> allEstafetas)
    {
        for (Pedido pedido : listaPedido) {
            Rua ruaParaEntregar = pedido.getRua();
            var path1 = DepthFirst.DFS(g,g.mainRua ,ruaParaEntregar);
            path1.Print();
            var path2 = BreadthFirst.BFS(g, g.mainRua, ruaParaEntregar);
            path2.Print();

            System.out.print("The best path is ");
            if (path1.cost > path2.cost)
            System.out.println("BFS");
            else
            System.out.println("DFS");

            var e1 = GetRandomEstafeta(allEstafetas);
            System.out.println(e1.nome + " is going there");

        }
    }

    public static void SolveDFS(List<Pedido> listaPedido, Grafo g)
    {
        for (Pedido pedido : listaPedido) {
            Rua ruaParaEntregar = pedido.getRua();
            var path = DepthFirst.DFS(g,g.mainRua ,ruaParaEntregar);
            path.Print();
        }
    }

    public static void SolveBFS(List<Pedido> listaPedido, Grafo g)
    {
        for (Pedido pedido : listaPedido) {
            Rua ruaParaEntregar = pedido.getRua();
            var path = BreadthFirst.BFS(g, g.mainRua, ruaParaEntregar);
            path.Print();
        }
    }

    private static Estafeta GetRandomEstafeta(Map<String,Estafeta> map)
    {
        Estafeta e1 = null;

        int size = map.size();
        int random =(int) Math.floor(Math.random() * size);
        e1 = map.values().stream().collect(Collectors.toList()).get(random);
        return e1;

    }
}
