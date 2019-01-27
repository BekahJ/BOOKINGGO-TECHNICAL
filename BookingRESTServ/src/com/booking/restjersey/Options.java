package com.booking.restjersey;
/**
 * BookingGo Technical Test.
 * 
 * This class creates a Options Object which stores information about the different 
 * options a supplier can provide for the customer.
 * 
 * @author Rebekah Jordan 
 **/
public class Options implements Comparable<Options>{

	String type;
	int price;
	String supplier;
	/**
	 * Constructor which sets the properties for created Options objects.
	 * 
	 * @param car_type The car type of the vehicle
	 * @param price The price of the journey for the customer.
	 * @param supplier The name of the supplier.
	 **/
	public Options(String car_type, int price, String supplier)
	{
		type = car_type;
		this.price = price; 
		this.supplier = supplier;
	}
	
	/**
	 * A method which returns the price for the journey 
	 * 
	 * @return The price 
	 */
	public int getOptionPrice() {
			return price;
	}
	/**
	 * A method which returns the car type of the vehicle
	 * 
	 * @return The car type. 
	 */
	public String getType() {
		return type;
	}
	/**
	 * A method which returns the supplier of this vehicle
	 * 
	 * @return The supplier's name. 
	 */
	public String getSupplier() {
		return supplier;
	}
/**
 * A method which compares items in an array list to each other to order them
 * 
 * @param compareOp The options to compare.
 * @return The ordered position. 
 */
	@Override
	public int compareTo(Options compareOp) {
		int comparePrice=((Options)compareOp).getOptionPrice();
        return this.price-comparePrice;
	}
	/**
	 * A method which overrides the normal toString() function in order to print the details of an array list
	 * 
	 * @return The array list output in the specified format. 
	 */
	@Override
	public String toString()
	{
		String output = type + " - " + supplier + " - " + price + "\n";
		return output;
	}
}