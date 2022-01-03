package BaseDados.Produtos;

public  class Produto {
    
    protected String nomeProduto;
    protected float preco;
    protected Integer kg;
    
    public Produto(String nomeProduto2, float preco2, int kg2) {
        nomeProduto = nomeProduto2;
        preco = preco2;
        kg = kg2;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    public int getKg() {
        return kg;
    }

    public void setKg(int kg) {
        this.kg = kg;
    }
}
