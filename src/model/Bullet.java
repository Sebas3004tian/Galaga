package model;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Bullet {
	private Canvas canvas;
	protected GraphicsContext gc;
	
	protected int x,y;
	protected int size;
	protected int speed;
	
	protected double width;
	protected double height;
	
	protected Color color;
	
	public Bullet(Canvas canvas, int x, int y) {
		this.canvas = canvas;
		this.gc = canvas.getGraphicsContext2D();
		this.x = x;
		this.y = y;
		
		this.width = size;
		this.height = size;
	}
	
	public abstract void paint();

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
	
    public Rectangle2D getBoundary() {
        return new Rectangle2D(this.x, this.y, width, height);
    }
	
    public boolean intersects(Enemy enemy) {
        return enemy.getBoundary().intersects(this.getBoundary());
    }
}
