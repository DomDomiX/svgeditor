package svgeditor;
import java.awt.*;

public class Circle extends Shape{
    private int radius;

    public Circle(int x, int y, int radius, String color, String name, int thickness) {
        super(x, y, color, name, thickness);
        this.radius = radius;
    }

    @Override
    public void draw(Graphics g) {
        //g.setColor(Color.decode(this.color));
        //g.fillOval(x - radius, y - radius, 2 * radius, 2 * radius);
        Graphics2D g2d = (Graphics2D) g;
        Color color = Color.decode(this.color);
        g2d.setColor(color); // Nastavení barvy pro kreslení
        g2d.setStroke(new BasicStroke(getThickness())); // Nastavení tloušťky čáry
        g.drawOval(x - radius, y - radius, 2 * radius, 2 * radius);
    }
}
