package model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Box extends Thread{
	private Canvas canvas;
	private GraphicsContext gc;
	
	private int x;
	private int y;
	private boolean isAlive=true;
	
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	public Box(Canvas canvas, int x, int y) {
		this.canvas = canvas;
		this.gc = canvas.getGraphicsContext2D();
		this.x = x;
		this.y = y;
	}

	public void paint() {
		gc.setFill(Color.WHITE);
		gc.fillRect(x,y,20,20);
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
	
	
	
	
}
