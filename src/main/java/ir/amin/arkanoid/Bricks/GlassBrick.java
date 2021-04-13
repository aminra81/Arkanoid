package ir.amin.arkanoid.Bricks;

import javax.swing.*;
import java.awt.*;

public class GlassBrick extends Brick{

    private final static ImageIcon glassBrickImg = new ImageIcon("./src/main/resources/photos/glassBrick.png");

    public GlassBrick(int x, int y) {
        super(x, y);
    }

    @Override
    public void draw(Graphics g) {
        if(this.alive != 0)
            g.drawImage(glassBrickImg.getImage(), x, y, null);
    }

    public GlassBrick(int x, int y, int alive) {
        super(x, y);
        this.alive = alive;
    }

    @Override
    public String toString() {
        return 2 + " " + x + " " + y + " " + alive;
    }
}
