package Procura;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

import BaseDados.Nodo.Pai;
import BaseDados.Nodo.Rua;
import Grafos.Grafo;
import Grafos.Path;


public class AStar {
    public static boolean enableDebug = false;

    public static Path AStarFind(Grafo g,Rua r1,Rua r2)
    {
        Queue<Rua> openSet = new PriorityQueue<Rua>();
        Queue<Rua> closedSet = new PriorityQueue<Rua>();

        Map<Rua,Pai> pathway = new HashMap<Rua,Pai>();

        List<Rua> allRuasTravelled = new ArrayList<Rua>();

        stackPath =  new Stack<Rua>();
        
        Path p = new Path();
        
        Rua next = r1;
        openSet.add(next);
        pathway.put(next, null);

        while(!openSet.isEmpty())
        {
            if (enableDebug)
            {
                System.out.println("OPEN SET");
                for (Rua rua : openSet) {
                    System.out.println(rua.ruaNome + " Cost " + rua.GetCostF());
                }
                System.out.println("\n");
            }
     
            //Tirar o Nodo com o menor custo do Open set
            Rua minimalRua = GetMinimum(openSet);
            next = minimalRua;

            allRuasTravelled.add(minimalRua);
            
            if (enableDebug) System.out.println("\nAStar is looking at " + next.ruaNome);

            if (next.equals(r2))
            {
                if (enableDebug) System.out.println("FOUND IT");
                break;
            }

            var nextRuas = g.getNextRuas(next);

            for (Rua adjacente : nextRuas.keySet()) {
                
                if ( closedSet.contains(adjacente))
                {
                     if (enableDebug) System.out.println("No Evaluation at " + adjacente.ruaNome);
                    continue;
                }
                if (!openSet.contains(adjacente))
                    openSet.add(adjacente);
                
                float costToReach = GetCostToReachRua(pathway, r1,next );
                if (enableDebug) System.out.println("Cost to reach " + adjacente.ruaNome + " = " + costToReach);
                float custoVisita = nextRuas.get(adjacente) + costToReach;
                float Hcost = Rua.DistanceRuas(adjacente, r2);
                float fCostTemporario = custoVisita + Hcost;
                if (adjacente.GetCostF() > fCostTemporario)
                {
                    //Atualizar este nodo pois este caminho agora descoberto é mais rapido
                    adjacente.SetCostH(Hcost);
                    adjacente.SetCostG(custoVisita);    
                    //Adicionar o caminho com o seu respetivo pai
                    pathway.put(adjacente, new Pai(next,nextRuas.get(adjacente)));
                }
                else
                {
                    if (enableDebug) System.out.println("Descartado novo caminho em " + adjacente.ruaNome + " vindo de " + next.ruaNome + " com custo " + nextRuas.get(adjacente));
                }
                if (enableDebug) System.out.println("Looking at " + adjacente.ruaNome + " with F cost " + adjacente.GetCostF());
              
            }
            //Este sitio já nao vai ser mais visitado
            closedSet.add(minimalRua);
            p.allRuasTravelled.add(minimalRua);
        }
        p = GetPath(pathway, r1, r2);
        p.allRuasTravelled = allRuasTravelled;
        p.algorithm = "AStar";

        CleanAllRuas(allRuasTravelled);
        return p;
    }

    private static void CleanAllRuas(List<Rua> allRuasTravelled) {
        for (Rua r : allRuasTravelled) {
            r.SetCostG(Float.MAX_VALUE);
            r.SetCostH(Float.MAX_VALUE);
        }
    }

    private static Rua GetMinimum(Queue<Rua> openSet) {
        var it = openSet.iterator();

        if (!it.hasNext()) return null;
        Rua smallest = it.next();
        while(it.hasNext())
        {
            Rua next = it.next();
            if (smallest.compareTo(next) >= 0)
                smallest = next;
        }
        openSet.remove(smallest);
        return smallest;
    }

    static Stack<Rua> stackPath;
    
    private static float GetCostToReachRua(Map<Rua,Pai> pathway,Rua r1,Rua r2)
    {
        float cost = 0f;
        if (pathway == null || pathway.size() == 0) return 0f;

        //Se ainda nao tiver um caminho guardado então devolve 0
        if (!pathway.containsKey(r2))
            return 0f;
        
        while(!r2.equals(r1))
        {
            Pai pai = pathway.get(r2);
            if (pai==null) break;
            cost += pathway.get(r2).cost;
            r2 = pathway.get(r2).ruaPai;
        }
        return cost;
    }

    private static Path GetPath(Map<Rua,Pai> pathway,Rua r1,Rua r2) {
        Path p = new Path();
        float cost = 0f;

        if (pathway == null || pathway.size() == 0) return p;


        while(! r2.equals(r1))
        {
            Pai pai = pathway.get(r2);
            stackPath.push(r2);

            if (pai == null) break;
            cost += pathway.get(r2).cost;
            r2 = pathway.get(r2).ruaPai;
        }

        stackPath.push(r1);
        p.cost = cost;
        p.SetPathToTravel(stackPath);
        
        return p;
    }
}
