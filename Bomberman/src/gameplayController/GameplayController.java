//*****************************************************************************************************
//  GameplayController.java
//
//*****************************************************************************************************

package gameplayController;

import gameplayModel.*;
import gameplayView.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.ArrayDeque;

import javax.swing.JFrame;
import javax.swing.JViewport;
import javax.swing.Timer;

import menuController.MenuController;


public class GameplayController implements ActionListener {
	
	public static final int TIMEOUT = 50;
	public static final int VIEW_PORT_WIDTH = 16 * GridObject.EFFECTIVE_PIXEL_WIDTH;
	
	private boolean placeBomb;
	
	private ArrayDeque<Integer> activeDirectionKeys;
    
	private MenuController menuCtrl;
	private GameContext gameContext;
	private GridMap gridMap;
	private ArtificialIntelligence AI;
	private CollisionDetector colDetect;
	private Timer timer;
	private GameplayKeyListener keyListener = new GameplayKeyListener();
	private JFrame gameFrame;
	private GameplayPanel gamePanel;
	private GameStatusPanel gameStatusPanel;
	private JViewport gameView;
	
	private Bomberman bomberman;
	private ArrayList<Concrete> concreteLayout;
	private ArrayList<Brick> bricks;
	private PowerUp powerup;
	private ArrayList<Bomb> bombs;
	private ArrayList<Bomb> unexplodedBombs;
	private ArrayList<Enemy> enemies;
	private Exitway exitway;
	
	
	public GameplayController(MenuController menuCtrl) {
		
		this.menuCtrl = menuCtrl;
		//gameContext = new GameContext(menuCtrl.getSelectedLevel());
		gameContext = new GameContext();
		initialize();
    	setupGameFrame(true);
    	timer = new Timer(TIMEOUT, this);
    	timer.start();
    }
	
	public GameplayController(MenuController menuCtrl, GameContext gameContext) {
		
		this.menuCtrl = menuCtrl;
		this.gameContext = gameContext;
		initialize();
    	setupGameFrame(false);
    	timer = new Timer(TIMEOUT, this);
    }
	
	public void initialize() {
		activeDirectionKeys = new ArrayDeque<Integer>();
    	gridMap = gameContext.getGridMap();
    	
    	
    	exitway = gameContext.getGridMap().getExitway();
    	bomberman = gameContext.getGridMap().getBomberman();
    	concreteLayout = gameContext.getGridMap().getConcreteLayout();
    	bricks = gameContext.getGridMap().getBricks();
    	powerup = gameContext.getGridMap().getPowerUps();
    	bombs = gameContext.getGridMap().getBombs();
    	unexplodedBombs = new ArrayList<Bomb>();
    	enemies = gameContext.getGridMap().getEnemies();
    	
        colDetect = new CollisionDetector(gameContext);
    	AI = new ArtificialIntelligence(bomberman,enemies, bricks, bombs,colDetect);
	}
    
    public void actionPerformed(ActionEvent event){
    	
    	if (!bomberman.isDead()) {
    		
	    	if (activeDirectionKeys.size() != 0) {
	    		
		    	switch (activeDirectionKeys.getFirst().intValue()) {
				case KeyEvent.VK_UP:
					if (bomberman.getAnimationNumber() != Bomberman.AnimationType.up.ordinal())
						bomberman.setCurrentAnimation(Bomberman.AnimationType.up.ordinal());
					else
						bomberman.getCurrentAnimation().cycleFrame();
					
					bomberman.setYPosition(bomberman.getYPosition() - bomberman.getSpeed());
					break;
				case KeyEvent.VK_DOWN:
					if (bomberman.getAnimationNumber() != Bomberman.AnimationType.down.ordinal())
						bomberman.setCurrentAnimation(Bomberman.AnimationType.down.ordinal());
					else
						bomberman.getCurrentAnimation().cycleFrame();
					
					bomberman.setYPosition(bomberman.getYPosition() + bomberman.getSpeed());
					break;
				case KeyEvent.VK_LEFT:
					if (bomberman.getAnimationNumber() != Bomberman.AnimationType.left.ordinal())
						bomberman.setCurrentAnimation(Bomberman.AnimationType.left.ordinal());
					else
						bomberman.getCurrentAnimation().cycleFrame();
					
					bomberman.setXPosition(bomberman.getXPosition() - bomberman.getSpeed());
					break;
				case KeyEvent.VK_RIGHT:
					if (bomberman.getAnimationNumber()!= Bomberman.AnimationType.right.ordinal())
						bomberman.setCurrentAnimation(Bomberman.AnimationType.right.ordinal());
					else
						bomberman.getCurrentAnimation().cycleFrame();
					
					bomberman.setXPosition(bomberman.getXPosition() + bomberman.getSpeed());
					break;
				case KeyEvent.VK_ENTER:
					if (bomberman.getAnimationNumber() != Bomberman.AnimationType.death.ordinal())
						bomberman.setCurrentAnimation(Bomberman.AnimationType.death.ordinal());
					bomberman.getCurrentAnimation().cycleFrame();
					break;
		    	}
	    	}
    	} else if (bomberman.isObsolete()) {
    		
    		timer.stop();
    		
    		if (gameContext.getLivesLeft() > 0) {
	    		
    			gameContext.decreaseLivesLeft();
	    		gameContext.initializeGameTime();
	    		gameContext.restartMap();
	    		initialize();
	    		
	    		try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    		
	    		timer.start();
    		} else {
    			gameFrame.setVisible(false);
    			menuCtrl.gameOver();
    		}
    		
    		
    	} else {
    		bomberman.cycleAnimation();
    	}
    	
    	AI.updatePosition();
    	
    	updateEnemiesAnim();
    	
    	updateBombsStatus();
    	
    	for (Bomb bomb : bombs) {
    		
    		if (bomb.getTimer() == TIMEOUT) {
    			
    			ArrayList<AnimatedObject> destBricks = colDetect.checkExplBricks(bomb);
    			
    			for (AnimatedObject destBrick : destBricks) {
    				
    				if (destBrick != null) {
	    				for (int i = 0 ; i < bricks.size() ; i++) {
	    					if ((destBrick.getXPosition() == bricks.get(i).getXPosition()) && (destBrick.getYPosition() == bricks.get(i).getYPosition()))
	    						bricks.get(i).triggerDeath();
	    				}
	    				
	    				for (int i = 0 ; i < bombs.size() ; i++) {
	    					
	    					if ((destBrick.getXPosition() == bombs.get(i).getXPosition()) && (destBrick.getYPosition() == bombs.get(i).getYPosition()))
	    						bombs.get(i).setTimer(TIMEOUT * 2);
	    				}
    				}
    			}
    			
    			ArrayList<Enemy> destEnemies = colDetect.checkExplEnemies(bomb);
    			
    			for (Enemy enemy : destEnemies) {
    				
    				if (enemy != null) {
	    				for (int i = 0 ; i < enemies.size() ; i++) {
	    					if ((enemy.getXPosition() == enemies.get(i).getXPosition()) && (enemy.getYPosition() == enemies.get(i).getYPosition())) {
	    						enemies.get(i).triggerDeath();
	    						gameContext.increaseScore(enemies.get(i).getPoints());
	    					}
	    				}
    				}

    			}
    			
    			if (colDetect.checkExplBomberman(bomb, bomberman))
    				bomberman.triggerDeath();
    		}
    	}
    	
    	for (int i = 0 ; i < bricks.size() ;) {
    		
    		if (bricks.get(i).isObsolete()) {
    			bricks.remove(i);
    		} else if (bricks.get(i).isDead()) {
    			bricks.get(i).cycleAnimation();
				i++;
    		} else {
    			i++;
    		}
    	}
    	
    	if (placeBomb)
    		addBomb();
    	
    	if (gameContext.getLevelSpecification()[8] == 0) {
    		gridMap.decreaseSpawnTimer();
    	}
    	
    	
    	if (gameContext.getGameTime() <= 0 && !gameContext.getEndGameEnemiesStatus()) {
    		
    		gameContext.setEndGameEnemiesStatus(true);
    		
    		int[] newEnemies = {0, 0, 0, 0, 0, 0, 0, 15};
    		
    		gridMap.generateEnemies(newEnemies);
    	}
    	
    	updateViewport();
    	gameStatusPanel.updateGameStatus();
    	gameContext.decreaseGameTime();
    }
    
    private class GameplayKeyListener implements KeyListener {
		
		public void keyPressed(KeyEvent event){
			
			if (!activeDirectionKeys.contains(event.getKeyCode())) {
				
				switch (event.getKeyCode()) {
				case KeyEvent.VK_UP:
					activeDirectionKeys.addFirst(KeyEvent.VK_UP);
					break;
				case KeyEvent.VK_DOWN:
					activeDirectionKeys.addFirst(KeyEvent.VK_DOWN);
					break;
				case KeyEvent.VK_LEFT:
					activeDirectionKeys.addFirst(KeyEvent.VK_LEFT);
					break;
				case KeyEvent.VK_RIGHT:
					activeDirectionKeys.addFirst(KeyEvent.VK_RIGHT);
					break;
				case KeyEvent.VK_ENTER:
					if (timer.isRunning()) {
						timer.stop();
						gameFrame.setVisible(false);
						menuCtrl.pause();
					}
					break;
				case KeyEvent.VK_X:
					placeBomb = true;
					break;
				case KeyEvent.VK_Z:
					Bomb.increaseRange();
					//gameContext.increaseScore(1000);
					break;
				}
			}
		}
		
		public void keyReleased(KeyEvent event){
			
			switch (event.getKeyCode()) {
			case KeyEvent.VK_UP:
				activeDirectionKeys.remove(KeyEvent.VK_UP);
				break;
			case KeyEvent.VK_DOWN:
				activeDirectionKeys.remove(KeyEvent.VK_DOWN);;
				break;
			case KeyEvent.VK_LEFT:
				activeDirectionKeys.remove(KeyEvent.VK_LEFT);;
				break;
			case KeyEvent.VK_RIGHT:
				activeDirectionKeys.remove(KeyEvent.VK_RIGHT);;
				break;
			case KeyEvent.VK_ENTER:
				
				break;
			case KeyEvent.VK_X:
				placeBomb = false;
				break;
			case KeyEvent.VK_Z:
				
				break;
			}
		}
		
		public void keyTyped(KeyEvent event){}
	}
    
    private void updateEnemiesAnim() {
    	
    	for (int i = 0 ; i < enemies.size() ;) {
    		
    		if (!enemies.get(i).isDead()) {
        		
    			if (enemies.get(i).getDirection() == 1 || enemies.get(i).getDirection() == 2) {
        			if (enemies.get(i).getAnimationNumber() != Enemy.AnimationType.left.ordinal())
        				enemies.get(i).setCurrentAnimation(Enemy.AnimationType.left.ordinal());
        			enemies.get(i).cycleAnimation();
        		} else {
        			if (enemies.get(i).getAnimationNumber() != Enemy.AnimationType.right.ordinal())
        				enemies.get(i).setCurrentAnimation(Enemy.AnimationType.right.ordinal());
        			enemies.get(i).cycleAnimation();
        		}
    			i++;
    		} else if (enemies.get(i).isObsolete()) {
    			
    			System.out.println(enemies.size());
    			enemies.remove(i);
    		} else {
    			enemies.get(i).cycleAnimation();
    			i++;
    		}
    		

    	}
    }
    
    private void updateBombsStatus() {
    	
    	for (int i = 0 ; i < bombs.size() ;) {
    		
    		if (bombs.get(i).isObsolete()) {
    			bombs.remove(i);
    		} else {
    			bombs.get(i).cycleAnimation();
				bombs.get(i).decreaseTimer();
				i++;
    		}
    	}
    	
    	for (int i = 0 ; i < unexplodedBombs.size() ;) {
    		
    		if (unexplodedBombs.get(i).isDead()) {
    			unexplodedBombs.remove(i);
				bomberman.increaseBombsLeft();
			} else {
				i++;
			}
    	}
    }
    
    private void addBomb() {
    	
    	int xPosition, yPosition;
    	
    	if ((bomberman.getXPosition() % GridObject.EFFECTIVE_PIXEL_WIDTH) < (GridObject.EFFECTIVE_PIXEL_WIDTH/2))
			xPosition = GridObject.EFFECTIVE_PIXEL_WIDTH * (bomberman.getXPosition()/GridObject.EFFECTIVE_PIXEL_WIDTH);
		else
			xPosition = GridObject.EFFECTIVE_PIXEL_WIDTH * (bomberman.getXPosition()/GridObject.EFFECTIVE_PIXEL_WIDTH + 1);
			
		if ((bomberman.getYPosition() % GridObject.EFFECTIVE_PIXEL_HEIGHT) < (GridObject.EFFECTIVE_PIXEL_HEIGHT/2))
			yPosition = GridObject.EFFECTIVE_PIXEL_HEIGHT * (bomberman.getYPosition()/GridObject.EFFECTIVE_PIXEL_HEIGHT);
		else
			yPosition = GridObject.EFFECTIVE_PIXEL_HEIGHT * (bomberman.getYPosition()/GridObject.EFFECTIVE_PIXEL_HEIGHT + 1);
			
		boolean canAddBomb = true;
		
		if (bombs.size() != 0) {
		
			int i = 0;
			
			while (canAddBomb && i < bombs.size()) {
				if (bombs.get(i).getXPosition() == xPosition && bombs.get(i).getYPosition() == yPosition)
					canAddBomb = false;
				
				i++;
			}
		}
		
		if (canAddBomb && bomberman.getBombsLeft() != 0) {
			
			Bomb tempBomb = new Bomb(xPosition,yPosition);
			
			bombs.add(tempBomb);
			unexplodedBombs.add(tempBomb);
			bomberman.decreaseBombsLeft();
		}
		
    }
    
    @SuppressWarnings("serial")
	private void setupGameFrame(boolean isVisible) {
    	
    	gamePanel = new GameplayPanel(keyListener) {
    		
    		public void paintComponent(Graphics page){
    			
    			super.paintComponent(page);
    			
    			Graphics2D g2d=(Graphics2D)page;
    			
    			
    			
    			if (exitway != null)
    				g2d.drawImage(exitway.getImage(), exitway.getXPosition(), exitway.getYPosition(),gamePanel);
    			
    			if (powerup != null)
    				g2d.drawImage(powerup.getImage(), powerup.getXPosition(), powerup.getYPosition(),gamePanel);
    			
    			for (Bomb bomb : bombs) {
    				
    				for (int i = 0 ; i < bomb.getCurrentAnimations().size() ; i++)
    					g2d.drawImage(bomb.getCurrentAnimations().get(i).getCurrentFrame(),
    							bomb.getXPosition() + bomb.getAnimXOffset().get(i)*GridObject.EFFECTIVE_PIXEL_WIDTH,
    							bomb.getYPosition() + bomb.getAnimYOffset().get(i)*GridObject.EFFECTIVE_PIXEL_HEIGHT, gamePanel);
    			}
    			
    			for (Brick brick : bricks)
    				g2d.drawImage(brick.getCurrentAnimation().getCurrentFrame(),brick.getXPosition(),brick.getYPosition(),gamePanel);
    			
    			for (Enemy enemy : enemies)
    				g2d.drawImage(enemy.getCurrentAnimation().getCurrentFrame(),enemy.getXPosition(),enemy.getYPosition(),gamePanel);
    			
    			g2d.drawImage(bomberman.getCurrentAnimation().getCurrentFrame(),bomberman.getXPosition(),bomberman.getYPosition(),gamePanel);
    			
    			for (Concrete block : concreteLayout)
    				g2d.drawImage(block.getImage(),block.getXPosition(),block.getYPosition(), gamePanel);
    		}
    	};
    	
    	gameStatusPanel = new GameStatusPanel(gameContext);
    	
    	gameView = new JViewport();
    	gameView.setView(gamePanel);
    	
    	gameFrame = new JFrame("Bomberman");
    	gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	gameFrame.setResizable(false);
    	gameFrame.getContentPane().setLayout(new BorderLayout());
    	gameFrame.getContentPane().add(gameStatusPanel, BorderLayout.NORTH);
    	gameFrame.getContentPane().add(gameView);
    	gameFrame.getContentPane().setPreferredSize(new Dimension(VIEW_PORT_WIDTH, GameplayPanel.HEIGHT + GameStatusPanel.HEADERHEIGHT));
    	gameFrame.pack();
    	gameFrame.setLocation(400, 200);
    	gameFrame.setVisible(isVisible);
    }
    
    public GameContext getGameContext() {
		return gameContext;
	}
    
    public void resumeGame() {
    	gameFrame.setVisible(true);
    	timer.start();
    }
    
	private void updateViewport() {
    	
    	if (bomberman.getXPosition() + GridObject.EFFECTIVE_PIXEL_WIDTH/2 <= VIEW_PORT_WIDTH/2) {
    		gamePanel.setLocation(0, 0);
    		gamePanel.repaint();
    	} else if (bomberman.getXPosition() + GridObject.EFFECTIVE_PIXEL_WIDTH/2 >= GameplayPanel.WIDTH - VIEW_PORT_WIDTH/2) {
    		gamePanel.setLocation(VIEW_PORT_WIDTH - GameplayPanel.WIDTH, 0);
    		gamePanel.repaint();
    	} else {
    		gamePanel.setLocation(VIEW_PORT_WIDTH/2 - bomberman.getXPosition() - GridObject.EFFECTIVE_PIXEL_WIDTH/2, 0);
			gamePanel.repaint();
    	}
    }
}

