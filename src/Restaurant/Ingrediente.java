package Restaurant;

public class Ingrediente extends DataInDB {
    private double cantidad;
    private String unidades;

    public Ingrediente(int id, String nombre, double cantidad, String unidades) {
        super(id, nombre);
        this.cantidad = cantidad;
        this.unidades = unidades;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public String getUnidades() {
        return unidades;
    }

    public void printIngrediente() {
        System.out.print("Ingrediente: " + getNombre());
        if (cantidad != 0) {
            System.out.print(" | Cantidad: " + cantidad);
        }
        if (unidades != null) {
            System.out.print(" | Unidades: " + unidades);
        }
        System.out.println();
    }
}
