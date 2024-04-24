package svgeditor;
import java.awt.*;
import java.util.Map;

public abstract class Shape {
    protected int x, y;
    protected String color;
    protected String name;
    protected int thickness;

    public Shape(int x, int y, String color, String name, int thickness){
        this.x = x;
        this.y = y;
        this.color = color;
        this.name = name;
        this.thickness = thickness;
    }

    public void setAttribute(String key, Object value) {
        switch (key) {
            case "Jméno":
                this.name = (String) value;
                break;
            case "Barva":
                this.color = (String) value;
                break;
            case "Šířka čáry":
                this.thickness = Integer.parseInt(value.toString());
                break;
            case "X pozice":
                this.x = Integer.parseInt(value.toString());
                break;
            case "Y pozice":
                this.y = Integer.parseInt(value.toString());
                break;
            default:
                throw new IllegalArgumentException("Neznámý atribut: " + key);
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public int getThickness() {
        return thickness;
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setThickness(int thickness) {
        this.thickness = thickness;
    }

    public abstract Map<String, Object> getAttributes();

    public abstract void draw(Graphics g);
}

