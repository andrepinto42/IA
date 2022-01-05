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
            velocidadeMedia = 10f - peso *0.7f;
    }

    @Override
    public float getVelocidadeMedia(int peso)
    {
        if (pesoMaximo<peso)
            return  -1;
        else
            return  10f - peso *0.7f;
    }
}
