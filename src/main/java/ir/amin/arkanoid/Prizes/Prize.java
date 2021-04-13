package ir.amin.arkanoid.Prizes;

import ir.amin.arkanoid.Commons;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Prize extends Rectangle {


    PrizeType prizeType;

    boolean isActive, isApplied;
    long activationTime;


    private final static ImageIcon fireBall = new ImageIcon("./src/main/resources/photos/fireBall.png");
    private final static ImageIcon multipleBall = new ImageIcon("./src/main/resources/photos/multipleBalls.png");
    private final static ImageIcon longPaddle = new ImageIcon("./src/main/resources/photos/longPaddle.png");
    private final static ImageIcon shortPaddle = new ImageIcon("./src/main/resources/photos/shortPaddle.png");
    private final static ImageIcon fastBall = new ImageIcon("./src/main/resources/photos/fastBall.png");
    private final static ImageIcon slowBall = new ImageIcon("./src/main/resources/photos/slowBall.png");
    private final static ImageIcon confusedPaddle = new ImageIcon("./src/main/resources/photos/confusedPaddle.png");
    private final static ImageIcon random = new ImageIcon("./src/main/resources/photos/random.png");

    public static PrizeType[] allTypes = {PrizeType.FIREBALL, PrizeType.MULTIPLE_BALL, PrizeType.LONG_PADDLE,
    PrizeType.SHORT_PADDLE, PrizeType.FAST_BALL, PrizeType.SLOW_BALL, PrizeType.CONFUSED_PADDLE, PrizeType.RANDOM_PRIZE};

    public Prize(int x, int y) {
        super(x, y, Commons.PRIZE_WIDTH, Commons.PRIZE_HEIGHT);
        isActive = true;
        isApplied = false;
        activationTime = 0;
        int type = new Random().nextInt(8);
        prizeType = allTypes[type];
    }

    public boolean isActive() { return isActive; }

    public boolean notApplied() { return !isApplied; }

    public void setApplied(boolean applied) { this.isApplied = applied; }

    public long getActivationTime() { return activationTime; }

    public void setActivationTime(long time) { this.activationTime = time; }

    public void setActive(boolean active) { this.isActive = active; }

    public PrizeType getPrizeType() { return prizeType; }

    public void setPrizeType(PrizeType prizeType) { this.prizeType = prizeType; }

    public void draw(Graphics g) {
        if(!this.isActive)
            return;
        switch (prizeType) {
            case FIREBALL:
                g.drawImage(fireBall.getImage(), x, y, null);
                break;
            case MULTIPLE_BALL:
                g.drawImage(multipleBall.getImage(), x, y, null);
                break;
            case LONG_PADDLE:
                g.drawImage(longPaddle.getImage(), x, y, null);
                break;
            case SHORT_PADDLE:
                g.drawImage(shortPaddle.getImage(), x, y, null);
                break;
            case FAST_BALL:
                g.drawImage(fastBall.getImage(), x, y, null);
                break;
            case SLOW_BALL:
                g.drawImage(slowBall.getImage(), x, y, null);
                break;
            case CONFUSED_PADDLE:
                g.drawImage(confusedPaddle.getImage(), x, y, null);
                break;
            case RANDOM_PRIZE:
                g.drawImage(random.getImage(), x, y, null);
                break;
        }
    }

    public void move() {
        this.y += 1;
    }

    public Prize(int x, int y, int width, int height, PrizeType prizeType,
                 boolean isActive, boolean isApplied, long activationTime) {
        super(x, y, width, height);
        this.prizeType = prizeType;
        this.isActive = isActive;
        this.isApplied = isApplied;
        this.activationTime = activationTime;
    }

    @Override
    public String toString() {
        return x + " " + y + " " + width + " " + height + " " + prizeType + " " +
                isActive + " " + isApplied + " " + activationTime;
    }
}
