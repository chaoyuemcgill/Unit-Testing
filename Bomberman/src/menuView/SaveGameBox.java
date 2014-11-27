package menuView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class SaveGameBox extends JFrame {
	
	JLabel enterName;
	JTextField name;

	String storeName;
	
	public SaveGameBox(ActionListener listener){
		setLayout(null);
	    setSize(300, 250);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    String response = JOptionPane.showInputDialog(null,
                "What is your name?",
                "Enter your name",
                JOptionPane.QUESTION_MESSAGE);

	}
}
