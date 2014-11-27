//*****************************************************************************************************
//  Exitway.java
//
//*****************************************************************************************************

package gameplayModel;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Exitway extends GridObject {
	
	private BufferedImage img;
	private int[] imageParameter = {2, 241};

	public Exitway(int x, int y) {
		
		super(x, y);
		
		img = resizeImage(image.getSubimage(imageParameter[0], imageParameter[1], PIXELWIDTH, PIXELHEIGHT), ZOOM);
		
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
		
		entryList.add(Integer.toString(xPosition));
		entryList.add(Integer.toString(yPosition));

		return entryList; 
	}
}