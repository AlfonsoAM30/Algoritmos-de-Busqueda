package Selecciones;

import Instancias.Interfaz.Instancia;
import Soluciones.Solucion;

import java.util.*;

public class Selecciones {

    public static List<Solucion> seleccionPorCalidad(List<Solucion> poblacion, int tamanioPoblacion) { // Cambiar a hashSet
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

    public static List<Solucion> seleccionPorRanking(List<Solucion> poblacion, int tamanioPoblacion) {
        List<Solucion> seleccionados = new ArrayList<>();
        List<Solucion> copiaPoblacion = new ArrayList<>(poblacion);
        copiaPoblacion.sort(Comparator.comparing(Solucion::getFitness).reversed());

        double sumaRanking = tamanioPoblacion * (tamanioPoblacion + 1) / 2.0;
        for (int i = 0; i < tamanioPoblacion; i++) {
            double probabilidad = (tamanioPoblacion - i) / sumaRanking;
            copiaPoblacion.get(i).setProbability(probabilidad);
        }

        for (int i = 0; i < tamanioPoblacion; i++) {
            double puntoAleatorio = Math.random();
            double acumulado = 0.0;

            for (Solucion s : copiaPoblacion) {
                acumulado += s.getProbability();
                if (puntoAleatorio <= acumulado) {
                    seleccionados.add(s);
                    break;
                }
            }
        }

        return seleccionados;
    }


    public static List<Solucion> seleccionPorTorneo(List<Solucion> poblacion, int k, boolean reemplazo, int tamanioPoblacion) {
        List<Solucion> seleccionados = new ArrayList<>();
        Random rand = new Random();

        for (int i = 0; i < tamanioPoblacion; i++) {
            List<Solucion> torneo = new ArrayList<>();

            for (int j = 0; j < k; j++) {
                if (reemplazo) {
                    torneo.add(poblacion.get(rand.nextInt(poblacion.size())));
                } else {
                    Solucion candidato;
                    do {
                        candidato = poblacion.get(rand.nextInt(poblacion.size()));
                    } while (torneo.contains(candidato));
                    torneo.add(candidato);
                }
            }
            seleccionados.add(torneo.stream().min(Comparator.comparing(Solucion::getFitness)).get());
        }
        return seleccionados;
    }

    // Para Set
    public static Set<Solucion> seleccionPorTorneoSinReps(Set<Solucion> poblacion, int k, boolean reemplazo, int tamanioPoblacion, Instancia instancia) {
        List<Solucion> pobalcionAux = new ArrayList<>(poblacion);
        Set<Solucion> seleccionados = new HashSet<>();
        Random rand = new Random();

        for (int i = 0; i < tamanioPoblacion; i++) {
            List<Solucion> torneo = new ArrayList<>();

            for (int j = 0; j < k; j++) {
                if (reemplazo) {
                    torneo.add(pobalcionAux.get(rand.nextInt(pobalcionAux.size())));
                } else {
                    Solucion candidato;
                    do {
                        candidato = pobalcionAux.get(rand.nextInt(pobalcionAux.size()));
                    } while (torneo.contains(candidato));
                    torneo.add(candidato);
                }
            }
            seleccionados.add(torneo.stream().min(Comparator.comparing(Solucion::getFitness)).get());
        }
        return seleccionados;
    }


    // AUXILIARES

    private static double sumaTotalFitness(List<Solucion> poblacion) {
        double suma = 0;
        for (Solucion s : poblacion) {
            suma += s.getFitness();
        }
        return suma;
    }

    // Para Set
}
