package Mutaciones.Interfaz;

import Soluciones.Solucion;

import java.util.List;

public interface Mutacion {
    void run(List<Solucion> o, double probMut);
}
