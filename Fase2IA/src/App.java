import java.util.List;
import java.util.Map;
import java.util.Scanner;

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
import Solver.Solver;
import Solver.SolverEncomendas;

public class App {
    public static void main(String[] args) throws Exception {        
        DataBase db = new DataBase();

        Grafo g = db.BuildGrafo();
        //Test(db.g);
        List<Pedido> listaPedido = db.BuildPedidos();
        
        Map<String,Estafeta> allEstafetas = db.BuildEstafetas();
        
        // SolverEncomendas.SolveDFS(listaPedido, g,allEstafetas);
        Scanner sc = new Scanner(System.in);
        String linha = null;
        String arr[] = new String[]{
            "Trabalho prático de IA",
            "Escreva quit para sair",
            "Insira o nome de um algoritmo",
            "DFS",
            "BFS",
            "ITERATIVE",
            "ASTAR",
            "GREEDY",
            "ALL -> corre todos os algoritmos",
        };

        Menu.ClearScreen();
        Menu.Print(arr, "Insira um algoritmo\n");
        while(! (linha =sc.nextLine()).equals("quit"))
        {
            if (linha.equals("all"))
            Solver.SolveAll(listaPedido, g, allEstafetas);
            else
            Solver.Solve(linha.toUpperCase(), listaPedido, g, allEstafetas);

            System.out.print("Insira uma linha qualquer: ");
            sc.nextLine();
            Menu.ClearScreen();
            Menu.Print(arr,"Insira outro algoritmo\n");
        }
       
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


        var path5 = IterativeSearch.Search(g, r1, r2,1);
        path5.Print();
    }
}
