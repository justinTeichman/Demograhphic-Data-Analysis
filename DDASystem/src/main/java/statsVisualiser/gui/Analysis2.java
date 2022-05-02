package statsVisualiser.gui;

import com.google.gson.*;

/**
 * This will analyze the data collected from DataFetcher for analysis type 2
 * Analysis type: PM2.5 air pollution, mean annual exposure (micrograms per cubic meter) vs Forest area (% of land area)
 * --Compatible viewers--
 * Pie chart		NO
 * Line chart		YES
 * Bar chart		YES
 * Scatter chart	YES
 * Report			YES
 * 
 * @author Nicolas Jacobs
 */
public class Analysis2 extends AnalysisADT
{
	//Variables
	JsonArray pm, forest;
	
	/**
	 * Perform an analysis of each subanalysis (pm and forest)
	 * 
	 * @param countryName	The country code of the country being analyzed
	 * @param yearStart		The start year of the analysis
	 * @param yearEnd		The end year of the analysis
	 * @return	A double float array where the arrays of each analysis data is being stored
	 */
	public float[][] analyze(String countryCode, int yearStart, int yearEnd)
	{
		dataArray = new float[2][yearEnd - yearStart + 1];
		
		//Store the pm2.5 pollution data in dataArray
		pm = DataFetcher.requestJSON(countryCode, yearStart, yearEnd, "EN.ATM.PM25.MC.M3");
		dataArray[0] = processData(pm, yearEnd - yearStart);
		
		//Store the forest coverage data in dataArray
		forest = DataFetcher.requestJSON(countryCode, yearStart, yearEnd, "AG.LND.FRST.ZS");
		dataArray[1] = processData(forest, yearEnd - yearStart);
				
		return dataArray;
	}
}