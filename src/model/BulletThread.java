package model;

import screens.GameScreen;

public class BulletThread extends Thread{
	
	int time;
	GameScreen screen;
	
	public BulletThread(int time, GameScreen screen) throws InterruptedException {
		this.time = time;
		this.screen = screen;
	}
	
	public void run()  {
		try {
			screen.setCanShoot(false);
			pause(time);
			screen.setCanShoot(true);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void pause(int time) throws InterruptedException {
		Thread.sleep(time);
	}
	
}
