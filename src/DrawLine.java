import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DrawLine extends JPanel {
    int width;
    int height;

    List<Point> onePairCoordinate = new ArrayList<>();

    public DrawLine(List<Point> listOfCoordinates, int width, int height) {
        this.onePairCoordinate = listOfCoordinates;
        this.width = width;
        this.height = height;
    }

    public void paintComponent(Graphics graphics) {
        graphics.setColor(Color.RED);
        for (Point onePairOfCoordinate : onePairCoordinate) {
            graphics.drawLine(width / 2, height / 2, (int) Math.round(Util.transformX(onePairOfCoordinate.getX(), width)), (int) Math.round(Util.transformY(onePairOfCoordinate.getY(), height)));
            graphics.fillOval((int) Math.round(Util.transformX(onePairOfCoordinate.getX(), width)), (int) Math.round(Util.transformY(onePairOfCoordinate.getY(), height)), 4, 4);
        }
    }
}