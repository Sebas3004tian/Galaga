package screens;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import control.GameOverWindow;
import control.MainWindow;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import main.Main;
import model.Avatar;
import model.AvatarBullet;
import model.Bullet;
import model.BulletThread;
import model.Enemy;
import model.EnemyBullet;
import model.Player;
import model.PlayerData;

public class GameScreen{

	protected Canvas canvas;
	protected ProgressBar healthBarPB;
	protected GraphicsContext gc;
	
	private Avatar avatar;
	private ArrayList<AvatarBullet> bullets;
	private ArrayList<EnemyBullet> enemyBullets;
	private ArrayList<Enemy> enemies;
	private int enemigos=10;
	int enemigoX=70;
	int enemigoY=40;
	private boolean canShoot; 
	private boolean canEnemiesShoot;
	private int difficulty = 0;
	private int level = 1;
	private Player player;
	
	private ArrayList<Image> fondoImages;
	private Image image;
	private Image vida;
	private int frame=0;
	private int muerteX=0;
	private int muerteY=0;
	

	private ArrayList<Image> muerte;
	private int contMuerte=0;
	boolean closed;
	
	static PlayerData data;
	
	public GameScreen(Canvas canvas, Player player, ProgressBar healthBarPB) {
		
		data = new PlayerData();
		data.loadJSON();
		
		fondoImages=new ArrayList<Image>();
		muerte=new ArrayList<Image>();
		
		closed = false;
		
		try {
			for(int i=1;i<=7;i++) {
				File file = new File("src/img/fondo/F("+i+").png");
				
				image = new Image(new FileInputStream(file));
				fondoImages.add(image);
				fondoImages.add(image);
				fondoImages.add(image);
			}
			for(int i=1;i<=5;i++) {
				File file2 = new File("src/img/enemy/Muerte("+i+").png");
				image = new Image(new FileInputStream(file2));
				muerte.add(image);
				muerte.add(image);
			}
			File file = new File("src/img/avatar/Normal.png");
			vida = new Image(new FileInputStream(file));
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
		this.player = player;
		this.canvas = canvas;
		this.healthBarPB=healthBarPB;
		
		gc = canvas.getGraphicsContext2D();
		avatar = new Avatar(canvas);
		bullets = new ArrayList<>();
		enemyBullets = new ArrayList<>();
		enemies = new ArrayList<>();
		
		canShoot = true;
		canEnemiesShoot = true;
		
		for(int i=1;i<enemigos+1;i++) {
			Enemy enemigos=new Enemy(canvas, enemigoX, enemigoY);
			enemigos.start();
			enemies.add(enemigos);
			enemigoX+=70;
			if(i%5==0) {
				enemigoX=70;
				enemigoY-=50;
			}
		}
	}		
	
	

	public void paint() {
		
		gc.drawImage(fondoImages.get(frame),0, 0);
		frame++;
		
		if(frame>=21) {
			frame=0;
		}
		
		avatar.paint();
		
		if(avatar.getHealth()<=0) {
			avatar.decreaseLives();
			healthBarPB.setProgress(1);
			avatar.setHealth(100);
		}
		
		if(avatar.getLives()<=0) {
			avatar.setAlive(false);
		}
		
		if(!avatar.isAlive() && !closed) {
			data.addPlayer(player);
			data.saveJSON();
			Stage stage = (Stage) canvas.getScene().getWindow();
			stage.close();
			try {
				FXMLLoader loader = new FXMLLoader(Main.class.getResource("../ui/GameOverWindow.fxml"));
				loader.setController(new GameOverWindow(data));
				Parent parent = loader.load();
				Scene scene = new Scene(parent);
				Stage stage2 = new Stage();
				stage2.setScene(scene);
				stage2.show();
			} catch(IOException e) {
				e.printStackTrace();
			}
			closed = true;
		}
		
		if(enemies.size()==0) {
			if(difficulty<2000) {
				difficulty+=250;
			}
			
			level += 1;
			enemigoX=70;
			enemigoY=40;
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
		
		for (int i = 0; i < enemies.size(); i++) {
			Enemy enemy = enemies.get(i);
			enemy.paint();
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
		
		if(canEnemiesShoot) {
			try {
				Random r = new Random();
				int enemyNumber = r.nextInt(enemies.size());	
				Enemy enemy = enemies.get(enemyNumber);
				enemyBullets.add(new EnemyBullet(this.canvas, enemy.getX()+24, enemy.getY()+32));
				BulletThread thread;
				thread = new BulletThread(2000-difficulty, this, 2);
				thread.start();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		

	
		for (int i = 0; i < enemyBullets.size(); i++) {
			try {
				BulletThread thread = new BulletThread(100, this, 0);
				thread.start();
				enemyBullets.get(i).paint();

			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			
			if (enemyBullets.get(i).getY() >= canvas.getHeight()) {
				enemyBullets.remove(i);
				i--;
			}
		}
		
		for (int i = 0; i < enemyBullets.size(); i++) {	
			EnemyBullet p = enemyBullets.get(i);

			if(avatar.intersects(p)) {
				avatar.decreaseHealth(p.getDamage());
				
				double actualHealth=healthBarPB.getProgress();
				double damage=p.getDamage();
				healthBarPB.setProgress(actualHealth-damage/100);
				
				enemyBullets.remove(i);
				return;
			}
			
		}

		for (int i = 0; i < enemies.size(); i++) {
			for (int j = 0; j < bullets.size(); j++) {	
				
				Enemy b = enemies.get(i);
				Bullet p = bullets.get(j);

				if(b.intersects(p)) {
					//AQUI SIGNIFICA QUE MATO A UNO
					muerteX=enemies.get(i).getX();
					muerteY=enemies.get(i).getY();
					contMuerte=10;
					
					
					Enemy deletedEnemy = enemies.remove(i);
					deletedEnemy.setAlive(false);
					bullets.remove(j);
					player.increaseEnemKilled(1);
					player.increaseScore(10*level);
					return;
				}
				
			}
		}	
		
		if(contMuerte!=0) {
			gc.drawImage(muerte.get(frame%10), muerteX, muerteY,64,64);
			contMuerte--;
		}
		
		for (int i = 0; i < enemies.size(); i++) {
	
			Enemy b = enemies.get(i);
			
			if(b.getY()>=canvas.getHeight()) {
				resetLevel();
				return;
			}
			
			/*if(avatar.intersects(b)) {
				Enemy deletedEnemy = enemies.remove(i);
				deletedEnemy.setAlive(false);
				avatar.setAlive(false);
				return;
			}*/

			if(avatar.intersects(b)) {
				Enemy deletedEnemy = enemies.remove(i);
				deletedEnemy.setAlive(false);
				if(avatar.getLives()>0) {
					resetLevel();
				}else {
					avatar.setAlive(false);
				}
				return;
			}

		}	
		
		
		for(int i=0;i<avatar.getLives();i++) {
			gc.drawImage(vida, 420-(i*20), 35,15,15);
		}
		
		gc.setFill(Color.YELLOW);
		gc.setFont(Font.font("MS Reference Sans Serif",FontWeight.BOLD, FontPosture.ITALIC, 16.0));
		gc.fillText(player.getScore()+"", 300, 48);

		gc.setFill(Color.MEDIUMPURPLE);
		gc.setFont(Font.font("MS Reference Sans Serif",FontWeight.BOLD, FontPosture.ITALIC, 18.0));
		gc.fillText("Level: "+level, 10, 20);
		
	}
	
	public void resetLevel() {
		avatar.decreaseLives();
		healthBarPB.setProgress(1);
		enemies = new ArrayList<>();
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
					bullets.add(new AvatarBullet(canvas, avatar.getX()+30, avatar.getY()));
					BulletThread thread = new BulletThread(200, this, 1);
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
				BulletThread thread = new BulletThread(200, this, 1);
				thread.start();
				canShoot = false;
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			
		} 
	}

	public void setCanShoot(boolean canShoot) {
		this.canShoot = canShoot;
	}

	public void setCanEnemiesShoot(boolean canEnemiesShoot) {
		this.canEnemiesShoot = canEnemiesShoot;
	}
	
	

	
}
