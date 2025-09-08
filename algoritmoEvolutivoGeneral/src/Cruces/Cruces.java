package Cruces;

import Instancias.Interfaz.Instancia;
import Soluciones.Solucion;

import java.util.*;

public class Cruces {

    public static List<Solucion> cruceTipico(List<Solucion> poblacionPrima, double probCruce, Instancia instancia) {
        List<Solucion> descendientes = new ArrayList<>();
        // Collections.shuffle(poblacionPrima);
        for (int i = 0; i < poblacionPrima.size(); i += 2) {
            Solucion padre1 = poblacionPrima.get(i);
            Solucion padre2 = poblacionPrima.get(i + 1);

            if (Math.random() < probCruce) {
                int puntoDeCruce = (int) (Math.random() * instancia.n());

                Solucion hijo1 = padre1.copia();
                Solucion hijo2 = padre2.copia();

                for (int j = puntoDeCruce; j < instancia.n(); j++) {
                    hijo1.put(j, padre2.get(j));
                    hijo2.put(j, padre1.get(j));
                }
                descendientes.add(hijo1);
                descendientes.add(hijo2);
            } else {
                descendientes.add(padre1);
                descendientes.add(padre2);
            }
        }

        return descendientes;
    }

    public static List<Solucion> cruceOrden1(List<Solucion> poblacionPrima, double probCruce, Instancia instancia) {
        Random rand = new Random();
        List<Solucion> descendientes = new ArrayList<>();
        int n = instancia.n();

        for (int i = 0; i < poblacionPrima.size(); i += 2) {
            Solucion padre1 = poblacionPrima.get(i);
            Solucion padre2 = poblacionPrima.get(i + 1);

            if (Math.random() < probCruce) {
                // Seleccionar puntos de cruce aleatorios, asegurando que start < end
                int start = rand.nextInt(instancia.n());
                int end = rand.nextInt(instancia.n());
                while (start == end) {
                    end = rand.nextInt(instancia.n());
                }
                if (start > end) {
                    int temp = start;
                    start = end;
                    end = temp;
                }

                Solucion hijo1 = new Solucion(instancia.n());
                Solucion hijo2 = new Solucion(instancia.n());

                // Inicializar con valores -1 para marcadores
                Arrays.fill(hijo1.getValores(), -1);
                Arrays.fill(hijo2.getValores(), -1);

                // Copiar segmento del primer padre a hijo1 y del segundo padre a hijo2
                for (int j = start; j <= end; j++) {
                    hijo1.put(j, padre1.get(j));
                    hijo2.put(j, padre2.get(j));
                }

                // Rellenar hijo1 y hijo2
                completarHijo(hijo1, padre2, start, end);
                completarHijo(hijo2, padre1, start, end);

                descendientes.add(hijo1);
                descendientes.add(hijo2);
            } else {
                descendientes.add(padre1.copia()); // Copia para evitar modificar padres originales
                descendientes.add(padre2.copia());
            }
        }
        return descendientes;
    }

    // Para Set
    public static Set<Solucion> cruceOrden1SinReps(Set<Solucion> poblacionPrima, double probCruce, Instancia instancia) {
        Random rand = new Random();
        List<Solucion> poblacionAux = new ArrayList<>(poblacionPrima);
        Set<Solucion> descendientes = new HashSet<>();

        for (int i = 0; i < poblacionAux.size(); i += 2) {
            Solucion padre1 = poblacionAux.get(i);
            Solucion padre2 = poblacionAux.get(i + 1);

            if (Math.random() < probCruce) {
                // Seleccionar puntos de cruce aleatorios, asegurando que start < end
                int start = rand.nextInt(instancia.n());
                int end = rand.nextInt(instancia.n());
                while (start == end) {
                    end = rand.nextInt(instancia.n());
                }
                if (start > end) {
                    int temp = start;
                    start = end;
                    end = temp;
                }

                Solucion hijo1 = new Solucion(instancia.n());
                Solucion hijo2 = new Solucion(instancia.n());

                // Inicializar con valores -1 para marcadores
                Arrays.fill(hijo1.getValores(), -1);
                Arrays.fill(hijo2.getValores(), -1);

                // Copiar segmento del primer padre a hijo1 y del segundo padre a hijo2
                for (int j = start; j <= end; j++) {
                    hijo1.put(j, padre1.get(j));
                    hijo2.put(j, padre2.get(j));
                }

                // Rellenar hijo1 y hijo2
                completarHijo(hijo1, padre2, start, end);
                completarHijo(hijo2, padre1, start, end);

                descendientes.add(hijo1);
                descendientes.add(hijo2);
            } else {
                descendientes.add(padre1.copia()); // Copia para evitar modificar padres originales
                descendientes.add(padre2.copia());
            }
        }
        return descendientes;
    }


    // AUXILIARES

    private static void completarHijo(Solucion hijo, Solucion padre, int start, int end) {
        int pos = (end + 1) % hijo.getValores().length;

        for (int i = 0; i < padre.getValores().length; i++) {
            int gen = padre.get((end + 1 + i) % padre.getValores().length);

            // Solo agregar genes que no estén en el segmento copiado del padre
            if (!contiene(hijo, gen)) {
                // Encontramos una posición válida para insertar el gen
                while (hijo.get(pos) != -1) {
                    pos = (pos + 1) % hijo.getValores().length;
                }
                hijo.put(pos, gen);
            }
        }
    }

    private static boolean contiene(Solucion hijo, int gen) {
        for (int i = 0; i < hijo.getValores().length; i++) {
            if (hijo.getValores()[i] == gen) {
                return true;
            }
        }
        return false;
    }

    // Codigo no necesario del completar Hijo

            /*
        // Asegurar que todas las posiciones del hijo estén completas, incluso si no quedan genes disponibles
        for (int i = 0; i < hijo.getValores().length; i++) {
            if (hijo.get(i) == -1) {
                // En este caso, si se quedan valores -1, los completamos con un gen aleatorio no repetido
                for (int j = 0; j < hijo.getValores().length; j++) {
                    if (!contiene(hijo, j)) {
                        hijo.put(i, j);
                        break;
                    }
                }
            }
        }
         */

}
