package svgeditor;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.List;
import java.util.ArrayList;

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
                this.radius = Integer.parseInt(value.toString());
                break;
            default:
                super.setAttribute(key, value);
                break;
        }
    }

    @Override
    public String toSVG() {
        return String.format("<circle cx='%d' cy='%d' r='%d' fill='%s' stroke='black' stroke-width='%d' id='%s'/>",
                x, y, radius, color, thickness, name);
    }

    public static List<Shape> parseFromSVG(String svg) {
        List<Shape> circles = new ArrayList<>();
        Pattern pattern = Pattern.compile("<circle cx='(\\d+)' cy='(\\d+)' r='(\\d+)' fill='([^']*)' stroke='([^']*)' stroke-width='(\\d+)' id='([^']*)'/>");
        Matcher matcher = pattern.matcher(svg);
        while (matcher.find()) {
            int cx = Integer.parseInt(matcher.group(1));
            int cy = Integer.parseInt(matcher.group(2));
            int radius = Integer.parseInt(matcher.group(3));
            String color = matcher.group(4);
            int thickness = Integer.parseInt(matcher.group(6));
            String name = matcher.group(7);
            circles.add(new Circle(cx, cy, radius, color, name, thickness));
        }
        return circles;
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
