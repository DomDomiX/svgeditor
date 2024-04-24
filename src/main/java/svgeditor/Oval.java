package svgeditor;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class Oval extends Shape{
    private int width;
    private int height;

    public Oval(int x, int y, String color, String name, int width, int height, int thickness) {
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
        return String.format("<ellipse cx='%d' cy='%d' rx='%d' ry='%d' fill='%s' stroke='black' stroke-width='%d'/>",
                x + width / 2, y + height / 2, width / 2, height / 2, color, thickness);
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
        // Převedení hexadecimálního řetězce barvy na Color objekt
        Color color = Color.decode(this.color);
        g2d.setColor(color); // Nastavení barvy pro kreslení
        g2d.setStroke(new BasicStroke(getThickness())); // Nastavení tloušťky čáry
        g.drawOval(x, y, width, height);
    }
}
