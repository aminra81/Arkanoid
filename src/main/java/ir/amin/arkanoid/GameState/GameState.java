package ir.amin.arkanoid.GameState;

import ir.amin.arkanoid.*;
import ir.amin.arkanoid.Bricks.Brick;
import ir.amin.arkanoid.Prizes.Prize;
import ir.amin.arkanoid.Prizes.PrizeType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameState {
    private Paddle paddle;
    private List<Ball> balls = new ArrayList<>();
    private List<Brick> bricks = new ArrayList<>();
    private List<Prize> prizes = new ArrayList<>();
    private boolean isGameOver;
    private boolean isGamePaused;
    private boolean isRunning;
    private long pausedTime;
    private int userHealth;
    final private Object lock;
    private AddBricks addBricks;
    String username, gameName;

    public GameState(String username, Paddle paddle, List<Ball> balls, List<Brick> bricks, List<Prize> prizes,
                     long pausedTime, int userHealth, AddBricks addBricks) {
        this.isRunning = true;
        this.isGamePaused = false;
        this.isGameOver = false;
        this.username = username;
        this.paddle = paddle;
        this.balls = balls;
        this.bricks = bricks;
        this.prizes = prizes;
        this.pausedTime = pausedTime;
        this.userHealth = userHealth;
        this.addBricks = addBricks;
        this.addBricks.setGameState(this);
        this.continueGame();
        lock = new Object();
    }
    public GameState(String username) {
        this.gameName = "";
        this.username = username;
        this.userHealth = 3;
        this.isRunning = true;
        this.isGamePaused = false;
        this.isGameOver = false;
        newPaddle();
        newBall();
        newBricks();
        lock = new Object();
    }

    public long getPausedTime() { return pausedTime; }

    public AddBricks getAddBricks() { return addBricks; }

    public String getUsername() { return username; }

    public String getGameName() { return gameName; }

    public void setGameName(String gameName) { this.gameName = gameName; }

    public void setRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    public boolean isRunning() { return isRunning; }

    public void setAddBricks(AddBricks addBricks) { this.addBricks = addBricks; }

    public void newBall() {
        balls.add(new Ball(Commons.BALL_INITIAL_X, Commons.BALL_INITIAL_Y));
    }

    public void newPaddle() {
        paddle = new Paddle(Commons.PADDLE_INITIAL_X, Commons.PADDLE_INITIAL_Y);
    }

    public void newBricks() {
        int curX = Commons.FIRST_BRICK_X;
        int curY = Commons.FIRST_BRICK_Y;
        for (int i = 0; i < Commons.NUM_OF_BRICK_ROWS; i++) {
            for (int j = 0; j < Commons.NUM_OF_BRICK_PER_ROW; j++) {
                bricks.add(Brick.brickGen(curX, curY));
                curX += Commons.BRICK_GAP_X;
            }
            curY += Commons.BRICK_GAP_Y;
            curX = Commons.FIRST_BRICK_X;
        }
    }

    public void checkCollision() {
        //balls with panel
        List<Ball> toBeRemovedBalls = new ArrayList<>();
        for (Ball ball : balls) {
            if (!ball.checkCollisionWithPanel())
                toBeRemovedBalls.add(ball);
        }
        for (Ball ball : toBeRemovedBalls)
            balls.remove(ball);
        if (balls.size() == 0) {
            userHealth--;
            if (userHealth == 0)
                gameOver();
            else
                balls.add(new Ball((int) paddle.getX(), (int) paddle.getMinY() - Commons.PADDLE_HEIGHT));
        }
        //ball with paddle
        for (Ball ball : balls)
            ball.checkCollisionWithPaddle(paddle);
        //ball with bricks
        for (Brick brick : bricks)
            for (Ball ball : balls)
                if (brick.checkCollisionWithBall(ball))
                    prizes.add(new Prize((int) brick.getX(), (int) brick.getY()));
        //bricks with panel and paddle
        for (Brick brick : bricks)
            if (brick.getAlive() > 0 && (brick.intersects(paddle) || brick.getMaxY() >= Commons.GAME_HEIGHT))
                gameOver();
        //prize with paddle
        for (Prize prize : prizes)
            if (prize.intersects(paddle) && prize.isActive()) {
                if (prize.getPrizeType() == PrizeType.RANDOM_PRIZE) {
                    int type = new Random().nextInt(7);
                    prize.setPrizeType(Prize.allTypes[type]);
                }
                prize.setActive(false);
                deactivateOtherPrizes(prize);
                activatePrize(prize);
            }
    }

    public Object getLock() {
        return lock;
    }

    public List<Brick> getBricks() {
        return bricks;
    }

    public void addToBricks(Brick brick) {
        bricks.add(brick);
    }

    public Paddle getPaddle() {
        return paddle;
    }

    public List<Ball> getBalls() {
        return balls;
    }

    public List<Prize> getPrizes() {
        return prizes;
    }

    public int getScore() {
        int score = 0;
        for (Brick brick : bricks)
            if(!brick.exists())
                score++;
        return score;
    }

    public int getUserHealth() { return userHealth; }

    public void move() {
        for (Prize prize : prizes)
            prize.move();
        paddle.move();
        for (Ball ball : balls)
            ball.move();
    }

    public void deactivateOtherPrizes(Prize prize) {
        for (Prize curPrize : prizes) {
            if (curPrize == prize || curPrize.notApplied())
                continue;
            switch (prize.getPrizeType()) {
                case FIREBALL:
                    if(curPrize.getPrizeType() == PrizeType.FIREBALL)
                        deactivatePrize(curPrize);
                    break;

                case LONG_PADDLE:
                case SHORT_PADDLE:
                    if(curPrize.getPrizeType() == PrizeType.LONG_PADDLE || curPrize.getPrizeType() == PrizeType.SHORT_PADDLE)
                        deactivatePrize(curPrize);
                    break;

                case FAST_BALL:
                case SLOW_BALL:
                    if(curPrize.getPrizeType() == PrizeType.FAST_BALL || curPrize.getPrizeType() == PrizeType.SLOW_BALL)
                        deactivatePrize(curPrize);
                    break;

                case CONFUSED_PADDLE:
                    if(curPrize.getPrizeType() == PrizeType.CONFUSED_PADDLE)
                        deactivatePrize(curPrize);
                    break;
            }
        }
    }

    public void activatePrize(Prize prize) {
        prize.setApplied(true);
        prize.setActivationTime(System.currentTimeMillis());
        switch (prize.getPrizeType()) {
            case FIREBALL:
                for (Ball ball : balls)
                    ball.setFireball(true);
                break;

            case LONG_PADDLE:
                paddle.makeLong();
                break;

            case SHORT_PADDLE:
                paddle.makeShort();
                break;

            case SLOW_BALL:
                for (Ball ball : balls) {
                    ball.setXDirection(ball.xVelocity / 2);
                    ball.setYDirection(ball.yVelocity / 2);
                }
                break;

            case FAST_BALL:
                for (Ball ball : balls) {
                    ball.setXDirection(ball.xVelocity * 2);
                    ball.setYDirection(ball.yVelocity * 2);
                }
                break;

            case MULTIPLE_BALL:
                balls.add(new Ball((int) paddle.getX(), (int) paddle.getMinY() - Commons.PADDLE_HEIGHT));
                prize.setApplied(false);
                break;

            case CONFUSED_PADDLE:
                paddle.setConfused(true);
                break;
        }
    }

    public void deactivatePrize(Prize prize) {
        switch (prize.getPrizeType()) {
            case FIREBALL:
                for (Ball ball : balls)
                    ball.setFireball(false);
                break;

            case LONG_PADDLE:
            case SHORT_PADDLE:
                paddle.makeNormal();
                break;

            case SLOW_BALL:
                for (Ball ball : balls) {
                    ball.setXDirection(ball.xVelocity * 2);
                    ball.setYDirection(ball.yVelocity * 2);
                }
                break;


            case FAST_BALL:
                for (Ball ball : balls) {
                    ball.setXDirection(ball.xVelocity / 2);
                    ball.setYDirection(ball.yVelocity / 2);
                }
                break;

            case CONFUSED_PADDLE:
                paddle.setConfused(false);
                break;
        }
        prize.setApplied(false);
    }

    public void prizeBackChecker() {
        for (Prize prize : prizes) {
            if (prize.notApplied())
                continue;
            long now = System.currentTimeMillis();
            if (now - prize.getActivationTime() < 5000)
                continue;
            deactivatePrize(prize);
        }
    }

    public void doGameCycle() {
        synchronized (lock) {
            if(isGameOver || isGamePaused)
                return;
            prizeBackChecker();
            move();
            checkCollision();
        }
    }

    public void gameOver() {
        this.isGameOver = true;
        if(!this.gameName.equals(""))
            ModelLoader.getInstance().removeGame(gameName);
        ScoreBoard.getInstance().add(username, getScore());
    }

    public boolean isGameOver() {
        return this.isGameOver;
    }

    public boolean isGamePaused() { return this.isGamePaused; }

    public String getDetails() {
        return "score: " + getScore() + "      Health: " + getUserHealth();
    }

    public void pauseGame() {
        isGamePaused = true;
        pausedTime = System.currentTimeMillis();
    }

    public void continueGame() {
        long now = System.currentTimeMillis();
        for (Prize prize : prizes)
            prize.setActivationTime(prize.getActivationTime() - pausedTime + now);
        addBricks.setLastAdd(addBricks.getLastAdd() - pausedTime + now);
        isGamePaused = false;
        pausedTime = 0;
    }
}
