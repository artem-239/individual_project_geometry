package ru.artem.project;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DrawDot extends JPanel {
    private Point point;
    private int width;
    private int height;


    public DrawDot(Point point, int width, int height) {
        this.point = point;
        this.width = width;
        this.height = height;
        this.setBounds(0, 0, width, height);
//      this.setPreferredSize(new Dimension(width, height));
        setOpaque(false);
    }

    public void paintComponent(Graphics graphics) {
        graphics.setColor(Color.BLACK);
        int pointSize = 12;
        graphics.fillOval((int) Math.round(Util.transformX(point.getX(), width)) - pointSize/2, 
            		(int) Math.round(Util.transformY(point.getY(), height)) - pointSize/2, pointSize, pointSize);
        System.out.println("нарисовал точку пересечения");
    }

}
