package svgeditor;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

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
                super.setAttribute(key, value);  // Volání metody v nadřazené třídě, pokud se nejedná o atribut specifický pro Line
                break;
        }
    }

    @Override
    public String toSVG() {
        return String.format("<rect x='%d' y='%d' width='%d' height='%d' fill='%s' stroke='black' stroke-width='%d'/>",
                x, y, width, height, color, thickness);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Color color = Color.decode(this.color);
        g2d.setColor(color); // Nastavení barvy pro kreslení
        g2d.setStroke(new BasicStroke(getThickness())); // Nastavení tloušťky čáry
        g2d.drawRect(x, y, width, height);
    }
}
