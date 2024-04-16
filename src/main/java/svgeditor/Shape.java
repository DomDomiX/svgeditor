package svgeditor;
import java.awt.*;

public abstract class Shape {
    protected int x, y;
    protected String color;

    public Shape(int x, int y, String color){
        this.x = x;
        this.y = y;
        this.color = color;
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

    public abstract void draw(Graphics g);
}

