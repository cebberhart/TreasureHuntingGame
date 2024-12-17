import java.util.ArrayList;
import java.util.List;

public class Player {
    private int health;
    private List<String> inventory;
    private Encounter currentEncounter;

    public Player() {
        this.health = 100; // Default health
        this.inventory = new ArrayList<>();
        this.currentEncounter = null;
    }

    // Get player's health
    public int getHealth() {
        return health;
    }

    // Set player's health
    public void setHealth(int health) {
        this.health = health;
    }

    // Check if player is alive
    public boolean isAlive() {
        return health > 0;
    }

    // Take damage
    public void takeDamage(int damage) {
        health -= damage;
    }

    // Get player's current encounter
    public Encounter getCurrentEncounter() {
        return currentEncounter;
    }

    // Set the current encounter
    public void setCurrentEncounter(Encounter encounter) {
        this.currentEncounter = encounter;
    }

    // Add a treasure to the inventory
    public void addTreasure(String treasure) {
        inventory.add(treasure);
    }

    // Get the player's inventory
    public List<String> getInventory() {
        return inventory;
    }
}
