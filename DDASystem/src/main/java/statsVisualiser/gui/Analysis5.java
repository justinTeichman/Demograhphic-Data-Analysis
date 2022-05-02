package statsVisualiser.gui;

import com.google.gson.*;

/**
 * This will analyze the data collected from DataFetcher for analysis type 5
 * Analysis type: Average of Government expenditure on education, total (% of GDP) for the selected years
 * --Compatible viewers--
 * Pie chart		YES
 * Line chart		YES
 * Bar chart		YES
 * Scatter chart	YES
 * Report			YES
 * 
 * @author Nicolas Jacobs
 */
public class Analysis5 extends AnalysisADT
{
	//Variables
	JsonArray edu;
	
	/**
	 * Perform an analysis of the subanalysis edu
	 * 
	 * @param countryName	The country code of the country being analyzed
	 * @param yearStart		The start year of the analysis
	 * @param yearEnd		The end year of the analysis
	 * @return	A double float array where the array of the analysis data is being stored
	 */
	public float[][] analyze(String countryCode, int yearStart, int yearEnd)
	{
		dataArray = new float[1][yearEnd - yearStart + 1];
		
		//Store the expenditure on education data in dataArray
		edu = DataFetcher.requestJSON(countryCode, yearStart, yearEnd, "SE.XPD.TOTL.GD.ZS");
		dataArray[0] = processData(edu, yearEnd - yearStart);
				
		return dataArray;
	}
}