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

public class App {
    public static void main(String[] args) throws Exception {        
        DataBase db = new DataBase();

        Grafo g = db.BuildGrafo();
        Test(db.g);
        // List<Pedido> listaPedido = db.BuildPedidos();
        
        // Map<String,Estafeta> allEstafetas = db.BuildEstafetas();
        
        // SolverEncomendas.Solve(listaPedido, g,allEstafetas);
    }



    private static void Test(Grafo g) {

        Rua r1 = g.mainRua;
        Rua r2 = g.getAllRuas().get(5);
        // Path path = BreadthFirst.BFS(g, r1,r2);

        
        // path = DepthFirst.DFS(g, r1,r2);

        var path = AStar.AStarFind(g, r1, r2);
        path.Print();
    }
}
