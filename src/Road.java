import java.util.Objects;

public class Road {

    private  City source;
    private  City destination;
    private double distance;

    public Road(City source, City destination, double distance) {
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


    


}

