package Restaurant;
import Exceptions.NotFound;
import Restaurant.Elaboracion.ListaElaboracion;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Receta extends DataInDB {

    public Receta(int id, String nombre) {
        super(id, nombre);
    }

    public ArrayList<Ingrediente> revisarIngredientes(Restaurant restaurant) throws FileNotFoundException, NotFound {
        ArrayList<Ingrediente> ingredientes = new ArrayList<>();
        File fileDB = new File("db/recetas/ingredientes/" + getId() + ".csv");
        try (Scanner myReader = new Scanner(fileDB)) {
            myReader.nextLine();
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String regex = ",";
                String[] myArray = data.split(regex);
                double cantidad = 0;
                if (!myArray[0].isEmpty()) {
                    cantidad = Double.parseDouble(myArray[0]);
                }
                ingredientes.add(new Ingrediente(0, String.valueOf(myArray[2]), cantidad, String.valueOf(myArray[1])));
            }
        }

        for (Ingrediente ingrediente : ingredientes) {
            Ingrediente inInventario = restaurant.searchInventario(ingrediente.getNombre());
            if (inInventario == null) {
                throw new NotFound("Ingrediente " + ingrediente.getNombre() + " no encontrado");
            }
            if (inInventario.getCantidad() < ingrediente.getCantidad()) {
                throw new NotFound("Ingrediente " + ingrediente.getNombre() + " no encontrado");
            }
        }

        return ingredientes;
    }

    public ListaElaboracion getPreparacion() throws FileNotFoundException {
        ListaElaboracion lista = new ListaElaboracion();
        File fileDB = new File("db/recetas/elaboracion/" + getId() + ".txt");
        try (Scanner myReader = new Scanner(fileDB)) {
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                lista.insertAtEnd(data);
            }
        }
        return lista;
    }
}
