package model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Bullet {
	private Canvas canvas;
	private GraphicsContext gc;
	
	private int x,y;
	private int size;
	private int speed;
	
	public Bullet(Canvas canvas, int x, int y) {
		this.canvas = canvas;
		this.gc = canvas.getGraphicsContext2D();
		this.x = x;
		this.y = y;
		this.size = 10;
		this.speed = 6;
	}
	
	public void paint() {
		gc.setFill(Color.RED);
		gc.fillOval(x,y,size, size);
		y-=speed;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	
	
}
