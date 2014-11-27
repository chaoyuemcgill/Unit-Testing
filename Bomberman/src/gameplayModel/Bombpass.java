//*****************************************************************************************************
//  Bombpass.java
//
//*****************************************************************************************************

package gameplayModel;

public class Bombpass extends PowerUp {
	
	int[] imageParam = {216, 259};
	
	public Bombpass(int x, int y) {
		super(x, y);
		
		isPermanent = false;
		img = generateImage(imageParam);
	}
}