package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Enemy extends Thread{
	private Canvas canvas;
	private GraphicsContext gc;
	
	private int x;
	private int y;
	private boolean isAlive=true;
	private Image image;
	
	private double width;
	private double height;
	
	@Override
	public void run() {
		while(isAlive) {
			int randX=(int)(7*Math.random())-3;
			if(x<430 && x>0) {
				x+=randX;
			}
			try {
				Thread.sleep(80);
				y++;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	public Enemy(Canvas canvas, int x, int y) {
		this.canvas = canvas;
		this.gc = canvas.getGraphicsContext2D();
		
		this.x = x;
		this.y = y;
		
		try {
			File file = new File("src/img/enemy.png");
			image = new Image(new FileInputStream(file));
			
			this.width = image.getWidth();
			this.height = image.getHeight();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void paint() {
		gc.drawImage(image, x, y);
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

	public boolean getAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
	
	public Rectangle2D getBoundary() {
        return new Rectangle2D(this.x, this.y, width, height-10);
    }

    public boolean intersects(Bullet bullet) {
        return bullet.getBoundary().intersects(this.getBoundary());
    }
	
	
}
