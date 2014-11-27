package menuModel;

public class CumulativeScore {

	
	public int gamesPlayed, cumulativePoints;
	
	public CumulativeScore(){
		cumulativePoints = 0;
		gamesPlayed = 0;
	}
	
	public CumulativeScore(int points, int gamePlayed){
		cumulativePoints = points;
		this.gamesPlayed = gamesPlayed;
	}
	
	public int getGamesPlayed(){
		return gamesPlayed;
	}
	
	public void increaseGamesPlayed(){
		gamesPlayed++;
	}
	
	public int getCumulativeScore(){
		return cumulativePoints;
	}
	
	public void increaseCumulativeScore(int additionalPoints){
		cumulativePoints += additionalPoints;
	}
	
	public void clearScore(){
		cumulativePoints = 0;
	}
}
