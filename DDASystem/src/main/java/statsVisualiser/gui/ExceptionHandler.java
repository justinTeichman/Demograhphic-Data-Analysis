// Imports all the necessary libraries for the program
package statsVisualiser.gui;
import javax.swing.JOptionPane;

/**
 * This class handles all of the errors that may occur in the program. If called,
 * a popup message is displayed with a given String message.
 * 
 * @author Justin Weller
 */
public class ExceptionHandler {

	/**
	 * The constructor of this class takes an error message as a parameter, 
	 * and displays a popup message to the user.
	 * 
	 * @param msg	The error message to be displayed to the user.
	 */
	public ExceptionHandler(String msg)
	{			
		JOptionPane.showMessageDialog(null, msg);
	}
}
