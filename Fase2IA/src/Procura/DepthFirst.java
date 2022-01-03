package Procura;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import BaseDados.Nodo.Rua;
import Grafos.Grafo;
import Grafos.Path;

public class DepthFirst {
    static Grafo g;
    static List<Rua> pathRuas;
    static float cost;
    static boolean canStop;
    static boolean keepLooking;
    
    static void DFSUtil(Rua r1,Rua r2,Map<Rua,Boolean> visited)
    {
        visited.put(r1,true);
        pathRuas.add(r1);
         
        // Recur for all the vertices adjacent to this vertex
        Map<Rua,Float> newSearch =  g.caminhos.get(r1);
        
        if (canStop && r1.equals(r2))
        {
            //Rua foi encontrada
            keepLooking = false;
            return;
        }
        for (var entry : newSearch.entrySet()) {
            Rua next = entry.getKey();
            Float costNext = entry.getValue();
            
         
            if (keepLooking && !visited.get(next))
            {
                cost += costNext;
                // System.out.print(" -> " + next.ruaNome + " " +costNext + "$\n");
                DFSUtil(next,r2,visited);    
            }
        }
    }

    public static Path DFS(Grafo graph)
    {
        return DFS(graph, graph.mainRua);
    }


    public static Path  DFS(Grafo graph, Rua r)
    {
        return  DFS(graph,r,null);
    }


    public static Path  DFS(Grafo graph, Rua r1,Rua r2)
    {
        //Se a rua destino for nula o programa nao pode parar
        canStop = !(r2 == null);
        keepLooking = true;

        Path path = new Path();
        pathRuas = new ArrayList<Rua>();
        cost = 0f;
        g = graph;
        
        // Mark all the vertices as not visited
        Map<Rua,Boolean> visited = new HashMap<Rua,Boolean>();

        for (Rua novo: g.caminhos.keySet()) {
            visited.put(novo, false);
        }
 
        // System.out.println("\n----DFS ALGORITHM-----\n");
        // System.out.println("Starting in " + r.ruaNome);
        DFSUtil(r1,r2, visited);

        path.cost = cost;
        path.allRuasTravelled = pathRuas;
        return path;
    }
}
