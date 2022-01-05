package BaseDados.Veiculo;

public class Mota extends Veiculo{

    public Mota()
    {
        rating = 2;
        pesoMaximo = 20;
        velocidadeMedia = 35f;
    }

    @Override
    public void setVelocidadeMedia(float peso) {
        velocidadeMedia -= peso * 0.5f;        
    }
}