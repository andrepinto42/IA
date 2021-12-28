package BaseDados.Nodo;

public class Caminho {
    public Rua rua1;
    public Rua rua2;
    
    public Caminho(Rua r1,Rua r2)
    {
        rua1 = r1;
        rua2 = r2;
    }
    public Caminho(String r1,String r2)
    {
        rua1 = new Rua(r1);
        rua2 = new Rua(r2);
    }
}
