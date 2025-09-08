package Algoritmos;

import Instancias.Interfaz.Instancia;
import Soluciones.Solucion;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class EvolutivoConReps {

    final Instancia instancia;
    final int tamanioPoblacion;
    final boolean debug;
    final int iteraciones;
    double probCruce = 0.6;
    double probMut;
    double procentajeReemplazo;
    Function<List<Solucion>, List<Solucion>> metodoSeleccion;
    BiFunction<List<Solucion>, Double, List<Solucion>> metodoCruce;
    BiConsumer<List<Solucion>, Double> metodoMutacion;

    public EvolutivoConReps(Instancia instancia, int tamanioPoblacion, int iteraciones,
                            double probCruce, double probMut, double porcentajeReemplazo, boolean debug,
                            Function<List<Solucion>, List<Solucion>> metodoSeleccion,
                            BiFunction<List<Solucion>, Double, List<Solucion>> metodoCruce,
                            BiConsumer<List<Solucion>, Double> metodoMutacion) {
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
    }


    public Solucion run() {
        List<Solucion> poblacion = generarPoblacionInicial();
        Solucion best = evaluarPoblacion(poblacion);
        imprimePoblacion("Población inicial:",poblacion,debug);

        for (int i = 0; i < this.iteraciones; i++) {
            List<Solucion> poblacionPrima = metodoSeleccion.apply(poblacion);

            // Definimos las probabilidades
            double probCruce = this.probCruce;
            double probMut = this.probMut;

            List<Solucion> O = metodoCruce.apply(poblacionPrima, probCruce);
            metodoMutacion.accept(O, probMut);

            Solucion bestO = evaluarPoblacion(O); // Evaluamos la mutación
            if (bestO.getFitness() < best.getFitness()) best = bestO; // Miramos si es mejor
            imprimePoblacion("Hijos: ", O, debug); // Imprimimos los hijos

            List<Solucion> poblacionUnida = new ArrayList<>(poblacion); // Inicializamos la población unida con la población
            poblacionUnida.addAll(O); // Añadimos todos de O
            poblacion = reemplazarPeoresConMejores(O, poblacionUnida, procentajeReemplazo);
        }

        return best;
    }

    private List<Solucion> reemplazarPeoresConMejores(List<Solucion> poblacion, List<Solucion> nuevasSoluciones, double porcentaje) {
        List<Solucion> nuevaPoblacion = new ArrayList<>(poblacion);

        // Calcular el número de soluciones a reemplazar
        int numReemplazos = (int) (nuevaPoblacion.size() * porcentaje);

        // Ordenar las soluciones por calidad (peor a mejor)
        nuevaPoblacion.sort(Comparator.comparingDouble(Solucion::getFitness).reversed());

        // Ordenar las nuevas soluciones por calidad (mejor a peor)
        nuevasSoluciones.sort(Comparator.comparingDouble(Solucion::getFitness));

        // Reemplazar las peores soluciones con las mejores nuevas soluciones
        for (int i = 0; i < numReemplazos; i++) {
            nuevaPoblacion.set(i, nuevasSoluciones.get(i));
        }

        return nuevaPoblacion;
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
        double bestScore = Integer.MAX_VALUE;
        for (Solucion s : poblacion) {
            double fitness = instancia.evaluar(s);
            if (fitness < bestScore) {
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
