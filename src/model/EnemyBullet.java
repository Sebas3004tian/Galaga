package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class EnemyBullet extends Bullet {
	
	private int damage;

	private Image bala;
	
	public EnemyBullet(Canvas canvas, int x, int y) {
		super(canvas, x, y);
		this.size = 10;
		this.speed = 6;
		this.color = Color.YELLOW;
		this.damage = 25;
		
		File file = new File("src/img/enemy/balaEnemy.png");
		try {
			bala = new Image(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void paint() {
		gc.drawImage(bala, x, y,10,10);
		y+=speed;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

}
