package statsVisualiser.gui;

import com.google.gson.*;

/**
 * This will analyze the data collected from DataFetcher for analysis type 7
 * Analysis type: Current health expenditure per capita (current US$) vs Mortality rate, infant (per 1,000 live births)
 * --Compatible viewers--
 * Pie chart		NO
 * Line chart		YES
 * Bar chart		YES
 * Scatter chart	YES
 * Report			YES
 * 
 * @author Nicolas Jacobs
 */
public class Analysis7 extends AnalysisADT
{
	//Variables
	JsonArray health, mor;
	
	/**
	 * Perform an analysis of each subanalysis (health and mor)
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
		health = DataFetcher.requestJSON(countryCode, yearStart, yearEnd, "SH.XPD.CHEX.PC.CD");
		dataArray[0] = processData(health, yearEnd - yearStart);
		
		//Store the gdp in dataArray
		mor = DataFetcher.requestJSON(countryCode, yearStart, yearEnd, "SP.DYN.IMRT.IN");
		dataArray[1] = processData(mor, yearEnd - yearStart);
				
		return dataArray;
	}
}