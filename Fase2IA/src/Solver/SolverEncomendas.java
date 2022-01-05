package Solver;

import java.util.*;

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


public class SolverEncomendas {

    public static Estafeta melhorEstafetaEcologico(Pedido p, Map<String,Estafeta> map, Float dist){
        int pesoEncomenda = p.produto.getKg();
        float horasParaEntregar = p.horasParaEntregar;
        Estafeta bestEstafeta = null;
        for (Estafeta e : map.values()) {
            
            boolean valid = CheckIfValid(pesoEncomenda, e, dist, horasParaEntregar);
            
            float horasAtuaisParaEntregar = GetHoursToDeliver(pesoEncomenda, e, dist);
            
            if (horasAtuaisParaEntregar<0 || !valid ) 
            {
                System.out.println(e.nome + " nao consegue entregar usando "+ e.veiculo.getClass().getSimpleName());
                continue;
            }
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
            //Se o estafeta demorar horas negativas significa que não pode fazer a entrega
            if (horasAtuaisParaEntregar<0 || !valid ) 
            {
                System.out.println(e.nome + " nao consegue entregar usando "+ e.veiculo.getClass().getSimpleName());
                continue;
            }
            System.out.println(e.nome + " com horas " + horasAtuaisParaEntregar + " usando "+ e.veiculo.getClass().getSimpleName());

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
            pathQueue.add(path);
        }
        
        return pathQueue.poll();
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
