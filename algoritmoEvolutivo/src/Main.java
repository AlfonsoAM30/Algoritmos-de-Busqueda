public class Main {

    public static void main(String[] args) {
        int n = 30;
        Instancia instancia = new Instancia(n);

        // Parámetros del algoritmo
        int tamanioPoblacion = 20;
        double probCruce = 0.7;
        int iteraciones = 100; // Generaciones
        boolean debug = false;
        AlgoritmoEvolutivo algoritmo = new AlgoritmoEvolutivo(instancia,tamanioPoblacion, iteraciones, probCruce, debug);

        Solucion bestSolution = algoritmo.run();

        System.out.println("\nBest solution:\n" + bestSolution);
    }

}