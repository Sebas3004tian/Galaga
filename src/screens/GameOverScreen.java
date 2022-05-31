package screens;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import model.PlayerData;


public class GameOverScreen {
	
	protected Canvas canvas;
	protected GraphicsContext gc;
	private ArrayList<Image> fondoImages;
	private Image gameOverImage;
	private Image image;
	private int frame=0;
	private PlayerData data;

	
	public GameOverScreen(Canvas canvas, PlayerData data) {
		
		this.data = data;
		
		fondoImages=new ArrayList<Image>();
		
		try {
			File gameOverFile = new File("src/img/gameover.png");
			gameOverImage = new Image(new FileInputStream(gameOverFile));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		try {
			for(int i=1;i<=7;i++) {
				File file = new File("src/img/fondo/F("+i+").png");
				
				image = new Image(new FileInputStream(file));
				fondoImages.add(image);
				fondoImages.add(image);
				fondoImages.add(image);
			}
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
		this.canvas = canvas;
		
		gc = canvas.getGraphicsContext2D();

	}		
	
	public void paint() {
		
		gc.drawImage(fondoImages.get(frame),0, 0);
		
		
		gc.setFill(Color.MEDIUMPURPLE);
		gc.setFont(Font.font("MS Reference Sans Serif",FontWeight.BOLD, FontPosture.ITALIC, 16.0));
		gc.fillText("NAME",30,170);
		gc.fillText("SCORE",350,170);
		
		for(int i=0; i<data.players.size(); i++) {
			gc.setFill(Color.YELLOW);
			gc.setFont(Font.font("MS Reference Sans Serif",FontWeight.BOLD, FontPosture.ITALIC, 16.0));
			gc.fillText(data.players.get(i).getName(), 30, 210+(i*30));
			gc.fillText(data.players.get(i).getScore()+"", 350, 210+(i*30));
		}
		
		frame++;
		
		if(frame>=21) {
			frame=0;
		}
		gc.drawImage(gameOverImage, 150, 75);
	}
}
