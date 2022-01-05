package BaseDados.Veiculo;

public class Mota extends Veiculo{

    public Mota()
    {
        rating = 2;
        pesoMaximo = 20;
        velocidadeMedia = 35f;
    }

    @Override
    public void setVelocidadeMedia(int peso) {
        if (pesoMaximo<peso)
            velocidadeMedia = -1;
        else
            velocidadeMedia = 35f - peso * 0.5f;        
    }

    @Override
    public float getVelocidadeMedia(int peso)
    {
        if (pesoMaximo<peso)
            return  -1;
        else
            return  35f - peso *0.7f;
    }
}