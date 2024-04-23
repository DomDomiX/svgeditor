package svgeditor;

import java.awt.*;

public class Line extends Shape{
    private int width;
    private int height;

    public Line(int x, int y, String color, String name, int height, int width, int thickness) {
        super(x, y, color, name, thickness);
        this.height = height;
        this.width = width;
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Color color = Color.decode(this.color);
        g2d.setColor(color); // Nastavení barvy pro kreslení
        g2d.setStroke(new BasicStroke(getThickness())); // Nastavení tloušťky čáry
        g.drawLine(x, y, width, height);
    }
}
