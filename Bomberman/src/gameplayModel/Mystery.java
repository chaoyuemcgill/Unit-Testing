//*****************************************************************************************************
//  Mystery.java
//
//*****************************************************************************************************

package gameplayModel;

public class Mystery extends PowerUp {
	
	int[] imageParam = {217, 223};
	
	public Mystery(int x, int y) {
		super(x, y);
		
		isPermanent = false;
		img = generateImage(imageParam);
	}
}