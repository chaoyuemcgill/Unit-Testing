package menuView;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;

public class PauseMenuPanel extends JPanel {

	
	private JButton resume, save, load, leaderboard, mainmenu, exit;
	private JLabel gamepaused;
	private BufferedImage img;	
	
	public PauseMenuPanel(ActionListener listener){
		
		try {
		      img = ImageIO.read(new File("bomber2.png"));
		    } catch(IOException e) {
		      e.printStackTrace();
		    }
		
		
		resume = new JButton();
		setFont(new Font("Eurostile", 0, 13));
		resume.setText("Resume Game");
		resume.addActionListener(listener);
		
		save = new JButton();
		setFont(new Font("Eurostile", 0, 13));
		save.setText("Save Game");
		save.addActionListener(listener);
		
		load = new JButton();
		setFont(new Font("Eurostile", 0, 13));
		load.setText("Load Game");
		load.addActionListener(listener);
		
		leaderboard = new JButton();
		setFont(new Font("Eurostile", 0, 13));
		leaderboard.setText("Leaderboard");
		leaderboard.addActionListener(listener);
		
		mainmenu = new JButton();
		setFont(new Font("Eurostile", 0, 13));
		mainmenu.setText("Return to Main Menu");
		mainmenu.addActionListener(listener);
		
		exit = new JButton();
		setFont(new Font("Eurostile", 0, 13));
		exit.setText("Exit Game");
		exit.addActionListener(listener);
		
		gamepaused = new JLabel();
		gamepaused.setFont(new java.awt.Font("Eurostile", 1, 28)); // NOI18N
        gamepaused.setText("Game Paused");
        
        setupLayout();
		
	}
	
	 protected void paintComponent(Graphics g) {
		    super.paintComponent(g);
		    // paint the background image and scale it to fill the entire space
		    g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
	 }
	
	public JButton getResumeGame(){
		return resume;
	}
	
	public JButton getSave(){
		return save;
	}
	
	public JButton getLoad(){
		return load;
	}
	
	public JButton getLeaderboard(){
		return leaderboard;
	}
	
	public JButton getMainMenu(){
		return mainmenu;
	}
	
	public JButton getExit(){
		return exit;
	}
	
	
	private void setupLayout(){
		GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
            .addGroup(javax.swing.GroupLayout.Alignment.CENTER, layout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(resume)
                    .addComponent(save)
                    .addComponent(load)
                    .addComponent(mainmenu)
                    .addComponent(leaderboard)
                    .addComponent(exit)
                    .addComponent(gamepaused))
                .addGap(150, 150, 150))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
            .addGroup(layout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addComponent(gamepaused)
                .addGap(18, 18, 18)
                .addComponent(resume)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(save)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(load)
                .addGap(18, 18, 18)
                .addComponent(leaderboard)
                .addGap(18, 18, 18)
                .addComponent(mainmenu)
                .addGap(18, 18, 18)
                .addComponent(exit)
                .addContainerGap(49, Short.MAX_VALUE))
        );
	}
}
