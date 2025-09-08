package Auxiliares;

import Instancias.Interfaz.Instancia;
import Soluciones.Solucion;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class RellenarPoblaciones {
    public static void rellenarPoblacion(Set<Solucion> poblacion, Set<Solucion> poblacionAnterior, int tamanioPoblacion, Instancia instancia){
        if (poblacion.size() >= tamanioPoblacion) return;

        // Lista ordenada de las mejores soluciones de la población anterior (si existe)
        List<Solucion> mejoresAnteriores = new ArrayList<>();
        if (poblacionAnterior != null) {
            mejoresAnteriores.addAll(poblacionAnterior);
            mejoresAnteriores.sort(Comparator.comparingDouble(Solucion::getFitness)); // Orden ascendente (mejor primero)
        }

        // Completar con las mejores soluciones anteriores o generar nuevas aleatorias
        while (poblacion.size() < tamanioPoblacion) {
            if (!mejoresAnteriores.isEmpty()) {
                // Agregar las mejores soluciones anteriores, evitando duplicados
                Solucion mejor = mejoresAnteriores.removeFirst();
                poblacion.add(mejor);
            } else {
                // Si no quedan mejores soluciones anteriores, generar aleatorias
                Solucion nueva = instancia.generarSolucionAleatoria();
                poblacion.add(nueva);
            }
        }
    }
}
