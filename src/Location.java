import java.util.ArrayList;
import java.util.List;

public class Location {
    private String name;
    private String description;
    private Puzzle puzzle;
    private List<Encounter> encounters;

    public Location(String name, String description, Puzzle puzzle) {
        this.name = name;
        this.description = description;
        this.puzzle = puzzle;
        this.encounters = new ArrayList<>();
    }

    // Get location name
    public String getName() {
        return name;
    }

    // Get location description
    public String getDescription() {
        return description;
    }

    // Add an encounter to the location
    public void addEncounter(Encounter encounter) {
        encounters.add(encounter);
    }

    // Get a random encounter from the location
    public Encounter getRandomEncounter() {
        if (encounters.isEmpty()) {
            return null;
        }
        return encounters.get((int) (Math.random() * encounters.size()));
    }

    // Get the puzzle at this location
    public Puzzle getPuzzle() {
        return puzzle;
    }
}
