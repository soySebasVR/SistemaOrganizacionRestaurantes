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

    public String getData() {
        return data;
    }

    public Paso getNext() {
        return next;
    }

    public Paso getPrev() {
        return prev;
    }
}
