package uts.tugas.tutor.paket;

public class Obj_Paket
{
    String id;
    String name;

    public Obj_Paket(String id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public String getId()
    {
        return id;
    }

    @Override
    public String toString()
    {
        return name;
    }
}
