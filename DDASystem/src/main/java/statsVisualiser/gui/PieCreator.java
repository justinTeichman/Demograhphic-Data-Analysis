// Imports all the necessary libraries for the program
package statsVisualiser.gui;

/**
 * This class is a sub-class of the Creator class and overrides the makeViewer()
 * method in order for makeViewer to construct and return a Viewer object.
 *
 * @author Justin Weller
 */
public class PieCreator extends Creator {

	/**
	 * This method implements the makeViewer() method by creating and returning
	 * and instance of the PieChart Viewer.
	 * 
	 * @return An instance of the PieChart Viewer class.
	 */
	public Viewer makeViewer()
	{
		return PieChart.getInstance();
	}
}
