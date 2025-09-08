public class Main {

    public static void main(String[] args) {
        // Definir los parámetros
        int numParticulas = 10;
        int maxIteraciones = 100;
        double w = 0.5;
        double c1 = 0.5;
        double c2 = 0.5;
        double[] intervalo = {0, 5};
        int dimensiones = 2;

        enjambreParticulasInterfaz PSO = new EnjambreParticulas();
        double[] mejorPosicion = PSO.PSOrun(numParticulas, maxIteraciones, w, c1, c2, intervalo, dimensiones);

        System.out.println("Mejor posición encontrada:");
        for (double pos : mejorPosicion) {
            System.out.print(pos + " ");
        }
        System.out.println(EnjambreParticulas.funcionObjetivo(mejorPosicion));
    }
}
