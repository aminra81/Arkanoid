package ir.amin.arkanoid.Panels;

import ir.amin.arkanoid.GameFrame;
import ir.amin.arkanoid.GameState.AddBricks;
import ir.amin.arkanoid.Ball;
import ir.amin.arkanoid.Bricks.*;
import ir.amin.arkanoid.Commons;
import ir.amin.arkanoid.GameState.GameState;
import ir.amin.arkanoid.ModelLoader;
import ir.amin.arkanoid.Prizes.Prize;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable {

    GameState gameState;
    AddBricks addBricks;
    Thread gameThread, addBricksThread;
    Image image;

    JButton continueGame = new JButton();
    JButton mainMenu = new JButton();
    JButton saveGame = new JButton();

    String username;

    ActionListener continueGameListener = actionEvent -> {
        gameState.continueGame();

        mainMenu.setVisible(false);

        continueGame.setVisible(false);

        saveGame.setVisible(false);
    };

    private void goToMainMenu() {
        gameState.setRunning(false);
        GameFrame.getInstance().setContentPane(new MainPanel(username));
    }

    ActionListener mainMenuListener = actionEvent -> goToMainMenu();

    ActionListener saveGameListener = actionEvent -> ModelLoader.getInstance().saveGameState(this.gameState);

    public GamePanel(GameState gameState) {
        this.username = gameState.getUsername();

        this.setBackground(Color.BLACK);
        this.gameState = gameState;
        this.addBricks = gameState.getAddBricks();

        this.setLayout(null);
        this.setFocusable(true);

        this.addKeyListener(new AL());
        this.setPreferredSize(Commons.SCREEN_SIZE);

        continueGame.setBackground(Color.WHITE);
        continueGame.setBounds(275, 300, 350, 100);
        continueGame.addActionListener(continueGameListener);
        continueGame.setFont(new Font("Minecraft Evenings", Font.BOLD, 50));
        continueGame.setText("Continue");
        continueGame.setVisible(false);

        saveGame.setBackground(Color.WHITE);
        saveGame.setBounds(275, 450, 350, 100);
        saveGame.addActionListener(saveGameListener);
        saveGame.setFont(new Font("Minecraft Evenings", Font.BOLD, 50));
        saveGame.setText("Save");
        saveGame.setVisible(false);

        mainMenu.setBackground(Color.WHITE);
        mainMenu.setBounds(275, 600, 350, 100);
        mainMenu.addActionListener(mainMenuListener);
        mainMenu.setFont(new Font("Minecraft Evenings", Font.BOLD, 50));
        mainMenu.setText("Main Menu");
        mainMenu.setVisible(false);


        this.add(mainMenu);
        this.add(continueGame);
        this.add(saveGame);


        gameThread = new Thread(this);
        gameThread.start();

        addBricksThread = new Thread(addBricks);
        addBricksThread.start();
    }

    public GamePanel(String username) {

        this.username = username;

        this.setBackground(Color.BLACK);
        gameState = new GameState(username);
        addBricks = new AddBricks(this.gameState);

        this.setLayout(null);
        this.setFocusable(true);

        this.addKeyListener(new AL());
        this.setPreferredSize(Commons.SCREEN_SIZE);

        continueGame.setBackground(Color.WHITE);
        continueGame.setBounds(275, 300, 350, 100);
        continueGame.addActionListener(continueGameListener);
        continueGame.setFont(new Font("Minecraft Evenings", Font.BOLD, 50));
        continueGame.setText("Continue");
        continueGame.setVisible(false);

        saveGame.setBackground(Color.WHITE);
        saveGame.setBounds(275, 450, 350, 100);
        saveGame.addActionListener(saveGameListener);
        saveGame.setFont(new Font("Minecraft Evenings", Font.BOLD, 50));
        saveGame.setText("Save");
        saveGame.setVisible(false);

        mainMenu.setBackground(Color.WHITE);
        mainMenu.setBounds(275, 600, 350, 100);
        mainMenu.addActionListener(mainMenuListener);
        mainMenu.setFont(new Font("Minecraft Evenings", Font.BOLD, 50));
        mainMenu.setText("Main Menu");
        mainMenu.setVisible(false);


        this.add(mainMenu);
        this.add(continueGame);
        this.add(saveGame);

        gameThread = new Thread(this);
        gameThread.start();

        addBricksThread = new Thread(addBricks);
        addBricksThread.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (gameState.isGamePaused()) {
            return;
        }
        image = createImage(getWidth(), getHeight());
        synchronized (gameState.getLock()) {
            draw(image.getGraphics());
        }
        g.drawImage(image, 0, 0, this);
    }

    public void draw(Graphics g) {
        //drawing game over text.
        if (gameState.isGameOver()) {
            g.setFont(new Font("Minecraft Evenings", Font.BOLD, 50));
            g.setColor(Color.RED);
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Game over", (Commons.GAME_WIDTH - metrics.stringWidth("Game over")) / 2,
                    g.getFont().getSize() + 450);
            return;
        }

        //drawing details text.
        g.setFont(new Font("Minecraft Evenings", Font.PLAIN, 30));
        g.setColor(Color.white);
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString(gameState.getDetails(), (Commons.GAME_WIDTH - metrics.stringWidth(gameState.getDetails())) / 2,
                g.getFont().getSize() + 450);

        gameState.getPaddle().draw(g);
        for (Ball ball : gameState.getBalls())
            ball.draw(g);
        for (Brick brick : gameState.getBricks())
            brick.draw(g);
        for (Prize prize : gameState.getPrizes())
            prize.draw(g);
    }

    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 90.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        while (gameState.isRunning()) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if (delta >= 1) {
                gameState.doGameCycle();
                repaint();
                revalidate();
                if (gameState.isGameOver()) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    goToMainMenu();
                }
                delta--;
            }
        }
    }

    public class AL extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                mainMenu.setVisible(true);

                continueGame.setVisible(true);

                saveGame.setVisible(true);

                gameState.pauseGame();
            }
            gameState.getPaddle().keyPressed(e);
        }

        public void keyReleased(KeyEvent e) {
            gameState.getPaddle().keyReleased(e);
        }
    }
}
