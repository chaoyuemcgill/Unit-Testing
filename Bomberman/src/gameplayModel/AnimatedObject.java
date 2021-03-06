//*****************************************************************************************************
//  AnimatedObject.java
//
//*****************************************************************************************************

package gameplayModel;

public abstract class AnimatedObject extends GridObject {
	
	public final int INITIAL_ANIMATION = 0;
	
	protected Animation[] animationList;
	private Animation currentAnimation;

	protected boolean isDead, isObsolete;
	
	private int animationNumber;

	protected int animCycleParam;

	protected int counter;
	
	public AnimatedObject (int x, int y) {
		
		super(x, y);
		
		generateAnimationList();
    	
    	currentAnimation = animationList[INITIAL_ANIMATION];
    	animationNumber = INITIAL_ANIMATION;
    	
    	counter = 0;
    	animCycleParam = 3;
    	
    	isDead = false;
    	isObsolete = false;
	}
	
	public void cycleAnimation() {
    	
    	if (counter % animCycleParam == 0) {
			
			if (!isDead) {
				currentAnimation.cycleFrame();
			
			} else {
					
				if (currentAnimation.isAnimDone())
					isObsolete = true;
				else
					currentAnimation.cycleFrame();
			}
		}
		counter++;
    }
	
	public void triggerDeath() {
		currentAnimation = animationList[animationList.length - 1];
		isDead = true;
	}
	
	public boolean isDead() {
		return isDead;
	}

	public boolean isObsolete() {
		return isObsolete;
	}
	
	public abstract void generateAnimationList();
	
	public Animation getCurrentAnimation() {
		return currentAnimation;
	}
	
	public int getAnimationNumber() {
		return animationNumber;
	}
	
	public void setCurrentAnimation(int aT) {
		currentAnimation = animationList[aT];
		currentAnimation.setToInitialFrame();
		animationNumber = aT;
	}
}
