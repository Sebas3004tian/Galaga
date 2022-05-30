package screens;

import java.util.ArrayList;

import control.GameWindow;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import model.Avatar;
import model.Bullet;
import model.BulletThread;
import model.Enemy;

public class GameScreen{

	protected Canvas canvas;
	protected GraphicsContext gc;
	
	private Avatar avatar;
	private ArrayList<Bullet> bullets;
	private ArrayList<Enemy> enemies;
	private int enemigos=10;
	private boolean canShoot; 
	BulletThread thread;
	
	
	public GameScreen(Canvas canvas) {
		this.canvas = canvas;
		gc = canvas.getGraphicsContext2D();
		avatar = new Avatar(canvas);
		bullets = new ArrayList<Bullet>();
		enemies = new ArrayList<>();
		
		canShoot = true;
		
		int enemigoX=70;
		int enemigoY=40;
		
		for(int i=0;i<enemigos;i++) {
			Enemy enemigos=new Enemy(canvas, enemigoX, enemigoY);
			enemigos.start();
			enemies.add(enemigos);
			enemigoX+=70;
			if(i==4) {
				enemigoX=70;
				enemigoY+=50;
			}
		}
	}

	public void paint() {
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

		avatar.paint();
		
		if(avatar.getX()>=canvas.getWidth()) {
			avatar.setX(-57);
		}
		
		if(avatar.getX()<=-58) {
			avatar.setX((int) canvas.getWidth());
		}

		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).paint();

			if (bullets.get(i).getY() < 0) {
				bullets.remove(i);
				i--;
			}
		}

		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).paint();
		}

		for (int i = 0; i < enemies.size(); i++) {
			for (int j = 0; j < bullets.size(); j++) {	
				
				Enemy b = enemies.get(i);
				Bullet p = bullets.get(j);

				if(b.intersects(p)) {
					Enemy deletedBox =enemies.remove(i);
					deletedBox.setAlive(false);
					//boxes.remove(i);
					bullets.remove(j);
					return;
				}
				
			}
		}
	}


	public void onClick(MouseEvent e) {
		
	}


	public void onKeyPressed(KeyEvent e) {
		
		if(e.getCode().equals(KeyCode.A)) {
			avatar.setRigth(true);
			avatar.setLeft(false);
		}else if (e.getCode().equals(KeyCode.D)) {
			avatar.setLeft(true);
			avatar.setRigth(false);

		} else if (e.getCode().equals(KeyCode.SPACE)) {	
			try {
				if(canShoot) {
					bullets.add(new Bullet(canvas, avatar.getX()+57, avatar.getY()-73));
					thread = new BulletThread(500, this);
					thread.start();
				}
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

		}

	}

	public void onKeyReleased(KeyEvent e) {
		
		if(e.getCode().equals(KeyCode.A)) {
			avatar.setRigth(false);
		}else if (e.getCode().equals(KeyCode.D)) {
			avatar.setLeft(false);
		} else if (e.getCode().equals(KeyCode.SPACE)) {
			try {
				thread = new BulletThread(500, this);
				thread.start();
				canShoot = false;
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			
		} 
	}

	public boolean isCanShoot() {
		return canShoot;
	}

	public void setCanShoot(boolean canShoot) {
		this.canShoot = canShoot;
	}


	

}
