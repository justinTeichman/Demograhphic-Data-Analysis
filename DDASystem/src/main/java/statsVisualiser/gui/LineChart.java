package statsVisualiser.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.title.Title;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;



/**
 * Viewer class for the linechart method
 * 
 * 
 * @author Liam
 *
 */
public class LineChart extends Viewer {

	private static LineChart instance;
	private ChartPanel linePanel;
	private JFreeChart chart;
	private XYSeriesCollection dataset = new XYSeriesCollection();
	
/**
 * Singleton method for creating only one instance of a linechart
 * 
 * @return the instance of the lineChart
 */
	public static LineChart getInstance() {
		if (instance == null)
			instance = new LineChart();

		return instance;
	}
	
	/**
	 * Constructor for the LineChart 
	 */
	private LineChart()
	{
		// Create the linechart with the default title and set up the boundaries
		chart = ChartFactory.createXYLineChart("Line Chart", "Year", "", dataset,PlotOrientation.VERTICAL, true, true, false);

		linePanel = new ChartPanel(chart);
		linePanel.setPreferredSize(new Dimension(400, 300));
		linePanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		linePanel.setBackground(Color.white);
		dataset.removeAllSeries();
	}
	
/**
 * Method for returning the chartpanel of the linechart
 * 
 * @return linePanel the chartpanel for the linechart class
 */
	public ChartPanel getChartPanel()
	{
		return linePanel;
	}
	/**
	 * Method for returning a scrollpanel
	 * 
	 * @return null because the linechart doesn't have one
	 */
	public JScrollPane getScrollPanel()
	{
		return null;
	}

	/**
	 * Will refresh the scatterchart with new updated data
	 * 
	 * @param analysisData	The processed analysis data collected from Analysis
	 * @param analysisType	A string that contains the name of the analysis
	 * @param startYear		The first year of analysis data
	 */
	public void updateViewer(float[][] analysisData, String analysisType, int startYear)
	{
		
		String[] analysisTypes = analysisType.split(" vs "); //string array for the different types of analysis being performed
		XYSeries datasetCO2 = new XYSeries("CO2 emissions metric tons per capita");
		XYSeries datasetEnergy = new XYSeries("Energy use");
		XYSeries datasetPM2= new XYSeries("PM2.5 air pollution");
		XYSeries datasetForest = new XYSeries("Forest area");
		XYSeries datasetGDP = new XYSeries("GDP");// different datasets for each type of analysis
		XYSeries datasetGEE = new XYSeries("Governemnt education expenditure");
		XYSeries datasetBeds = new XYSeries("Hospital beds");
		XYSeries datasetHealth = new XYSeries("Health expenditure");
		XYSeries datasetMort = new XYSeries("Mortality");
		dataset.removeAllSeries(); // removes the datasets from the main dataset
		for (int i = 0; i < analysisData.length; i++) {
			// if there is a specific type of analysis being performed then a dataset is created for all the values
			// then add that dataset to the main one
			if (analysisTypes[i].equals("CO2 emissions")) {
				datasetCO2 = updateDataset(analysisData[i],startYear, datasetCO2);// create the data set 
				dataset.addSeries(datasetCO2); // add the data set to the main one
				}
			
			else if (analysisTypes[i].equals("Energy use"))	{
				datasetEnergy = updateDataset(analysisData[i],startYear, datasetEnergy);
				dataset.addSeries(datasetEnergy);}
		
			else if (analysisTypes[i].equals("PM2.5 air pollution")) {
				datasetPM2 = updateDataset(analysisData[i],startYear, datasetPM2);
				dataset.addSeries(datasetPM2);}
			
			else if (analysisTypes[i].equals("Forest area")) {
				datasetForest = updateDataset(analysisData[i],startYear, datasetForest);
				dataset.addSeries(datasetForest);
					
			}
			
			else if (analysisTypes[i].equals("GDP")) {
				datasetGDP = updateDataset(analysisData[i],startYear, datasetGDP);
				dataset.addSeries(datasetGDP);}
			
			else if (analysisTypes[i].equals("Government education expenditure")) {
				datasetGEE = updateDataset(analysisData[i],startYear, datasetGEE);
				dataset.addSeries(datasetGEE);}
			
			else if (analysisTypes[i].equals("Hospital beds")) {
				datasetBeds = updateDataset(analysisData[i],startYear, datasetBeds);
				dataset.addSeries(datasetBeds);}
			
			
			else if (analysisTypes[i].equals("Health expenditure")) {
				datasetHealth = updateDataset(analysisData[i],startYear, datasetHealth);
				dataset.addSeries(datasetHealth);}
			
			else if (analysisTypes[i].equals("Mortality")) {
				datasetMort = updateDataset(analysisData[i],startYear, datasetMort);
				dataset.addSeries(datasetMort);}
			
	}

		XYPlot plot = chart.getXYPlot(); // create the plot for the linechart

		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer(); // create the renderer for the chart
		renderer.setSeriesPaint(0, Color.RED);
		renderer.setSeriesStroke(0, new BasicStroke(2.0f));


		plot.setRenderer(renderer);
		plot.setBackgroundPaint(Color.white);

		plot.setRangeGridlinesVisible(true);
		plot.setRangeGridlinePaint(Color.BLACK);

		plot.setDomainGridlinesVisible(true);
		plot.setDomainGridlinePaint(Color.BLACK);

		chart.getLegend().setFrame(BlockBorder.NONE);
		chart.setTitle(new TextTitle(analysisType, new Font("Serif", java.awt.Font.BOLD, 18))); // change the title to the current analysis type
	}
	
	
	/**
	 * Method for updating a dataset with the value gotten from the analysis
	 * 
	 * @param analysisData The processed analysis data collected from Analysis for the specific type of analysis
	 * @param startYear A string that contains the name of the analysis
	 * @param datasetTmp The dataset that will have the data added to it
	 * @return the dataset that has had the data added to it
	 */	
	private XYSeries updateDataset(float[] analysisData, int startYear, XYSeries datasetTmp)
	{	
		for (int i = 0; i < analysisData.length; i++)
			datasetTmp.add(startYear + i, (Number)analysisData[i]);
		return datasetTmp;
	}


}
