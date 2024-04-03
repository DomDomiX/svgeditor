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

    public abstract void draw(Graphics g);
}

