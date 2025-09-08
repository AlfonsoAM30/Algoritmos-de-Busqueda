package Mutaciones;

import Mutaciones.Interfaz.Mutacion;
import Soluciones.Solucion;

import java.util.List;

public class MutacionAleatoria01 implements Mutacion {

    @Override
    public void run(List<Solucion> o, double probMut) {
        for (Solucion s : o) {
            for (int i = 0; i < s.getValores().length; i++) {
                if (Math.random() < probMut) {
                    s.put(i, 1 - s.get(i)); // Invertir el valor
                }
            }
        }
    }
}
