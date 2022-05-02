package statsVisualiser.gui;

import com.google.gson.*;

/**
 * This will analyze the data collected from DataFetcher for analysis type 8
 * Analysis type: Ratio of Government expenditure on education, total (% of GDP) vs Current health expenditure (% of GDP)
 * --Compatible viewers--
 * Pie chart		YES
 * Line chart		YES
 * Bar chart		YES
 * Scatter chart	YES
 * Report			YES
 * 
 * @author Nicolas Jacobs
 */
public class Analysis8 extends AnalysisADT
{
	//Variables
	JsonArray edu, health;
	
	/**
	 * Perform an analysis of each subanalysis (edu and health)
	 * 
	 * @param countryName	The country code of the country being analyzed
	 * @param yearStart		The start year of the analysis
	 * @param yearEnd		The end year of the analysis
	 * @return	A double float array where the arrays of each analysis data is being stored
	 */
	public float[][] analyze(String countryCode, int yearStart, int yearEnd)
	{
		dataArray = new float[2][yearEnd - yearStart + 1];
		
		//Store the co2 levels in dataArray
		edu = DataFetcher.requestJSON(countryCode, yearStart, yearEnd, "SE.XPD.TOTL.GD.ZS");
		dataArray[0] = processData(edu, yearEnd - yearStart);
		
		//Store the gdp in dataArray
		health = DataFetcher.requestJSON(countryCode, yearStart, yearEnd, "SH.XPD.CHEX.GD.ZS");
		dataArray[1] = processData(health, yearEnd - yearStart);
				
		return dataArray;
	}
}