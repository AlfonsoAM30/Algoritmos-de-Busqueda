package Mutaciones;

import Mutaciones.Interfaz.Mutacion;
import Soluciones.Solucion;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MutacionInsert implements Mutacion {

    @Override
    public void run(List<Solucion> o, double probMut) {
        Random rand = new Random();
        for (Solucion s : o) {
            if (rand.nextDouble() < probMut) {
                int n = s.getValores().length;
                int x = rand.nextInt(n); // Selección de la primera posición
                int y;
                do {
                    y = rand.nextInt(n); // Selección de la segunda posición
                } while (x == y); // Asegurarse de que las posiciones son diferentes

                // Obtener el valor en la posición x
                int valorAInsertar = s.getValores()[x];

                // Crear una copia del arreglo de valores para no modificar el arreglo original mientras lo manipulamos
                int[] copia = Arrays.copyOf(s.getValores(), n);

                // Eliminar el valor de la posición x desplazando los demás valores
                if (x < y) {
                    for (int i = x; i < y; i++) {
                        s.put(i, copia[i + 1]); // Mover hacia la izquierda
                    }
                } else {
                    for (int i = x; i > y; i--) {
                        s.put(i, copia[i - 1]); // Mover hacia la derecha
                    }
                }
                // Colocar el valor en la nueva posición y
                s.put(y, valorAInsertar);
            }
        }
    }
}
