/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameplayModel;

import java.util.ArrayList;

/**
 *
 * @author Jonti
 */

public class CollisionDetector {
	
	private GameContext gameContext;
	private ArrayList<Brick> bricks;
	private ArrayList<Enemy> enemies;
	private boolean collision;
	
	public CollisionDetector(GameContext gC) {
		
		gameContext = gC;
		bricks = gameContext.getGridMap().getBricks();
		enemies = gameContext.getGridMap().getEnemies();
		boolean collision = false;
	}
	
	public boolean checkCollision(GridObject a, GridObject b) {
       
        /*if(checkExactCollision(a,b))
           return true;
        else*/ if(a.getYPosition()==b.getYPosition())
       {
           if(a.getXPosition()-b.getXPosition()>0)
           {
               if (b.getXPosition()+GridObject.EFFECTIVE_PIXEL_WIDTH >= a.getXPosition())
               {
                   return true;
               }
           }
           if(a.getXPosition()-b.getXPosition()<0)
           {
               if (a.getXPosition()+GridObject.EFFECTIVE_PIXEL_WIDTH >= b.getXPosition())
               {
                   return true;
               }
           }
       }
        else if(a.getXPosition()==b.getXPosition())
       {
           if(a.getYPosition()-b.getYPosition()>0)
           {
               if (b.getYPosition()+GridObject.EFFECTIVE_PIXEL_HEIGHT >= a.getYPosition())
               {
                   return true;
               }
           }
           if(a.getXPosition()-b.getXPosition()<0)
           {
               if (a.getYPosition()+GridObject.EFFECTIVE_PIXEL_HEIGHT >= b.getYPosition())
               {
                   return true;
               }
           }
       }
       return false;
    }
	public boolean checkUpCollision(GridObject bomberman, GridObject b) {
		
		if(Math.abs(bomberman.getXPosition() - b.getXPosition()) < bomberman.MISALIGNMENT_ALLOWED) 
			return ((b.getYPosition() + GridObject.EFFECTIVE_PIXEL_HEIGHT > bomberman.getYPosition()) && (b.getYPosition() < bomberman.getYPosition()));
		
		return false;
	}
	
	public boolean checkDownCollision(GridObject bomberman, GridObject b) {
		
		if(Math.abs(bomberman.getXPosition() - b.getXPosition()) < bomberman.MISALIGNMENT_ALLOWED) 
			return ((bomberman.getYPosition() + GridObject.EFFECTIVE_PIXEL_HEIGHT > b.getYPosition()) && (bomberman.getYPosition() < b.getYPosition()));
			
		return false;
	}
	
	public boolean checkLeftCollision(GridObject bomberman, GridObject b) {
		
		if(bomberman.getYPosition()==b.getYPosition())
			return((b.getXPosition()+GridObject.EFFECTIVE_PIXEL_WIDTH > bomberman.getXPosition())&&(b.getXPosition()< bomberman.getXPosition()));
		
		return false;
	}
	
	public boolean checkRightCollision(GridObject bomberman, GridObject b) {
   
       if(bomberman.getYPosition()==b.getYPosition())
        return((bomberman.getXPosition()+GridObject.EFFECTIVE_PIXEL_WIDTH > b.getXPosition())&&(bomberman.getXPosition()< b.getXPosition()));
       

       return false;
	}

    
    public boolean checkExplBomberman(Bomb bomb, Bomberman bomberman) {
			
    	if (Math.abs(bomb.getYPosition() - bomberman.getYPosition()) < GridObject.EFFECTIVE_PIXEL_HEIGHT) {
    		
    		boolean isInRightRange = (bomb.getXPosition() + (bomb.getRightRange() + 1) * GridObject.EFFECTIVE_PIXEL_WIDTH) >= (bomberman.getXPosition() + 6) && bomb.getXPosition() < bomberman.getXPosition();
    		
    		if (isInRightRange) {
    			return true;
    		}
    		
    		boolean isInLeftRange = (bomb.getXPosition() - (bomb.getLeftRange() + 1) * GridObject.EFFECTIVE_PIXEL_WIDTH) <= (bomberman.getXPosition() - 6) && bomb.getXPosition() > bomberman.getXPosition();
    		
    		if (isInLeftRange) {
    			return true;
			}
		}
		
		if (Math.abs(bomb.getXPosition() - bomberman.getXPosition()) < GridObject.EFFECTIVE_PIXEL_WIDTH) {
			
			boolean isInDownRange = (bomb.getYPosition() + (bomb.getDownRange() + 1) * GridObject.EFFECTIVE_PIXEL_HEIGHT) >= (bomberman.getYPosition() + 2) && bomb.getYPosition() < bomberman.getYPosition();
			
			if (isInDownRange) {
				return true;
			}
			
			boolean isInUpRange = (bomb.getYPosition() - (bomb.getUpRange() + 1) * GridObject.EFFECTIVE_PIXEL_HEIGHT) <= (bomberman.getYPosition() - 2) && bomb.getYPosition() > bomberman.getYPosition();
			
			if (isInUpRange) {
				return true;
			}
		}
		return false;
	}
    
	public ArrayList<Enemy> checkExplEnemies(Bomb bomb) {
    	
		ArrayList<Enemy> destroyedEnemies = new ArrayList<Enemy>();
    	
		for (Enemy enemy : enemies) {
			
			if (Math.abs(bomb.getYPosition() - enemy.getYPosition()) < GridObject.EFFECTIVE_PIXEL_HEIGHT) {
				
				boolean isInRightRange = (bomb.getXPosition() + (bomb.getRightRange() + 1) * GridObject.EFFECTIVE_PIXEL_WIDTH) >= (enemy.getXPosition() + 1) && bomb.getXPosition() < enemy.getXPosition();
				
				if (isInRightRange) {
					destroyedEnemies.add(enemy);
				}
				
				boolean isInLeftRange = (bomb.getXPosition() - (bomb.getLeftRange() + 1) * GridObject.EFFECTIVE_PIXEL_WIDTH) <= (enemy.getXPosition() - 1) && bomb.getXPosition() > enemy.getXPosition();
				
				if (isInLeftRange) {
					destroyedEnemies.add(enemy);
				}
			}
			
			if (Math.abs(bomb.getXPosition() - enemy.getXPosition()) < GridObject.EFFECTIVE_PIXEL_WIDTH) {
				
				boolean isInDownRange = (bomb.getYPosition() + (bomb.getDownRange() + 1) * GridObject.EFFECTIVE_PIXEL_HEIGHT) >= (enemy.getYPosition() + 1) && bomb.getYPosition() < enemy.getYPosition();
				
				if (isInDownRange) {
					destroyedEnemies.add(enemy);
				}
				
				boolean isInUpRange = (bomb.getYPosition() - (bomb.getUpRange() + 1) * GridObject.EFFECTIVE_PIXEL_HEIGHT) <= (enemy.getYPosition() - 1) && bomb.getYPosition() > enemy.getYPosition();
				
				if (isInUpRange) {
					destroyedEnemies.add(enemy);
				}
			}
		}
		return destroyedEnemies;
	}
	
	public ArrayList<AnimatedObject> checkExplBricks(Bomb bomb) {
    	
		ArrayList<AnimatedObject> destroyedObjs = new ArrayList<AnimatedObject>();
		
		destroyedObjs.add(null);
		destroyedObjs.add(null);
		destroyedObjs.add(null);
		destroyedObjs.add(null);
    	
		for (Brick brick : bricks)
			adjustRanges(bomb, brick, destroyedObjs);
		
		for (Bomb bombOnMap : gameContext.getGridMap().getBombs()) {
			
			if (!bombOnMap.isDead() && !(bombOnMap.getXPosition() == bomb.getXPosition() && bombOnMap.getYPosition() == bomb.getYPosition()))
				adjustRanges(bomb, bombOnMap, destroyedObjs);
		}
		return destroyedObjs;
	}
	
	private void adjustRanges(Bomb bomb, AnimatedObject animObj,ArrayList<AnimatedObject> destroyedObjs) {
		
		if (bomb.getYPosition() == animObj.getYPosition()) {
			
			boolean isInRightRange = (bomb.getXPosition() + (bomb.getRightRange() + 1) * GridObject.EFFECTIVE_PIXEL_WIDTH) >= animObj.getXPosition() && bomb.getXPosition() < animObj.getXPosition();
			
			if (isInRightRange) {
				
				int newRightRange = (animObj.getXPosition() - bomb.getXPosition()) / GridObject.EFFECTIVE_PIXEL_WIDTH - 1;
				
				if (bomb.getRightRange() > newRightRange) {
					bomb.setRightRange(newRightRange);
					destroyedObjs.set(0, animObj);
				}
			}
			
			boolean isInLeftRange = (bomb.getXPosition() - (bomb.getLeftRange()) * GridObject.EFFECTIVE_PIXEL_WIDTH) <= animObj.getXPosition() && bomb.getXPosition() > animObj.getXPosition();
			
			if (isInLeftRange) {
				
				int newLeftRange = (bomb.getXPosition() - animObj.getXPosition()) / GridObject.EFFECTIVE_PIXEL_WIDTH - 1;
				
				if (bomb.getLeftRange() > newLeftRange) {
					bomb.setLeftRange(newLeftRange);
					destroyedObjs.set(1, animObj);
				}
			}
		}
		
		if (bomb.getXPosition() == animObj.getXPosition()) {
			
			boolean isInDownRange = (bomb.getYPosition() + (bomb.getDownRange() + 1) * GridObject.EFFECTIVE_PIXEL_HEIGHT) >= animObj.getYPosition() && bomb.getYPosition() < animObj.getYPosition();
			
			if (isInDownRange) {
				
				int newDownRange = (animObj.getYPosition() - bomb.getYPosition()) / GridObject.EFFECTIVE_PIXEL_HEIGHT - 1;
				
				if (bomb.getDownRange() > newDownRange) {
					bomb.setDownRange(newDownRange);
					destroyedObjs.set(2, animObj);
				}
			}
			
			boolean isInUpRange = (bomb.getYPosition() - (bomb.getUpRange()) * GridObject.EFFECTIVE_PIXEL_HEIGHT) <= animObj.getYPosition() && bomb.getYPosition() > animObj.getYPosition();
			
			if (isInUpRange) {
				
				int newUpRange = (bomb.getYPosition() - animObj.getYPosition()) / GridObject.EFFECTIVE_PIXEL_HEIGHT - 1;
				
				if (bomb.getUpRange() > newUpRange) {
					bomb.setUpRange(newUpRange);
					destroyedObjs.set(3, animObj);
				}
			}
		}
	}
	
	public boolean checkExactCollision(Bomberman a, GridObject b) {
		return (Math.abs(a.xPosition - b.xPosition) < a.MISALIGNMENT_ALLOWED && (a.yPosition == b.yPosition)) || (Math.abs(a.yPosition - b.yPosition) < a.MISALIGNMENT_ALLOWED && (a.xPosition == b.xPosition));	
    }
}


