// Imports all the necessary libraries for the program
package statsVisualiser.gui;

/**
 * This abstract class specifies a common interface for the LoginProxy and Main
 * classes so that the LoginProxy can be used where the Main class can be used.
 * 
 * @author Justin Weller
 */
public abstract class UserInterface {

	/**
	 * This method specifies the method that class LoginProxy and Main must implement
	 * to regulate admitting the user to the system.
	 */
	public abstract void admitUser();
}
