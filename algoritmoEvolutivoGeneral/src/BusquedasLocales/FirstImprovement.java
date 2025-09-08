package BusquedasLocales;

import BusquedasLocales.Interfaz.BusquedaLocal;
import Instancias.Interfaz.Instancia;
import Soluciones.Solucion;

public class FirstImprovement implements BusquedaLocal {

    @Override
    public Solucion run(Solucion s, Instancia instancia) {
        boolean improvement = true;
        Solucion currentRoute = s.copia();

        while (improvement) {
            improvement = false;
            int n = currentRoute.getValores().length;

            for (int i = 0; i < n - 1; i++) {
                for (int j = i + 1; j < n; j++) {
                    Solucion newRoute = swap(currentRoute, i, j);

                    if (instancia.evaluar(newRoute) < instancia.evaluar(currentRoute)) {
                        improvement = true;
                        currentRoute = newRoute;
                        break;
                    }
                }
                if (improvement) break;
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
