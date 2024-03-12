import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Graph {
    private Map<City, Set<Road>> outputRoad;
    private Map<Integer, City> correspondanceIndiceCity;
    private Map<String, City> correspondanceNameCity;
    public int nbCity = 0;

    public Graph(File cities, File roads) {
        outputRoad = new HashMap<City, Set<Road>>();
        correspondanceIndiceCity = new HashMap<Integer, City>();
        correspondanceNameCity = new HashMap<String,City>();
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
        City city1 = correspondanceNameCity.get(c1);
        City city2 = correspondanceNameCity.get(c2);

        for(Road road : arcSortants(city1)){
            if(road.getDestination().equals(city2)){
                System.out.println(road.getDistance());
            }
        }

        /*
        City baladeur = new city ( ... )

        Arraylist file = new Arraylist() // Les sommets parcourus en retirant les sommets choisis

        Pourquoi utilise-t-on une file ?

        // voir si c'est mieux hashSet ou hashMap

        HashMap retiens tt les sommets parcourru  -> liste ou set

        HashMap retiens la ville et la route par laquel on est passe
        Key -> City
        Value -> arcSortants

        while(baladeur != null){

        file.add(sommet);

        if(baladeur == city2){

        }
        */
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
                correspondanceIndiceCity.put(id,city);
                correspondanceNameCity.put(name,city);
                nbCity++;

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
                String[] parts = line.split(",");

                City sourceCity = correspondanceIndiceCity.get(Integer.parseInt(parts[0]));
                City destinationCity = correspondanceIndiceCity.get(Integer.parseInt(parts[1]));
                // ajouter nom dans correspondanceNameCity

                Road road = new Road(sourceCity, destinationCity);
                outputRoad.get(sourceCity).add(road);
            }
            reader.close();
        } catch (IOException e) {
            e.getMessage();
        }
    }
}
