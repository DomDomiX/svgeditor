package svgeditor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.*;

public class MainFrame extends JFrame
{
    private final JTabbedPane tabsPane;
    public MainFrame()
    {
        super("SVG Editor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);

        tabsPane = new JTabbedPane();
        add(tabsPane);

        MujPanel panel = new MujPanel();
        MujPanel panelSVG = new MujPanel();

        tabsPane.addTab("Editor", panel);
        tabsPane.addTab("SVG", panelSVG);

        panel.addTvar(new Obdelnik(300,200,"#2C2F93",80, 60));
        panel.addTvar(new Kruh(400, 500, 40, "#2C2F93"));
        panel.addTvar(new Line(600, 700, "#2C2F93", 50, 50));
        panel.addTvar(new Oval(800, 300, "#2C2F93", 100, 50));

        setVisible(true);
    }
}
