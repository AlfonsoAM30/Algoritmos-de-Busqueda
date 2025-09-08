package Auxiliares;

import Soluciones.Solucion;

public class Contiene {

    public static boolean contiene(Solucion hijo, int gen) {
        for (int i = 0; i < hijo.getValores().length; i++) {
            if (hijo.getValores()[i] == gen) {
                return true;
            }
        }
        return false;
    }
}
