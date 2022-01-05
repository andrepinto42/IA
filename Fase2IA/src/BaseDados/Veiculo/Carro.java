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
        if (pesoMaximo<peso)
            velocidadeMedia = -1;
        else
            velocidadeMedia = 25f - peso * 0.1f;        
    }
    
    @Override
    public float getVelocidadeMedia(int peso)
    {
        if (pesoMaximo<peso)
            return  -1;
        else
            return  25f - peso *0.7f;
    }
}