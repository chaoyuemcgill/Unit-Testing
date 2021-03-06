package Database;

import gameplayModel.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import menuModel.*;
import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

public class Database {
	
	private ArrayList<Player> players;
	private Player currentLoggedPlayer;
	
	public Database() {
		
		players = new ArrayList<Player>();
		currentLoggedPlayer = null;
		
		try {
			readCSV();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sortPlayers();
	}
	
	public void generateCSV() throws IOException {

		FileWriter file = new FileWriter("database.csv", false);
		
		CSVWriter writer = new CSVWriter(file);
		
		for (Player player : players) {
			
			ArrayList<String> temp = player.toCSVEntry();
			
			String[] csvEntries = new String[temp.size()];
			
			for (int i = 0 ; i < temp.size() ; i++) {
				csvEntries[i] = temp.get(i);
			}
			writer.writeNext(csvEntries);
		}
		writer.close();
	}
	
	private void readCSV() throws IOException {
		
		CSVReader reader = new CSVReader(new FileReader("database.csv"));
		
		String[] nextLine;
		
		int index = 0;
		
		nextLine = reader.readNext();
		
		while (nextLine != null) {
			
			ArrayList<String> data = new ArrayList<String>();
	    	
			for (String token : nextLine) {
				data.add(token);
				//System.out.println(token);
			}
			
			players.add(new Player(data.get(0), data.get(1), data.get(2), Integer.parseInt(data.get(3)), Integer.parseInt(data.get(4)), Integer.parseInt(data.get(5))));
			
			while (data.contains("SavedGame")) {
				
				data = new ArrayList<String>(data.subList(data.indexOf("SavedGame") + 1, data.size()));
				
				SavedGame savedGame;
				
				if (data.contains("SavedGame"))
					savedGame = generateSavedGame(new ArrayList<String>(data.subList(0, data.indexOf("SavedGame"))));
				else
					savedGame = generateSavedGame(data);
				
				players.get(index).addSavedGame(savedGame);
			}
			
	        index++;
	        nextLine = reader.readNext();
	    }
		
		/*
		System.out.println("---------------");
		
		for (Player player : players) {
			System.out.println("Number of saved game" + player.getSavedGameList().size());
			for (String token : player.toCSVEntry())
				System.out.println(token);
		}*/
	    reader.close();
	}
	
	private SavedGame generateSavedGame(ArrayList<String> data) {
		
		String gameName, gameDate;
		
		GameContext gameContext = generateGameContext(new ArrayList<String>(data.subList(data.indexOf("GameContext") + 1, data.size())));
		
		gameName = data.get(0);
		gameDate = data.get(1);
		
		return new SavedGame(gameName, gameDate, gameContext);
	}
	
	private GameContext generateGameContext(ArrayList<String> context) {
		
		int gameTime, livesLeft, score, level;
		
		GridMap gridMap = generateGridMap(new ArrayList<String>(context.subList(context.indexOf("GridMap") + 1, context.size())));
		
		gameTime = Integer.parseInt(context.get(0));
		livesLeft = Integer.parseInt(context.get(1));
		score = Integer.parseInt(context.get(2));
		level = Integer.parseInt(context.get(3));
		
		return new GameContext(gameTime, livesLeft, score, level, gridMap);
	}
	
	private GridMap generateGridMap(ArrayList<String> map) {
		
		int spawnTimer = Integer.parseInt(map.get(0));
		
		ArrayList<Brick> bricks = generateBricks(new ArrayList<String>(map.subList(map.indexOf("Bricks") + 1, map.indexOf("Bombs"))));
		ArrayList<Bomb> bombs = generateBombs(new ArrayList<String>(map.subList(map.indexOf("Bombs") + 1, map.indexOf("Enemies"))));
		ArrayList<Enemy> enemies = generateEnemies(new ArrayList<String>(map.subList(map.indexOf("Enemies") + 1, map.indexOf("Exitway"))));
		Exitway exitway = generateExitway(new ArrayList<String>(map.subList(map.indexOf("Exitway") + 1, map.indexOf("PowerUp"))));
		PowerUp powerup = generatePowerUp(new ArrayList<String>(map.subList(map.indexOf("PowerUp") + 1, map.indexOf("Bomberman"))));
		Bomberman bomberman = generateBomberman(new ArrayList<String>(map.subList(map.indexOf("Bomberman") + 1, map.size())));
		
		return new GridMap(spawnTimer, bricks, bombs, enemies, exitway, powerup, bomberman);
	}
	
	private ArrayList<Brick> generateBricks(ArrayList<String> data) {
		
		int xPosition, yPosition;
		
		ArrayList<Brick> bricks = new ArrayList<Brick>();
		
		while (data.size() != 0) {
			
			xPosition = Integer.parseInt(data.remove(0));
			yPosition = Integer.parseInt(data.remove(0));

			
			bricks.add(new Brick(xPosition, yPosition));
		}
		
		return bricks;
	}
	
	private ArrayList<Bomb> generateBombs(ArrayList<String> data) {
		
		int range, xPosition, yPosition, timer, rightRange, leftRange, downRange, upRange;
		
		ArrayList<Bomb> bombs = new ArrayList<Bomb>();
		
		range = Integer.parseInt(data.remove(0));
		
		while (data.size() != 0) {
			
			xPosition = Integer.parseInt(data.remove(0));
			yPosition = Integer.parseInt(data.remove(0));
			timer = Integer.parseInt(data.remove(0));
			rightRange = Integer.parseInt(data.remove(0));
			leftRange = Integer.parseInt(data.remove(0));
			downRange = Integer.parseInt(data.remove(0));
			upRange = Integer.parseInt(data.remove(0));
			
			bombs.add(new Bomb(range, xPosition, yPosition, timer, rightRange, leftRange, downRange, upRange));
		}
		
		return bombs;
	}
	
	private ArrayList<Enemy> generateEnemies(ArrayList<String> data) {
		
		int xPosition, yPosition, direction;
		String type;
		
		ArrayList<Enemy> enemies = new ArrayList<Enemy>();
		
		while (data.size() != 0) {
			
			type = data.remove(0);
			
			xPosition = Integer.parseInt(data.remove(0));
			yPosition = Integer.parseInt(data.remove(0));
			direction = Integer.parseInt(data.remove(0));
			
			switch (type) {
			case "class gameplayModel.Balloom":
				enemies.add(new Balloom(xPosition, yPosition, direction));
				break;
			case "class gameplayModel.Oneal":
				enemies.add(new Oneal(xPosition, yPosition, direction));
				break;
			case "class gameplayModel.Doll":
				enemies.add(new Doll(xPosition, yPosition, direction));
				break;
			case "class gameplayModel.Minvo":
				enemies.add(new Minvo(xPosition, yPosition, direction));
				break;
			case "class gameplayModel.Kondoria":
				enemies.add(new Kondoria(xPosition, yPosition, direction));
				break;
			case "class gameplayModel.Ovapi":
				enemies.add(new Ovapi(xPosition, yPosition, direction));
				break;
			case "class gameplayModel.Pass":
				enemies.add(new Pass(xPosition, yPosition, direction));
				break;
			case "class gameplayModel.Pontan":
				enemies.add(new Pontan(xPosition, yPosition, direction));
				break;
			}
		}
		return enemies;
	}
	
	private Exitway generateExitway(ArrayList<String> data) {
		
		int xPosition = Integer.parseInt(data.remove(0));
		int yPosition = Integer.parseInt(data.remove(0));
		
		return new Exitway(xPosition, yPosition);
	}
	
	private PowerUp generatePowerUp(ArrayList<String> data) {
		
		String type = data.remove(0);
		
		int xPosition = Integer.parseInt(data.remove(0));
		int yPosition = Integer.parseInt(data.remove(0));
		
		return determinePowerUp(type, xPosition, yPosition);
	}
	
	private Bomberman generateBomberman(ArrayList<String> data) {
		
		int xPosition, yPosition, speed, bombsAvailable, bombsLeft;
		
		xPosition = Integer.parseInt(data.remove(0));
		yPosition = Integer.parseInt(data.remove(0));
		speed = Integer.parseInt(data.remove(0));
		bombsAvailable = Integer.parseInt(data.remove(0));
		bombsLeft = Integer.parseInt(data.remove(0));

		ArrayList<PowerUp> powerUpsAcquired = generatePowerUpsAcquired(new ArrayList<String>(data.subList(data.indexOf("PowerUpAcquired") + 1, data.size())));
		
		
		return new Bomberman(xPosition, yPosition, speed, bombsAvailable, bombsLeft, powerUpsAcquired);
	}
	
	private ArrayList<PowerUp> generatePowerUpsAcquired(ArrayList<String> data) {
		
		int xPosition, yPosition;
		
		String type;
		
		ArrayList<PowerUp> powerups = new ArrayList<PowerUp>();
		
		while (data.size() != 0) {
			
			type = data.remove(0);
			xPosition = Integer.parseInt(data.remove(0));
			yPosition = Integer.parseInt(data.remove(0));
			
			powerups.add(determinePowerUp(type, xPosition, yPosition));
		}
		
		return powerups;
	}
	
	private PowerUp determinePowerUp(String type, int xPosition, int yPosition) {
		
		PowerUp powerup = new PowerUp(xPosition, yPosition);
		
		switch (type) {
		case "class gameplayModel.BombPU":
			powerup = new BombPU(xPosition, yPosition);
			break;
		case "class gameplayModel.Flames":
			powerup = new Flames(xPosition, yPosition);
			break;
		case "class gameplayModel.Speed":
			powerup = new Speed(xPosition, yPosition);
			break;
		case "class gameplayModel.Wallpass":
			powerup = new Wallpass(xPosition, yPosition);
			break;
		case "class gameplayModel.Detonator":
			powerup = new Detonator(xPosition, yPosition);
			break;
		case "class gameplayModel.Bombpass":
			powerup = new Bombpass(xPosition, yPosition);
			break;
		case "class gameplayModel.Flamepass":
			powerup = new Flamepass(xPosition, yPosition);
			break;
		case "class gameplayModel.Mystery":
			powerup = new Mystery(xPosition, yPosition);
			break;
		}
		return powerup;
	}
	
	public Player addPlayer(Player newPlayer) {
		
		if (findPlayer(newPlayer) == -1) {
			players.add(newPlayer);
			currentLoggedPlayer = newPlayer;
			return newPlayer;
		} else {
			return null;
		}
	}
	
	public void deletePlayer() {
		
		int index = findPlayer(currentLoggedPlayer);
		
		players.remove(index);
	}
	
	public void editRealName(String realName) {
		
		int index = findPlayer(currentLoggedPlayer);
		
		players.get(index).setRealName(realName);
	}
	
	public void editPassword(String password) {

		int index = findPlayer(currentLoggedPlayer);
		
		players.get(index).setPassword(password);
	}
	
	public Player getPlayer(String username, String password) {
		
		int index = findPlayer(new Player(null, username, password));
		
		if (index == -1) {
			return null;
		} else {
			
			if (players.get(index).getPassword().equals(password))
				return players.get(index);
			else
				return null;
		}
	}
	
	public ArrayList<Player> sortPlayers() {
		
		ArrayList<Player> topPlayers = players;
		
		Collections.sort(topPlayers);
		
		topPlayers = new ArrayList<Player>(topPlayers.subList(0, 10));
		
		for (Player player : topPlayers)
			System.out.println(player.getUsername() + " --- " + player.getCumulativeScore());
				
		return topPlayers;
	}
	
	private int findPlayer(Player pl) {
		
		int index = 0;
		
		for (Player player : players) {
			
			if (pl.getUsername().equals(player.getUsername()))
				return index;
			index++;
		}
		return -1;
	}
}
