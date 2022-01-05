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



public class IterativeSearch {
    static Rua stoppingRua;
    static Map<Rua,Pai> pathway;
    static Map<Rua,Boolean> visited ;
    static List<Rua> allRuasTravelled;
    static LinkedList<Rua> queue;

    public static boolean enableDebug = false;

    public static Path Search(Grafo g)
    {
        return Search(g,g.mainRua,null,3);
    }
    public static Path Search(Grafo g,Rua r,int maxLevel)
    {
        return Search(g,r,null,maxLevel);
    }

    public static Path Search(Grafo g,Rua r1,Rua r2,int maxLevel)
    {
        boolean enableStoping = true;
        if (r2 == null)
            enableStoping = false;
        
        Path path = new Path();
        path.cost = 0f;

        if (enableDebug) System.out.println("\n---- ALGORITHM-----\n");

        // Mark all the vertices as not visited(By default
        // set as false)
        visited = new HashMap<Rua,Boolean>();
        for (Rua novo: g.caminhos.keySet()) {
            visited.put(novo, false);
        }

       allRuasTravelled = new ArrayList<Rua>();

        // Create a queue for BFS
        queue = new LinkedList<Rua>();
 
        if (enableDebug) System.out.println("Starting at " + r1.ruaNome);
        // Mark the current node as visited and enqueue it
        visited.put(r1, true);
        queue.add(r1);
        allRuasTravelled.add(r1);

        //Inicializar a pathway com a rua a começar sendo a rua que não tem Pai
        pathway = new HashMap<Rua,Pai>();
        pathway.put(r1,null);

        boolean found =Search(g, r2, enableStoping, path,maxLevel);

        if (enableStoping)
        {
            if (!found)
            {
                path = new Path();
                path.cost = -1f;
                path.pathToTravel = new Stack<Rua>();
            }
            else
            {
                path = GetPath(r1,r2);
            }

            path.allRuasTravelled = allRuasTravelled;
        }
        
        return path;
    }

    private static boolean Search(Grafo g, Rua r2, boolean enableStoping, Path path,int maxLevel) 
    {
        int level = 0;
        Rua r1;
        while (queue.size() != 0 && level <maxLevel)
        {
            // Dequeue a vertex from queue and print it
            r1 = queue.poll();
            
            //Found the rua we were looking after
            if (enableStoping && r1.equals(r2))
            {
                return true;
            }

            for (var next : g.caminhos.get(r1).entrySet()) {  
                
                Rua nextRua = next.getKey();
                float nextCost = next.getValue();
                
                //Still not visited this path
                if ( ! visited.get(nextRua) )
                {
                    if (enableDebug) System.out.print("from " + r1.ruaNome + "-> " + nextRua.ruaNome + " " + nextCost + "$\n");
                    visited.put(nextRua,true);
                    queue.add(nextRua);
                    allRuasTravelled.add(nextRua);
                    
                    path.cost += nextCost;

                    pathway.put(nextRua,new Pai(r1,nextCost));
                }
            }
            //Done searching
            level++;
        }
        return false;
    }


    private static Path GetPath(Rua r1,Rua r2) {
        Path p = new Path();

        Stack<Rua> stackPath = new Stack<Rua>();
        
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
        p.SetPathToTravel(stackPath);
        
        return p;
    }

}
