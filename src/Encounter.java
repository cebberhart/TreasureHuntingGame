public class Encounter {
    private String creatureName;
    private int healthImpact; // Negative value means damage

    public Encounter(String creatureName, int healthImpact) {
        this.creatureName = creatureName;
        this.healthImpact = healthImpact;
    }

    // Get the creature's name
    public String getCreatureName() {
        return creatureName;
    }

    // Get the health impact (damage) from the encounter
    public int getHealthImpact() {
        return healthImpact;
    }
}
