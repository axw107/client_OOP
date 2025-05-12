package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;


public class GameVisualizer extends JPanel {

    public List<Robot> robots = new ArrayList<>();
    public List<Target> targets = new ArrayList<>();

    public GameVisualizer() {}


    public void setRobots(List<Robot> robots) {
        this.robots = new ArrayList<>(robots);
        repaint();
    }

    public void setTargets(List<Target> targets) {
        this.targets = new ArrayList<>(targets);
        repaint();
    }



    private static int round(double value) {
        return (int) (value + 0.5);
    }

    private static void fillOval(Graphics g, int centerX, int centerY, int diam1, int diam2) {
        g.fillOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }

    private static void drawOval(Graphics g, int centerX, int centerY, int diam1, int diam2) {
        g.drawOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }


    private void drawRobot(Graphics2D g, int x, int y, double direction) {
        int robotCenterX = round(x);
        int robotCenterY = round(y);
        AffineTransform t = AffineTransform.getRotateInstance(direction, robotCenterX, robotCenterY);
        g.setTransform(t);
        g.setColor(Color.YELLOW);
        fillOval(g, robotCenterX, robotCenterY, 30, 10);
        g.setColor(Color.BLACK);
        drawOval(g, robotCenterX, robotCenterY, 30, 10);
        g.setColor(Color.WHITE);
        fillOval(g, robotCenterX + 10, robotCenterY, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, robotCenterX + 10, robotCenterY, 5, 5);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Обязательно вызываем super.paintComponent!
        Graphics2D g2d = (Graphics2D) g;

        for (Robot robot : robots) {
            drawRobot(g2d, robot.x, robot.y, robot.direction); // Рисуем каждого робота
        }

        for (Target target : targets) {
            drawTarget(g2d, round(target.x), round(target.y));
        }
    }

    private void drawTarget(Graphics2D g, int x, int y) {
        AffineTransform t = AffineTransform.getRotateInstance(0, 0, 0);
        g.setTransform(t);
        g.setColor(Color.GREEN);
        fillOval(g, x, y, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, x, y, 5, 5);
    }



    //Вспомогательный класс, представляющий состояние робота
    public static class Robot {
        public int x;
        public int y;
        public double direction;

        public Robot(int x, int y, double direction) {
            this.x = x;
            this.y = y;
            this.direction = direction;
        }
    }

    public static class Target {
        public int x;
        public int y;

        public Target(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}