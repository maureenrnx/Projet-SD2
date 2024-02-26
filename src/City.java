import java.util.Objects;

public class City {
    private int id;
    private String name;
    private double latitude, longitude;

    public City(int id, String name, double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof City city)) return false;
        return id == city.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
