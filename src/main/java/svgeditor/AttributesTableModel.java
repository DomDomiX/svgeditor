package svgeditor;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;

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
        return shape != null ? 5 : 0; // Čtyři řádky pro atributy tvaru
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
                        return "Šířka čáry";
                    case 3:
                        return "X pozice";
                    case 4:
                        return "Y pozice";
                    default:
                        return null;
                }
            case 1: // Hodnota atributu
                switch (rowIndex) {
                    case 0:
                        return shape.getName();
                    case 1:
                        return shape.getColor(); // Vrátí hexadecimální řetězec
                    case 2:
                        return shape.getThickness();
                    case 3:
                        return shape.getX();
                    case 4:
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
                // Spouštíme JColorChooser, pokud uživatel chce změnit barvu
                Color initialColor = Color.decode(shape.getColor());  // Dekódujeme hexa barvu na Color
                Color newColor = JColorChooser.showDialog(null, "Vyberte barvu", initialColor);

                if (newColor != null) { // Pokud uživatel nezrušil dialog
                    String hexColor = String.format("#%02X%02X%02X", newColor.getRed(), newColor.getGreen(), newColor.getBlue());
                    System.out.println("Stará barva: " + shape.getColor() + ", Nová barva: " + hexColor); // Kontrolní výpis
                    shape.setColor(hexColor);
                    panel.repaint();
                }
                break;
            case 2:
                try {
                    shape.setThickness(Integer.parseInt((String) aValue));
                } catch (NumberFormatException e) {
                    // Handle error
                    JOptionPane.showMessageDialog(null, "Špatný formát pro nastavování hodnoty šířky.");
                    return;
                }
                break;
            case 3:
                try {
                    shape.setX(Integer.parseInt((String) aValue));
                } catch (NumberFormatException e) {
                    // Handle error
                    JOptionPane.showMessageDialog(null, "Špatný formát pro nastavení hodnoty X.");
                    return;
                }
                break;
            case 4:
                try {
                    shape.setY(Integer.parseInt((String) aValue));
                } catch (NumberFormatException e) {
                    // Handle error
                    JOptionPane.showMessageDialog(null, "Špatný formát pro nastavení hodnoty Y.");
                    return;
                }
                break;
        }
        fireTableDataChanged(); // Informuje tabulku o změně dat
        panel.repaint(); // Zajištění překreslení pro všechny případy
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column]; // Vrátí název sloupce
    }
}
