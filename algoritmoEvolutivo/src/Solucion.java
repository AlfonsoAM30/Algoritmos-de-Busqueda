import java.util.Arrays;

public class Solucion {
    private int[] valores;
    private int fitness;

    public Solucion(int n) {
        this.valores = new int[n];
    }

    public void put(int i, int valor) {
        valores[i] = valor;
    }

    @Override
    public String toString() {
        return "Fitness: " + fitness +
                " - Genotipo = " + Arrays.toString(valores);
    }

    public int get(int i) {
        return valores[i];
    }

    public int[] getValores(){
        return valores;
    }

    public void setFitness(int valorFitness) {
        fitness = valorFitness;
    }

    public int getFitness() {
        return fitness;
    }

    public boolean esPeorQue(Solucion otra) {
        return this.fitness < otra.fitness;
    }

    public Solucion copia() {
        Solucion copia = new Solucion(valores.length);
        copia.valores = valores.clone();
        copia.fitness = fitness;
        return copia;
    }

}