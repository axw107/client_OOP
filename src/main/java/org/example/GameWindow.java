package org.example;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JInternalFrame {
    public GameWindow(GameVisualizer visualizer){
        super("game");
        setSize(600,  600);
        GameVisualizer m_visualizer = visualizer;
        //m_visualizer.setSize(200, 500);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
        setVisible(true);
    }

}
