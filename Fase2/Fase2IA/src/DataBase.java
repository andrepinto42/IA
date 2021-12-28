import java.util.ArrayList;
import java.util.List;

import BaseDados.Nodo.Rua;

public class DataBase {
    public List<Rua> allRuas = new ArrayList<Rua>();

    public DataBase()
    {
        Rua r1 = new Rua("rua dos tremoços");
        Rua r2 = new Rua("rua das macas");
        Rua r3 = new Rua("rua dos dinossauros");
        Rua r4 = new Rua("rua de abril");
        Rua r5 = new Rua("rua do Jose");
        Rua r6 = new Rua("rua da Africa");

        allRuas.add(r1);
        allRuas.add(r2);
        allRuas.add(r3);
        allRuas.add(r4);
        allRuas.add(r5);
        allRuas.add(r6);
    }
}
