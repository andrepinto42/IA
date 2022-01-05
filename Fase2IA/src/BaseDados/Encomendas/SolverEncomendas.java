package BaseDados.Encomendas;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

import javax.lang.model.util.ElementScanner6;

import BaseDados.Nodo.Rua;
import BaseDados.Pessoa.Estafeta;
import BaseDados.Veiculo.Bicicleta;
import BaseDados.Veiculo.Carro;
import BaseDados.Veiculo.Mota;
import BaseDados.Veiculo.Veiculo;
import Grafos.Grafo;
import Procura.AStar;
import Procura.BreadthFirst;
import Procura.DepthFirst;

//import static jdk.nashorn.internal.parser.DateParser.HOUR;

public class SolverEncomendas {
    
    public static void Solve(List<Pedido> listaPedido, Grafo g,Map<String,Estafeta> allEstafetas) {

        Scanner scan = new Scanner(System.in);

        for (Pedido pedido : listaPedido) {

            if(pedido.produto.getKg()>100) {
                System.out.print("A encomenda ultrapassou o limite de peso!");
                return;
            }
            System.out.println("Encomenda: " + pedido.produto.getNomeProduto() + " | Cliente: " + pedido.cliente.getNome() + "\n");

            float a = printDFS(pedido,g,allEstafetas);
            float b = printBFS(pedido,g,allEstafetas);
            float c = printAStar(pedido,g,allEstafetas);

            double min = DoubleStream.of(a, b, c)
                    .min()
                    .getAsDouble();

            System.out.print("Melhor algoritmo: ");
            if (min == a){
                System.out.println("DFS");
            }else if(min == b){
                System.out.println("BFS");
            }else if(min == c) {
                System.out.println("A*");
            }else
                System.out.println("Greedy");

        }
    }
    public static float printDFS(Pedido p,Grafo g, Map<String,Estafeta> allEstafetas){
        Rua ruaParaEntregar = p.getRua();
        var path1 = DepthFirst.DFS(g,g.mainRua ,ruaParaEntregar);

        System.out.println("------------------------------------/");
        System.out.println("Algoritmo: Depth First \n");
        path1.Print();
        var e1 = melhorEstafeta(p, allEstafetas, path1.cost);
        assert e1 != null: "Nenhum estafeta encontrado!";
        System.out.println("Estafeta: " + e1.nome + " | Melhor Veiculo: "+ e1.veiculo + "\n");

        return path1.cost;
    }

    public static float printBFS(Pedido p,Grafo g, Map<String,Estafeta> allEstafetas){
        Rua ruaParaEntregar = p.getRua();
        var path1 = BreadthFirst.BFS(g, g.mainRua, ruaParaEntregar);

        System.out.println("------------------------------------/");
        System.out.println("Algoritmo: Depth First \n");
        path1.Print();
        var e1 = melhorEstafeta(p, allEstafetas, path1.cost);
        assert e1 != null: "Nenhum estafeta encontrado!";
        System.out.println("Estafeta: " + e1.nome + " | Melhor Veiculo: "+ e1.veiculo + "\n");

        return path1.cost;
    }

    public static float printAStar(Pedido p,Grafo g, Map<String,Estafeta> allEstafetas){
        Rua ruaParaEntregar = p.getRua();
        var path1 = AStar.AStarFind(g, g.mainRua,ruaParaEntregar);

        System.out.println("------------------------------------/");
        System.out.println("Algoritmo: Depth First \n");
        path1.Print();
        var e1 = melhorEstafeta(p, allEstafetas, path1.cost);
        assert e1 != null: "Nenhum estafeta encontrado!";
        System.out.println("Estafeta: " + e1.nome + " | Melhor Veiculo: "+ e1.veiculo + "\n");

        return path1.cost;
    }

    public static void SolveListaPedidos(List<Pedido> listaPedido, Grafo g,Map<String,Estafeta> allEstafetas){

        for(Pedido pedido : listaPedido){
            //Rua ruaParaEntregar = pedido.getRua();
        }


    }

    public static Estafeta melhorEstafetaEcologico(Pedido p, Map<String,Estafeta> map, Float dist){
        int pesoEncomenda = p.produto.getKg();
        float horasParaEntregar = p.horasParaEntregar;
        Estafeta bestEstafeta = null;
        for (Estafeta e : map.values()) {
            
            boolean valid = CheckIfValid(pesoEncomenda, e, dist, horasParaEntregar);
            
            if (!valid) continue;
            float horasAtuaisParaEntregar = GetHoursToDeliver(pesoEncomenda, e, dist);
            
            if (horasAtuaisParaEntregar > horasParaEntregar)
            {
               //Estafeta nao vai chegar a horas
                continue;
            }
            if (bestEstafeta == null)
            {
                bestEstafeta = e;
                continue;
            }
            if (bestEstafeta.veiculo instanceof Bicicleta)
                continue;
            
            if (bestEstafeta.veiculo instanceof Mota && !(e.veiculo instanceof Carro) )
                bestEstafeta = e;
            else 
                bestEstafeta = e;
            
        }
        return bestEstafeta;
    }

    public static Estafeta melhorEstafetaTempo(Pedido p, Map<String,Estafeta> map, Float dist){

        int pesoEncomenda = p.produto.getKg();
        float horasParaEntregar = p.horasParaEntregar;
        float melhorTempoParaEntrega = 0f;
        Estafeta bestEstafeta = null;
        for (Estafeta e : map.values()) {

            boolean valid = CheckIfValid(pesoEncomenda, e, dist, horasParaEntregar);
            
            if (!valid) continue;
            float horasAtuaisParaEntregar = GetHoursToDeliver(pesoEncomenda, e, dist);

            if (bestEstafeta == null)
            {
                melhorTempoParaEntrega = horasAtuaisParaEntregar;
                bestEstafeta = e;
            }
            else
            {
                if (melhorTempoParaEntrega> horasAtuaisParaEntregar)
                {
                    melhorTempoParaEntrega = horasAtuaisParaEntregar;
                    bestEstafeta = e;
                }
            }
        }
        return bestEstafeta;
    }
    public static Estafeta melhorEstafeta (Pedido p, Map<String,Estafeta> map, Float dist){

        int kg1 = p.produto.getKg();

        double tempoB=dist/(10-(kg1*0.7));
        double tempoM=dist/(35-(kg1*0.5));
        double tempoC=dist/(25-(kg1*0.1));
        //Date newDate = new Date((long) (p.dataInicial.getTime() + p.horasParaEntregar * HOUR));

        for(Estafeta e : map.values()){

            if(kg1<=5 && tempoB<p.horasParaEntregar){
                if(e.veiculo instanceof Bicicleta){
                    if (!e.emUso) return e;
                }else continue;

            }else if(kg1<=20 && tempoM<p.horasParaEntregar){
                if(e.veiculo instanceof Mota){
                    if (!e.emUso) return e;
                }else continue;
            }else if(kg1<=100 && tempoC<p.horasParaEntregar){
                if(e.veiculo instanceof Carro){
                    if (!e.emUso) return e;
                }else continue;

            }else if(kg1<=20 && tempoM>p.horasParaEntregar){
                if(e.veiculo instanceof Mota){
                    if (!e.emUso) {
                        System.out.println("A encomenda será entregue mas fora do prazo esatbelecido!\n");
                        return e;
                    }
                }else continue;

            }else if(kg1<=100 && tempoC>p.horasParaEntregar) {
                if (e.veiculo instanceof Carro) {
                    if (!e.emUso) {
                        System.out.println("A encomenda será entregue mas fora do prazo esatbelecido!\n");
                        return e;
                    }
                }else continue;
            }
        }
        //Nao sei o que colocar aqui
        return null;
        
        // int kg1 = p.produto.getKg();
        // //Date newDate = new Date((long) (p.dataInicial.getTime() + p.horasParaEntregar * HOUR));

        // for(Estafeta e : map.values()){
        //     //tenta entregar com o veiculo mais ecologico
        //     if(e.veiculo instanceof Bicicleta && getTempo(e,dist,kg1)<p.horasParaEntregar && kg1<=5) {
        //         if (!e.emUso) return e;
        //     }

        //     else if(e.veiculo instanceof Mota && getTempo(e,dist,kg1)<p.horasParaEntregar && kg1<=20) {
        //         if (!e.emUso) return e;
        //     }

        //     else if(e.veiculo instanceof Carro && getTempo(e,dist,kg1)<p.horasParaEntregar && kg1<=100) {
        //         if (!e.emUso) return e;
        //     }

        //     //ultrapassa o tempo, tenta entregar com o veiculo mais rapido (se o peso for aceitavel)
        //     else if(e.veiculo instanceof Mota && getTempo(e,dist,kg1)>p.horasParaEntregar && kg1<=20) {
        //         if (!e.emUso) {
        //             System.out.println("A encomenda será entregue mas fora do prazo esatbelecido!\n");
        //             return e;
        //         }
        //     }
        //     else if (e.veiculo instanceof Carro && getTempo(e,dist,kg1)>p.horasParaEntregar && kg1<=100) {
        //         if (!e.emUso) {
        //             System.out.println("A encomenda será entregue mas fora do prazo esatbelecido!\n");
        //             return e;
        //         }
        //     }
        // }
        // return null;
    }

    public static void SolveDFS(List<Pedido> listaPedido, Grafo g,Map<String,Estafeta> allEstafetas)
    {
        System.out.println("\n-----------------DFS-----------------\n");
        for (Pedido pedido : listaPedido) {
            Rua ruaParaEntregar = pedido.getRua();
            int peso = pedido.produto.getKg();
            var path = DepthFirst.DFS(g,g.mainRua ,ruaParaEntregar);
            System.out.println("\nInicio:\n");
           
            for (Estafeta e : allEstafetas.values()) {
                if (e.veiculo.getPesoMaximo() < peso)
                {
                    System.out.println("Estafeta " + e.nome + 
                    " nao consegue entregar pois tem carga a mais para " + e.veiculo.getClass().getSimpleName());
                    continue;
                }
                float velocidadeMedia = e.GetVelocidadeMedia(peso);
                //Neste momento o estafeta tem de ir para o local de entrega e regressar
                float tempo = (path.cost * 2) / velocidadeMedia;
                System.out.println("Estafeta " + e.nome + " de " + e.veiculo.getClass().getSimpleName() + " demora "
                + tempo + " horas" );
            }
        

            Estafeta best = melhorEstafetaTempo(pedido, allEstafetas, path.cost);
            System.out.println("Estafeta " + best.nome + " é o mais RAPIDO para entregar ");
            Estafeta eco = melhorEstafetaEcologico(pedido, allEstafetas, path.cost);
            System.out.println("Estafeta " + eco.nome + " é o mais ECOLOGICO para entregar ");

            path.PrintPath();
            path.ReversePath();
            path.PrintPath();
        }
    }

    public static void SolveBFS(List<Pedido> listaPedido, Grafo g,Map<String,Estafeta> allEstafetas)
    {
        for (Pedido pedido : listaPedido) {
            Rua ruaParaEntregar = pedido.getRua();
            var path = BreadthFirst.BFS(g, g.mainRua, ruaParaEntregar);
            path.Print();
        }
    }

    /*private static Estafeta GetRandomEstafeta(Map<String,Estafeta> map)
    {
        Estafeta e1 = null;

        int size = map.size();
        int random =(int) Math.floor(Math.random() * size);
        e1 = map.values().stream().collect(Collectors.toList()).get(random);
        return e1;

    }*/

    private static boolean CheckIfValid(int pesoEncomenda,Estafeta e,float dist,float horasParaEntregar)
    {
        if (pesoEncomenda > e.veiculo.getPesoMaximo())
        {
            //System.out.println("Estafeta nao consegue transportar esse peso :(");
            return false;
        }

        float horasAtuaisParaEntregar = GetHoursToDeliver(pesoEncomenda, e, dist);

        if (horasAtuaisParaEntregar > horasParaEntregar)
        {
            //System.out.println("Estafeta nao vai chegar a horas :(");
            return false;
        }
        return true;
    }

    private static float GetHoursToDeliver(int peso, Estafeta e,float dist)
    {
        float veloMedia = e.GetVelocidadeMedia( peso);
        float horasAtuaisParaEntregar = dist / veloMedia;
        return horasAtuaisParaEntregar;
    }
}
