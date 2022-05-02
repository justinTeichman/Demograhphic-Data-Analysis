package statsVisualiser.gui;

import com.google.gson.*;

/**
 * This will analyze the data collected from DataFetcher for analysis type 6
 * Analysis type: Ratio of Hospital beds (per 1,000 people) and Current health expenditure (per 1,000 people)
 * --Compatible viewers--
 * Pie chart		YES
 * Line chart		YES
 * Bar chart		YES
 * Scatter chart	YES
 * Report			YES
 * 
 * @author Nicolas Jacobs
 */
public class Analysis6 extends AnalysisADT
{
	//Variables
	JsonArray beds, health;
	
	/**
	 * Perform an analysis of each subanalysis (beds and health)
	 * 
	 * @param countryName	The country code of the country being analyzed
	 * @param yearStart		The start year of the analysis
	 * @param yearEnd		The end year of the analysis
	 * @return	A double float array where the arrays of each analysis data is being stored
	 */
	public float[][] analyze(String countryCode, int yearStart, int yearEnd)
	{
		dataArray = new float[2][yearEnd - yearStart + 1];
		
		//Store the hospital bed data in dataArray
		beds = DataFetcher.requestJSON(countryCode, yearStart, yearEnd, "SH.MED.BEDS.ZS");
		dataArray[0] = processData(beds, yearEnd - yearStart);
		
		//Store the health care expenditure data in dataArray
		health = DataFetcher.requestJSON(countryCode, yearStart, yearEnd, "SH.XPD.CHEX.PC.CD");
		dataArray[1] = processData(health, yearEnd - yearStart, 1000);
				
		return dataArray;
	}
	
	protected float[] processData(JsonArray jsonData, int arraySize, int multiple)
	{
		float[] analyArray = new float[arraySize + 1];
		
		//Loop through the json array and store all the values in analyArray
		for (int i = 0; i < arraySize + 1; i++)
			try
			{
				//Set the elements in analyArray to be the values stored in the 'value' tag of the jsonArray
				analyArray[arraySize - i] = jsonData.get(1).getAsJsonArray().get(i).getAsJsonObject().get("value").getAsFloat() * 1000;
			}
			//Catch any JsonNull errors and store the value 0.00 instead
			catch(java.lang.UnsupportedOperationException e)
			{
				analyArray[arraySize - i] = (float)0.00;
			}
			//Catch any errors where the json array does not exist
			catch(java.lang.NullPointerException e)
			{
				analyArray[arraySize - i] = (float)0.00;
			}
		
		return analyArray;
	}
}