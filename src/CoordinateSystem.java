import javax.swing.*;
import java.awt.*;

public class CoordinateSystem extends JPanel {
    int width;
    int height;

    CoordinateSystem(int width, int height){
        this.width = width;
        this.height = height;
    }

    public void paintComponent(Graphics graphics){
        graphics.drawLine(width / 2, height, width / 2, 0);
        graphics.drawLine(0, height / 2, width, height / 2);

        graphics.drawLine(width / 2, 0, width /2 - 15, 10);
        graphics.drawLine(width / 2, 0, width / 2 + 15, 10);

        graphics.drawLine(width, height / 2, width - 15, height / 2 - 10);
        graphics.drawLine(width, height / 2, width - 15, height / 2 + 10);

        graphics.drawLine(width / 2 + 100, height / 2 + 15, width / 2 + 100, height / 2 - 15);
        graphics.drawLine(width / 2 - 15, height / 2, width / 2 + 15, height / 2);
    }
}
