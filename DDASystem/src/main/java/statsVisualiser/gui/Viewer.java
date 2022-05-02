package statsVisualiser.gui;

import javax.swing.JScrollPane;

import org.jfree.chart.ChartPanel;

/**
 * The ADT responsible for all viewer types
 * 
 * @author 
 *
 */
public abstract class Viewer
{
	/**
	 * When this is called the Viewer subclass that has already been rendered will be rerendered with
	 * Data from the parameters listed
	 * 
	 * @param analysisData	The processed analysis data collected from Analysis
	 * @param analysisType	A string that contains the name of the analysis
	 * @param startYear		The first year of analysis data
	 */
	public abstract void updateViewer(float[][] analysisData, String analysisType, int startYear);
	
	/**
	 * 
	 * @return The scroll panel for the Viewer
	 */
	public abstract JScrollPane getScrollPanel();
	
	/**
	 * 
	 * @return	The chart panel for the Viewer
	 */
	public abstract ChartPanel getChartPanel();
}
