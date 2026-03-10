package ru.artem.project;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Application {

    private JFrame frame;
    private JPanel leftPanel;

    private JPanel rectanglesContainer;
    private JPanel pointsContainer;
    
    private MathPanel drawingArea;
    
    private final int FRAME_WIDTH = 1200;
    private final int FRAME_HEIGHT = 900;
    //Треть от общей ширины
    private final int LEFTPANEL_WIDTH = 400;
    private final int LEFTPANEL_HEIGHT = 900;
    private final int LEFTPANEL_RETURN_WIDTH = 350;
    private final int LEFTPANEL_RETURN_HEIGHT = 100;
    private final int LEFTPANEL_TRIANGLES_WIDTH = 350;
    private final int LEFTPANEL_TRIANGLES_HEIGHT = 400;
    private final int LEFTPANEL_POINTS_WIDTH = 350;
    private final int LEFTPANEL_POINTS_HEIGHT = 400;
    //Две трети от общей ширины
    private final int MATHPANEL_WIDTH = 800;
    private final int MATHPANEL_HEIGHT = 700;
    private final int MATHPANEL_BOTTOM_WIDTH = 800;
    private final int MATHPANEL_BOTTOM_HEIGHT = 200;
    
    private final int TEXTFIELDS_WIDTH = 60;
    private final int TEXTFIELDS_HEIGHT = 25;
    
    private List<OurRectangle> rectangles = new ArrayList<>();
    private List<Point> onePairCoordinate = new ArrayList<>();
    
    private JTextField resultField = new JTextField();
    
    private List<String> fileResults = new ArrayList<>();

    public Application() {

        frame = new JFrame("Нахождение максимальной длины отрезка");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setLayout(new BorderLayout());

        leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(LEFTPANEL_WIDTH, LEFTPANEL_HEIGHT));
        leftPanel.setLayout(new BorderLayout());

        JPanel rightPanel = createRightPanel();

        frame.add(leftPanel, BorderLayout.WEST);
        frame.add(rightPanel, BorderLayout.CENTER);

        showRadioSelection();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /* ---------------- добавляем правую панель ---------------- */
    private JPanel createRightPanel() {

        JPanel rightPanel = new JPanel(new BorderLayout());

        /* верхняя рабочая область - где рисуем объекты и систему координат */

        drawingArea = new MathPanel(MATHPANEL_WIDTH,MATHPANEL_HEIGHT);
        drawingArea.paint();
        

        rightPanel.add(drawingArea, BorderLayout.CENTER);

        /* нижняя область вычислений - кнопка Сделать вычисления и область вывода результата */

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setPreferredSize(new Dimension(MATHPANEL_BOTTOM_WIDTH, MATHPANEL_BOTTOM_HEIGHT));

        JButton calculateButton = new JButton("Сделать вычисления");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(calculateButton);
        
        JPanel resultsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        bottomPanel.add(buttonPanel, BorderLayout.NORTH);
        bottomPanel.add(resultsPanel, BorderLayout.SOUTH);
        
        buttonPanel.add(calculateButton);

        rightPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        calculateButton.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				double maxLength = drawingArea.makeCalculation();
	        	resultsPanel.removeAll();
	        	resultsPanel.revalidate();
	        	resultsPanel.repaint();
	        	resultField = new JTextField();
	        	resultField.setEditable(false);
	        	resultField.setText("Максимальная длина равна: " + maxLength);
	        	resultsPanel.add(resultField);
			}
		});

        return rightPanel;
    }

    /* ---------------- левая панель - управление и ввод данных ---------------- */

    private void showRadioSelection() {

        leftPanel.removeAll();

        JPanel radioPanel = new JPanel();
        radioPanel.setLayout(new BoxLayout(radioPanel, BoxLayout.Y_AXIS));

        JRadioButton loadFromFile = new JRadioButton("Загрузить из файла");
        JRadioButton keyboard = new JRadioButton("Ввести данные с клавиатуры");

        ButtonGroup group = new ButtonGroup();
        group.add(loadFromFile);
        group.add(keyboard);
        
        loadFromFile.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				showFileChooser();
			}
		});        
        keyboard.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				showKeyboardInput();
			}
		});

        radioPanel.add(loadFromFile);
        radioPanel.add(keyboard);

        leftPanel.add(radioPanel, BorderLayout.NORTH);

        refresh();
    }
    
    /* ----------------левая панель: Загрузка из файла ---------------- */
    private void showFileChooser() {

        leftPanel.removeAll();

        JPanel filePanel = new JPanel(new BorderLayout());

        JTextField fileField = new JTextField();
        fileField.setEditable(false);

        JButton chooseButton = new JButton("Выбрать файл");
        
        JPanel fileResultsPanel = new JPanel(new BorderLayout());
        fileResultsPanel.setLayout(new BoxLayout(fileResultsPanel, BoxLayout.Y_AXIS));
        
        chooseButton.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
	            if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
	                fileField.setText(chooser.getSelectedFile().getAbsolutePath());
	                dataFileInput(chooser.getSelectedFile().getAbsolutePath());
	                drawingArea.addPoints(onePairCoordinate);
	                System.out.println("Количество прямоугольников в Application: " + rectangles.size() 
	                		+ "; hash: " + drawingArea.hashCode());
	                drawingArea.addRectangles(rectangles);
	                fileResultsPanel.removeAll();
	                fileResultsPanel.revalidate();
	                fileResultsPanel.repaint();
	                for (String row : fileResults) {
	                	JLabel lbl = new JLabel(row);
	                	fileResultsPanel.add(lbl);
	                	System.out.println("::::::::::::::::: " + row);
	                }
	            }
			}
		});
        
        
        filePanel.add(fileField, BorderLayout.CENTER);
        filePanel.add(chooseButton, BorderLayout.EAST);
        
        leftPanel.add(createReturnPanel(), BorderLayout.NORTH);
        leftPanel.add(fileResultsPanel);
        leftPanel.add(filePanel, BorderLayout.SOUTH);

        refresh();
    }
    
    /* ---------- левая панель: панель ввода с клавиатуры ---------- */
    private void showKeyboardInput() {

        leftPanel.removeAll();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        leftPanel.add(createReturnPanel());
        leftPanel.add(createRectanglesPanel());
        leftPanel.add(createPointsPanel());

        refresh();
    }
    /* ---------- левая панель: выход в главное меню ---------- */
    
    private JPanel createReturnPanel() {
    	JPanel panel = new JPanel(new BorderLayout());
    	panel.setPreferredSize(new Dimension(LEFTPANEL_RETURN_WIDTH, LEFTPANEL_RETURN_HEIGHT));
        panel.setBorder(BorderFactory.createTitledBorder("Выход в основное меню"));
        JButton returnBtn = new JButton("Выйти в основное меню");
        returnBtn.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				backToMainMenu();
			}
		});
        panel.add(returnBtn, BorderLayout.NORTH);
    	return panel;
    }
    
    private void backToMainMenu() {
    	drawingArea.deleteAllRectangles();
    	drawingArea.deleteAllPoints();
    	resultField.setText(null);
    	showRadioSelection();
    }

    /* ---------- левая панель: создание прямоугольников ---------- */

    private JPanel createRectanglesPanel() {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(LEFTPANEL_TRIANGLES_WIDTH, LEFTPANEL_TRIANGLES_HEIGHT));
        panel.setBorder(BorderFactory.createTitledBorder("Введите параметры прямоугольников"));

        rectanglesContainer = new JPanel();
        rectanglesContainer.setLayout(new BoxLayout(rectanglesContainer, BoxLayout.Y_AXIS));

        JScrollPane scroll = new JScrollPane(rectanglesContainer);
        panel.add(scroll, BorderLayout.CENTER);

        JButton addRow = new JButton("Добавить прямоугольник");
        addRow.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				addRectangleRow();
			}
		});

        panel.add(addRow, BorderLayout.SOUTH);

        addRectangleRow();
        addRectangleRow();
        addRectangleRow();

        return panel;
    }

    /* ---------- левая панель: точки ---------- */

    private JPanel createPointsPanel() {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(LEFTPANEL_POINTS_WIDTH, LEFTPANEL_POINTS_HEIGHT));
        panel.setBorder(BorderFactory.createTitledBorder("Введите точки"));

        pointsContainer = new JPanel();
        pointsContainer.setLayout(new BoxLayout(pointsContainer, BoxLayout.Y_AXIS));

        JScrollPane scroll = new JScrollPane(pointsContainer);
        panel.add(scroll, BorderLayout.CENTER);
        JButton addRow = new JButton("Добавить точку");
        addRow.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				addPointRow();
			}
		});

        panel.add(addRow, BorderLayout.SOUTH);

        addPointRow();
        addPointRow();
        addPointRow();

        return panel;
    }

    /* ---------- лева панель: ряды в прямоугольниках ---------- */

    private void addRectangleRow() {

        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT));

//        JLabel label = new JLabel("Прямоугольник:   ");

        JLabel l1 = new JLabel("Вершина 1");
        JTextField f1 = createField();
        JLabel l2 = new JLabel("Вершина 2");
        JTextField f2 = createField();
        JLabel l3 = new JLabel("Точка");
        JTextField f3 = createField();

        JButton paint = new JButton(new BrushIcon());
        JButton delete = new JButton(new TrashIcon());
        
        paint.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO Обработка исключения - когда значения не соответствуют
	            String v1 = f1.getText();
	            String v2 = f2.getText();
	            String v3 = f3.getText();
	            Point vertex1 = new Point(v1);
	            Point vertex2 = new Point(v2);
	            Point point3 = new Point(v3);
	            OurRectangle rectangle = new OurRectangle(vertex1, vertex2, point3);
	            if (rectangle.isRectangleExist()) {
	            	drawingArea.addRectangle(rectangle);
	            }
	            System.out.println("Прямоугольник:");
	            System.out.println("  Вершина1 = " + v1);
	            System.out.println("  Вершина2 = " + v2);
	            System.out.println("  Точка = " + v3);
	            System.out.println("-----------------------");
	            
	            f1.setEditable(false);
	            f2.setEditable(false);
	            f3.setEditable(false);
	            paint.setEnabled(false);
			}
		});
        
        delete.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
	        		String v1 = f1.getText();
	                String v2 = f2.getText();
	                String v3 = f3.getText();
	                Point vertex1 = new Point(v1);
	                Point vertex2 = new Point(v2);
	                Point point3 = new Point(v3);
	                OurRectangle rectangle = new OurRectangle(vertex1, vertex2, point3);
	                drawingArea.deleteRectangle(rectangle);
				} catch (Exception e2) {
					System.out.println(e2);
				} finally {
					rectanglesContainer.remove(row);
					refreshContainer(rectanglesContainer);
				}
			}
		});

//        row.add(label);
        row.add(l1);
        row.add(f1);
        row.add(l2);
        row.add(f2);
        row.add(l3);
        row.add(f3);
        row.add(paint);
        row.add(delete);

        rectanglesContainer.add(row);

        refreshContainer(rectanglesContainer);
    }

    /* ---------- левая панель: ряды точек ---------- */
    private void addPointRow() {

        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JLabel label = new JLabel("Точка");

        JTextField field = createField();

        JButton paint = new JButton(new BrushIcon());
        JButton delete = new JButton(new TrashIcon());
        
        paint.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				String value = field.getText();
	            Point point = new Point(value);
	            drawingArea.addPoint(point);
	            System.out.println("Точка:");
	            System.out.println("  Координаты  = " + value);
	            System.out.println("-----------------------");
	            field.setEditable(false);
	            paint.setEnabled(false);
			}
		});
        
        delete.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				String value = field.getText();
	            Point point = new Point(value);
	            drawingArea.deletePoint(point);
	            pointsContainer.remove(row);
	            refreshContainer(pointsContainer);
			}
		});

        row.add(label);
        row.add(field);
        row.add(paint);
        row.add(delete);

        pointsContainer.add(row);

        refreshContainer(pointsContainer);
    }

    private JTextField createField() {
        JTextField f = new JTextField();
        f.setPreferredSize(new Dimension(TEXTFIELDS_WIDTH, TEXTFIELDS_HEIGHT));
        return f;
    }

    private void refreshContainer(JPanel panel) {
        panel.revalidate();
        panel.repaint();
    }

    private void refresh() {
        leftPanel.revalidate();
        leftPanel.repaint();
    }
    
    private void dataFileInput(String filePath) {
        try {
        	fileResults.clear();
            Scanner scanner = new Scanner(new File(filePath));
            boolean pointsHandle = false;
            while (scanner.hasNextLine()) {
            	String coordinates = scanner.nextLine();
            	String curRow = coordinates;
            	if (coordinates.equals("#####")) {
            		//Начало обработки точек
            		pointsHandle = true;
            		continue;
            	}
            	if (!pointsHandle) {
            		OurRectangle rectangle = createRectangle(coordinates);
                    boolean isRectangleExist = rectangle.isRectangleExist();
                    if (isRectangleExist) {
                        rectangles.add(rectangle);
                        System.out.println("Добавили прямоугольник: " + coordinates);
                        curRow = "Прямоугольник: " + curRow;
                    } else {
                        System.out.println("Такой прямоугольник не нарисовать: " + coordinates);
                        curRow = "Прямоугольник: " + curRow + "; прямоугольника не существует";
                    }
            	} else {
                    Point point = createPoint(coordinates);
                    onePairCoordinate.add(point);
                    curRow = "Точка: " + curRow;
            	}
            	fileResults.add(curRow);
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    private OurRectangle createRectangle(String coordinates) {
        StringTokenizer tokenizer = new StringTokenizer(coordinates, ";");
        Point vertex1 = new Point(tokenizer.nextToken());
        Point vertex2 = new Point(tokenizer.nextToken());
        Point point3 = new Point(tokenizer.nextToken());
        OurRectangle rectangle = new OurRectangle(vertex1, vertex2, point3);
        return rectangle;
    }
    
    private Point createPoint(String coordinates) {
        StringTokenizer tokenizer = new StringTokenizer(coordinates, ",");
        int x = Integer.parseInt(tokenizer.nextToken());
        int y = Integer.parseInt(tokenizer.nextToken());
        Point point = new Point(x, y);
        return point;
    }

    public static void main(String[] args) {
    	new Application();
    }
}

/* -------- Иконки -------- */

class TrashIcon implements Icon {

    public int getIconWidth() { return 16; }
    public int getIconHeight() { return 16; }

    public void paintIcon(Component c, Graphics g, int x, int y) {

        g.drawRect(x+3,y+5,10,8);
        g.drawLine(x+2,y+5,x+14,y+5);
        g.drawLine(x+6,y+2,x+10,y+2);

        g.drawLine(x+6,y+7,x+6,y+11);
        g.drawLine(x+8,y+7,x+8,y+11);
        g.drawLine(x+10,y+7,x+10,y+11);
    }
}

class BrushIcon implements Icon {

    public int getIconWidth() { return 16; }
    public int getIconHeight() { return 16; }

    public void paintIcon(Component c, Graphics g, int x, int y) {

        g.drawLine(x+3,y+12,x+11,y+4);
        g.setColor(Color.ORANGE);
        g.fillOval(x+10,y+2,4,4);
    }
}