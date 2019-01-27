
package com.booking.restjersey;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import org.json.*;	

/**
 * BookingGo Technical Test.
 * 
 * This application takes information obtained from a supplier's API regarding their
 * current availability which is searched and presented in a format to aid a 
 * customer in choosing their best option. 
 * 
 * @author Rebekah Jordan 
 **/
public class BookingGoTechnical {

	//create array lists to hold all available options and all the car types
	static ArrayList <Options> allOptions = new ArrayList<Options>();
	static ArrayList <Cars> allCars = new ArrayList<Cars>();
	
	/**
	 * The main method takes input parameters which can then be used to 
	 * receive the responses from the suppliers regarding suitable vehicles.
	 * It then calls the method to display the best options. 
	 */
	public static void main(String[] args) {
      
	    try
	    {
	      	System.out.println("----BookingGo Technical Test----");
	      	//UTILIZED DURING PART ONE OF THE TECHNICAL TASK. 
	      	
	      	//calls method to populate the car object's data
	      	populateCars();
	      	
	      	//receives user input
	      	Scanner input = new Scanner(System.in);
	      	System.out.println("Enter Pickup Co-ordinations: ");
	      	String pickupIn = input.nextLine();
	      	System.out.println("Enter Dropoff Co-ordinations: ");
	      	String dropoffIn = input.nextLine();
	      	System.out.println("Enter Number of Passengers: ");
	      	int passengers = input.nextInt();
	      	input.close();
	      	
	      	JSONArray options = new JSONArray();
	      	getResponse("dave", pickupIn, dropoffIn);
	      	getResponse("eric", pickupIn, dropoffIn);
	      	options = getBestOptions(getResponse("jeff", pickupIn, dropoffIn), passengers);
	      	System.out.println(options);
	      	
		}
		catch (Exception e)
		{
			System.out.println(e);
		} 
	}
	
	/**
	 * A method which interacts with APIs to get responses from the suppliers
	 * regarding their available vehicles. 
	 *
	 * @param name The name of the supplier which can be inputed into the URL.
	 * @param pickupIn The location at which the customer wishes to be picked up from.
	 * @param dropoffIn The location at which the customer wishes to be dropped off at. 
	 * @param numPass The number of passengers wishing to be transported. 
	 * @return An array list containing all the options the supplier can provide, in ascending order. 
	 */
    public static ArrayList<Options> getResponse(String name, String pickupIn, String dropoffIn)
    {
    	try {
    		long startTime = 0;
    		
    		//allows a response only for 2 seconds.
    		while (startTime < 2000)
    		{
    			//URL for each specific supplier
	    		URL urlForGetRequest = new URL("https://techtest.rideways.com/" + name + "/?pickup=" + pickupIn + "&dropoff=" + dropoffIn);
	    		String readLine = null;
	    		
	    		//opens HTTP Connection, sets request type
		    	HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection(); 	
		    	conection.setRequestMethod("GET");
		   		int responseCode = conection.getResponseCode();
		    	
		   		//if response received
		    	if (responseCode == HttpURLConnection.HTTP_OK) 
		    	{
			        //reads in response
		    		BufferedReader in = new BufferedReader(new InputStreamReader(conection.getInputStream()));
			        StringBuffer response = new StringBuffer();
			        try {
						while ((readLine = in .readLine()) != null) 
						{
						    response.append(readLine);
						}
					} catch (IOException e) {
							
						e.printStackTrace();
					} 
			        in .close();
			        
			        //parse response
			        JSONObject obj = new JSONObject(response.toString());
		            String supplier_id = obj.getString("supplier_id");
		            JSONArray options = obj.getJSONArray("options");
		            
		            //adds options into array
		            for (int i = 0; i < options.length(); i++)
		            {
		            	Options op = new Options(options.getJSONObject(i).getString("car_type"), options.getJSONObject(i).getInt("price"), supplier_id);
		            	allOptions.add(op);           	
		            }
				    
		            //sort array into cheapest price first and return options.
				    Collections.sort(allOptions);
				    //System.out.println(allOptions.toString());
				    return allOptions;
				    
		    	}		    	
    		}
    		//if timeout occurs, ignore supplier
    		if (startTime > 2000)
    		{
    			System.out.println("SUPPLIER " + name + " TOOK TOO LONG TO RESPOND.");
    		}
    	}
    	catch (Exception e)
		{
			System.out.println(e);
		}
		return allOptions;	   	
    }  
    
	/**
	 * A method which finds the cheapest prices for each individual car type and prints 
	 * them in a json payout.  
	 *
	 * @param allOp The array list containing all possible options.
	 * @param numPass The number of passengers wishing to utilise the service. 
	 */
    public static JSONArray getBestOptions(ArrayList<Options> allOp, int numPass)
    {
    	//creates new array list to add best options to.
    	ArrayList<Options> bestOptions = new ArrayList<Options>();
    	
    	//creates list to only have one of each type of car.
    	List<String> types = new ArrayList<String>();
    	
    	for (Options i: allOp)
	    {
    		for (Cars j: allCars)
    		{
    			// checks if the car types are the same, checks to see if the type hasn't already been used and checks the car has enough seats to
    			// cater to the amount of passengers. 
    			// as the options array list is sorted cheapest first, only the cheapest options will appear for each car type. 
    			if (j.getName().contains(i.getType()) && !types.contains(i.getType()) && j.getSeats()>= numPass )
        		{
    				//adds option to new array list
    				//add car type to types list to state it already has the cheapest priced car. 
        			bestOptions.add(i);
        			types.add(i.getType());
        		}   
    		}  		
	    } 
    	//convert to json payout 
    	JSONArray output = new JSONArray(bestOptions);
    	return output;
    }
	/**
	 * A method which creates a Cars object and adds to the all Cars array list.
	 * Note: Would not normally use this method.  
	 */
    public static void populateCars()
    {
    	//TODO make this not hard coded - read from file? 
    	 Cars standard = new Cars("STANDARD", 4);
    	 Cars executive = new Cars("EXECUTIVE", 4);
    	 Cars luxury = new Cars("LUXURY", 4);
    	 Cars peopleCar = new Cars("PEOPLE_CARRIER", 6);
    	 Cars peopleCarLux = new Cars("LUXURY_PEOPLE_CARRIER", 6);
    	 Cars minibus = new Cars("MINIBUS", 16);
    	 allCars.add(standard);
    	 allCars.add(executive);
    	 allCars.add(luxury);
    	 allCars.add(peopleCar);
    	 allCars.add(peopleCarLux);
    	 allCars.add(minibus);
    }
    /**
     * A method which is called by the web service to receive a JSON object containing
     * the best options for the customer.
     * It manipulates the getResponse and getBestOptions methods. 
     * 
     * @param pickup The pickup location of the customer.
     * @param dropoff The dropoff location of the customer
     * @param passengers The number of passengers.
     * @return JSON object contains the return data.
     */
    public JSONArray getAllResponses(String pickup, String dropoff, int passengers)
    {
    	populateCars();
    	JSONArray bestOptions = new JSONArray();
    	
    	//gets responses from the suppliers 
      	getResponse("dave", pickup, dropoff);
      	getResponse("eric", pickup, dropoff);
      	bestOptions = getBestOptions(getResponse("jeff", pickup, dropoff), passengers);
      	//returns best options to service
    	return bestOptions;
    	
    }
    
}
