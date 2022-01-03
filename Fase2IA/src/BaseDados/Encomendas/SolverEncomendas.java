package BaseDados.Encomendas;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

import BaseDados.Nodo.Rua;
import BaseDados.Pessoa.Estafeta;
import BaseDados.Veiculo.Bicicleta;
import BaseDados.Veiculo.Carro;
import BaseDados.Veiculo.Mota;
import Grafos.Grafo;
import Procura.BreadthFirst;
import Procura.DepthFirst;

//import static jdk.nashorn.internal.parser.DateParser.HOUR;

public class SolverEncomendas {
    
    public static void Solve(List<Pedido> listaPedido, Grafo g,Map<String,Estafeta> allEstafetas)
    {
        for (Pedido pedido : listaPedido) {
            Rua ruaParaEntregar = pedido.getRua();
            var path1 = DepthFirst.DFS(g,g.mainRua ,ruaParaEntregar);
            // path1.Print();
            var path2 = BreadthFirst.BFS(g, g.mainRua, ruaParaEntregar);
            // path2.Print();

            if(pedido.produto.getKg()>100) {
                System.out.print("A encomenda ultrapassou o limite de peso!");
                return;
            }

            System.out.print("The best path is ");
            if (path1.cost > path2.cost){

                System.out.println("BFS");

                var e1 = melhorEstafeta(pedido, allEstafetas, path1.cost);
                if(e1!=null) {
                    System.out.println("Encomenda: " + pedido.produto.getNomeProduto() + "Cliente: " + pedido.cliente.getNome() + "\n");
                    System.out.println("Estafeta: " + e1.nome + "Veiculo: "+ e1.veiculo + "\n");
                }
            }
            else{
                System.out.println("DFS");

                var e1 = melhorEstafeta(pedido, allEstafetas, path2.cost);
                if(e1!=null) {
                    System.out.println("Encomenda: " + pedido.produto.getNomeProduto() + "Cliente: " + pedido.cliente.getNome() + "\n");
                    System.out.println("Estafeta: " + e1.nome + "Veiculo: "+ e1.veiculo + "\n");
                }
            }
        }
    }

    public static Estafeta melhorEstafeta (Pedido p, Map<String,Estafeta> map, Float dist){

        int kg1 = p.produto.getKg();

        double v1 = (10-(kg1*0.7));//bicicleta
        double v2 = (35-(kg1*0.5));//mota
        double v3 = (25-(kg1*0.1));//carro

        //Date newDate = new Date((long) (p.dataInicial.getTime() + p.horasParaEntregar * HOUR));

        /*double max = DoubleStream.of(v1, v2, v3)
                .max()
                .getAsDouble();*/

        double tempo1 = dist/v1;
        double tempo2 = dist/v2;
        double tempo3 = dist/v3;

        Estafeta e1 = null;

        for(Estafeta e : map.values()){

            if(tempo1<p.horasParaEntregar && kg1<=5){
                if(e.veiculo instanceof Bicicleta) return e;

            }else if(tempo2<p.horasParaEntregar && kg1<=20){
                if(e.veiculo instanceof Mota) return e;

            }else if(tempo3<p.horasParaEntregar && kg1<=100){
                if(e.veiculo instanceof Carro) return e;

            }
        }

        return e1;
    }

    public static void SolveDFS(List<Pedido> listaPedido, Grafo g)
    {
        for (Pedido pedido : listaPedido) {
            Rua ruaParaEntregar = pedido.getRua();
            var path = DepthFirst.DFS(g,g.mainRua ,ruaParaEntregar);
            path.Print();
        }
    }

    public static void SolveBFS(List<Pedido> listaPedido, Grafo g)
    {
        for (Pedido pedido : listaPedido) {
            Rua ruaParaEntregar = pedido.getRua();
            var path = BreadthFirst.BFS(g, g.mainRua, ruaParaEntregar);
            path.Print();
        }
    }

    private static Estafeta GetRandomEstafeta(Map<String,Estafeta> map)
    {
        Estafeta e1 = null;

        int size = map.size();
        int random =(int) Math.floor(Math.random() * size);
        e1 = map.values().stream().collect(Collectors.toList()).get(random);
        return e1;

    }
}
