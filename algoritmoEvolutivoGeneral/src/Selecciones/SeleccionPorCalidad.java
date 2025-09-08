package Selecciones;

import Selecciones.Interfaz.Seleccion;
import Soluciones.Solucion;

import java.util.ArrayList;
import java.util.List;

public class SeleccionPorCalidad implements Seleccion {

    @Override
    public List<Solucion> run(List<Solucion> poblacion, int tamanioPoblacion) { // Cambiar a hashSet
        List<Solucion> seleccionados = new ArrayList<>();
        double sumaFitness = sumaTotalFitness(poblacion);

        for (int i = 0; i < tamanioPoblacion; i++) {
            double puntoAleatorio = Math.random() * sumaFitness; // Generar un punto aleatorio entre 0 y la suma total de fitness
            double acumulado = 0.0; // Acumulación de los fitness para la ruleta

            // Buscar el individuo correspondiente al punto aleatorio
            for (Solucion s : poblacion) {
                acumulado += s.getFitness();
                if (acumulado >= puntoAleatorio) { // Si el acumulado es igual o supera el punto Random lo seleccionamos
                    seleccionados.add(s);
                    break;
                }
            }
        }

        return seleccionados;
    }

    private static double sumaTotalFitness(List<Solucion> poblacion) {
        double suma = 0;
        for (Solucion s : poblacion) {
            suma += s.getFitness();
        }
        return suma;
    }
}
