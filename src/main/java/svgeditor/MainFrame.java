package svgeditor;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class MainFrame extends JFrame {
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
        JMenuItem generateSVG = new JMenuItem("Vygenerovat SVG");
        JMenuItem applyChangesSVG = new JMenuItem("Potvrdit změny v SVG");

        JMenu menuFile = new JMenu("Uložit a Načíst");
        JMenuItem panelSave = new JMenuItem("Uložit Editor");
        JMenuItem SVGSave = new JMenuItem("Uložit SVG");
        JMenuItem SVGLoad = new JMenuItem("Načíst SVG");

        // Sestavení menu
        menuNastroje.add(generateSVG);
        menuBar.add(menuNastroje);
        menuBar.add(menuFile);
        menuNastroje.add(applyChangesSVG);
        menuFile.add(panelSave);
        menuFile.add(SVGSave);
        menuFile.add(SVGLoad);


        // Přidání akce k tlačítku menu
        generateSVG.addActionListener(e -> exportSVG());
        applyChangesSVG.addActionListener(e -> updateShapesFromSVG());

        // Ukládání panelu a SVG
        panelSave.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Vyberte soubor pro uložení obrázku");
            // Nastavíme filtr souborů, aby uživatel viděl pouze soubory PNG
            fileChooser.setFileFilter(new FileNameExtensionFilter("PNG Images", "png"));
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                savePanel(panel, new File(file.getAbsolutePath() + ".png")); // Přidá příponu .png, pokud není zadána
            }
        });

        SVGSave.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Vyberte soubor pro uložení SVG");
            fileChooser.setFileFilter(new FileNameExtensionFilter("SVG Files", "svg"));
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                saveSVG(panelSVG, new File(file.getAbsolutePath() + ".svg")); // Přidá příponu .svg, pokud není zadána
            }
        });

        // Načítání SVG
        SVGLoad.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Vyberte SVG soubor k načtení");
            if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                loadSVG(file, panelSVG);
            }
        });


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

    private List<Shape> parseSVG(String svg) {
        List<Shape> shapes = new ArrayList<>();
        shapes.addAll(Rectangle.parseFromSVG(svg));
        shapes.addAll(Circle.parseFromSVG(svg));
        shapes.addAll(Oval.parseFromSVG(svg));
        shapes.addAll(Line.parseFromSVG(svg));
        return shapes;
    }

    public void savePanel(JPanel panel, File file) {
        int width = panel.getWidth();
        int height = panel.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = image.createGraphics();
        panel.paint(graphics2D);
        try {
            ImageIO.write(image, "png", file); // Ulož jako PNG
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveSVG(JEditorPane panelSVG, File file) {
        String content = panelSVG.getText();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadSVG(File file, JEditorPane editorPane) {
        try {
            String svgContent = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
            editorPane.setText(svgContent);

            List<Shape> shapes = parseSVG(svgContent);
            panel.clearShapes();
            for (Shape shape : shapes) {
                panel.addShape(shape);
            }
            panel.repaint();

            shapeModel.setShapes(shapes); // Aktualizuje model tabulky s novými tvary
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Nepodařilo se načíst SVG soubor: " + e.getMessage());
        }
    }
}
