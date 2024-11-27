import Restaurant.Restaurant;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        System.out.println("Bienvenido al Sistema de Organización de Restaurantes");

        try {
            Restaurant restaurant = new Restaurant();
            restaurant.loadFromDB();

            System.out.println(restaurant);

        } catch (FileNotFoundException e) {
            System.err.println("No se encontró archivo");
        }

    }
}