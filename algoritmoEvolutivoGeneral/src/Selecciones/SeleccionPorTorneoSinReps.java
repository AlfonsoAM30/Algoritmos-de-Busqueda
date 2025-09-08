package Selecciones;

import Selecciones.Interfaz.SeleccionTorneoSinRepsInterfaz;
import Soluciones.Solucion;

import java.util.*;

public class SeleccionPorTorneoSinReps implements SeleccionTorneoSinRepsInterfaz {

    @Override
    public  Set<Solucion> run(Set<Solucion> poblacion, int k, boolean reemplazo, int tamanioPoblacion) {
        List<Solucion> pobalcionAux = new ArrayList<>(poblacion);
        Set<Solucion> seleccionados = new HashSet<>();
        Random rand = new Random();

        for (int i = 0; i < tamanioPoblacion; i++) {
            List<Solucion> torneo = new ArrayList<>();

            for (int j = 0; j < k; j++) {
                if (reemplazo) {
                    torneo.add(pobalcionAux.get(rand.nextInt(pobalcionAux.size())));
                } else {
                    Solucion candidato;
                    do {
                        candidato = pobalcionAux.get(rand.nextInt(pobalcionAux.size()));
                    } while (torneo.contains(candidato));
                    torneo.add(candidato);
                }
            }
            seleccionados.add(torneo.stream().min(Comparator.comparing(Solucion::getFitness)).get());
        }
        return seleccionados;
    }
}
