package org.example;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Client {

    private String SERVER_ADDRESS;
    private int PORT;
    private PrintWriter out;
    private final GameVisualizer visualizer;

    public Client(int port, String serverAddress, GameVisualizer visualizer){
        SERVER_ADDRESS = serverAddress;
        PORT = port;
        this.visualizer = visualizer;
    }

    // Отправка команды на сервер
    public void sendCommand(String command) {
        if (out != null) {
            out.println(command);
            out.flush();
            System.out.println(STR."Send command: \{command}");
        }
    }

    public void start() {

        try (Socket socket = new Socket(SERVER_ADDRESS, PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            this.out = out; // Сохраняем ссылку на PrintWriter

            System.out.println("Connected to server.");


            // Чтение сообщений от сервера
            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("Received from server: " + message);
                processServerMessage(message);
            }
            //gameVisualizer.onRedrawEvent();


            // Здесь можно добавить код для получения состояния от сервера, если это необходимо

            // Блокировка потока, чтобы окно не закрывалось
            while (true) {
                // Можно добавить логику для обработки состояния игры, если это нужно
            }

        } catch (IOException e) {
            System.err.println("Client exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void processServerMessage(String message) {
        System.out.println(message);
        List<GameVisualizer.Robot> newRobots = new ArrayList<>();
        List<GameVisualizer.Target> newTargets = new ArrayList<>();
        String[] parts = message.split(" ");
        for (int i = 0; i < parts.length; ) {
            if (parts[i].equals("ROBOT")) {
                try {
                    int x = Integer.parseInt(parts[i + 1]);
                    int y = Integer.parseInt(parts[i + 2]);
                    double direction = Double.parseDouble(parts[i + 3].replace(",", "."));

                    newRobots.add(new GameVisualizer.Robot(x, y, direction));
                    i += 4; // Пропускаем "ROBOT x y direction"
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    System.err.println("Error parsing robot data: " + message);
                    return; // Прерываем обработку сообщения при ошибке
                }
            } else if (parts[i].equals("TARGET")) {
                try {
                    int x = Integer.parseInt(parts[i + 1]);
                    int y = Integer.parseInt(parts[i + 2]);

                    newTargets.add(new GameVisualizer.Target(x, y));
                    i += 3; // Пропускаем "TARGET x y"
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    System.err.println("Error parsing target data: " + message);
                    return; // Прерываем обработку сообщения при ошибке
                }
            } else {
                System.err.println("Unknown data type: " + parts[i]);
                return; // Прерываем обработку сообщения при ошибке
            }
        }

        SwingUtilities.invokeLater(() -> {
            if (visualizer != null) {
                visualizer.setRobots(newRobots);
                visualizer.setTargets(newTargets);
            }
        });
    }
}

