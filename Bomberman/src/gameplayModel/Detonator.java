//*****************************************************************************************************
//  Detonator.java
//
//*****************************************************************************************************

package gameplayModel;

public class Detonator extends PowerUp {
	
	int[] imageParam = {198, 259};
	
	public Detonator(int x, int y) {
		super(x, y);
		
		isPermanent = false;
		img = generateImage(imageParam);
	}
}