package Restaurant;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Restaurant {
    private ArrayList<Ingrediente> inventario;
    private ArrayList<Receta> recetas;

    public Restaurant() {
        inventario = new ArrayList<>();
        recetas = new ArrayList<>();
    }

    public void loadFromDB() throws FileNotFoundException {
        // recetas
        File fileRecetas = new File("db/recetas/recetas.txt");
        int id = 0;
        try (Scanner myReader = new Scanner(fileRecetas)) {
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                recetas.add(new Receta(id, data));
                id++;
            }
        }
        quickSortReceta(recetas, 0, recetas.size() - 1);

        // inventario
        File fileInventario = new File("db/inventario.csv");
        id = 0;
        try (Scanner myReader = new Scanner(fileInventario)) {
            myReader.nextLine();
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String regex = ",";
                String[] myArray = data.split(regex);
                inventario.add(new Ingrediente(id, String.valueOf(myArray[1]), Double.parseDouble(myArray[0]), ""));
                id++;
            }
        }
        quickSortInventario(inventario, 0, inventario.size() - 1);
    }

    private void quickSortReceta(ArrayList<Receta> array, int low, int high) {
        if (low < high) {
            int pivotIndex = partitionReceta(array, low, high);
            quickSortReceta(array, low, pivotIndex - 1);
            quickSortReceta(array, pivotIndex + 1, high);
        }
    }

    private int partitionReceta(ArrayList<Receta> array, int low, int high) {
        String pivot = array.get(high).getNombre();
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (array.get(j).getNombre().compareTo(pivot) > 0) {
                i++;
                Receta temp = array.get(i);
                array.set(i, array.get(j));
                array.set(j, temp);
            }
        }
        Receta temp = array.get(i + 1);
        array.set(i + 1, array.get(high));
        array.set(high, temp);
        return i + 1;
    }

    private void quickSortInventario(ArrayList<Ingrediente> array, int low, int high) {
        if (low < high) {
            int pivotIndex = partitionInventario(array, low, high);
            quickSortInventario(array, low, pivotIndex - 1);
            quickSortInventario(array, pivotIndex + 1, high);
        }
    }

    private int partitionInventario(ArrayList<Ingrediente> array, int low, int high) {
        String pivot = array.get(high).getNombre();
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (array.get(j).getNombre().compareTo(pivot) > 0) {
                i++;
                Ingrediente temp = array.get(i);
                array.set(i, array.get(j));
                array.set(j, temp);
            }
        }
        Ingrediente temp = array.get(i + 1);
        array.set(i + 1, array.get(high));
        array.set(high, temp);
        return i + 1;
    }
}
