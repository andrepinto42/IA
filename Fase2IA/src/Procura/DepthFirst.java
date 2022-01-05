package Procura;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

import BaseDados.Nodo.Pai;
import BaseDados.Nodo.Rua;
import Grafos.Grafo;
import Grafos.Path;

public class DepthFirst {
    static Grafo g;
    static List<Rua> pathRuas;
    static Stack<Rua> pathway;
    static boolean canStop;
    static boolean keepLooking;
    static boolean enableDebug = false;
    
    private static void DFSUtil(Rua r1,Rua r2,Map<Rua,Boolean> visited)
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
                pathway.push(next);
                if (enableDebug) System.out.print("From " + r1.ruaNome + " -> " + next.ruaNome + " " +costNext + "$\n");
                DFSUtil(next,r2,visited);    
            }
        }
        if (keepLooking)
            pathway.pop();
        
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

        pathway = new Stack<Rua>();
        Path path = new Path();
        pathRuas = new ArrayList<Rua>();
        g = graph;
        
        // Mark all the vertices as not visited
        Map<Rua,Boolean> visited = new HashMap<Rua,Boolean>();

        for (Rua novo: g.caminhos.keySet()) {
            visited.put(novo, false);
        }
 
        if (enableDebug) System.out.println("\n----DFS ALGORITHM-----\n");
        if (enableDebug) System.out.println("Starting in " + r1.ruaNome);
        
       

        DFSUtil(r1,r2, visited);



        path.allRuasTravelled = pathRuas;
        path.cost = GetCost(pathway,g);

        // Stack<Rua> reversedStack = new Stack<Rua>();
        // while(!pathway.empty())
        // {
        //     reversedStack.push(pathway.pop());
        // }
        // reversedStack.push(r1);
        path.SetPathToTravel(pathway);
        path.ReversePath();
        path.pathToTravel.push(r1);
        

        if (enableDebug) System.out.println("DISTANCIA TOTAL = " + path.cost);
        return path;
    }



    private static float GetCost(Stack<Rua> pathway,Grafo g)
    {
        float cost = 0f;
        Rua startingRua = g.mainRua;

        for (Rua rua : pathway) {
            cost += g.caminhos.get(startingRua).get(rua);
            startingRua = rua;
        }
        return cost;
    }

}
