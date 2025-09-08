package Algoritmos;

import Selecciones.Selecciones;
import Instancias.Interfaz.Instancia;
import Soluciones.*;
import Cruces.*;
import Mutaciones.*;

import java.util.ArrayList;
import java.util.List;

public class EvolutivoGeneral {

    final Instancia instancia;
    final int tamanioPoblacion;
    final boolean debug;
    final int iteraciones;
    double probCruce = 0.6;
    double probMut;

    public EvolutivoGeneral(Instancia instancia, int tamanioPoblacion, int iteraciones,
                              double probCruce, double probMut, boolean debug) {
        this.instancia = instancia;
        this.tamanioPoblacion = tamanioPoblacion;
        this.debug = debug;
        this.iteraciones = iteraciones;
        this.probCruce = probCruce;
        this.probMut = probMut;
    }


    public Solucion run() {
        List<Solucion> poblacion = generarPoblacionInicial();
        Solucion best = evaluarPoblacion(poblacion);
        imprimePoblacion("Población inicial:",poblacion,debug);

        for (int i = 0; i < this.iteraciones; i++) {
            List<Solucion> poblacionPrima = Selecciones.seleccionPorCalidad(poblacion, tamanioPoblacion);

            // Definimos las probabilidades
            double probCruce = this.probCruce;
            double probMut = this.probMut;

            List<Solucion> O = Cruces.cruceTipico(poblacionPrima, probCruce, instancia);
            Mutaciones.mutacionAleatoria(O, probMut);

            Solucion bestO = evaluarPoblacion(O); // Evaluamos la mutación
            if (bestO.getFitness() > best.getFitness()) best = bestO; // Miramos si es mejor
            imprimePoblacion("Hijos: ", O, debug); // Imprimimos los hijos

            O.remove(peor(O)); // Quitamos el peor de O
            List<Solucion> poblacionUnida = new ArrayList<>(poblacion); // Inicializamos la población unida con la población
            poblacionUnida.addAll(O); // Añadimos todos de O
            Solucion mejorDeAmbas = mejor(poblacionUnida); // Obtenemos la mejor de ambas
            poblacion = reemplazarPeorConMejor(O, mejorDeAmbas); // remplazamos la O con la mejor de ambas
        }

        return best;
    }

    private List<Solucion> reemplazarPeorConMejor(List<Solucion> poblacion, Solucion mejorDeAmbas) {
        List<Solucion> nuevaPoblacion = new ArrayList<>(poblacion);
        // Encuentra el índice de la peor solución
        int indicePeor = 0;
        for (int i = 1; i < nuevaPoblacion.size(); i++) {
            if (nuevaPoblacion.get(i).esPeorMaximizarQue(nuevaPoblacion.get(indicePeor))) {
                indicePeor = i;
            }
        }
        nuevaPoblacion.set(indicePeor, mejorDeAmbas); // Reemplaza la peor con la mejor
        return nuevaPoblacion;
    }

    private Solucion mejor(List<Solucion> poblacionUnida) {
        Solucion mejor = poblacionUnida.getFirst();
        for (Solucion s : poblacionUnida) {
            if (!s.esPeorMaximizarQue(mejor)) {
                mejor = s;
            }
        }
        return mejor;
    }

    private Solucion peor(List<Solucion> o) {
        Solucion peor = o.getFirst();
        for (Solucion s : o) {
            if (s.esPeorMaximizarQue(peor)) {
                peor = s;
            }
        }
        return peor;
    }

    private void imprimePoblacion(String msg, List<Solucion> poblacion, boolean debug) {
        if (debug) {
            System.out.println(msg);
            for (Solucion s : poblacion) {
                System.out.println(s);
            }
        }
    }

    private Solucion evaluarPoblacion(List<Solucion> poblacion) {
        Solucion best = null;
        double bestScore = Integer.MIN_VALUE;
        for (Solucion s : poblacion) {
            double fitness = instancia.evaluar(s);
            if (fitness > bestScore) {
                best = s;
                bestScore = fitness;
            }
            s.setFitness(fitness);
        }
        return best;
    }

    private List<Solucion> generarPoblacionInicial() {
        List<Solucion> poblacion = new ArrayList<>();
        for (int i = 0; i < tamanioPoblacion; i++) {
            poblacion.add(instancia.generarSolucionAleatoria());
        }
        return poblacion;
    }
}
