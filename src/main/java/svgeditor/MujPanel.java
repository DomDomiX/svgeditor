package svgeditor;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class MujPanel extends JPanel {
    private List<Shape> tvary;

    public MujPanel() {
        tvary = new ArrayList<>();
    }

    public void addTvar(Shape tvar) {
        tvary.add(tvar);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Shape tvar : tvary) {
            tvar.draw(g);
        }
    }
}
