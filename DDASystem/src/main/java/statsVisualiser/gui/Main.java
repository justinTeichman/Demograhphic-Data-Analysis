// Imports all the necessary libraries for the program
package statsVisualiser.gui;
import javax.swing.JFrame;

/**
 * This class defines the 'real' object in the proxy pattern and is accessed
 * if the LoginProxy admits it due to valid credentials. This class extends
 * the UserInterface and is responsible for launching the MainUI for the user. 
 * 
 * @author Justin Weller
 */
public class Main extends UserInterface{

	/**
	 * This method invokes the MainUI class by getting a single instance of it and
	 * setting the basic requirements for the main frame of the system.
	 */
	public void admitUser()
	{		
		JFrame frame = MainUI.getInstance();
		frame.setSize(1250, 725);
		frame.setVisible(true);
	}
}
