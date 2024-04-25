package svgeditor;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainFrame extends JFrame
{
    private final JTabbedPane tabsPane;
    private int cisloObdelnik;
    private int cisloLine;
    private int cisloKruh;
    private int cisloOval;
    private MyPanel panel;
    private JEditorPane panelSVG;
    private ShapeTableModel shapeModel;
    private ShapeTable shapeTable;
    private AttributesTable attributesTable;
    private AttributesTableModel attributesModel;

    public MainFrame() {
        super("SVG Editor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Vytvoření panelů pro jednotlivé záložky
        panel = new MyPanel();

        // Vytvoření editor panelu pro zobrazení SVG
        panelSVG = new JEditorPane();
        panelSVG.setContentType("text/html"); // Nastavení, aby mohl zobrazovat HTML/SVG
        panelSVG.setEditable(true);

        // Vytvoření JTabbedPane
        tabsPane = new JTabbedPane();

        // Vytvoření a konfigurace JTabbedPane
        tabsPane.addTab("Editor", new JScrollPane(panel));
        tabsPane.addTab("SVG", new JScrollPane(panelSVG));
        add(tabsPane, BorderLayout.CENTER);

        panelSVG.setContentType("image/svg+xml");

        // Vytvoření menu
        JMenuBar menuBar = new JMenuBar();
        JMenu menuNastroje = new JMenu("Nástroje");
        JMenuItem menuItemGenerateSVG = new JMenuItem("Vygenerovat SVG");
        JMenuItem applyChanges = new JMenuItem("Potvrdit změny v SVG");

        // Sestavení menu
        menuNastroje.add(menuItemGenerateSVG);
        menuBar.add(menuNastroje);
        menuNastroje.add(applyChanges);

        // Přidání akce k tlačítku menu
        menuItemGenerateSVG.addActionListener(e -> exportSVG());
        applyChanges.addActionListener(e -> updateShapesFromSVG());

        // Nastavení menu bar pro JFrame
        setJMenuBar(menuBar);

        // Vytvoření tabulky a jejího modelu
        shapeModel = new ShapeTableModel();
        shapeTable = new ShapeTable(); // Vytvoření instance ShapeTable
        shapeTable.setModel(shapeModel); // Nastavení modelu pro tabulku

        // Vytvoření JScrollPane pro tabulku
        JScrollPane tableScrollPane = new JScrollPane(shapeTable);

        // Vytvoření JScrollPane pro novou tabulku atributů
        attributesModel = new AttributesTableModel();
        attributesTable = new AttributesTable(attributesModel); // Vytvoříme instanci tabulky atributů
        attributesModel.setPanel(panel);
        JScrollPane attributeScrollPane = new JScrollPane(attributesTable); // Nastavíme tabulku do JScrollPane

        // Vytvoření rozdělovače pro umístění tabulky a záložek na BorderLayout.CENTER
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tabsPane, tableScrollPane);
        splitPane.setResizeWeight(0.75); // Nastavení poměru šířky panelů v rozdělovači
        splitPane.setEnabled(false); // Nastavení nezměnitelnosti rozdělovače

        // Přidání rozdělovače do hlavního okna
        getContentPane().add(splitPane, BorderLayout.CENTER);

        // Vytvoření Listeneru pro události kliknutí na tabulku
        shapeTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                int viewRow = shapeTable.getSelectedRow();
                if (viewRow < 0) {
                    // no rows are selected
                    attributesModel.setShape(null);
                } else {
                    int modelRow = shapeTable.convertRowIndexToModel(viewRow);
                    Shape selectedShape = shapeModel.getShape(modelRow);
                    attributesModel.setShape(selectedShape);
                    attributesTable.repaint(); // Refresh attribute table display
                }
            }
        });

        // Přidání několika tvarů do panelu a modelu tabulky
        addShape(new Rectangle(300, 200, "#2C2F93", "Obdelnik" + (++cisloObdelnik), 80, 60, 2));
        addShape(new Rectangle(300, 200, "#2C2F93", "Obdelnik" + (++cisloObdelnik), 80, 60, 2));
        addShape(new Circle(400, 500, 40, "#2C2F93", "Kruh" + (++cisloKruh), 2));
        addShape(new Line(600, 700, "#2C2F93", "Linka" + (++cisloLine), 50, 50, 2));
        addShape(new Oval(800, 300, "#2C2F93", "Oval" + (++cisloOval), 100, 50, 2));

        // Vytvoření nového rozdělovače pro umístění nové tabulky pod stávající tabulkou
        JSplitPane bottomSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tableScrollPane, attributeScrollPane);
        bottomSplitPane.setResizeWeight(0.5); // Nastavení poměru výšky panelů v novém rozdělovači

        // Přidání nového rozdělovače pod stávající rozdělovač
        splitPane.setRightComponent(bottomSplitPane);

        // Zobrazení hlavního okna
        pack();
        setVisible(true);
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
    }

    public void addShape(Shape shape) {
        panel.addShape(shape);
        shapeModel.addShape(shape);
    }



    private void exportSVG() {
        if (panel != null && panel.getShapeCount() > 0) {
            String svgData = panel.generateSVG();
            panelSVG.setText(svgData);
            System.out.println("SVG has been generated and set to panelSVG."); // Debugovací výpis
        } else {
            System.err.println("Panel is not initialized or no shapes are present.");
        }
    }

    private void updateShapesFromSVG() {
        String svgContent = panelSVG.getText();
        List<Shape> newShapes = parseSVG(svgContent); // Tato metoda by měla být implementována pro parsování SVG stringu do listu Shape objektů
        panel.clearShapes(); // Odstraní všechny tvary z panelu
        for (Shape shape : newShapes) {
            panel.addShape(shape); // Přidá nové tvary do panelu
        }
        panel.repaint(); // Překreslí panel s novými tvary
        shapeModel.setShapes(newShapes); // aktualizace modelu pro tabulku atributů
        attributesTable.repaint(); // Překreslí tabulku atributů
    }

    /*private List<Shape> parseSVG(String svg) {
        List<Shape> shapes = new ArrayList<>();
        Shape rect = Rectangle.parseFromSVG(svg);
        if (rect != null) shapes.add(rect);
        Shape circle = Circle.parseFromSVG(svg);
        if (circle != null) shapes.add(circle);
        Shape oval = Oval.parseFromSVG(svg);
        if (oval != null) shapes.add(oval);
        Shape line = Line.parseFromSVG(svg);
        if (line != null) shapes.add(line);
        return shapes;
    }*/

    private List<Shape> parseSVG(String svg) {
        List<Shape> shapes = new ArrayList<>();
        shapes.addAll(Rectangle.parseFromSVG(svg));
        shapes.addAll(Circle.parseFromSVG(svg));
        shapes.addAll(Oval.parseFromSVG(svg));
        shapes.addAll(Line.parseFromSVG(svg));
        return shapes;
    }
}
