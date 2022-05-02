package statsVisualiser.gui;

import com.google.gson.*;

/**
 * This will analyze the data collected from DataFetcher for analysis type 1
 * Analysis type: CO2 emissions (metric tons per capita) vs Energy use (kg of oil equivalent per capita) vs PM2.5 air pollution, mean annual exposure (micrograms per cubic meter)
 * --Compatible viewers--
 * Pie chart		NO
 * Line chart		YES
 * Bar chart		YES
 * Scatter chart	YES
 * Report			YES
 * 
 * @author Nicolas Jacobs
 */
public class Analysis1 extends AnalysisADT
{
	//Variables
	JsonArray co2, energy, pm;
	
	/**
	 * Perform an analysis of each subanalysis (co2, energy use, and pm2.5)
	 * 
	 * @param countryName	The country code of the country being analyzed
	 * @param yearStart		The start year of the analysis
	 * @param yearEnd		The end year of the analysis
	 * @return	A double float array where the arrays of each analysis data is being stored
	 */
	public float[][] analyze(String countryCode, int yearStart, int yearEnd)
	{
		dataArray = new float[3][yearEnd - yearStart + 1];
		
		//Store the co2 levels in dataArray
		co2 = DataFetcher.requestJSON(countryCode, yearStart, yearEnd, "EN.ATM.CO2E.PC");
		dataArray[0] = processData(co2, yearEnd - yearStart);
		
		//Store the energy use data in dataArray
		energy = DataFetcher.requestJSON(countryCode, yearStart, yearEnd, "EG.USE.PCAP.KG.OE");
		dataArray[1] = processData(energy, yearEnd - yearStart);
		
		//Store the pm2.5 pollution data in dataArray
		pm = DataFetcher.requestJSON(countryCode, yearStart, yearEnd, "EN.ATM.PM25.MC.M3");
		dataArray[2] = processData(pm, yearEnd - yearStart);
				
		return dataArray;
	}
}