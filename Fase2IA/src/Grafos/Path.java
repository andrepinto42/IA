package Grafos;

import java.util.ArrayList;
import java.util.List;

import BaseDados.Nodo.Rua;

public class Path {
    public List<Rua> allRuasTravelled;
    public float cost;

    public Path()
    {
        allRuasTravelled = new ArrayList<Rua>();
        cost = 0f;
    }

    public void Print()
    {
        System.out.println("\n-----Starting at " + allRuasTravelled.get(0).ruaNome + "------");

        for (int i = 1; i < allRuasTravelled.size(); i++) {
            System.out.println("From "+ allRuasTravelled.get(i-1).ruaNome + " to " + allRuasTravelled.get(i).ruaNome);
        }

        System.out.println("Distance = " + cost + "km\n");
    }
}
