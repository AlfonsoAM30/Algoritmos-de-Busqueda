package Algoritmos;

import Instancias.Interfaz.Instancia;
import Soluciones.Solucion;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import Auxiliares.RellenarPoblaciones;

public class EvolutivoSinReps {

    final Instancia instancia;
    final int tamanioPoblacion;
    final boolean debug;
    final int iteraciones;
    double probCruce = 0.6;
    double probMut;
    double procentajeReemplazo;
    Function<Set<Solucion>, Set<Solucion>> metodoSeleccion;
    BiFunction<Set<Solucion>, Double, Set<Solucion>> metodoCruce;
    BiConsumer<Set<Solucion>, Double> metodoMutacion;
    boolean maximizar;

    public EvolutivoSinReps(Instancia instancia, int tamanioPoblacion, int iteraciones,
                        double probCruce, double probMut, double porcentajeReemplazo, boolean debug,
                        Function<Set<Solucion>, Set<Solucion>> metodoSeleccion,
                        BiFunction<Set<Solucion>, Double, Set<Solucion>> metodoCruce,
                        BiConsumer<Set<Solucion>, Double> metodoMutacion, boolean maximizar) {
        this.instancia = instancia;
        this.tamanioPoblacion = tamanioPoblacion;
        this.debug = debug;
        this.iteraciones = iteraciones;
        this.probCruce = probCruce;
        this.probMut = probMut;
        this.procentajeReemplazo = porcentajeReemplazo;
        this.metodoSeleccion = metodoSeleccion;
        this.metodoCruce = metodoCruce;
        this.metodoMutacion = metodoMutacion;
        this.maximizar = maximizar;
    }


    public Solucion run() {
        Set<Solucion> poblacion = generarPoblacionInicial();
        Set<Solucion> poblacionAnterior = new HashSet<>(poblacion);
        Solucion best = evaluarPoblacion(poblacion);
        imprimePoblacion("Población inicial:",poblacion,debug);

        for (int i = 0; i < this.iteraciones; i++) {
            Set<Solucion> poblacionPrima = metodoSeleccion.apply(poblacion);
            RellenarPoblaciones.rellenarPoblacion(poblacionPrima, poblacionAnterior, tamanioPoblacion, instancia);

            // Definimos las probabilidades
            double probCruce = this.probCruce;
            double probMut = this.probMut;

            Set<Solucion> O = metodoCruce.apply(poblacionPrima, probCruce);
            RellenarPoblaciones.rellenarPoblacion(O, poblacionAnterior, tamanioPoblacion, instancia);
            metodoMutacion.accept(O, probMut);
            RellenarPoblaciones.rellenarPoblacion(O, poblacionAnterior, tamanioPoblacion, instancia);

            Solucion bestO = evaluarPoblacion(O); // Evaluamos la mutación
            if ((!maximizar && bestO.getFitness() < best.getFitness()) || (maximizar && bestO.getFitness() > best.getFitness())){
                best = bestO; // Miramos si es mejor
            }
            imprimePoblacion("Hijos: ", O, debug); // Imprimimos los hijos

            List<Solucion> poblacionUnida = new ArrayList<>(poblacion); // Inicializamos la población unida con la población
            poblacionUnida.addAll(O); // Añadimos todos de O
            poblacionAnterior = new HashSet<>(poblacion);
            poblacion = reemplazarPeoresConMejores(O, poblacionAnterior, poblacionUnida, procentajeReemplazo);
        }

        return best;
    }

    private Set<Solucion> reemplazarPeoresConMejores(Set<Solucion> poblacion, Set<Solucion> poblacionAnterior, List<Solucion> nuevasSoluciones, double porcentaje) {
        Set<Solucion> nuevaPoblacion = new HashSet<>(poblacion);

        List<Solucion> listaPoblacion = new ArrayList<>(nuevaPoblacion);
        // Ordenar las soluciones por calidad
        if (maximizar) {
            listaPoblacion.sort(Comparator.comparingDouble(Solucion::getFitness));
            nuevasSoluciones.sort(Comparator.comparingDouble(Solucion::getFitness).reversed());
        } else {
            listaPoblacion.sort(Comparator.comparingDouble(Solucion::getFitness).reversed());
            nuevasSoluciones.sort(Comparator.comparingDouble(Solucion::getFitness));
        }

        int numReemplazos = Math.min((int) (nuevaPoblacion.size() * porcentaje), nuevasSoluciones.size());
        for (int i = 0; i < numReemplazos; i++) {
            nuevaPoblacion.remove(listaPoblacion.get(i));
            nuevaPoblacion.add(nuevasSoluciones.get(i));
        }

        RellenarPoblaciones.rellenarPoblacion(nuevaPoblacion, poblacionAnterior, tamanioPoblacion, instancia);

        return nuevaPoblacion;
    }


    private void imprimePoblacion(String msg, Set<Solucion> poblacion, boolean debug) {
        if (debug) {
            System.out.println(msg);
            for (Solucion s : poblacion) {
                System.out.println(s);
            }
        }
    }

    private Solucion evaluarPoblacion(Set<Solucion> poblacion) {
        Solucion best = null;
        double bestScore = maximizar ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        for (Solucion s : poblacion) {
            double fitness = instancia.evaluar(s);
            if (!maximizar && fitness < bestScore || maximizar && fitness > bestScore) {
                best = s;
                bestScore = fitness;
            }
            s.setFitness(fitness);
        }
        return best;
    }

    private Set<Solucion> generarPoblacionInicial() {
        Set<Solucion> poblacion = new HashSet<>();
        while (poblacion.size() < this.tamanioPoblacion) {
            poblacion.add(instancia.generarSolucionAleatoria());
        }
        return poblacion;
    }
}
