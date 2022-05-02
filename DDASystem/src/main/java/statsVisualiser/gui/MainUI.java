// Imports all the necessary libraries for the program
package statsVisualiser.gui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.util.TableOrder;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Year;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * This class creates and operates the main GUI of the program for the user. It is
 * responsible for a major portion of the DDA system: Taking in user selections,
 * adding various viewers, and displaying the results of the data retrieval to these
 * viewers. It handles all the required errors that may occur when interacting with
 * the GUI and is the main GUI that the user interacts with.
 * 
 * @author Justin Weller
 */
public class MainUI extends JFrame {
	
	/**
	 * The selected country value.
	 */
	private String countryValue;
	
	/**
	 * The selected start year value.
	 */
	private int startYearValue;
	
	/**
	 * The selected end year value.
	 */
	private int endYearValue;
	
	/**
	 * The selected viewer value.
	 */
	private String viewerValue;
	
	/**
	 * The selected analysis type value.
	 */
	private String analysisValue;
	
	/**
	 * All of the viewers currently added to the GUI.
	 */
	private String[] addedViewers = new String[5];
	
	/**
	 * The value of the subclass of Creator based on which viewer must be created 
	 * (Part of the factory pattern).
	 */
	private Creator viewCreator;
	
	/**
	 * The single instance of the Report viewer class.
	 */
	private Viewer reportInstance;
	
	/**
	 * The single instance of the BarChart viewer class.
	 */
	private Viewer barInstance;
	
	/**
	 * The single instance of the LineChart viewer class.
	 */
	private Viewer lineInstance;
	
	/**
	 * The single instance of the ScatterChart viewer class.
	 */
	private Viewer scatterInstance;
	
	/**
	 * The single instance of the PieChart viewer class.
	 */
	private Viewer pieInstance;
	
	/**
	 * The value of the JPanel holding all of the viewers.
	 */
	private JPanel west;
	
	/**
	 * The value of the panel holding the report viewer.
	 */
	private JScrollPane reportPanel;
	
	/**
	 * The value of the panel holding the pie viewer.
	 */
	private ChartPanel piePanel;
	
	/**
	 * The value of the panel holding the scatter viewer.
	 */
	private ChartPanel scatterPanel;
	
	/**
	 * The value of the panel holding the bar viewer.
	 */
	private ChartPanel barPanel;
	
	/**
	 * The value of the panel holding the line viewer.
	 */
	private ChartPanel linePanel;
	
	/**
	 * The value of the version of the program.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The value of the single instance of this MainUI class.
	 */
	private static MainUI instance;

	/**
	 * This method demonstrates the singleton pattern which ensures that
	 * only one instance of this MainUI class is created.
	 * 
	 * @return	The single instance of this class.
	 */
	public static MainUI getInstance() {
		// If this class is initially being created, then create a new instance.
		// and store it in the global static variable 'instance'. This will then
		// launch the constructor of this class.
		if (instance == null)
			instance = new MainUI();

		return instance;
	}

	/**
	 * This constructor of this class creates and displays the main GUI of the
	 * program to the user. It saves the users selections, checks for any errors
	 * that may occur, and interacts with the other methods in this class to add
	 * and remove viewers to it's GUI. It also displays the requested data to the
	 * viewers once the 'recalculate' button is pressed.
	 */
	private MainUI() {
		// Set window title
		super("Country Statistics");

		// Set top bar
		JLabel chooseCountryLabel = new JLabel("Choose a country: ");
		// Adds all the country options to a combo box
		Vector<String> countriesNames = new Vector<String>();
		countriesNames.add("");
		countriesNames.add("USA");
		countriesNames.add("Canada");
		countriesNames.add("France");
		countriesNames.add("China");
		countriesNames.add("Brazil");
		countriesNames.sort(null);
		final JComboBox<String> countriesList = new JComboBox<String>(countriesNames);

		// Adds all the start and end years to 2 different combo boxes
		JLabel from = new JLabel("From");
		JLabel to = new JLabel("To");
		Vector<String> years = new Vector<String>();
		for (int i = 2020; i >= 2010; i--) {
			years.add("" + i);
		}
		years.add("");
		final JComboBox<String> fromList = new JComboBox<String>(years);
		final JComboBox<String> toList = new JComboBox<String>(years);

		// Forms the top panel from the created elements
		JPanel north = new JPanel();
		north.add(chooseCountryLabel);
		north.add(countriesList);
		north.add(from);
		north.add(fromList);
		north.add(to);
		north.add(toList);

		// Set bottom bar
		JButton recalculate = new JButton("Recalculate");

		JLabel viewsLabel = new JLabel("Available Views: ");

		// Adds all the viewer type options to a combo box
		Vector<String> viewsNames = new Vector<String>();
		viewsNames.add("");
		viewsNames.add("Pie Chart");
		viewsNames.add("Line Chart");
		viewsNames.add("Bar Chart");
		viewsNames.add("Scatter Chart");
		viewsNames.add("Report");
		final JComboBox<String> viewsList = new JComboBox<String>(viewsNames);
		
		// Creates add and remove viewer buttons
		JButton addView = new JButton("+");
		JButton removeView = new JButton("-");

		JLabel methodLabel = new JLabel("        Choose analysis method: ");

		// Adds all the analysis types to a combo box
		Vector<String> methodsNames = new Vector<String>();
		methodsNames.add("");
		methodsNames.add("CO2 emissions vs Energy use vs PM2.5 air pollution");
		methodsNames.add("PM2.5 air pollution vs Forest area");
		methodsNames.add("CO2 emissions vs GDP");
		methodsNames.add("Forest area");
		methodsNames.add("Government education expenditure");
		methodsNames.add("Hospital beds vs Health expenditure");
		methodsNames.add("Health expenditure vs Mortality");
		methodsNames.add("Government education expenditure vs Health expenditure");


		final JComboBox<String> methodsList = new JComboBox<String>(methodsNames);

		// Creates the bottom panel from the created elements
		JPanel south = new JPanel();
		south.add(viewsLabel);
		south.add(viewsList);
		south.add(addView);
		south.add(removeView);

		south.add(methodLabel);
		south.add(methodsList);
		south.add(recalculate);

		JPanel east = new JPanel();

		// Set charts region
		west = new JPanel();
		west.setLayout(new GridLayout(2, 0));

		// Add all of the panes to the main GUI pane
		getContentPane().add(north, BorderLayout.NORTH);
		getContentPane().add(east, BorderLayout.EAST);
		getContentPane().add(south, BorderLayout.SOUTH);
		getContentPane().add(west, BorderLayout.WEST);
		
		// Saves the initial '""' value of the country selection
		countryValue = (String) countriesList.getSelectedItem();
		
		// Adds an action listener to the country selection whenever it changes
		countriesList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Saves the newly selected country value
				countryValue = (String) countriesList.getSelectedItem();
				ConfigurationVerifier config = new ConfigurationVerifier();
				
				// Checks if analysis is present
				if(analysisValue == "") {
					ExceptionHandler except = new ExceptionHandler("Error, please select an analysis.");
					countriesList.setSelectedItem("");
					countryValue ="";
					
				
				}else {
					
					// Uses Config class to check if selection is valid
					
					boolean cond;
					cond = config.checkCountry(analysisValue, countryValue);
					
					if(!cond) {
						countriesList.setSelectedItem("");
						countryValue ="";
					}
				}
			}
		});
		
		// Sets the initial value of the year selection to be blank '""'
		fromList.setSelectedItem("");
		
		// Adds an action listener to the year selection whenever it changes
		fromList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// The string value of the selected year
				String startStringValue = (String) fromList.getSelectedItem();
				ConfigurationVerifier config = new ConfigurationVerifier();
				
				
				// Checks if analysis is present
				if(analysisValue == "" ) {
					ExceptionHandler except = new ExceptionHandler("Error, please select an analysis.");
					fromList.setSelectedItem("");
					startYearValue = 0;
				
				}else {
					
					// Checks if start year is valid
					
					if(startStringValue.equals(""))	
					{
						startYearValue = 0;
					} 
					else 
					{
						startYearValue = Integer.parseInt(startStringValue);
						boolean cond;
						cond = config.startYear(analysisValue, startYearValue);
						
						// Use config class to carry out check 
						if(!cond) {
							fromList.setSelectedItem("");
							startYearValue = 0;
						}
						
					}
					
					// Checks if start year is greater then end year
					
					if(startYearValue != 0 && endYearValue != 0 && startYearValue > endYearValue)
					{
						ExceptionHandler except = new ExceptionHandler("Error, the start year can't be greater than the end year.");
						fromList.setSelectedItem("");
						startYearValue = 0;
						
						toList.setSelectedItem("");
						endYearValue = 0;					
					}
					
				}
			}
		});
		
		// Sets the initial value of the year selection to be blank '""'
		toList.setSelectedItem("");
		
		// Adds an action listener to the year selection whenever it changes
		toList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// The string value of the selected year
				String endStringValue = (String) toList.getSelectedItem();
				ConfigurationVerifier config = new ConfigurationVerifier();
				
				// Checks if analysis is present
				if(analysisValue == "") {
					ExceptionHandler except = new ExceptionHandler("Error, please select an analysis.");
					toList.setSelectedItem("");
					endYearValue = 0;
				}else {
				
					// Checks if end year is valid
					
					if(endStringValue.equals(""))
					{
						endYearValue = 0;
					}
					else
					{
						endYearValue = Integer.parseInt((String) toList.getSelectedItem());
						boolean cond;
						cond = config.endYear(analysisValue, endYearValue);
						
						// Use config class to check if end year is valid
						if(!cond) {
							toList.setSelectedItem("");
							endYearValue = 0;
						}
					}
					
					// Checks if start year is greater then end year
					
					if(startYearValue != 0 && endYearValue != 0 && startYearValue > endYearValue)
					{
						ExceptionHandler except = new ExceptionHandler("Error, the start year can't be greater than the end year.");
						fromList.setSelectedItem("");
						startYearValue = 0; 
						
						toList.setSelectedItem("");
						endYearValue = 0;					
					}
					
				}
			}
		});
		
		// Saves the initial country selection value '""'
		viewerValue = (String) viewsList.getSelectedItem();
		
		// Adds an action listener to the view selection whenever it changes
		viewsList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				viewerValue = (String) viewsList.getSelectedItem();
			}
		});
		
		// Adds an action listener to the add viewer button
		addView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				ConfigurationVerifier config = new ConfigurationVerifier();
				boolean cond = true;
				
				// If user tries to add Pie Chart, we call config class to verify 
				// that the request is valid
				
				if(analysisValue == "") {
					ExceptionHandler except = new ExceptionHandler("Error, please select an analysis.");
					viewerValue = "";
					viewsList.setSelectedItem("");
				}else {
					
					if(viewerValue == "Pie Chart" && analysisValue != "") {
						cond = config.checkViewer(analysisValue, viewerValue);
					}	
					
					if(!cond) {
						viewerValue = "";
						viewsList.setSelectedItem("");
						
					}else {
						
						// Checks which viewers are active
						
						for(int i = 0; i < addedViewers.length; i++)
						{
							if(viewerValue.equals(""))
							{
								break;
							}
							else if(addedViewers[i] == null)
							{
								addedViewers[i] = viewerValue;
								addViewer();
								break;
							} 
							else if(addedViewers[i].equals(viewerValue))
							{
								ExceptionHandler except = new ExceptionHandler("Error, viewer has already been added.");
								break;
							}
						}
					}	
				}	
			}
		});

		// Adds an action listener to the add viewer button
		removeView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				boolean removed = false;
								
				// Checks the array of the viewers already added
				for(int i = 0; i < addedViewers.length; i++)
				{
					// If the selected viewer is blank then nothing happens
					if(viewerValue.equals(""))
					{
						break;
					}
					// If a value in the array is null then checks the next element
					else if(addedViewers[i] == null)
					{
						continue;
					}
					// If a viewer exists in the array, then it is removed from the GUI
					else if(addedViewers[i].equals(viewerValue))
					{
						addedViewers[i] = null;
						removeViewer();
						removed = true;
						break;
					}
				}
				
				// If the viewer was not removed, an error occurs
				if(removed == false)
				{
					ExceptionHandler except = new ExceptionHandler("Error, viewer has not been removed.");
				}
			}
		});
		
		// Saves the initial country selection value '""'
		analysisValue = (String) methodsList.getSelectedItem();
		
		// Adds an action listener to the analysis type selection whenever it changes
		methodsList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Saves the newly selected analysis value
				analysisValue = (String) methodsList.getSelectedItem();
				
				methodsList.removeItem("");
				
				// Upon changing the analysis, all the other values are reset to blank
				countriesList.setSelectedItem("");
				fromList.setSelectedItem("");
				toList.setSelectedItem("");
				viewsList.setSelectedItem("");
				
				// All viewers are removed and the GUI is refreshed when the analysis changes
				west.removeAll();
				west.revalidate();
				west.repaint();
				
				// Resets the addedViewers array
				for(int i = 0; i < addedViewers.length; i++)
				{
					addedViewers[i] = null;
				}
			}
		});
		
		// Adds an action listener to the recalculate button
		recalculate.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				boolean empty = true;
				
				// Checks if a viewer exists on the GUI
				for(int i = 0; i < addedViewers.length; i++)
					if(addedViewers[i] != null)
					{
						empty = false;
						break;
					}
				
				// Checks if any of the selections are blank, an error occurs if they are
				if(countryValue.equals("") || startYearValue == 0 || endYearValue == 0 || analysisValue.equals(""))
				{
					ExceptionHandler except = new ExceptionHandler("Error, please fill in all selections.");
				
				// If no viewers are added, an error occurs
				}
				else if (empty)
				{
					ExceptionHandler except = new ExceptionHandler("Error, please add a viewer.");
					
				
				//There is no error, the data can be collected
				}
				else
				{
					//System.out.println("Parameters for Data retrieval from api " + "\n"+ countryValue + "," + startYearValue + "," + endYearValue + "," + analysisValue);
					
					Analysis analysis;
					//Call the correct analysis subclass
					if (analysisValue.equals("CO2 emissions vs Energy use vs PM2.5 air pollution"))
						analysis = new Analysis(new Analysis1());
					else if (analysisValue.equals("PM2.5 air pollution vs Forest area"))
						analysis = new Analysis(new Analysis2());
					else if (analysisValue.equals("CO2 emissions vs GDP"))
						analysis = new Analysis(new Analysis3());
					else if (analysisValue.equals("Forest area"))
						analysis = new Analysis(new Analysis4());
					else if (analysisValue.equals("Government education expenditure"))
						analysis = new Analysis(new Analysis5());
					else if (analysisValue.equals("Hospital beds vs Health expenditure"))
						analysis = new Analysis(new Analysis6());
					else if (analysisValue.equals("Health expenditure vs Mortality"))
						analysis = new Analysis(new Analysis7());
					else if (analysisValue.equals("Government education expenditure vs Health expenditure"))
						analysis = new Analysis(new Analysis8());
					else
					{
						System.out.println("ERROR: Invalid analysis name (This literally should not even be reachable!)");
						ExceptionHandler except = new ExceptionHandler("Error, this analysis name does not exist.");
						analysis = new Analysis(new Analysis1());
					}
					
					//Send the request to Analysis for the data
					float[][] analysisData = analysis.analyze(analysis.convertCountry(countryValue), startYearValue, endYearValue);
					//System.out.println(analysisData[0][0]);
					
					//Check if one of the year ranges is null
					if (analysis.isYearNull(analysisData))
					{
						ExceptionHandler except = new ExceptionHandler("Error, all the requested data is null for this time range.");
					}
					//The data is not all null and can be presented
					else
					{
						//Update the viewers that have been requested by the user
						for (String view : addedViewers)
						{
							try
							{
								if(view.equals("Pie Chart"))
									pieInstance.updateViewer(analysisData, analysisValue, startYearValue);
								else if(view.equals("Line Chart"))
									lineInstance.updateViewer(analysisData, analysisValue, startYearValue);
								else if(view.equals("Bar Chart"))
									barInstance.updateViewer(analysisData, analysisValue, startYearValue);
								else if(view.equals("Scatter Chart"))
									scatterInstance.updateViewer(analysisData, analysisValue, startYearValue);
								else if(view.equals("Report"))
									reportInstance.updateViewer(analysisData, analysisValue, startYearValue);
								
								west.revalidate();
								west.repaint();
							}
							catch (java.lang.NullPointerException e) {}
						}
					}
				}
				
				
				
				
				// ADD IN ANALYSIS GRABBING HERE, MAKE SURE IT ONLY HAPPENS IF NO ERRORS OCCUR
				

			}
		});
	}
	
	/**
	 * This method is invoked whenever the user attempts to add a viewer.
	 * The selected empty viewer is then added to the main GUI based on the
	 * selected viewerValue. This method initializes the factory pattern by
	 * calling the respective viewer's creator class, which then returns
	 * an instance of the viewer class and gets added to the west panel.
	 * The GUI finally gets refreshed after the method is finished.
	 */
	private void addViewer()
	{
		// Each option creates it's respective viewCreator value, saves an
		// instance of the viewer, and adds the viewer's panel to the
		// main GUI west panel.
		if(viewerValue.equals("Pie Chart"))
		{
			viewCreator = new PieCreator();
			pieInstance = viewCreator.makeViewer();
			piePanel = pieInstance.getChartPanel();
			west.add(piePanel);
		}
		else if(viewerValue.equals("Line Chart"))
		{
			viewCreator = new LineCreator();
			lineInstance = viewCreator.makeViewer();
			linePanel = lineInstance.getChartPanel();
			west.add(linePanel);
		}
		else if(viewerValue.equals("Bar Chart"))
		{
			viewCreator = new BarCreator();
			barInstance = viewCreator.makeViewer();
			barPanel = barInstance.getChartPanel();
			west.add(barPanel);
		}
		else if(viewerValue.equals("Scatter Chart"))
		{
			viewCreator = new ScatterCreator();
			scatterInstance = viewCreator.makeViewer();
			scatterPanel = scatterInstance.getChartPanel();
			west.add(scatterPanel);
		}
		else if(viewerValue.equals("Report"))
		{
			viewCreator = new ReportCreator();
			reportInstance = viewCreator.makeViewer();
			reportPanel = reportInstance.getScrollPanel();
			west.add(reportPanel);
		}
		west.revalidate();
		west.repaint();
	}
	
	/**
	 * This method is invoked whenever the user attempts to remove a viewer.
	 * The selected viewer is then removed from the panel and the GUI is 
	 * refreshed.
	 */
	private void removeViewer()
	{
		if(viewerValue.equals("Pie Chart"))
		{
			west.remove(piePanel);
		}
		else if(viewerValue.equals("Line Chart"))
		{
			west.remove(linePanel);
		}
		else if(viewerValue.equals("Bar Chart"))
		{
			west.remove(barPanel);
		}
		else if(viewerValue.equals("Scatter Chart"))
		{
			west.remove(scatterPanel);
		}
		else if(viewerValue.equals("Report"))
		{
			west.remove(reportPanel);
		}
		west.revalidate();
		west.repaint();
	}
}