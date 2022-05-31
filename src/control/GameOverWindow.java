package control;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ProgressBar;
import model.Player;
import model.PlayerData;
import screens.GameOverScreen;
import screens.GameScreen;

public class GameOverWindow implements Initializable{
	
	@FXML
	private Canvas canvas;
	
	@FXML
	private ProgressBar healthBarPB;
	 
	private GraphicsContext gc;
	
	public static int SCREEN = 0;
	public static long FRAMES = 0;
	
	private GameOverScreen screen;
	
	private Player player;
	
	private PlayerData data;
	
	public GameOverWindow(PlayerData data) {
		this.data = data;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		screen = new GameOverScreen(canvas, data);
	
	
		gc = canvas.getGraphicsContext2D();
		canvas.setFocusTraversable(true);
		
		new Thread(() -> {
			while (true) {
				Platform.runLater(()->{
					paint();
				});
				pause(35);
				FRAMES++;
			}
		}).start();
		
		
	}
	
	private void paint() {
		screen.paint();
	}



	private void pause(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}
	
}