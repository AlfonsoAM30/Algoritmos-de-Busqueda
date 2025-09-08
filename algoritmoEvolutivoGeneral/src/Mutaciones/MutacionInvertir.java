package Mutaciones;

import Mutaciones.Interfaz.Mutacion;
import Soluciones.Solucion;

import java.util.List;
import java.util.Random;

public class MutacionInvertir implements Mutacion {

    @Override
    public void run(List<Solucion> o, double probMut) {
        Random rand = new Random();
        for (Solucion s : o) {
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
    }
}
