package ir.amin.arkanoid;

import javax.swing.*;
import java.awt.*;

public interface Commons {
    int GAME_HEIGHT = 900;
    int GAME_WIDTH = 900;
    Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);

    int PADDLE_INITIAL_SPEED = 10;

    int PADDLE_WIDTH = new ImageIcon("./src/main/resources/photos/paddle.png").getIconWidth();
    int PADDLE_HEIGHT = new ImageIcon("./src/main/resources/photos/paddle.png").getIconHeight();
    int PADDLE_INITIAL_X = (GAME_WIDTH / 2) - (PADDLE_WIDTH / 2);
    int PADDLE_INITIAL_Y = GAME_HEIGHT - PADDLE_HEIGHT;

    int LONG_PADDLE_WIDTH = new ImageIcon("./src/main/resources/photos/long_paddle.png").getIconWidth();
    int LONG_PADDLE_HEIGHT = new ImageIcon("./src/main/resources/photos/long_paddle.png").getIconHeight();

    int SHORT_PADDLE_WIDTH = new ImageIcon("./src/main/resources/photos/short_paddle.png").getIconWidth();
    int SHORT_PADDLE_HEIGHT = new ImageIcon("./src/main/resources/photos/short_paddle.png").getIconHeight();

    int BALL_WIDTH = new ImageIcon("./src/main/resources/photos/ball.png").getIconWidth();
    int BALL_HEIGHT = new ImageIcon("./src/main/resources/photos/ball.png").getIconHeight();

    int PRIZE_WIDTH = new ImageIcon("./src/main/resources/photos/random.png").getIconWidth();
    int PRIZE_HEIGHT = new ImageIcon("./src/main/resources/photos/random.png").getIconHeight();

    int BALL_INITIAL_X = PADDLE_INITIAL_X;
    int BALL_INITIAL_Y = PADDLE_INITIAL_Y - Commons.PADDLE_HEIGHT;

    double BALL_INITIAL_SPEED_X = 2;
    double BALL_INITIAL_SPEED_Y = 3;

    int BRICK_WIDTH = 90;
    int BRICK_HEIGHT = 30;
    int FIRST_BRICK_X = 10;
    int FIRST_BRICK_Y = 50;
    int BRICK_GAP_X = 100;
    int BRICK_GAP_Y = 40;
    int NUM_OF_BRICK_PER_ROW = 9;
    int NUM_OF_BRICK_ROWS = 5;
}
