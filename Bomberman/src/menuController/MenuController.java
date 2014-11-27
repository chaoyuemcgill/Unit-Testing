package menuController;

import gameplayController.GameplayController;
import gameplayModel.Bomb;
import gameplayModel.Flames;

import javax.swing.*;

import Database.Database;

import java.awt.CardLayout;
import java.awt.event.*;
import java.io.IOException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import menuModel.*;
import menuView.*;

public class MenuController implements ActionListener {
	
	private Player currentPlayer;
	private GameplayController gameplayCtrl;
	
	private JFrame menuFrame;
	private JPanel mainPanel;
	private CardLayout layout;
	private LoginMenuPanel loginPanel;
	private CreateAccountPanel createAccPanel;
	private LeaderboardPanel leaderboard;
	private MainMenuPanel mainMenuPanel;
	private Leaderboard updater;
	private PauseMenuPanel pauseMenuPanel;
	private LoadGamePanel loadGamePanel;
	
	private Database database;
	
	private int leaderboardReturn, loadGameReturn, chosenLevel;
	private int prePauseScore;
	
	public MenuController() {
		
		database = new Database();
		
		setupLookAndFeel();
		
		layout = new CardLayout();
		
		updater = new Leaderboard(database);
		
		loginPanel = new LoginMenuPanel(this);
		createAccPanel = new CreateAccountPanel(this);
		mainMenuPanel = new MainMenuPanel(this);
		leaderboard = new LeaderboardPanel(this, updater);
		pauseMenuPanel = new PauseMenuPanel(this);
		loadGamePanel = new LoadGamePanel(this);

		mainPanel = new JPanel();
		mainPanel.setLayout(layout);
		mainPanel.add(loginPanel, "Login");
		mainPanel.add(createAccPanel, "Create");
		mainPanel.add(mainMenuPanel, "Main");
		mainPanel.add(leaderboard, "Leaderboard");
		mainPanel.add(pauseMenuPanel, "Pause");
		mainPanel.add(loadGamePanel, "LoadGame");

		menuFrame = new JFrame("Bomberman");
		menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menuFrame.getContentPane().add(mainPanel);
		menuFrame.pack();
		menuFrame.setLocation(400, 200);
		menuFrame.setVisible(true);
		
		leaderboardReturn = 0;
		loadGameReturn = 0;
		chosenLevel = 0;
		
		prePauseScore = 0;
	}
	
	
	public void actionPerformed(ActionEvent event) {
		
		//***********Login Panel Buttons**************
		if (event.getSource() == loginPanel.getLoginButton()) {

				if((currentPlayer = database.getPlayer(loginPanel.getUsername(), loginPanel.getPassword())) != null)
					layout.show(mainPanel, "Main");
				else {
					JOptionPane.showMessageDialog(null, "Incorrect Password Or Username",null, JOptionPane.ERROR_MESSAGE);
					loginPanel.resetTextFields();
				}
		}
		
		
		else if (event.getSource() == loginPanel.getCreateAccButton()) {
			layout.show(mainPanel, "Create");
		
		//***********Create Account buttons**************	
		} else if (event.getSource() == createAccPanel.getBackButton()) {
			layout.show(mainPanel, "Login");
			
		} else if (event.getSource() == createAccPanel.getCreateAccButton()) {
			verifyNewAccount();
		
		//***********MainMenu Buttons**************	
		} else if (event.getSource() == mainMenuPanel.getResumeButton()){
			loadGamePanel.updateLoad(currentPlayer);
			layout.show(mainPanel, "LoadGame");
			loadGameReturn = 0;
			
		} else if (event.getSource() == mainMenuPanel.getNewGameButton()){
			
			Bomb.resetRange();
	
			int maxLevel = currentPlayer.getLevelUnlocked(); 
			if(maxLevel!=0){
			
			String[] levelArray = new String[maxLevel];
			for(int i = 0; i<maxLevel; i++)
				levelArray[i] = String.valueOf(i+1);
			
		    String selectedLevel = (String) JOptionPane.showInputDialog(null, "Level Select", "Choose Starting Level:", JOptionPane.QUESTION_MESSAGE, null, levelArray,  levelArray[0]);
			chosenLevel = Integer.parseInt(selectedLevel)-1;
			}
			else
				chosenLevel = 0;
			menuFrame.setVisible(false);
			gameplayCtrl = new GameplayController(this);
			
		} else if (event.getSource() == mainMenuPanel.getLeaderboardButton()){
			leaderboard.updateLeaderboard();
			layout.show(mainPanel, "Leaderboard");
			leaderboardReturn = 0;
			
		} else if (event.getSource() == mainMenuPanel.getLogoutButton()){
			try {
				database.generateCSV();
				currentPlayer = null;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			layout.show(mainPanel, "Login");
			loginPanel.resetTextFields();
			
		} else if (event.getSource() == mainMenuPanel.getExitButton()){
			try {
				database.generateCSV();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.exit(0);
			
		//************Leaderboard buttons**************
		} else if (event.getSource() == leaderboard.getReturnButton()){
			if(leaderboardReturn == 0)
				layout.show(mainPanel, "Main");
			else if(leaderboardReturn == 1)
				layout.show(mainPanel, "Pause");
			
		//************Load buttons**************		
		} else if(event.getSource() == loadGamePanel.getReturn()){
			if(loadGameReturn == 0)
				layout.show(mainPanel, "Main");
			else if (loadGameReturn ==1)
				layout.show(mainPanel, "Pause");
		} else if(event.getSource() == loadGamePanel.getLoad()){
			menuFrame.setVisible(false);
			gameplayCtrl = new GameplayController(this, currentPlayer.getSavedGameList().get(loadGamePanel.getSaveIndex()).getGameContext());
			prePauseScore = gameplayCtrl.getGameContext().getScore();
			gameplayCtrl.resumeGame();

		//************Pause buttons**************
		} else if (event.getSource() == pauseMenuPanel.getResumeGame()) {
			menuFrame.setVisible(false);
			gameplayCtrl.resumeGame();
		} else if (event.getSource() == pauseMenuPanel.getSave()) {
			String saveName = JOptionPane.showInputDialog(null, "Enter Save Name: ", "Save Game", JOptionPane.QUESTION_MESSAGE);
			currentPlayer.addSavedGame(new SavedGame(saveName, new Date().toString(), gameplayCtrl.getGameContext()));
		} else if (event.getSource() == pauseMenuPanel.getExit()){
			try {
				database.generateCSV();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.exit(0);
		} else if(event.getSource() == pauseMenuPanel.getMainMenu()){
			int choice = JOptionPane.showConfirmDialog(null, "All unsaved progress will be lost. Continue?", null, JOptionPane.OK_CANCEL_OPTION);
			if(choice == 0)
				layout.show(mainPanel, "Main");
				
		} else if(event.getSource() == pauseMenuPanel.getLeaderboard()){
			leaderboard.updateLeaderboard();
			layout.show(mainPanel, "Leaderboard");
			leaderboardReturn = 1;
		} else if(event.getSource() == pauseMenuPanel.getLoad()){
			loadGamePanel.updateLoad(currentPlayer);
			layout.show(mainPanel, "LoadGame");
			loadGameReturn = 1;
		}
		
	}
	
	
	
	private void verifyNewAccount() {

		if(checkUser(createAccPanel.getUsername()) == false) {
			JOptionPane.showMessageDialog(null, "Username must be at least 6 characters and consist of only digits and characters",null, JOptionPane.INFORMATION_MESSAGE);
		} else if(checkPassword(createAccPanel.getPassword()) == false)
			JOptionPane.showMessageDialog(null, "Password must be at least 8 characters long and include the following: a digit, an uppercase and lowercase character, and a special character",null, JOptionPane.INFORMATION_MESSAGE);
		else if(!createAccPanel.getPassword().equals(createAccPanel.getConfirmedPassword()))
			JOptionPane.showMessageDialog(null, "Confirmed password does not match",null, JOptionPane.ERROR_MESSAGE);
		else {
			
			if (database.addPlayer(new Player(createAccPanel.getRealName(),createAccPanel.getUsername(), createAccPanel.getPassword())) == null) {
				JOptionPane.showMessageDialog(null, "Username already exists",null, JOptionPane.ERROR_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "Account Created!",null, JOptionPane.INFORMATION_MESSAGE);
				currentPlayer = database.getPlayer(createAccPanel.getUsername(), createAccPanel.getPassword());
				layout.show(mainPanel, "Main");
			}
        }
        
		createAccPanel.resetTextFields();
	}
    
    private boolean checkUser(String user){
    	Pattern p = Pattern.compile("[^a-z0-9]", Pattern.CASE_INSENSITIVE);
    	Matcher m = p.matcher(user);
    	boolean b = m.find();
    	return (!b && !(user.length()<6));
    }
    
    private boolean checkPassword(String password){
        if (!password.matches(".*[A-Z].*")) return false;

        if (!password.matches(".*[a-z].*")) return false;

        if (!password.matches(".*\\d.*")) return false;
    	
        Pattern p = Pattern.compile("[^a-z0-9]", Pattern.CASE_INSENSITIVE);
    	Matcher m = p.matcher(password);
    	boolean b = m.find();
        if (!b) return false;
        
        if(password.length()<8) return false;
        		
        return true;
    }
    
    public void pause() {
    	menuFrame.setVisible(true);
    	layout.show(mainPanel, "Pause");
    	int newPauseScore = gameplayCtrl.getGameContext().getScore();
    	currentPlayer.updateScore(newPauseScore-prePauseScore);
    	prePauseScore = newPauseScore;
    }
    
    public void gameOver() {
    	menuFrame.setVisible(true);
    	layout.show(mainPanel, "Main");
    	int newPauseScore = gameplayCtrl.getGameContext().getScore();
    	currentPlayer.updateScore(newPauseScore-prePauseScore);
    	prePauseScore = 0;
    }
    
    public int getSelectedLevel(){
    	return chosenLevel;
    }
	
	private void setupLookAndFeel() {
		
		try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MenuController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MenuController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MenuController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MenuController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
	}
}

