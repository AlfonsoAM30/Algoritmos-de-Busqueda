package Selecciones.Interfaz;

import Soluciones.Solucion;

import java.util.List;

public interface SeleccionTorneoInterfaz {
    List<Solucion> run(List<Solucion> poblacion, int k, boolean reemplazo, int tamanioPoblacion);
}
