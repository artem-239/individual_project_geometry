package ru.artem.project;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class OurRectangle {
    //Вершина 1
    private Point vertex1;
    //Вершина 2
    private Point vertex2;
    //Вершина 3
    private Point vertex3;
    //Вершина 4
    private Point vertex4;
    //буффер для смены вершин 1 и 2, в случае, если не выполнено условие обхода против часовой стрелки
    private Point vertexBuffer;
    //Точка, лежащая на прямой, проходящей через 2 противоположенные вершины к данным(точка)
    private Point point;
    //Длина векторы, построенного от точки до вершины 1 по х
    private double Vx;
    //Длина векторы, построенного от точки до вершины 1 по у
    private double Vy;
    //Длина стороны, образованной 1 и 2 вершиной
    private double lengthBetweenVertex;
    //Скалярное произведение векторов V и R
    private double dotProduct;
    //Длина стороны, образованной 1 и 2 вершиной по х(длина вектора R по х)
    private double lengthBetweenVertexX;
    //Длина стороны, образованной 1 и 2 вершиной по у(длина вектора R по у)
    private double lengthBetweenVertexY;
    //Параметр точки перпендикуляра от точки до прямой, проходящей через 1 и 2 вершины
    private double parameter;
    //Длина перпендикуляра от точки до стороны, образованной вершинами 1 и 2 по х
    private double perpendicularLengthX;
    //Длина перпендикуляра от точки до стороны, образованной вершинами 1 и 2 по у
    private double perpendicularLengthY;
    //Длина перпендикуляра от точки до стороны, образованной вершинами 1 и 2
    private double perpendicularLength;
    //Координата точки перпендикуляра на прямой, образованной вершинами 1 и 2 по х
    private double perpendicularX;
    //Координата точки перпендикуляра на прямой, образованной вершинами 1 и 2 по у
    private double perpendicularY;
    //Переменная для определения существования прямоугольника
    private boolean isRectangleExist = true;
    //Векторное произведение
    private double vectorProduct;

    public OurRectangle(Point vertex1, Point vertex2, Point point3) {
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
        this.point = point3;
        isRectangleExist = checkRectangleExist();
        System.out.println("Добавленный прямоугольник существует? " + isRectangleExist);
    }

    private boolean checkRectangleExist() {
        //Строим вспомогательный вектор V от точки до 1 вершины
        Vx = point.getX() - vertex1.getX();
        Vy = point.getY() - vertex1.getY();

        //Находим длину линии от 1 вершины до 2
        lengthBetweenVertexX = vertex2.getX() - vertex1.getX();
        lengthBetweenVertexY = vertex2.getY() - vertex1.getY();
        lengthBetweenVertex = Math.sqrt(Math.pow(lengthBetweenVertexX, 2) + Math.pow(lengthBetweenVertexY, 2));

        //ищем скалярное произведение
        dotProduct = (Vx * lengthBetweenVertexX) + (Vy * lengthBetweenVertexY);

        parameter = dotProduct / (Math.pow(lengthBetweenVertex, 2));

        //ищем точку перпендикуляра от точки до стороны
        perpendicularX = vertex1.getX() + lengthBetweenVertexX * parameter;
        perpendicularY = vertex1.getY() + lengthBetweenVertexY * parameter;

        //далее находим длину перпендикуляра
        perpendicularLengthX = perpendicularX - point.getX();
        perpendicularLengthY = perpendicularY - point.getY();
        perpendicularLength = Math.sqrt(Math.pow(perpendicularLengthX, 2) + Math.pow(perpendicularLengthY, 2));

        if (parameter >= 0 && parameter <= 1 && perpendicularLength > 0) {
            return true;
        }
        else {
            return false;
        }
    }

    public List<Point> getAllPoints() {
        List<Point> pointList = new ArrayList<Point>();
        vectorProduct = lengthBetweenVertexX * Vy - lengthBetweenVertexY * Vx;

        if (vectorProduct < 0) {
            //меняем вершины местами
            vertexBuffer = vertex1;
            vertex1 = vertex2;
            vertex2 = vertexBuffer;
            calculateAngle();
        }
        // опускаем перпендикуляр из T на R
        lengthBetweenVertex = Math.sqrt(Math.pow(lengthBetweenVertexX, 2) + Math.pow(lengthBetweenVertexY, 2));
        parameter = dotProduct / (Math.pow(lengthBetweenVertex, 2));
        perpendicularX = vertex1.getX() + (vertex2.getX() - vertex1.getX()) * parameter;
        perpendicularY = vertex1.getY() + (vertex2.getY() - vertex1.getY()) * parameter;

        // Строим вектор h
        double hx = point.getX() - perpendicularX;
        double hy = point.getY() - perpendicularY;

        // Находим точки P3 P4
        vertex3 = new Point(vertex2.getX() + hx, vertex2.getY() + hy);
        vertex4 = new Point(vertex1.getX() + hx, vertex1.getY() + hy);

        pointList.add(vertex1);
        pointList.add(vertex2);
        pointList.add(vertex3);
        pointList.add(vertex4);

        return pointList;
    }

    private void calculateAngle() {
        //строим направлющий вектор R
        lengthBetweenVertexX = vertex2.getX() - vertex1.getX();
        lengthBetweenVertexY = vertex2.getY() - vertex1.getY();
        //строим вспомогательный вектор V
        Vx = point.getX() - vertex1.getX();
        Vy = point.getY() - vertex1.getY();
        //определяем угол между векторами V и R
        dotProduct = lengthBetweenVertexX * Vx + Vy * lengthBetweenVertexY;
    }

    public Point getPoint(){
       return vertex1;
    }

    public boolean isRectangleExist() {
        return isRectangleExist;
    }

    public String toString() {
        return String.valueOf(vertex1.getX()) + "," + String.valueOf(vertex1.getY())
        	+ ";" + String.valueOf(vertex2.getX()) + "," + String.valueOf(vertex2.getY())
        	+ ";" + String.valueOf(point.getX()) + "," + String.valueOf(point.getY());
    }
    
    @Override
    public boolean equals(Object obj) {
    	if (obj instanceof OurRectangle) {
    		OurRectangle check = (OurRectangle) obj;
    		return this.vertex1.getX() == check.vertex1.getX() 
    				&& this.vertex1.getY() == check.vertex1.getY()
    				&& this.vertex2.getX() == check.vertex2.getX() 
    				&& this.vertex2.getY() == check.vertex2.getY()
    				&& this.point.getX() == check.point.getX() 
    	    		&& this.point.getY() == check.point.getY();
    	}
    	return super.equals(obj);
    }
}