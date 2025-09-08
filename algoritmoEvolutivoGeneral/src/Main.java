// Algoritmos
import Algoritmos.*;
// Cruces
import Cruces.*;
import Cruces.Interfaz.*;
// Instacias
import Instancias.InstaciaTSP;
import Instancias.Interfaz.Instancia;
import Mutaciones.Interfaz.Mutacion;
// Mutaciones
import Mutaciones.*;
import Mutaciones.Interfaz.*;
// Selecciones
import Selecciones.*;
import Selecciones.Interfaz.*;
// Solucion
import Soluciones.Solucion;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Main {
    public static void main(String[] args) throws IOException {
        //int n = 30;
        //Instancias.Interfaz.Instancia instancia = new Instancias.InstanciaDe1(n);
        String filename = "berlin52.tsp";
        //String filename = "00_n00050_R01000_s000.txt";
        Instancia instancia = new InstaciaTSP(filename);
        int n = instancia.n();

        // Parámetros del algoritmo
        int tamanioPoblacion = 600;
        double probCruce = 0.9;
        double probMut = 1 / (double) n;
        double porcentajeReemplazo = 0.2;
        int generaciones = 400; // Iteraciones
        boolean debug = false;
        boolean maximizar = false;

        // Definimos las selección, cruce y mutación
        SeleccionTorneoInterfaz seleccion = new SeleccionPorTorneo();
        Function<List<Solucion>, List<Solucion>> seleccionUsar = poblacion -> seleccion.run(poblacion, 20, true, tamanioPoblacion);

        Cruce cruce = new CruceOrden1();
        BiFunction<List<Solucion>, Double, List<Solucion>> cruceUsar = (poblacionPrima, prob) ->
                cruce.run(poblacionPrima, prob, instancia);

        Mutacion mutacion = new MutacionInvertir();
        BiConsumer<List<Solucion>, Double> mutacionUsar = mutacion::run;

        // Definimos las selección, cruce y mutación sin repeticiones
        SeleccionTorneoSinRepsInterfaz seleccionSet = new SeleccionPorTorneoSinReps();
        Function<Set<Solucion>, Set<Solucion>> seleccionUsarSet = poblacion -> seleccionSet.run(poblacion, 5, true, tamanioPoblacion);

        CruceSinReps cruceSinReps = new CruceOrden1SinReps();
        BiFunction<Set<Solucion>, Double, Set<Solucion>> cruceUsarSet = (poblacionPrima, prob) ->
                cruceSinReps.run(poblacionPrima, prob, instancia);

        MutacionSinReps mutacionSinReps = new MutacionInvertirSinReps();
        BiConsumer<Set<Solucion>, Double> mutacionUsarSet = mutacionSinReps::run;

        // Definimos los algoritmos

        //Algoritmos.EvolutivoGeneral algoritmo = new Algoritmos.EvolutivoGeneral(instancia,tamanioPoblacion, generaciones, probCruce, probMut, debug);
        Algoritmos.EvolutivoConReps algoritmo = new Algoritmos.EvolutivoConReps(instancia,tamanioPoblacion, generaciones, probCruce, probMut, porcentajeReemplazo,
                                                  debug, seleccionUsar, cruceUsar, mutacionUsar);
        //EvolutivoSinReps algoritmo = new EvolutivoSinReps(instancia,tamanioPoblacion, generaciones, probCruce, probMut, porcentajeReemplazo,
                //debug, seleccionUsarSet, cruceUsarSet, mutacionUsarSet);

        //Algoritmos.MemeticoConReps algoritmo = new MemeticoConReps(instancia,tamanioPoblacion, generaciones, probCruce, probMut, porcentajeReemplazo,
                //debug, seleccionUsar, cruceUsar, mutacionUsar);

        //Algoritmos.MemeticoSinReps algoritmo = new MemeticoSinReps(instancia,tamanioPoblacion, generaciones, probCruce, probMut, porcentajeReemplazo,
                //debug, seleccionUsarSet, cruceUsarSet, mutacionUsarSet, maximizar);

        Solucion bestSolution = algoritmo.run();

        System.out.println("\nBest solution:\n" + bestSolution);
    }
}