package statsVisualiser.gui;

import com.google.gson.*;

/**
 * This will analyze the data collected from DataFetcher for analysis type 4
 * Analysis type: Average Forest area (% of land area) for the selected years
 * --Compatible viewers--
 * Pie chart		YES
 * Line chart		YES
 * Bar chart		YES
 * Scatter chart	YES
 * Report			YES
 * 
 * @author Nicolas Jacobs
 */
public class Analysis4 extends AnalysisADT
{
	//Variables
	JsonArray forest;
	
	/**
	 * Perform an analysis of the subanalysis forest
	 * 
	 * @param countryName	The country code of the country being analyzed
	 * @param yearStart		The start year of the analysis
	 * @param yearEnd		The end year of the analysis
	 * @return	A double float array where the array of the analysis data is being stored
	 */
	public float[][] analyze(String countryCode, int yearStart, int yearEnd)
	{
		dataArray = new float[1][yearEnd - yearStart + 1];
		
		//Store the forest coverage data in dataArray
		forest = DataFetcher.requestJSON(countryCode, yearStart, yearEnd, "AG.LND.FRST.ZS");
		dataArray[0] = processData(forest, yearEnd - yearStart);
				
		return dataArray;
	}
}