import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DrawDot extends JPanel {
    Point point;
    int width;
    int height;


    public DrawDot(Point point, int width, int height) {
        this.point = point;
        this.width = width;
        this.height = height;
    }

    public void paintComponent(Graphics graphics) {
        graphics.setColor(Color.BLACK);
            graphics.fillOval((int) Math.round(Util.transformX(point.getX(), width)), (int) Math.round(Util.transformY(point.getY(), height)), 6, 6);
    }



}
