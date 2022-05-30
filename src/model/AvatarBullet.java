package model;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

public class AvatarBullet extends Bullet {
	public AvatarBullet(Canvas canvas, int x, int y) {
		super(canvas, x, y);
		this.size = 10;
		this.speed = 6;
		this.color = Color.RED;
	}
	
	@Override
	public void paint() {
		gc.setFill(color);
		gc.fillOval(x,y,size, size);
		y-=speed;
	}
}
