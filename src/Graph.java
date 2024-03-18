import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

public class Graph {
    private Map<City, Set<Road>> outputRoad;
    private Map<Integer, City> correspondanceIndiceCity;
    private Map<String, City> correspondanceNameCity;
    public int nbCity = 0;

    public Graph(File cities, File roads) {
        outputRoad = new HashMap<City, Set<Road>>();
        correspondanceIndiceCity = new HashMap<Integer, City>();
        correspondanceNameCity = new HashMap<String, City>();
    }

    protected void ajouterSommet(City c) {
        outputRoad.put(c, new HashSet<>());
        correspondanceIndiceCity.put(c.getId(), c);
        correspondanceNameCity.put(c.getName(), c);
        nbCity++;
    }

    public void ajouterArc(Road r) {
        Set<Road> s = outputRoad.get(r.getSource());
        s.add(r);
    }

    public boolean sontAdjacents(City c1, City c2) {
        for (Road road : outputRoad.get(c1)) {
            if (road.getDestination().equals(c2)) {
                return true;
            }
        }

        for (Road road : outputRoad.get(c2)) {
            if (road.getDestination().equals(c1)) {
                return true;
            }
        }
        return false;
    }

    public Set<Road> arcSortants(City c1) {
        return outputRoad.get(c1);
    }

    //BFS
    public void calculerItineraireMinimisantNombreRoutes(String c1, String c2) {
        City city1 = correspondanceNameCity.get(c1);
        City city2 = correspondanceNameCity.get(c2);

        if (city1 == null || city2 == null) {
            System.out.println("Villes non trouvées dans le graphe.");
            return;
        }

        Queue<City> fileDeSommets = new LinkedList<>();
        Map<City, Road> predecesseurs = new HashMap<>();

        fileDeSommets.offer(city1);

        while (!fileDeSommets.isEmpty()) {
            City baladeur = fileDeSommets.poll();

            for (Road r : arcSortants(baladeur)) {
                City voisin = r.getDestination();

                if (!predecesseurs.containsKey(voisin)) {
                    fileDeSommets.offer(voisin);
                    predecesseurs.put(voisin, r);
                }
            }
        }

        if (!predecesseurs.containsKey(city2)) {
            System.out.println("Aucun chemin trouvé entre " + c1 + " et " + c2);
            return;
        }

        LinkedList<Road> chemin = new LinkedList<>();
        double distanceTotale = 0;
        City baladeur = city2;

        while (baladeur != null) {
            Road road = predecesseurs.get(baladeur);
            if (baladeur == city1) {
                break;
            }
            if (road != null) {
                chemin.addFirst(road);
                distanceTotale += road.getDistance();
                baladeur = road.getSource();
            } else {
                baladeur = null;
            }
        }

        System.out.println("Itinéraire de " + c1 + " à " + c2 + ": " + chemin.size() + " routes et " + String.format("%.2f", distanceTotale) + " km");
        for (Road road : chemin) {
            System.out.println(road.getSource().getName() + " -> " + road.getDestination().getName() + "(" + String.format("%.2f", road.getDistance()) + " km)");
        }
    }

    //Dijkstra
    public void calculerItineraireMinimisantKm(String c1, String c2) {

    }

    public void readCities(File file) {
        try {
            String line;
            // ArrayList<String> data= new ArrayList<>();
            FileReader reader = new FileReader(file);
            BufferedReader br = new BufferedReader(reader);

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                double latitude = Double.parseDouble(parts[2]);
                double longitude = Double.parseDouble(parts[3]);

                City city = new City(id, name, latitude, longitude);

                ajouterSommet(city);
            }
            reader.close();
        } catch (IOException e) {
            e.getMessage();
        }
    }

    public void readRoads(File file) {
        try {
            String line;
            // ArrayList<String> data= new ArrayList<>();
            FileReader reader = new FileReader(file);
            BufferedReader br = new BufferedReader(reader);

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                City sourceCity = correspondanceIndiceCity.get(Integer.parseInt(parts[0]));
                City destinationCity = correspondanceIndiceCity.get(Integer.parseInt(parts[1]));

                Road road1 = new Road(sourceCity, destinationCity);
                Road road2 = new Road(destinationCity, sourceCity);
                ajouterArc(road1);
                ajouterArc(road2);
            }
            reader.close();
        } catch (IOException e) {
            e.getMessage();
        }
    }
}
