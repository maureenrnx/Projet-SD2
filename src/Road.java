import java.util.Objects;


public class Road {
    private City source;
    private City destination;
    private double distance;

    public Road(City source, City destination) {
        this.source = source;
        this.destination = destination;
        this.distance = Util.distance(source.getLatitude(),source.getLongitude(),destination.getLatitude(),destination.getLongitude());
    }

    public City getSource() {
        return source;
    }

    public void setSource(City source) {
        this.source = source;
    }

    public City getDestination() {
        return destination;
    }

    public void setDestination(City destination) {
        this.destination = destination;
    }

    public double getDistance() {
        return distance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Road road)) return false;
        return Double.compare(distance, road.distance) == 0 && Objects.equals(source, road.source) && Objects.equals(destination, road.destination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, destination, distance);
    }
}





