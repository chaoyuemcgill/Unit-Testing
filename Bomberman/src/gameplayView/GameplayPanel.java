//*****************************************************************************************************
//  GameplayPanel.java
//
//*****************************************************************************************************

package gameplayView;

import gameplayModel.GridMap;
import gameplayModel.GridObject;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyListener;

import javax.swing.JPanel;


@SuppressWarnings("serial")
public class GameplayPanel extends JPanel {
	
	public static final int WIDTH = GridMap.MAPWIDTH * GridObject.EFFECTIVE_PIXEL_WIDTH;
	public static final int HEIGHT = (GridMap.MAPHEIGHT) * GridObject.EFFECTIVE_PIXEL_HEIGHT;
	
	public GameplayPanel(KeyListener keyListener){
		
		setBackground(new Color(31, 139,0));
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setFocusable(true);
		addKeyListener(keyListener);
	}
}
