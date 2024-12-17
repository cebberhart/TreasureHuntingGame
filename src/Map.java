import java.util.ArrayList;

public class Map {
    private ArrayList<Location> locations;

    public Map() {
        locations = new ArrayList<>();
    }

    public void addLocation(Location location){
        locations.add(location);
    }

    public ArrayList<Location> getLocations() {
        return new ArrayList<>(locations);
    }

}
