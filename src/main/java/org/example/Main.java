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

        int port = 12345;
        String serverAddress = "127.0.0.1";

        // Обработка аргументов командной строки
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]); // Пытаемся получить порт из первого аргумента
            } catch (NumberFormatException e) {
                System.err.println(STR."Неверный формат порта. Используется порт по умолчанию: \{port}");
            }
        }

        if (args.length > 1) {
            serverAddress = args[1]; // Получаем адрес сервера из второго аргумента
        }
        else System.err.println(STR."Используется адрес по умолчанию: \{serverAddress}");

        GameVisualizer visualizer = new GameVisualizer();
        Client client = new Client(port, serverAddress, visualizer);
        SetupKeyBindingsWindow keyBindingsWindow = new SetupKeyBindingsWindow(client);


        MainApplicationFrame frame = new MainApplicationFrame(keyBindingsWindow, visualizer);

        frame.pack();
        frame.setVisible(true);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);

        System.out.println("AAAAAAAAAAA");



        client.start();

    }
}
