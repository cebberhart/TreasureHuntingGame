import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import javafx.scene.control.TextField;

public class TreasureHuntingGame extends Application {
    public static List<String> availableTreasure;
    private TextField puzzleAnswerField = new TextField();
    private Player player = new Player();
    private Map map = new Map();
    private Label infoLabel = new Label("Welcome to the Treasure Hunting Game!");
    private Label hpLabel = new Label("HP: " + player.getHealth());
    private int currentLocationIndex = 0;
    private Random random = new Random();

    // Controls
    private Button enterLocationButton = new Button("Enter Location");
    private Button solvePuzzleButton = new Button("Solve Puzzle");
    private Button nextLocationButton = new Button("Go to Next Location");
    private Button viewInventoryButton = new Button("View Inventory");
    private Button fightButton = new Button("Fight");
    private Button negotiateButton = new Button("Negotiate");
    private Button fleeButton = new Button("Flee");

    @Override
    public void start(Stage primaryStage) {
        initializeMap();

        enterLocationButton.setOnAction(e -> enterLocation(currentLocationIndex));
        nextLocationButton.setOnAction(e -> goToNextLocation());
        viewInventoryButton.setOnAction(e -> viewInventory());
        fightButton.setOnAction(e -> handleFight());
        negotiateButton.setOnAction(e -> handleNegotiate());
        fleeButton.setOnAction(e -> handleFlee());

        solvePuzzleButton.setOnAction(e -> {
            Location location = map.getLocations().get(currentLocationIndex);
            Puzzle puzzle = location.getPuzzle();
            if (puzzle != null && !puzzle.isSolved()) {
                String playerAnswer = puzzleAnswerField.getText();
                if (playerAnswer.equalsIgnoreCase(puzzle.getCorrectAnswer())) {
                    puzzle.solve();
                    infoLabel.setText("Correct! You solved the puzzle and gained a reward: Crystal Shard!");
                    player.addTreasure("Crystal Shard");
                    puzzleAnswerField.clear();
                    puzzleAnswerField.setVisible(false);
                    solvePuzzleButton.setVisible(false);
                } else {
                    infoLabel.setText("Incorrect answer. Try again.");
                }
            }
        });

        VBox layout = new VBox(10, infoLabel, hpLabel, enterLocationButton, nextLocationButton, viewInventoryButton,
                fightButton, negotiateButton, fleeButton, puzzleAnswerField, solvePuzzleButton);
        Scene scene = new Scene(layout, 400, 350);

        primaryStage.setTitle("Treasure Hunting Game");
        primaryStage.setScene(scene);
        primaryStage.show();

        toggleEncounterButtons(false); // Hide encounter buttons initially

        puzzleAnswerField.setPromptText("Enter your puzzle answer here");
        puzzleAnswerField.setVisible(false);
        solvePuzzleButton.setVisible(false);
    }

    private void initializeMap() {
        availableTreasure = new ArrayList<>();
        availableTreasure.add("Magic Wand");
        availableTreasure.add("Magic Cloak");
        availableTreasure.add("Crystal Shard");

        Location location1 = new Location("Enchanted Forest", "A lush forest filled with ancient trees.", null);
        location1.addEncounter(new Encounter("Wild Bear", -20));
        location1.addEncounter(new Encounter("Forest Elf", -10));
        map.addLocation(location1);

        Puzzle cavePuzzle = new Puzzle("What is 20 + 44?", "64");
        Location location2 = new Location("Crystal Cave", "A shimmering cave with hidden crystals.", cavePuzzle);
        location2.addEncounter(new Encounter("Crystal Golem", -15));
        map.addLocation(location2);
    }

    private void enterLocation(int locationNumber) {
        if (!player.isAlive()) {
            infoLabel.setText("Game Over! You can no longer explore.");
            disableGameControls();
            return;
        }

        Location location = map.getLocations().get(locationNumber);
        infoLabel.setText("You Entered " + location.getName() + ". " + location.getDescription());

        // Check for encounters
        if (random.nextInt(100) < 50) { // 50% chance of encounter
            Encounter encounter = location.getRandomEncounter();
            if (encounter != null) {
                player.setCurrentEncounter(encounter);
                infoLabel.setText(infoLabel.getText() + "\nYou encountered a " + encounter.getCreatureName() + "!");
                toggleEncounterButtons(true);
            }
        } else {
            infoLabel.setText(infoLabel.getText() + "\nNo creatures here. You're safe for now!");
            toggleEncounterButtons(false);
        }

        // Check if there's a puzzle at this location
        Puzzle puzzle = location.getPuzzle();
        if (puzzle != null && !puzzle.isSolved()) {
            // If there's an unsolved puzzle, show the puzzle button and answer field
            solvePuzzleButton.setVisible(true);
            puzzleAnswerField.setVisible(true);
        } else {
            // If no puzzle or already solved, hide the puzzle button and answer field
            solvePuzzleButton.setVisible(false);
            puzzleAnswerField.setVisible(false);
        }

        // Update HP display
        hpLabel.setText("HP: " + player.getHealth());
    }

    private void handleFight() {
        Encounter encounter = player.getCurrentEncounter();
        if (encounter != null) {
            int diceRoll = random.nextInt(6) + 1; // Roll 1-6
            if (diceRoll >= 4) { // Win on 4, 5, or 6
                infoLabel.setText("You defeated the " + encounter.getCreatureName() + "! Well done.");
                toggleEncounterButtons(false);
            } else { // Lose
                int damage = Math.abs(encounter.getHealthImpact());
                player.takeDamage(damage);
                infoLabel.setText("You fought bravely but took " + damage + " damage.");
                checkGameOver();
            }
            hpLabel.setText("HP: " + player.getHealth());
        }
    }

    private void handleNegotiate() {
        Encounter encounter = player.getCurrentEncounter();
        if (encounter != null) {
            int diceRoll = random.nextInt(6) + 1;
            if (diceRoll >= 3) { // Successful negotiation on 3+
                infoLabel.setText("You negotiated with the " + encounter.getCreatureName() + ". It lets you pass!");
                toggleEncounterButtons(false);
            } else {
                int damage = Math.abs(encounter.getHealthImpact()) / 2; // Half damage
                player.takeDamage(damage);
                infoLabel.setText("Negotiation failed! The " + encounter.getCreatureName() + " hit you for " + damage + " damage.");
                checkGameOver();
            }
            hpLabel.setText("HP: " + player.getHealth());
        }
    }

    private void handleFlee() {
        Encounter encounter = player.getCurrentEncounter();
        if (encounter != null) {
            int diceRoll = random.nextInt(6) + 1;
            if (diceRoll >= 5) { // Successful flee on 5 or 6
                infoLabel.setText("You fled successfully from the " + encounter.getCreatureName() + "!");
                toggleEncounterButtons(false);
            } else {
                int damage = Math.abs(encounter.getHealthImpact());
                player.takeDamage(damage);
                infoLabel.setText("Failed to flee! The " + encounter.getCreatureName() + " hit you for " + damage + " damage.");
                checkGameOver();
            }
            hpLabel.setText("HP: " + player.getHealth());
        }
    }

    private void checkGameOver() {
        if (!player.isAlive()) {
            infoLabel.setText("Game Over! You've been defeated.");
            disableGameControls();
        }
    }

    private void toggleEncounterButtons(boolean show) {
        fightButton.setVisible(show);
        negotiateButton.setVisible(show);
        fleeButton.setVisible(show);
    }

    private void goToNextLocation() {
        currentLocationIndex++;
        if (currentLocationIndex >= map.getLocations().size()) {
            currentLocationIndex = 0; // Loops back to the first location
        }
        enterLocation(currentLocationIndex);
    }

    private void viewInventory() {
        infoLabel.setText("Your Inventory: " + player.getInventory());
    }

    private void disableGameControls() {
        enterLocationButton.setDisable(true);
        nextLocationButton.setDisable(true);
        viewInventoryButton.setDisable(true);
        toggleEncounterButtons(false);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
