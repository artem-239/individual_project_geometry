package ru.artem.project;
public class Util {

    public static double transformX(double inputX, int width) {
        double x = inputX + width / 2;
        return x;
    }

    public static double transformY(double inputY, int height) {
        double y = -1 * inputY + height / 2;
        return y;
    }
}
