package model;

public class Player {
	private String name;
	private int score;
	private int enemKilled;
	
	
	public Player(String name) {
		this.name = name;
		this.score = 0;
		this.enemKilled=0;
	}
	
	

	public int getEnemKilled() {
		return enemKilled;
	}



	public void setEnemKilled(int enemKilled) {
		this.enemKilled = enemKilled;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	public void increaseScore(int score) {
		this.score += score;
	}
	
	public void increaseEnemKilled(int enemKilled) {
		this.enemKilled += enemKilled;
	}

}
