package BusquedasLocales;

import BusquedasLocales.Interfaz.BusquedaLocal;
import Instancias.Interfaz.Instancia;
import Soluciones.Solucion;

public class Operator2OPT implements BusquedaLocal {

    @Override
    public Solucion run(Solucion solucion, Instancia instancia) {
        int[] recorrido = solucion.getValores();
        boolean mejora = true;

        while (mejora) {
            mejora = false;
            double mejorDistancia = solucion.getFitness();

            for (int i = 0; i < recorrido.length - 2; i++) {
                for (int j = i + 1; j < recorrido.length - 1; j++) {
                    int[] nuevoRecorrido = realizarIntercambio(recorrido, i, j);
                    Solucion nuevaSolucion = solucion.copia();
                    nuevaSolucion.setValores(nuevoRecorrido);
                    double nuevaDistancia = instancia.evaluar(nuevaSolucion);
                    nuevaSolucion.setFitness(nuevaDistancia);

                    if (nuevaDistancia < mejorDistancia) { // Minimización
                        solucion.setValores(nuevoRecorrido);
                        solucion.setFitness(nuevaDistancia);
                        mejorDistancia = nuevaDistancia;
                        mejora = true;
                    }
                }
            }
        }

        return solucion;
    }

    private int[] realizarIntercambio(int[] recorrido, int i, int j) {
        int[] nuevoRecorrido = recorrido.clone();

        // Invertir el segmento entre i y j
        while (i < j) {
            int temp = nuevoRecorrido[i];
            nuevoRecorrido[i] = nuevoRecorrido[j];
            nuevoRecorrido[j] = temp;
            i++;
            j--;
        }

        return nuevoRecorrido;
    }

}
