package BusquedasLocales.Interfaz;

import Instancias.Interfaz.Instancia;
import Soluciones.Solucion;

public interface BusquedaLocal {
    Solucion run(Solucion s, Instancia instancia);
}
