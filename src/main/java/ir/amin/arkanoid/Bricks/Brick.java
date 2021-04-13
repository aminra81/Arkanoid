package ir.amin.arkanoid.Bricks;

import ir.amin.arkanoid.Ball;
import ir.amin.arkanoid.Commons;

import java.awt.*;
import java.util.Random;

public class Brick extends Rectangle {

    int alive;

    public Brick(int x, int y) {
        super(x, y, Commons.BRICK_WIDTH, Commons.BRICK_HEIGHT);
        alive = 1;
    }

    public static Brick brickGen(int x, int y) {
        Random random = new Random();
        int type = random.nextInt(5);
        switch (type) {
            case 0:
                return new BlinkerBrick(x, y);
            case 1:
                return new GlassBrick(x, y);
            case 2:
                return new InvisibleBrick(x, y);
            case 3:
                return new PrizeBrick(x, y);
            case 4:
                return new WoodenBrick(x, y);
        }
        return null;
    }

    public boolean checkCollisionWithBall(Ball ball) {
        if(this.alive == 0)
            return false;
        if(!ball.intersects(this))
            return false;

        if(ball.isFireball()) {
            this.alive = 0;
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
         return false;
    }

    public void move() {
        this.y += Commons.BRICK_GAP_Y;
    }

    public int getAlive() {
        return alive;
    }

    public boolean exists() { return alive > 0; }

    public void draw(Graphics g) {

    }
}
