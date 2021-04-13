package ir.amin.arkanoid.GameState;

import ir.amin.arkanoid.Bricks.Brick;
import ir.amin.arkanoid.Commons;

public class AddBricks implements Runnable {
    private GameState gameState;
    private long lastAdd;

    public AddBricks(long lastAdd) {
        this.gameState = null;
        this.lastAdd = lastAdd;
    }

    public AddBricks(GameState gameState) {
        this.gameState = gameState;
        gameState.setAddBricks(this);
        lastAdd = 0;
    }

    public void setLastAdd(long lastAdd) { this.lastAdd = lastAdd; }

    public void setGameState(GameState gameState) { this.gameState = gameState; }

    public long getLastAdd() { return lastAdd; }

    @Override
    public void run() {
        while (gameState.isRunning()) {
            if(gameState.isGamePaused())
                continue;
            long now = System.currentTimeMillis();
            if(now - lastAdd < 30000)
                continue;
            lastAdd = now;
            synchronized (gameState.getLock()) {
                for (Brick brick : gameState.getBricks())
                    brick.move();
                int curX = Commons.FIRST_BRICK_X;
                int curY = Commons.FIRST_BRICK_Y;
                for (int j = 0; j < Commons.NUM_OF_BRICK_PER_ROW; j++) {
                    gameState.addToBricks(Brick.brickGen(curX, curY));
                    curX += Commons.BRICK_GAP_X;
                }
            }
        }
    }
}
