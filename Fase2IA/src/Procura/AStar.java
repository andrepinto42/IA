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
    
    public static Path AStarFind(Grafo g,Rua r1,Rua r2)
    {
        Queue<Rua> openSet = new PriorityQueue<Rua>();
        Queue<Rua> closedSet = new PriorityQueue<Rua>();

        Map<Rua,Pai> pathway = new HashMap<Rua,Pai>();

        List<Rua> allRuasTravelled = new ArrayList<Rua>();
        
        float shortestDistance = Float.MAX_VALUE;
        Path p = new Path();
        
        Rua next = r1;
        openSet.add(next);
        pathway.put(next, null);

        while(!openSet.isEmpty())
        {
            System.out.println("OPEN SET");
            for (Rua rua : openSet) {
                System.out.println(rua.ruaNome + " Cost " + rua.GetCostF());
            }
            System.out.println("\n");

            //Tirar o Nodo com o menor custo do Open set
            Rua minimalRua = GetMinimum(openSet);
            next = minimalRua;

            allRuasTravelled.add(minimalRua);
            
            System.out.println("\nAStar is looking at " + next.ruaNome);
            if (next.equals(r2))
            {
                System.out.println("FOUND IT");
                break;
            }

            var nextRuas = g.getNextRuas(next);

            float min = shortestDistance;

            for (Rua adjacente : nextRuas.keySet()) {
                
                if ( closedSet.contains(adjacente))
                {
                    System.out.println("No Evaluation at " + adjacente.ruaNome);
                    continue;
                }
                if (!openSet.contains(adjacente))
                    openSet.add(adjacente);
                
                float custoVisita = nextRuas.get(adjacente);
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
                    System.out.println("Descartado novo caminho");
                }
              
                System.out.println("Looking at " + adjacente.ruaNome + " with F cost " + adjacente.GetCostF());
              
            }
            System.out.println("Done searching\n");
            //Este sitio já nao vai ser mais visitado
            closedSet.add(minimalRua);
            p.allRuasTravelled.add(minimalRua);
        }
        
        p = GetCost(pathway, r1, r2);
        p.allRuasTravelled = allRuasTravelled;
        return p;
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

    static Stack<Rua> stackPath = new Stack<Rua>();
    
    private static Path GetCost(Map<Rua,Pai> pathway,Rua r1,Rua r2) {
        Path p = new Path();

        if (pathway == null || pathway.size() == 0) return p;

        float cost = 0f;
        while(! r2.equals(r1))
        {
            stackPath.push(r2);
            cost += pathway.get(r2).cost;
            r2 = pathway.get(r2).ruaPai;
        }

        stackPath.push(r1);
        p.cost = cost;
        p.pathToTravel = stackPath;
        

        return p;
    }
}
