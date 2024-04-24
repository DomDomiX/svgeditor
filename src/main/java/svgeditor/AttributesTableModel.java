package svgeditor;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AttributesTableModel extends AbstractTableModel {
    private Shape shape; // Aktuálně vybraný tvar
    private MyPanel panel; // Panel pro překreslení
    private List<String> attributeNames = new ArrayList<>(); // Názvy atributů tvaru
    private List<Object> attributeValues = new ArrayList<>(); // Hodnoty atributů tvaru
    private String[] columnNames = {"Název", "Hodnota"}; // Sloupce tabulky

    public AttributesTableModel() {
        this.shape = null; // Inicializace bez konkrétního tvaru
    }

    public void setPanel(MyPanel panel) {
        this.panel = panel; // Nastavení panelu pro možnost překreslení
    }

    public void setShape(Shape shape) {
        this.shape = shape; // Nastavení aktuálně vybraného tvaru
        updateAttributes(); // Aktualizace seznamu atributů pro nový tvar
    }

    private void updateAttributes() {
        attributeNames.clear();
        attributeValues.clear();
        if (shape != null) {
            Map<String, Object> attributes = shape.getAttributes(); // Získání atributů od tvaru
            for (Map.Entry<String, Object> entry : attributes.entrySet()) {
                attributeNames.add(entry.getKey()); // Přidání názvu atributu
                attributeValues.add(entry.getValue()); // Přidání hodnoty atributu
            }
        }
        fireTableDataChanged(); // Informuje tabulku o změně dat
    }

    @Override
    public int getRowCount() {
        return attributeNames.size(); // Počet řádků závisí na počtu atributů
    }

    @Override
    public int getColumnCount() {
        return columnNames.length; // Vždy dva sloupce, Název a Hodnota
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (shape == null) {
            return null;
        }
        if (columnIndex == 0) {
            return attributeNames.get(rowIndex); // Vrátí název atributu
        } else {
            return attributeValues.get(rowIndex); // Vrátí hodnotu atributu
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 1; // Umožňuje editaci pouze v druhém sloupci
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (shape == null || columnIndex != 1) {
            return;
        }
        String attributeName = attributeNames.get(rowIndex);
        try {
            // Speciální případ pro barvu
            if (attributeName.equals("Barva")) {
                Color initialColor = Color.decode((String) aValue);
                Color newColor = JColorChooser.showDialog(null, "Vyberte barvu", initialColor);
                if (newColor != null) {
                    String hexColor = String.format("#%02X%02X%02X", newColor.getRed(), newColor.getGreen(), newColor.getBlue());
                    aValue = hexColor;
                }
            }
            shape.setAttribute(attributeName, aValue);
            updateAttributes();  // Aktualizace atributů po změně
            fireTableCellUpdated(rowIndex, columnIndex); // Aktualizace tabulky
            if (panel != null) {
                panel.repaint(); // Překreslení panelu
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error setting attribute value: " + e.getMessage());
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column]; // Vrátí název sloupce
    }
}
