package ir.amin.arkanoid;

import ir.amin.arkanoid.Bricks.*;
import ir.amin.arkanoid.GameState.AddBricks;
import ir.amin.arkanoid.GameState.GameState;
import ir.amin.arkanoid.Panels.MainPanel;
import ir.amin.arkanoid.Prizes.Prize;
import ir.amin.arkanoid.Prizes.PrizeType;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ModelLoader {
    private final static File gameStatesFolder = new File("./src/main/resources/gameStates");

    static private ModelLoader modelLoader;

    public static ModelLoader getInstance() {
        if (modelLoader == null)
            modelLoader = new ModelLoader();
        return modelLoader;
    }

    private String getNewName() {
        String newName;
        do {
            newName = JOptionPane.showInputDialog(null, "Enter the game name: ");
        } while (newName == null || newName.length() == 0 || new File(gameStatesFolder, newName).exists());
        return newName;
    }

    private void saveNewGame(GameState gameState, String details) {
        gameState.setGameName(getNewName());
        File curFile = new File(gameStatesFolder, gameState.getGameName());
        try {
            curFile.createNewFile();
            PrintStream printStream = new PrintStream(curFile);
            printStream.print(details);
            printStream.flush();
            printStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save(GameState gameState, String details) {
        if (gameState.getGameName().equals("")) {
            saveNewGame(gameState, details);
        } else {

            String[] options = {"yes", "no"};
            int selectedOption;
            do {
                selectedOption = JOptionPane.showOptionDialog(null,
                        "Do you want to make a new game state?", "save", JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            } while (selectedOption != 0 && selectedOption != 1);


            if (selectedOption == 0)
                saveNewGame(gameState, details);
            else {
                File curFile = new File(gameStatesFolder, gameState.getGameName());
                try {
                    PrintStream printStream = new PrintStream(curFile);
                    printStream.print(details);
                    printStream.flush();
                    printStream.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void saveGameState(GameState gameState) {
        StringBuilder gameStateDetails = new StringBuilder();

        gameStateDetails.append(gameState.getUsername()).append("\n");

        gameStateDetails.append(gameState.getPaddle()).append("\n");

        gameStateDetails.append(gameState.getBalls().size()).append("\n");
        for (Ball ball : gameState.getBalls())
            gameStateDetails.append(ball).append("\n");

        gameStateDetails.append(gameState.getBricks().size()).append("\n");
        for (Brick brick : gameState.getBricks())
            gameStateDetails.append(brick).append("\n");

        gameStateDetails.append(gameState.getPrizes().size()).append("\n");
        for (Prize prize : gameState.getPrizes())
            gameStateDetails.append(prize).append("\n");

        gameStateDetails.append(gameState.getPausedTime()).append("\n");

        gameStateDetails.append(gameState.getUserHealth()).append("\n");

        gameStateDetails.append(gameState.getAddBricks().getLastAdd());

        save(gameState, gameStateDetails.toString());

        gameState.setRunning(false);
        GameFrame.getInstance().setContentPane(new MainPanel(gameState.getUsername()));
    }

    public void removeGame(String gameName) {
        File curFile = new File(gameStatesFolder, gameName);
        curFile.delete();
    }

    private boolean isValid(String gameName, String username) {
        File curFile = new File(gameStatesFolder, gameName);
        if (!curFile.exists())
            return false;
        boolean isOk = false;
        try {
            Scanner scanner = new Scanner(curFile);
            isOk = scanner.next().equals(username);
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return isOk;
    }

    public String getGameToLoad(String username) {
        String gameName;
        do {
            gameName = JOptionPane.showInputDialog(null, "Enter the game name: ");
        } while (gameName == null || gameName.length() == 0 || !isValid(gameName, username));
        return gameName;
    }

    public GameState loadGameState(String gameName) {
        File curFile = new File(gameStatesFolder, gameName);
        try {
            Scanner scanner = new Scanner(curFile);

            String username = scanner.next();

            Paddle paddle = new Paddle(scanner.nextInt(), scanner.nextInt(), scanner.nextInt(), scanner.nextInt(),
                    scanner.nextInt(), scanner.nextInt(), scanner.nextInt(), scanner.nextBoolean());

            int nBalls = scanner.nextInt();
            List<Ball> balls = new ArrayList<>();
            for (int i = 0; i < nBalls; i++) {
                balls.add(new Ball(scanner.nextInt(), scanner.nextInt(), scanner.nextInt(), scanner.nextInt(),
                        scanner.nextDouble(), scanner.nextDouble(), scanner.nextBoolean()));
            }

            int nBricks = scanner.nextInt();
            List<Brick> bricks = new ArrayList<>();
            for (int i = 0; i < nBricks; i++) {
                int type = scanner.nextInt();
                switch (type) {
                    case 1:
                        bricks.add(new BlinkerBrick(scanner.nextInt(), scanner.nextInt(), scanner.nextInt(),
                                scanner.nextInt(), scanner.nextLong()));
                        break;
                    case 2:
                        bricks.add(new GlassBrick(scanner.nextInt(), scanner.nextInt(), scanner.nextInt()));
                        break;
                    case 3:
                        bricks.add(new InvisibleBrick(scanner.nextInt(), scanner.nextInt(), scanner.nextInt()));
                        break;
                    case 4:
                        bricks.add(new PrizeBrick(scanner.nextInt(), scanner.nextInt(), scanner.nextInt()));
                        break;
                    case 5:
                        bricks.add(new WoodenBrick(scanner.nextInt(), scanner.nextInt(), scanner.nextInt()));
                        break;
                }
            }

            int nPrizes = scanner.nextInt();
            List<Prize> prizes = new ArrayList<>();
            for (int i = 0; i < nPrizes; i++)
                prizes.add(new Prize(scanner.nextInt(), scanner.nextInt(), scanner.nextInt(), scanner.nextInt(),
                        PrizeType.valueOf(scanner.next()), scanner.nextBoolean(),
                        scanner.nextBoolean(), scanner.nextLong()));

            long pausedTime = scanner.nextLong();

            int userHealth = scanner.nextInt();

            long lastAdd = scanner.nextLong();
            AddBricks addBricks = new AddBricks(lastAdd);

            scanner.close();
            GameState gameState = new GameState(username, paddle, balls, bricks, prizes, pausedTime,
                    userHealth, addBricks);
            gameState.setGameName(gameName);
            return gameState;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
