//*****************************************************************************************************
//  Bomb.java
//
//*****************************************************************************************************

package gameplayModel;

import java.util.ArrayList;

import gameplayController.GameplayController;

public class Bomb extends AnimatedObject {
	
	public static enum AnimationType {unexploded, expCenter, expRight, expLeft, expDown, expUp,expVertical, expHorizontal};
	
	public final int TIME_TO_EXPLOSION = 2500;
	
	private ArrayList<Animation> currentAnimations;
	private ArrayList<Integer> animXOffset, animYOffset;
	
	private static int range = 1;
	private int timer, counter, animCycleParam, rightRange, leftRange, downRange, upRange;
	
	private boolean wasTrigByBomb, wasRightRangeChg, wasLeftRangeChg, wasDownRangeChg, wasUpRangeChg;
	
	public Bomb(int x, int y) {
		super(x, y);
		
		currentAnimations = new ArrayList<Animation>();
		animXOffset = new ArrayList<Integer>();
		animYOffset = new ArrayList<Integer>();
		
		timer = TIME_TO_EXPLOSION;
		counter = 0;
		animCycleParam = 3;
		
		
		rightRange = range;
		leftRange = range;
		downRange = range;
		upRange = range;
		setRanges();
		
		wasTrigByBomb = false;
		wasRightRangeChg = false;
		wasLeftRangeChg = false;
		wasDownRangeChg = false;
		wasUpRangeChg = false;
		
		addAnimation(Bomb.AnimationType.unexploded.ordinal(), 0, 0);
	}
	
	public Bomb(int range, int x, int y, int timer, int right, int left, int down, int up) {
		super(x, y);
		
		currentAnimations = new ArrayList<Animation>();
		animXOffset = new ArrayList<Integer>();
		animYOffset = new ArrayList<Integer>();
		
		this.timer = timer;
		counter = 0;
		animCycleParam = 3;
		Bomb.range = range;
		rightRange = right;
		leftRange = left;
		downRange = down;
		upRange = up;
		
		addAnimation(Bomb.AnimationType.unexploded.ordinal(), 0, 0);
	}

	@Override
	public void generateAnimationList() {
		
		int[][] animParam = {	{113, 21, 4, 4, PIXELWIDTH},
								{19, 223, 7, 4, 54},
								{37, 223, 7, 4, 54},
								{1, 223, 7, 4, 54},
								{19, 241, 7, 4, 54},
								{19, 205, 7, 4, 54},
								{37, 205, 7, 4, 54},
								{37, 241, 7, 4, 54}};
		
		animationList = new Animation[AnimationType.values().length];
		
		for (AnimationType type : AnimationType.values()){
			
			int i = type.ordinal();
			
			animationList[i] = new Animation(animParam[i][2]);
			
			for (int j = 0 ; j < animParam[i][3] ; j++){
				
				animationList[i].setFrame(resizeImage(image.getSubimage(animParam[i][0] + animParam[i][4] * j, animParam[i][1], GridObject.PIXELWIDTH, GridObject.PIXELHEIGHT), ZOOM), j);
			}
			
			for (int n = (animParam[i][2] - animParam[i][3]) ; n > 0 ; n--) {
				
				animationList[i].setFrame(resizeImage(image.getSubimage(animParam[i][0] + animParam[i][4] * n, animParam[i][1], GridObject.PIXELWIDTH, GridObject.PIXELHEIGHT), ZOOM), animParam[i][3] - n);
			}
		}
	}
	
	public void cycleAnimation() {
		
		if (counter % animCycleParam == 0) {
			
			if (!isDead) {
				
				for (Animation animation : currentAnimations)
					animation.cycleFrame();
			
			} else {
				
				for (int i = 0 ; i < currentAnimations.size() ;) {
					
					if (currentAnimations.get(i).isAnimDone()) {
						removeAnimation(i);
						
						if (currentAnimations.size() == 0)
							isObsolete = true;
						
					} else {
						currentAnimations.get(i).cycleFrame();
						i++;
					}
				}
			}
		}
		counter++;
	}
	
	public void decreaseTimer() {
		timer -= GameplayController.TIMEOUT;
		
		if (timer <= 0 && !isDead) {
			//isDead = true;
			triggerExplosion();
		}
	}
	
	public int getTimer() {
		return timer;
	}

	public void setTimer(int timer) {
		this.timer = timer;
	}

	public void triggerExplosion() {
		
		isDead = true;
		timer = 0;
		
		animCycleParam = 2;
		
		clearAnimation();
		
		addAnimation(Bomb.AnimationType.expCenter.ordinal(), 0, 0);
		
		if (leftRange > 0) {
			
			if (wasLeftRangeChg) {
				for (int i = 1 ; i <= leftRange ; i++)
					addAnimation(Bomb.AnimationType.expHorizontal.ordinal(), -i, 0);
			} else {
				addAnimation(Bomb.AnimationType.expLeft.ordinal(), -leftRange, 0);
				
				for (int i = 1 ; i < leftRange ; i++)
					addAnimation(Bomb.AnimationType.expHorizontal.ordinal(), -i, 0);
			}
		}
		
		if (rightRange > 0) {
			
			if (wasRightRangeChg) {
				for (int i = 1 ; i <= rightRange ; i++)
					addAnimation(Bomb.AnimationType.expHorizontal.ordinal(), i, 0);
			} else {
				addAnimation(Bomb.AnimationType.expRight.ordinal(), rightRange, 0);
				
				for (int i = 1 ; i < rightRange ; i++)
					addAnimation(Bomb.AnimationType.expHorizontal.ordinal(), i, 0);
			}
		}
		
		if (upRange > 0) {
			
			if (wasUpRangeChg) {
				for (int i = 1 ; i <= upRange ; i++)
					addAnimation(Bomb.AnimationType.expVertical.ordinal(), 0, -i);
			} else {
				addAnimation(Bomb.AnimationType.expUp.ordinal(), 0, -upRange);
				
				for (int i = 1 ; i < upRange ; i++)
					addAnimation(Bomb.AnimationType.expVertical.ordinal(), 0, -i);
			}
		}
		
		if (downRange > 0) {
			
			if (wasDownRangeChg) {
				for (int i = 1 ; i <= downRange ; i++)
					addAnimation(Bomb.AnimationType.expVertical.ordinal(), 0, i);
			} else {
				addAnimation(Bomb.AnimationType.expDown.ordinal(), 0, downRange);
				
				for (int i = 1 ; i < downRange ; i++)
					addAnimation(Bomb.AnimationType.expVertical.ordinal(), 0, i);
			}
		}
	}
	
	private void setRanges() {
		
		boolean isNotAlignedWithRow = (yPosition % (EFFECTIVE_PIXEL_HEIGHT * 2)) == 0;
		
		if (isNotAlignedWithRow) {
			rightRange = 0;
			leftRange = 0;
		} else {
			if ((xPosition + rightRange * EFFECTIVE_PIXEL_WIDTH) >= EFFECTIVE_PIXEL_WIDTH * (GridMap.MAPWIDTH - 2))
				rightRange = (GridMap.MAPWIDTH - 2) - xPosition / EFFECTIVE_PIXEL_WIDTH;
			if ((xPosition - leftRange * EFFECTIVE_PIXEL_WIDTH) <= EFFECTIVE_PIXEL_WIDTH)
				leftRange = xPosition / EFFECTIVE_PIXEL_WIDTH - 1;
		}
		
		
		boolean isNotAlignedWithColumn = (xPosition % (EFFECTIVE_PIXEL_WIDTH * 2)) == 0;
		
		if (isNotAlignedWithColumn) {
			downRange = 0;
			upRange = 0;
		} else {
			if ((yPosition + downRange * EFFECTIVE_PIXEL_HEIGHT) >= EFFECTIVE_PIXEL_HEIGHT * (GridMap.MAPHEIGHT - 2))
				downRange = (GridMap.MAPHEIGHT - 2) - yPosition / EFFECTIVE_PIXEL_HEIGHT;
			if ((yPosition - upRange * EFFECTIVE_PIXEL_HEIGHT) <= EFFECTIVE_PIXEL_HEIGHT)
				upRange = yPosition / EFFECTIVE_PIXEL_HEIGHT - 1;
		}
	}
	
	private void addAnimation(int animType, int xOffset, int yOffset) {
		
		currentAnimations.add(new Animation(animationList[animType]));
		animXOffset.add(xOffset);
		animYOffset.add(yOffset);	
	}
	
	private void removeAnimation(int index) {
		currentAnimations.remove(index);
		animXOffset.remove(index);
		animYOffset.remove(index);
	}
	
	private void clearAnimation() {
		currentAnimations.clear();
		animXOffset.clear();
		animYOffset.clear();
	}
	
	public boolean wasTrigByBomb() {
		return wasTrigByBomb;
	}

	public void setWasTrigByBomb() {
		wasTrigByBomb = true;
	}

	public static int getRange() {
		return range;
	}

	public static void increaseRange() {
		range++;
	}
	
	public static void resetRange() {
		range = 1;
	}
	
	public int getRightRange() {
		return rightRange;
	}

	public void setRightRange(int rightRange) {
		wasRightRangeChg = true;
		this.rightRange = rightRange;
	}

	public int getLeftRange() {
		return leftRange;
	}

	public void setLeftRange(int leftRange) {
		wasLeftRangeChg = true;
		this.leftRange = leftRange;
	}

	public int getDownRange() {
		return downRange;
	}

	public void setDownRange(int downRange) {
		wasDownRangeChg = true;
		this.downRange = downRange;
	}

	public int getUpRange() {
		return upRange;
	}

	public void setUpRange(int upRange) {
		wasUpRangeChg = true;
		this.upRange = upRange;
	}
	
	public ArrayList<Animation> getCurrentAnimations() {
		return currentAnimations;
	}
	
	public ArrayList<Integer> getAnimXOffset() {
		return animXOffset;
	}
	
	public ArrayList<Integer> getAnimYOffset() {
		return animYOffset;
	}
	
	public ArrayList<String> toCSVEntry() {
		
		ArrayList<String> entryList = new ArrayList<String>();
		
		entryList.add(Integer.toString(xPosition));
		entryList.add(Integer.toString(yPosition));
		entryList.add(Integer.toString(timer));
		entryList.add(Integer.toString(rightRange));
		entryList.add(Integer.toString(leftRange));
		entryList.add(Integer.toString(downRange));
		entryList.add(Integer.toString(upRange));

		return entryList; 
	}
}


