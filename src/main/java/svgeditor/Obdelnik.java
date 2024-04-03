package svgeditor;

import java.awt.*;

public class Obdelnik extends Shape {
    private int width;
    private int height;

    public Obdelnik(int x, int y, String barva, int width, int height) {
        super(x, y, barva);
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(Graphics g) {
        g.drawRect(x, y, width, height);
    }
}
