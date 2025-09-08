package Selecciones;

import Selecciones.Interfaz.Seleccion;
import Selecciones.Interfaz.SeleccionTorneoInterfaz;
import Soluciones.Solucion;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class SeleccionPorTorneo implements SeleccionTorneoInterfaz {

    @Override
    public List<Solucion> run(List<Solucion> poblacion, int k, boolean reemplazo, int tamanioPoblacion) {
        List<Solucion> seleccionados = new ArrayList<>();
        Random rand = new Random();

        for (int i = 0; i < tamanioPoblacion; i++) {
            List<Solucion> torneo = new ArrayList<>();

            for (int j = 0; j < k; j++) {
                if (reemplazo) {
                    torneo.add(poblacion.get(rand.nextInt(poblacion.size())));
                } else {
                    Solucion candidato;
                    do {
                        candidato = poblacion.get(rand.nextInt(poblacion.size()));
                    } while (torneo.contains(candidato));
                    torneo.add(candidato);
                }
            }
            seleccionados.add(torneo.stream().min(Comparator.comparing(Solucion::getFitness)).get());
        }
        return seleccionados;
    }
}
