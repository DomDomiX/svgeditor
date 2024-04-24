package svgeditor;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class Circle extends Shape{
    private int radius;

    public Circle(int x, int y, int radius, String color, String name, int thickness) {
        super(x, y, color, name, thickness);
        this.radius = radius;
    }

    @Override
    public Map<String, Object> getAttributes() {
        Map<String, Object> attributes = new LinkedHashMap<>();
        attributes.put("Jméno", getName());
        attributes.put("Barva", getColor());
        attributes.put("Šířka čáry", getThickness());
        attributes.put("Rádius", getRadius());
        attributes.put("X pozice", getX());
        attributes.put("Y pozice", getY());
        return attributes;
    }

    @Override
    public void setAttribute(String key, Object value) {
        switch (key) {
            case "Rádius":
                this.radius = Integer.parseInt(value.toString());  // Ujistěte se, že hodnota je ve správném formátu a může být převedena na int
                break;
            default:
                super.setAttribute(key, value);  // Správně delegujte všechny ostatní atributy na nadřazenou třídu
                break;
        }
    }


    public int getRadius() {
        return radius;
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
