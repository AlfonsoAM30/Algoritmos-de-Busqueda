package Mutaciones;

import Mutaciones.Interfaz.Mutacion;
import Soluciones.Solucion;

import java.util.List;
import java.util.Random;

public class MutacionSwap implements Mutacion {

    @Override
    public void run(List<Solucion> o, double probMut) {
        Random rand = new Random();
        for (Solucion s : o) {
            if (rand.nextDouble() < probMut) {
                int x = rand.nextInt(s.getValores().length);
                int y;
                do {
                    y = rand.nextInt(s.getValores().length);
                } while (x == y || s.getValores()[x] == s.getValores()[y]);
                int[] copia = new int[s.getValores().length];
                for (int k = 0; k < s.getValores().length; k++) {
                    copia[k] = s.getValores()[k];
                }
                int temp = s.getValores()[x];
                s.put(x, copia[y]);
                s.put(y, temp);
            }
        }
    }
}
