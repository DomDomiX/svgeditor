package svgeditor;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.MouseEvent;
import javax.swing.*;
import java.awt.event.MouseAdapter;

public class MyPanel extends JPanel {
    private List<Shape> tvary;
    private ShapeTableModel shapeTableModel;
    private ShapeTable shapeTable;

    public MyPanel() {
        tvary = new ArrayList<>();
        shapeTableModel = new ShapeTableModel();
    }

    public void addShape(Shape tvar) {
        tvary.add(tvar);
        repaint();
    }

    // Metoda pro získání počtu tvarů
    public int getShapeCount() {
        return tvary.size();
    }

    // Metoda pro odstranění všech tvarů
    public void clearShapes() {
        tvary.clear();
        repaint();  // Překreslí panel, aby odstranil zobrazení předchozích tvarů
    }

    public String generateSVG() {
        StringBuilder svgBuilder = new StringBuilder();
        svgBuilder.append("<svg width='800' height='600' xmlns='http://www.w3.org/2000/svg'>\n");
        for (Shape shape : tvary) {
            svgBuilder.append(shape.toSVG()).append("\n");
        }
        svgBuilder.append("</svg>");
        return svgBuilder.toString();
    }

    public void startDrawingRectangle() {
        MouseAdapter adapter = new MouseAdapter() {
            Point start;

            @Override
            public void mousePressed(MouseEvent e) {
                start = e.getPoint();
                Rectangle rect = new Rectangle(start.x, start.y, "#000000", "New Rectangle", 0, 0, 1);
                addShape(rect);
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                Rectangle rect = (Rectangle) tvary.get(tvary.size() - 1);
                rect.setWidth(Math.abs(e.getX() - start.x));
                rect.setHeight(Math.abs(e.getY() - start.y));
                rect.setX(Math.min(start.x, e.getX()));
                rect.setY(Math.min(start.y, e.getY()));
                repaint();  // Překreslení panelu
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                removeMouseListener(this);
                removeMouseMotionListener(this);
                showRectangleDialog((Rectangle) tvary.get(tvary.size() - 1));  // Zobrazí dialog pro editaci obdelníku
                repaint();
            }
        };
        addMouseListener(adapter);
        addMouseMotionListener(adapter);
    }

    private void showRectangleDialog(Rectangle rect) {
        JTextField nameField = new JTextField(rect.getName());
        JColorChooser colorChooser = new JColorChooser(Color.decode(rect.getColor()));
        int result = JOptionPane.showConfirmDialog(null, new Object[] {"Name:", nameField, "Color:", colorChooser}, "Rectangle Properties", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            rect.setName(nameField.getText());
            Color color = colorChooser.getColor();
            rect.setColor(String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue()));  // Nastavení barvy jako hex
            shapeTableModel.addShape(rect);  // Přidání obdelníku do modelu tabulky
            shapeTable.repaint();  // Překreslení tabulky
        }
    }

    public void setShapeTableModel(ShapeTableModel model) {
        this.shapeTableModel = model;
    }

    public void setShapeTable(ShapeTable table) {
        this.shapeTable = table;
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
