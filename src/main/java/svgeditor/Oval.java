package svgeditor;

import java.awt.*;

public class Oval extends Shape{
    private int width;
    private int height;

    public Oval(int x, int y, String color, String name, int width, int height) {
        super(x, y, color, name);
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(Graphics g) {
        g.drawOval(x, y, width, height);
    }
}
