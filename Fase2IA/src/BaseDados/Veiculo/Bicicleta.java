package BaseDados.Veiculo;

public class Bicicleta extends Veiculo{

    public Bicicleta()
    {
        rating = 3;
        pesoMaximo = 5;
        velocidadeMedia = 10f;
    }

    @Override
    public void setVelocidadeMedia(int peso) {

        velocidadeMedia -= peso *0.7f;
    }
}
