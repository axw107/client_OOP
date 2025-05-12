package org.example;


//import log.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainApplicationFrame extends JFrame
{

    private final JDesktopPane desktopPane = new JDesktopPane();


    public MainApplicationFrame(SetupKeyBindingsWindow keyBindingsWindow, GameVisualizer visualizer)  {

        GameWindow gameWindow = createGameWindow(visualizer);
        desktopPane.add(gameWindow);
        gameWindow.setVisible(true);

        desktopPane.add(keyBindingsWindow);
        keyBindingsWindow.setVisible(true);


        configureFrame();
        setJMenuBar(generateMenuBar());

    }


    private JMenuItem createStopGameButton() {
        JMenuItem gameButton = new JMenuItem();
        gameButton.setMaximumSize(new Dimension(50, 20));

        gameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        return gameButton;
    }

    private void configureFrame() {
        //Make the big window be indented 50 pixels from each edge
        //of the screen.
        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
                screenSize.width  - inset*2,
                screenSize.height - inset*2);
        setContentPane(desktopPane);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }

    protected GameWindow createGameWindow(GameVisualizer visualizer)
    {
        GameWindow gameWindow = new GameWindow(visualizer);

        gameWindow.setSize(800,  800);

        return gameWindow;
    }



    private JMenuBar generateMenuBar()
    {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createStopGameButton());

        return menuBar;
    }


}
