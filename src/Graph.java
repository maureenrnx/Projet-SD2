import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Graph {
    private Map<City, Set<Road>> outputRoads;
    private Map<Integer, City> correspondanceIndiceCity;
    private Map<String, City> correspondanceNameCity;

    public Graph(File cities, File roads) {
        outputRoads = new HashMap<City, Set<Road>>();
        correspondanceIndiceCity = new HashMap<Integer, City>();
        correspondanceNameCity = new HashMap<String, City>();

        readCities(cities);
        readRoads(roads);
    }

    protected void ajouterSommet(City c) {
        outputRoads.put(c, new HashSet<>());
        correspondanceIndiceCity.put(c.getId(), c);
        correspondanceNameCity.put(c.getName(), c);

    }

    public void ajouterArc(Road r) {
        Set<Road> s = outputRoads.get(r.getSource());
        s.add(r);
    }

    public Set<Road> arcSortants(City c1) {
        return outputRoads.get(c1);
    }

    public void calculerItineraireMinimisantNombreRoutes(String c1, String c2) {
        try {
            City city1 = stringToCity(c1);
            City city2 = stringToCity(c2);

            if (city1 == null || city2 == null || c1.equals(" ") || c2.equals(" ")) {
                System.out.println("Paramètres invalides");
                return;
            }

            Queue<City> fileDeSommets = new LinkedList<>();
            Map<City, Road> sommetsVisites = new HashMap<>();

            fileDeSommets.offer(city1);

            // Recherche du chemin minimisant le nombre de route a partir des arcSortants
            while (!fileDeSommets.isEmpty()) {
                City baladeur = fileDeSommets.poll();

                for (Road r : arcSortants(baladeur)) {
                    City voisin = r.getDestination();

                    if (!sommetsVisites.containsKey(voisin)) {
                        fileDeSommets.offer(voisin);
                        sommetsVisites.put(voisin, r);
                    }
                }
            }

            if (!sommetsVisites.containsKey(city2)) {
                throw new NoSuchElementException();
            }

            // Construction du chemin à partir des villes visitées
            List<Road> chemin = new LinkedList<>();
            double distanceTotale = 0;
            City baladeur = city2;

            while (baladeur != null) {
                Road road = sommetsVisites.get(baladeur);
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

            afficherTrajet(chemin,distanceTotale,city1,city2);

        } catch (NoSuchElementException e) {
            System.err.println("Erreur : Aucun trajet trouvé entre " + c1 + " et " + c2);
        }
    }

    public void calculerItineraireMinimisantKm(String c1, String c2) {
        try {
            City city1 = stringToCity(c1);
            City city2 = stringToCity(c2);

            if (city1 == null || city2 == null || c1.equals(" ") || c2.equals(" ")) {
                System.out.println("Paramètres invalides");
                return;
            }

            Map<City, Double> etiquettesDefinitives = new HashMap<>();
            TreeSet<City> etiquettesProvisoires = new TreeSet<>(new Comparator<City>() {
                @Override
                public int compare(City cityA, City cityB) {
                    // Comparaison par distance
                    int comparaisonDistance = Double.compare(etiquettesDefinitives.get(cityA), etiquettesDefinitives.get(cityB));
                    if (comparaisonDistance != 0) {
                        return comparaisonDistance;
                    }
                    // Si les distances sont égales, comparer par nom
                    return cityA.getName().compareTo(cityB.getName());
                }
            });
            Map<City, Road> villesVisitees = new HashMap<>();

            // Remplissage des etiquettesProvisoires et etiquettes
            for (City city : outputRoads.keySet()) {
                if (city.equals(city1)) {
                    etiquettesDefinitives.put(city, 0.0);
                    etiquettesProvisoires.add(city);
                } else {
                    etiquettesDefinitives.put(city, Double.MAX_VALUE);
                    etiquettesProvisoires.add(city);
                }
            }

            while (!etiquettesProvisoires.isEmpty()) {
                City currentCity = etiquettesProvisoires.pollFirst();
                etiquettesProvisoires.remove(currentCity);

                if (currentCity.equals(city2)) {
                    break;
                }

                //Recherche de la plus petite distance -> remplissage des éttiquettes
                for (Road road : outputRoads.get(currentCity)) {
                    City neighborCity = road.getDestination();
                    double distanceToNeighbor = road.getDistance();
                    double distanceThroughCurrent = etiquettesDefinitives.get(currentCity) + distanceToNeighbor;

                    if (distanceThroughCurrent < etiquettesDefinitives.get(neighborCity)) {
                        etiquettesProvisoires.remove(neighborCity);
                        etiquettesDefinitives.put(neighborCity, distanceThroughCurrent);
                        etiquettesProvisoires.add(neighborCity);
                        villesVisitees.put(neighborCity, road);
                    }
                }
            }

            // Construction du chemin à partir des villes visitées
            List<Road> chemin = new LinkedList<>();
            double distanceTotale = 0;
            City currentCity = city2;

            while (villesVisitees.containsKey(currentCity)) {
                Road road = villesVisitees.get(currentCity);
                chemin.addFirst(road);
                distanceTotale += road.getDistance();
                if (road.getSource().equals(currentCity)) {
                    currentCity = road.getDestination();
                } else {
                    currentCity = road.getSource();
                }
            }

            afficherTrajet(chemin,distanceTotale,city1,city2);

        } catch (NoSuchElementException e) {
            System.err.println("Erreur : Aucun trajet trouvé entre " + c1 + " et " + c2);
        }
    }

    // Méthode pour afficher le résultat demandé
    public void afficherTrajet(List<Road> chemin, double distanceTotale, City c1, City c2){
        System.out.println("Trajet de " + c1.getName() + " à " + c2.getName() + ": " + chemin.size() + " routes et " + distanceTotale + " kms");
        for (Road road : chemin) {
            System.out.println(road.getSource().getName() + " -> " + road.getDestination().getName() + " (" + String.format("%.2f", road.getDistance()) + " km)");
        }
    }

    public City stringToCity(String city){
        return correspondanceNameCity.get(city);
    }

    //Lecture du fichier cities.txt et remplissage des structures
    public void readCities(File file) {
        try {
            String line;
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

    // Lecture du fichier roads.txt et remplissage des structures
    public void readRoads(File file) {
        try {
            String line;
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
