//*****************************************************************************************************
//  GameContext.java
//
//*****************************************************************************************************

package gameplayModel;

import java.util.ArrayList;

import gameplayController.GameplayController;

public class GameContext {

	public final int MAX_GAME_TIME = 200 * 1000;
	public final int INITIAL_LIVES_LEFT = 2;
	
	private int[][] levelSpec = {	{6, 0, 0, 0, 0, 0, 0, 0, 2},
									{3, 3, 0, 0, 0, 0, 0, 0, 1},
									{2, 2, 2, 0, 0, 0, 0, 0, 5},
									{1, 1, 2, 2, 0, 0, 0, 0, 3},
									{0, 4, 3, 0, 0, 0, 0, 0, 1},
									{-1, 0, 0, 0, 0, 0, 0, 0, 0},
									{0, 2, 3, 2, 0, 0, 0, 0, 1},
									{0, 2, 3, 0, 2, 0, 0, 0, 2},
									{0, 1, 2, 4, 0, 0, 0, 0, 5},
									{0, 1, 1, 4, 1, 0, 0, 0, 6},
									{0, 1, 1, 1, 3, 1, 0, 0, 4},
									{0, -1, 0, 0, 0, 0, 0, 0, 0},
									{0, 1, 2, 3, 1, 1, 0, 0, 1},
									{0, 1, 1, 1, 4, 1, 0, 0, 1},
									{0, 0, 3, 3, 3, 0, 0, 0, 5},
									{0, 0, 0, 0, 0, 7, 1, 0, 6},
									{0, 0, 1, 3, 3, 0, 1, 0, 2},
									{0, 0, -1, 0, 0, 0, 0, 0, 0},
									{0, 0, 0, 3, 4, 0, 1, 0, 4},
									{0, 0, 5, 0, 2, 0, 1, 0, 1},
									{3, 3, 0, 0, 0, 0, 2, 0, 6},
									{1, 1, 3, 0, 0, 1, 2, 0, 1},
									{0, 1, 1, 1, 2, 1, 2, 0, 5},
									{0, 0, 0, -1, 0, 0, 0, 0, 0},
									{0, 0, 0, 0, 4, 3, 2, 0, 6},
									{0, 0, 4, 3, 1, 0, 1, 0, 5},
									{0, 0, 2, 2, 2, 2, 1, 0, 1},
									{0, 0, 1, 1, 4, 2, 1, 0, 5},
									{0, 2, 1, 1, 2, 2, 1, 0, 6},
									{0, 0, 0, 0, -1, 0, 0, 0, 0},
									{1, 1, 1, 1, 2, 1, 1, 0, 8},
									{1, 1, 0, 0, 5, 1, 1, 0, 2},
									{0, 1, 3, 3, 1, 0, 1, 0, 1},
									{0, 0, 0, 0, 2, 5, 2, 0, 5},
									{0, 0, 3, 2, 1, 2, 1, 0, 7},
									{0, 0, 0, 0, -1, 0, 0, 0, 0},
									{0, 2, 2, 2, 2, 2, 0, 0, 4},
									{0, 1, 1, 3, 4, 0, 1, 0, 1},
									{0, 0, 2, 2, 3, 1, 2, 0, 5},
									{0, 0, 2, 3, 3, 0, 2, 0, 8},
									{0, 0, 2, 1, 3, 1, 2, 0, 6},
									{0, 0, 0, 0, 0, 0, -1, 0, 0},
									{0, 0, 2, 2, 3, 0, 3, 0, 7},
									{0, 0, 2, 1, 3, 1, 3, 0, 5},
									{0, 0, 2, 2, 3, 0, 3, 0, 2},
									{0, 0, 1, 1, 2, 2, 4, 0, 4},
									{0, 0, 0, 0, 0, 0, 0, -1, 0},
									{0, 0, 1, 2, 3, 0, 4, 0, 8},
									{0, 0, 1, 1, 3, 1, 4, 0, 5},
									{0, 0, 0, 1, 3, 1, 5, 0, 4},
									{0, 0, 0, 1, 2, 1, 6, 0, 6},
									{0, 0, 0, 1, 2, 1, 6, 0, 5},
									{0, 0, 0, 0, 0, 0, 0, -1, 0},
									{0, 0, 0, 0, 2, 2, 6, 0, 8},
									{0, 0, 0, 0, 2, 2, 6, 0, 4},
									{0, 0, 0, 0, 2, 2, 6, 0, 6},
									{0, 0, 0, 0, 2, 1, 6, 1, 5},
									{0, 0, 0, 0, 1, 2, 6, 1, 7},
									{0, 0, 0, 0, 0, 0, 0, -1, 0},
									{0, 0, 0, 0, 1, 2, 5, 2, 8}};
	
	private int gameTime, livesLeft, score, level, actualLevel;
	
	private boolean endGameEnemiesSpawned;
	
	private GridMap gridMap;
	
	public GameContext() {
		gameTime = MAX_GAME_TIME;
		livesLeft = INITIAL_LIVES_LEFT;
		score = 0;
		level = 0;
		actualLevel = 0;
		
		endGameEnemiesSpawned = false;
		
		gridMap = new GridMap(levelSpec[level]);

	}
	
	public GameContext(int selectedLevel) {
		gameTime = MAX_GAME_TIME;
		livesLeft = INITIAL_LIVES_LEFT;
		score = 0;
		actualLevel = selectedLevel;
		System.out.println(computeLevel());
		System.out.println("Value of actualLevel is "+actualLevel);
		level = computeLevel();
				
		endGameEnemiesSpawned = false;
		
		gridMap = new GridMap(levelSpec[level]);

	}
	
	public GameContext(int time, int lives, int score, int level, GridMap gridMap) {
		gameTime = time;
		livesLeft = lives;
		this.score = score;
		this.level = level;
		
		computeActualLevel();
		
		endGameEnemiesSpawned = false;
		
		this.gridMap = gridMap;

	}
	
	public void restartMap() {
		gridMap = new GridMap(levelSpec[level]);
	}
	
	public void setLevel(int setlevel){
		level = setlevel;
		System.out.print(level);
	}

	private void computeActualLevel() {
		
		int specialLevel = 0;
		
		for (int i = 0 ; i <= level ; i++) {
			if (levelSpec[i][8] != 0) {
				specialLevel++;
			}
		}
		actualLevel = level - specialLevel;
	}
	
	private int computeLevel() {
		
		int specialLevel = 0;
		
		for (int i = 0 ; i <= actualLevel ; i++) {
			if (levelSpec[i][8] == 0) {
				specialLevel++;
			}
		}
		return actualLevel + specialLevel;
	}
	
	public int getGameTime() {
		return gameTime;
	}
	
	public void decreaseGameTime() {
		if (gameTime > 0)
			gameTime -= GameplayController.TIMEOUT;
	}
	
	public void initializeGameTime() {
		gameTime = MAX_GAME_TIME;
	}
	
	public int getLivesLeft() {
		return livesLeft;
	}
	
	public void increaseLivesLeft() {
		livesLeft++;
	}
	
	public void decreaseLivesLeft() {
		if (livesLeft > 0)
			livesLeft--;
	}
	
	public int getScore() {
		return score;
	}
	
	
	public void increaseScore(int additionnalPoints) {
		score += additionnalPoints;
	}
	
	public int[] getLevelSpecification() {
		return levelSpec[level];
	}
	
	public void increaseLevel() {
		level++;
		computeActualLevel();
	}
	
	public int getActualLevel() {
		return actualLevel;
	}
	public int getLevel() {
		return level;
	}
	
	public GridMap getGridMap() {
		return gridMap;
	}
	
	public boolean getEndGameEnemiesStatus() {
		return endGameEnemiesSpawned;
	}
	
	public void setEndGameEnemiesStatus(boolean status) {
		endGameEnemiesSpawned = status;
	}
	
	public ArrayList<String> toCSVEntry() {
		
		ArrayList<String> entryList = new ArrayList<String>();
		
		entryList.add(Integer.toString(gameTime));
		entryList.add(Integer.toString(livesLeft));
		entryList.add(Integer.toString(score));
		entryList.add(Integer.toString(level));
		entryList.add("GridMap");
		
		for (String token : gridMap.toCSVEntry())
			entryList.add(token);
		
		return entryList; 
	}
}

