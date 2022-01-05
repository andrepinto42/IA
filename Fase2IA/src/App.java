import java.util.List;
import java.util.Map;

import BaseDados.Encomendas.Pedido;
import BaseDados.Encomendas.SolverEncomendas;
import BaseDados.Nodo.Rua;
import BaseDados.Pessoa.Estafeta;

import Grafos.Grafo;
import Grafos.Path;
import Procura.AStar;
import Procura.BreadthFirst;
import Procura.DepthFirst;
import Procura.Greedy;

public class App {
    public static void main(String[] args) throws Exception {        
        DataBase db = new DataBase();

        Grafo g = db.BuildGrafo();
        Test(db.g);
        List<Pedido> listaPedido = db.BuildPedidos();
        
        Map<String,Estafeta> allEstafetas = db.BuildEstafetas();
        
        SolverEncomendas.SolveDFS(listaPedido, g,allEstafetas);
    }



    private static void Test(Grafo g) {

        Rua r1 = g.mainRua;
        Rua r2 = g.getAllRuas().get(5);
        // Path path = BreadthFirst.BFS(g, r1,r2);

        
        // path = DepthFirst.DFS(g, r1,r2);

        var path1 = AStar.AStarFind(g, r1, r2);
        path1.Print();

        var path2 = BreadthFirst.BFS(g, r1, r2);
        path2.Print();
    
        var path3 = DepthFirst.DFS(g, r1, r2);
        path3.Print();

        var path4 = Greedy.GreedySearch(g, r1, r2);
        path4.Print();
    }
}
