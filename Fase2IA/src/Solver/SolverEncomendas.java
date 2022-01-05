package Solver;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

import javax.lang.model.util.ElementScanner6;

import BaseDados.Encomendas.Pedido;
import BaseDados.Nodo.Rua;
import BaseDados.Pessoa.Estafeta;
import BaseDados.Veiculo.Bicicleta;
import BaseDados.Veiculo.Carro;
import BaseDados.Veiculo.Mota;
import BaseDados.Veiculo.Veiculo;
import Grafos.Grafo;
import Grafos.Path;
import Procura.AStar;
import Procura.BreadthFirst;
import Procura.DepthFirst;
import Procura.Greedy;
import Procura.IterativeSearch;

//import static jdk.nashorn.internal.parser.DateParser.HOUR;

public class SolverEncomendas {

    public static Estafeta melhorEstafetaEcologico(Pedido p, Map<String,Estafeta> map, Float dist){
        int pesoEncomenda = p.produto.getKg();
        float horasParaEntregar = p.horasParaEntregar;
        Estafeta bestEstafeta = null;
        for (Estafeta e : map.values()) {
            
            boolean valid = CheckIfValid(pesoEncomenda, e, dist, horasParaEntregar);
            
            if (!valid) continue;
            float horasAtuaisParaEntregar = GetHoursToDeliver(pesoEncomenda, e, dist);
            
            if (horasAtuaisParaEntregar > horasParaEntregar)
            {
               //Estafeta nao vai chegar a horas
                continue;
            }
            if (bestEstafeta == null)
            {
                bestEstafeta = e;
                continue;
            }
            if (bestEstafeta.veiculo instanceof Bicicleta)
                continue;
            
            if (bestEstafeta.veiculo instanceof Mota && !(e.veiculo instanceof Carro) )
                bestEstafeta = e;
            else 
                bestEstafeta = e;
            
        }
        return bestEstafeta;
    }

    public static Estafeta melhorEstafetaTempo(Pedido p, Map<String,Estafeta> map, Float dist){

        int pesoEncomenda = p.produto.getKg();
        float horasParaEntregar = p.horasParaEntregar;
        float melhorTempoParaEntrega = 0f;
        Estafeta bestEstafeta = null;
        for (Estafeta e : map.values()) {

            boolean valid = CheckIfValid(pesoEncomenda, e, dist, horasParaEntregar);
            
            if (!valid) continue;
            float horasAtuaisParaEntregar = GetHoursToDeliver(pesoEncomenda, e, dist);

            if (bestEstafeta == null)
            {
                melhorTempoParaEntrega = horasAtuaisParaEntregar;
                bestEstafeta = e;
            }
            else
            {
                if (melhorTempoParaEntrega> horasAtuaisParaEntregar)
                {
                    melhorTempoParaEntrega = horasAtuaisParaEntregar;
                    bestEstafeta = e;
                }
            }
        }
        return bestEstafeta;
    }

    public static Path SolveUsingAllAlgorithm(Pedido pedido, Grafo g){
        Rua StartingPoint = g.mainRua;

        Comparator<Path> pathComparer = new Comparator<Path>() {
            @Override
            public int compare(Path o1, Path o2) {
                return (int) (o1.cost - o2.cost);
            }
        };
        Path[] allPaths = new Path[5];

        Rua endingPoint = pedido.getRua();
        PriorityQueue<Path> pathQueue = new PriorityQueue<>(pathComparer);

        allPaths[0] = BreadthFirst.BFS(g, StartingPoint,endingPoint);
        allPaths[1] = DepthFirst.DFS(g, StartingPoint,endingPoint);
        allPaths[2] = IterativeSearch.Search(g, StartingPoint,endingPoint,3);
        allPaths[3] = AStar.AStarFind(g,StartingPoint, endingPoint);
        allPaths[4] = Greedy.GreedySearch(g,StartingPoint, endingPoint);

        
        for (Path path : allPaths) {
            path.PrintPath();
            pathQueue.add(path);
        }

        return pathQueue.poll();
    }

    public static void SolveDFS(List<Pedido> listaPedido, Grafo g,Map<String,Estafeta> allEstafetas)
    {
        System.out.println("\n-----------------DFS-----------------\n");
        for (Pedido pedido : listaPedido) {
            Rua ruaParaEntregar = pedido.getRua();
            int peso = pedido.produto.getKg();
            var path = DepthFirst.DFS(g,g.mainRua ,ruaParaEntregar);
            System.out.println("\nInicio:\n");
           
            for (Estafeta e : allEstafetas.values()) {
                if (e.veiculo.getPesoMaximo() < peso)
                {
                    System.out.println("Estafeta " + e.nome + 
                    " nao consegue entregar pois tem carga a mais para " + e.veiculo.getClass().getSimpleName());
                    continue;
                }
                float velocidadeMedia = e.GetVelocidadeMedia(peso);
                //Neste momento o estafeta tem de ir para o local de entrega e regressar
                float tempo = (path.cost * 2) / velocidadeMedia;
                System.out.println("Estafeta " + e.nome + " de " + e.veiculo.getClass().getSimpleName() + " demora "
                + tempo + " horas" );
            }
        

            Estafeta best = melhorEstafetaTempo(pedido, allEstafetas, path.cost);
            System.out.println("Estafeta " + best.nome + " é o mais RAPIDO para entregar ");
            Estafeta eco = melhorEstafetaEcologico(pedido, allEstafetas, path.cost);
            System.out.println("Estafeta " + eco.nome + " é o mais ECOLOGICO para entregar ");

            path.PrintPath();
            path.ReversePath();
            path.PrintPath();
        }
    }

    public static void SolveBFS(List<Pedido> listaPedido, Grafo g,Map<String,Estafeta> allEstafetas)
    {
        System.out.println("\n-----------------BFS-----------------\n");
        for (Pedido pedido : listaPedido) {
            Rua ruaParaEntregar = pedido.getRua();
            int peso = pedido.produto.getKg();
            var path = BreadthFirst.BFS(g,g.mainRua ,ruaParaEntregar);
            System.out.println("\nInicio:\n");

            for (Estafeta e : allEstafetas.values()) {
                if (e.veiculo.getPesoMaximo() < peso)
                {
                    System.out.println("Estafeta " + e.nome +
                            " nao consegue entregar pois tem carga a mais para " + e.veiculo.getClass().getSimpleName());
                    continue;
                }
                float velocidadeMedia = e.GetVelocidadeMedia(peso);
                //Neste momento o estafeta tem de ir para o local de entrega e regressar
                float tempo = (path.cost * 2) / velocidadeMedia;
                System.out.println("Estafeta " + e.nome + " de " + e.veiculo.getClass().getSimpleName() + " demora "
                        + tempo + " horas" );
            }
            
            Estafeta best = melhorEstafetaTempo(pedido, allEstafetas, path.cost);
            System.out.println("Estafeta " + best.nome + " é o mais RAPIDO para entregar ");
            Estafeta eco = melhorEstafetaEcologico(pedido, allEstafetas, path.cost);
            System.out.println("Estafeta " + eco.nome + " é o mais ECOLOGICO para entregar ");

            path.PrintPath();
            path.ReversePath();
            path.PrintPath();
        }
    }

    public static void SolveAStar(List<Pedido> listaPedido, Grafo g,Map<String,Estafeta> allEstafetas)
    {
        System.out.println("\n-----------------A*-----------------\n");
        for (Pedido pedido : listaPedido) {
            Rua ruaParaEntregar = pedido.getRua();
            int peso = pedido.produto.getKg();
            var path = AStar.AStarFind(g,g.mainRua ,ruaParaEntregar);
            System.out.println("\nInicio:\n");

            for (Estafeta e : allEstafetas.values()) {
                if (e.veiculo.getPesoMaximo() < peso)
                {
                    System.out.println("Estafeta " + e.nome +
                            " nao consegue entregar pois tem carga a mais para " + e.veiculo.getClass().getSimpleName());
                    continue;
                }
                float velocidadeMedia = e.GetVelocidadeMedia(peso);
                //Neste momento o estafeta tem de ir para o local de entrega e regressar
                float tempo = (path.cost * 2) / velocidadeMedia;
                System.out.println("Estafeta " + e.nome + " de " + e.veiculo.getClass().getSimpleName() + " demora "
                        + tempo + " horas" );
            }

            Estafeta best = melhorEstafetaTempo(pedido, allEstafetas, path.cost);
            System.out.println("Estafeta " + best.nome + " é o mais RAPIDO para entregar ");
            Estafeta eco = melhorEstafetaEcologico(pedido, allEstafetas, path.cost);
            System.out.println("Estafeta " + eco.nome + " é o mais ECOLOGICO para entregar ");

            path.PrintPath();
            path.ReversePath();
            path.PrintPath();
        }
    }

    public static void SolveGreedy(List<Pedido> listaPedido, Grafo g,Map<String,Estafeta> allEstafetas)
    {
        System.out.println("\n-----------------Greedy-----------------\n");
        for (Pedido pedido : listaPedido) {
            Rua ruaParaEntregar = pedido.getRua();
            int peso = pedido.produto.getKg();
            var path = Greedy.GreedySearch(g,g.mainRua ,ruaParaEntregar);
            System.out.println("\nInicio:\n");

            for (Estafeta e : allEstafetas.values()) {
                if (e.veiculo.getPesoMaximo() < peso)
                {
                    System.out.println("Estafeta " + e.nome +
                            " nao consegue entregar pois tem carga a mais para " + e.veiculo.getClass().getSimpleName());
                    continue;
                }
                float velocidadeMedia = e.GetVelocidadeMedia(peso);
                //Neste momento o estafeta tem de ir para o local de entrega e regressar
                float tempo = (path.cost * 2) / velocidadeMedia;
                System.out.println("Estafeta " + e.nome + " de " + e.veiculo.getClass().getSimpleName() + " demora "
                        + tempo + " horas" );
            }

            Estafeta best = melhorEstafetaTempo(pedido, allEstafetas, path.cost);
            System.out.println("Estafeta " + best.nome + " é o mais RAPIDO para entregar ");
            Estafeta eco = melhorEstafetaEcologico(pedido, allEstafetas, path.cost);
            System.out.println("Estafeta " + eco.nome + " é o mais ECOLOGICO para entregar ");

            path.PrintPath();
            path.ReversePath();
            path.PrintPath();
        }
    }

    public static void SolveIterativeSearch(List<Pedido> listaPedido, Grafo g,Map<String,Estafeta> allEstafetas)
    {
        System.out.println("\n-----------------IterativeSearch-----------------\n");
        for (Pedido pedido : listaPedido) {
            Rua ruaParaEntregar = pedido.getRua();
            int peso = pedido.produto.getKg();
            var path = IterativeSearch.Search(g,g.mainRua ,ruaParaEntregar, 3);
            System.out.println("\nInicio:\n");

            for (Estafeta e : allEstafetas.values()) {
                if (e.veiculo.getPesoMaximo() < peso)
                {
                    System.out.println("Estafeta " + e.nome +
                            " nao consegue entregar pois tem carga a mais para " + e.veiculo.getClass().getSimpleName());
                    continue;
                }
                float velocidadeMedia = e.GetVelocidadeMedia(peso);
                //Neste momento o estafeta tem de ir para o local de entrega e regressar
                float tempo = (path.cost * 2) / velocidadeMedia;
                System.out.println("Estafeta " + e.nome + " de " + e.veiculo.getClass().getSimpleName() + " demora "
                        + tempo + " horas" );
            }

            Estafeta best = melhorEstafetaTempo(pedido, allEstafetas, path.cost);
            System.out.println("Estafeta " + best.nome + " é o mais RAPIDO para entregar ");
            Estafeta eco = melhorEstafetaEcologico(pedido, allEstafetas, path.cost);
            System.out.println("Estafeta " + eco.nome + " é o mais ECOLOGICO para entregar ");

            path.PrintPath();
            path.ReversePath();
            path.PrintPath();
        }
    }


    private static boolean CheckIfValid(int pesoEncomenda,Estafeta e,float dist,float horasParaEntregar)
    {
        if (pesoEncomenda > e.veiculo.getPesoMaximo())
        {
            //System.out.println("Estafeta nao consegue transportar esse peso :(");
            return false;
        }

        float horasAtuaisParaEntregar = GetHoursToDeliver(pesoEncomenda, e, dist);

        if (horasAtuaisParaEntregar > horasParaEntregar)
        {
            //System.out.println("Estafeta nao vai chegar a horas :(");
            return false;
        }
        return true;
    }

    private static float GetHoursToDeliver(int peso, Estafeta e,float dist)
    {
        float veloMedia = e.GetVelocidadeMedia( peso);
        float horasAtuaisParaEntregar = dist / veloMedia;
        return horasAtuaisParaEntregar;
    }
}
