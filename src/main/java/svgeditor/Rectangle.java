package svgeditor;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.List;
import java.util.ArrayList;

public class Rectangle extends Shape {
    private int width;
    private int height;

    public Rectangle(int x, int y, String color, String name, int width, int height, int thickness) {
        super(x, y, color, name, thickness);
        this.width = width;
        this.height = height;
    }

    @Override
    public Map<String, Object> getAttributes() {
        Map<String, Object> attributes = new LinkedHashMap<>();
        attributes.put("Jméno", getName());
        attributes.put("Barva", getColor());
        attributes.put("Šířka čáry", getThickness());
        attributes.put("Šířka", getWidth());
        attributes.put("Výška", getHeight());
        attributes.put("X pozice", getX());
        attributes.put("Y pozice", getY());
        return attributes;
    }

    @Override
    public void setAttribute(String key, Object value) {
        switch (key) {
            case "Šířka":
                this.width = Integer.parseInt(value.toString());
                break;
            case "Výška":
                this.height = Integer.parseInt(value.toString());
                break;
            default:
                super.setAttribute(key, value);  // Volání metody v nadřazené třídě, pokud se nejedná o atribut specifický pro Rectangle
                break;
        }
    }

    @Override
    public String toSVG() {
        return String.format("<rect x='%d' y='%d' width='%d' height='%d' fill='%s' stroke='black' stroke-width='%d' id='%s'/>",
                x, y, width, height, color, thickness, name);
    }

    public static List<Shape> parseFromSVG(String svg) {
        List<Shape> rectangles = new ArrayList<>();
        Pattern pattern = Pattern.compile("<rect x='(\\d+)' y='(\\d+)' width='(\\d+)' height='(\\d+)' fill='([^']*)' stroke='([^']*)' stroke-width='(\\d+)' id='([^']*)'/>");
        Matcher matcher = pattern.matcher(svg);
        while (matcher.find()) {
            int x = Integer.parseInt(matcher.group(1));
            int y = Integer.parseInt(matcher.group(2));
            int width = Integer.parseInt(matcher.group(3));
            int height = Integer.parseInt(matcher.group(4));
            String color = matcher.group(5);
            int thickness = Integer.parseInt(matcher.group(7));
            String name = matcher.group(8);
            rectangles.add(new Rectangle(x, y, color, name, width, height, thickness));
        }
        return rectangles;
    }

    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public void setWidth(int width) { this.width = width; }
    public void setHeight(int height) { this.height = height; }
    public void setX(int x) { this.x = x;
    }
    public void setY(int y) { this.y = y; }
    public void setColor(String color) { this.color = color; }
    public void setName(String name) { this.name = name; }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Color color = Color.decode(this.color);
        g2d.setColor(color); // Nastavení barvy pro kreslení
        g2d.setStroke(new BasicStroke(getThickness())); // Nastavení tloušťky čáry
        g2d.drawRect(x, y, width, height);
    }
}
