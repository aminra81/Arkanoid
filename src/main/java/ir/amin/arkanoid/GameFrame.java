package ir.amin.arkanoid;

import ir.amin.arkanoid.Panels.MainPanel;

import java.awt.*;
import javax.swing.*;

public class GameFrame extends JFrame {

    static private GameFrame gameFrame;

    public static GameFrame getInstance() {
        if(gameFrame == null)
            gameFrame = new GameFrame();
        return gameFrame;
    }

    public String getUsername() {
        String username;
        do {
            username = JOptionPane.showInputDialog(null, "Enter player name: ");
        } while (username == null || username.length() == 0);
        return username;
    }

    private GameFrame() {
        this.setContentPane(new MainPanel(getUsername()));
        this.setTitle("Arkanoid");
        this.setResizable(false);
        this.setBackground(Color.BLACK);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    @Override
    public void setContentPane(Container contentPane) {
        super.setContentPane(contentPane);
        contentPane.requestFocus();
        this.repaint();
        this.revalidate();
    }
}
