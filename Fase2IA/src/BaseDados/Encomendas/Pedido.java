package BaseDados.Encomendas;

import java.time.LocalDate;

import BaseDados.Nodo.Rua;
import BaseDados.Pessoa.Cliente;
import BaseDados.Produtos.Produto;

public class Pedido {
    public Cliente cliente;
    public Produto produto;
    public LocalDate dataInicial;
    public float horasParaEntregar;

    public Pedido(Cliente cliente, String nomeProduto,float preco, LocalDate dataInicial, float horasParaEntregar) {
        this.cliente = cliente;
        this.produto = new Produto(nomeProduto, preco);
        this.dataInicial = dataInicial;
        this.horasParaEntregar = horasParaEntregar;
    }
    public Pedido(Cliente cliente, Produto produto, LocalDate dataInicial, float horasParaEntregar) {
        this.cliente = cliente;
        this.produto = produto;
        this.dataInicial = dataInicial;
        this.horasParaEntregar = horasParaEntregar;
    }
    
    public Rua getRua()
    {
        return cliente.rua;
    }
}
