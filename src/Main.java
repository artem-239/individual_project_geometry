import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import javax.swing.*;

public class Main {

    private static int SCREEN_WIDTH = 1600;
    private static int SCREEN_HEIGHT = 900;
    private static Point startingPoint = new Point(0,0);

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("если хотите ввести данные с файла, введите 'файл', если хотите с консоли, введите 'консоль'");
        List<OurRectangle> rectangles = new ArrayList<>();
        List<Point> onePairCoordinate = new ArrayList<>();

        String dataEntrySelection = input.nextLine();
        if (dataEntrySelection.equals("консоль")){
            System.out.println("если хотите ввести координаты точек линии, введите 'точки', если хотите ввести координаты точек прямоугольника, введите 'прямоугольники', если хотите выйти из ввода введите 'exit'");
            String inputObject = input.nextLine();
            if (inputObject.equals("точки")){
                lineConsoleInput(onePairCoordinate, input);
                System.out.println("введите координаты прямоугольников");
                rectangleConsoleInput(rectangles, input);
            }
            if (inputObject.equals("прямоугольники")){
               rectangleConsoleInput(rectangles, input);
               System.out.println("введите координаты точек");
              lineConsoleInput(onePairCoordinate, input);
            }
        }
        if (dataEntrySelection.equals("файл")){
            rectangleFileInput(rectangles);
            System.out.println("Файл с прямоугольниками успешно прочитан");
            lineFileInput(onePairCoordinate);
            System.out.println("Файл с линиями успешно прочитан");
        }

        JFrame mainFrame = new JFrame();
        mainFrame.setBackground(Color.gray);
        mainFrame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        DrawRectangle drawRectangle = new DrawRectangle(SCREEN_WIDTH, SCREEN_HEIGHT, rectangles);
        mainFrame.add(drawRectangle);
        mainFrame.setVisible(true);

        DrawLine drawLine = new DrawLine(onePairCoordinate, SCREEN_WIDTH, SCREEN_HEIGHT);
        mainFrame.add(drawLine);
        mainFrame.setVisible(true);

        CoordinateSystem coordinateSystem = new CoordinateSystem(SCREEN_WIDTH, SCREEN_HEIGHT);
        mainFrame.add(coordinateSystem);
        mainFrame.setVisible(true);

        JTextField coordinateStepX = new JTextField("100", 3);
        coordinateStepX.setBounds(SCREEN_WIDTH / 2 + 100, SCREEN_HEIGHT / 2, 23, 23);

        JTextField coordinateStepY = new JTextField("100", 3);
        coordinateStepY.setBounds(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2 - 100, 23, 23);

        mainFrame.add(coordinateStepX);
        mainFrame.add(coordinateStepY);

        double maxLength = 0;
        OurRectangle curRect = null;
        Point curPoint = null;

        for (OurRectangle rectangle : rectangles){
            for (Point point : onePairCoordinate){
                CalculateLength calculateLength = new CalculateLength(point, rectangle);
                double curLength = calculateLength.getLength(point, rectangle);
               if (curLength > maxLength) {
                   maxLength = curLength;
                   curRect = rectangle;
                   curPoint = point;

               }
            }
        }
        if (curRect != null){
            CalculateLength calculateLength = new CalculateLength(curPoint, curRect);
            System.out.println(curRect.toString());
            System.out.println(maxLength);
            List<Point> points = calculateLength.getIntersectionPoints(curPoint, curRect);
            Point point1 = points.get(0);
            Point point2 = points.get(1);
            System.out.println(point1.getX() + " " + point1.getY());
            System.out.println(point2.getX() + " " + point2.getY());

            DrawMaxLine drawMaxLine = new DrawMaxLine(calculateLength.getIntersectionPoints(curPoint, curRect), SCREEN_WIDTH, SCREEN_HEIGHT);
            mainFrame.add(drawMaxLine);
            mainFrame.setVisible(true);


            System.out.println(curPoint.getX() + " " + curPoint.getY());
            DrawDot drawDot = new DrawDot(curPoint, SCREEN_WIDTH, SCREEN_HEIGHT);
            mainFrame.add(drawDot);
            mainFrame.setVisible(true);
        }
        else {
            System.out.println("нет пересечений");
        }

    }

    private static void rectangleFileInput(List<OurRectangle> rectangles) {
        try {
            Scanner scanner = new Scanner(new File("src/resources/rectangles"));
            while (scanner.hasNextLine()) {
                String coordinates = scanner.nextLine();
                OurRectangle rectangle = createRectangle(coordinates);
                boolean isRectangleExist = rectangle.isRectangleExist();
                if (isRectangleExist) {
                    rectangles.add(rectangle);
                } else {
                    System.out.println("Такой прямоугольник не нарисовать: " + coordinates);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void rectangleConsoleInput(List<OurRectangle> rectangles, Scanner coordinateInput) {
        System.out.println("Последовательно введите координаты двух вершин и точки. Пары координат вводить через запятую, разделитель между парами точка с запятой. " +
                "Чтобы ввести следующий прямоугольник нажмите Enter, для завершения ввода введите exit, затем нажмите Enter.");
        //Ввод из прямоугольников консоли

        while (true) {
            String coordinates = coordinateInput.nextLine();
            if (coordinates.equals("exit")) {
                break;
            }

            OurRectangle rectangle = createRectangle(coordinates);
            boolean isRectangleExist = rectangle.isRectangleExist();
            if (isRectangleExist) {
                rectangles.add(rectangle);
            } else {
                System.out.println("Такой прямоугольник не нарисовать: " + coordinates);
            }
        }
    }

    private static OurRectangle createRectangle(String coordinates) {
        StringTokenizer tokenizer = new StringTokenizer(coordinates, ";");
        Point vertex1 = new Point(tokenizer.nextToken());
        Point vertex2 = new Point(tokenizer.nextToken());
        Point point3 = new Point(tokenizer.nextToken());
        OurRectangle rectangle = new OurRectangle(vertex1, vertex2, point3);
        return rectangle;
    }
    private static void lineFileInput(List<Point> points) {
        try {
            Scanner scanner = new Scanner(new File("src/resources/lines"));
            while (scanner.hasNextLine()) {
                String coordinates = scanner.nextLine();
                Point point = createPoint(coordinates);
                points.add(point);
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void lineConsoleInput(List<Point> onePairCoordinate, Scanner coordinateInput) {
        System.out.println("Последовательно введите координаты. Вводите координаты точки через запятую" +
                "Чтобы ввести следующую точку нажмите Enter, для завершения ввода введите exit, затем нажмите Enter.");
        //Ввод из прямоугольников консоли

        while (true) {
            String coordinates = coordinateInput.nextLine();
            if (coordinates.equals("exit")) {
                break;
            }
            onePairCoordinate.add(createPoint(coordinates));
        }
    }

    private static Point createPoint(String coordinates) {
        StringTokenizer tokenizer = new StringTokenizer(coordinates, ",");
        int x = Integer.parseInt(tokenizer.nextToken());
        int y = Integer.parseInt(tokenizer.nextToken());
        Point point = new Point(x, y);
        return point;
    }
}




