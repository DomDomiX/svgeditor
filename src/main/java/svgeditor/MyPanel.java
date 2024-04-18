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
        Graphics2D gg = (Graphics2D) g;
        gg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for (Shape tvar : tvary) {
            tvar.draw(g);
        }
    }
}
