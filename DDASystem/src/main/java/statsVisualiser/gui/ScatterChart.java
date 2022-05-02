package statsVisualiser.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Year;
import org.jfree.data.xy.XYSeries;

/**
 * Viewer class for the scatter chart method
 * 
 * @author Liam
 *
 */
public class ScatterChart extends Viewer{

	private static ScatterChart instance;
	private ChartPanel scatterPanel;
	private TimeSeriesCollection dataset = new TimeSeriesCollection();
	private XYPlot plot = new XYPlot();
	private JFreeChart scatterChart;
	
	
	/**
	 *  Singleton method is used to ensure only one scatter chart is created
	 *  
	 * @return the instance of the scatterchart
	 */
	public static ScatterChart getInstance() {
		if (instance == null)
			instance = new ScatterChart();

		return instance;
	}
	/**
	 * Constructor for the scatterchart
	 */
	private ScatterChart()
	{
		// make an empty scatter chart and set up the dimensions 
		scatterChart = new JFreeChart("Scatter chart",new Font("Serif", java.awt.Font.BOLD, 18), plot, true);
		scatterPanel = new ChartPanel(scatterChart);
		scatterPanel.setPreferredSize(new Dimension(400, 300));
		scatterPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		scatterPanel.setBackground(Color.white);
	}
	/**
	 * Method to return the Chartpanel of the scatter chart
	 * 
	 * @return the chartpanel
	 */
	public ChartPanel getChartPanel()
	{
		return scatterPanel;
	}
	
	/**
	 * Method to return a JScrollPane
	 * 
	 * @return null because a JScrollpane isnt used in for a scatterchart
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
	public void updateViewer(float[][] analysisData, String analysisType, int startYear) {
		boolean GEE = false; // boolean for if its GEE vs curent health expenditure
		String[] titles = new String[2]; // string array for x and y axis titles  
		String[] analysisTypes = analysisType.split(" vs "); //string array for the different types of analysis being performed
		TimeSeries datasetCO2 = new TimeSeries("CO2 emissions metric tons per capita");
		TimeSeries datasetEnergy = new TimeSeries("Energy use");
		TimeSeries datasetPM2= new TimeSeries("PM2.5 air pollution");
		TimeSeries datasetForest = new TimeSeries("Forest area");
		TimeSeries datasetGDP = new TimeSeries("GDP"); // different datasets for each type of analysis
		TimeSeries datasetGEE = new TimeSeries("Governemnt education expenditure");
		TimeSeries datasetBeds = new TimeSeries("Hospital beds");
		TimeSeries datasetHealth = new TimeSeries("Health expenditure");
		TimeSeries datasetMort = new TimeSeries("Mortality");
		dataset.removeAllSeries(); // removes the datasets from the main dataset
		// loop through the number of different types of analysis being performed
		for (int i = 0; i < analysisData.length; i++) {
			// if there is a specific type of analysis being performed then a dataset is created for all the values
			// then add that dataset to the main one as well as adds the titles to the axis
			if (analysisTypes[i].equals("CO2 emissions")) {
				datasetCO2 = updateDataset(analysisData[i],startYear, datasetCO2); // create the data set 
				dataset.addSeries(datasetCO2); // add the data set to the main one
				titles[0] = ("metric tons per capita");} // add the axis title
			
			else if (analysisTypes[i].equals("Energy use"))	{
				datasetEnergy = updateDataset(analysisData[i],startYear, datasetEnergy);
				dataset.addSeries(datasetEnergy);
				titles[0] = ("kg of oil equivalent per capita");}
		
			else if (analysisTypes[i].equals("PM2.5 air pollution")) {
				datasetPM2 = updateDataset(analysisData[i],startYear, datasetPM2);
				dataset.addSeries(datasetPM2);
				titles[1] = ("micrograms per cubic meter");}
			
			else if (analysisTypes[i].equals("Forest area")) {
				datasetForest = updateDataset(analysisData[i],startYear, datasetForest);
				dataset.addSeries(datasetForest);
				titles[0] = ("% of land area");}
			
			else if (analysisTypes[i].equals("GDP")) {
				datasetGDP = updateDataset(analysisData[i],startYear, datasetGDP);
				dataset.addSeries(datasetGDP);
				titles[1] = ("US$");}
			
			else if (analysisTypes[i].equals("Government education expenditure")) {
				datasetGEE = updateDataset(analysisData[i],startYear, datasetGEE);
				dataset.addSeries(datasetGEE);
				titles[0] = ("% of GDP");}
			
			else if (analysisTypes[i].equals("Hospital beds")) {
				datasetBeds = updateDataset(analysisData[i],startYear, datasetBeds);
				dataset.addSeries(datasetBeds);
				titles[0] = ("per 1000 people");}
			
			
			else if (analysisTypes[i].equals("Health expenditure")) {
				datasetHealth = updateDataset(analysisData[i],startYear, datasetHealth);
				dataset.addSeries(datasetHealth);
				GEE = true;}// boolean for this specific analysis for the axis names
			
			else if (analysisTypes[i].equals("Mortality")) {
				datasetMort = updateDataset(analysisData[i],startYear, datasetMort);
				dataset.addSeries(datasetMort);
				titles[0] = ("per 1,000 live births");}
			
	}
		
			// render the data to the graph
		XYItemRenderer itemrenderer1 = new XYLineAndShapeRenderer(false, true);
		XYItemRenderer itemrenderer2 = new XYLineAndShapeRenderer(false, true);
		plot.setDataset(0, dataset);
		plot.setRenderer(0, itemrenderer1);
		DateAxis domainAxis = new DateAxis("Year");
		plot.setDomainAxis(domainAxis); // set the title for the doamin axis
		if (titles[0].equals("per 1000 people")) { // 3 different title combinations for different current health expenditure analysis
			titles[1] = "per 1000 people"; // hospitals beds vs current health expenditure 
		}
		else if (titles[0].equals("per 1,000 live birth")){
			titles[1] = "per capita";// mortality rate vs current health expenditure 
		}
		else if ((titles[0].equals("% of GDP")) && (GEE)){
			titles[1] = ("% of GDP");// government expenditure on education vs current health expenditure 
		}
		plot.setRangeAxis(0,new NumberAxis(titles[0])); // set the titles for the different y axis
		plot.setRangeAxis(1,new NumberAxis(titles[1]));
		plot.setRenderer(1, itemrenderer2);
		plot.mapDatasetToRangeAxis(0, 0);// 1st dataset to 1st y-axis
		plot.mapDatasetToRangeAxis(1, 1); // 2nd dataset to 2nd y-axis
		scatterChart.setTitle(new TextTitle(analysisType, new Font("Serif", java.awt.Font.BOLD, 18))); // change the title to the analysis type
		
	}	
	

/**
 * Method for updating a dataset with the value gotten from the analysis
 * 
 * @param analysisData The processed analysis data collected from Analysis for the specific type of analysis
 * @param startYear A string that contains the name of the analysis
 * @param datasetTmp The dataset that will have the data added to it
 * @return the dataset that has had the data added to it
 */
	private TimeSeries updateDataset(float[] analysisData, int startYear, TimeSeries datasetTmp)
	{	
		for (int i = 0; i < analysisData.length; i++)
			datasetTmp.add(new Year(startYear + i), (Number)analysisData[i]); // add the new data to the dataset
		return datasetTmp;
	}
}
