package Instancias;

import Instancias.Interfaz.Instancia;
import Soluciones.Solucion;


public class InstanciaDe1 implements Instancia {
    int n;

    public InstanciaDe1(int n) {
        this.n = n;
    }

    @Override
    public int n() {
        return n;
    }

    @Override
    public Solucion generarSolucionAleatoria() {
        Solucion s = new Solucion(n);
        for (int i=0; i<n; i++) {
            // Valor aleatorio 0 o 1
            int valor = (int) (Math.random() * 2);
            s.put(i, valor);
        }
        return s;
    }

    @Override
    public double evaluar(Solucion s) {
        int fitness = 0;
        for (int i=0; i<n; i++) {
            fitness += s.get(i);
        }
        return fitness;
    }
}
