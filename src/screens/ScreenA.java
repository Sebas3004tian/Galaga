package screens;

import java.util.ArrayList;

import control.GameWindow;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import model.Avatar;
import model.Box;
import model.Bullet;

public class ScreenA extends BaseScreen{

	// Los objetos sobre el escenario
	
	private Avatar avatar;
	private ArrayList<Bullet> bullets;
	private ArrayList<Box> boxes;
	private int enemigos=10;
	

	public ScreenA(Canvas canvas) {
		super(canvas);
		avatar = new Avatar(canvas);
		bullets = new ArrayList<Bullet>();
		boxes = new ArrayList<>();
		
		int enemigoX=70;
		int enemigoY=0;
		
		for(int i=0;i<enemigos;i++) {
			Box enemigos=new Box(canvas, enemigoX, enemigoY);
			enemigos.start();
			boxes.add(enemigos);
			enemigoX+=70;
			if(i==4) {
				enemigoX=70;
				enemigoY+=50;
			}
		}
		
	}

	@Override
	public void paint() {
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

		avatar.paint();

		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).paint();

			if (bullets.get(i).getY() < 0) {
				bullets.remove(i);
				i--;
			}
		}

		for (int i = 0; i < boxes.size(); i++) {
			boxes.get(i).paint();
		}

		// Calcular distancia
		for (int i = 0; i < boxes.size(); i++) {
			for (int j = 0; j < bullets.size(); j++) {
				
				//Comparar 
				Box b = boxes.get(i);
				Bullet p = bullets.get(j);
				double D = Math.sqrt( 
						Math.pow(b.getX()-p.getX(), 2) + 
						Math.pow(b.getY()-p.getY(), 2) 
				);
				
				if(D <= 10) {
					Box deletedBox =boxes.remove(i);
					deletedBox.setAlive(false);
					//boxes.remove(i);
					bullets.remove(j);
					return;
				}
				
				System.out.println("Distancia:" + D);
				
			}
		}

	}

	@Override
	public void onClick(MouseEvent e) {
		GameWindow.SCREEN = (GameWindow.SCREEN + 1) % 2;
	}

	@Override
	public void onKey(KeyEvent e) {
		
		if(e.getCode().equals(KeyCode.A)) {
			if(avatar.getX()>0) {
				avatar.moveXBy(-4);
			}
		}else if (e.getCode().equals(KeyCode.D)) {
			if(avatar.getX()<430) {
				avatar.moveXBy(4);
			}
		} else if (e.getCode().equals(KeyCode.SPACE)) {
			bullets.add(new Bullet(canvas, avatar.getX()+5, avatar.getY()+5));
			avatar.setStatus(1);
		}

	}

	

}
