package svgeditor;

import javax.swing.table.AbstractTableModel;

public class AttributesTableModel extends AbstractTableModel {
    private Shape shape;
    private String[] columnNames = {"Atribut", "Hodnota"}; // Dva sloupce: název a hodnota

    public AttributesTableModel() {
        this.shape = null; // Nastavíme shape na null, aby byla možnost vytvářet instanci bez parametrů
    }

    public void setShape(Shape shape) {
        this.shape = shape; // Metoda pro nastavení objektu typu Shape
        fireTableDataChanged(); // Upozorní tabulku na změny v datech
    }

    @Override
    public int getRowCount() {
        return shape != null ? 4 : 0; // Čtyři řádky pro atributy tvaru
    }

    @Override
    public int getColumnCount() {
        return columnNames.length; // Dva sloupce
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (shape == null) {
            return null;
        }
        switch (columnIndex) {
            case 0: // Název atributu
                switch (rowIndex) {
                    case 0:
                        return "Jméno";
                    case 1:
                        return "Barva";
                    case 2:
                        return "X pozice";
                    case 3:
                        return "Y pozice";
                    default:
                        return null;
                }
            case 1: // Hodnota atributu
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
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column]; // Vrátí název sloupce
    }
}
