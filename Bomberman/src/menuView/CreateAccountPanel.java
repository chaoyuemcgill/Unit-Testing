package menuView;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
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

@SuppressWarnings("serial")
public class CreateAccountPanel extends JPanel {
	
	private int TEXTFIELD_LENGTH = 15;
	
	private JLabel realNameLabel, usernameLabel, passwordLabel, confirmPWLabel, bombermanLabel;
	private JTextField realName, username;
	private JButton createAccount, back;
	private JPasswordField password, confirmPassword;
	private BufferedImage img;
	
	public CreateAccountPanel (ActionListener listener) {
		
		try {
		      img = ImageIO.read(new File("bomber.png"));
		    } catch(IOException e) {
		      e.printStackTrace();
		    }
	
		realNameLabel = new JLabel("Real Name:");
		realNameLabel.setForeground(Color.white);
		
		usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.white);
		
		passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.white);
		
		confirmPWLabel = new JLabel("Confirm Password:");
		confirmPWLabel.setForeground(Color.white);
		
		bombermanLabel = new JLabel("Bomberman");
		bombermanLabel.setFont(new Font("Eurostile", 0, 48)); // NOI18N
        bombermanLabel.setForeground(Color.white);
		
        realName = new JTextField(TEXTFIELD_LENGTH);
		realName.addActionListener(listener);
        
		username = new JTextField(TEXTFIELD_LENGTH);
		username.addActionListener(listener);
		
		password = new JPasswordField();
		password.setEchoChar('*');
		password.addActionListener(listener);
		
		confirmPassword = new JPasswordField();
		confirmPassword.setEchoChar('*');
		confirmPassword.addActionListener(listener);
		
		createAccount = new JButton("Create Account");
		createAccount.addActionListener(listener);
		
		back = new JButton("Return to Login");
		back.addActionListener(listener);
		
		setupLayout();
        
		setBackground(Color.black);
	}
	
	 protected void paintComponent(Graphics g) {
		    super.paintComponent(g);
		    // paint the background image and scale it to fill the entire space
		    g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
	 }
	
	public void resetTextFields() {
		realName.setText(null);
		username.setText(null);
		password.setText(null);
		confirmPassword.setText(null);
	}
	
	public JButton getCreateAccButton(){
		return createAccount;
	}
	
	public JButton getBackButton(){
		return back;
	}
	
	public String getRealName(){
		return realName.getText();
	}
	
	public String getUsername(){
		return username.getText();
	}
	
	public String getPassword() {
		String stringPas = new String(password.getPassword());
		return stringPas;
	}
	
	public String getConfirmedPassword(){
		String stringConfirmedPas = new String(confirmPassword.getPassword());
		return stringConfirmedPas;
	}
	
	private void setupLayout() {
		
		GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(100, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(bombermanLabel)
                        .addGap(110))
                    .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        	.addComponent(confirmPWLabel, GroupLayout.Alignment.TRAILING)
                            .addComponent(passwordLabel, GroupLayout.Alignment.TRAILING)
                            .addComponent(usernameLabel, GroupLayout.Alignment.TRAILING)
                            .addComponent(realNameLabel, GroupLayout.Alignment.TRAILING))
                        .addGap(12)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                        	.addComponent(realName)
                        	.addComponent(username)
                            .addComponent(password)
                            .addComponent(confirmPassword))
                        .addGap(100))
                    .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    		.addComponent(createAccount)
                    		.addGap(180))))
                   .addGroup(GroupLayout.Alignment.CENTER, layout.createSequentialGroup()
                    		.addGap(5)
                		    .addComponent(back))
        );
        
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(50)
                .addComponent(bombermanLabel)
                .addGap(25)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(realName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(realNameLabel))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(username, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(usernameLabel))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(password, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(passwordLabel))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(confirmPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(confirmPWLabel))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGap(30)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(createAccount))
                .addGap(10) 
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(back))
                    
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addContainerGap(33, Short.MAX_VALUE))
        );
	}
}