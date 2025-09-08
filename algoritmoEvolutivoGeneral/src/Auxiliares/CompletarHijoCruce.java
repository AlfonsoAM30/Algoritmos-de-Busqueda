package Auxiliares;
import Soluciones.Solucion;
import static Auxiliares.Contiene.contiene;

public class CompletarHijoCruce {

    public static void completarHijo(Solucion hijo, Solucion padre, int start, int end) {
        int pos = (end + 1) % hijo.getValores().length;

        for (int i = 0; i < padre.getValores().length; i++) {
            int gen = padre.get((end + 1 + i) % padre.getValores().length);

            // Solo agregar genes que no estén en el segmento copiado del padre
            if (!contiene(hijo, gen)) {
                // Encontramos una posición válida para insertar el gen
                while (hijo.get(pos) != -1) {
                    pos = (pos + 1) % hijo.getValores().length;
                }
                hijo.put(pos, gen);
            }
        }
    }
}
