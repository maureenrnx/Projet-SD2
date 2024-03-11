import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Graph {
    private Map<City, Set<Road>> outputRoad;
    private Map<Integer, City> correspondanceIndiceCity;
    public int nbCity = 0;

    public Graph(File cities, File roads) {
        outputRoad = new HashMap<City, Set<Road>>();
    }

    protected void ajouterSommet(City c) {
        outputRoad.put(c, new HashSet<>());
    }

    public boolean sontAdjacents(City c1, City c2) {
        for(Road road : outputRoad.get(c1)){
            if(road.getDestination().equals(c2)){
                return true;
            }
        }
        for(Road road : outputRoad.get(c2)){
            if(road.getDestination().equals(c1)){
                return true;
            }
        }
        return false;
    }

    public void ajouterArc(Road r) {
        Set<Road> s = outputRoad.get(r.getSource());
        s.add(r);
    }

    public Set<Road> arcSortants(City c1) {
        return outputRoad.get(c1);
    }

    //BFS
    public void calculerItineraireMinimisantNombreRoutes(String c1, String c2) {
        City city1 = correspondanceIndiceCity.get()
        for(Road road : arcSortants(c1))
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
a
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");

                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                double latitude = Double.parseDouble(parts[2]);
                double longitude = Double.parseDouble(parts[3]);

                City city = new City(id, name, latitude, longitude);
                correspondanceIndiceCity.put(id,city);

                Set<Road> routesSortantes = new HashSet<Road>();
                outputRoad.put(city,routesSortantes);
            }
            reader.close();
        } catch (IOException e) {
            e.getMessage();
        }
    }
    
    public void readRoads(File file){
        try {
            String line;
            // ArrayList<String> data= new ArrayList<>();
            FileReader reader = new FileReader(file);

            BufferedReader br = new BufferedReader(reader);

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");

                City sourceCity = correspondanceIndiceCity.get(Integer.parseInt(parts[0]));
                City destinationCity = correspondanceIndiceCity.get(Integer.parseInt(parts[1]));

                Road road = new Road(sourceCity, destinationCity);
                outputRoad.get(sourceCity).add(road);
            }
            reader.close();
        } catch (IOException e) {
            e.getMessage();
        }
    }
}
