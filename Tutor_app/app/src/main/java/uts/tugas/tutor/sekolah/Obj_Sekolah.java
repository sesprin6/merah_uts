package uts.tugas.tutor.sekolah;

public class Obj_Sekolah
{
    String id;
    String name;

    public Obj_Sekolah(String id, String name)
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
