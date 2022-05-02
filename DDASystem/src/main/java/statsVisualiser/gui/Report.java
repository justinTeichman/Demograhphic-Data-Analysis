package statsVisualiser.gui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JFrame;

import org.jfree.chart.ChartPanel;


/**
 * The rendering functionality for the report viewer
 * 
 * @author Liam
 *
 */
public class Report extends Viewer {
	
	private static Report instance;
	private JScrollPane reportPanel;
	private String reportMessage;
	private JTextArea report;

	/**
	 * Singleton method makes sure only one report is created
	 * 
	 * @return The reference to the one Report viewer
	 */
	public static Report getInstance() {
		if (instance == null)
			instance = new Report();

		return instance;
	}
	
	/**
	 * Constructor for the report
	 */
	private Report()
	{
		
		report = new JTextArea(); // create an empty JTextArea for the report
		report.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		report.setBackground(Color.white);
		reportMessage = "Report Chart"; // add the default message for the report chart
		report.setText(reportMessage);
		reportPanel = new JScrollPane(report,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		reportPanel.setPreferredSize(new Dimension(400,300));
	}
	

	/**
	 * Updates the report when a recalculation is called.
	 * 
	 * @param analysisData	The processed analysis data collected from Analysis
	 * @param analysisValue	A string that contains the name of the analysis
	 * @param startYearValue		The first year of analysis data
	 */
	public void updateViewer(float[][] analysisData, String analysisValue, int startYearValue) {
		
		
		String[] analysisTypes = analysisValue.split(" vs "); // split up the types of analysis for displaying
		reportMessage = analysisValue + "\n ============================== "; // add the full type of analysis that is being performed
		for (int i = 0; i < analysisData[0].length;i++) {  // loop for how many years there are in the analysis
			reportMessage +=   "\n Year " + (startYearValue + analysisData[0].length - i - 1) + ":"  + "\n                                "; // add the year for which the data is for
			for (int k = 0; k < analysisTypes.length; k++) { // loop for how many different types of analysis are being displayed
				reportMessage += analysisTypes[k] + " => " + analysisData[k][analysisData[k].length-i-1] + "\n                                "; // add the data from each analysis for the specific year
			}
		}
		report.setText(reportMessage); // set the message that will be displayed

		
	}


	/**
	 * @return the JscrollPanel of the report class
	 */
	public JScrollPane getScrollPanel() {
		return reportPanel;
	}

	/**
	 * @return null because the report does not require a chartpanel
	 */
	public ChartPanel getChartPanel() {

		return null;
	}	
}
