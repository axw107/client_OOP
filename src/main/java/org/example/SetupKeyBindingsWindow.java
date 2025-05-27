package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class SetupKeyBindingsWindow extends JInternalFrame {

    private final Client client;

    public SetupKeyBindingsWindow(Client client)
    {
        this.client = client;
        setTitle("click on me");
        setSize(300, 200);
        setLocation(0, 0);
        setupKeyBindings();
        setVisible(true);
    }


    private void setupKeyBindings() {
        JComponent contentPane = (JComponent) getContentPane();

        InputMap inputMap = contentPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = contentPane.getActionMap();


        inputMap.put(KeyStroke.getKeyStroke("LEFT"), "LEFT");
        inputMap.put(KeyStroke.getKeyStroke("RIGHT"), "RIGHT");
        inputMap.put(KeyStroke.getKeyStroke("SPACE"), "SPACE");

        actionMap.put("LEFT", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.sendCommand("LEFT");
            }
        });
        actionMap.put("RIGHT", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.sendCommand("RIGHT");
            }
        });
        actionMap.put("SPACE", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.sendCommand("SPACE");
            }
        });
    }
}
