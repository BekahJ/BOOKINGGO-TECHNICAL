package com.booking.restjersey;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.json.JSONArray;
import org.json.JSONException;
 
@Path("/bookingservice")

/**
 * This class creates a connection to the web service using a REST API.
 * 
 * @author Rebekah Jordan
 *
 */
public class BookingService {
 
	@Path("{s}/{f}/{p}")
	@GET
	@Produces("application/json")
	
	/**
	 * A method which communicates with methods to interact and receive responses from
	 * the suppliers APIs. It takes the customers details to formulate the response.
	 * 
	 * @param s The start point of the customer's journey.
	 * @param f The finish point of the customer's journey.
	 * @param p The number of passengers involved in this journey.
	 * @return The list of options for the seller, in the format of a JSON payload.
	 * @throws JSONException
	 */
	public Response getOptions(@PathParam("s") String s,@PathParam("f") String f, @PathParam("p") int p) throws JSONException {
		
		//utilizes the methods of the BookingGoTechnical class to gain responses
		JSONArray jsonArray = new JSONArray();
		BookingGoTechnical Options = new BookingGoTechnical();
		jsonArray = Options.getAllResponses(s,f,p);
		
		//formats response and returns 
		String result = jsonArray.toString();
		return Response.status(200).entity(result).build();
	}
}
