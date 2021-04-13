package ir.amin.arkanoid;
import java.awt.*;
import javax.swing.*;

public class Ball extends Rectangle{
    public double xVelocity;
    public double yVelocity;
    private boolean isFireball;

    private final static ImageIcon ballImg = new ImageIcon("./src/main/resources/photos/ball.png");
    private final static ImageIcon fireballImg = new ImageIcon("./src/main/resources/photos/fireball.png");


    public Ball(int x, int y) {
        super(x, y, Commons.BALL_WIDTH, Commons.BALL_HEIGHT);
        setYDirection(Commons.BALL_INITIAL_SPEED_Y);
        setXDirection(Commons.BALL_INITIAL_SPEED_X);
        isFireball = false;
    }

    public void setFireball(boolean fireball) {
        this.isFireball = fireball;
    }

    public boolean isFireball() {
        return isFireball;
    }

    public void checkCollisionWithPaddle(Paddle paddle) {
        if (!this.intersects(paddle))
            return;
        double xCenter = paddle.getCenterX();
        this.setYDirection(-Math.abs(yVelocity));
        if(this.getCenterX() > paddle.getCenterX()) {
            if (xVelocity > 0)
                this.setXDirection(0.005 * Math.abs(xCenter - this.getX()) + xVelocity);
            else
                this.setXDirection(-(-0.005 * Math.abs(xCenter - this.getX()) + xVelocity));
        }
        else {
            if (xVelocity > 0)
                this.setXDirection(-(0.005 * Math.abs(xCenter - this.getX()) + xVelocity));
            else
                this.setXDirection(-0.005 * Math.abs(xCenter - this.getX()) + xVelocity);
        }
    }

    public boolean checkCollisionWithPanel() {
        if (getMinX() <= 0)
            setXDirection(Math.abs(xVelocity));
        if (getMaxX() >= Commons.GAME_WIDTH)
            setXDirection(-Math.abs(xVelocity));
        if (getMaxY() >= Commons.GAME_HEIGHT)
            return false;
        if (getMinY() <= 0)
            setYDirection(Math.abs(yVelocity));
        return true;
    }

    public void setXDirection(double xVelocity) {
        this.xVelocity = xVelocity;
    }

    public void setYDirection(double yVelocity) {
        this.yVelocity = yVelocity;
    }

    public void move() {
        x += xVelocity;
        y += yVelocity;
    }

    public void draw(Graphics g) {
        if(!isFireball)
            g.drawImage(ballImg.getImage(), x, y, null);
        else
            g.drawImage(fireballImg.getImage(), x, y, null);
    }

    public Ball(int x, int y, int width, int height, double xVelocity, double yVelocity, boolean isFireball) {
        super(x, y, width, height);
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;
        this.isFireball = isFireball;
    }

    @Override
    public String toString() {
        return x + " " + y + " " + width + " " + height + " " + xVelocity + " " + yVelocity + " " + isFireball;
    }
}
