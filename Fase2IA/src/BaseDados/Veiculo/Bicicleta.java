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
        if (pesoMaximo<peso)
            velocidadeMedia = -1;
        else
            velocidadeMedia -= peso *0.7f;
    }
}
