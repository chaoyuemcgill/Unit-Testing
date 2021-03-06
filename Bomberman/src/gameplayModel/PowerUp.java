//*****************************************************************************************************
//  PowerUp.java
//
//*****************************************************************************************************

package gameplayModel;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class PowerUp extends GridObject {
	
	protected BufferedImage img;
	protected boolean isPermanent;
	
	public PowerUp(int x, int y) {
		super(x, y);
	}
	
	public BufferedImage generateImage(int[] imageParameter) {
		
		return resizeImage(image.getSubimage(imageParameter[0], imageParameter[1], PIXELWIDTH, PIXELHEIGHT), ZOOM);
	}
	
	public BufferedImage getImage(){
		return img;
	}
	
    public void setXPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public void setYPosition(int yPosition) {
        this.yPosition = yPosition;
    }
    
	public ArrayList<String> toCSVEntry() {
		
		ArrayList<String> entryList = new ArrayList<String>();
		
		entryList.add(this.getClass().toString());
		entryList.add(Integer.toString(xPosition));
		entryList.add(Integer.toString(yPosition));

		return entryList; 
	}
}
