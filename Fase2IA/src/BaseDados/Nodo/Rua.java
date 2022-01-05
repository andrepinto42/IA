package BaseDados.Nodo;

public class Rua implements Comparable<Rua>{
    public String ruaNome;
    public float x;
    public float y;
   
    public Rua(String r,float x,float y)
    {
        ruaNome = r;
        this.x = x;
        this.y = y;
    }
    @Override
    public boolean equals(Object obj) {
       
        if (!(obj instanceof Rua)) return false;

        return ((Rua) obj).ruaNome.equals(this.ruaNome); 
    }
    


    private float gCost = Float.MAX_VALUE;//Distance to begin node
    private float hCost = Float.MIN_VALUE;//Distance to end node;

    public float GetCostF()
    {
        return (gCost + hCost);
    }
    public void SetCostG(float g)
    {
        gCost = g;
    }
    public void SetCostH(float h)
    {
        hCost = h;
    }
    public float GetCostH()
    {
        return hCost;
    }
    @Override
    public int compareTo(Rua another) {
        
        return (int) (GetCostF() - another.GetCostF());
    }

    public static float DistanceRuas(Rua r1,Rua r2)
    {
        return Distance(r1.x, r1.y, r2.x, r2.y);
    }


    public static float Distance(float x1,float y1,float x2,float y2)
    {
        return (float) Math.sqrt( (x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
    }
}
