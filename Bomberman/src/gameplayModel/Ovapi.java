//*****************************************************************************************************
//  Ovapi.java
//
//*****************************************************************************************************

package gameplayModel;

public class Ovapi extends Enemy {
	
	public final int POINTS = 2000;
	public final int SPEED = 2;
	public final int SMARTNESS = 2;
	public final boolean WALLPASS = true;
	
	public Ovapi(int x, int y) {
		super(x, y);
		
		initialize();
	}
	
	public Ovapi(int x, int y, int dir) {
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
		
		int[][] animParam = {	{55, 164, 3},
								{1, 164, 3},
								{109, 164, 5}};

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
