package Selecciones.Interfaz;

import Soluciones.Solucion;

import java.util.Set;

public interface SeleccionTorneoSinRepsInterfaz {
    Set<Solucion> run(Set<Solucion> poblacion, int k, boolean reemplazo, int tamanioPoblacion);
}
