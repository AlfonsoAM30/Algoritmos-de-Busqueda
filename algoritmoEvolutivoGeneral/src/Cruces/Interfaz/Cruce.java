package Cruces.Interfaz;

import Instancias.Interfaz.Instancia;
import Soluciones.Solucion;

import java.util.List;

public interface Cruce {
    List<Solucion> run(List<Solucion> poblacionPrima, double probCruce, Instancia instancia);
}
