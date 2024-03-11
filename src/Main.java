import java.io.File;
public class Main {
	public static void main(String[] args) {
		File cities = new File("cities.txt");
		File roads = new File("roads.txt");
		Graph graph = new Graph(cities, roads);

		graph.readFile(cities);

		City source = new City(1,"Greenock",55.9473423,-4.7564721);
		City dest = new City(2,"Glasgow",55.861155,-4.2501687);
		Road road = new Road(source,dest);
		System.out.println("TEST DISTANCE " +road.getDistance());
		graph.calculerItineraireMinimisantNombreRoutes('berlin','madrid');
		System.out.println("--------------------------");
		graph.calculerItineraireMinimisantKm("Berlin", "Madrid");
	}

}
