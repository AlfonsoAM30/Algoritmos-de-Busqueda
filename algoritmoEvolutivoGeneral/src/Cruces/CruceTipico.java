package Cruces;

import Cruces.Interfaz.Cruce;
import Instancias.Interfaz.Instancia;
import Soluciones.Solucion;

import java.util.ArrayList;
import java.util.List;

public class CruceTipico implements Cruce {

    @Override
    public List<Solucion> run(List<Solucion> poblacionPrima, double probCruce, Instancia instancia) {
        List<Solucion> descendientes = new ArrayList<>();
        // Collections.shuffle(poblacionPrima);
        for (int i = 0; i < poblacionPrima.size(); i += 2) {
            Solucion padre1 = poblacionPrima.get(i);
            Solucion padre2 = poblacionPrima.get(i + 1);

            if (Math.random() < probCruce) {
                int puntoDeCruce = (int) (Math.random() * instancia.n());

                Solucion hijo1 = padre1.copia();
                Solucion hijo2 = padre2.copia();

                for (int j = puntoDeCruce; j < instancia.n(); j++) {
                    hijo1.put(j, padre2.get(j));
                    hijo2.put(j, padre1.get(j));
                }
                descendientes.add(hijo1);
                descendientes.add(hijo2);
            } else {
                descendientes.add(padre1);
                descendientes.add(padre2);
            }
        }

        return descendientes;
    }
}
