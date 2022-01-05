package BaseDados.Veiculo;

public class Carro extends Veiculo{

    public Carro()
    {
        rating = 1;
        pesoMaximo = 100;
        velocidadeMedia = 25f;
    }

    @Override
    public void setVelocidadeMedia(int peso) {
        velocidadeMedia -= peso * 0.1f;        
    }
}