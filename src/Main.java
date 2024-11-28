import Exceptions.NotFound;
import Restaurant.Elaboracion.ListaElaboracion;
import Restaurant.Elaboracion.Paso;
import Restaurant.Ingrediente;
import Restaurant.IngredientesLista;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        clearScreen();
        System.out.println("Bienvenido al Sistema de Organización de Restaurantes");
        System.out.println();
        String opcion;

        try {
            Restaurant.Restaurant restaurant = new Restaurant.Restaurant();
            restaurant.loadFromDB();
            TimeUnit.MILLISECONDS.sleep(300);
            clearScreen();

            try (Scanner sc = new Scanner(System.in)) {
                label:
                while (true) {
                    System.out.println("\n¿Qué desea hacer?");
                    System.out.println("1. Preparación de receta");
                    System.out.println("2. Nueva receta");
                    System.out.println("3. Ingreso de ingredientes");
                    System.out.println("9. Salir");
                    opcion = sc.nextLine();
                    TimeUnit.MILLISECONDS.sleep(300);

                    switch (opcion) {
                        case "1":
                            preparacionReceta(sc, restaurant);
                            TimeUnit.SECONDS.sleep(1);
                            break;
                        case "2":
                            nuevaReceta(sc, restaurant);
                            TimeUnit.SECONDS.sleep(1);
                            break;
                        case "3":
                            ingresoIngredientes(sc, restaurant);
                            TimeUnit.SECONDS.sleep(1);
                            break;
                        case "9":
                            System.out.println("Muchas Gracias");
                            break label;
                        default:
                            System.out.println("Número ingresado no es válido");
                            break;
                    }
                }
            }

        } catch (FileNotFoundException e) {
            System.err.println("No se encontró archivo");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void preparacionReceta(Scanner sc, Restaurant.Restaurant restaurant) {
        String recetaNombre;
        Restaurant.Receta receta;
        String option;
        while (true) {
            System.out.println("Ingrese el nombre del receta");
            recetaNombre = sc.nextLine();
            receta = restaurant.searchReceta(recetaNombre);
            if (receta != null) {
                break;
            }
            System.out.println("Receta no encontrada");
        }
        try {
            ArrayList<Ingrediente> ingredientes = receta.revisarIngredientes(restaurant);
            ListaElaboracion lista = receta.getPreparacion();
            Paso current = lista.getHead();

            while (true) {
                clearScreen();
                System.out.println("(S)iguiente - (A)nterior - (T)erminar");
                System.out.println();
                System.out.println(current.getData());
                option = sc.nextLine();

                if (option.equalsIgnoreCase("s")) {
                    if (current.getNext() != null) {
                        current = current.getNext();
                    } else {
                        System.out.println("No hay next");
                    }
                } else if (option.equalsIgnoreCase("a")) {
                    if (current.getNext() != null) {
                        current = current.getPrev();
                    } else {
                        System.out.println("No hay prev");
                    }
                } else if (option.equalsIgnoreCase("t")) {
                    break;
                }
            }

            System.out.println("Confirma guardar en BD? (S)i - (N)o");
            option = sc.nextLine();
            if (option.equalsIgnoreCase("s")) {
                restaurant.updateInventario(ingredientes);
            }
        } catch (FileNotFoundException | NotFound e) {
            e.printStackTrace();
            return;
        }
    }

    public static void nuevaReceta(Scanner sc, Restaurant.Restaurant restaurant) throws InterruptedException {
        String option = "";
        String recetaNombre = "";
        ListaElaboracion preparacion = new ListaElaboracion();
        IngredientesLista ingredientes = new IngredientesLista();

        while (true) {
            while (!option.equalsIgnoreCase("s")) {
                System.out.println("Ingrese el nombre de la nueva receta");
                recetaNombre = sc.nextLine();
                System.out.println("Confirma? (S)í");
                option = sc.nextLine();
            }

            clearScreen();
            System.out.println("Ingresar ingredientes");
            TimeUnit.MILLISECONDS.sleep(300);
            while (true) {
                try {
                    clearScreen();
                    System.out.println("Ingredientes ingresados:");
                    for (Ingrediente ingrediente : ingredientes.getIngredientes()) {
                        ingrediente.printIngrediente();
                    }
                    System.out.println();
                    System.out.println("1. Ingresar ingrediente");
                    System.out.println("2. Eliminar ingrediente");
                    System.out.println("3. Terminar edición");
                    option = sc.nextLine();
                    if (option.equalsIgnoreCase("1")) {
                        System.out.println("Nombre del ingrediente:");
                        String nombreIngrediente = sc.nextLine();
                        System.out.println("Cantidad:");
                        double cantidadIngrediente = Double.parseDouble(sc.nextLine());
                        System.out.println("Unidades:");
                        String unidadesIngrediente = sc.nextLine();
                        ingredientes.addIngrediente(0, nombreIngrediente, cantidadIngrediente, unidadesIngrediente);
                    } else if (option.equalsIgnoreCase("2")) {
                        System.out.println("Nombre del ingrediente a eliminar:");
                        String nombreIngrediente = sc.nextLine();
                        ingredientes.deleteIngrediente(nombreIngrediente);
                    } else if (option.equalsIgnoreCase("3")) {
                        break;
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Formato de número inválido");
                }
            }

            while (true) {
                try {
                    clearScreen();
                    System.out.println("Pasos de preparación:");
                    preparacion.printList();
                    System.out.println();
                    System.out.println("1. Ingresar paso");
                    System.out.println("2. Terminar edición");
                    option = sc.nextLine();
                    if (option.equalsIgnoreCase("1")) {
                        System.out.println("Paso:");
                        String paso = sc.nextLine();
                        preparacion.insertAtEnd(paso);
                    } else if (option.equalsIgnoreCase("2")) {
                        break;
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Formato de número inválido");
                }
            }

            System.out.println("Confirma guardar en BD? (S)i - (N)o");
            option = sc.nextLine();
            if (option.equalsIgnoreCase("s")) {
                restaurant.saveReceta(recetaNombre, ingredientes, preparacion);
                break;
            }
        }
    }

    public static void ingresoIngredientes(Scanner sc, Restaurant.Restaurant restaurant) {
        String option = "";
        String nombreIngrediente = "";
        double cantidadIngrediente = 0;

        while (true) {
            try {
                System.out.println("Ingresar nombre de ingrediente:");
                nombreIngrediente = sc.nextLine();
                System.out.println("Ingresar cantidad de ingrediente:");
                cantidadIngrediente = Double.parseDouble(sc.nextLine());

                System.out.println("Confirma guardar en BD? (S)i - (N)o");
                option = sc.nextLine();
                if (option.equalsIgnoreCase("s")) {
                    restaurant.saveInventario(nombreIngrediente, cantidadIngrediente);
                }
                System.out.println("Ingresar otro ingrediente? (S)i - (N)o");
                option = sc.nextLine();
                if (option.equalsIgnoreCase("n")) {
                    break;
                }
            } catch (NumberFormatException e) {
                System.err.println("Formato de número inválido");
            }
        }
    }
}