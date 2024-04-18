package svgeditor;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ShapeTable extends JTable {
    private ShapeTableModel model;

    public ShapeTable() {
        model = new ShapeTableModel();
        setModel(model);
    }
}
