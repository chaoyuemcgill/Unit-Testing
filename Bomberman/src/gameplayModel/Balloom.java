//*****************************************************************************************************
//  Balloom.java
//
//*****************************************************************************************************

package gameplayModel;

public class Balloom extends Enemy {
	
	public final int POINTS = 100;
	public final int SPEED = 2;
	public final int SMARTNESS = 1;
	public final boolean WALLPASS = false;
	
	public Balloom(int x, int y) {
		super(x, y);
		
		initialize();
	}
	
	public Balloom(int x, int y, int dir) {
		super(x, y, dir);
		
		initialize();
	}
	
	public void initialize() {
		points = POINTS;
		speed = SPEED * SPEED_MULTIPLIER;
		smartness = SMARTNESS;
		wallpass = WALLPASS;
	}

	public void generateAnimationList() {
		
		int[][] animParam = {	{72, 39, 4},
								{1, 39, 4},
								{144, 39, 5}};

		animationList = new Animation[AnimationType.values().length];

		for (AnimationType type : AnimationType.values()){
			
			int i = type.ordinal();
			
			animationList[i] = new Animation(animParam[i][2]);
			
			for (int j = 0 ; j < animParam[i][2] ; j++){
			
			animationList[i].setFrame(resizeImage(image.getSubimage(animParam[i][0] + (GridObject.PIXELWIDTH + 2) * j, 
					animParam[i][1], GridObject.PIXELWIDTH, GridObject.PIXELHEIGHT), ZOOM), j);
			}
		}
	}
}
