package Restaurant;

public class DataInDB {
    private int id;
    private String nombre;

    public DataInDB(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}
