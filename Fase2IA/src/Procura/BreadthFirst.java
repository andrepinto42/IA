package Procura;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import BaseDados.Nodo.Pai;
import BaseDados.Nodo.Rua;
import Grafos.Grafo;
import Grafos.Path;



public class BreadthFirst {
    static Rua stoppingRua;

    static Map<Rua,Pai> pathway;

    public static Path BFS(Grafo g)
    {
        return BFS(g,g.mainRua,null);
    }
    public static Path BFS(Grafo g,Rua r)
    {
        return BFS(g,r,null);
    }

    public static Path BFS(Grafo g,Rua r1,Rua r2)
    {
        boolean enableStoping = true;
        if (r2 == null)
            enableStoping = false;
        
        Path path = new Path();
        path.cost = 0f;

        System.out.println("\n----BFS ALGORITHM-----\n");

        // Mark all the vertices as not visited(By default
        // set as false)
        Map<Rua,Boolean> visited = new HashMap<Rua,Boolean>();
        for (Rua novo: g.caminhos.keySet()) {
            visited.put(novo, false);
        }

        // Create a queue for BFS
        LinkedList<Rua> queue = new LinkedList<Rua>();
 
        System.out.println("Starting at " + r1.ruaNome);
        // Mark the current node as visited and enqueue it
        visited.put(r1, true);
        queue.add(r1);
        path.allRuasTravelled.add(r1);

        //Inicializar a pathway com a rua a começar sendo a rua que não tem Pai
        pathway = new HashMap<Rua,Pai>();
        pathway.put(r1,null);

        Search(g, r2, enableStoping, path, visited, queue);
        if (enableStoping)
            path.cost = GetCost(r1,r2);
        
        return path;
    }

    private static void Search(Grafo g, Rua r2, boolean enableStoping, Path path, Map<Rua, Boolean> visited,LinkedList<Rua> queue) 
    {
        Rua r1;
        while (queue.size() != 0)
        {
            // Dequeue a vertex from queue and print it
            r1 = queue.poll();
            
            //Found the rua we were looking after
            if (enableStoping && r1.equals(r2))
            {
                break;
            }
            // Get all adjacent vertices of the dequeued "Rua r"
            // If a adjacent has not been visited, then mark it
            // visited and enqueue it
            for (var next : g.caminhos.get(r1).entrySet()) {  
                
                Rua nextRua = next.getKey();
                float nextCost = next.getValue();
                
                //Still not visited this path
                if ( ! visited.get(nextRua) )
                {
                    System.out.print("from " + r1.ruaNome + "-> " + nextRua.ruaNome + " " + nextCost + "$\n");
                    visited.put(nextRua,true);
                    queue.add(nextRua);
                    path.allRuasTravelled.add(nextRua);
                    
                    path.cost += nextCost;

                    pathway.put(nextRua,new Pai(r1,nextCost));
                }
            }
        }
    }


    private static float GetCost(Rua r1,Rua r2) {
        if (pathway == null || pathway.size() == 0) return -1f;

        float cost = 0f;
        while(! r2.equals(r1))
        {
            System.out.println(r2.ruaNome);
            cost += pathway.get(r2).cost;
            r2 = pathway.get(r2).ruaPai;
        }

        return cost;
    }

}
