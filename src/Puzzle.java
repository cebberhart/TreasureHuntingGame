public class Puzzle {
    private String question;
    private String correctAnswer;
    private boolean solved;

    public Puzzle(String question, String correctAnswer) {
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.solved = false;
    }

    // Get the puzzle question
    public String getQuestion() {
        return question;
    }

    // Get the correct answer to the puzzle
    public String getCorrectAnswer() {
        return correctAnswer;
    }

    // Check if the puzzle is solved
    public boolean isSolved() {
        return solved;
    }

    // Solve the puzzle
    public void solve() {
        this.solved = true;
    }
}
