//*****************************************************************************************************
//  BombPU.java
//
//*****************************************************************************************************

package gameplayModel;

public class BombPU extends PowerUp {
	
	int[] imageParam = {163, 259};
	
	public BombPU(int x, int y) {
		super(x, y);
		
		isPermanent = true;
		img = generateImage(imageParam);
	}
}
