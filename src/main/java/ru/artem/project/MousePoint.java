package ru.artem.project;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.StringTokenizer;

import javax.swing.JPanel;

public class MousePoint extends JPanel {
	private int x;
	private int y;
	private int radius;

	public MousePoint(Point pairOfCoordinates, int width, int height) {
		this.x = (int) Util.transformX(pairOfCoordinates.getX(), width);
		this.y = (int) Util.transformY(pairOfCoordinates.getY(), height);
		
		System.out.println(">>>>>>>добавил точку мыши: " + x + ", " + y);
		this.setBounds(0, 0, width, height);
//      this.setPreferredSize(new Dimension(width, height));
		setOpaque(false);
	}

	public void setBeacon(int x, int y) {
		this.x = x;
		this.y = y;
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawPoint((Graphics2D) g, x, y);
//		System.out.println("нарисовал точки мыши");
		
	}

	private void drawPoint(Graphics2D g2, int x, int y) {

		g2.setColor(Color.RED);

		// центр
		g2.fillOval(x - 4, y - 4, 8, 8);

		// кольцо
		g2.drawOval(x - 12, y - 12, 24, 24);

		// крест
		g2.drawLine(x - 15, y, x + 15, y);
		g2.drawLine(x, y - 15, x, y + 15);
	}
}