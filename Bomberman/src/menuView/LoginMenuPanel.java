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

@SuppressWarnings("serial")
public class LoginMenuPanel extends JPanel {
	
	private int TEXTFIELD_LENGTH = 15;
	
	private JLabel usernameLabel, passwordLabel, bombermanLabel;
	//private JTextField username, password, confirmPassword;
	private JTextField username;
	private JButton login, createAccount;
	private JPasswordField password;
	private BufferedImage img;
	
	public LoginMenuPanel (ActionListener listener) {
		
		try {
		      img = ImageIO.read(new File("bomber.png"));
		    } catch(IOException e) {
		      e.printStackTrace();
		    }
		
		password = new JPasswordField();
		password.setEchoChar('*');
		password.addActionListener(listener);
		
		usernameLabel = new JLabel("Username: ");
        usernameLabel.setForeground(Color.white);
		
		passwordLabel = new JLabel("Password: ");
        passwordLabel.setForeground(Color.white);
				
		bombermanLabel = new JLabel("Bomberman");
		bombermanLabel.setFont(new Font("Eurostile", 0, 48)); // NOI18N
        bombermanLabel.setForeground(Color.white);
		
		username = new JTextField(TEXTFIELD_LENGTH);
		username.addActionListener(listener);
		
		login = new JButton("Login");
		login.addActionListener(listener);
		
		createAccount = new JButton("Create Account");
		createAccount.addActionListener(listener);
		
		setupLayout();
        
		setBackground(Color.black);
	}
	
	 protected void paintComponent(Graphics g) {
		    super.paintComponent(g);
		    // paint the background image and scale it to fill the entire space
		    g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
	 }
	
	public void resetTextFields() {
			username.setText(null);
			password.setText(null);
	}
	 
	private void setupLayout() {
		
		GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(110, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(bombermanLabel)
                        .addGap(110))
                    .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(passwordLabel, GroupLayout.Alignment.TRAILING)
                            .addComponent(usernameLabel, GroupLayout.Alignment.TRAILING))
                        .addGap(12)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addComponent(username)
                            .addComponent(password))
                        .addGap(110))
                    .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    		.addComponent(login)
                    		.addGap(12)
                    		.addComponent(createAccount)
                    		.addGap(130))))
        );
        
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(80)
                .addComponent(bombermanLabel)
                .addGap(45)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(username, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(usernameLabel))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(password, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(passwordLabel))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE))
                    //.addComponent(confirmPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    //.addComponent(confirmPWLabel))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGap(30)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(login, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(createAccount))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addContainerGap(33, Short.MAX_VALUE))
        );
	}
	
	public JButton getCreateAccButton(){
		return createAccount;
	}
	
	public JButton getLoginButton(){
		return login;
	}
	
	public String getUsername(){
		return username.getText();
	}
	
	public String getPassword() {
		String stringPas = new String(password.getPassword());
		return stringPas;
	}
	
	/*public String getConfirmedPassword(){
		return confirmPassword.getText();
	}*/
}





