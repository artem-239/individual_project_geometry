import java.awt.geom.Point2D;
import java.util.StringTokenizer;

public class Point extends Point2D {
    private double x;
    private double y;
    private int radius;

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

    public int getRadius() {
        return radius;
    }

    @Override
    public void setLocation(double x, double y) {
        this.x = x;
        this.y = y;
    }
}