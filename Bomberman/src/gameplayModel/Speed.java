//*****************************************************************************************************
//  Speed.java
//
//*****************************************************************************************************

package gameplayModel;

public class Speed extends PowerUp {
	
	int[] imageParam = {180, 259};
	
	public Speed(int x, int y) {
		super(x, y);
		
		isPermanent = true;
		img = generateImage(imageParam);
	}
}