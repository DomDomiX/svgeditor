package svgeditor;

import java.awt.*;

public class Line extends Shape{
    private int width;
    private int height;

    public Line(int x, int y, String color, String name, int height, int width) {
        super(x, y, color, name);
        this.height = height;
        this.width = width;
    }

    @Override
    public void draw(Graphics g) {
        g.drawLine(x, y, width, height);
    }
}
