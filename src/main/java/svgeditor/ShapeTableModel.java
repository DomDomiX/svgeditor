package svgeditor;

import javax.swing.table.AbstractTableModel;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class ShapeTableModel extends AbstractTableModel {

    private List<Shape> shapes;
    private String[] columnNames = {"Typ", "X", "Y", "Barva"};

    public ShapeTableModel() {
        this.shapes = new ArrayList<>();
    }

    public void addShape(Shape shape) {
        shapes.add(shape);
        fireTableDataChanged(); // Upozorní tabulku na změny v datech
    }

    @Override
    public int getRowCount() {
        return shapes.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Shape shape = shapes.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return shape.getName();
            case 1:
                return shape.getX();
            case 2:
                return shape.getY();
            case 3:
                return shape.getColor();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}
