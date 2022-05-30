package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Avatar {
	private Canvas canvas;
	private GraphicsContext gc;
	
	private int x;
	private int y;
	private boolean rigth;
	private boolean left;
	private Image image;
	
	private double width;
	private double height;
	
	private Boolean alive;

	
	public Avatar(Canvas canvas) {
		this.canvas = canvas;
		this.gc = canvas.getGraphicsContext2D();
		
		this.x = 215;
		this.y = 550;
		
		this.rigth=false;
		this.left=false;
		
		this.alive = true;
		
		try {
			File file = new File("src/img/ship.png");
			this.image = new Image(new FileInputStream(file));
			
			this.width = image.getWidth();
			this.height = image.getHeight();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void paint() {

		if(rigth) {
			moveXBy(-4);
		}
		if(left) {
			moveXBy(4);
		}
		
		gc.drawImage(image, x, y-80);
		
	}

	public void moveXBy(int i) {
		this.x += i;
	}
	
	public void moveYBy(int i) {
		this.y += i;	
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

	public boolean isRigth() {
		return rigth;
	}

	public void setRigth(boolean rigth) {
		this.rigth = rigth;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}
	
	public Rectangle2D getBoundary() {
        return new Rectangle2D(this.x, this.y, width, height-10);
    }

    public boolean intersects(Bullet bullet) {
        return bullet.getBoundary().intersects(this.getBoundary());
    }

	public Boolean isAlive() {
		return alive;
	}

	public void setAlive(Boolean alive) {
		this.alive = alive;
	}
    
    
	
}
