import javax.swing.*;

public class CoordinateStep extends JPanel {
    private JTextField coordinateStepX;
    private JTextField coordinateStepY;
    int width;
    int height;

    CoordinateStep(int width, int height){
        coordinateStepX = new JTextField(3);
        coordinateStepX.setText("100");
        coordinateStepX.setBounds(width / 2 + 100, height / 2, 10, 10);

        coordinateStepY = new JTextField(3);
        coordinateStepY.setText("100");
        coordinateStepY.setBounds(width / 2, height / 2 - 100, 10, 10);

        this.width = width;
        this.height = height;
    }
}
