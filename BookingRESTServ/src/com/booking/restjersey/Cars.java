package com.booking.restjersey;
/**
 * BookingGo Technical Test.
 * 
 * This class creates a Cars Object which stores information of the car types.
 * 
 * @author Rebekah Jordan 
 **/
public class Cars {
	
	String name;
	int seats;
	/**
	 * Constructor which sets the properties for created Cars objects.
	 * 
	 * @param name The name/car type of the vehicle
	 * @param seats The number of seats the vehicle has.
	 **/
	public Cars (String name, int seats)
	{
		this.name = name;
		this.seats = seats;
	}
	/**
	 * A method which returns the number of seats a vehicle has 
	 * 
	 * @return The number of seats. 
	 */
	public int getSeats()
	{
		return seats;
	}
	
	/**
	 * A method which returns name/car type of a vehicle 
	 * 
	 * @return The car type. 
	 */
	public String getName()
	{
		return name;
	}
}
