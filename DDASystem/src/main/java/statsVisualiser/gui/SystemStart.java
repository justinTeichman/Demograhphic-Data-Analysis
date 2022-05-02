// Imports all the necessary libraries for the program
package statsVisualiser.gui;

/**
 * This class is used as the starting point of the program. When the program is launched
 * this class will begin the proxy pattern by invoking the LoginProxy class.
 * 
 * @author Justin Weller
 */
public class SystemStart {

	/**
	 * This is the main method used as the initializer of the program.
	 * 
	 * @param args	This variable takes input upon initialization of the program. This
	 * system did not make use of this variable.
	 */
	public static void main(String[] args) {

		// Begins the proxy pattern by invoking the login proxy acting as the 'substitute'
		// of the main UI
		UserInterface proxy = new LoginProxy();
		proxy.admitUser();
	}
}
