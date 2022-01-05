package BaseDados.Veiculo;

public abstract class Veiculo {
    protected int rating;
    protected int pesoMaximo;
    protected float velocidadeMedia;

    public int getRating() {
        return rating;
    }
    public void setRating(int rating) {
        this.rating = rating;
    }
    public int getPesoMaximo() {
        return pesoMaximo;
    }
    public void setPesoMaximo(int pesoMaximo) {
        this.pesoMaximo = pesoMaximo;
    }
    public float getVelocidadeMedia() {
        return velocidadeMedia;
    }
    public abstract void setVelocidadeMedia(int peso);

}
