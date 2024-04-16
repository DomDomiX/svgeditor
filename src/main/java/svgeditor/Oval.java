package svgeditor;

import java.awt.*;

public class Oval extends Shape{
    private int width;
    private int height;

    public Oval(int x, int y, String color, String name, int width, int height, int thickness) {
        super(x, y, color, name, thickness);
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(getThickness())); // Nastavení tloušťky čáry
        g.drawOval(x, y, width, height);
    }
}
