//*****************************************************************************************************
//  GridObject.java
//
//*****************************************************************************************************

package gameplayModel;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GridObject {
    
	public static BufferedImage image;
	
	public static final int ZOOM = 2;
    public static final int PIXELWIDTH = 16;
    public static final int PIXELHEIGHT = 16;
    public static final int EFFECTIVE_PIXEL_WIDTH = PIXELWIDTH * ZOOM;
    public static final int EFFECTIVE_PIXEL_HEIGHT = PIXELHEIGHT * ZOOM;
    public static final int MISALIGNMENT_ALLOWED = 16;
	
	protected int xPosition;
	protected int yPosition;
    protected boolean concreteCollision;

    public GridObject(int x, int y) {
    	
    	xPosition = x;
    	yPosition = y;
        concreteCollision = false;
    	
    	try {
    		image = ImageIO.read(new File("spritesheet.png"));
        } 
    	catch (IOException e) {
    		System.out.println("LOLOLO");
    		image = null;
    		e.printStackTrace();
        }
    	
    }

    public boolean isConcreteCollision() {
        return concreteCollision;
    }

    public int getXPosition() {
        return xPosition;
    }

    public void setXPosition(int xPosition) {
    	concreteCollision = false;
    	boolean isInXRange = (xPosition >= EFFECTIVE_PIXEL_WIDTH) && (xPosition <= EFFECTIVE_PIXEL_WIDTH * (GridMap.MAPWIDTH - 2));
    	boolean isAlignedWithRow = ((this.yPosition - EFFECTIVE_PIXEL_HEIGHT) % (EFFECTIVE_PIXEL_HEIGHT * 2)) == 0;
    	boolean isBelowRow = ((this.yPosition - EFFECTIVE_PIXEL_HEIGHT) % (EFFECTIVE_PIXEL_HEIGHT * 2)) <= MISALIGNMENT_ALLOWED;
    	boolean isAboveRow = ((this.yPosition - EFFECTIVE_PIXEL_HEIGHT) % (EFFECTIVE_PIXEL_HEIGHT * 2)) >= (EFFECTIVE_PIXEL_HEIGHT * 2 - MISALIGNMENT_ALLOWED);
    	
    	if (isAlignedWithRow && isInXRange) {
			this.xPosition = xPosition;
    	} else if (isAboveRow && isInXRange) {
    		this.xPosition = xPosition;
    		this.yPosition += 4;
    	} else if (isBelowRow && isInXRange) {
    		this.xPosition = xPosition;
    		this.yPosition -= 4;
    	}else 
    		concreteCollision = true;
    }

    public int getYPosition() {
        return yPosition;
    }

    public void setYPosition(int yPosition) {
    	
        concreteCollision = false;
    	int xError = (this.xPosition - EFFECTIVE_PIXEL_WIDTH) % (EFFECTIVE_PIXEL_WIDTH * 2);
    	
    	boolean isInYRange = (yPosition >= EFFECTIVE_PIXEL_HEIGHT) && (yPosition <= EFFECTIVE_PIXEL_HEIGHT * (GridMap.MAPHEIGHT - 2));
    	boolean isAlignedWithColumn = ((xError) == 0);
    	boolean isRightFromColumn = (xError) <= MISALIGNMENT_ALLOWED;
    	boolean isLeftFromColumn = (xError) >= (EFFECTIVE_PIXEL_HEIGHT * 2 - MISALIGNMENT_ALLOWED);
    	
    	if (isAlignedWithColumn && isInYRange) {
    		this.yPosition = yPosition;
    	} else if (isRightFromColumn && isInYRange) {
    		this.yPosition = yPosition;
    		this.xPosition -= 4;
    	} else if (isLeftFromColumn && isInYRange) {
    		this.yPosition = yPosition;
    		this.xPosition += 4;
    	} else 
    		concreteCollision = true;
    }
    
	public static BufferedImage resizeImage(BufferedImage imageIn, int factor) {
        
		final BufferedImage imageOut = new BufferedImage(imageIn.getWidth()*factor, imageIn.getHeight()*factor, BufferedImage.TYPE_INT_RGB);
        final Graphics2D graphics2D = imageOut.createGraphics();
        graphics2D.setComposite(AlphaComposite.Src);
        
        //below three lines are for RenderingHints for better image quality at cost of higher processing time
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.drawImage(imageIn, 0, 0, imageIn.getWidth()*factor, imageIn.getHeight()*factor, null);
        graphics2D.dispose();
        return imageOut;
    }
}
    
    
    


