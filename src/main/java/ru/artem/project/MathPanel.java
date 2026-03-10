package ru.artem.project;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class MathPanel extends JPanel {

    private int width;
    private int height;
    private List<OurRectangle> rectangles = new ArrayList<>();
    private List<Point> onePairCoordinate = new ArrayList<>();
    
    public MathPanel(int width, int height) {
    	this.width = width;
    	this.height = height;
    	this.setPreferredSize(new Dimension(width, height));
    	this.setBackground(Color.WHITE);
    	setLayout(null);
    }
    
    public void addRectangle(OurRectangle rectangle) {
    	rectangles.add(rectangle);
    	//Добавить на панель
    	refresh();
    	paint();
    }
    
    public void addRectangles(List<OurRectangle> rectangles) {
    	this.rectangles.addAll(rectangles);
    	//Добавить на панель
    	refresh();
    	paint();
    }
    
    public void deleteRectangle(OurRectangle rectangle) {
    	rectangles.remove(rectangle);
    	refresh();
    	paint();
    	
    }
    
    public void deleteAllRectangles() {
    	rectangles.clear();
    	refresh();
    	paint();
    	
    }
    
    public void addPoint(Point point) {
    	onePairCoordinate.add(point);
    	//Добавить на панель
    	refresh();
    	paint();
    	
    }
    
    public void addPoints(List<Point> points) {
    	onePairCoordinate.addAll(points);
    	//Добавить на панель
    	refresh();
    	paint();
    }
    
    public void deletePoint(Point point) {
    	onePairCoordinate.remove(point);
    	//Добавить на панель
    	refresh();
    	paint();
    }
    
    public void deleteAllPoints() {
    	onePairCoordinate.clear();
    	refresh();
    	paint();
    	
    }
    
    private void refresh() {
    	this.removeAll();
        this.revalidate();
        this.repaint();
    }

    public JPanel paint() {
        CoordinateSystem coordinateSystem = new CoordinateSystem(width, height);
        System.out.println("создал систему координат");
        DrawRectangle drawRectangle = new DrawRectangle(width, height, rectangles);
        System.out.println("Количество прямоугольников в MathPanel: " + rectangles.size()
        		+ "; hash: " + this.hashCode());
        System.out.println("создал прямоугольники");
        DrawLine drawLine = new DrawLine(onePairCoordinate, width, height);
        System.out.println("создал точки");
        
        this.add(drawLine);
        this.add(drawRectangle);
        this.add(coordinateSystem);
        
//        this.setVisible(true);
        
        return this;
    }
    
    public double makeCalculation() {
    	double maxLength = 0;
        OurRectangle curRect = null;
        Point curPoint = null;

        for (OurRectangle rectangle : rectangles){
        	System.out.println(">>>>>>>>>>>>>Проверяем прямоугольник: " + rectangle);
            for (Point point : onePairCoordinate){
            	System.out.println(">>>>>>>>>>>>>Проверяем точку: " + point);
                CalculateLength calculateLength = new CalculateLength(point, rectangle);
                double curLength = calculateLength.getLength(point, rectangle);
               if (curLength > maxLength) {
                   maxLength = curLength;
                   curRect = rectangle;
                   curPoint = point;

               }
            }
        }
        System.out.println(">>>>>>>>>>>>>Найденый прямоугольник: " + curRect);
        System.out.println(">>>>>>>>>>>>>Найденая точка: " + curPoint);
        if (curRect != null){
            CalculateLength calculateLength = new CalculateLength(curPoint, curRect);
            System.out.println(curRect.toString());
            System.out.println("maxLength = " + maxLength);
            List<Point> points = calculateLength.getIntersectionPoints(curPoint, curRect);
            Point point1 = points.get(0);
            Point point2 = points.get(1);
            System.out.println(point1.getX() + " " + point1.getY());
            System.out.println(point2.getX() + " " + point2.getY());

            DrawMaxLine drawMaxLine = new DrawMaxLine(calculateLength.getIntersectionPoints(curPoint, curRect), width, height);
            System.out.println(curPoint.getX() + " " + curPoint.getY());
            DrawDot drawDot = new DrawDot(curPoint, width, height);
            
            refresh();
            paint();
            this.add(drawDot);
            this.add(drawMaxLine);
            this.setVisible(true);
        }
        else {
            System.out.println("нет пересечений");
        }
        return maxLength;
    }
}




