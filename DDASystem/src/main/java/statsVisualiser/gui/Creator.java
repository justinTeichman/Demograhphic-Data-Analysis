// Imports all the necessary libraries for the program
package statsVisualiser.gui;

/**
 * This abstract class specifies a common interface for the all of the viewer creator
 * classes as part of the factory design pattern. It specifies the methods needed to
 * construct and return a Viewer item.
 * 
 * @author Justin Weller
 */
public abstract class Creator {

	/**
	 * This outlines the method that the creator classes need to implement
	 * in order to create the specified Viewer item.
	 * 
	 * @return The specified Viewer object.
	 */
	public abstract Viewer makeViewer();
}
