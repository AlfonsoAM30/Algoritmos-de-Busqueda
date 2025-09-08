package Mutaciones;

import Mutaciones.Interfaz.MutacionSinReps;
import Soluciones.Solucion;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class MutacionInvertirSinReps implements MutacionSinReps {

    @Override
    public void run(Set<Solucion> o, double probMut) {
        List<Solucion> poblacionAux = new ArrayList<>(o);
        Random rand = new Random();
        for (Solucion s : poblacionAux) {
            if (rand.nextDouble() < probMut) {
                int x = rand.nextInt(s.getValores().length);
                int y;
                do {
                    y = rand.nextInt(s.getValores().length);
                } while (x == y);
                int start = Math.min(x, y);
                int end = Math.max(x, y);
                while (start < end) {
                    int temp = s.getValores()[start];
                    s.getValores()[start] = s.getValores()[end];
                    s.getValores()[end] = temp;
                    start++;
                    end--;
                }
            }
        }
        o.clear();
        o.addAll(poblacionAux);
    }
}
