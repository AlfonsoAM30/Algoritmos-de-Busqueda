package Mutaciones;

import Mutaciones.Interfaz.Mutacion;
import Soluciones.Solucion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MutacionScramble implements Mutacion {

    @Override
    public void run(List<Solucion> o, double probMut) {
        Random rand = new Random();
        for (Solucion s : o) {
            if (rand.nextDouble() < probMut) {
                int numGenes = 2 + rand.nextInt(s.getValores().length - 1);
                List<Integer> indices = new ArrayList<Integer>();
                while (indices.size() < numGenes) {
                    int index = rand.nextInt(s.getValores().length);
                    if (!indices.contains(index)) {
                        indices.add(index);
                    }
                }
                List<Integer> subCadena = new ArrayList<Integer>();
                for (Integer i : indices) {
                    subCadena.add(s.getValores()[i]);
                }
                Collections.shuffle(subCadena);
                for (int i = 0; i < indices.size(); i++) {
                    s.getValores()[indices.get(i)] = subCadena.get(i);
                }
            }
        }
    }
}
