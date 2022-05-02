package statsVisualiser.gui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.util.TableOrder;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * The rendering functionality for a pie chart
 * 
 * @author Nicolas Jacobs
 */
public class PieChart extends Viewer
{
	private static PieChart instance;
	private ChartPanel piePanel;
	
	private static JFreeChart pieChart;
	private static DefaultCategoryDataset dataset;

	/**
	 * Singleton method, will make sure only one is created
	 * 
	 * @return	The reference to the one and only PieChart
	 */
	public static PieChart getInstance()
	{
		if (instance == null)
			instance = new PieChart();

		return instance;
	}
	
	/**
	 * Constructor
	 */
	private PieChart()
	{
		//Initialize the dataset or everything breaks
		dataset = new DefaultCategoryDataset();
		
		//And then the rest of this is just making an empty pie chart in the display window
		pieChart = ChartFactory.createMultiplePieChart("Pie Chart", dataset, TableOrder.BY_COLUMN, true, true, false);

		piePanel = new ChartPanel(pieChart);
		piePanel.setPreferredSize(new Dimension(400, 300));
		piePanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		piePanel.setBackground(Color.white);
//		west.add(piePanel);
	}
	
	/**
	 * @return	The reference to the bar panel
	 */
	public ChartPanel getChartPanel()
	{
		return piePanel;
	}
	
	/**
	 * @return	Null, because Bar Chart does not require a scroll panel
	 */
	public JScrollPane getScrollPanel()
	{
		return null;
	}
	
	/**
	 * Will refresh the pie chart with new updated data
	 * 
	 * @param analysisData	The processed analysis data collected from Analysis
	 * @param analysisType	A string that contains the name of the analysis
	 * @param startYear		The first year of analysis data
	 */
	public void updateViewer(float[][] analysisData, String analysisType, int startYear)
	{
		//Variables
		String[] analysisTypes = analysisType.split(" vs ");
		
		//Clear the dataset or previous analysis data stays in the dataset
		dataset.clear();
		
		//Loop through the subanalyses in this analysis and add their corresponding data to the correct dataset type
		for (int i = 0; i < analysisData.length; i++)
			if (analysisTypes[i].equals("Forest area"))
				updateDataset(analysisData[i], "Forest area", startYear, dataset);
			else if (analysisTypes[i].equals("Government education expenditure"))
			{
				if (analysisType.equals("Government education expenditure"))
					updateDataset(analysisData[i], "Edu expend.", startYear, dataset);
				else
					updateDataset(analysisData[i], "Edu expend. ", startYear, dataset);
			}
			else if (analysisTypes[i].equals("Health expenditure"))
				updateDataset(analysisData[i], "Health expend.", startYear, dataset);
			else if (analysisTypes[i].equals("Hospital beds"))
				updateDataset(analysisData[i], "Hosp. beds", startYear, dataset);
			else if (analysisTypes[i].equals("GDP"))
				updateDataset(analysisData[i], "GDP", startYear, dataset);
			else if (analysisTypes[i].equals("CO2 emissions"))
				updateDataset(analysisData[i], "CO2", startYear, dataset);
		
		//Set the title of the graph
		pieChart.setTitle(analysisType);
	}
	
	//Add new elements to the dataset
	private void updateDataset(float[] analysisData, String analysisType, int startYear, DefaultCategoryDataset datasetTmp)
	{	
		//Loop through every year of data for this subanalysis and add it to a dataset
		for (int i = 0; i < analysisData.length; i++)
		{
			datasetTmp.addValue((Number)analysisData[i], analysisType, startYear + i);
			if (analysisType.equals("Forest area"))
				datasetTmp.addValue((Number)(100 - analysisData[i]), "Non-forest area", startYear + i);
			else if (analysisType.equals("Edu expend."))
				datasetTmp.addValue((Number)(100 - analysisData[i]), "Other expend.", startYear + i);
		}
	}
}
