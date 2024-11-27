package Restaurant.Elaboracion;

public class Paso {
    String data;
    Paso prev;
    Paso next;

    public Paso(String data)
    {
        this.data = data;
        this.prev = null;
        this.next = null;
    }
}
