package svgeditor;

import javax.swing.*;
import java.awt.Dimension;

public class AttributesTable extends JTable {
    public AttributesTable(AttributesTableModel model) {
        super(model);
        setAutoCreateRowSorter(true); // Povolí třídění tabulky
        setFillsViewportHeight(true); // Nastaví automatické vyplnění výšky tabulky
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Povolí výběr pouze jednoho řádku
    }

    @Override
    public Dimension getPreferredScrollableViewportSize() {
        return new Dimension(300, getRowHeight() * getRowCount()); // Nastaví výchozí velikost zobrazení tabulky
    }
}
