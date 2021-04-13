package ir.amin.arkanoid;

import java.awt.*;
import java.io.File;

public class Arkanoid {

    public static void main(String[] args) {
        loadFonts();
        GameFrame.getInstance();
    }

    public static void loadFonts() {
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT,
                    new File("./src/main/resources/fonts/MinecraftEvenings-RBao.ttf")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
