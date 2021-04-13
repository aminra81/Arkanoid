package ir.amin.arkanoid;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;

public class ScoreBoard {
    private final static File scoreFolder = new File("./src/main/resources/scoreBoard");

    static private ScoreBoard scoreBoard;

    public static ScoreBoard getInstance() {
        if (scoreBoard == null)
            scoreBoard = new ScoreBoard();
        return scoreBoard;
    }

    public void add(String username, int score) {
        File userFile = new File(scoreFolder, username);
        if (!userFile.exists())
            try {
                userFile.createNewFile();
                PrintStream printStream = new PrintStream(userFile);
                printStream.print(score);
                printStream.flush();
                printStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        else {
            try {
                Scanner scanner = new Scanner(userFile);
                score = Math.max(score, scanner.nextInt());
                scanner.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                PrintStream printStream = new PrintStream(userFile);
                printStream.print(score);
                printStream.flush();
                printStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public int getUserScore(String username) {
        for (File userFile : scoreFolder.listFiles())
            if(userFile.getName().equals(username))
            try {
                Scanner scanner = new Scanner(userFile);
                return scanner.nextInt();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        return 0;
    }

    public List<ScoreState> getBestScores() {
        List<ScoreState> ls = new ArrayList<>();
        for (File userFile : scoreFolder.listFiles()) {
            try {
                Scanner scanner = new Scanner(userFile);
                int curScore = scanner.nextInt();
                ls.add(new ScoreState(userFile.getName(), curScore));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        Comparator<ScoreState> byValue = Comparator.comparing(ScoreState::getScore);
        ls.sort(byValue);
        return ls;
    }
}
