package Cruces;

import Cruces.Interfaz.CruceSinReps;
import Instancias.Interfaz.Instancia;
import Soluciones.Solucion;

import java.util.*;

import static Auxiliares.CompletarHijoCruce.completarHijo;

public class CruceOrden1SinReps implements CruceSinReps {

    @Override
    public Set<Solucion> run(Set<Solucion> poblacionPrima, double probCruce, Instancia instancia) {
        Random rand = new Random();
        List<Solucion> poblacionAux = new ArrayList<>(poblacionPrima);
        Set<Solucion> descendientes = new HashSet<>();

        for (int i = 0; i < poblacionAux.size(); i += 2) {
            Solucion padre1 = poblacionAux.get(i);
            Solucion padre2 = poblacionAux.get(i + 1);

            if (Math.random() < probCruce) {
                // Seleccionar puntos de cruce aleatorios, asegurando que start < end
                int start = rand.nextInt(instancia.n());
                int end = rand.nextInt(instancia.n());
                while (start == end) {
                    end = rand.nextInt(instancia.n());
                }
                if (start > end) {
                    int temp = start;
                    start = end;
                    end = temp;
                }

                Solucion hijo1 = new Solucion(instancia.n());
                Solucion hijo2 = new Solucion(instancia.n());

                // Inicializar con valores -1 para marcadores
                Arrays.fill(hijo1.getValores(), -1);
                Arrays.fill(hijo2.getValores(), -1);

                // Copiar segmento del primer padre a hijo1 y del segundo padre a hijo2
                for (int j = start; j <= end; j++) {
                    hijo1.put(j, padre1.get(j));
                    hijo2.put(j, padre2.get(j));
                }

                // Rellenar hijo1 y hijo2
                completarHijo(hijo1, padre2, start, end);
                completarHijo(hijo2, padre1, start, end);

                descendientes.add(hijo1);
                descendientes.add(hijo2);
            } else {
                descendientes.add(padre1.copia()); // Copia para evitar modificar padres originales
                descendientes.add(padre2.copia());
            }
        }
        return descendientes;
    }
}
