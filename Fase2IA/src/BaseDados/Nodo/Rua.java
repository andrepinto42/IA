package BaseDados.Nodo;

public class Rua {
    public String ruaNome;

    public Rua(String r)
    {
        ruaNome = r;
    }
    @Override
    public boolean equals(Object obj) {
       
        if (!(obj instanceof Rua)) return false;

        return ((Rua) obj).ruaNome.equals(this.ruaNome); 
    }
}
