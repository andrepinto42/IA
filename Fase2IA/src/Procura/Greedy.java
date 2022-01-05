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


public class Greedy {
    public static boolean enableDebug = false;

    public static Path GreedySearch(Grafo g,Rua r1,Rua r2)
    {
        Queue<Rua> openSet = new PriorityQueue<Rua>();
        Queue<Rua> closedSet = new PriorityQueue<Rua>();

        Map<Rua,Pai> pathway = new HashMap<Rua,Pai>();

        List<Rua> allRuasTravelled = new ArrayList<Rua>();
        
        stackPath = new Stack<Rua>();
        
        CleanAllRuas(allRuasTravelled);
        
        Path p = new Path();
        
        Rua next = r1;
        openSet.add(next);
        pathway.put(next, null);
        next.SetCostH(Rua.DistanceRuas(r1,r2 ));
        while(!openSet.isEmpty())
        {
            if (enableDebug)
            {
                System.out.println("OPEN SET");
                for (Rua rua : openSet) {
                    System.out.println(rua.ruaNome + " H Cost " + rua.GetCostH());
                }
                System.out.println("\n");
            }
     
            //Tirar o Nodo com o menor custo do Open set
            Rua minimalRua = GetMinimum(openSet);
            next = minimalRua;

            allRuasTravelled.add(minimalRua);
            
            if (enableDebug) System.out.println("\nGreedy is looking at " + next.ruaNome);

            //Estado Final
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
                
                float Hcost = Rua.DistanceRuas(adjacente, r2);
                if (enableDebug) System.out.println("\n\n H COST " + Hcost+ " current " + adjacente.GetCostH());
                if (adjacente.GetCostH() >= Hcost)
                {
                    //Atualizar este nodo pois este caminho agora descoberto é mais rapido
                    adjacente.SetCostH(Hcost);
                    //Adicionar o caminho com o seu respetivo pai
                    pathway.put(adjacente, new Pai(next,nextRuas.get(adjacente)));
                }
                else
                {
                    if (enableDebug) System.out.println("Descartado novo caminho em " + adjacente.ruaNome + " vindo de " + next.ruaNome + " com custo " + nextRuas.get(adjacente));
                }
                if (enableDebug) System.out.println("Looking at " + adjacente.ruaNome + " with H cost " + adjacente.GetCostH());
              
            }
            //Este sitio já nao vai ser mais visitado
            closedSet.add(minimalRua);
            p.allRuasTravelled.add(minimalRua);
        }
        p = GetPath(pathway, r1, r2);
        p.allRuasTravelled = allRuasTravelled;
        p.algorithm = "Greedy Search";
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
            if ((smallest.GetCostH() - next.GetCostH()) >= 0)
                smallest = next;
        }
        openSet.remove(smallest);
        return smallest;
    }

    static Stack<Rua> stackPath;
    

    private static Path GetPath(Map<Rua,Pai> pathway,Rua r1,Rua r2) {
        Path p = new Path();

        if (pathway == null || pathway.size() == 0 || !pathway.containsKey(r2)) return p;

        float cost = 0f;
      
        while(! r2.equals(r1))
        {
            stackPath.push(r2);

            cost += pathway.get(r2).cost;
            r2 = pathway.get(r2).ruaPai;
        }

        stackPath.push(r1);
        p.cost = cost;
        p.SetPathToTravel(stackPath);
        
        return p;
    }
}
