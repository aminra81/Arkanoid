package ir.amin.arkanoid.Bricks;

import ir.amin.arkanoid.Ball;
import ir.amin.arkanoid.Commons;

import javax.swing.*;
import java.awt.*;

public class PrizeBrick extends Brick{

    private final static ImageIcon prizeBrickImg = new ImageIcon("./src/main/resources/photos/prizeBrick.png");

    public PrizeBrick(int x, int y) {
        super(x, y);
    }

    @Override
    public void draw(Graphics g) {
        if(this.alive != 0)
            g.drawImage(prizeBrickImg.getImage(), x, y, null);
    }

    @Override
    public boolean checkCollisionWithBall(Ball ball) {
        if(this.alive == 0)
            return false;
        if(!ball.intersects(this))
            return false;

        if(ball.isFireball()) {
            this.alive = 0;
            return true;
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
        return true;
    }

    public PrizeBrick(int x, int y, int alive) {
        super(x, y);
        this.alive = alive;
    }

    @Override
    public String toString() {
        return 4 + " " + x + " " + y + " " + alive;
    }
}
