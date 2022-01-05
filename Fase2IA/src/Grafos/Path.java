package Grafos;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import BaseDados.Nodo.Rua;

public class Path {
    public List<Rua> allRuasTravelled;
    public float cost;
    public Stack<Rua> pathToTravel;
    public Path()
    {
        allRuasTravelled = new ArrayList<Rua>();
        cost = 0f;
    }

    public void Print()
    {
        System.out.println("\n-----Algoritmo começou em " + allRuasTravelled.get(0).ruaNome + "------");

        for (int i = 1; i < allRuasTravelled.size(); i++) {
            System.out.println("Visited -> " + allRuasTravelled.get(i).ruaNome);
        }

        PrintPath();
    }

    public void PrintPath()
    {
        System.out.println("Caminho a viajar :");
        
        Stack<Rua> clone = (Stack<Rua>) pathToTravel.clone();

        while(!clone.isEmpty())
        {
            System.out.print( clone.pop().ruaNome + " -> ");
        }

        System.out.println("Distance = " + cost + "km");
    }

    public Stack<Rua> GetPathToTravel()
    {
        return (Stack<Rua>) pathToTravel.clone();
    }

    public void SetPathToTravel(Stack<Rua> stackPath) {
        pathToTravel = stackPath;
    }

    public void ReversePath()
    {
        Stack<Rua> reversedStack = new Stack<Rua>();
        while(!pathToTravel.empty())
        {
            reversedStack.push(pathToTravel.pop());
        }
        pathToTravel = reversedStack;
    }
}