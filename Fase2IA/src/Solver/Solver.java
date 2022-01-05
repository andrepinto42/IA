package Solver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import BaseDados.Encomendas.Pedido;
import BaseDados.Nodo.Rua;
import BaseDados.Pessoa.Estafeta;
import Grafos.Grafo;
import Grafos.Path;
import Menu.Menu;
import Procura.AStar;
import Procura.BreadthFirst;
import Procura.DepthFirst;
import Procura.Greedy;
import Procura.IterativeSearch;

public class Solver {
    static Map<Rua,Integer> mapaEntregas;
    
    private static void Initialize() {
        mapaEntregas = new HashMap<Rua,Integer>();
    }
    public static void SolveAll(List<Pedido> listaPedido, Grafo g, Map<String, Estafeta> allEstafetas)
    {
        Initialize();
        for (int i = 0; i < listaPedido.size(); i++) {
            Pedido pedidoAtual = listaPedido.get(i);
            var bestpath = SolverEncomendas.SolveUsingAllAlgorithm(pedidoAtual, g);
            
            HandlePath(allEstafetas, pedidoAtual, bestpath);
        }
        Finalize();
    }
    
    public static void Solve(String algorithm,List<Pedido> listaPedido, Grafo g, Map<String, Estafeta> allEstafetas)
    {
        Initialize();
        for (int i = 0; i < listaPedido.size(); i++) {
            Pedido pedidoAtual = listaPedido.get(i);
            Path path = null;
            switch (algorithm) {
                case "DFS":
                    path = DepthFirst.DFS(g, g.mainRua, pedidoAtual.getRua());
                    break;
                case "BFS":
                    path = BreadthFirst.BFS(g, g.mainRua, pedidoAtual.getRua());
                    break;
                case "ASTAR":
                    path = AStar.AStarFind(g, g.mainRua, pedidoAtual.getRua());
                    break;
                case "ITERATIVE":
                    path = IterativeSearch.Search(g, g.mainRua, pedidoAtual.getRua(),3);
                    break;
                case "GREEDY":
                    path = Greedy.GreedySearch(g, g.mainRua, pedidoAtual.getRua());
                default:
                    System.out.println("Not found algorithm");
                    return;
            }
            HandlePath(allEstafetas, pedidoAtual, path);
        }
        Finalize();
    }



   
    public static void PrintEstafetas(Pedido pedidoAtual, Map<String, Estafeta> allEstafetas, Path bestpath)
    {
        var ecoEstafeta = SolverEncomendas.melhorEstafetaEcologico(pedidoAtual,allEstafetas , bestpath.cost);
        var tempoEstafeta = SolverEncomendas.melhorEstafetaTempo(pedidoAtual,allEstafetas , bestpath.cost);
        
        if (tempoEstafeta == null || ecoEstafeta == null)
        {
            System.out.println("Nao existe um estafeta para realizar essa entrega :(");
            return;
        }
        System.out.println("Estafeta mais Ecologico " + ecoEstafeta.nome);
        System.out.println("Estafeta mais rápido " + tempoEstafeta.nome);
    }

    private static void HandlePath(Map<String, Estafeta> allEstafetas, Pedido pedidoAtual, Path bestpath) {
        var it= bestpath.pathToTravel.iterator();
        Rua last=it.next();
     
        if (last != null)
        System.out.println("Pedido foi entregue em " + last.ruaNome + "\n\n----------------------------");
        

        if (!mapaEntregas.containsKey(last))
        {
            mapaEntregas.put(last,1);
        }
        else
        {
            mapaEntregas.put(last, mapaEntregas.get(last)+1 );
        }

        pedidoAtual.Print();


        PrintEstafetas(pedidoAtual, allEstafetas, bestpath);
        
        bestpath.PrintActualPath();
    }

    private static void Finalize() {
        String[] arr = new String[mapaEntregas.size() * 2];
        int i=0;
        for (var entry : mapaEntregas.entrySet()) {
            arr[i] = entry.getKey().ruaNome;
            i++;
            arr[i] = entry.getValue() + " entregas";
            i++;
        }

        Menu.Print(arr);

    }
}
