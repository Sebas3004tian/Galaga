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
import model.AvatarBullet;
import model.Bullet;
import model.BulletThread;
import model.Enemy;
import model.EnemyBullet;

public class GameScreen{

	protected Canvas canvas;
	protected GraphicsContext gc;
	
	private Avatar avatar;
	private ArrayList<Bullet> bullets;
	private ArrayList<Bullet> enemyBullets;
	private ArrayList<Enemy> enemies;
	private int enemigos=10;
	private boolean canShoot; 
	private boolean canEnemyShoot;
	
	
	
	public GameScreen(Canvas canvas) {
		this.canvas = canvas;
		gc = canvas.getGraphicsContext2D();
		avatar = new Avatar(canvas);
		bullets = new ArrayList<Bullet>();
		enemyBullets = new ArrayList<Bullet>();
		enemies = new ArrayList<>();
		
		canShoot = true;
		canEnemyShoot = true;
		
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
		
		if(!avatar.isAlive()) {
			System.out.println("MORISTE");
			try {
				Thread.sleep(2000);
				System.exit(0);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
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
			Enemy enemy = enemies.get(i);
			enemy.paint();
		}	
		
		int k = 0;
		while(k<enemies.size()) {
			Enemy enemy = enemies.get(k);
			if(canEnemyShoot) {
				try {
					BulletThread thread = new BulletThread(4000, this, 2);
					thread.start();
					enemyBullets.add(new EnemyBullet(this.canvas, enemy.getX()+24, enemy.getY()+32));
					k++;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			try {
				BulletThread thread = new BulletThread(500, this, 0);
				thread.start();
				k++;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		
		for (int i = 0; i < enemyBullets.size(); i++) {
			enemyBullets.get(i).paint();
			
			if (enemyBullets.get(i).getY() >= canvas.getHeight()) {
				enemyBullets.remove(i);
				i--;
			}
		}
		
		for (int i = 0; i < enemyBullets.size(); i++) {	
			Bullet p = enemyBullets.get(i);

			if(avatar.intersects(p)) {
				avatar.setAlive(false);
				enemyBullets.remove(i);
				return;
			}
			
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
					bullets.add(new AvatarBullet(canvas, avatar.getX()+57, avatar.getY()-73));
					BulletThread thread = new BulletThread(500, this, 1);
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
				BulletThread thread = new BulletThread(500, this, 1);
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

	public boolean isCanEnemyShoot() {
		return canEnemyShoot;
	}

	public void setCanEnemyShoot(boolean canEnemyShoot) {
		this.canEnemyShoot = canEnemyShoot;
	}

	
	

}
