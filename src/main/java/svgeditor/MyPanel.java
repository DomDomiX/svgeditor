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

    // Metoda pro získání počtu tvarů
    public int getShapeCount() {
        return tvary.size();
    }

    public String generateSVG() {
        StringBuilder svgBuilder = new StringBuilder();
        svgBuilder.append("<svg width='800' height='600' xmlns='http://www.w3.org/2000/svg'>\n");
        for (Shape shape : tvary) {
            svgBuilder.append(shape.toSVG()).append("\n");
        }
        svgBuilder.append("</svg>");
        System.out.println("Generated SVG: " + svgBuilder.toString()); // Výpis vygenerovaného SVG
        return svgBuilder.toString();
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
