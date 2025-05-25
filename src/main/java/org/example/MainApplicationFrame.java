package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainApplicationFrame extends JFrame
{

    private final JDesktopPane desktopPane = new JDesktopPane();
    private final Client client;


    public MainApplicationFrame(GameVisualizer visualizer, Client client)  {

        this.client = client;

        SetupKeyBindingsWindow keyBindingsWindow = new SetupKeyBindingsWindow(client);
        desktopPane.add(keyBindingsWindow);
        keyBindingsWindow.setVisible(true);

        GameWindow gameWindow = createGameWindow(visualizer);
        desktopPane.add(gameWindow);
        gameWindow.setVisible(true);


        configureFrame();

        setJMenuBar(generateMenuBar());

    }

    private JMenuItem createStartButton() {

        JMenuItem startButton = new JMenuItem("start");
        startButton.setMaximumSize(new Dimension(50, 30));

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showConfigDialog();
            }
        });

        return startButton;
    }

    private void showConfigDialog() {

        JTextField addressField = new JTextField(15);
        JTextField portField = new JTextField(5);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));
        panel.add(new JLabel("address"));
        panel.add(addressField);
        panel.add(new JLabel("port"));
        panel.add(portField);

        while (true) {

            int result = JOptionPane.showConfirmDialog(this, panel,
                    "server parameters", JOptionPane.OK_CANCEL_OPTION);


            if (result == JOptionPane.OK_OPTION) {
                String address = addressField.getText().trim();
                String port = portField.getText().trim();


                if (!isValidAddress(address)) {
                    JOptionPane.showMessageDialog(this, "invalid server address.",
                            "error", JOptionPane.ERROR_MESSAGE);
                    continue;
                }

                if (!isValidPort(port)) {
                    JOptionPane.showMessageDialog(this, "invalid port.",
                            "error", JOptionPane.ERROR_MESSAGE);
                    continue;
                }

                Thread serverThread = new Thread(() -> {
                    try {
                        client.start(Integer.parseInt(port),address );
                    } catch (Exception ex) {
                        SwingUtilities.invokeLater(() -> {
                            JOptionPane.showMessageDialog(MainApplicationFrame.this,
                                     ex.getMessage(),
                                    "error",
                                    JOptionPane.ERROR_MESSAGE);
                        });
                    }
                });
                serverThread.setDaemon(true);
                serverThread.start();
            }
            break;
        }
    }

    private boolean isValidAddress(String address) {
        String ipPattern = "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
        return address != null && address.matches(ipPattern);
    }
    private boolean isValidPort(String port) {
        try {
            int portNumber = Integer.parseInt(port);
            return portNumber > 0 && portNumber <= 65535;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    private void configureFrame() {
        //Make the big window be indented 50 pixels from each edge
        //of the screen.
        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(inset, inset,
                screenSize.width  - inset*2,
                screenSize.height - inset*2);
        setContentPane(desktopPane);

    }

    protected GameWindow createGameWindow(GameVisualizer visualizer)
    {
        GameWindow gameWindow = new GameWindow(visualizer);
        gameWindow.setSize(800,  800);
        gameWindow.setLocation(800, 100);
        return gameWindow;
    }


    private JMenuBar generateMenuBar()
    {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createStartButton());
        return menuBar;
    }

}
