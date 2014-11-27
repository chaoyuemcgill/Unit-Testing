

package menuModel;

import java.util.ArrayList;

public class Player implements Comparable<Player> {
	
	private int levelUnlocked;
	
	private String realName, username, password;
	private CumulativeScore cumulativeScore;
	private ArrayList<SavedGame> savedGameList;
	
	
	public Player(String name, String user, String pw){
		realName = name;
		username = user;
		password = pw;
		
		levelUnlocked = 0;
		
		savedGameList = new ArrayList<SavedGame>();
		cumulativeScore = new CumulativeScore();
	}
	
	public Player(String name, String user, String pw, int unlocked, int points, int gamePlayed){
		realName = name;
		username = user;
		password = pw;
		levelUnlocked = unlocked;
		
		savedGameList = new ArrayList<SavedGame>();
		cumulativeScore = new CumulativeScore(points, gamePlayed);
	}
	
	public String getRealName(){
		return realName;
	}
	
	public String getUsername(){
		return username;
	}
	
	public String getPassword(){
		return password;
	}
	
	public void setRealName(String real){
		realName = real;
	}
	
	public void setPassword(String pass){
		password = pass;
	}
	
	public void updateScore(int score){
		cumulativeScore.increaseCumulativeScore(score);
	}
	
	public void updateGamesPlayed(){
		cumulativeScore.increaseGamesPlayed();
	}
	
	public int getCumulativeScore(){
		return cumulativeScore.getCumulativeScore();
	}
	
	public void resetScore(){
		cumulativeScore.clearScore();
	}
	
	public int getLevelUnlocked() {
		return levelUnlocked;
	}
	
	public void setLevelUnlocked(int unlocked) {
		levelUnlocked = unlocked;
	}

	public ArrayList<SavedGame> getSavedGameList() {
		return savedGameList;
	}

	public void addSavedGame(SavedGame game) {
		savedGameList.add(game);
	}
	
	public ArrayList<String> toCSVEntry() {
		
		ArrayList<String> entryList = new ArrayList<String>();
		
		entryList.add(realName);
		entryList.add(username);
		entryList.add(password);
		entryList.add(Integer.toString(levelUnlocked));
		entryList.add(Integer.toString(cumulativeScore.getCumulativeScore()));
		entryList.add(Integer.toString(cumulativeScore.gamesPlayed));
		
		for (SavedGame game : savedGameList) {
			
			entryList.add("SavedGame");
			
			for (String token : game.toCSVEntry())
				entryList.add(token);
		}
		return entryList; 
	}

	public int compareTo(Player plr) {
		
		return plr.getCumulativeScore() - this.getCumulativeScore();
	}


}
