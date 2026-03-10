package ru.artem.project;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DrawMaxLine extends JPanel {

    List<Point> intersectionPoints = new ArrayList<>();
    int width;
    int height;

    public DrawMaxLine(List<Point> intersectionPoints, int width, int height) {
        this.intersectionPoints = intersectionPoints;
        this.width = width;
        this.height = height;
        this.setBounds(0, 0, width, height);
//      this.setPreferredSize(new Dimension(width, height));
        setOpaque(false);
    }

    public void paintComponent(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics;
        BasicStroke basicStroke = new BasicStroke(5f);

        graphics2D.setStroke(basicStroke);
        graphics2D.setColor(Color.BLACK);

        Point firstIntersectionPoint = intersectionPoints.get(0);
        Point secondIntersectionPoint = intersectionPoints.get(1);

        int firstIntersectionPointX = (int) Math.round(Util.transformX(firstIntersectionPoint.getX(), width));
        int firstIntersectionPointY = (int) Math.round(Util.transformY(firstIntersectionPoint.getY(), height));

        int secondIntersectionPointX = (int) Math.round(Util.transformX(secondIntersectionPoint.getX(), width));
        int secondIntersectionPointY = (int) Math.round(Util.transformY(secondIntersectionPoint.getY(), height));

        graphics2D.drawLine(firstIntersectionPointX, firstIntersectionPointY, secondIntersectionPointX, secondIntersectionPointY);
        System.out.println("нарисовал линию пересечения");
    }
}
