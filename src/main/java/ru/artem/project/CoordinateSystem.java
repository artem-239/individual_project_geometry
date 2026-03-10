package ru.artem.project;

import javax.swing.*;
import java.awt.*;

public class CoordinateSystem extends JPanel {
    int width;
    int height;

    CoordinateSystem(int width, int height){
        this.width = width;
        this.height = height;
        this.setBounds(0, 0, width, height);
//        this.setPreferredSize(new Dimension(width, height));
        setOpaque(false);
    }

    public void paintComponent(Graphics graphics){
    	super.paintComponent(graphics);
    	//ось Y
        graphics.drawLine(width / 2, height, width / 2, 0);
        //ось X
        graphics.drawLine(0, height / 2, width, height / 2);
        
        //Стрелка оси Y
        graphics.drawLine(width / 2, 0, width /2 - 15, 10);
        graphics.drawLine(width / 2, 0, width / 2 + 15, 10);
        //Стрелка оси X
        graphics.drawLine(width, height / 2, width - 15, height / 2 - 10);
        graphics.drawLine(width, height / 2, width - 15, height / 2 + 10);
        
        //Риски по оси X
        graphics.drawLine(width / 2 - 50, height / 2 + 5, width / 2 - 50, height / 2 - 5);
        graphics.drawLine(width / 2 - 100, height / 2 + 10, width / 2 - 100, height / 2 - 10);
        graphics.drawLine(width / 2 - 150, height / 2 + 5, width / 2 - 150, height / 2 - 5);
        graphics.drawLine(width / 2 - 200, height / 2 + 10, width / 2 - 200, height / 2 - 10);
        graphics.drawLine(width / 2 - 250, height / 2 + 5, width / 2 - 250, height / 2 - 5);
        graphics.drawLine(width / 2 - 300, height / 2 + 10, width / 2 - 300, height / 2 - 10);
        graphics.drawLine(width / 2 - 350, height / 2 + 5, width / 2 - 350, height / 2 - 5);
        graphics.drawLine(width / 2 + 50, height / 2 + 5, width / 2 + 50, height / 2 - 5);
        graphics.drawLine(width / 2 + 100, height / 2 + 10, width / 2 + 100, height / 2 - 10);
        graphics.drawLine(width / 2 + 150, height / 2 + 5, width / 2 + 150, height / 2 - 5);
        graphics.drawLine(width / 2 + 200, height / 2 + 10, width / 2 + 200, height / 2 - 10);
        graphics.drawLine(width / 2 + 250, height / 2 + 5, width / 2 + 250, height / 2 - 5);
        graphics.drawLine(width / 2 + 300, height / 2 + 10, width / 2 + 300, height / 2 - 10);
        graphics.drawLine(width / 2 + 350, height / 2 + 5, width / 2 + 350, height / 2 - 5);
        
        //Риски по оси Y
        graphics.drawLine(width / 2 - 5, height / 2 - 50, width / 2 + 5, height / 2 - 50);
        graphics.drawLine(width / 2 - 10, height / 2 - 100, width / 2 + 10, height / 2 - 100);
        graphics.drawLine(width / 2 - 5, height / 2 - 150, width / 2 + 5, height / 2 - 150);
        graphics.drawLine(width / 2 - 10, height / 2 - 200, width / 2 + 10, height / 2 - 200);
        graphics.drawLine(width / 2 - 5, height / 2 - 250, width / 2 + 5, height / 2 - 250);
        graphics.drawLine(width / 2 - 10, height / 2 - 300, width / 2 + 10, height / 2 - 300);
        graphics.drawLine(width / 2 - 5, height / 2 - 350, width / 2 + 5, height / 2 - 350);
        graphics.drawLine(width / 2 - 5, height / 2 + 50, width / 2 + 5, height / 2 + 50);
        graphics.drawLine(width / 2 - 10, height / 2 + 100, width / 2 + 10, height / 2 + 100);
        graphics.drawLine(width / 2 - 5, height / 2 + 150, width / 2 + 5, height / 2 + 150);
        graphics.drawLine(width / 2 - 10, height / 2 + 200, width / 2 + 10, height / 2 + 200);
        graphics.drawLine(width / 2 - 5, height / 2 + 250, width / 2 + 5, height / 2 + 250);
        graphics.drawLine(width / 2 - 10, height / 2 + 300, width / 2 + 10, height / 2 + 300);
        graphics.drawLine(width / 2 - 5, height / 2 + 350, width / 2 + 5, height / 2 + 350);
        
        
//        graphics.drawLine(width / 2 - 15, height / 2, width / 2 + 15, height / 2);
        
        System.out.println("нарисовал систему координат");
    }
}
