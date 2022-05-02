// Imports all the necessary libraries for the program
package statsVisualiser.gui;
import java.io.File;
import java.util.Scanner;

/**
 * This class is used to verify the login credentials that the user entered into the
 * login GUI. It reads the valid usernames and passwords from the "CredentialsDatabase.txt"
 * and compares them to the users entered credentials.
 * 
 * @author Justin Weller
 */
public class CredentialsVerifier 
{
	/**
	 * A valid username from the credentials database.
	 */
	private String validUser;
	
	/**
	 * A valid password from the credentials database.
	 */
	private String validPass;
	
	/**
	 * This method compares the user's entered credentials with the textfile of valid
	 * credentials. If they are equal, this method returns true, else, it returns false.
	 * 
	 * @param user	The user's entered username.
	 * @param pass	The user's entered password.
	 * @return		True if the credentials are valid, else, return false.
	 */
	public boolean verifyCred(String user, String pass)
	{
		// Attempt to read the file.
		try
		{
			File credData = new File("CredentialsDatabase.txt");
			Scanner sc = new Scanner(credData);
			
			// Gets a pair of valid credentials
			while(sc.hasNextLine())
			{
				validUser = sc.nextLine();
				validPass = sc.nextLine();
				
				// If the credentials are valid, return true
				if(user.equals(validUser) && pass.equals(validPass))
				{
					return true;
				}
			}
		}
		// If the file cannot be read
		catch(Exception e)
		{
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		
		// Displays a popup to the user if the credentials are invalid and return false
		new ExceptionHandler("Invalid credentials entered, closing application.");
		
		return false;
	}
}
