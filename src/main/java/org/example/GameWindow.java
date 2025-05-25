package org.example;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JInternalFrame {
    public GameWindow(GameVisualizer visualizer)
    {
        super("game");
        GameVisualizer m_visualizer = visualizer;
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
        setVisible(true);
    }

}
