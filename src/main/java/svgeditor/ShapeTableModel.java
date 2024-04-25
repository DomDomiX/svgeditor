package svgeditor;

import javax.swing.table.AbstractTableModel;
import java.awt.Color;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class ShapeTableModel extends AbstractTableModel {

    private List<Shape> shapes;
    private String[] columnNames = {"Tvary"};

    public ShapeTableModel() {
        this.shapes = new ArrayList<>();
    }

    public void addShape(Shape shape) {
        shapes.add(shape);
        fireTableDataChanged(); // Upozorní tabulku na změny v datech
    }

    public void setShapes(List<Shape> shapes) {
        this.shapes = shapes;
        fireTableDataChanged(); // Aktualizuje tabulku, aby odrážela změny
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
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public Shape getShape(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < shapes.size()) {
            return shapes.get(rowIndex);
        }
        return null;
    }
}
