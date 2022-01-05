package BaseDados.Encomendas;

import java.time.LocalDate;
import java.util.Date;

import BaseDados.Nodo.Rua;
import BaseDados.Pessoa.Cliente;
import BaseDados.Produtos.Produto;

public class Pedido {
    public Cliente cliente;
    public Produto produto;
    public Date dataInicial;
    public float horasParaEntregar;

    public Pedido(Cliente cliente, String nomeProduto,float preco,int kg, Date dataInicial, float horasParaEntregar) {
        this.cliente = cliente;
        this.produto = new Produto(nomeProduto, preco, kg);
        this.dataInicial = dataInicial;
        this.horasParaEntregar = horasParaEntregar;
    }
    public Pedido(Cliente cliente, Produto produto, Date dataInicial, float horasParaEntregar) {
        this.cliente = cliente;
        this.produto = produto;
        this.dataInicial = dataInicial;
        this.horasParaEntregar = horasParaEntregar;
    }
    
    public Rua getRua()
    {
        return cliente.rua;
    }
    public void Print() {
        StringBuilder sb = new StringBuilder();
        sb.append("Pedido do Cliente ").append(cliente.nome).append(" -> ");
        sb.append(produto.getNomeProduto()).append(" com custo ").append(produto.getPreco());
        sb.append(" | Prazo de entrega ").append(horasParaEntregar).append(" horas");
        System.out.println(sb.toString());
    }
}
