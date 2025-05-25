package org.example;

import javax.swing.*;
import java.awt.*;


public class Main
{
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }


        GameVisualizer visualizer = new GameVisualizer();
        MainApplicationFrame frame = new MainApplicationFrame(visualizer, new Client(visualizer));

        frame.pack();
        frame.setVisible(true);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);

    }
}
