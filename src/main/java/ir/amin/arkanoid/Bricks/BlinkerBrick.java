package ir.amin.arkanoid.Bricks;

import ir.amin.arkanoid.Ball;
import ir.amin.arkanoid.Commons;

import javax.swing.*;
import java.awt.*;

public class BlinkerBrick extends Brick {


    private final static ImageIcon blinkerBrickImg = new ImageIcon("./src/main/resources/photos/blinkerBrick.png");

    int realLive = 1;
    long lastChange;

    public BlinkerBrick(int x, int y) {
        super(x, y);
        lastChange = System.currentTimeMillis();
    }

    @Override
    public boolean checkCollisionWithBall(Ball ball) {
        if(this.alive == 0)
            return false;
        if(!ball.intersects(this))
            return false;

        if(ball.isFireball()) {
            this.alive = 0;
            this.realLive = 0;
            return false;
        }

        int ballLeft = (int)ball.getMinX();
        int ballTop = (int)ball.getMinY();
        int ballHeight = Commons.BALL_HEIGHT;
        int ballWidth = Commons.BALL_WIDTH;

        var pointRight = new Point(ballLeft + ballWidth + 1, ballTop);
        var pointLeft = new Point(ballLeft - 1, ballTop);
        var pointTop = new Point(ballLeft, ballTop - 1);
        var pointBottom = new Point(ballLeft, ballTop + ballHeight + 1);

        if(this.contains(pointRight) || this.contains(pointLeft))
            ball.setXDirection(-ball.xVelocity);
        if(this.contains(pointTop) || this.contains(pointBottom))
            ball.setYDirection(-ball.yVelocity);

        this.alive--;
        this.realLive = 0;
        return false;
    }

    @Override
    public boolean exists() { return realLive > 0; }

    @Override
    public void draw(Graphics g) {
        long now = System.currentTimeMillis();
        if(now - lastChange > 1000 && this.realLive > 0) {
            this.alive = 1 - this.alive;
            lastChange = now;
        }
        if(this.alive != 0)
            g.drawImage(blinkerBrickImg.getImage(), x, y, null);
    }

    public BlinkerBrick(int x, int y, int alive, int realLive, long lastChange) {
        super(x, y);
        this.alive = alive;
        this.realLive = realLive;
        this.lastChange = lastChange;
    }

    @Override
    public String toString() {
        return 1 + " " + x + " " + y + " " + alive + " " + realLive + " " + lastChange;
    }
}
