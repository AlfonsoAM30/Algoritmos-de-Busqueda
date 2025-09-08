package Instancias.Interfaz;
import Soluciones.Solucion;

public interface Instancia {
    int n();
    Solucion generarSolucionAleatoria();
    double evaluar(Solucion s);
}
