package Grafos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import BaseDados.Nodo.Rua;

public class Grafo {
    public Rua mainRua;
    // Mapa de rua e cada key corresponde a uma lista de proximas ruas
    public Map<Rua,HashMap<Rua,Float>> caminhos = new HashMap<Rua,HashMap<Rua,Float>>();

    public Grafo()
    {      
    }

    public void setMainRua(Rua r1)
    {
        mainRua = r1;
    }

    public void addRua(Rua r1)
    {
        caminhos.put(r1, new HashMap<Rua,Float>());
    }
    
    public void addEdge(Rua r1, Rua r2,Float value)
    {
        caminhos.get(r1).put(r2,value);
        caminhos.get(r2).put(r1,value);
    }

    public void GetEverything()
    {
        for (Rua rua : getAllRuas()) {
            System.out.println(rua.ruaNome + " tem os seguintes caminhos : \n");
            for (var entry : getNextRuas(rua).entrySet()){
                System.out.println( entry.getKey().ruaNome + " |Cost = " + entry.getValue() );
            }
            System.out.println("-------------\n");
        }
    }

    public List<Rua> getAllRuas()
    {
        List<Rua> l = new  ArrayList<Rua>(caminhos.keySet());
        return l;
    }

    public Map<Rua,Float> getNextRuas(Rua r)
    {
        return caminhos.get(r);
    }
}
