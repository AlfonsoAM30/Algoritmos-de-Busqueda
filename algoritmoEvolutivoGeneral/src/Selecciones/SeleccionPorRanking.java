package Selecciones;

import Selecciones.Interfaz.Seleccion;
import Soluciones.Solucion;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SeleccionPorRanking implements Seleccion {

    @Override
    public List<Solucion> run(List<Solucion> poblacion, int tamanioPoblacion) {
        List<Solucion> seleccionados = new ArrayList<>();
        List<Solucion> copiaPoblacion = new ArrayList<>(poblacion);
        copiaPoblacion.sort(Comparator.comparing(Solucion::getFitness).reversed());

        double sumaRanking = tamanioPoblacion * (tamanioPoblacion + 1) / 2.0;
        for (int i = 0; i < tamanioPoblacion; i++) {
            double probabilidad = (tamanioPoblacion - i) / sumaRanking;
            copiaPoblacion.get(i).setProbability(probabilidad);
        }

        for (int i = 0; i < tamanioPoblacion; i++) {
            double puntoAleatorio = Math.random();
            double acumulado = 0.0;

            for (Solucion s : copiaPoblacion) {
                acumulado += s.getProbability();
                if (puntoAleatorio <= acumulado) {
                    seleccionados.add(s);
                    break;
                }
            }
        }

        return seleccionados;
    }
}
