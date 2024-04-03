package svgeditor;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame
{
    public MainFrame()
    {
        JFrame frame = new JFrame("SVG Editor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(MAXIMIZED_BOTH);

        MujPanel panel = new MujPanel();
        panel.addTvar(new Obdelnik(300,200,"#2C2F93",80, 60));
        panel.addTvar(new Kruh(400, 500, 40, "#2C2F93"));
        panel.addTvar(new Line(600, 700, "#2C2F93", 50, 50));
        panel.addTvar(new Oval(800, 300, "#2C2F93", 100, 50));

        frame.add(panel);

        frame.setVisible(true);
    }

}
