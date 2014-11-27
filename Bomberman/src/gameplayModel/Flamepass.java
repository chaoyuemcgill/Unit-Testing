//*****************************************************************************************************
//  Flamepass.java
//
//*****************************************************************************************************

package gameplayModel;

public class Flamepass extends PowerUp {
	
	int[] imageParam = {217, 204};
	
	public Flamepass(int x, int y) {
		super(x, y);
		
		isPermanent = false;
		img = generateImage(imageParam);
	}
}