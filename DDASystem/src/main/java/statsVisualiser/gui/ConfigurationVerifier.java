// Checks inputed parameters with database
package statsVisualiser.gui;

import javax.swing.JOptionPane;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class is used to verify the user inputed parameters that the user entered into the
 * Main GUI. It check country, years and model type.
 * 
 * @author Justin Teichman
 */

public class ConfigurationVerifier {

	
	/**
	 * This method checks if the user selected country with the configuration
	 * database. It return true if valid and false if not valid.
	 * 
	 * @param Analysis	User selected analysis.
	 * @param Country	User selected Country.
	 * @return		True if the parameters are valid, else, return false.
	 */
	
	public boolean checkCountry(String Analysis, String Country) {
		
		try {
			
			File configData = new File("ConfigurationDatabase.txt");
			Scanner scanner = new Scanner(configData);
			
			while(!scanner.nextLine().equals(Analysis)) {}	// scrolls to line section of type analysis
			
			String Countries;
			String[] countryVector;
			
			Countries = scanner.nextLine();		
			countryVector = Countries.split(",");	// takes commma seperated line and puts it into array
			
			if(countryVector[0].equals("")) {	// checks if there are no country restrictions
				return true;
			}
			
			for(int i = 0; i < countryVector.length; i++) {		// checks country selected is in country restricted list
				if(countryVector[i].equals(Country)) {
					new ExceptionHandler("Analysis not avaliable in Country");
					return false;
				}
			}
			
			return true;
		
		} catch (FileNotFoundException e) {
		
			new ExceptionHandler("Error has occured");
			return false;
		
		}
	}
	
	/**
	 * This method checks if the user selected startYear with the configuration
	 * database. It return true if valid and false if not valid.
	 * 
	 * @param Analysis	User selected analysis.
	 * @param startYear	User selected start year.
	 * @return		True if the parameters are valid, else, return false.
	 */
	public boolean startYear(String Analysis, int startYear) {
		
		try {
			
			File configData = new File("ConfigurationDatabase.txt");
			Scanner scanner = new Scanner(configData);
			
			while(!scanner.nextLine().equals(Analysis)) {}	// scrolls to line section of type analysis
			scanner.nextLine();
			
			String years;
			String[] yearVector;
			
			years = scanner.nextLine();
			yearVector = years.split(",");	// create 2 element array of years in string form
			ArrayList<Integer> dates = new ArrayList<Integer>();	// convert string array into integer array list
			dates.add(Integer.parseInt(yearVector[0]));
			dates.add(Integer.parseInt(yearVector[1]));
			
			if(startYear < dates.get(0) || startYear > dates.get(1)) {	//check if first element is in the range of valid years
				new ExceptionHandler("Analysis is valid from " + dates.get(0) + " to " + dates.get(1));
				return false;
			}
			
			return true;
			
		} catch (FileNotFoundException e) {
			new ExceptionHandler("Error has occured");
			return false;
		}
	}
	
	
	/**
	 * This method checks if the user selected endYear with the configuration
	 * database. It return true if valid and false if not valid.
	 * 
	 * @param Analysis	User selected analysis.
	 * @param endYear	User selected end year.
	 * @return		True if the parameters are valid, else, return false.
	 */
	public boolean endYear(String Analysis, int endYear) {
		
		try {
			
			File configData = new File("ConfigurationDatabase.txt");
			Scanner scanner = new Scanner(configData);
			
			while(!scanner.nextLine().equals(Analysis)) {} // scrolls to line section of type analysis
			scanner.nextLine();
			
			String years;
			String[] yearVector;
			
			years = scanner.nextLine();
			yearVector = years.split(",");	// create 2 element array of years in string form
			ArrayList<Integer> dates = new ArrayList<Integer>();	// convert string array into integer array list
			dates.add(Integer.parseInt(yearVector[0]));
			dates.add(Integer.parseInt(yearVector[1]));
			
			if(endYear > dates.get(1) || endYear < dates.get(0)) {	//check if second element is in the range of valid years
				new ExceptionHandler("Analysis is valid from " + dates.get(0) + " to " + dates.get(1));
				return false;
			}
			
			return true;
			
		} catch (FileNotFoundException e) {
			new ExceptionHandler("Error has occured");
			return false;
		}
	}
	
	/**
	 * This method checks if the user selected model with the configuration
	 * database. It return true if valid and false if not valid.
	 * 
	 * @param Analysis	User selected analysis.
	 * @param endYear	User selected end year.
	 * @return		True if the parameters are valid, else, return false.
	 */
	public boolean checkViewer(String Analysis, String model) {
	
		try {
		
			File configData = new File("ConfigurationDatabase.txt");
			Scanner scanner = new Scanner(configData);
			
			while(!scanner.nextLine().equals(Analysis)) {}
			scanner.nextLine();
			scanner.nextLine();
		
			String line = scanner.nextLine();	// scrolls to line containing model configuration
		
			if(line.equals("")) {		// if Pie Chart is absent then it is incompatiable with analysis
				new ExceptionHandler("Analysis is incompatiable with Pie Chart");
				return false;
			}
		
			return true;
		
		}catch(FileNotFoundException e) {
			new ExceptionHandler("Error has occured");
			return false;
		}
	}

}