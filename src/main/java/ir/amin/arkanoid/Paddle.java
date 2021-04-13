package ir.amin.arkanoid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Paddle extends Rectangle{

    int xVelocity;
    int speed, size;
    boolean isConfused;

    private final static ImageIcon paddleImg = new ImageIcon("./src/main/resources/photos/paddle.png");
    private final static ImageIcon shortPaddleImg = new ImageIcon("./src/main/resources/photos/short_paddle.png");
    private final static ImageIcon longPaddleImg = new ImageIcon("./src/main/resources/photos/long_paddle.png");


    public Paddle(int x, int y) {
        super(x, y, Commons.PADDLE_WIDTH, Commons.PADDLE_HEIGHT);
        this.speed = Commons.PADDLE_INITIAL_SPEED;
        this.xVelocity = 0;
        this.size = 1;
        this.isConfused = false;
    }

    public void makeShort() {
        this.size = 0;
        this.width = Commons.SHORT_PADDLE_WIDTH;
        this.height = Commons.SHORT_PADDLE_HEIGHT;
    }

    public void makeNormal() {
        this.size = 1;
        this.width = Commons.PADDLE_WIDTH;
        this.height = Commons.PADDLE_HEIGHT;
    }

    public void makeLong() {
        this.size = 2;
        this.width = Commons.LONG_PADDLE_WIDTH;
        this.height = Commons.LONG_PADDLE_HEIGHT;
    }

    public void setXDirection(int xDirection) {
        xVelocity = xDirection;
    }

    public void setConfused(boolean confused) { this.isConfused = confused; }

    public void move() {
        x = x + xVelocity;
        if (x <= 0)
            x = 0;
        if (x >= (Commons.GAME_WIDTH - (int)getWidth()))
            x = Commons.GAME_WIDTH - (int)getWidth();
    }


    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if(!isConfused)
                setXDirection(-speed);
            else
                setXDirection(speed);
            move();
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if(!isConfused)
                setXDirection(speed);
            else
                setXDirection(-speed);
            move();
        }
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) {
            setXDirection(0);
            move();
        }
    }

    public void draw(Graphics g) {
        if(size == 0)
            g.drawImage(shortPaddleImg.getImage(), x, y, null);
        else if(size == 1)
            g.drawImage(paddleImg.getImage(), x, y, null);
        else
            g.drawImage(longPaddleImg.getImage(), x, y, null);
    }

    public Paddle(int x, int y, int width, int height, int xVelocity, int speed, int size, boolean isConfused) {
        super(x, y, width, height);
        this.xVelocity = xVelocity;
        this.speed = speed;
        this.size = size;
        this.isConfused = isConfused;
    }

    @Override
    public String toString() {
        return x + " " + y + " " + width + " " + height + " " + xVelocity + " " + speed + " " + size + " " + isConfused;
    }
}
