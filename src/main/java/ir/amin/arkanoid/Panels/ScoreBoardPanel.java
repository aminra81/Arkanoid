package ir.amin.arkanoid.Panels;

import ir.amin.arkanoid.Commons;
import ir.amin.arkanoid.GameFrame;
import ir.amin.arkanoid.ScoreBoard;
import ir.amin.arkanoid.ScoreState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ScoreBoardPanel extends JPanel {
    JButton backButton = new JButton();
    List<JLabel> bestScores = new ArrayList<>();
    JLabel yourScore = new JLabel();
    String username;

    ActionListener backListener = actionEvent -> {
        if(actionEvent.getSource() == backButton) {
            GameFrame.getInstance().setContentPane(new MainPanel(username));
        }
    };

    public ScoreBoardPanel(String username) {
        this.username = username;

        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.setPreferredSize(Commons.SCREEN_SIZE);
        this.setLayout(null);

        yourScore.setBackground(Color.BLACK);
        yourScore.setBounds(275, 50, 500, 100);
        yourScore.setFont(new Font("Minecraft Evenings", Font.BOLD, 50));
        yourScore.setForeground(Color.BLUE);
        yourScore.setText("your score: " + ScoreBoard.getInstance().getUserScore(username));

        int curY = 150;
        List<ScoreState> scoreStateList = ScoreBoard.getInstance().getBestScores();
        int counter = 1;
        for (int id = scoreStateList.size()  - 1; id >= Math.max(0, scoreStateList.size() - 5); id--) {
            String user = scoreStateList.get(id).getUsername();
            JLabel curLabel = new JLabel();
            curLabel.setBackground(Color.BLACK);
            curLabel.setBounds(350, curY, 500, 50);
            curLabel.setFont(new Font("Minecraft Evenings", Font.PLAIN, 50));
            curLabel.setForeground(Color.RED);
            curLabel.setText(counter + ". "+ user + ": " + ScoreBoard.getInstance().getUserScore(user));
            bestScores.add(curLabel);
            counter++;
            curY += 70;
        }

        backButton.setBackground(Color.WHITE);
        backButton.setBounds(300, 600, 300, 100);
        backButton.addActionListener(backListener);
        backButton.setFont(new Font("Minecraft Evenings", Font.BOLD, 50));
        backButton.setText("Back");

        for (JLabel curLabel : bestScores)
            this.add(curLabel);
        this.add(yourScore);
        this.add(backButton);
    }
}
