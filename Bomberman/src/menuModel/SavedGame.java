package menuModel;

import gameplayModel.GameContext;

import java.sql.Date;
import java.util.ArrayList;

public class SavedGame {
	
	private String gameName, gameDate;
	
	private GameContext gameContext;
	
	public SavedGame(String name, String date, GameContext context) {
		
		gameName = name;
		gameDate = date;
		gameContext = context;
		
	}

	public String getGameName() {
		return gameName;
	}

	public String getGameDate() {
		return gameDate;
	}

	public GameContext getGameContext() {
		return gameContext;
	}
	
	public ArrayList<String> toCSVEntry() {
		
		ArrayList<String> entryList = new ArrayList<String>();
		
		entryList.add(gameName);
		entryList.add(gameDate);
		entryList.add("GameContext");
		
		for (String token : gameContext.toCSVEntry())
			entryList.add(token);
		
		return entryList; 
	}
}
