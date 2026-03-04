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
    }

    public void paintComponent(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics;
        BasicStroke basicStroke = new BasicStroke(3.4f);

        graphics2D.setStroke(basicStroke);
        graphics2D.setColor(Color.BLACK);

        Point firstIntersectionPoint = intersectionPoints.get(0);
        Point secondIntersectionPoint = intersectionPoints.get(1);

        double firstIntersectionPointX = firstIntersectionPoint.getX();
        double firstIntersectionPointY = firstIntersectionPoint.getY();

        double secondIntersectionPointX = secondIntersectionPoint.getX();
        double secondIntersectionPointY = secondIntersectionPoint.getY();

        graphics2D.drawLine((int) Math.round(Util.transformX(firstIntersectionPointX, width)), (int) Math.round(Util.transformY(firstIntersectionPointY, height)), (int) Math.round(Util.transformX(secondIntersectionPointX, width)), (int) Math.round(Util.transformY(secondIntersectionPointY, height)));
    }
}
