package statsVisualiser.gui;

import com.google.gson.*;

/**
 * The abstract class in charge of processing the analysis of JSON objects returned from DataFetcher
 * Strategy component: Strategy
 *  
 * @author Nicolas Jacobs
 */
public abstract class AnalysisADT
{
	float[][] dataArray;
	
	/**
	 * The request of the system to analyze this country, under these year ranges, and this analysis type
	 * 
	 * @param countryName	The country code of the country being analyzed
	 * @param yearStart		The start year of the analysis
	 * @param yearEnd		The end year of the analysis
	 * @return	A double float array where the arrays of each analysis data is being stored
	 */
	public abstract float[][] analyze(String countryCode, int yearStart, int yearEnd);
	
	/**
	 * Process the JsonArray into an array of three float arrays
	 * 
	 * @param jsonData	The json file that stores the data accessed from DataFetcher
	 * @param arraySize	The size of how many items need to be stored (yearEnd - yearStart)
	 * @return	A single float array of the data collected from this analysis
	 */
	protected float[] processData(JsonArray jsonData, int arraySize)
	{
		float[] analyArray = new float[arraySize + 1];
		
		//Loop through the json array and store all the values in analyArray
		for (int i = 0; i < arraySize + 1; i++)
			try
			{
				//Set the elements in analyArray to be the values stored in the 'value' tag of the jsonArray
				analyArray[arraySize - i] = jsonData.get(1).getAsJsonArray().get(i).getAsJsonObject().get("value").getAsFloat();
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