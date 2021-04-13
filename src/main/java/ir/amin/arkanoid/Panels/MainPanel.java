package ir.amin.arkanoid.Panels;

import ir.amin.arkanoid.Commons;
import ir.amin.arkanoid.GameFrame;
import ir.amin.arkanoid.GameState.GameState;
import ir.amin.arkanoid.ModelLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainPanel extends JPanel{
    JButton startButton = new JButton();
    JButton exitButton = new JButton();
    JButton scoreBoardButton = new JButton();
    JButton loadButton = new JButton();
    JLabel gameName = new JLabel();
    String username;

    ActionListener startListener = actionEvent -> {
        if(actionEvent.getSource() == startButton)
            GameFrame.getInstance().setContentPane(new GamePanel(username));
    };

    ActionListener exitListener = actionEvent -> {
        if(actionEvent.getSource() == exitButton)
            System.exit(0);
    };

    ActionListener scoreBoardListener = actionEvent -> {
        if(actionEvent.getSource() == scoreBoardButton) {
            GameFrame.getInstance().setContentPane(new ScoreBoardPanel(username));
        }
    };

    ActionListener loadListener = actionEvent -> {
        if(actionEvent.getSource() == loadButton) {

            String gameName = ModelLoader.getInstance().getGameToLoad(username);
            GameState gameState = ModelLoader.getInstance().loadGameState(gameName);
            GamePanel gamePanel = new GamePanel(gameState);

            GameFrame.getInstance().setContentPane(gamePanel);
        }
    };

    public MainPanel(String username) {

        this.username = username;

        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.setPreferredSize(Commons.SCREEN_SIZE);
        this.setLayout(null);

        gameName.setBackground(Color.BLACK);
        gameName.setBounds(325, 100, 300, 100);
        gameName.setFont(new Font("Minecraft Evenings", Font.BOLD, 50));
        gameName.setForeground(Color.GREEN);
        gameName.setText("Arkanoid");

        startButton.setBackground(Color.WHITE);
        startButton.setBounds(300, 300, 300, 100);
        startButton.addActionListener(startListener);
        startButton.setFont(new Font("Minecraft Evenings", Font.BOLD, 50));
        startButton.setText("Start");

        loadButton.setBackground(Color.WHITE);
        loadButton.setBounds(300, 450, 300, 100);
        loadButton.addActionListener(loadListener);
        loadButton.setFont(new Font("Minecraft Evenings", Font.BOLD, 50));
        loadButton.setText("Load");

        scoreBoardButton.setBackground(Color.WHITE);
        scoreBoardButton.setBounds(300, 600, 300, 100);
        scoreBoardButton.addActionListener(scoreBoardListener);
        scoreBoardButton.setFont(new Font("Minecraft Evenings", Font.BOLD, 50));
        scoreBoardButton.setText("Scores");

        exitButton.setBackground(Color.WHITE);
        exitButton.setBounds(300, 750, 300, 100);
        exitButton.addActionListener(exitListener);
        exitButton.setFont(new Font("Minecraft Evenings", Font.BOLD, 50));
        exitButton.setText("Exit");

        this.add(gameName);
        this.add(startButton);
        this.add(loadButton);
        this.add(scoreBoardButton);
        this.add(exitButton);
    }
}

