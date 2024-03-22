import java.io.File;

public class Main {
	public static void main(String[] args) {
		File cities = new File("cities.txt");
		File roads = new File("roads.txt");
		Graph graph = new Graph(cities, roads);
		graph.calculerItineraireMinimisantNombreRoutes("", "");
		System.out.println("--------------------------");
		graph.calculerItineraireMinimisantKm("", "");
	}

}
