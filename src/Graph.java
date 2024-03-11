import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Graph {
    public Map<Integer, City> correspondanceIndiceCity;
    public Map<City, Integer> correspondanceCityInteger;
    public Road[][] matrice = new Road[0][0];
    public int nbCity = 0;


    public Graph(File cities, File roads) {
        correspondanceCityInteger = new HashMap<City, Integer>();
        correspondanceIndiceCity = new HashMap<Integer, City>();
    }

    protected void ajouterSommet(City c) {
        correspondanceIndiceCity.put(nbCity, c);
        correspondanceCityInteger.put(c, nbCity);
        nbCity++;
        Road[][] matrx = new Road[nbCity][nbCity];
        for (int i = 0; i < nbCity; i++) {
            for (int j = 0; i < nbCity; j++) {
                matrx[i][j] = matrice[i][j];
            }
        }
        matrice = matrx;
    }

    public boolean sontAdjacents(City c1, City c2) {
        // ils sont adjacents donc c' est dans les 2 sens
        // il faut vérifier s' ils sont nulls
        //
        return matrice[correspondanceCityInteger.get(c1)][correspondanceCityInteger.get(c2)] != null ||
                matrice[correspondanceCityInteger.get(c2)][correspondanceCityInteger.get(c1)] != null;
    }

    protected void ajouterArc(Road r) {
        int i = correspondanceCityInteger.get(r.getSource());
        int j = correspondanceCityInteger.get(r.getDestination());
        matrice[i][j] = r;
    }

    public Set<Road> arcSortants(City c1) {
        Set<Road> res = new HashSet<>();
        for (int i = 0; i < matrice.length; i++) {
            if (matrice[correspondanceCityInteger.get(c1)][i] != null){
                res.add(matrice[correspondanceCityInteger.get(c1)][i]);
            }
        }



    public void calculerItineraireMinimisantNombreRoutes() {

        }
    }

    }

    public void calculerItineraireMinimisantNombreRoutes(String berlin, String madrid) {
        
    }


    public void calculerItineraireMinimisantKm(String berlin, String madrid) {
    }
    public void calculerItineraireMinimisantNombreRoutes(String berlin, String madrid) {
    }

    public void readFile(File file){
        try
        {
            String line;
           // ArrayList<String> data= new ArrayList<>();
            FileReader reader = new FileReader(file);

            BufferedReader br = new BufferedReader(reader);

            while((line = br.readLine()) != null)
            {
                String[] parts = line.split(" ");
                for(String part: parts){
                    System.out.println(part);
                }

            }
            reader.close();

        }
        catch(IOException e)
        {
            e.getMessage();
        }
    }


}
