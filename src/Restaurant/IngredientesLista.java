package Restaurant;

import java.util.ArrayList;

public class IngredientesLista {
    private final ArrayList<Ingrediente> ingredientes;

    public IngredientesLista() {
        ingredientes = new ArrayList<Ingrediente>();
    }

    public ArrayList<Ingrediente> getIngredientes() {
        return ingredientes;
    }

    public void addIngrediente(int id, String nombre, double cantidad, String unidades) {
        int inList = search(nombre);
        if (inList == -1) {
            ingredientes.add(new Ingrediente(id, nombre, cantidad, unidades));
            quickSort(0, ingredientes.size() - 1);
        } else {
            ingredientes.set(inList, new Ingrediente(id, nombre, cantidad, unidades));
        }
    }

    public void deleteIngrediente(String nombre) {
        int inList = search(nombre);
        if (inList != -1) {
            ingredientes.remove(inList);
        }
    }

    private void quickSort(int low, int high) {
        if (low < high) {
            int pivotIndex = partition(low, high);
            quickSort(low, pivotIndex - 1);
            quickSort(pivotIndex + 1, high);
        }
    }

    private int partition(int low, int high) {
        String pivot = ingredientes.get(high).getNombre();
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (ingredientes.get(j).getNombre().compareTo(pivot) > 0) {
                i++;
                Ingrediente temp = ingredientes.get(i);
                ingredientes.set(i, ingredientes.get(j));
                ingredientes.set(j, temp);
            }
        }
        Ingrediente temp = ingredientes.get(i + 1);
        ingredientes.set(i + 1, ingredientes.get(high));
        ingredientes.set(high, temp);
        return i + 1;
    }

    public int search(String nombre) {
        int inicio = 0;
        int fin = ingredientes.size() - 1;

        while (inicio <= fin) {
            int medio = inicio + (fin - inicio) / 2;

            if (ingredientes.get(medio).getNombre().equalsIgnoreCase(nombre)) {
                return medio;
            }
            if (ingredientes.get(medio).getNombre().compareToIgnoreCase(nombre) > 0) {
                inicio = medio + 1;
            } else {
                fin = medio - 1;
            }
        }
        return -1;
    }
}
