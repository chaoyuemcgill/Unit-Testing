//*****************************************************************************************************
//  Flames.java
//
//*****************************************************************************************************

package gameplayModel;

public class Flames extends PowerUp {
	
	int[] imageParam = {145, 259};
	
	public Flames(int x, int y) {
		super(x, y);
		
		isPermanent = true;
		img = generateImage(imageParam);
	}
}