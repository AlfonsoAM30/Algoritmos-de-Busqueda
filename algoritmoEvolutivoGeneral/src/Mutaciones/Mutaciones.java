package Mutaciones;

import Soluciones.Solucion;

import java.util.*;

public class Mutaciones {

    public static void mutacionAleatoria(List<Solucion> o, double probMut) {
        for (Solucion s : o) {
            for (int i = 0; i < s.getValores().length; i++) {
                if (Math.random() < probMut) {
                    s.put(i, 1 - s.get(i)); // Invertir el valor
                }
            }
        }
    }

    public static void mutacionInsert(List<Solucion> o, double probMut) {
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

    public static void mutacionSwap(List<Solucion> o, double probMut) {
        Random rand = new Random();
        for (Solucion s : o) {
            if (rand.nextDouble() < probMut) {
                int x = rand.nextInt(s.getValores().length);
                int y;
                do {
                    y = rand.nextInt(s.getValores().length);
                } while (x == y || s.getValores()[x] == s.getValores()[y]);
                int[] copia = new int[s.getValores().length];
                for (int k = 0; k < s.getValores().length; k++) {
                    copia[k] = s.getValores()[k];
                }
                int temp = s.getValores()[x];
                s.put(x, copia[y]);
                s.put(y, temp);
            }
        }
    }

    public static void mutacionInvert(List<Solucion> o, double probMut) {
        Random rand = new Random();
        for (Solucion s : o) {
            if (rand.nextDouble() < probMut) {
                int x = rand.nextInt(s.getValores().length);
                int y;
                do {
                    y = rand.nextInt(s.getValores().length);
                } while (x == y);
                int start = Math.min(x, y);
                int end = Math.max(x, y);
                while (start < end) {
                    int temp = s.getValores()[start];
                    s.getValores()[start] = s.getValores()[end];
                    s.getValores()[end] = temp;
                    start++;
                    end--;
                }
            }
        }
    }

    public static void mutacionScramle(List<Solucion> o, double probMut) {
        Random rand = new Random();
        for (Solucion s : o) {
            if (rand.nextDouble() < probMut) {
                int numGenes = 2 + rand.nextInt(s.getValores().length - 1);
                List<Integer> indices = new ArrayList<Integer>();
                while (indices.size() < numGenes) {
                    int index = rand.nextInt(s.getValores().length);
                    if (!indices.contains(index)) {
                        indices.add(index);
                    }
                }
                List<Integer> subCadena = new ArrayList<Integer>();
                for (Integer i : indices) {
                    subCadena.add(s.getValores()[i]);
                }
                Collections.shuffle(subCadena);
                for (int i = 0; i < indices.size(); i++) {
                    s.getValores()[indices.get(i)] = subCadena.get(i);
                }
            }
        }
    }

    // En SET

    public static void mutacionInvertSinReps(Set<Solucion> o, double probMut) {
        List<Solucion> poblacionAux = new ArrayList<>(o);
        Random rand = new Random();
        for (Solucion s : poblacionAux) {
            if (rand.nextDouble() < probMut) {
                int x = rand.nextInt(s.getValores().length);
                int y;
                do {
                    y = rand.nextInt(s.getValores().length);
                } while (x == y);
                int start = Math.min(x, y);
                int end = Math.max(x, y);
                while (start < end) {
                    int temp = s.getValores()[start];
                    s.getValores()[start] = s.getValores()[end];
                    s.getValores()[end] = temp;
                    start++;
                    end--;
                }
            }
        }
        o.clear();
        o.addAll(poblacionAux);
    }
}
