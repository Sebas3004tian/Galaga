package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

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
	
	private int health;
	
	private int lives;
	
	private ArrayList<Image> normal;
	private ArrayList<Image> der;
	private ArrayList<Image> izq;
	private ArrayList<Image> muerte;
	private ArrayList<Image> aparicion;
	
	private int state=0;
	private int contMuerte=0;
	private int contAparicion=0;
	private int frame=0;

	
	public Avatar(Canvas canvas) {
		
		normal=new ArrayList<Image>();
		der=new ArrayList<Image>();
		izq=new ArrayList<Image>();
		muerte=new ArrayList<Image>();
		aparicion=new ArrayList<Image>();
		
		try {
			for(int i=1;i<=3;i++) {
				File file = new File("src/img/avatar/Normal("+i+").png");
				File file2 = new File("src/img/avatar/Nada.png");
				image = new Image(new FileInputStream(file));
				normal.add(image);
				aparicion.add(image);
				aparicion.add(new Image(new FileInputStream(file2)));
			}
			for(int i=1;i<=3;i++) {
				File file = new File("src/img/avatar/Der("+i+").png");
				image = new Image(new FileInputStream(file));
				der.add(image);
			}
			for(int i=1;i<=3;i++) {
				File file = new File("src/img/avatar/Izq("+i+").png");
				image = new Image(new FileInputStream(file));
				izq.add(image);
			}
			for(int i=1;i<=4;i++) {
				File file = new File("src/img/avatar/muerte ("+i+").png");
				image = new Image(new FileInputStream(file));
				muerte.add(image);
				muerte.add(image);
				muerte.add(image);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.contAparicion=6;
		
		this.canvas = canvas;
		this.gc = canvas.getGraphicsContext2D();
		
		this.x = 215;
		this.y = 500;
		
		this.rigth=false;
		this.left=false;
		
		this.health = 100;
		
		this.lives = 3;
		
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
		if(contAparicion!=0) {
			gc.drawImage(aparicion.get(frame%6), x, y,60,60);
			contAparicion--;
			frame++;
		}else if(contMuerte!=0) {
			gc.drawImage(muerte.get(frame%12), x-34, y-34,128,128);
			contMuerte--;
			if(contMuerte==0) {
				this.x = 215;
				this.y = 500;
				contAparicion=6;
			}
			frame++;
		}else {
			if(!rigth && !left) {
				gc.drawImage(normal.get(frame%3), x, y,60,60);
				frame++;
			}else if(rigth) {
				gc.drawImage(izq.get(frame%3), x, y,60,60);
				frame++;
			}else if(left) {
				gc.drawImage(der.get(frame%3), x, y,60,60);
				frame++;
			}
		}
		
		//gc.drawImage(image, x, y-80,100,100);
		
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

	public boolean intersects(Enemy enemy) {
		return enemy.getBoundary().intersects(this.getBoundary());
	}
    
	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
	
	public void decreaseHealth(int damage) {
		this.health -=  damage;
	}
	
	public void decreaseLives() {
		contMuerte=12;
		this.lives -=  1;
	}

	public int getLives() {
		return lives;
	}
	
	
}
