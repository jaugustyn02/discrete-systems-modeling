package org.example;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.event.MouseInputListener;

public class Board extends JComponent implements MouseInputListener, ComponentListener {
	private static final long serialVersionUID = 1L;
	private Point[][] points;
//	private int size = 10;
	private int size = 16;
	public int editType=0;


	public Board(int length, int height) {
		initialize(length, height);
		addMouseListener(this);
		addComponentListener(this);
		addMouseMotionListener(this);
		setBackground(Color.WHITE);
		setOpaque(true);
	}

	public void iteration() {
		Point point;

		for(int lane = 0; lane < 3; lane++) {
			for (int x = 0; x < points.length; ++x) {
				points[x][lane].hasChangedLine = false;
			}
		}

		for(int lane = 0; lane < 3; lane++) {

			for( int x = 0; x < points.length; ++x){
				point = points[x][lane];
				if (!point.isCar() && !point.hasChangedLine)
					continue;
				boolean T1 = getDistanceToNextCar(point, x, lane) < Point.l_gap;
				if(T1){
					if (lane > 0) {
						boolean T2 = getDistanceToNextCarOnSideLine(x, lane-1) > Point.l_lead;
						boolean T3 = getDistanceToPreviousCarOnSideLine(x, lane-1) > Point.l_back;
						if (T2 && T3) {
							point.changeLane(points[x][lane-1]);
							continue;
						}
					}
					if (lane < 2) {
						boolean T2 = getDistanceToNextCarOnSideLine(x, lane+1) > Point.l_lead;
						boolean T3 = getDistanceToPreviousCarOnSideLine(x, lane+1) > Point.l_back;
						if (T2 && T3) {
							point.changeLane(points[x][lane+1]);
							point.hasChangedLine = true;
						}
					}
				}
			}

			for (int x = 0; x < points.length; ++x) {
				point = points[x][lane];
				point.accelerate();
				point.slowDown(getDistanceToNextCar(point, x, lane));
				point.randomSlowDown();
				point.hasMoved = false;
			}

			for (int x = 0; x < points.length; ++x) {
				point = points[x][lane];
				point.moveCar(points[(x + point.getVelocity()) % points.length][lane], x + point.getVelocity() >= points.length);
			}

			points[0][lane].randomAppearance();
		}
		this.repaint();
	}

	private int getDistanceToNextCar(Point tmp, int x, int y){
		if(!tmp.isCar()) return 0;
		for(int i = 1; i <= Math.max(Point.maxVelocity, Point.l_gap); i++){
			if(points[(x+i)%points.length][y].isCar())
				return i-1;
		}
		return Math.max(Point.maxVelocity, Point.l_gap);
	}

	private int getDistanceToNextCarOnSideLine(int x, int line){
		for(int i = 0; i <= Point.l_lead+1; i++){
			if(points[(x+i)%points.length][line].isCar())
				return i-1;
		}
		return Point.l_lead+1;
	}

	private int getDistanceToPreviousCarOnSideLine(int x, int y){
		for(int i = 0; i <= Point.l_back+1; i++){
			if(points[Math.floorMod(x-i, points.length)][y].isCar())
				return i-1;
		}
		return Point.l_back+1;
	}

	public void clear() {
		for (int x = 0; x < points.length; ++x)
			for (int y = 0; y < points[x].length; ++y) {
				points[x][y].clear();
			}
		this.repaint();
	}

	private void initialize(int length, int height) {
		points = new Point[length][height];

		for (int x = 0; x < points.length; ++x)
			for (int lane = 0; lane < points[x].length; ++lane) {
				points[x][lane] = new Point(lane);
			}
	}

	protected void paintComponent(Graphics g) {
		if (isOpaque()) {
			g.setColor(getBackground());
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
		}
		g.setColor(Color.GRAY);
		drawNetting(g, size);
	}

	private void drawNetting(Graphics g, int gridSpace) {
		Insets insets = getInsets();
		int firstX = insets.left;
		int firstY = insets.top;
		int lastX = this.getWidth() - insets.right;
		int lastY = this.getHeight() - insets.bottom;


		int y = firstY;
		while (y < lastY) {
			g.drawLine(firstX, y, lastX, y);
			y += gridSpace;
		}

		g.setColor(new Color(0, 0, 0, 0.8f));
		int x = firstX;
		while (x < lastX) {
			g.drawLine(x, firstY, x, lastY);
			x += gridSpace;
		}

		for (x = 0; x < points.length; ++x) {
			for (y = 0; y < points[x].length; ++y) {
				float a = 1.0F;
				if(points[x][y].isCar())
					switch (points[x][y].currentColorID) {
						case 0 -> g.setColor(new Color(255, 0, 0));
						case 1 -> g.setColor(new Color(0, 255, 0));
						default -> g.setColor(new Color(0, 0, 255));
					}
				else
//					g.setColor(new Color(0, 0, 255));
					g.setColor(new Color(0, 0, 0, 0.8f));
				g.fillRect((x * size) + 1, (y * size) + 1, (size - 1), (size - 1));
			}
		}
	}

	public void mouseClicked(MouseEvent e) {
		int x = e.getX() / size;
		int y = e.getY() / size;
		if ((x < points.length) && (x >= 0) && y>=0 && y < 3) {
			if(editType==0){
				points[x][y].clicked();
			}
			else {
		//		points[x][y].type= editType;
			}
			this.repaint();
		}
	}

	public void componentResized(ComponentEvent e) {
		int dlugosc = (this.getWidth() / size) + 1;
		int wysokosc = (this.getHeight() / size) + 1;
		initialize(dlugosc, wysokosc);
	}

	public void mouseDragged(MouseEvent e) {
		int x = e.getX() / size;
		int y = e.getY() / size;
		if ((x < points.length) && (x >= 0) && y>= 0 && y < 3) {
			if(editType==0){
				points[x][y].clicked();
			}
			else {
			//	points[x][y].type= editType;
			}
			this.repaint();
		}
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void componentShown(ComponentEvent e) {
	}

	public void componentMoved(ComponentEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseMoved(MouseEvent e) {
	}

	public void componentHidden(ComponentEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

}
