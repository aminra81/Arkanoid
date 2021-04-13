package ir.amin.arkanoid.Bricks;

import javax.swing.*;
import java.awt.*;

public class WoodenBrick extends Brick {

    private final static ImageIcon woodenBrickImg = new ImageIcon("./src/main/resources/photos/woodenBrick.png");
    private final static ImageIcon brokenWoodenBrickImg =
            new ImageIcon("./src/main/resources/photos/brokenWoodenBrick.png");

    public WoodenBrick(int x, int y) {
        super(x, y);
        this.alive = 2;
    }

    @Override
    public void draw(Graphics g) {
        if (this.alive == 2)
            g.drawImage(woodenBrickImg.getImage(), x, y, null);
        else if (this.alive == 1)
            g.drawImage(brokenWoodenBrickImg.getImage(), x, y, null);
    }

    public WoodenBrick(int x, int y, int alive) {
        super(x, y);
        this.alive = alive;
    }

    @Override
    public String toString() {
        return 5 + " " + x + " " + y + " " + alive;
    }
}
