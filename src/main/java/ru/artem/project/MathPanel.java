package ru.artem.project;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class MathPanel extends JPanel {

    private int width;
    private int height;
    private ArrayList<OurRectangle> rectangles = new ArrayList<>();
    private ArrayList<Point> onePairCoordinate = new ArrayList<>();
    private List<Point> mouseRectanglePoints = new ArrayList<>();
    
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
    
    public ArrayList<OurRectangle> getAllRectangles() {
    	return rectangles;
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
    
    public ArrayList<Point> getAllPoints() {
    	return onePairCoordinate;
    }
    
    public void deleteAllPoints() {
    	onePairCoordinate.clear();
    	refresh();
    	paint();
    	
    }
    
    public void addMouseRectanglePoints(List<Point> points) {
    	mouseRectanglePoints.addAll(points);
    	//Добавить на панель
    	refresh();
    	paint();
    }
    
    public void addMouseRectanglePoint(Point point) {
    	mouseRectanglePoints.add(point);
    	//Добавить на панель
    	refresh();
    	paint();
    	
    }
    
    public void deleteMouseRectanglePoint(Point point) {
    	mouseRectanglePoints.remove(point);
    	//Добавить на панель
    	refresh();
    	paint();
    }
    
    public void deleteAllMouseRectanglePoints() {
    	mouseRectanglePoints.clear();
    	refresh();
    	paint();
    	
    }
    
    private void refresh() {
    	this.removeAll();
        this.revalidate();
        this.repaint();
    }

    public JPanel paint() {
    	System.out.println("Размер списка точек прямоугольников мыши: " + mouseRectanglePoints.size());
    	if (mouseRectanglePoints.size() == 3) {
        	//создаю прямоугольник 
    		OurRectangle or = new OurRectangle(mouseRectanglePoints.get(0), mouseRectanglePoints.get(1), mouseRectanglePoints.get(2));
    		if (or.isRectangleExist()) {
    			rectangles.add(or);
    		}
    		mouseRectanglePoints.clear();
        }
        CoordinateSystem coordinateSystem = new CoordinateSystem(width, height);
//        System.out.println("создал систему координат");
        DrawRectangle drawRectangle = new DrawRectangle(width, height, rectangles);
//        System.out.println("Количество прямоугольников в MathPanel: " + rectangles.size()
//        		+ "; hash: " + this.hashCode());
//        System.out.println("создал прямоугольники");
        DrawLine drawLine = new DrawLine(onePairCoordinate, width, height);
//        System.out.println("создал точки");
        
        this.add(drawLine);
        this.add(drawRectangle);
        this.add(coordinateSystem);
        
        for (Point mp : mouseRectanglePoints) {
        	MousePoint mpG = new MousePoint(mp, width, height);
        	System.out.println("создал точку прямоугольника мыши");
        	this.add(mpG);
        }
        
//        this.setVisible(true);
        addMouseLabel();
        return this;
    }
    
    private void addMouseLabel() {
    	JLabel mouseLabel = new JLabel();
        mouseLabel.setOpaque(true);
        mouseLabel.setBackground(new Color(255,255,200));
        mouseLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        mouseLabel.setSize(80,20);
        
        this.add(mouseLabel);
        
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
            	int x = e.getX();
                int y = e.getY();
                
                int realX = Util.backTransformX(x, width);
                int realY = Util.backTransformY(y, height);

                mouseLabel.setText(realX + ", " + realY);

                // небольшое смещение от курсора
                mouseLabel.setLocation(x + 10, y + 10);
            }
        });
    }
    
    public double makeCalculation() {
    	double maxLength = 0;
        OurRectangle curRect = null;
        Point curPoint = null;

        for (OurRectangle rectangle : rectangles){
        	System.out.println(">>>>>>>>>>>>>Проверяем прямоугольник: " + rectangle);
            for (Point point : onePairCoordinate){
            	System.out.println(">>>>>>>>>>>>>Проверяем точку: " + point);
                CalculateLength calculateLength = new CalculateLength();
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
            CalculateLength calculateLength = new CalculateLength();
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




