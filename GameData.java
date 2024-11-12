public class GameData {
    private String playerName;
    private int score;

    public GameData(String playerName) {
        this.playerName = playerName;
        this.score = DatabaseConnection.getPlayerScore(playerName); 
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }

    public void addPoints(int points) {
        score += points;
        DatabaseConnection.updatePlayerScore(playerName, score); 
    }

    public void subtractPoints(int points) {
        score -= points;
        DatabaseConnection.updatePlayerScore(playerName, score); 
    }
}

