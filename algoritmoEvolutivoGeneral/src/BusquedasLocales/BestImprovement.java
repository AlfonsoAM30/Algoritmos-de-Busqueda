package BusquedasLocales;

import BusquedasLocales.Interfaz.BusquedaLocal;
import Instancias.Interfaz.Instancia;
import Soluciones.Solucion;

public class BestImprovement implements BusquedaLocal {

    @Override
    public Solucion run(Solucion s, Instancia instancia) {
        boolean improvement = true;
        Solucion currentRoute = s.copia();

        while (improvement) {
            improvement = false;
            int n = currentRoute.getValores().length;
            double bestDistance = instancia.evaluar(s);
            int bestI = -1, bestJ = -1;

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (i != j) {
                        Solucion newRoute = swap(currentRoute, i, j);
                        double newDistance = instancia.evaluar(newRoute);
                        if (newDistance < bestDistance) {
                            bestDistance = newDistance;
                            bestI = i;
                            bestJ = j;
                            improvement = true;
                        }
                    }
                }
            }
            if (improvement) {
                currentRoute = swap(currentRoute, bestI, bestJ);
            }
        }

        for (int i = 0; i < currentRoute.getValores().length; i++) {
            s.put(i, currentRoute.get(i));
        }

        return s;
    }

    private Solucion swap(Solucion route, int i, int j) {
        Solucion newRoute = route.copia();
        int temp = newRoute.get(i);
        newRoute.put(i, newRoute.getValores()[j]);
        newRoute.put(i, temp);
        return newRoute;
    }
}
