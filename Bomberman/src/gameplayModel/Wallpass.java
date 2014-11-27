//*****************************************************************************************************
//  Wallpass.java
//
//*****************************************************************************************************

package gameplayModel;

public class Wallpass extends PowerUp {
	
	int[] imageParam = {217, 241};
	
	public Wallpass(int x, int y) {
		super(x, y);
		
		isPermanent = false;
		img = generateImage(imageParam);
	}
}