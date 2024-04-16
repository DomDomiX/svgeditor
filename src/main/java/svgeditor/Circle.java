package svgeditor;
import java.awt.*;

public class Circle extends Shape{
    private int radius;

    public Circle(int x, int y, int radius, String color, String name) {
        super(x, y, color, name);
        this.radius = radius;
    }

    @Override
    public void draw(Graphics g) {
        //g.setColor(Color.decode(this.color));
        //g.fillOval(x - radius, y - radius, 2 * radius, 2 * radius);
        g.drawOval(x - radius, y - radius, 2 * radius, 2 * radius);
    }
}
