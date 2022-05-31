package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import com.google.gson.Gson;

public class PlayerData {
	public ArrayList<Player> players;
	
	public PlayerData() {
		players = new ArrayList<>();
	}
	
	public void addPlayer(Player player) {
		players.add(player);
	}
	
	public void saveJSON() {	
		try {
			Gson gson = new Gson();
			String json = gson.toJson(this);
			File file = new File("src/data.json");
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(json.getBytes());
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadJSON() {
		FileInputStream fis;
		try {
			fis = new FileInputStream(new File("src/data.json"));
			BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
			
			String line;
			String json = "";
			while((line = reader.readLine())!=null) {
				json += line;
			}
					
			Gson gson = new Gson();
			PlayerData data = gson.fromJson(json,PlayerData.class);
			
			if(data!=null) {
				this.players = data.players;
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
