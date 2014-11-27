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
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import menuModel.SavedGame;
import menuModel.Player;


public class LoadGamePanel extends JPanel {

	private JList list;
	private JButton goBack, loadGame;
	private JLabel savename, level, datesaved; //headers
	private JLabel name, levelNumber, date; //variable
	private JScrollPane listScroller;
	private DefaultListModel<String> model;
	private Player currentPlayer;
	private int index;
	private BufferedImage img;
	
	
	public LoadGamePanel(ActionListener listener){
	
		try {
		      img = ImageIO.read(new File("bomber.png"));
		    } catch(IOException e) {
		      e.printStackTrace();
		    }
		
		ListSelectionListener listSelectionListener = new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent evt){
				index = evt.getLastIndex();
				name.setText(currentPlayer.getSavedGameList().get(index).getGameName());
				date.setText(currentPlayer.getSavedGameList().get(index).getGameDate());
				levelNumber.setText(Integer.toString((currentPlayer.getSavedGameList().get(index).getGameContext().getLevel())+1));
			}
		};
		
		model = new DefaultListModel<String>();
		
		list = new JList(model);
		list.setModel(model);
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);
		list.addListSelectionListener(listSelectionListener);
		
		listScroller = new JScrollPane(list);
		listScroller.setPreferredSize(new Dimension(250, 130));
		
		goBack = new JButton("Return");
		goBack.addActionListener(listener);
		
		loadGame = new JButton("Load Game");
		loadGame.addActionListener(listener);
		
		savename = new JLabel();
        savename.setFont(new Font("Eurostile", 1, 15)); // NOI18N
        savename.setText("Save Name:");
        
		level = new JLabel();
        level.setFont(new Font("Eurostile", 1, 15)); // NOI18N
        level.setText("Level:");
        
		datesaved = new JLabel();
		datesaved.setFont(new Font("Eurostile", 1, 15)); // NOI18N
		datesaved.setText("Date Saved:");
		
		levelNumber = new JLabel();
		levelNumber.setFont(new Font("Eurostile", 0, 13));
		levelNumber.setText("          ");
		
		name = new JLabel();
		name.setFont(new Font("Eurostile", 0, 13));
		name.setText("      ");
		
		date = new JLabel();
		date.setFont(new Font("Eurostile", 0, 13));
		date.setText("                                  ");
		
		setupLayout();
		
	}
	
	 protected void paintComponent(Graphics g) {
		    super.paintComponent(g);
		    // paint the background image and scale it to fill the entire space
		    g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
	 }
	
	public void updateLoad(Player currentPlayer){
		this.currentPlayer=currentPlayer;
		model.clear();
		for(int i = 0; i < currentPlayer.getSavedGameList().size() ; i++)
			model.addElement(currentPlayer.getSavedGameList().get(i).getGameName());
		list.setModel(model);
	}
	
	public JButton getReturn(){
		return goBack;
	}
	
	public JButton getLoad(){
		return loadGame;
	}
	
	public int getSaveIndex(){
		return index;
	}
	
	private void setupLayout(){
		GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(listScroller, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(savename)
                            .addComponent(datesaved)
                            .addComponent(levelNumber)
                            .addComponent(date)
                            .addComponent(name)
                            .addComponent(level)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(loadGame)
                            .addComponent(goBack)))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(130, 130, 130))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
            .addGroup(layout.createSequentialGroup()
                .addGap(183, 183, 183))
            .addGroup(javax.swing.GroupLayout.Alignment.CENTER, layout.createSequentialGroup()
                .addGap(155, 155, 155)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(listScroller, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(savename)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(name)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(level)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(levelNumber)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(datesaved)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(date)))
                .addGap(30, 30, 30)
                .addComponent(loadGame)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(goBack)
                .addGap(91, 91, 91))
        );
		
	}
}
