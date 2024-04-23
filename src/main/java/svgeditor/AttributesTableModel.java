package svgeditor;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

public class AttributesTableModel extends AbstractTableModel {
    private Shape shape;
    private MyPanel panel;
    private String[] columnNames = {"Název", "Hodnota"}; // Dva sloupce: název a hodnota

    public AttributesTableModel() {
        this.shape = null; // Shape na null, aby byla možnost vytvářet instanci bez parametrů
    }

    public void setPanel(MyPanel panel) {
        this.panel = panel;
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
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 1; // Umožní editaci pouze ve druhém sloupci, kde jsou hodnoty
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (shape == null || columnIndex != 1) {
            return;
        }
        switch (rowIndex) {
            case 0:
                shape.setName((String) aValue);
                break;
            case 1:
                shape.setColor((String) aValue);
                break;
            case 2:
                try {
                    shape.setX(Integer.parseInt((String) aValue));
                } catch (NumberFormatException e) {
                    // Handle error
                    JOptionPane.showMessageDialog(null, "Invalid number format for X position.");
                    return;
                }
                break;
            case 3:
                try {
                    shape.setY(Integer.parseInt((String) aValue));
                } catch (NumberFormatException e) {
                    // Handle error
                    JOptionPane.showMessageDialog(null, "Invalid number format for Y position.");
                    return;
                }
                break;
        }
        fireTableDataChanged(); // Informuje tabulku o změně dat

        if (panel != null) { // Překreslí panel, pokud není null, aby došlo k aktualizaci jeho zobrazení
            panel.repaint();
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column]; // Vrátí název sloupce
    }
}
