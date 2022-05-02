package statsVisualiser.gui;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

/**
 * This class is responsible for accessing the world bank through an API and getting a json array 
 * --Country Codes--
 * Brazil: bra
 * China:  chn
 * USA:    usa
 * France: fra
 * Canada: can
 * 
 * @author Liam Garrett & Nicolas Jacobs
 */
public class DataFetcher
{	
	/**
	 * Send a request to the World Bank for a json object matching this country, year range, and analysis type
	 * 
	 * @param country	The country code for the country that is being analyzed. See above for a list of codes
	 * @param startYear	The year the analysis will start at
	 * @param endYear	The year the analysis will end at
	 * @param analysis	The code of what type of analysis for which data will be requested
	 * @return	A JsonArray object storing the data corresponding to the arguments
	 */
	public static JsonArray requestJSON(String country, int startYear, int endYear, String analysis)
	{
		String urlString = String.format("http://api.worldbank.org/v2/country/%s/indicator/%s?date=%s:%s&format=json", country, analysis, startYear , endYear);
		
		//Access the World Bank and return the json array
		try
		{
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
			
			if (conn.getResponseCode() == 200)
			{
				String inline = "";
				Scanner sc = new Scanner(url.openStream());
				while (sc.hasNext())
					inline += sc.nextLine();
				
				sc.close();
				return new JsonParser().parse(inline).getAsJsonArray();
			}
		}
		//Something went wrong and the World Bank likely does not have this data
		catch (IOException e) {}
		
		return null;
	}
}