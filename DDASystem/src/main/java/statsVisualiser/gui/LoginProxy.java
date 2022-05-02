// Imports all the necessary libraries for the program
package statsVisualiser.gui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * This class acts as a protection proxy by first launching a login UI for the user
 * to interact with. Upon entering valid credentials on this UI, the proxy
 * will allow access to the 'real' Main class.
 * 
 * @author Justin Weller
 */
public class LoginProxy extends UserInterface implements ActionListener {
	
	/**
	 * The text field for the user to enter their username.
	 */
	private JTextField userField;
	
	/**
	 * The text field for the user to enter their password.
	 */
	private JPasswordField passField;
	
	/**
	 * The login GUI frame displayed to the user.
	 */
	private JFrame frame;
	
	/**
	 * The boolean determining if the entered credentials were valid or not.
	 */
	private boolean validCred = false;
	
	/**
	 * This method initializes the login UI which is the gatekeeper GUI to the main
	 * program. The user may interact with this GUI to enter credentials.
	 */
	public void admitUser()
	{
		// The panel holding the elements inside the JFrame
		JPanel panel = new JPanel();
		
		// Creates the main JFrame and adds the panel
		frame = new JFrame("Login");
		frame.setSize(350, 170);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		
		panel.setLayout(null);
		
		// Inserts and positions the Username label
		JLabel userLabel = new JLabel("Username:");
		userLabel.setBounds(10, 20, 80, 25);
		panel.add(userLabel);
		
		// Inserts and positions the Username text field
		userField = new JTextField();
		userField.setBounds(90, 20, 230, 25);
		panel.add(userField);
		
		// Inserts and positions the Password label
		JLabel passLabel = new JLabel("Password:");
		passLabel.setBounds(10, 50, 80, 25);
		panel.add(passLabel);

		// Inserts and positions the Password text field
		passField = new JPasswordField();
		passField.setBounds(90, 50, 230, 25);
		panel.add(passField);
		
		// Inserts and positions the credential submission button and adds an
		// action listener to it
		JButton loginButton = new JButton("Submit!");
		loginButton.setBounds(125, 85, 100, 25);
		loginButton.addActionListener(this);
		panel.add(loginButton);
		
		frame.setVisible(true);
	}
	
	/**
	 * This method acts on the action listener on the submission button. It
	 * interacts with the CredentialsVerifier class to check if the credentials
	 * are valid. If they are, the user is admitted to the Main program, else,
	 * an error message pops up and the program closes.
	 */
	public void actionPerformed(ActionEvent arg0) 
	{
		CredentialsVerifier verif = new CredentialsVerifier();
		
		validCred = verif.verifyCred(userField.getText(), passField.getText());
		
		// If the entered credentials are valid, user is admitted to main program
		if(validCred == true)
		{
			Main main = new Main();

			frame.dispose();
			
			main.admitUser();
		}
		else // If the credentials are invalid, error message pops up and program exits
		{
			System.exit(0);
		}
	}
}
