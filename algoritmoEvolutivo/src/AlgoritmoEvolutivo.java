import java.util.ArrayList;
import java.util.List;

public class AlgoritmoEvolutivo {

    final Instancia instancia;
    final int tamanioPoblacion;
    final boolean debug;
    final int iteraciones;
    double probCruce = 0.6;

    public AlgoritmoEvolutivo(Instancia instancia, int tamanioPoblacion, int iteraciones,
                              double probCruce, boolean debug) {
        this.instancia = instancia;
        this.tamanioPoblacion = tamanioPoblacion;
        this.debug = debug;
        this.iteraciones = iteraciones;
        this.probCruce = probCruce;
    }


    public Solucion run() {
        List<Solucion> poblacion = generarPoblacionInicial();
        Solucion best = evaluarPoblacion(poblacion);
        imprimePoblacion("Población inicial:",poblacion,debug);

        for (int i = 0; i < this.iteraciones; i++) {
            List<Solucion> poblacionPrima = seleccionPorCalidad(poblacion); // Seleccionamos por calidad

            // Definimos las probabilidades
            double probCruce = this.probCruce;
            double probMut = 1 / (double) instancia.n;

            List<Solucion> O = cruce(poblacionPrima, probCruce); // Hacemos el cruce

            mutacion(O, probMut);
            Solucion bestO = evaluarPoblacion(O); // Evaluamos la mutación
            if (bestO.getFitness() > best.getFitness()) best = bestO; // Miramos si es mejor
            imprimePoblacion("Hijos: ", O, debug); // Imprimimos los hijos

            O.remove(peor(O)); // Quitamos el peor de O
            List<Solucion> poblacionUnida = new ArrayList<>(poblacion); // Inicializamos la población unida con la población
            poblacionUnida.addAll(O); // Añadimos todos de O
            Solucion mejorDeAmbas = mejor(poblacionUnida); // Obtenemos la mejor de ambas
            poblacion = reemplazarPeorConMejor(O, mejorDeAmbas); // remplazamos la O con la mejor de ambas
        }

        return best;
    }

    private List<Solucion> reemplazarPeorConMejor(List<Solucion> poblacion, Solucion mejorDeAmbas) {
        List<Solucion> nuevaPoblacion = new ArrayList<>(poblacion);
        // Encuentra el índice de la peor solución
        int indicePeor = 0;
        for (int i = 1; i < nuevaPoblacion.size(); i++) {
            if (nuevaPoblacion.get(i).esPeorQue(nuevaPoblacion.get(indicePeor))) {
                indicePeor = i;
            }
        }
        nuevaPoblacion.set(indicePeor, mejorDeAmbas); // Reemplaza la peor con la mejor
        return nuevaPoblacion;
    }

    private Solucion mejor(List<Solucion> poblacionUnida) {
        Solucion mejor = poblacionUnida.getFirst();
        for (Solucion s : poblacionUnida) {
            if (!s.esPeorQue(mejor)) {
                mejor = s;
            }
        }
        return mejor;
    }

    private Solucion peor(List<Solucion> o) {
        Solucion peor = o.getFirst();
        for (Solucion s : o) {
            if (s.esPeorQue(peor)) {
                peor = s;
            }
        }
        return peor;
    }

    private void mutacion(List<Solucion> o, double probMut) {
        for (Solucion s : o) {
            for (int i = 0; i < s.getValores().length; i++) {
                if (Math.random() < probMut) {
                    s.put(i, 1 - s.get(i)); // Invertir el valor
                }
            }
        }
    }

    private List<Solucion> cruce(List<Solucion> poblacionPrima, double probCruce) {
        List<Solucion> descendientes = new ArrayList<>();
        // Collections.shuffle(poblacionPrima);
        for (int i = 0; i < tamanioPoblacion; i += 2) {
            Solucion padre1 = poblacionPrima.get(i);
            Solucion padre2 = poblacionPrima.get(i + 1);

            if (Math.random() < probCruce) {
                int puntoDeCruce = (int) (Math.random() * instancia.n);

                Solucion hijo1 = padre1.copia();
                Solucion hijo2 = padre2.copia();

                for (int j = puntoDeCruce; j < instancia.n; j++) {
                    hijo1.put(j, padre2.get(j));
                    hijo2.put(j, padre1.get(j));
                }
                descendientes.add(hijo1);
                descendientes.add(hijo2);
            } else {
                descendientes.add(padre1);
                descendientes.add(padre2);
            }
        }
        
        return descendientes;
    }

    private List<Solucion> seleccionPorCalidad(List<Solucion> poblacion) { // Cambiar a hashSet
        List<Solucion> seleccionados = new ArrayList<>();
        double sumaFitness = sumaTotalFitness(poblacion);

        for (int i = 0; i < tamanioPoblacion; i++) {
            double puntoAleatorio = Math.random() * sumaFitness; // Generar un punto aleatorio entre 0 y la suma total de fitness
            double acumulado = 0.0; // Acumulación de los fitness para la ruleta

            // Buscar el individuo correspondiente al punto aleatorio
            for (Solucion s : poblacion) {
                acumulado += s.getFitness();
                if (acumulado >= puntoAleatorio) { // Si el acumulado es igual o supera el punto Random lo seleccionamos
                    seleccionados.add(s);
                    break;
                }
            }
        }

        return seleccionados;
    }

    private double sumaTotalFitness(List<Solucion> poblacion) {
        double suma = 0;
        for (Solucion s : poblacion) {
            suma += s.getFitness();
        }
        return suma;
    }

    private void imprimePoblacion(String msg, List<Solucion> poblacion, boolean debug) {
        if (debug) {
            System.out.println(msg);
            for (Solucion s : poblacion) {
                System.out.println(s);
            }
        }
    }

    private Solucion evaluarPoblacion(List<Solucion> poblacion) {
        Solucion best = null;
        int bestScore = Integer.MIN_VALUE;
        for (Solucion s : poblacion) {
            int fitness = instancia.evaluar(s);
            if (fitness > bestScore) {
                best = s;
                bestScore = fitness;
            }
            s.setFitness(fitness);
        }
        return best;
    }

    private List<Solucion> generarPoblacionInicial() {
        List<Solucion> poblacion = new ArrayList<>();
        for (int i = 0; i < tamanioPoblacion; i++) {
            poblacion.add(instancia.generarSolucionAleatoria());
        }
        return poblacion;
    }

}