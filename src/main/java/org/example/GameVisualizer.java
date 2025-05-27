package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;


public class GameVisualizer extends JPanel {

    private List<Robot> robots = new ArrayList<>();
    private List<Target> targets = new ArrayList<>();

    public void setRobots(List<Robot> robots)
    {
        this.robots = robots;
        repaint();
    }

    public void setTargets(List<Target> targets)
    {
        this.targets = targets;
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


    private void drawRobot(Graphics2D g, int x, int y, double direction, Color color)
    {
        int robotCenterX = round(x);
        int robotCenterY = round(y);
        AffineTransform t = AffineTransform.getRotateInstance(direction, robotCenterX, robotCenterY);
        g.setTransform(t);
        g.setColor(color);
        fillOval(g, robotCenterX, robotCenterY, 30, 10);
        g.setColor(Color.BLACK);
        drawOval(g, robotCenterX, robotCenterY, 30, 10);
        g.setColor(Color.WHITE);
        fillOval(g, robotCenterX + 10, robotCenterY, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, robotCenterX + 10, robotCenterY, 5, 5);
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        for (Robot robot : robots) {
            drawRobot(g2d, robot.x, robot.y, robot.direction, robot.color);
        }

        for (Target target : targets) {
            drawTarget(g2d, round(target.x), round(target.y));
        }
    }

    private void drawTarget(Graphics2D g, int x, int y)
    {
        AffineTransform t = AffineTransform.getRotateInstance(0, 0, 0);
        g.setTransform(t);
        g.setColor(Color.GREEN);
        fillOval(g, x, y, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, x, y, 5, 5);
    }

    public static class Robot
    {
        public int x;
        public int y;
        public double direction;
        public Color color;

        public Robot(int x, int y, double direction, int r, int g, int b) {
            this.x = x;
            this.y = y;
            this.direction = direction;
            this.color = new Color(r, g, b);

        }
    }

    public static class Target
    {
        public int x;
        public int y;

        public Target(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}