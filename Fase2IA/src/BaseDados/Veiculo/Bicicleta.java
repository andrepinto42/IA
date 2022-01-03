package BaseDados.Veiculo;

public class Bicicleta extends Veiculo{

    public Bicicleta()
    {
        rating = 3;
        pesoMaximo = 5;
        velocidadeMedia = 10f;
    }

    @Override
    protected void setVelocidadeMedia(float peso) {

        velocidadeMedia -= peso *0.7f;
    }
}
