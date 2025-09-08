package Cruces.Interfaz;

import Instancias.Interfaz.Instancia;
import Soluciones.Solucion;

import java.util.Set;

public interface CruceSinReps {
    Set<Solucion> run(Set<Solucion> poblacionPrima, double probCruce, Instancia instancia);
}
