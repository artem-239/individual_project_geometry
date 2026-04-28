package ru.artem.project;
import java.awt.geom.Point2D;
import java.util.StringTokenizer;

public class Point extends Point2D {
    private double x;
    private double y;

    public Point(String pairOfCoordinates) {
        //Пара координат X и Y через запятую
        StringTokenizer tokenizer = new StringTokenizer(pairOfCoordinates, ",");
        double x = java.lang.Double.parseDouble(tokenizer.nextToken());
        double y = java.lang.Double.parseDouble(tokenizer.nextToken());
        setLocation(x, y);
    }

    public Point(double x, double y) {
        setLocation(x, y);
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public void setLocation(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public String toString() {
        return String.valueOf((int) Math.round(getX())) + "," + String.valueOf((int) Math.round(getY()));
    }
    
    @Override
    public boolean equals(Object obj) {
    	if (obj instanceof Point) {
    		Point check = (Point) obj;
    		return this.getX() == check.getX() 
    				&& this.getY() == check.getY();
    	}
    	return super.equals(obj);
    }
}