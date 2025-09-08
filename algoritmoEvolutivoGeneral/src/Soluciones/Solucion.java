package Soluciones;

import java.util.Arrays;

public class Solucion {
    private int[] valores;
    private double fitness;
    private  double probability;

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

    public void setValores(int[] valores) {this.valores = valores;}

    public void setFitness(double valorFitness) {
        fitness = valorFitness;
    }

    public double getFitness() {
        return fitness;
    }

    public boolean esPeorMaximizarQue(Solucion otra) {
        return this.fitness < otra.fitness;
    }

    public boolean esPeorMinimizarQue(Solucion otra) {return this.fitness > otra.fitness; }

    public double getProbability() {return probability;}

    public void setProbability(double probability) {this.probability = probability;}

    public Solucion copia() {
        Solucion copia = new Solucion(valores.length);
        copia.valores = valores.clone();
        copia.fitness = fitness;
        return copia;
    }

}