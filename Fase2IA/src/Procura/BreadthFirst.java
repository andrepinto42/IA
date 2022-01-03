package Procura;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import BaseDados.Nodo.Rua;
import Grafos.Grafo;
import Grafos.Path;

public class BreadthFirst {
    static Rua stoppingRua;

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
        boolean enableStoping = false;
        if (r2 == null)
            enableStoping = true;
        
        Path path = new Path();
        path.cost = 0f;

        // System.out.println("\n----BFS ALGORITHM-----\n");

        // Mark all the vertices as not visited(By default
        // set as false)
        Map<Rua,Boolean> visited = new HashMap<Rua,Boolean>();
        for (Rua novo: g.caminhos.keySet()) {
            visited.put(novo, false);
        }

        // Create a queue for BFS
        LinkedList<Rua> queue = new LinkedList<Rua>();
 
        // System.out.println("Starting at " + r1.ruaNome);
        // Mark the current node as visited and enqueue it
        visited.put(r1, true);
        queue.add(r1);
        path.allRuasTravelled.add(r1);

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
                //Still not visited this path
                if ( ! visited.get(next.getKey()) )
                {
                    path.cost += next.getValue();
                    // System.out.print("-> " + next.getKey().ruaNome + " " + next.getValue() + "$\n");
                    visited.put(next.getKey(),true);
                    queue.add(next.getKey());
                    path.allRuasTravelled.add(next.getKey());
                }
            }
        }
        return path;
    }

}
