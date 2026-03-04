import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class OurRectangle {
    private Point vertex1;
    private Point vertex2;
    private Point vertex3;
    private Point vertex4;
    private Point vertexBuffer;
    private Point point;
    private double Vx;
    private double Vy;
    private double lengthBetweenVertex;
    private double dotProduct;
    private double lengthBetweenVertexX;
    private double lengthBetweenVertexY;
    private double parameter;
    private double perpendicularLengthX;
    private double perpendicularLengthY;
    private double perpendicularLength;
    private double perpendicularX;
    private double perpendicularY;
    private boolean isRectangleExist = true;
    private double vectorProduct;

    public OurRectangle(Point vertex1, Point vertex2, Point point3) {
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
        this.point = point3;
        isRectangleExist = checkRectangleExist();
    }

    private boolean checkRectangleExist() {
        Vx = point.getX() - vertex1.getX();
        Vy = point.getY() - vertex1.getY();

        lengthBetweenVertexX = vertex2.getX() - vertex1.getX();
        lengthBetweenVertexY = vertex2.getY() - vertex1.getY();
        lengthBetweenVertex = Math.sqrt(Math.pow(lengthBetweenVertexX, 2) + Math.pow(lengthBetweenVertexY, 2));

        dotProduct = (Vx * lengthBetweenVertexX) + (Vy * lengthBetweenVertexY);

        parameter = dotProduct / (Math.pow(lengthBetweenVertex, 2));

        perpendicularX = vertex1.getX() + (vertex2.getX() - vertex1.getX()) * parameter;
        perpendicularY = vertex1.getY() + (vertex2.getY() - vertex1.getY()) * parameter;
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
        //1 строим направлющий вектор R
        lengthBetweenVertexX = vertex2.getX() - vertex1.getX();
        lengthBetweenVertexY = vertex2.getY() - vertex1.getY();
        //2 строим вспомогательный вектор V
        Vx = point.getX() - vertex1.getX();
        Vy = point.getY() - vertex1.getY();
        //3 определяем угол между векторами V и R
        dotProduct = lengthBetweenVertexX * Vx + Vy * lengthBetweenVertexY;
    }

   public double getHeight() {
        perpendicularX = vertex1.getX() + (vertex2.getX() - vertex1.getX()) * parameter;
        perpendicularY = vertex1.getY() + (vertex2.getY() - vertex1.getY()) * parameter;
        perpendicularLengthX = perpendicularX - point.getX();
        perpendicularLengthY = perpendicularY - point.getY();
        perpendicularLength = Math.sqrt(Math.pow(perpendicularLengthX, 2) + Math.pow(perpendicularLengthY, 2));
        if (lengthBetweenVertex > perpendicularLength){
            return lengthBetweenVertex;
        }
        else {
            return perpendicularLength;
        }
    }

    public double getWidth() {
        perpendicularX = vertex1.getX() + (vertex2.getX() - vertex1.getX()) * parameter;
        perpendicularY = vertex1.getY() + (vertex2.getY() - vertex1.getY()) * parameter;
        perpendicularLengthX = perpendicularX - point.getX();
        perpendicularLengthY = perpendicularY - point.getY();
        perpendicularLength = Math.sqrt(Math.pow(perpendicularLengthX, 2) + Math.pow(perpendicularLengthY, 2));
        if (lengthBetweenVertex < perpendicularLength){
            return lengthBetweenVertex;
        }
        else {
            return perpendicularLength;
        }
    }

    public Point getPoint(){
       return vertex1;
    }

    public boolean isRectangleExist() {
        return isRectangleExist;
    }

    public String toString() {
        return String.valueOf(vertex1.getX()) + ";" + String.valueOf(vertex1.getY()) + ";" + String.valueOf(vertex2.getX()) + ";" + String.valueOf(vertex2.getY()) + ";" + String.valueOf(point.getX()) + ";" + String.valueOf(point.getY());
    }
}
