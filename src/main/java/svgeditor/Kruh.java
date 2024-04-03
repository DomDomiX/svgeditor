package svgeditor;
import java.awt.*;

public class Kruh extends Shape{
    private int radius;

    public Kruh(int x, int y, int radius, String color) {
        super(x, y, color);
        this.radius = radius;
    }

    @Override
    public void draw(Graphics g) {
        //g.setColor(Color.decode(this.color));
        //g.fillOval(x - radius, y - radius, 2 * radius, 2 * radius);
        g.drawOval(x - radius, y - radius, 2 * radius, 2 * radius);
    }
}
