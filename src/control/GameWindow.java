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
import screens.GameScreen;

public class GameWindow implements Initializable{
	
	@FXML
	private Canvas canvas;
	
	@FXML
	private ProgressBar healthBarPB;
	 
	private GraphicsContext gc;
	
	public static int SCREEN = 0;
	public static long FRAMES = 0;
	
	private GameScreen screen;
	
	private Player player;
	
	
	
	public GameWindow(Player player) {
		this.player = player;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		screen = new GameScreen(canvas, player,healthBarPB);
	
	
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
		
		
		
		initEvents();
	}
	
	private void paint() {
		screen.paint();
	}

	public void initEvents() {
		//Lambda 1
		canvas.setOnMouseClicked(e -> {
			screen.onClick(e);
		});
		//Lambda 2
		canvas.setOnKeyPressed(e -> {
			screen.onKeyPressed(e);	
		});
		
		canvas.setOnKeyReleased(e -> {
			screen.onKeyReleased(e);	
		});
	}

	private void pause(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}
	
}
