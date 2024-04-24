package svgeditor;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class Line extends Shape{
    private int x2;
    private int y2;

    public Line(int x, int y, String color, String name, int height, int width, int thickness) {
        super(x, y, color, name, thickness);
        this.x2 = height;
        this.y2 = width;
    }

    @Override
    public Map<String, Object> getAttributes() {
        Map<String, Object> attributes = new LinkedHashMap<>();
        attributes.put("Jméno", getName());
        attributes.put("Barva", getColor());
        attributes.put("Šířka čáry", getThickness());
        attributes.put("X2", getX2());
        attributes.put("Y2", getY2());
        attributes.put("X pozice", getX());
        attributes.put("Y pozice", getY());
        return attributes;
    }

    @Override
    public void setAttribute(String key, Object value) {
        switch (key) {
            case "X2":
                this.x2 = Integer.parseInt(value.toString());
                break;
            case "Y2":
                this.y2 = Integer.parseInt(value.toString());
                break;
            default:
                super.setAttribute(key, value);  // Volání metody v nadřazené třídě, pokud se nejedná o atribut specifický pro Line
                break;
        }
    }
    @Override
    public String toSVG() {
        return String.format("<line x1='%d' y1='%d' x2='%d' y2='%d' style='stroke:%s;stroke-width:%d'/>",
                x, y, x2, y2, color, thickness);
    }

    public int getX2() {
        return x2;
    }

    public int getY2() {
        return y2;
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Color color = Color.decode(this.color);
        g2d.setColor(color); // Nastavení barvy pro kreslení
        g2d.setStroke(new BasicStroke(getThickness())); // Nastavení tloušťky čáry
        g.drawLine(x, y, x2, y2);
    }
}
