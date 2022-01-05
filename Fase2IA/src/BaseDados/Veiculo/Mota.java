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
            velocidadeMedia -= peso * 0.5f;        
    }
}