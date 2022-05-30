package model;

import screens.GameScreen;

public class BulletThread extends Thread{
	
	int time;
	int mode;
	GameScreen screen;
	
	public BulletThread(int time, GameScreen screen, int mode) throws InterruptedException {
		this.time = time;
		this.screen = screen;
		this.mode = mode;
	}
	
	public void run()  {
		try {
			if(mode == 0) {
				pause(time);
			} else if(mode==1) {
				screen.setCanShoot(false);
				pause(time);
				screen.setCanShoot(true);
			} else if(mode==2) {
				screen.setCanEnemiesShoot(false);
				pause(time);
				screen.setCanEnemiesShoot(true);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void pause(int time) throws InterruptedException {
		Thread.sleep(time);
	}
	
}
