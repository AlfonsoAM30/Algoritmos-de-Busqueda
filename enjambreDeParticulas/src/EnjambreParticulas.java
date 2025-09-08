import java.security.cert.X509Certificate;
import java.util.Random;

public class EnjambreParticulas implements enjambreParticulasInterfaz {

    @Override
    public double[] PSOrun(int numParticulas, int maxIteraciones, double w, double c1, double c2, double[] intervalo, int dimensiones) {
        double[][] X = creaParticulasIniciales(numParticulas, intervalo, dimensiones);
        double[][] V = crearVelocidadesIniciales(numParticulas, dimensiones);
        // Inicialización de los mejores valores (se puede hacer en el primer paso)

        double[][] pBest = new double[numParticulas][dimensiones];
        for (int i = 0; i < numParticulas; i++) {
            pBest[i] = X[i].clone();
        }
        double[] gBest = obtenerMejor(X, dimensiones);

        for (int i = 0; i < maxIteraciones; i++) {
            actualizarVelocidades(X, V, w, c1, c2, pBest, gBest);
            // Se actualizan pBest y gBest al actualizar X
            actualizarPosiciones (X, V, pBest, gBest, intervalo);
            gBest = obtenerMejor(X, dimensiones);
        }
        return gBest;
    }

    private static void actualizarPosiciones(double[][] x, double[][] v, double[][] pBest, double[] gBest, double[] intervalo) {
        for (int i = 0; i < x.length; i++) {
            for (int j = 0; j < x[i].length; j++) {
                x[i][j] += v[i][j];

                if (x[i][j] < intervalo[0]){
                    x[i][j] = intervalo[0];
                } else if (x[i][j] > intervalo[1]){
                    x[i][j] = intervalo[1];
                }
            }
            double valor = funcionObjetivo(x[i]);
            if (valor < funcionObjetivo(pBest[i])){
                pBest[i] = x[i].clone();
            }
        }
    }

    private static void actualizarVelocidades(double[][] x, double[][] v, double w, double c1, double c2, double[][] pBest, double[] gBest) {
        Random rand = new Random();
        for (int i = 0; i < v.length; i++) {
            for (int j = 0; j < v[i].length; j++) {
                double r1 = rand.nextDouble();
                double r2 = rand.nextDouble();
                v[i][j] = w * v[i][j] + c1 * r1 * (pBest[i][j] - x[i][j]) + c2 * r2 * (gBest[j] - x[i][j]);
            }
        }
    }

    private static double[] obtenerMejor(double[][] x, int dimensiones) {
        double mejorValorGlobal = Double.MAX_VALUE;
        double[] mejorPosicionGlobal = new double[dimensiones];
        for (int i = 0; i < x.length; i++) {
            double valor = funcionObjetivo(x[i]);
            if (valor < mejorValorGlobal) {
                mejorPosicionGlobal = x[i].clone();
                mejorValorGlobal = valor;
            }
        }
        return mejorPosicionGlobal;
    }

    private static double[][] crearVelocidadesIniciales(int numParticulas, int dimensiones) {
        Random rand = new Random();
        double[][] V = new double[numParticulas][dimensiones];
        for (int i = 0; i < numParticulas; i++) {
            for (int j = 0; j < dimensiones; j++) {
                V[i][j] = rand.nextDouble();
            }
        }
        return V;
    }

    private static double[][] creaParticulasIniciales(int numParticulas, double[] intervalo, int dimensiones) {
        Random random = new Random();
        double[][] X = new double[numParticulas][dimensiones];
        for (int i = 0; i < numParticulas; i++) {
            for (int j = 0; j < dimensiones; j++) {
                X[i][j] = intervalo[0] + (intervalo[1] - intervalo[0]) * random.nextDouble();
            }
        }
        return X;
    }

    public static double funcionObjetivo(double[] posicion){
        double x = posicion[0];
        double y = posicion[1];
        return Math.pow(x - 3.14, 2) + Math.pow(y - 2.72, 2) + Math.sin(3 * x + 1.41) + Math.sin(4 * y - 1.73);
    }

}
