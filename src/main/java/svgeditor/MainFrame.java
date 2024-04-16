package svgeditor;

import javax.swing.*;
import java.awt.BorderLayout;

public class MainFrame extends JFrame
{
    private final JTabbedPane tabsPane;
    private int cisloObdelnik;
    private int cisloLine;
    private int cisloKruh;
    private int cisloOval;

    public MainFrame() {
        super("SVG Editor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Vytvoření JTabbedPane
        tabsPane = new JTabbedPane();
        add(tabsPane, BorderLayout.CENTER); // Přidání do BorderLayout.CENTER

        // Vytvoření panelů pro jednotlivé záložky
        MyPanel panel = new MyPanel();
        MyPanel panelSVG = new MyPanel();

        // Přidání panelů do záložek
        tabsPane.addTab("Editor", panel);
        tabsPane.addTab("SVG", panelSVG);

        // Vytvoření tabulky a jejího modelu
        ShapeTableModel model = new ShapeTableModel();
        ShapeTable table = new ShapeTable();
        table.setModel(model); // Nastavení modelu pro tabulku

        // Vytvoření JScrollPane pro tabulku
        JScrollPane tableScrollPane = new JScrollPane(table);

        // Vytvoření rozdělovače pro umístění tabulky a záložek na BorderLayout.CENTER
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tabsPane, tableScrollPane);
        splitPane.setResizeWeight(1); // Nastavení poměru šířky panelů v rozdělovači
        splitPane.setEnabled(false); // Nastavení nezměnitelnosti rozdělovače

        // Přidání rozdělovače do hlavního okna
        getContentPane().add(splitPane, BorderLayout.CENTER);

        // Přidání několika tvarů do panelu
        panel.addShape(new Rectangle(300, 200, "#2C2F93", "Obdelnik" + (cisloObdelnik + 1), 80, 60));
        panel.addShape(new Circle(400, 500, 40, "#2C2F93", "Kruh" + (cisloKruh + 1)));
        panel.addShape(new Line(600, 700, "#2C2F93", "Linka" + (cisloLine + 1), 50, 50));
        panel.addShape(new Oval(800, 300, "#2C2F93", "Oval" + (cisloOval + 1), 100, 50));

        model.addShape(new Rectangle(300, 200, "#2C2F93", "Obdelnik" + (cisloObdelnik + 1), 80, 60));
        model.addShape(new Circle(400, 500, 40, "#2C2F93", "Kruh" + (cisloKruh + 1)));
        model.addShape(new Line(600, 700, "#2C2F93", "Linka" + (cisloLine + 1), 50, 50));
        model.addShape(new Oval(800, 300, "#2C2F93", "Oval" + (cisloOval + 1), 100, 50));

        // Pack
        pack();
        // Zobrazení hlavního okna
        setVisible(true);

        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
    }
}
