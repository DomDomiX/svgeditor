package svgeditor;

import javax.swing.*;

public class ShapeTable extends JTable {
    private ShapeTableModel model;

    public ShapeTable() {
        model = new ShapeTableModel();
        setModel(model);
    }
}
