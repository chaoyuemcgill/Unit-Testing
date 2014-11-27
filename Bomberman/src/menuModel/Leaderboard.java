
package menuModel;

import java.util.ArrayList;

import Database.Database;

public class Leaderboard {

	
	public Database playerList;
	public ArrayList<Player> topPlayers = new ArrayList<Player>();
	public String[] names = new String[10];
	public int[] scores = new int[10];
	
	public Leaderboard(Database database){
		playerList = database;
		topPlayers = playerList.sortPlayers();
		for(int i = 0; i<topPlayers.size(); i++){
			scores[i] = topPlayers.get(i).getCumulativeScore();
		for(int j=0; j<topPlayers.size(); j++)
			names[j] = topPlayers.get(j).getUsername();	
		}
	}
	
	public int[] generateScores(){
		return scores;
	}
	
	public String[] generateNames(){
		return names;
	}
	
	public void updateLeaderboard(){
		topPlayers = playerList.sortPlayers();
		for(int i = 0; i<topPlayers.size(); i++){
			scores[i] = topPlayers.get(i).getCumulativeScore();
		for(int j=0; j<topPlayers.size(); j++)
			names[j] = topPlayers.get(j).getUsername();	
		}
	}
	
	public void addPlayer(Player player){
		int score = player.getCumulativeScore();
		for(int i = 0; i<10; i++){
			if(score>scores[i]){
				for(int j = 9; j>i; j++){
					scores[j]=scores[j-1];
					names[j] = names[j-1];
				scores[i]=score;
				names[i] = player.getUsername();
				}
			}
		}
	}
	
	public void removePlayer(int position){
		for(int i=(position-1); i<=8; i++){
			scores[i]=scores[i+1];
			names[i]=names[i+1];
		}
		scores[9]=0;
		names[9]=" ";
	}
	
	public void clearLeaderboard(){
		for(int i=0; i<10; i++){
			names[i] = " ";
			scores[i] = 0;
		}
	}
}

