package statsVisualiser.gui;

import com.google.gson.*;

/**
 * This will analyze the data collected from DataFetcher for analysis type 3
 * Analysis type: Ratio of CO2 emissions (metric tons per capita) and GDP per capita (current US$)
 * --Compatible viewers--
 * Pie chart		YES
 * Line chart		YES
 * Bar chart		YES
 * Scatter chart	YES
 * Report			YES
 * 
 * @author Nicolas Jacobs
 */
public class Analysis3 extends AnalysisADT
{
	//Variables
	JsonArray co2, gdp;
	
	/**
	 * Perform an analysis of each subanalysis (co2 and gdp)
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
		co2 = DataFetcher.requestJSON(countryCode, yearStart, yearEnd, "EN.ATM.CO2E.PC");
		dataArray[0] = processData(co2, yearEnd - yearStart);
		
		//Store the gdp in dataArray
		gdp = DataFetcher.requestJSON(countryCode, yearStart, yearEnd, "NY.GDP.PCAP.CD");
		dataArray[1] = processData(gdp, yearEnd - yearStart);
				
		return dataArray;
	}
}