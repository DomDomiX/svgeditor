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
    private MyPanel panel;
    private ShapeTableModel model;
    private ShapeTable table;

    public MainFrame() {
        super("SVG Editor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Vytvoření JTabbedPane
        tabsPane = new JTabbedPane();
        add(tabsPane, BorderLayout.CENTER); // Přidání do BorderLayout.CENTER

        // Vytvoření panelů pro jednotlivé záložky
        panel = new MyPanel();
        MyPanel panelSVG = new MyPanel();

        // Přidání panelů do záložek
        tabsPane.addTab("Editor", panel);
        tabsPane.addTab("SVG", panelSVG);

        // Vytvoření tabulky a jejího modelu
        model = new ShapeTableModel();
        table = new ShapeTable();
        table.setModel(model); // Nastavení modelu pro tabulku

        // Vytvoření JScrollPane pro tabulku
        JScrollPane tableScrollPane = new JScrollPane(table);

        // Vytvoření rozdělovače pro umístění tabulky a záložek na BorderLayout.CENTER
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tabsPane, tableScrollPane);
        splitPane.setResizeWeight(1); // Nastavení poměru šířky panelů v rozdělovači
        splitPane.setEnabled(false); // Nastavení nezměnitelnosti rozdělovače

        // Přidání rozdělovače do hlavního okna
        getContentPane().add(splitPane, BorderLayout.CENTER);

        // Přidání několika tvarů do panelu a modelu tabulky
        addShape(new Rectangle(300, 200, "#2C2F93", "Obdelnik"  + (cisloObdelnik + 1), 80, 60, 2));
        addShape(new Circle(400, 500, 40, "#2C2F93", "Kruh"  + (cisloKruh + 1), 2));
        addShape(new Line(600, 700, "#2C2F93", "Linka"  + (cisloLine + 1), 50, 50, 2));
        addShape(new Oval(800, 300, "#2C2F93", "Oval" + (cisloOval + 1), 100, 50, 2));

        // Pack
        pack();

        // Zobrazení hlavního okna
        setVisible(true);

        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
    }

    public void addShape(Shape shape) {
        panel.addShape(shape);
        model.addShape(shape);
    }
}
