package statsVisualiser.gui;

import java.io.File;
import java.util.Scanner;

/**
 * This class is the context of what the analysis type is
 * This class will use it's internal AnalysisADT object to store the analysis type that will be called
 * Strategy component: Context
 * 
 * @author Nicolas Jacobs
 *
 */
public class Analysis
{
	private AnalysisADT strategy;
	
	/**
	 * Constructor
	 * 
	 * @param analysisType	The type of analysis the user wants to perform
	 */
	public Analysis(AnalysisADT analysisType)
	{
		this.strategy = analysisType;
	}
	
	/**
	 * Set the current strategy to analysisType
	 * 
	 * @param analysisType	The type of analysis the user wants to perform
	 */
	public void setAnalysis(AnalysisADT analysisType)
	{
		this.strategy = analysisType;
	}
	
	/**
	 * Will call the appropriate analysis subclass and return the result from it
	 *  
	 * @param countryName	The country code of the country being analyzed
	 * @param yearStart		The start year of the analysis
	 * @param yearEnd		The end year of the analysis
	 * @return	A double float array where the arrays of each analysis data is being stored
	 */
	public float[][] analyze(String countryName, int yearStart, int yearEnd)
	{
		return strategy.analyze(countryName, yearStart, yearEnd);
	}
	
	/**
	 * Converts the full name of a country to the three letter code
	 * 
	 * @param countryName The full name of the country
	 * @return The three letter code of the country
	 */
	public String convertCountry(String countryName)
	{
		if (countryName.equals("Canada"))
			return "can";
		else if (countryName.equals("China"))
			return "chn";
		else if (countryName.equals("USA"))
			return "usa";
		else if (countryName.equals("France"))
			return "fra";
		else if (countryName.equals("Brazil"))
			return "bra";
		else
		{
			System.out.println("ERROR: The country name supplied is not valid");
			return null;
		}
	}
	
	/**
	 * Checks if the entire double array of analysis data is null
	 * 
	 * @param analysisData	The analysis data that is being checked
	 * @return	True if the analysis data is null - False if the analysis data is not all null
	 */
	public boolean isYearNull(float[][] analysisData)
	{
		for (int i = 0; i < analysisData.length; i++)
			for (int j = 0; j < analysisData[i].length; j++)
				if (analysisData[i][j] != 0.00)
					return false;
		
		return true;
	}
}
