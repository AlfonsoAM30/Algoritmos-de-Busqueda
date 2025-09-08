package Mutaciones.Interfaz;

import Soluciones.Solucion;

import java.util.Set;

public interface MutacionSinReps {
    void run(Set<Solucion> o, double probMut);
}
