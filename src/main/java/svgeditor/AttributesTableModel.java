package svgeditor;

import javax.swing.table.AbstractTableModel;

public class AttributesTableModel extends AbstractTableModel {
    private Shape shape;
    private String[] columnNames = {"Atribut"};

    public AttributesTableModel() {
        this.shape = null; // Nastavíme shape na null, aby byla možnost vytvářet instanci bez parametrů
    }

    public void setShape(Shape shape) {
        this.shape = shape; // Metoda pro nastavení objektu typu Shape
        fireTableDataChanged(); // Upozorní tabulku na změny v datech
    }

    @Override
    public int getRowCount() {
        if (shape != null) {
            // Počet řádků bude odpovídat počtu atributů objektu
            return 4; // Zde bude počet atributů, které máš
        }
        return 0;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (shape != null) {
            switch (rowIndex) {
                case 0:
                    return shape.getName();
                case 1:
                    return shape.getColor();
                case 2:
                    return shape.getX();
                case 3:
                    return shape.getY();
                default:
                    return null;
            }
        }
        return null;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}
