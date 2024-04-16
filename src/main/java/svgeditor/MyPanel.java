package svgeditor;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class MyPanel extends JPanel {
    private List<Shape> tvary;

    public MyPanel() {
        tvary = new ArrayList<>();
    }

    public void addShape(Shape tvar) {
        tvary.add(tvar);
        repaint();
    }

    public List getTvar(){
        return tvary;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Shape tvar : tvary) {
            tvar.draw(g);
        }
    }
}
