package com.nasa.spaceapps.resources;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONObject;

import com.nasa.spaceapps.ResponseGenerator;

@Produces(MediaType.APPLICATION_JSON)
@Path("/apod")
public class ApodResource {

	@GET
	@Path("/hello")
	public String hello() {
		return "Hello!";
	}

	@GET
	@Path("pictures")
	public JSONObject pictures(@QueryParam("date") String date) {
		DateFormat dateFormat = new SimpleDateFormat("YYYY-mm-DD");
		JSONObject response = null;
		
		try {
			dateFormat.parse(date);
			
			ResponseGenerator responseGenerator = new ResponseGenerator();
			response = responseGenerator.makeRestCalls(date);
			
		} catch (ParseException e) {
			
		}
		
		return response;
	}

}
