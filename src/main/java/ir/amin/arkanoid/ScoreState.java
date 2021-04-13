package ir.amin.arkanoid;

public class ScoreState {
    String username;
    int score;

    ScoreState(String username, int score) {
        this.username = username;
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public String getUsername() {
        return username;
    }
}