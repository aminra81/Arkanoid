package ir.amin.arkanoid.Bricks;

import javax.swing.*;
import java.awt.*;

public class InvisibleBrick extends Brick{
    private final static ImageIcon invisibleBrickImg = new ImageIcon("./src/main/resources/photos/invisibleBrick.png");

    public InvisibleBrick(int x, int y) {
        super(x, y);
    }

    @Override
    public void draw(Graphics g) {
        if(this.alive != 0)
            g.drawImage(invisibleBrickImg.getImage(), x, y, null);
    }

    public InvisibleBrick(int x, int y, int alive) {
        super(x, y);
        this.alive = alive;
    }

    @Override
    public String toString() {
        return 3 + " " + x + " " + y + " " + alive;
    }
}