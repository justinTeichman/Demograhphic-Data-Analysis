package statsVisualiser.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * The rendering functionality for a bar chart
 * 
 * @author Nicolas Jacobs
 */
public class BarChart extends Viewer
{
	
	private static BarChart instance;
	private ChartPanel barPanel;
	
	/**
	 * All the datasets for each type of unit in the analyses
	 */
	private DefaultCategoryDataset datasetPerCapita;
	private DefaultCategoryDataset datasetPerCubicMeter;
	private DefaultCategoryDataset datasetPercentLandCover;
	private DefaultCategoryDataset datasetPercentGDP;
	private DefaultCategoryDataset dataset1000People;
	private DefaultCategoryDataset dataset1000LiveBirths;
	/**
	 * Variables related to chart rendering
	 */
	private CategoryPlot plot = new CategoryPlot();
	private BarRenderer barrenderer1 = new BarRenderer();
	private BarRenderer barrenderer2 = new BarRenderer();
	private CategoryAxis domainAxis = new CategoryAxis("Year");
	private JFreeChart barChart;
	
	/**
	 * Singleton method, will make sure only one is created
	 * 
	 * @return	The reference to the one and only BarChart
	 */
	public static BarChart getInstance()
	{
		if (instance == null)
			instance = new BarChart();

		return instance;
	}
	
	/**
	 * Constructor
	 */
	private BarChart()
	{
		//Make an empty bar chart in the display window
		barChart = new JFreeChart("Bar Chart", new Font("Serif", java.awt.Font.BOLD, 18), plot, true);

		barPanel = new ChartPanel(barChart);
		barPanel.setPreferredSize(new Dimension(400, 300));
		barPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		barPanel.setBackground(Color.white);
//		west.add(barPanel);
	}
	
	/**
	 * @return	The reference to the bar panel
	 */
	public ChartPanel getChartPanel()
	{
		return barPanel;
	}
	
	/**
	 * @return	Null, because Bar Chart does not require a scroll panel
	 */
	public JScrollPane getScrollPanel()
	{
		return null;
	}
	
	/**
	 * Will refresh the bar chart with new updated data
	 * 
	 * @param analysisData	The processed analysis data collected from Analysis
	 * @param analysisType	A string that contains the name of the analysis
	 * @param startYear		The first year of analysis data
	 */
	public void updateViewer(float[][] analysisData, String analysisType, int startYear)
	{
		//Variables
		String[] analysisTypes = analysisType.split(" vs ");
		datasetPerCapita = new DefaultCategoryDataset();
		datasetPerCubicMeter = new DefaultCategoryDataset();
		datasetPercentLandCover = new DefaultCategoryDataset();
		datasetPercentGDP = new DefaultCategoryDataset();
		dataset1000People = new DefaultCategoryDataset();
		dataset1000LiveBirths = new DefaultCategoryDataset();
		
		//Fill any datasets that were used for this analysis
		//This corresponds to the unit that the analysis uses for this subanalysis
		for (int i = 0; i < analysisData.length; i++)
			if (analysisTypes[i].equals("CO2 emissions"))
				updateDataset(analysisData[i], analysisTypes[i] + " metric tons per capita", startYear, datasetPerCapita);
			else if (analysisTypes[i].equals("Energy use"))
				updateDataset(analysisData[i], analysisTypes[i] + " kg of oil equivalent per capita", startYear, datasetPerCapita);
			else if (analysisTypes[i].equals("PM2.5 air pollution"))
				updateDataset(analysisData[i], analysisTypes[i] + " micrograms per cubic meter", startYear, datasetPerCubicMeter);
			else if (analysisTypes[i].equals("Forest area"))
				updateDataset(analysisData[i], analysisTypes[i] + " % of land area", startYear, datasetPercentLandCover);
			else if (analysisTypes[i].equals("GDP"))
				updateDataset(analysisData[i], analysisTypes[i] + " US$", startYear, datasetPerCapita);
			else if (analysisTypes[i].equals("Government education expenditure"))
				updateDataset(analysisData[i], analysisTypes[i] + " % of GDP", startYear, datasetPercentGDP);
			else if (analysisTypes[i].equals("Hospital beds"))
				updateDataset(analysisData[i], analysisTypes[i] + " per 1000 people", startYear, dataset1000People);
			else if (analysisTypes[i].equals("Health expenditure"))
			{
				if (analysisType.equals("Hospital beds vs Health expenditure"))
					updateDataset(analysisData[i], analysisTypes[i] + " per 1000 people", startYear, dataset1000People);
				else if (analysisType.equals("Health expenditure vs Mortality"))
					updateDataset(analysisData[i], analysisTypes[i] + " per capita", startYear, datasetPerCapita);
				else
					updateDataset(analysisData[i], analysisTypes[i] + " % of GDP", startYear, datasetPercentGDP);
			}
			else if (analysisTypes[i].equals("Mortality"))
				updateDataset(analysisData[i], analysisTypes[i] + " per 1000 live births", startYear, dataset1000LiveBirths);
		
		//Render the correct datasets depending on what analysis was selected
		if (analysisType.equals("CO2 emissions vs Energy use vs PM2.5 air pollution"))
		{
			plot.setDataset(1, datasetPerCubicMeter);
			plot.setDataset(0, datasetPerCapita);
			plot.setRenderer(0, barrenderer1);
			plot.setRenderer(1, barrenderer2);
			plot.setRangeAxis(0, new NumberAxis(""));
			plot.setRangeAxis(1, new NumberAxis("micrograms per cubic meter"));
			
			//Set datasets to y-axis'
			plot.mapDatasetToRangeAxis(0, 0);
			plot.mapDatasetToRangeAxis(1, 1);
		}
		else if (analysisType.equals("PM2.5 air pollution vs Forest area"))
		{
			plot.setDataset(1, datasetPerCubicMeter);
			plot.setDataset(0, datasetPercentLandCover);
			plot.setRenderer(0, barrenderer1);
			plot.setRenderer(1, barrenderer2);
			plot.setRangeAxis(0, new NumberAxis(""));
			plot.setRangeAxis(1, new NumberAxis("micrograms per cubic meter"));
			
			//Set datasets to y-axis'
			plot.mapDatasetToRangeAxis(0, 0);
			plot.mapDatasetToRangeAxis(1, 1);
		}
		else if (analysisType.equals("CO2 emissions vs GDP"))
		{
			plot.setDataset(0, datasetPerCapita);
			plot.setRenderer(0, barrenderer1);
			plot.setRangeAxis(1, new NumberAxis("per capita"));
			
			//Set datasets to y-axis'
			plot.mapDatasetToRangeAxis(0, 0);
		}
		else if (analysisType.equals("Forest area"))
		{
			plot.setDataset(0, datasetPercentLandCover);
			plot.setRenderer(0, barrenderer1);
			plot.setRangeAxis(0, new NumberAxis("percent land cover"));
			
			//Set datasets to y-axis'
			plot.mapDatasetToRangeAxis(0, 0);
		}
		else if (analysisType.equals("Government education expenditure"))
		{
			plot.setDataset(0, datasetPercentGDP);
			plot.setRenderer(0, barrenderer1);
			plot.setRangeAxis(0, new NumberAxis("% of GDP"));
			
			//Set datasets to y-axis'
			plot.mapDatasetToRangeAxis(0, 0);
		}
		else if (analysisType.equals("Hospital beds vs Health expenditure"))
		{
			plot.setDataset(0, dataset1000People);
			plot.setRenderer(0, barrenderer1);
			plot.setRangeAxis(0, new NumberAxis("per 1000 people"));
			
			//Set datasets to y-axis'
			plot.mapDatasetToRangeAxis(0, 0);
		}
		else if (analysisType.equals("Health expenditure vs Mortality"))
		{
			plot.setDataset(0, dataset1000LiveBirths);
			plot.setRenderer(0, barrenderer1);
			plot.setDataset(1, datasetPerCapita);
			plot.setRenderer(1, barrenderer2);
			plot.setRangeAxis(1, new NumberAxis("per capita"));
			
			//Set datasets to y-axis'
			plot.mapDatasetToRangeAxis(0, 0);
		}
		else if (analysisType.equals("Government education expenditure vs Health expenditure"))
		{
			plot.setDataset(0, datasetPercentGDP);
			plot.setRenderer(0, barrenderer1);
			plot.setRangeAxis(0, new NumberAxis("% GDP"));
			
			//Set datasets to y-axis'
			plot.mapDatasetToRangeAxis(0, 0);
		}
		
		plot.setDomainAxis(domainAxis);
		plot.setRangeAxis(new NumberAxis(""));
		
		//Update the title of the bar chart
		barChart.setTitle(analysisType);
	}
	
	//Add new elements to the dataset
	private void updateDataset(float[] analysisData, String analysisType, int startYear, DefaultCategoryDataset datasetTmp)
	{	
		//Loop through the dataset and add all analysis data to it
		for (int i = 0; i < analysisData.length; i++)
			datasetTmp.addValue((Number)analysisData[i], analysisType, startYear + i);
	}
}
