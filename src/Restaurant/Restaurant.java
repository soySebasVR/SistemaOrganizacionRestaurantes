package Restaurant;

import Restaurant.Elaboracion.ListaElaboracion;
import Restaurant.Elaboracion.Paso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Restaurant {
    private final ArrayList<Ingrediente> inventario;
    private final ArrayList<Receta> recetas;

    public Restaurant() {
        inventario = new ArrayList<>();
        recetas = new ArrayList<>();
    }

    public ArrayList<Ingrediente> getInventario() {
        return inventario;
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

    public void saveReceta(String recetaNombre, IngredientesLista ingredientes, ListaElaboracion elaboracion) {
        int idReceta = recetas.size() + 1;
        recetas.add(new Receta(idReceta, recetaNombre));
        quickSortReceta(recetas, 0, recetas.size() - 1);

        File fileRecetas = new File("db/recetas/recetas.txt");
        ArrayList<String> recetasNombres = new ArrayList<>();
        try (Scanner myReader = new Scanner(fileRecetas)) {
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                recetasNombres.add(data);
            }
        } catch (FileNotFoundException e) {
            System.err.println("No se encontró archivo");
        }
        recetasNombres.add(recetaNombre);
        try (FileWriter fw = new FileWriter(fileRecetas)) {
            for (String s : recetasNombres) {
                fw.write(s + "\n");
            }
            fw.flush();
        } catch (IOException e) {
            System.err.println("Error al guardar en BD");
            e.printStackTrace();
        }

        File fileIngredientes = new File("db/recetas/ingredientes/" + idReceta + ".csv");
        try (FileWriter fw = new FileWriter(fileIngredientes)) {
            fw.write("cantidad,unidad,ingrediente\n");
            for (Ingrediente i : ingredientes.getIngredientes()) {
                fw.write(i.getCantidad() + "," + i.getUnidades() + "," + i.getNombre() + "\n");
            }
            fw.flush();
        } catch (IOException e) {
            System.err.println("Error al guardar en BD");
            e.printStackTrace();
        }

        File fileElaboracion = new File("db/recetas/elaboracion/" + idReceta + ".txt");
        try (FileWriter fw = new FileWriter(fileElaboracion)) {
            Paso current = elaboracion.getHead();
            while (current != null) {
                fw.write(current.getData() + "\n");
                current = current.getNext();
            }
            fw.flush();
        } catch (IOException e) {
            System.err.println("Error al guardar en BD");
            e.printStackTrace();
        }
    }

    public void updateInventario(ArrayList<Ingrediente> toUpdate) {
        for (Ingrediente upd : toUpdate) {
            Ingrediente old = searchInventario(upd.getNombre());
            old.setCantidad(old.getCantidad() - upd.getCantidad());
        }
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

    public Ingrediente searchInventario(String nombre) {
        int inicio = 0;
        int fin = inventario.size() - 1;

        while (inicio <= fin) {
            int medio = inicio + (fin - inicio) / 2;

            if (inventario.get(medio).getNombre().equalsIgnoreCase(nombre)) {
                return inventario.get(medio);
            }
            if (inventario.get(medio).getNombre().compareToIgnoreCase(nombre) > 0) {
                inicio = medio + 1;
            } else {
                fin = medio - 1;
            }
        }
        return null;
    }

    public Receta searchReceta(String nombre) {
        int inicio = 0;
        int fin = recetas.size() - 1;

        while (inicio <= fin) {
            int medio = inicio + (fin - inicio) / 2;

            if (recetas.get(medio).getNombre().equalsIgnoreCase(nombre)) {
                return recetas.get(medio);
            }
            if (recetas.get(medio).getNombre().compareToIgnoreCase(nombre) > 0) {
                inicio = medio + 1;
            } else {
                fin = medio - 1;
            }
        }
        return null;
    }
}
