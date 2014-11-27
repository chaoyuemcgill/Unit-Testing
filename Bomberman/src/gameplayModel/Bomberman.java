//*****************************************************************************************************
//  GridObject.java
//
//*****************************************************************************************************

package gameplayModel;

import java.util.ArrayList;

public class Bomberman extends AnimatedObject {

	public static enum AnimationType {right, left, down, up, death};
	
	public final int INITIAL_SPEED = 4,SPEED_INCREMENT = 2, MISALIGNMENT_ALLOWED = 16;
	
	private int speed, bombsAvailable, bombsLeft;

	private ArrayList<PowerUp> powerUpsAcquired;
	
	public Bomberman(int x, int y) {
		
		super(x, y);
		
		speed = INITIAL_SPEED;
		bombsAvailable = 3;
		bombsLeft = bombsAvailable;
		
		powerUpsAcquired = new ArrayList<PowerUp>();
	}
	
	public Bomberman(int x, int y, int speed, int bombsAvailable, int bombsLeft, ArrayList<PowerUp> powerUpsAcquired) {
		
		super(x, y);
		
		this.speed = speed;
		this.bombsAvailable = bombsAvailable;
		this.bombsLeft = bombsLeft;
		
		this.powerUpsAcquired = powerUpsAcquired;
	}
	
	public void generateAnimationList(){
		
		int[][] animParam = {	{50, 3, 4},
								{50, 21, 4},
								{2, 3, 3},
								{2, 21, 3},
								{113, 3, 7}};
		
		animationList = new Animation[AnimationType.values().length];
		
		for (AnimationType type : AnimationType.values()){
			
			int i = type.ordinal();
			
			animationList[i] = new Animation(animParam[i][2]);
			
			for (int j = 0 ; j < animParam[i][2] ; j++){
				
				animationList[i].setFrame(resizeImage(image.getSubimage(animParam[i][0] + GridObject.PIXELWIDTH * j, animParam[i][1], GridObject.PIXELWIDTH, GridObject.PIXELHEIGHT), ZOOM), j);
			}
		}
	}
	
	public void setXPosition(int xPosition) {
		
		int yError = (this.yPosition - EFFECTIVE_PIXEL_HEIGHT) % (EFFECTIVE_PIXEL_HEIGHT * 2);
		
    	boolean isInXRange = (xPosition >= EFFECTIVE_PIXEL_WIDTH) && (xPosition <= EFFECTIVE_PIXEL_WIDTH * (GridMap.MAPWIDTH - 2));
    	boolean isAlignedWithRow = (yError) == 0;
    	boolean isBelowRow = (yError) <= MISALIGNMENT_ALLOWED;
    	boolean isAboveRow = (yError) >= (EFFECTIVE_PIXEL_HEIGHT * 2 - MISALIGNMENT_ALLOWED);
    	
    	if (isAlignedWithRow && isInXRange) {
			this.xPosition = xPosition;
    	} else if (isAboveRow && isInXRange && yError <= (EFFECTIVE_PIXEL_HEIGHT * 2 - MISALIGNMENT_ALLOWED + speed)) {
    		this.xPosition = xPosition;
    		this.yPosition += speed;
    	} else if (isAboveRow && isInXRange && yError > (EFFECTIVE_PIXEL_HEIGHT * 2 - MISALIGNMENT_ALLOWED + speed)) {
    		this.xPosition = xPosition;
    		this.yPosition += 2;
    	} else if (isBelowRow && isInXRange && yError >= speed) {
    		this.xPosition = xPosition;
    		this.yPosition -= speed;
    	} else if (isBelowRow && isInXRange && yError < speed) {
    		this.xPosition = xPosition;
    		this.yPosition -= 2;
    	}
    }

    public int getYPosition() {
        return yPosition;
    }

    public void setYPosition(int yPosition) {
    	
    	int xError = (this.xPosition - EFFECTIVE_PIXEL_WIDTH) % (EFFECTIVE_PIXEL_WIDTH * 2);
    	
    	boolean isInYRange = (yPosition >= EFFECTIVE_PIXEL_HEIGHT) && (yPosition <= EFFECTIVE_PIXEL_HEIGHT * (GridMap.MAPHEIGHT - 2));
    	boolean isAlignedWithColumn = ((xError) == 0);
    	boolean isRightFromColumn = (xError) <= MISALIGNMENT_ALLOWED;
    	boolean isLeftFromColumn = (xError) >= (EFFECTIVE_PIXEL_HEIGHT * 2 - MISALIGNMENT_ALLOWED);
    	
    	if (isAlignedWithColumn && isInYRange) {
    		this.yPosition = yPosition;
    	} else if (isRightFromColumn && isInYRange && xError >= speed) {
    		this.yPosition = yPosition;
    		this.xPosition -= speed;
    	} else if (isRightFromColumn && isInYRange && xError < speed) {
        	this.yPosition = yPosition;
        	this.xPosition -= 2;
    	} else if (isLeftFromColumn && isInYRange && xError <= (EFFECTIVE_PIXEL_HEIGHT * 2 - MISALIGNMENT_ALLOWED + speed)) {
    		this.yPosition = yPosition;
    		this.xPosition += speed;
    	} else if (isLeftFromColumn && isInYRange && xError > (EFFECTIVE_PIXEL_HEIGHT * 2 - MISALIGNMENT_ALLOWED + speed)) {
    		this.yPosition = yPosition;
    		this.xPosition += 2;
    	}
    }

	public void addPowerUp(PowerUp powerUp) {
		powerUpsAcquired.add(powerUp);
	}

	public void removePowerUp(PowerUp powerUp) {
		powerUpsAcquired.remove(powerUp);
	}

	public void increaseSpeed() {
		speed += SPEED_INCREMENT;
	}
	
	public int getSpeed(){
		return speed;
	}
	
	public void increaseBombsAvailable() {
		bombsAvailable++;
	}
	
	public int getBombsAvailable() {
		return bombsAvailable;
	}
	
	public void increaseBombsLeft() {
		if (bombsLeft < bombsAvailable)
			bombsLeft++;
	}
	
	public void decreaseBombsLeft() {
		if (bombsLeft > 0)
			bombsLeft--;
	}
	
	public int getBombsLeft() {
		return bombsLeft;
	}
	
	public ArrayList<String> toCSVEntry() {
		
		ArrayList<String> entryList = new ArrayList<String>();
		
		entryList.add(Integer.toString(xPosition));
		entryList.add(Integer.toString(yPosition));
		entryList.add(Integer.toString(speed));
		entryList.add(Integer.toString(bombsAvailable));
		entryList.add(Integer.toString(bombsLeft));
		entryList.add("PowerUpAcquired");
		
		for (PowerUp powerup : powerUpsAcquired) {
			entryList.add(powerup.getClass().toString());
			entryList.add(Integer.toString(powerup.getXPosition()));
			entryList.add(Integer.toString(powerup.getYPosition()));
		}
		return entryList; 
	}
}


