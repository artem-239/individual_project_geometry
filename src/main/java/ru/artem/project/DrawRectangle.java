package ru.artem.project;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DrawRectangle extends JPanel {
    List<OurRectangle> rectangles = new ArrayList<>();;
    int width;
    int height;

    public DrawRectangle(int width, int height, List<OurRectangle> rectangles) {
        this.rectangles = rectangles;
        this.width = width;
        this.height = height;
        this.setBounds(0, 0, width, height);
//        this.setPreferredSize(new Dimension(width, height));
        setOpaque(false);
    }

    public void paintComponent(Graphics graphics) {
//    	super.paintComponent(graphics);
//    	System.out.println("Количество прямоугольников: " + rectangles.size());
        for (OurRectangle rectangle : rectangles) {
        	if (!rectangle.isRectangleExist()) {
        		System.out.println("Прямоугольник не существует: " + rectangle);
        		continue;
        	}
            List<Point> points = rectangle.getAllPoints();
            double x0 = Util.transformX(points.get(0).getX(), width);
            double x1 = Util.transformX(points.get(1).getX(), width);
            double x2 = Util.transformX(points.get(2).getX(), width);
            double x3 = Util.transformX(points.get(3).getX(), width);

            double y0 = Util.transformY(points.get(0).getY(), height);
            double y1 = Util.transformY(points.get(1).getY(), height);
            double y2 = Util.transformY(points.get(2).getY(), height);
            double y3 = Util.transformY(points.get(3).getY(), height);

            int[] xPoints = {(int) Math.round(x0),
                    (int) Math.round(x1),
                    (int) Math.round(x2),
                    (int) Math.round(x3)};
            int[] yPoints = {(int) Math.round(y0),
                    (int) Math.round(y1),
                    (int) Math.round(y2),
                    (int) Math.round(y3)};
            int numberOfPoints = 4;
            graphics.drawPolygon(xPoints, yPoints, numberOfPoints);
        }
//        System.out.println("нарисовал прямоугольники");
    }
}