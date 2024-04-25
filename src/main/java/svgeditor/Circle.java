package svgeditor;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @Override
    public String toSVG() {
        return String.format("<circle cx='%d' cy='%d' r='%d' fill='%s' stroke='black' stroke-width='%d'/>",
                x, y, radius, color, thickness);
    }

    public static Circle parseFromSVG(String svg) {
        Pattern pattern = Pattern.compile("<circle cx='(\\d+)' cy='(\\d+)' r='(\\d+)' fill='([^']*)' stroke='([^']*)' stroke-width='(\\d+)'/>");
        Matcher matcher = pattern.matcher(svg);
        if (matcher.find()) {
            int cx = Integer.parseInt(matcher.group(1));
            int cy = Integer.parseInt(matcher.group(2));
            int radius = Integer.parseInt(matcher.group(3));
            String color = matcher.group(4);
            int thickness = Integer.parseInt(matcher.group(6));
            return new Circle(cx, cy, radius, color, "Circle", thickness);
        }
        return null;
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
