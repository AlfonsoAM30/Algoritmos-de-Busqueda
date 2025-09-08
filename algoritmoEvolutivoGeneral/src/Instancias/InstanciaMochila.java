package Instancias;

import Instancias.Interfaz.Instancia;
import Soluciones.Solucion;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

public class InstanciaMochila implements Instancia {
    private String name;
    private int numberOfItems;
    private int capacity;
    private int[] values;
    private int[] weights;

    public InstanciaMochila(String filename) throws IOException {
        this.name = filename;
        this.importInstance(new BufferedReader(new FileReader(filename)));
    }

    public void importInstance(BufferedReader reader) throws IOException {
        Scanner sc = new Scanner(reader).useLocale(Locale.US);

        // Leer el número de productos
        sc.nextLine(); // Salta la línea de comentario
        this.numberOfItems = Integer.parseInt(sc.nextLine().trim());

        // Leer la capacidad de la mochila
        //sc.nextLine();
        //sc.nextLine(); // Salta la línea de comentario
        this.capacity = Integer.parseInt(sc.nextLine().trim());

        // Inicializar arrays de valores y pesos
        this.values = new int[numberOfItems];
        this.weights = new int[numberOfItems];

        // Leer los valores y pesos de cada producto
        //sc.nextLine();
        sc.nextLine(); // Salta la línea de comentario
        for (int i = 0; i < numberOfItems; i++) {
            int value = sc.nextInt();
            int weight = sc.nextInt();
            this.values[i] = value;
            this.weights[i] = weight;
        }
    }

    @Override
    public int n() {
        return this.numberOfItems;
    }

    @Override
    public Solucion generarSolucionAleatoria() {
        Solucion s = new Solucion(numberOfItems);
        int totalWeight = 0;

        for (int i = 0; i < numberOfItems; i++) {
            // Decidimos si incluir el artículo con una probabilidad, pero solo si no excedemos la capacidad
            int valor = Math.random() < 0.5 ? 1 : 0;

            // Si el valor es 1 (seleccionamos el artículo)
            if (valor == 1) {
                // Si añadir el artículo no excede la capacidad, lo seleccionamos
                if (totalWeight + this.weights[i] <= this.capacity) {
                    s.put(i, 1);  // Marcamos el artículo como seleccionado
                    totalWeight += this.weights[i];  // Actualizamos el peso total
                } else {
                    s.put(i, 0);  // No seleccionamos el artículo
                }
            } else {
                s.put(i, 0);  // No seleccionamos el artículo
            }
        }

        return s;  // Devolvemos la solución generada
    }

    @Override
    public double evaluar(Solucion s) {
        int fitness = 0;
        int totalWeight = 0;

        // Recorremos todos los artículos seleccionados en la solución
        for (int i = 0; i < numberOfItems; i++) {
            // Si el artículo está seleccionado (s.get(i) == 1)
            if (s.get(i) == 1) {
                fitness += this.values[i];  // Sumamos el valor
                totalWeight += this.weights[i]; // Sumamos el peso
            }
        }

        // Si el peso total excede la capacidad, la solución no es válida, se devuelve un valor muy bajo
        if (totalWeight > this.capacity) {
            return Integer.MIN_VALUE;  // Penalización por exceder la capacidad
        }

        return fitness;  // Retornamos el valor total si no excede la capacidad
    }

    public int getCapacity() {
        return this.capacity;
    }

    public int getValue(int itemID) {
        return this.values[itemID];
    }

    public int getWeight(int itemID) {
        return this.weights[itemID];
    }

    public String getName() {
        return this.name;
    }

    // Métodos adicionales si necesitas obtener los arrays completos de valores y pesos
    public int[] getValues() {
        return this.values;
    }

    public int[] getWeights() {
        return this.weights;
    }
}
