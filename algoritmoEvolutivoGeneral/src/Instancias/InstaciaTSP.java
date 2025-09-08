package Instancias;

import Instancias.Interfaz.Instancia;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import Soluciones.Solucion;

public class InstaciaTSP implements Instancia {

    private String name;
    private double[][] coordinates;
    private double[][] distances;
    private int dimension;

    public InstaciaTSP(String filename) throws IOException {
        this.name = filename;
        this.importInstance(new BufferedReader(new FileReader(filename)));
    }

    public double getDistancesCities(double[] location1, double[] location2) {
        double di = location1[0] - location2[0];
        double dj = location1[1] - location2[1];
        return Math.sqrt((di * di) + (dj * dj));
    }

    public void importInstance(BufferedReader reader) throws IOException {
        Scanner sc = new Scanner(reader).useLocale(Locale.US);
        String name = sc.nextLine().split(":")[1].trim();
        String type = sc.nextLine().split(":")[1];
        String comment = sc.nextLine().split(":")[1];
        int dimension = Integer.parseInt(sc.nextLine().split(":")[1].trim());
        String edgeWeightType = sc.nextLine().split(":")[1];
        String nodeCoordSection = sc.nextLine();
        this.coordinates = new double[dimension][2];
        while (!sc.hasNext("EOF")) {
            int id = sc.nextInt() - 1;
            double x = sc.nextDouble();
            double y = sc.nextDouble();
            this.coordinates[id][0] = x;
            this.coordinates[id][1] = y;
        }
        this.distances = new double[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = i + 1; j < dimension; j++) {
                distances[i][j] = getDistancesCities(coordinates[i], coordinates[j]);
                distances[j][i] = distances[i][j];
            }
        }
        this.dimension = dimension;
    }

    @Override
    public int n() {
        return this.dimension;
    }

    @Override
    public Solucion generarSolucionAleatoria() {
        Solucion s = new Solucion(dimension);
        List<Integer> lista = new ArrayList<>();

        for (int i = 0; i < dimension; i++) {
            lista.add(i);
        }
        Collections.shuffle(lista);
        for (int i = 0; i < lista.size(); i++) {
            s.put(i, lista.get(i));
        }

        return s;
    }

    @Override
    public double evaluar(Solucion s) {
        double fitness = 0.0;
        for (int i = 0; i < s.getValores().length - 1; i++) {
            fitness += distances[s.getValores()[i]][s.getValores()[i + 1]];
        }
        fitness += distances[s.getValores()[s.getValores().length - 1]][s.getValores()[0]];
        return fitness;
    }

    public double[] getCoordinates(int cityID) {
        return this.coordinates[cityID];
    }

    public int getNumberOfCities() {
        return this.coordinates.length;
    }

    public String getName() {
        return this.name;
    }

    public double getDistances(int cityID, int cityID2) { return this.distances[cityID][cityID2];}

    public double[][] getDistances2() { return this.distances;}

    public int getDimension() { return this.dimension;}

}
