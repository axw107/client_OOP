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

    private PrintWriter out;
    private final GameVisualizer visualizer;

    public Client(GameVisualizer visualizer)
    {
        this.visualizer = visualizer;
    }

    public void start(int port, String serverAddress)
    {

        try (Socket socket = new Socket(serverAddress, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            this.out = out;

            System.out.println("Connected to server.");

            String message;
            while ((message = in.readLine()) != null) {
                System.out.println(STR."Received from server: \{message}");
                processServerMessage(message);
            }

        } catch (IOException e) {
            System.err.println(STR."Client exception: \{e.getMessage()}");
            e.printStackTrace();
        }
    }

    private void processServerMessage(String message)
    {
        List<GameVisualizer.Robot> newRobots = new ArrayList<>();
        List<GameVisualizer.Target> newTargets = new ArrayList<>();
        String[] parts = message.split(" ");
        for (int i = 0; i < parts.length; ) {
            if (parts[i].equals("ROBOT")) {
                try {
                    int x = Integer.parseInt(parts[i + 1]);
                    int y = Integer.parseInt(parts[i + 2]);
                    double direction = Double.parseDouble(parts[i + 3].replace(",", "."));
                    int r = Integer.parseInt(parts[i + 4]);
                    int g = Integer.parseInt(parts[i + 5]);
                    int b = Integer.parseInt(parts[i + 6]);

                    newRobots.add(new GameVisualizer.Robot(x, y, direction, r, g, b));
                    i += 7; // pass "ROBOT x y direction r g b"
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    return;
                }
            } else if (parts[i].equals("TARGET")) {
                try {
                    int x = Integer.parseInt(parts[i + 1]);
                    int y = Integer.parseInt(parts[i + 2]);

                    newTargets.add(new GameVisualizer.Target(x, y));
                    i += 3; // pass "TARGET x y"
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    return;
                }
            } else {
                return;
            }
        }

        SwingUtilities.invokeLater(() -> {
            if (visualizer != null) {
                visualizer.setRobots(newRobots);
                visualizer.setTargets(newTargets);
            }
        });
    }

    public void sendCommand(String command)
    {
        if (out != null) {
            out.println(command);
            out.flush();
            System.out.println(STR."Send command: \{command}");
        }
    }

}

