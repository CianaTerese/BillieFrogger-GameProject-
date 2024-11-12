import java.io.*;
import java.util.*;

public class DatabaseConnection {
    private static final String DATABASE_FILE = "score.db"; 

    public static Map<String, Integer> loadPlayerData() {
        Map<String, Integer> playerData = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(DATABASE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String playerName = parts[0].trim();
                    int score = Integer.parseInt(parts[1].trim());
                    playerData.put(playerName, score);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading player data: " + e.getMessage());
        }
        return playerData;
    }

    public static void savePlayerData(Map<String, Integer> playerData) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATABASE_FILE))) {
            for (Map.Entry<String, Integer> entry : playerData.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error writing player data: " + e.getMessage());
        }
    }

    public static void updatePlayerScore(String playerName, int score) {
        Map<String, Integer> playerData = loadPlayerData();
        playerData.put(playerName, score);
        savePlayerData(playerData);
    }

    public static int getPlayerScore(String playerName) {
        Map<String, Integer> playerData = loadPlayerData();
        return playerData.getOrDefault(playerName, 0);     }
}

