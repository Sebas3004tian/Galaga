package model;


import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Avatar {
	private Canvas canvas;
	private GraphicsContext gc;
	
	private int x=215;
	private int y=550;
	private boolean rigth;
	private boolean left;
	private Image image;
	
	//private ArrayList<Image> runImages;
	//private ArrayList<Image> attackImages;
	
	private int state=0;
	private int frame=0;
	
	public Avatar(Canvas canvas) {
		this.canvas = canvas;
		gc = canvas.getGraphicsContext2D();
		rigth=false;
		left=false;
		/*runImages=new ArrayList<Image>();
		attackImages=new ArrayList<Image>();
		try {
			for(int i=1;i<=10;i++) {
				File file = new File("src/image/Run ("+i+").png");
				image = new Image(new FileInputStream(file));
				runImages.add(image);
			}
			for(int i=1;i<=10;i++) {
				File file = new File("src/image/Attack ("+i+").png");
				image = new Image(new FileInputStream(file));
				attackImages.add(image);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	
	public void paint() {

		if(rigth) {
			moveXBy(-4);
		}
		if(left) {
			moveXBy(4);
		}
		
		gc.setFill(Color.RED);
		gc.fillRect(x,y,20,20);
		
		/*if(state==0) {
			gc.drawImage(runImages.get(frame%10), x, y,100,100);
			frame++;
		}else if(state==1) {
			gc.drawImage(attackImages.get(frame), x, y,100,100);
			frame++;
			if(frame==10) {
				this.state=0;
			}
		}*/
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

	public int getStatus() {
		return state;
	}

	public void setStatus(int state) {
		this.state = state;
		this.frame=0;
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
	
	
}
