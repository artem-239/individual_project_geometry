package ru.artem.project;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DrawLine extends JPanel {
	private int width;
    private int height;

    private List<Point> onePairCoordinate = new ArrayList<>();

    public DrawLine(List<Point> listOfCoordinates, int width, int height) {
        this.onePairCoordinate = listOfCoordinates;
        this.width = width;
        this.height = height;
        this.setBounds(0, 0, width, height);
//      this.setPreferredSize(new Dimension(width, height));
        setOpaque(false);
    }

    public void paintComponent(Graphics graphics) {
        graphics.setColor(Color.RED);
        for (Point onePairOfCoordinate : onePairCoordinate) {
        	int x = (int) Math.round(Util.transformX(onePairOfCoordinate.getX(), width));
        	int y = (int) Math.round(Util.transformY(onePairOfCoordinate.getY(), height));
//        	System.out.println("Рисую линию к X = " + x + ", Y = " + y);
        	int pointSize = 8;
            graphics.drawLine(width / 2, height / 2, x , y);
            graphics.fillOval(x - pointSize/2, y - pointSize/2, pointSize, pointSize);
        }
    }
}