package Selecciones.Interfaz;

import Soluciones.Solucion;

import java.util.List;

public interface Seleccion {
    List<Solucion> run(List<Solucion> poblacion, int tamanioPoblacion);
}
