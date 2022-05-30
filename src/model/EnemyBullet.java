package model;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

public class EnemyBullet extends Bullet {

	public EnemyBullet(Canvas canvas, int x, int y) {
		super(canvas, x, y);
		this.size = 10;
		this.speed = 6;
		this.color = Color.YELLOW;
	}
	
	@Override
	public void paint() {
		gc.setFill(color);
		gc.fillOval(x,y,size, size);
		y+=speed;
	}

}
